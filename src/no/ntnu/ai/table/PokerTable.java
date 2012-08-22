package no.ntnu.ai.table;

import java.util.LinkedList;

import no.ntnu.ai.players.PokerPlayer;

public class PokerTable {

	private LinkedList<PokerPlayer> players = new LinkedList<PokerPlayer>();
	private final int maxPlayers;
	private int dealer = 0;
	private boolean playing = false;
	private int smallBlind, bigBlind, round = 0;

	/**
	 * Create a new PokerTable
	 * @param maxPlayers - Maximum amount of players
	 * @param small - The starting small blind
	 * @param big - The starting big blind
	 */
	public PokerTable(int maxPlayers, int small, int big){
		this.maxPlayers = maxPlayers;
		this.smallBlind = small;
		this.bigBlind = big;
	}

	/**
	 * Create a new Poker table with the given players
	 * @param m - The maximum amount of players
	 * @param s - The starting small blind
	 * @param b - The starting big blind
	 * @param players - The players to add
	 */
	public PokerTable(int m, int s, int b, PokerPlayer[] players){
		this(m, s, b);
		for(PokerPlayer p : players){
			this.players.add(p);
		}
	}

	public boolean addPlayer(PokerPlayer player){
		if(!playing){
			if(this.players.size() < maxPlayers){
				this.players.add(player);
				return true;
			}
		}
		return false;
	}
	
	public void nextDealer(){
		if(playing){
			this.dealer = (dealer + 1) % this.players.size();
		}else{
			throw new IllegalStateException("Can't go to next dealer if this " +
					"table has not started to play");
		}
	}
	
	public PokerPlayer getCurrentDealer(){
		return this.players.get(this.dealer);
	}
	
	public PokerPlayer getCurrentSmallBlindPlayer(){
		return this.players.get(this.dealer + 1);
	}
	
	public PokerPlayer getCurrentBigBlindPlayer(){
		return this.players.get(this.dealer + 2);
	}

	public int getSmallBlind() {
		return smallBlind;
	}

	public void setSmallBlind(int smallBlind) {
		this.smallBlind = smallBlind;
	}

	public int getBigBlind() {
		return bigBlind;
	}

	public void setBigBlind(int bigBlind) {
		this.bigBlind = bigBlind;
	}

	public LinkedList<PokerPlayer> getPlayers() {
		return players;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public boolean isPlaying() {
		return playing;
	}
	
	public int getCurrentRound(){
		return this.round;
	}
	

}
