package no.ntnu.ai.deck;

public class Card implements Comparable<Card> {

	private final Suit suit;
	private final int value;

	/**
	 * Create a new Card. Throws IllegalArgumentException if value is not correct.
	 * @param value - The value of the card, must be between 2 and 14
	 * @param su - The suit of the card
	 */
	public Card(int value, Suit su){
		if(value >= 2 && value <= 14){
			this.value = value;
			this.suit = su;
		}else{
			throw new IllegalArgumentException("Given value is not witin legal range." +
					"Legal range is [2,14], given value was: '" + value + "'");
		}
	}

	@Override
	public int compareTo(Card other) {
		if(other.getValue() > this.value){
			return -1;
		}else if(other.getValue() < this.value){
			return 1;
		}else{
			return this.compareSuits(other);
		}
	}

	/**
	 * Compare the suits of two cards returning -1 if the other card has a
	 * better suite and 1 if this card has a better suit
	 * @param other - The card to compare against.
	 * @return -1 or 1 depending on which card has the higher suit
	 */
	private int compareSuits(Card other){
		int suitCompare = this.suit.compareTo(other.getSuit());
		if (suitCompare == 0){
			return 0;
		}else if (suitCompare > 0){
			return -1;
		}else{
			return 1;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		result = prime * result + value;
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
		Card other = (Card) obj;
		if (suit != other.suit)
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.getValueName() + " of " + this.getSuitName();
	}

	/**
	 * Get the name of the card
	 * @return - The name of the card
	 */
	private String getValueName() {
		String res = "";
		if (this.value <= 10){
			res = this.value + "";
		}else{
			switch (this.value) {
			case 11:
				res = "Jack";
				break;
			case 12:
				res = "Queen";
				break;
			case 13:
				res = "King";
				break;
			case 14:
				res = "Ace";
				break;
			}
		}
		return res;
	}
	
	/**
	 * Get the unicode representation of the suit
	 * @return - The unicode representation of the suit
	 */
	private String getSuitName(){
		String res = "";
		switch (this.suit) {
		case HEARTS:
			res = "\u2665"; 
			break;
		case DIAMONDS:
			res = "\u2666";
			break;
		case CLUBS:
			res = "\u2663";
			break;
		case SPADES:
			res = "\u2660";
			break;
		}
		return res;
	}

	public Suit getSuit() {
		return suit;
	}

	public int getValue() {
		return value;
	}

}
