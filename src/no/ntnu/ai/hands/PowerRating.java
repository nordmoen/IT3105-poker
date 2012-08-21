package no.ntnu.ai.hands;

import java.util.ArrayList;
import java.util.Arrays;

import no.ntnu.ai.deck.Card;

public class PowerRating implements Comparable<PowerRating> {

	private final HandRank rank;
	//Cards not used in the actual hand, but which can be used to determine
	//the winner in case of a tie
	private final Card[] kickers;
	//The cards used to calculate the selected rank
	private final Card[] rankCards;

	//The sum of kickers.length + rankCards.length must not be above 5!

	/**
	 * This creates a new power rating from an array of cards, if more than
	 * five cards are given, this will calculate the best hand in the 
	 * cards and use the remaining cards as kickers if less than five cards where
	 * used.
	 * @param cards
	 */
	public PowerRating(Card[] cards){
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
		ArrayList<Card> kickers = new ArrayList<Card>(4); //Max size of kickers is 4
		int i = 0;
		int j = 0;
		while(cardsInRank.length + kickers.size() < 5){
			//Since both cardsInRank and allCards are sorted descending this
			//should work
			if(cardsInRank[j].getValue() == allCards[i].getValue()){
				i++;
				j++;
			}else{
				kickers.add(allCards[i]);
				i++;
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
			for(int i = 0; i < this.kickers.length; i++){
				if(this.kickers[i].getValue() > otherKickers[i].getValue()){
					return 1;
				}else if(this.kickers[i].getValue() < otherKickers[i].getValue()){
					return -1;
				}
			}
			//Both power ratings have the same kickers, both hands are equal
			return 0;
		}
	}

	/**
	 * Calculate the rank of a list of cards
	 * @param cards - The cards to calculate the rank from
	 * @return - A HandRank with the rank of the cards
	 */
	private HandRank getRank(Card[] cards){
		return HandRank.FLUSH;
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
