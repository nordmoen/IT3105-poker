package no.ntnu.ai.simulator;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import no.ntnu.ai.deck.Deck;
import no.ntnu.ai.player.PokerHand;

public class RolloutSimulator implements Runnable {
	
	private final int maxPlayers;
	private final long nrSimulations; 
	private final BlockingQueue<SimResult> output;
	private final List<PokerHand> hands; //hands to check
	
	public RolloutSimulator(int maxPlayers, long nrSimulations, BlockingQueue<SimResult> out,
			List<PokerHand> hands){
		this.maxPlayers = maxPlayers;
		this.nrSimulations = nrSimulations;
		this.output = out;
		this.hands = hands;
	}

	@Override
	public void run() {
		for(PokerHand testHand : this.hands){
			Deck cloneDeck = new Deck();
			cloneDeck.remove(testHand.getC1());
			cloneDeck.remove(testHand.getC2());
			
			for(int i = 2; i < maxPlayers + 1; i++){
				
			}
		}
	}

}
