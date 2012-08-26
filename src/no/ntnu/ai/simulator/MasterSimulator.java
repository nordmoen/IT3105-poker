package no.ntnu.ai.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import no.ntnu.ai.deck.CardUtils;
import no.ntnu.ai.player.PokerHand;

public class MasterSimulator {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {

		List<PokerHand> hands = CardUtils.generatePreFlop();
		int numCores = Integer.parseInt(System.getProperty("numCores", 
				Runtime.getRuntime().availableProcessors() + ""));
		int maxP = Integer.parseInt(System.getProperty("maxPlayers", "2"));
		int numSims = Integer.parseInt(System.getProperty("numSims", "1000"));
		int rounded = (int) Math.floor((double)hands.size()/numCores);
		String filename = System.getProperty("filename", "preflot_data.txt");
		
		System.out.println("Running preflop simulator on " + numCores + " cores. ");
		System.out.println("Simulating " + maxP + " players.");
		System.out.println("Running " + numSims + " simulations per poker hand.");
		System.out.println("Each core will have about " + rounded + " poker hands each.");
		System.out.println("Results will end up in " + filename);
		
		SimResultWriter writer = SimResultWriter.getInstance();
		BlockingQueue<SimResult> queue = new LinkedBlockingQueue<SimResult>();
		writer.addOutput(queue);
		writer.setOutputFilename(filename);

		List<RolloutSimulator> sims = new ArrayList<RolloutSimulator>();

		for(int i = 0; i < numCores - 1; i++){
			sims.add(new RolloutSimulator(maxP, numSims, queue, hands.subList(i*rounded, rounded*(i+1))));
		}
		sims.add(new RolloutSimulator(maxP, numSims, queue, hands.subList((numCores-1)*rounded, hands.size())));
		
		Thread wThread = new Thread(writer);
		wThread.start();
		System.out.println("Starting simulation");
		long start = System.currentTimeMillis();
		for(RolloutSimulator sim : sims){
			new Thread(sim).start();
		}
		System.out.println("...");
		wThread.join();
		System.out.println("Done!");
		System.out.println("It took " + (System.currentTimeMillis() - start) + "ms");
	}

}
