package no.ntnu.ai.test;

import no.ntnu.ai.deck.tests.TestCard;
import no.ntnu.ai.deck.tests.TestCardUtils;
import no.ntnu.ai.deck.tests.TestDeck;
import no.ntnu.ai.hands.tests.TestPowerRating;
import no.ntnu.ai.simulator.test.TestRolloutStat;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TestPowerRating.class, TestCard.class, TestDeck.class, TestCardUtils.class,
	TestRolloutStat.class})
public class AllTests {

}
