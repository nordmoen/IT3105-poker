package no.ntnu.ai.players;

import no.ntnu.ai.deck.Card;

public interface PokerPlayer {
	public PokerAction getDecision(Card[] table);
	public Card[] showCards();
	public void newHand(PokerHand newHand);
	public PokerHand getHand();
}
