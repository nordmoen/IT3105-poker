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
		Card[] h1 = {new Card(10, Suit.DIAMONDS), new Card(11, Suit.DIAMONDS), new Card(12, Suit.DIAMONDS),
				new Card(13, Suit.DIAMONDS), new Card(14, Suit.DIAMONDS)};
		Card[] h2 = {new Card(13, Suit.CLUBS), new Card(10, Suit.DIAMONDS), new Card(10, Suit.HEARTS),
				new Card(10, Suit.SPADES), new Card(5, Suit.SPADES)};
		Card[] h3 = {new Card(9, Suit.CLUBS), new Card(9, Suit.DIAMONDS), new Card(9, Suit.HEARTS),
				new Card(5, Suit.CLUBS), new Card(5, Suit.SPADES)};
		Card[] h4 = {new Card(2, Suit.SPADES), new Card(5, Suit.SPADES), new Card(7, Suit.SPADES),
				new Card(11, Suit.SPADES), new Card(13, Suit.SPADES)};
		Card[] h5 = {new Card(5, Suit.CLUBS), new Card(8, Suit.DIAMONDS), new Card(6, Suit.SPADES),
				new Card(7, Suit.SPADES), new Card(9, Suit.CLUBS)};
		Card[] h6 = {new Card(6, Suit.CLUBS), new Card(4, Suit.DIAMONDS), new Card(6, Suit.HEARTS),
				new Card(8, Suit.SPADES), new Card(6, Suit.DIAMONDS)};
		Card[] h7 = {new Card(8, Suit.SPADES), new Card(14, Suit.CLUBS), new Card(6, Suit.DIAMONDS),
				new Card(14, Suit.CLUBS), new Card(8, Suit.HEARTS)};
		Card[] h8 = {new Card(13, Suit.DIAMONDS), new Card(2, Suit.CLUBS), new Card(7, Suit.SPADES),
				new Card(13, Suit.HEARTS), new Card(9, Suit.HEARTS)};
		Card[] h9 = {new Card(3, Suit.SPADES), new Card(6, Suit.HEARTS), new Card(14, Suit.CLUBS),
				new Card(9, Suit.DIAMONDS), new Card(12, Suit.CLUBS)};
		
	
		PowerRating hp1 = new PowerRating(h1);
		PowerRating hp2 = new PowerRating(h2);
		PowerRating hp3 = new PowerRating(h3);
		PowerRating hp4 = new PowerRating(h4);
		PowerRating hp5 = new PowerRating(h5);
		PowerRating hp6 = new PowerRating(h6);
		PowerRating hp7 = new PowerRating(h7);
		PowerRating hp8 = new PowerRating(h8);
		PowerRating hp9 = new PowerRating(h9);
		
		assertEquals(9, hp1.getRank());
		assertEquals(8, hp2.getRank());
		assertEquals(7, hp3.getRank());
		assertEquals(6, hp4.getRank());
		assertEquals(5, hp5.getRank());
		assertEquals(4, hp6.getRank());
		assertEquals(3, hp7.getRank());
		assertEquals(2, hp8.getRank());
		assertEquals(1, hp9.getRank());
	}

	@Test
	public void testGetKickers() {
		Card[] kh1 = {new Card(6, Suit.SPADES), new Card(8, Suit.DIAMONDS), new Card(10, Suit.HEARTS),
				new Card(6, Suit.CLUBS), new Card(2,Suit.CLUBS)};
		Card[] k1 = {new Card(10, Suit.HEARTS), new Card(8, Suit.DIAMONDS), new Card(2, Suit.CLUBS)};
		
		PowerRating kp1 = new PowerRating(kh1);
		
		assertArrayEquals(k1, kp1.getKickers());
	}

	@Test
	public void testGetRankCards() {
		Card[] rh1 = {new Card(10, Suit.SPADES), new Card(11, Suit.HEARTS), new Card(8, Suit.HEARTS), 
				new Card(5, Suit.CLUBS), new Card(10, Suit.CLUBS), new Card(11, Suit.SPADES), new Card(5, Suit.HEARTS)};
		Card[] r1 = {new Card(11, Suit.HEARTS), new Card(11, Suit.SPADES), new Card(10, Suit.CLUBS),
				new Card(10, Suit.SPADES)};
		
		PowerRating rp1 = new PowerRating(rh1);
		
		assertArrayEquals(r1, rp1.getRankCards());
	}

}