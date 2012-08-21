package no.ntnu.ai.deck;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Utility class for the card class
 */
public class CardUtils {
	/**
	 * Create an array of arrays of cards where each sub-array is grouped by
	 * values, meaning all cards of the same value is in the same sub-array.
	 * @param cards - The cards to group
	 * @return - An array of arrays
	 */
	public static Card[][] groupByValues(Card[] cards){
		HashMap<Integer, ArrayList<Card>> map = new HashMap<Integer, ArrayList<Card>>();
		for(Card c : cards){
			if(!map.containsKey(c.getValue())){
				map.put(c.getValue(), new ArrayList<Card>());
			}
			map.get(c.getValue()).add(c);
		}
		Card[][] res = new Card[map.keySet().size()][];
		int i = 0;
		for(Integer j : map.keySet()){
			res[i] = (Card[]) map.get(j).toArray();
			i++;
		}
		return res;
	}

	/**
	 * Create an array of arrays of cards where each sub-array is grouped by
	 * suits, meaning all cards of the same suit is in the same sub-array.
	 * @param cards - The cards to group
	 * @return - An array of arrays
	 */
	public static Card[][] groupBySuits(Card[] cards){
		HashMap<Suit, ArrayList<Card>> map = new HashMap<Suit, ArrayList<Card>>();
		for(Card c : cards){
			if(!map.containsKey(c.getSuit())){
				map.put(c.getSuit(), new ArrayList<Card>());
			}
			map.get(c.getSuit()).add(c);
		}
		Card[][] res = new Card[map.keySet().size()][];
		int i = 0;
		for(Suit j : map.keySet()){
			res[i] = (Card[]) map.get(j).toArray();
			i++;
		}
		return res;
	}
}
