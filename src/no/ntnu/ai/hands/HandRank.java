package no.ntnu.ai.hands;

public enum HandRank {
	/*
	 * The following ranks are from worst to best retrieved from 
	 * https://en.wikipedia.org/wiki/List_of_poker_hands
	 */
	HIGH_CARD,
	ONE_PAIR,
	TWO_PAIR,
	THREE_OF_A_KIND,
	STRAIGHT,
	FLUSH,
	FULL_HOUSE,
	FOUR_OF_A_KIND,
	STRAIGHT_FLUSH;
}
