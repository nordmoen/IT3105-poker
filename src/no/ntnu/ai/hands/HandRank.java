package no.ntnu.ai.hands;

public enum HandRank {
	/*
	 * The following ranks are from best to worst retrieved from 
	 * https://en.wikipedia.org/wiki/List_of_poker_hands
	 */
	STRAIGHT_FLUSH,
	FOUR_OF_A_KIND,
	FULL_HOUSE,
	FLUSH,
	STRAIGHT,
	THREE_OF_A_KIND,
	TWO_PAIR,
	ONE_PAIR,
	HIGH_CARD;
}
