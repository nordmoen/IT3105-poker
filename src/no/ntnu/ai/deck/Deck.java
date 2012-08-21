package no.ntnu.ai.deck;

import java.util.Random;
import java.util.Stack;


public class Deck{

	private final Stack<Card> deck = new Stack<Card>();
	private final Random randomGen = new Random();

	public Deck(int seed){
		deck.setSize(52);
		this.reset();
		this.randomGen.setSeed(seed);
	}
	
	public Deck(){
		this(42);
	}

	/**
	 * Reset the state of the deck to a completely fresh sorted deck.
	 */
	private void reset(){
		this.deck.clear();
		for(int i = 3; i >= 0; i--){
			for(int j = 14; j > 1; j--){
				this.deck.add(new Card(j, Suit.values()[i]));
			}
		}
	}
	
	public Card dealCard(){
		return this.deck.pop();
	}
	
	public Card[] dealCards(int nr){
		Card[] res = new Card[nr];
		for(int i = 0; i < nr; i++){
			res[i] = this.dealCard();
		}
		return res;
	}

	/**
	 * Sort the deck such that the smallest cards are at the top
	 */
	public void sort(){
		java.util.Collections.sort(this.deck, java.util.Collections.reverseOrder());
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return this.deck.clone();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deck == null) ? 0 : deck.hashCode());
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
		Deck other = (Deck) obj;
		if (deck == null) {
			if (other.deck != null)
				return false;
		} else if (!deck.equals(other.deck))
			return false;
		return true;
	}

	public int size() {
		return this.deck.size();
	}

	public void shuffle(int nr){
		for(int i = 0; i < nr; i++){
			java.util.Collections.shuffle(this.deck, this.randomGen);
		}
	}

	@Override
	public String toString() {
		return "Deck (" + deck + ")";
	}

	/**
	 * Shuffle the deck 7 times to ensure randomness
	 */
	public void stdShuffle(){
		this.shuffle(7);
	}
	
	public boolean remove(Card remCard){
		return deck.remove(remCard);
	}
	
	public boolean contains(Card aCard){
		return deck.contains(aCard);
	}

}
