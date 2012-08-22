package no.ntnu.ai.deck.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import no.ntnu.ai.deck.Card;
import no.ntnu.ai.deck.Suit;

import org.junit.Test;

public class TestCard {
	
	final Card c1 = new Card(10, Suit.HEARTS);
	final Card c2 = new Card(11, Suit.CLUBS);
	final Card c3 = new Card(2, Suit.DIAMONDS);
	final Card c4 = new Card(13, Suit.HEARTS);
	final Card c5 = new Card(10, Suit.SPADES);

	@Test
	public void testCompareTo() {
		assertTrue("C1 equals C1", c1.equals(c1));
		assertTrue("C1 less than C2", c1.compareTo(c2) == -1);
		assertTrue("C2 greater than C1", c2.compareTo(c1) == 1);
		
		assertTrue("C3 less than C4", c3.compareTo(c4) == -1);
		assertTrue("C5 value equals C1", c5.getValue() == c1.getValue());
		assertEquals("C1 greater than C5", -3, c1.compareTo(c5));
		assertEquals("C5 less than C1", 3, c5.compareTo(c1));
	}

	@Test
	public void testGetSuit() {
		assertEquals("C1 has suit Hearts", Suit.HEARTS, c1.getSuit());
		assertEquals("C2 has suit Clubs", Suit.CLUBS, c2.getSuit());
		assertEquals("C3 has suit Diamonds", Suit.DIAMONDS, c3.getSuit());
		assertEquals("C4 has suit Hearts", Suit.HEARTS, c4.getSuit());
		assertEquals("C5 has suit Spades", Suit.SPADES, c5.getSuit());
	}

	@Test
	public void testGetValue() {
		assertEquals("C1 has value 10", 10, c1.getValue());
		assertEquals("C2 has value 11", 11, c2.getValue());
		assertEquals("C3 has value 2", 2, c3.getValue());
		assertEquals("C4 has value 13", 13, c4.getValue());
		assertEquals("C5 has value 10", 10, c5.getValue());
	}
	
	@Test
	public void testToString(){
		assertEquals("C1 toString", "10 of Hearts", c1.toString());
		assertEquals("C2 toString", "Jack of Clubs", c2.toString());
		assertEquals("C3 toString", "2 of Diamonds", c3.toString());
		assertEquals("C4 toString", "King of Hearts", c4.toString());
		assertEquals("C5 toString", "10 of Spades", c5.toString());
	}
	
	@Test
	public void testCard(){
		assertNotNull(new Card(10, Suit.SPADES));
		assertNotNull(new Card(2, Suit.DIAMONDS));
		assertNotNull(new Card(14, Suit.HEARTS));
		assertNotNull(new Card(7, Suit.CLUBS));
		
		for(int i = 0; i < 100; i++){
			if(i >= 2 && i <= 14){
				assertTrue("Testing value '" + i + "'", testCreateCard(i));
			}else{
				assertFalse("Testing value '" + i + "'", testCreateCard(i));
			}
		}
	}
	
	private boolean testCreateCard(int value){
		try{
			new Card(value, Suit.DIAMONDS);
			return true;
		}catch(IllegalArgumentException e){
			return false;
		}
	}

}
