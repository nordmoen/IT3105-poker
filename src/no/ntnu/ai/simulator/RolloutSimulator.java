package no.ntnu.ai.simulator;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import no.ntnu.ai.deck.Deck;
import no.ntnu.ai.master.PreFlopMaster;
import no.ntnu.ai.player.PokerHand;

public class RolloutSimulator implements Runnable {

	private final int maxPlayers;
	private final int nrSimulations; 
	private final BlockingQueue<SimResult> output;
	private final List<PokerHand> hands; //hands to check

	public RolloutSimulator(int maxPlayers, int nrSimulations, BlockingQueue<SimResult> out,
			List<PokerHand> hands){
		this.maxPlayers = maxPlayers;
		this.nrSimulations = nrSimulations;
		this.output = out;
		this.hands = hands;
	}

	@Override
	public void run() {
		for(PokerHand testHand : this.hands){
			Deck cloneDeck = Deck.getInstance();
			cloneDeck.remove(testHand.getC1());
			cloneDeck.remove(testHand.getC2());

			PreFlopMaster master = new PreFlopMaster(testHand);

			HashMap<Integer, TestResult> results = new HashMap<Integer, TestResult>();

			for(int i = 2; i < maxPlayers + 1; i++){
				master.addPlayer();
				try {
					TestResult res = master.simulate(nrSimulations, (Deck) cloneDeck.clone());
					results.put(i, res);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
			try {
				output.put(new SimResult(testHand, results));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
