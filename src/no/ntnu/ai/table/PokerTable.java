package no.ntnu.ai.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import no.ntnu.ai.player.PokerPlayer;

public class PokerTable{

	private final List<PokerPlayer> players = new ArrayList<PokerPlayer>();
	private final int maxPlayers;
	private final HashMap<PokerPlayer, Integer> bets = new HashMap<PokerPlayer, Integer>(); 
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
			this.addPlayer(p);
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

	public boolean removePlayer(PokerPlayer player){
		if(!playing){
			this.players.remove(player);
			return true;
		}
		return false;
	}

	private void nextDealer(){
		this.dealer = (dealer + 1) % this.players.size();
	}

	public void nextRound(){
		//We first need to check that both small blind and big blind
		//players have put up their blinds.
		if(bets.get(getCurrentSmallBlindPlayer()) >= smallBlind){
			if (bets.get(getCurrentBigBlindPlayer()) >= bigBlind){
				this.round++;
				this.nextDealer();
				this.playing = false;
				this.bets.clear(); //Clear this rounds bets
				return;
			}
		}
		throw new IllegalStateException("All blinds have not been payed");
	}

	public void makeBet(PokerPlayer player, int amount){
		if(this.players.contains(player)){
			this.bets.put(player, amount + 
					(bets.containsKey(player) ? bets.get(player) : 0));
		}else{
			throw new IllegalStateException("A player who is not playing at this " +
					"table can not make a bet at this table");
		}
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

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public boolean isPlaying() {
		return playing;
	}

	public int getCurrentRound(){
		return this.round;
	}

	public int getPotSize(){
		int pot = 0;
		for(Integer i : bets.values()){
			pot += i;
		}
		return pot;
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
