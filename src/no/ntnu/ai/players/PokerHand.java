package no.ntnu.ai.players;

import no.ntnu.ai.deck.Card;

public class PokerHand {
	private final Card c1;
	private final Card c2;
	
	public PokerHand(Card a, Card b){
		c1 = a;
		c2 = b;
	}

	public Card getC1() {
		return c1;
	}

	public Card getC2() {
		return c2;
	}
}
