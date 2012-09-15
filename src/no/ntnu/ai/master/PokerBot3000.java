package no.ntnu.ai.master;

import java.util.ArrayList;

import no.ntnu.ai.player.Phase1Player;
import no.ntnu.ai.player.Phase2Player;
import no.ntnu.ai.player.Phase3Player;
import no.ntnu.ai.player.PokerPlayer;
import no.ntnu.ai.table.PokerTable;

public class PokerBot3000 {

	private final static int numPhase1 = Integer.parseInt(System.getProperty("phase1", "4"));
	private final static int numPhase2 = Integer.parseInt(System.getProperty("phase2", "0"));
	private final static int numPhase3 = Integer.parseInt(System.getProperty("phase3", "0"));
	private final static int smallBlind = Integer.parseInt(System.getProperty("smallBlind", "10"));
	private final static int bigBlind = Integer.parseInt(System.getProperty("bigBlind", "20"));
	private final static int simulations = Integer.parseInt(System.getProperty("sims", "1000"));
	private final static String filename = System.getProperty("rolloutFilename", "100k.txt");
	private final static String[] phase3Aggs = System.getProperty("phase3Aggs").split(",");
	private final static int learningSims = Integer.parseInt(System.getProperty("learningSims", "10000"));




	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(numPhase1 + numPhase2 + numPhase3 < 11){
			ArrayList<PokerPlayer> players = new ArrayList<PokerPlayer>();
			for(int i = 0; i < numPhase1; i++){
				players.add(new Phase1Player("Player " + i, 1000));
			}
			for(int i = 0; i < numPhase2; i++){
				players.add(new Phase2Player("Player " + i, 1000, filename));
			}
			for(int i = 0; i < numPhase3; i++){
				players.add(new Phase3Player("Player " + i, 1000, filename, Double.parseDouble(phase3Aggs[i])));
			}

			PokerMaster master = new PokerMaster(players, new PokerTable(smallBlind, bigBlind), true);
			master.simulate(learningSims);

			if(numPhase3 > 0){
				for(PokerPlayer p:players){
					p.reset();
				}
				master = new PokerMaster(players, new PokerTable(smallBlind, bigBlind), false);
				master.simulate(simulations);
			}
		}

	}

}
