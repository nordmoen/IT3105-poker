package no.ntnu.ai.player;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.hands.PowerRating;


public abstract class AbstractPokerPlayer implements PokerPlayer {

	protected PokerHand currentHand;
	private int chipCount = 0;
	
	public AbstractPokerPlayer(int count){
		this.chipCount = count;
	}

	abstract public PokerAction makeDecision(Card[] table, int small, int big, int amount, int chipCount);

	@Override
	public PokerAction getDecision(Card[] table, int bigBlind, int smallBlind, int amount){
		PokerAction pAct = makeDecision(table, bigBlind, smallBlind, amount, this.chipCount);
		switch (pAct.getAct()) {
		case BET:
			if(pAct.getAmount() > amount){
				chipCount -= pAct.getAmount();
			}else if (pAct.getAmount() == amount){
				return new PokerAction(Action.CALL, amount);
			}else{
				throw new IllegalArgumentException("Can not bet with an amount " +
						"lower than the current needed amount. Player tried to bet" +
						" with an amount equal to " + pAct.getAmount() + " " + this);
			}
			break;
		case CALL:
			if(pAct.getAmount() >= 0 && pAct.getAmount() == amount){
				chipCount -= pAct.getAmount();
			}else{
				throw new IllegalArgumentException("Can not call with an amount " +
						"lower than the current needed amount. Player tried to call" +
						" with an amount equal to " + pAct.getAmount() + " " + this);
			}
			break;
		}
		return pAct;
	}

	@Override
	public void newHand(PokerHand newHand) {
		currentHand = newHand;
	}

	@Override
	public PokerHand getHand() {
		return currentHand;
	}

	public int getChipCount(){
		return chipCount;
	}

	public void giveChips(int amount){
		this.chipCount += amount;
	}

	@Override
	public PowerRating showCards(Card[] table){
		return new PowerRating(this.currentHand, table);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + chipCount;
		result = prime * result
				+ ((currentHand == null) ? 0 : currentHand.hashCode());
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
		AbstractPokerPlayer other = (AbstractPokerPlayer) obj;
		if (chipCount != other.chipCount)
			return false;
		if (currentHand == null) {
			if (other.currentHand != null)
				return false;
		} else if (!currentHand.equals(other.currentHand))
			return false;
		return true;
	}

	public void getBlind(int sum){
		this.chipCount -= sum;
	}

}
