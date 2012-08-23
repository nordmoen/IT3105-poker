package no.ntnu.ai.deck.tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import no.ntnu.ai.deck.CardUtils;
import no.ntnu.ai.player.PokerHand;

import org.junit.Test;

public class TestCardUtils {
	
	@Test
	public void testGeneratePreFlop(){
		List<PokerHand> preFlops = CardUtils.generatePreFlop();
		
		assertEquals(169, preFlops.size());
	}

}
