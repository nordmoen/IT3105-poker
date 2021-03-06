package no.ntnu.ai.deck;

/**
 * A class representing a playing card
 */
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
		return this.suit.compareTo(other.getSuit());
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
	 * Get the name of the suit
	 * @return - The full name of the suit
	 */
	private String getSuitName(){
		String res = "";
		switch (this.suit) {
		case HEARTS:
			res = "Hearts"; 
			break;
		case DIAMONDS:
			res = "Diamonds";
			break;
		case CLUBS:
			res = "Clubs";
			break;
		case SPADES:
			res = "Spades";
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
