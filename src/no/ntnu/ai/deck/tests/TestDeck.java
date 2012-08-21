package no.ntnu.ai.deck.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.deck.Deck;
import no.ntnu.ai.deck.Suit;

import org.junit.Before;
import org.junit.Test;

public class TestDeck {
	
	Deck d1;
	
	@Before
	public void before(){
		d1 = new Deck();
	}

	@Test
	public void testDealCard() {
		assertEquals("The top card of a new deck is 2 of hearts", 
				new Card(2, Suit.HEARTS), d1.dealCard());
		assertEquals("After the one use 3 of hearts should be at the top", 
				new Card(3, Suit.HEARTS), d1.dealCard());
	}

	@Test
	public void testDealCards() {
		ArrayList<Card> firstFive = new ArrayList<Card>(5);
		firstFive.add(new Card(2, Suit.HEARTS));
		firstFive.add(new Card(3, Suit.HEARTS));
		firstFive.add(new Card(4, Suit.HEARTS));
		firstFive.add(new Card(5, Suit.HEARTS));
		firstFive.add(new Card(6, Suit.HEARTS));
		assertArrayEquals("The first five cards", firstFive.toArray(), d1.dealCards(5));
	}

	@Test
	public void testSort() {
		Deck d2 = new Deck();
		d2.stdShuffle();
		d2.sort();
		d1.sort();
		assertEquals("D2 shuffled then sorted is equal to a d1 sorted", d1, d2);
		
		ArrayList<Card> first = new ArrayList<Card>(5);
		first.add(new Card(2, Suit.HEARTS));
		first.add(new Card(2, Suit.CLUBS));
		first.add(new Card(2, Suit.DIAMONDS));
		first.add(new Card(2, Suit.SPADES));
		assertArrayEquals("After D2 sorted top 4 cards smallest", first.toArray(), d2.dealCards(4));
	}

	@Test
	public void testSize() {
		assertEquals("New Deck size", 52, d1.size());
		d1.dealCards(5);
		assertEquals("After dealing five cards", 47, d1.size());
		d1.dealCards(30);
		assertEquals("After another 30 cards", 17, d1.size());
	}

	@Test
	public void testShuffle() {
		Deck d2 = new Deck();
		d2.shuffle(1);
		assertFalse("D2 shuffled is not equal to D1", d2.equals(d1));
		
		Deck d3 = new Deck();
		d3.shuffle(1);
		assertTrue("D3 shuffled is equal to D2 shuffled once", d3.equals(d2));
		
		d2.shuffle(1);
		assertFalse("D2 shuffled twice is not equal to D3", d2.equals(d3));
	}
	
	@Test
	public void testRemove() {
		Card c1 = new Card(5, Suit.CLUBS);
		assertTrue(d1.contains(c1));
		d1.remove(c1);
		assertFalse(d1.contains(c1));
	}

}
