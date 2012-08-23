package no.ntnu.ai.hands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.deck.CardUtils;

public class PowerRating implements Comparable<PowerRating> {

	private final HandRank rank;
	//Cards not used in the actual hand, but which can be used to determine
	//the winner in case of a tie
	private final Card[] kickers;
	//The cards used to calculate the selected rank
	private final Card[] rankCards;
	
	private final int[] groupedByValues;
	private final int[] groupedBySuits;

	//The sum of kickers.length + rankCards.length must not be above 5!

	/**
	 * This creates a new power rating from an array of cards, if more than
	 * five cards are given, this will calculate the best hand in the 
	 * cards and use the remaining cards as kickers if less than five cards where
	 * used.
	 * 
	 * This Class will create a clone of the cards so the user should not have to
	 * worry about restructuring of the cards given to this method.
	 * @param cards
	 */
	public PowerRating(Card[] cards){
		
		Card[] cardsCopy = cards.clone();
		
		groupedByValues = CardUtils.groupByValues(cardsCopy);
		groupedBySuits = CardUtils.groupBySuits(cardsCopy);
		
		//Sort cards descending
		Arrays.sort(cardsCopy, java.util.Collections.reverseOrder());

		this.rank = this.getRank(cardsCopy);
		this.rankCards = this.getUsedCards(this.rank, cardsCopy);
		this.kickers = this.getKickers(this.rankCards, cardsCopy);
	}

	/**
	 * Get the kickers from the remaining cards not used in the rank
	 * @param cardsInRank - The cards used in the hand rank, must be sorted descending
	 * @param allCards - All the cards given to this class, must be sorted descending
	 * @return - A list of cards which can be used as kickers sorted in descending order
	 */
	private Card[] getKickers(Card[] cardsInRank, Card[] allCards) {
		Stack<Card> kickers = new Stack<Card>();
		for(int i = 0; i < allCards.length; i++){
			kickers.push(allCards[i]);
		}
		//The stack now contains the weakest card at the top of the stack
		if(!kickers.isEmpty()){
			for(int i = 0; i < cardsInRank.length; i++){
				kickers.remove(cardsInRank[i]);
			}

			while(kickers.size() + cardsInRank.length > 5 && !kickers.isEmpty()){
				//Pop the weakest cards until we have enough cards
				kickers.pop();
			}
		}

		return kickers.toArray(new Card[kickers.size()]);
	}

	@Override
	public String toString() {
		return "PowerRating [rank=" + rank + ", kickers="
				+ Arrays.toString(kickers) + ", rankCards="
				+ Arrays.toString(rankCards) + "]";
	}

	/**
	 * Extract the cards which can be used in the given rank
	 * @param rank - The rank to match against
	 * @param cards - All the cards to extract from
	 * @return - A list of cards sorted descending
	 */
	private Card[] getUsedCards(HandRank rank, Card[] cards) {
		ArrayList<Card> res = null;
		switch (rank) {
		case STRAIGHT_FLUSH:
			res = removeNotInSuits(cards);
			break;
		case FOUR_OF_A_KIND:
			res = removeNotInNumber(cards, 4);
			break;
		case FULL_HOUSE:
			res = removeNotInFull(cards);
			break;
		case FLUSH:
			res = removeNotInSuits(cards);
			break;
		case STRAIGHT:
			res = removeNotInStraight(cards);
			break;
		case THREE_OF_A_KIND:
			res = removeNotInNumber(cards, 3);
			break;
		case TWO_PAIR:
			res = removeNotInTwoPair(cards);
			break;
		case ONE_PAIR:
			res = removeNotInNumber(cards, 2);
			break;
		case HIGH_CARD:
			res = new ArrayList<Card>();
			res.add(cards[0]);
			break;
		}
		java.util.Collections.sort(res, java.util.Collections.reverseOrder());
		return res.toArray(new Card[res.size()]);
	}
	
	private ArrayList<Card> removeNotInNumber(Card[] cards, int val){
		ArrayList<Card> res = new ArrayList<Card>();
		int pairValue = 0;
		for(int i = 0; i < groupedByValues.length; i++){
			if(groupedByValues[i] == val){
				pairValue = i + 2;
				break;
			}
		}
		for(Card c : cards){
			if(c.getValue() == pairValue){
				res.add(c);
			}
		}
		return res;
	}

	private ArrayList<Card> removeNotInFull(Card[] cards) {
		ArrayList<Card> res = new ArrayList<Card>();
		int pairValue1 = -1;
		int pairValue2 = -1;
		for(int i = 0; i < groupedByValues.length; i++){
			if(groupedByValues[i] == 2){
				pairValue1 = i + 2;
			}else if(groupedByValues[i] == 3){
				pairValue2 = i + 2;
			}
		}
		for(Card c : cards){
			if(c.getValue() == pairValue1 || c.getValue() == pairValue2){
				res.add(c);
			}
		}
		return res;
	}

	private ArrayList<Card> removeNotInStraight(Card[] cards) {
		ArrayList<Card> res = new ArrayList<Card>();
		int max = 0;
		int min = 0;
		
		for(int i = groupedByValues.length - 1; i >= 0; i--){
			if(groupedByValues[i] != 0){
				if(this.checkNextFour(i, groupedByValues)){
					max = i + 2;
					min = i - 5 + 2;
					break;
				}
			}
		}
		for(Card c : cards){
			if(c.getValue() >= min && c.getValue() <= max){
				boolean found = false;
				for(Card c2 : res){
					if(c2.getValue() == c.getValue()){
						found = true;
					}
				}
				if(!found){
					res.add(c);
				}
			}
		}
		
		return res;
	}

