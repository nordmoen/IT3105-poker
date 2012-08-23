package no.ntnu.ai.simulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
		InputStreamReader converter = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(converter);
		SimResultWriter writer = SimResultWriter.getInstance();
		BlockingQueue<SimResult> queue = new LinkedBlockingQueue<SimResult>();
		writer.addOutput(queue);
		writer.setOutputFilename(getOutName(in));

		List<PokerHand> hands = CardUtils.generatePreFlop();

		List<RolloutSimulator> sims = new ArrayList<RolloutSimulator>();
		int numCores = getNumCores(in);
		int maxP = getMaxPlayers(in);
		int numSims = getNumSims(in);
		int rounded = (int) Math.floor((double)hands.size()/numCores);

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

	private static int getNumSims(BufferedReader in) {
		System.out.print("Select number of simulations per preflop hand: ");
		try {
			return Integer.parseInt(in.readLine());
		} catch (NumberFormatException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return 100;
	}

	private static int getMaxPlayers(BufferedReader in) {
		System.out.print("How many players should be simulated: ");
		try {
			return Integer.parseInt(in.readLine());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 2;
	}

	private static String getOutName(BufferedReader in){
		System.out.print("Select output filename: ");
		try {
			return in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return "TestResult.txt";
		}
	}

	private static int getNumCores(BufferedReader in){
		System.out.print("Select number of cores to run on: ");
		try {
			return Integer.parseInt(in.readLine());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 1;
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
	}

}
