package no.ntnu.ai.player;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.hands.PowerRating;

public interface PokerPlayer {
	
	/**
	 * Get a decision from a Player about what to do
	 * @param table - The cards on the table
	 * @param smallBlind - The small blind amount
	 * @param bigBlind - The big blind amount
	 * @param amount - The amount the player must add to call
	 * @return - The desired action
	 */
	public PokerAction getDecision(Card[] table, int smallBlind, int bigBlind, int amount, 
			int potSize, int players, boolean allowedBet);
	
	
	/**
	 * Show the cards that make up the best hand the player can get, from the cards
	 * on the table and on their hand.
	 * @param table - The cards currently on the table
	 * @return - The current best power rating of the player
	 */
	public PowerRating showCards(Card[] table);
	
	/**
	 * Give the player a new hand to play in the coming round
	 * @param newHand - The new hand to play
	 */
	public void newHand(PokerHand newHand);
	
	/**
	 * Get the current hand of the player
	 * @return - The current hand
	 */
	public PokerHand getHand();
	
	@Override
	public boolean equals(Object o);
	
	@Override
	public int hashCode();
	
	public void getBlind(int sum);
	
	public void reset();
}
