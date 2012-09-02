package no.ntnu.ai.hands.tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import no.ntnu.ai.deck.Card;
import no.ntnu.ai.deck.Suit;
import no.ntnu.ai.hands.HandRank;
import no.ntnu.ai.hands.PowerRating;
import no.ntnu.ai.player.PokerHand;

import org.junit.Test;

public class TestPowerRating {
	Card[] h1 = {new Card(10, Suit.DIAMONDS), new Card(11, Suit.DIAMONDS), new Card(12, Suit.DIAMONDS),
			new Card(13, Suit.DIAMONDS), new Card(14, Suit.DIAMONDS)};
	Card[] h2 = {new Card(10, Suit.CLUBS), new Card(10, Suit.DIAMONDS), new Card(10, Suit.HEARTS),
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
	Card[] h10 = {new Card(3, Suit.HEARTS), new Card(4, Suit.DIAMONDS), new Card(5, Suit.CLUBS),
			new Card(6, Suit.HEARTS), new Card(7, Suit.DIAMONDS), new Card(3, Suit.SPADES)};
	Card[] h11 = {new Card(3, Suit.HEARTS), new Card(4, Suit.DIAMONDS), new Card(5, Suit.CLUBS),
			new Card(6, Suit.HEARTS), new Card(7, Suit.DIAMONDS), new Card(3, Suit.SPADES),
			new Card(7, Suit.CLUBS)};
	Card[] h12 = {new Card(3, Suit.HEARTS), new Card(4, Suit.DIAMONDS), new Card(5, Suit.CLUBS),
			new Card(6, Suit.HEARTS), new Card(7, Suit.DIAMONDS), new Card(3, Suit.SPADES),
			new Card(3, Suit.CLUBS)};
	Card[] h13 = {new Card(10, Suit.DIAMONDS), new Card(11, Suit.DIAMONDS), new Card(12, Suit.DIAMONDS),
			new Card(13, Suit.DIAMONDS), new Card(9, Suit.DIAMONDS)};
	Card[] h14 = {new Card(10, Suit.DIAMONDS), new Card(11, Suit.CLUBS), new Card(12, Suit.DIAMONDS),
			new Card(13, Suit.DIAMONDS), new Card(14, Suit.DIAMONDS), new Card(8, Suit.DIAMONDS)};
	
	PowerRating hp1 = new PowerRating(h1);
	PowerRating hp2 = new PowerRating(h2);
	PowerRating hp3 = new PowerRating(h3);
	PowerRating hp4 = new PowerRating(h4);
	PowerRating hp5 = new PowerRating(h5);
	PowerRating hp6 = new PowerRating(h6);
	PowerRating hp7 = new PowerRating(h7);
	PowerRating hp8 = new PowerRating(h8);
	PowerRating hp9 = new PowerRating(h9);
	PowerRating hp10 = new PowerRating(h10);
	PowerRating hp11 = new PowerRating(h11);
	PowerRating hp12 = new PowerRating(h12);
	PowerRating hp13 = new PowerRating(h13);
	PowerRating hp14 = new PowerRating(h14);
	
	PowerRating[] ratings = {hp1, hp2, hp3, hp4, hp5, hp6, hp7, hp8, hp9, hp10, hp11,
			hp12, hp13, hp14};
	
	@Test
	public void testCompareTo() {
		assertEquals(1, hp1.compareTo(hp2));
		assertEquals(1, hp1.compareTo(hp13));
		assertEquals(-1, hp13.compareTo(hp1));
	}

	@Test
	public void testGetRank() {
		assertEquals(HandRank.STRAIGHT_FLUSH, hp1.getRank());
		assertEquals(HandRank.FOUR_OF_A_KIND, hp2.getRank());
		assertEquals(HandRank.FULL_HOUSE, hp3.getRank());
		assertEquals(HandRank.FLUSH, hp4.getRank());
		assertEquals(HandRank.STRAIGHT, hp5.getRank());
		assertEquals(HandRank.THREE_OF_A_KIND, hp6.getRank());
		assertEquals(HandRank.TWO_PAIR, hp7.getRank());
		assertEquals(HandRank.ONE_PAIR, hp8.getRank());
		assertEquals(HandRank.HIGH_CARD, hp9.getRank());
		assertEquals(HandRank.STRAIGHT, hp10.getRank());
		assertEquals(HandRank.STRAIGHT, hp11.getRank());
		assertEquals(HandRank.STRAIGHT, hp12.getRank());
		assertEquals(HandRank.FLUSH, hp14.getRank());
		
		

		PokerHand p1 = new PokerHand(new Card(2, Suit.SPADES), new Card(3, Suit.SPADES));
		Card[] cards = {new Card(4, Suit.HEARTS), new Card(5, Suit.DIAMONDS), 
				new Card(6, Suit.HEARTS), new Card(9, Suit.HEARTS)};
		
		assertEquals(HandRank.STRAIGHT, new PowerRating(p1, cards).getRank());
		
		PokerHand p2 = new PokerHand(new Card(2, Suit.SPADES), new Card(3, Suit.SPADES));
		Card[] cards2 = {new Card(4, Suit.HEARTS), new Card(5, Suit.DIAMONDS), 
				new Card(14, Suit.HEARTS)};
		
		assertEquals(HandRank.STRAIGHT, new PowerRating(p2, cards2).getRank());
	}
	
	@Test
	public void testNonDestructive(){
		//Test that making a power rank does not change the original array
		//This test will fail if the clone() in the PowerRating constructor is
		//removed
		Card[] h11 = {new Card(10, Suit.DIAMONDS), new Card(11, Suit.DIAMONDS), new Card(12, Suit.DIAMONDS),
				new Card(13, Suit.DIAMONDS), new Card(14, Suit.DIAMONDS)};
		Card[] h22 = {new Card(10, Suit.CLUBS), new Card(10, Suit.DIAMONDS), new Card(10, Suit.HEARTS),
				new Card(10, Suit.SPADES), new Card(5, Suit.SPADES)};
		Card[] h33 = {new Card(9, Suit.CLUBS), new Card(9, Suit.DIAMONDS), new Card(9, Suit.HEARTS),
				new Card(5, Suit.CLUBS), new Card(5, Suit.SPADES)};
		Card[] h44 = {new Card(2, Suit.SPADES), new Card(5, Suit.SPADES), new Card(7, Suit.SPADES),
				new Card(11, Suit.SPADES), new Card(13, Suit.SPADES)};
		Card[] h55 = {new Card(5, Suit.CLUBS), new Card(8, Suit.DIAMONDS), new Card(6, Suit.SPADES),
				new Card(7, Suit.SPADES), new Card(9, Suit.CLUBS)};
		
		assertArrayEquals(h11, h1);
		assertArrayEquals(h22, h2);
		assertArrayEquals(h33, h3);
		assertArrayEquals(h44, h4);
		assertArrayEquals(h55, h5);
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
		Card[] r1 = {new Card(11, Suit.SPADES), new Card(11, Suit.HEARTS), new Card(10, Suit.SPADES),
				new Card(10, Suit.CLUBS)};
		
		PowerRating rp1 = new PowerRating(rh1);
		
		assertArrayEquals(r1, rp1.getRankCards());
		
		assertEquals(5, hp1.getRankCards().length);
		assertEquals(4, hp2.getRankCards().length);
		assertEquals(5, hp3.getRankCards().length);
		assertEquals(5, hp4.getRankCards().length);
		assertEquals(5, hp5.getRankCards().length);
		assertEquals(3, hp6.getRankCards().length);
		assertEquals(4, hp7.getRankCards().length);
		assertEquals(2, hp8.getRankCards().length);
		assertEquals(1, hp9.getRankCards().length);
		assertEquals(5, hp10.getRankCards().length);
		assertEquals(5, hp11.getRankCards().length);
		assertEquals(5, hp12.getRankCards().length);
	}

}
