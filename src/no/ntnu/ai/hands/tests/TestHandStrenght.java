package no.ntnu.ai.hands.tests;

import static org.junit.Assert.*;
import no.ntnu.ai.deck.Card;
import no.ntnu.ai.deck.Suit;
import no.ntnu.ai.hands.HandStrength;
import no.ntnu.ai.player.PokerHand;

import org.junit.Test;

public class TestHandStrenght {

	@Test
	public void testCalculateHandStrength(){
		Card[] cards = {new Card(4, Suit.HEARTS), new Card(5, Suit.DIAMONDS), 
				new Card(6, Suit.HEARTS)};

		PokerHand p1 = new PokerHand(new Card(2, Suit.SPADES), new Card(3, Suit.SPADES));
		PokerHand p2 = new PokerHand(new Card(6, Suit.DIAMONDS), new Card(10, Suit.CLUBS));
		PokerHand p3 = new PokerHand(new Card(2, Suit.CLUBS), new Card(9, Suit.CLUBS));
		
		//P1 is the best because it has a straight and therefore it should get
		//a higher hand strength
		//P2 is better than P3 because it has a pair of 6s'
		
		for(int i = 1; i < 11; i++){
			assertTrue(HandStrength.calculateHandStrength(p1, cards, i) > 
				HandStrength.calculateHandStrength(p2, cards, i));
			
			assertTrue(HandStrength.calculateHandStrength(p1, cards, i) > 
			HandStrength.calculateHandStrength(p3, cards, i));
			
			assertTrue(HandStrength.calculateHandStrength(p2, cards, i) > 
			HandStrength.calculateHandStrength(p3, cards, i));
		}
		
		Card[] cards2 = {new Card(4, Suit.HEARTS), new Card(5, Suit.DIAMONDS), 
				new Card(6, Suit.HEARTS), new Card(9, Suit.HEARTS)};
		
		//P1 is still the better hand because of the straight, but P3 should
		//now be better than P2 because it has a pair of 9s'
		
		for(int i = 1; i < 11; i++){
			assertTrue(HandStrength.calculateHandStrength(p1, cards2, i) > 
				HandStrength.calculateHandStrength(p2, cards2, i));
			
			assertTrue(HandStrength.calculateHandStrength(p1, cards2, i) > 
			HandStrength.calculateHandStrength(p3, cards2, i));
			
			assertTrue(HandStrength.calculateHandStrength(p2, cards2, i) < 
			HandStrength.calculateHandStrength(p3, cards2, i));
		}
		
		Card[] cards3 = {new Card(4, Suit.HEARTS), new Card(5, Suit.HEARTS), 
				new Card(6, Suit.HEARTS)};
		
		PokerHand p4 = new PokerHand(new Card(7, Suit.HEARTS), new Card(8, Suit.HEARTS));
		
		//Nothing can beat P4 with the given cards and thus hand strength should be 1.0
		
		assertEquals(1.0, HandStrength.calculateHandStrength(p4, cards3, 1), 0);
		
		//Values below are taken from ai-poker-player.pdf
		Card[] cards4 = {new Card(14, Suit.SPADES), new Card(2, Suit.SPADES), 
				new Card(3, Suit.SPADES)};
		
		PokerHand p5 = new PokerHand(new Card(14, Suit.DIAMONDS), new Card(14, Suit.HEARTS));
		PokerHand p6 = new PokerHand(new Card(4, Suit.SPADES), new Card(5, Suit.SPADES));
		PokerHand p7 = new PokerHand(new Card(4, Suit.HEARTS), new Card(5, Suit.HEARTS));
		
		assertEquals(0.944, HandStrength.calculateHandStrength(p5, cards4, 1), 0.001); // Our number is a bit more accurate
		assertEquals(1.0, HandStrength.calculateHandStrength(p6, cards4, 1), 0);
		assertEquals(0.955, HandStrength.calculateHandStrength(p7, cards4, 1), 0.001);
	}

}
