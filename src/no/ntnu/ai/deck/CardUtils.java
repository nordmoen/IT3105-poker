package no.ntnu.ai.deck;

import java.util.ArrayList;
import java.util.List;

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
	
	public static List<PokerHand> generatePreFlop(){
		ArrayList<PokerHand> preFlops = new ArrayList<PokerHand>(200);
		for(int i=2; i<15; i++){
			preFlops.add(new PokerHand(new Card(i, Suit.SPADES), new Card(i, Suit.HEARTS)));
			for(int j=i+1; j<15; j++){
				preFlops.add(new PokerHand(new Card(i, Suit.SPADES), new Card(j, Suit.SPADES)));
				preFlops.add(new PokerHand(new Card(i, Suit.SPADES), new Card(j, Suit.HEARTS)));
			}
		}
		return preFlops;
	}
	
	/**
	 * Create all the PokerHand permutations from a given Deck
	 * @param d - The deck to get the permutations from
	 * @return - A list of all the PokreHand permutations
	 */
	public static List<PokerHand> permuteDeck(Deck d){
		List<PokerHand> res = new ArrayList<PokerHand>();
		for(int i = 0; i < d.size(); i++){
			for(int j = i + 1; j < d.size(); j++){
				res.add(new PokerHand(d.get(i), d.get(j)));
			}
		}
		return res;
	}
	
	/**
	 * Split a list into a given number of sublists. Each sublist will be about
	 * the same size with the exception of the last list, which may be smaller then
	 * all the other lists
	 * @param list - The list to split
	 * @param numberToSplit - The number of times to split the list
	 * @return - A list of lists where each sublist is about the same size
	 */
	public static <T> List<List<T>> splitList(List<T> list, int numberToSplit){
		int rounded = (int) Math.floor((double)list.size()/numberToSplit);
		List<List<T>> res = new ArrayList<List<T>>();
		for(int i = 0; i < numberToSplit - 1; i++){
			res.add(list.subList(rounded * i, rounded * (i+1)));
		}
		res.add(list.subList((numberToSplit-1)*rounded, list.size()));
		return res;
	}
}