	private ArrayList<Card> removeNotInTwoPair(Card[] cards) {
		ArrayList<Card> res = new ArrayList<Card>();
		int pairValue1 = -1;
		int pairValue2 = -1;
		for(int i = groupedByValues.length - 1; i >= 0; i--){
			if(groupedByValues[i] == 2){
				if(pairValue1 == -1){
					pairValue1 = i + 2;
				}else{
					pairValue2 = i + 2;
					break;
				}
			}
		}
		for(Card c : cards){
			if(c.getValue() == pairValue1 || c.getValue() == pairValue2){
				res.add(c);
			}
		}
		return res;
	}
	
	private ArrayList<Card> removeNotInSuits(Card[] cards){
		ArrayList<Card> res = new ArrayList<Card>();
		for(Card c : cards){
			if(groupedBySuits[c.getSuit().ordinal()] == 5){
				res.add(c);
			}
		}
		return res;
	}

	@Override
	public int compareTo(PowerRating arg0) {
		int compRank = this.rank.compareTo(arg0.getRank());
		if(compRank != 0){
			//One hand has a better rank
			return compRank;
		}else{
			//Equal rank, must use high cards and kickers
			Card[] otherRankCards = arg0.getRankCards();
			for(int i = 0; i < this.rankCards.length; i++){
				//Since cards used in the rank are sorted descending
				//we can compare card by card
				if(this.rankCards[i].getValue() > otherRankCards[i].getValue()){
					return 1;
				}else if(this.rankCards[i].getValue() < otherRankCards[i].getValue()){
					return -1;
				}
			}
			//If we get here this means that both hands had the same card values in their hand
			//Compare kickers
			Card[] otherKickers = arg0.getKickers();
			for(int i = 0; i < this.kickers.length && i < otherKickers.length; i++){
				//The list of kickers doesn't have to be the same length!
				if(this.kickers[i].getValue() > otherKickers[i].getValue()){
					return 1;
				}else if(this.kickers[i].getValue() < otherKickers[i].getValue()){
					return -1;
				}
			}
			if(otherKickers.length == this.kickers.length){
				//Both power ratings have the same kickers, both hands are equal
				return 0;
			}else{
				//The one with the most kickers is now the best one
				if(this.kickers.length > otherKickers.length){
					return 1;
				}else{
					return -1;
				}
			}
		}
	}

	/**
	 * Calculate the rank of a list of cards
	 * @param cards - The cards to calculate the rank from
	 * @return - A HandRank with the rank of the cards
	 */
	private HandRank getRank(Card[] cards){
		boolean flush = isFlush();
		boolean straight = isStraight(cards);
		
		if(flush){
			ArrayList<Card> flushList = this.removeNotInSuits(cards);
			if(isStraight(flushList.toArray(new Card[flushList.size()]))){
				return HandRank.STRAIGHT_FLUSH;
			}else{
				return HandRank.FLUSH;				
			}
		}else if(straight){
			return HandRank.STRAIGHT;
		}else{
			if(isFourOfAKind()){
				return HandRank.FOUR_OF_A_KIND;
			}else if(isFullHouse()){
				return HandRank.FULL_HOUSE;
			}else if(isThreeOfAKind()){
				return HandRank.THREE_OF_A_KIND;
			}else if (isTwoPair()){
				return HandRank.TWO_PAIR;
			}else if (isPair()){
				return HandRank.ONE_PAIR;
			}else{
				return HandRank.HIGH_CARD;
			}
		}
	}
	

	private boolean isFlush(){
		for(Integer i : groupedBySuits){
			if(i > 4){
				return true;
			}
		}
		return false;
	}
	
	private boolean isStraight(Card[] cards){
		int[] values = CardUtils.groupByValues(cards);
		for(int i = values.length - 1; i >= 0; i--){
			if(values[i] != 0){
				return checkNextFour(i, values);
			}
		}
		return false;
	}
	
	private boolean checkNextFour(int index, int[] values){
		for(int i = 1; i < 5; i++){
			if(values[index-i] == 0){
				return false;
			}
		}
		return true;
	}
	
	private boolean isFourOfAKind(){
		for(Integer i : groupedByValues){
			if(i == 4){
				return true;
			}
		}
		return false;
	}
	
	private boolean isFullHouse(){
		return isThreeOfAKind() && isPair();
	}

	private boolean isThreeOfAKind() {
		for(Integer i : groupedByValues){
			if(i == 3){
				return true;
			}
		}
		return false;
	}
	
	private boolean isTwoPair() {
		boolean found = false;
		for(Integer i : groupedByValues){
			if(i == 2){
				if(!found){
					found = true;
				}else{
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isPair(){
		for(Integer i : groupedByValues){
			if(i == 2){
				return true;
			}
		}
		return false;
	}

	public HandRank getRank() {
		return rank;
	}

	public Card[] getKickers() {
		return kickers;
	}

	public Card[] getRankCards() {
		return rankCards;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(kickers);
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + Arrays.hashCode(rankCards);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerRating other = (PowerRating) obj;
		if (!Arrays.equals(kickers, other.kickers))
			return false;
		if (rank == null) {
			if (other.rank != null)
				return false;
		} else if (!rank.equals(other.rank))
			return false;
		if (!Arrays.equals(rankCards, other.rankCards))
			return false;
		return true;
	}
}
