package no.ntnu.ai.simulator;

import java.util.concurrent.BlockingQueue;

import no.ntnu.ai.player.PokerHand;

public class RolloutSimulator implements Runnable {
	
	private final int maxPlayers;
	private final long nrSimulations; 
	private final BlockingQueue<String> output;
	private final PokerHand[] hands; //hands to check
	
	public RolloutSimulator(int maxPlayers, long nrSimulations, BlockingQueue<String> out,
			PokerHand[] hands){
		this.maxPlayers = maxPlayers;
		this.nrSimulations = nrSimulations;
		this.output = out;
		this.hands = hands;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
