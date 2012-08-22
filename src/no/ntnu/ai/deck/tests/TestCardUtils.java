package no.ntnu.ai.deck.tests;

import static org.junit.Assert.*;
import no.ntnu.ai.deck.Card;
import no.ntnu.ai.deck.CardUtils;
import no.ntnu.ai.deck.Suit;
import no.ntnu.ai.players.PokerHand;

import org.junit.Test;

public class TestCardUtils {
	
	Card[] c1 = {new Card(3, Suit.SPADES), new Card(5, Suit.HEARTS), 
			new Card(10, Suit.DIAMONDS), new Card(13, Suit.CLUBS)};
	Card[] c2 = {new Card(3, Suit.SPADES), new Card(5, Suit.HEARTS)};
	Card[] c3 = {new Card(3, Suit.SPADES), new Card(14, Suit.HEARTS), new Card(3, Suit.DIAMONDS)};

	@Test
	public void testHandPermutations() {
		PokerHand[] res1 = {new PokerHand(new Card(3, Suit.SPADES), new Card(5, Suit.HEARTS)),
				new PokerHand(new Card(3, Suit.SPADES), new Card(10, Suit.DIAMONDS)),
				new PokerHand(new Card(3, Suit.SPADES), new Card(13, Suit.CLUBS)),
				new PokerHand(new Card(5, Suit.HEARTS), new Card(10, Suit.DIAMONDS)),
				new PokerHand(new Card(5, Suit.HEARTS), new Card(13, Suit.CLUBS)),
				new PokerHand(new Card(10, Suit.DIAMONDS), new Card(13, Suit.CLUBS))};
		PokerHand[] res2 = {new PokerHand(new Card(5, Suit.HEARTS), new Card(3, Suit.SPADES))};
		PokerHand[] res3 = {new PokerHand(new Card(14, Suit.HEARTS), new Card(3, Suit.SPADES)),
				new PokerHand(new Card(3, Suit.SPADES), new Card(3, Suit.DIAMONDS)),
				new PokerHand(new Card(14, Suit.HEARTS), new Card(3, Suit.DIAMONDS))};
		assertArrayEquals(res1, CardUtils.handPermutationsArray(c1));
		assertArrayEquals(res2, CardUtils.handPermutationsArray(c2));
		assertArrayEquals(res3, CardUtils.handPermutationsArray(c3));
	}

}
