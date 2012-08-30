package no.ntnu.ai.master;

import java.util.ArrayList;

import no.ntnu.ai.player.Phase1Player;
import no.ntnu.ai.player.PokerPlayer;
import no.ntnu.ai.table.PokerTable;

public class Phase1Master {
	
	private final static int numbPlayers = Integer.parseInt(System.getProperty("players", "4"));
	private final static int smallBlind = Integer.parseInt(System.getProperty("smallBlind", "10"));
	private final static int bigBlind = Integer.parseInt(System.getProperty("bigBlind", "20"));
	private final static int simulations = Integer.parseInt(System.getProperty("sims", "1000"));
	
	
	private final static PokerTable table = new PokerTable(smallBlind, bigBlind);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<PokerPlayer> players = new ArrayList<PokerPlayer>();
		for(int i = 0; i < numbPlayers; i++){
			players.add(new Phase1Player("Player " + i, 1000));
		}
		
		PokerMaster master = new PokerMaster(players, table);
		master.simulate(simulations);

	}

}
