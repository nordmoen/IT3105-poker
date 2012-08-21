package no.ntnu.ai.hands;

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
	
	private final Card[][] groupedByValues;
	private final Card[][] groupedBySuits;

	//The sum of kickers.length + rankCards.length must not be above 5!

	/**
	 * This creates a new power rating from an array of cards, if more than
	 * five cards are given, this will calculate the best hand in the 
	 * cards and use the remaining cards as kickers if less than five cards where
	 * used.
	 * @param cards
	 */
	public PowerRating(Card[] cards){
		
		groupedByValues = CardUtils.groupByValues(cards);
		groupedBySuits = CardUtils.groupBySuits(cards);
		
		//Sort cards descending
		Arrays.sort(cards, java.util.Collections.reverseOrder());

		this.rank = this.getRank(cards);
		this.rankCards = this.getUsedCards(this.rank, cards);
		this.kickers = this.getKickers(this.rankCards, cards);
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

		return (Card[]) kickers.toArray();
	}

	/**
	 * Extract the cards which can be used in the given rank
	 * @param rank - The rank to match against
	 * @param cards - All the cards to extract from
	 * @return - A list of cards sorted descending
	 */
	private Card[] getUsedCards(HandRank rank, Card[] cards) {
		// TODO Auto-generated method stub
		return null;
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
		boolean flush = groupedBySuits.length == 1 && groupedBySuits[0].length == 5;
		boolean straight = groupedByValues.length == 5;
		
		if(straight && flush){
			return HandRank.STRAIGHT_FLUSH;
		}else if(flush){
			return HandRank.FLUSH;
		}else if(straight){
			return HandRank.STRAIGHT;
		}else{
			if(isFourOfAKind()){
				return HandRank.FOUR_OF_A_KIND;
			}else if(isFullHouse()){
				return HandRank.FULL_HOUSE;
			}
		}
	}
	
	private boolean isFourOfAKind(){
		return groupedByValues.length == 2 && 
				(groupedByValues[0].length == 4 || 
				groupedByValues[1].length == 4);
	}
	
	private boolean isFullHouse(){
		return groupedByValues.length == 2 &&
				((groupedByValues[0].length == 3 && groupedByValues[1].length == 2) ||
						(groupedByValues[1].length == 3 && groupedByValues[0].length == 2));
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
}
