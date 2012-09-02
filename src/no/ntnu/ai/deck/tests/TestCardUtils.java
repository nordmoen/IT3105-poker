package no.ntnu.ai.deck.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.deck.CardUtils;
import no.ntnu.ai.deck.Deck;
import no.ntnu.ai.deck.Suit;
import no.ntnu.ai.player.PokerHand;

import org.junit.Test;

public class TestCardUtils {

	@Test
	public void testGeneratePreFlop(){
		List<PokerHand> preFlops = CardUtils.generatePreFlop();

		assertEquals(169, preFlops.size());
	}

	@Test
	public void testPermutDeck(){
		Deck d = Deck.getInstance();

		d.remove(new Card(13, Suit.DIAMONDS));
		d.remove(new Card(14, Suit.HEARTS));

		assertEquals(1225, CardUtils.permuteDeck(d).size());

		d.remove(new Card(2, Suit.SPADES));
		d.remove(new Card(3, Suit.HEARTS));
		d.remove(new Card(11, Suit.HEARTS));

		assertEquals(1081, CardUtils.permuteDeck(d).size());

		d.remove(new Card(14, Suit.SPADES));

		assertEquals(1035, CardUtils.permuteDeck(d).size());

		d.remove(new Card(13, Suit.SPADES));

		assertEquals(990, CardUtils.permuteDeck(d).size());
	}

	@Test
	public void testSplitList(){
		for(int k = 0; k < 1000; k++){
			int val = (int) ((Math.random() * 1024) + 1);
			int val2 = (int) ((Math.random() * 20) + 1);
			int small = (int) Math.floor(val /(double)val2);
			int large = val - (val2-1) * small;

			List<Double> testList = new ArrayList<Double>();
			for(int i = 0; i < val; i++){
				testList.add(Math.random());
			}

			List<List<Double>> resList = CardUtils.splitList(testList, val2);

			assertEquals(small, resList.get(0).size());
			assertEquals(large, resList.get(resList.size() - 1).size());
		}
	}

}
