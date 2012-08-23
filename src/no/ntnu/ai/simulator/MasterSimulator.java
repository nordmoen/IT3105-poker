package no.ntnu.ai.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.deck.Suit;
import no.ntnu.ai.player.PokerHand;

public class MasterSimulator {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		SimResultWriter writer = SimResultWriter.getInstance();
		BlockingQueue<SimResult> queue = new LinkedBlockingQueue<SimResult>();
		writer.addOutput(queue);
		writer.setOutputFilename("TestOutput.txt");
		
		List<PokerHand> hands = new ArrayList<PokerHand>();
		hands.add(new PokerHand(new Card(2, Suit.HEARTS), new Card(2, Suit.CLUBS)));
		RolloutSimulator simer = new RolloutSimulator(2, 100, queue, hands);
		
		Thread wThread = new Thread(writer);
		Thread sThread = new Thread(simer);
		
		long start = System.currentTimeMillis();
		sThread.start();
		wThread.start();
		
		wThread.join();
		System.out.println("Simulation Done!");
		System.out.println("Simulation tok " + (System.currentTimeMillis() - start) +
				"ms");
	}

}
