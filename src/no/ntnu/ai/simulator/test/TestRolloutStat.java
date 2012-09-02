package no.ntnu.ai.simulator.test;

import static org.junit.Assert.*;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.deck.Suit;
import no.ntnu.ai.player.PokerHand;
import no.ntnu.ai.simulator.RolloutStats;

import org.junit.Test;

public class TestRolloutStat {

	@Test
	public void testGetStat() {
		PokerHand h1 = new PokerHand(new Card(13, Suit.CLUBS), new Card(13, Suit.HEARTS));
		PokerHand h2 = new PokerHand(new Card(2, Suit.CLUBS), new Card(12, Suit.HEARTS));
		PokerHand h3 = new PokerHand(new Card(10, Suit.CLUBS), new Card(10, Suit.HEARTS));
		PokerHand h4 = new PokerHand(new Card(14, Suit.CLUBS), new Card(13, Suit.CLUBS));
		PokerHand h5 = new PokerHand(new Card(2, Suit.CLUBS), new Card(3, Suit.CLUBS));
		
		RolloutStats stats = RolloutStats.getInstance("100k_v4.txt");
		
		assertEquals(0.415375, stats.getStat(7, h1), 0);
		assertEquals(0.54378, stats.getStat(5, h1), 0);
		assertEquals(0.84906, stats.getStat(2, h1), 0);
		assertEquals(0.29349, stats.getStat(10, h1), 0);
		
		assertEquals(0.09941, stats.getStat(7, h2), 0);
		assertEquals(0.147775, stats.getStat(5, h2), 0);
		assertEquals(0.4676, stats.getStat(2, h2), 0);
		assertEquals(0.063615, stats.getStat(10, h2), 0);
		
		assertEquals(0.27627, stats.getStat(7, h3), 0);
		assertEquals(0.39295, stats.getStat(5, h3), 0);
		assertEquals(0.76952, stats.getStat(2, h3), 0);
		assertEquals(0.189055, stats.getStat(10, h3), 0);
		
		assertEquals(0.293965, stats.getStat(7, h4), 0);
		assertEquals(0.37131, stats.getStat(5, h4), 0);
		assertEquals(0.684205, stats.getStat(2, h4), 0);
		assertEquals(0.220935, stats.getStat(10, h4), 0);
		
		assertEquals(0.097525, stats.getStat(7, h5), 0);
		assertEquals(0.12731, stats.getStat(5, h5), 0);
		assertEquals(0.32816, stats.getStat(2, h5), 0);
		assertEquals(0.07745, stats.getStat(10, h5), 0);
		
	}

}
