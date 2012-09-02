package no.ntnu.ai.deck;

import java.util.ArrayList;
import java.util.Random;


public class Deck{

	private ArrayList<Card> deck = new ArrayList<Card>(52);
	private final Random randomGen = new Random();
	private final int stdShuffles;

	/**
	 * Create a new Deck
	 * @param seed - The seed for the random generator
	 * @param shuffles - The amount of shuffles in a standard shuffle
	 */
	private Deck(int seed, int shuffles){
		this.randomGen.setSeed(seed);
		this.stdShuffles = shuffles;
	}
	
	/**
	 * Create a new default deck with the random generator and the amount of
	 * shuffles in a standard shuffle set.
	 */
	private Deck(){
		this(42, 7);
	}
	
	/**
	 * Get a Deck instance
	 * @param seed - The seed to use for the deck random generator. Used when shuffling
	 * @param shuffles - The number of shuffles in a standard shuffle
	 * @return - A new Deck instance
	 */
	public static Deck getInstance(int seed, int shuffles){
		Deck result = new Deck(seed, shuffles);
		result.reset();
		return result;
	}
	
	/**
	 * Get a default Deck
	 * @return - A default deck. Created with a seed of 42 and a std shuffle of 7.
	 */
	public static Deck getInstance(){
		Deck result = new Deck();
		result.reset();
		return result;
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
		return this.deck.remove(this.deck.size() - 1);
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

	@SuppressWarnings("unchecked")
	@Override
	public Object clone(){
		Deck newDeck = new Deck(this.randomGen.nextInt(), stdShuffles);
		newDeck.setStack((ArrayList<Card>) this.deck.clone());
		return newDeck;
	}

	private void setStack(ArrayList<Card> s){
		this.deck = s;
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

	public void shuffle(int nr){
		for(int i = 0; i < nr; i++){
			java.util.Collections.shuffle(this.deck, this.randomGen);
		}
	}
	
	/**
	 * Shuffle the deck 7 times to ensure randomness
	 */
	public void stdShuffle(){
		this.shuffle(this.stdShuffles);
	}

	@Override
	public String toString() {
		return "Deck (" + deck + ")";
	}

	public int size() {
		return this.deck.size();
	}
	
	public boolean remove(Card remCard){
		return deck.remove(remCard);
	}
	
	public boolean contains(Card aCard){
		return deck.contains(aCard);
	}
	
	public Card get(int index){
		return this.deck.get(index);
	}

}
