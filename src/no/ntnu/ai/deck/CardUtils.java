package no.ntnu.ai.deck;


/**
 * Utility class for the card class
 */
public class CardUtils {
	/**
	 * Create an array of arrays of cards where each sub-array is grouped by
	 * values, meaning all cards of the same value is in the same sub-array.
	 * @param cards - The cards to group
	 * @return - An array giving the amount of cards with each value, offset by -2.
	 * 			i.e. res[0] gives the amount of 2's in the hand.
	 */
	public static int[] groupByValues(Card[] cards){
		int[] res = new int[13];
		for(Card c : cards){
			res[c.getValue() - 2]++;
		}
		return res;
	}

	/**
	 * Create an array of arrays of cards where each sub-array is grouped by
	 * suits, meaning all cards of the same suit is in the same sub-array.
	 * @param cards - The cards to group
	 * @return - An array giving the amount of cards of each suit, following the numbering in the suit enum declaration.
	 */
	public static int[] groupBySuits(Card[] cards){
		int[] res = new int[4];
		for(Card c : cards){
			res[c.getSuit().ordinal()]++;
		}
		return res;
	}
}
