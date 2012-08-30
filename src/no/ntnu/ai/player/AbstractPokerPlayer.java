package no.ntnu.ai.player;

import no.ntnu.ai.deck.Card;


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

	/**
	 * Helper method to test equality between players
	 * @param player - The player to compare to
	 * @return
	 */
	abstract public boolean playerEquals(AbstractPokerPlayer player);

	@Override
	public boolean equals(Object o){
		if(this == o){
			return true;
		}else if(this.getClass() != o.getClass()){
			return false;
		}else{
			return this.playerEquals((AbstractPokerPlayer) o);
		}
	}

	public void getBlind(int sum){
		this.chipCount -= sum;
	}

}
