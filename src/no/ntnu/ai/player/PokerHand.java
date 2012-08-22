package no.ntnu.ai.player;

import no.ntnu.ai.deck.Card;

public class PokerHand implements Comparable<PokerHand>{
	private final Card c1;
	private final Card c2;
	
	public PokerHand(Card a, Card b){
		c1 = a;
		c2 = b;
	}

	public Card getC1() {
		return c1;
	}

	public Card getC2() {
		return c2;
	}

	@Override
	public int compareTo(PokerHand o) {
		if((this.c1.getValue() == o.getC1().getValue() && 
				this.c2.getValue() == o.getC2().getValue()) || 
				(this.c2.getValue() == o.getC1().getValue() && 
				this.c1.getValue() == o.getC2().getValue())){
			return 0;
		}else{
			return (this.c1.getValue() + this.c2.getValue()) - (o.getC1().getValue() + o.getC2().getValue());
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((c1 == null) ? 0 : c1.hashCode());
		result = prime * result + ((c2 == null) ? 0 : c2.hashCode());
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
		PokerHand other = (PokerHand) obj;
		if (c1 == null) {
			if (other.c1 != null)
				return false;
		} else if (!c1.equals(other.c1) && !c1.equals(other.c2))
			return false;
		if (c2 == null) {
			if (other.c2 != null)
				return false;
		} else if (!c2.equals(other.c2) && !c2.equals(other.c1))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PokerHand (" + c1 + ", " + c2 + ")";
	}
}
