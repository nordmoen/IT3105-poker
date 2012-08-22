package no.ntnu.ai.table;

import java.util.ArrayList;
import java.util.List;

import no.ntnu.ai.player.PokerPlayer;

public class PokerTable{

	private final List<PokerPlayer> players = new ArrayList<PokerPlayer>();
	private int dealer = 0;
	private int smallBlind, bigBlind, round = 0;

	/**
	 * Create a new PokerTable
	 * @param small - The starting small blind
	 * @param big - The starting big blind
	 */
	public PokerTable(int small, int big){
		this.smallBlind = small;
		this.bigBlind = big;
	}

	/**
	 * Create a new Poker table with the given players
	 * @param s - The starting small blind
	 * @param b - The starting big blind
	 * @param players - The players to add
	 */
	public PokerTable(int s, int b, PokerPlayer[] players){
		this(s, b);
		for(PokerPlayer p : players){
			this.addPlayer(p);
		}
	}

	public void addPlayer(PokerPlayer player){
		this.players.add(player);
	}

	public void removePlayer(PokerPlayer player){
		this.players.remove(player);
	}

	private void nextDealer(){
		this.dealer = (dealer + 1) % this.players.size();
	}

	public void nextRound(){
		this.round++;
		this.nextDealer();
	}

	public PokerPlayer getCurrentDealer(){
		return this.players.get(this.dealer);
	}

	public PokerPlayer getCurrentSmallBlindPlayer(){
		return this.getPlayer(1);
	}

	public PokerPlayer getCurrentBigBlindPlayer(){
		return this.getPlayer(2);
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

	public List<PokerPlayer> getPlayers() {
		return players;
	}

	public int getCurrentRound(){
		return this.round;
	}

	/**
	 * Get the player which is index after the current dealer. This means that
	 * getPlayer(0) returns the dealer, getPlayer(1) returns the smallBlind and so on.
	 * @param index - The index from the current dealer to get
	 * @return - The poker player
	 */
	public PokerPlayer getPlayer(int index){
		return this.players.get((this.dealer + index) % this.players.size());
	}
}
