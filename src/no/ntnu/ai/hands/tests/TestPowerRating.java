package no.ntnu.ai.hands.tests;

import static org.junit.Assert.*;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.deck.Suit;
import no.ntnu.ai.hands.PowerRating;

import org.junit.Test;

public class TestPowerRating {
	Card[] c1 = {new Card(10, Suit.DIAMONDS), new Card(10, Suit.CLUBS)};
	Card[] c2 = {new Card(10, Suit.DIAMONDS), new Card(10, Suit.CLUBS), new Card(13, Suit.CLUBS), 
			new Card(2, Suit.SPADES), new Card(3, Suit.HEARTS)};
	PowerRating p1 = new PowerRating(c1);
	PowerRating p2 = new PowerRating(c2);

	@Test
	public void testCompareTo() {
		assertEquals("P1 is equal to P1",0, p1.compareTo(p1));
		assertEquals("P2 is better than P1 because of kickers", 1, p2.compareTo(p1));
		assertEquals("P1 is less than P2", -1, p1.compareTo(p2));
	}

	@Test
	public void testGetRank() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetKickers() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRankCards() {
		fail("Not yet implemented");
	}

}
