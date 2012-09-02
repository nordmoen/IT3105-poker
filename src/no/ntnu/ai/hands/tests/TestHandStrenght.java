package no.ntnu.ai.hands.tests;

import static org.junit.Assert.assertTrue;
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
		
		for(int i = 1; i < 11; i++){
			assertTrue(HandStrength.calculateHandStrength(p1, cards, i) > 
				HandStrength.calculateHandStrength(p2, cards, i));
			
			assertTrue(HandStrength.calculateHandStrength(p1, cards, i) > 
			HandStrength.calculateHandStrength(p3, cards, i));
			
			assertTrue(HandStrength.calculateHandStrength(p2, cards, i) > 
			HandStrength.calculateHandStrength(p3, cards, i));
		}
	}

}
