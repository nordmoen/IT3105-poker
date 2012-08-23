package no.ntnu.ai.deck;

import java.util.ArrayList;

import no.ntnu.ai.player.PokerHand;

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
	
	/**
	 * Generate all pairs of poker hands from a list of cards. This method
	 * will not generate duplicates with the same cards. This means that given
	 * 3 of spades, 2 of hearts and 13 of clubs this method will generate:
	 * 3 of spades + 3 of spades
	 * 3 of spades + 2 of hearts
	 * 3 of spades + 13 of clubs
	 * 2 of hearts + 13 of clubs
	 * @param cards - The list of cards to generate permutations from
	 * @return - An ArrayList<PokerHand> of permutations
	 */
	public static ArrayList<PokerHand> handPermutations(Card[] cards){
		ArrayList<PokerHand> res = new ArrayList<PokerHand>(cards.length*2);
		for(int i = 0; i < cards.length - 1; i++){
			res.add(new PokerHand(cards[i], cards[i]));
			for(int j = i + 1; j < cards.length; j++){
				if(cards[i].getValue() != cards[j].getValue()){
					res.add(new PokerHand(cards[i], cards[j]));
				}
			}
		}
		return res;
	}
	
	/**
	 * Utility method to return Array instead of ArrayList.
	 * @param cards
	 * @return
	 */
	public static PokerHand[] handPermutationsArray(Card[] cards){
		ArrayList<PokerHand> res = handPermutations(cards);
		return res.toArray(new PokerHand[res.size()]);
	}
}
