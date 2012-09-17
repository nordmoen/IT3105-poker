package no.ntnu.ai.player;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.hands.PowerRating;


public abstract class AbstractPokerPlayer implements PokerPlayer {

	protected PokerHand currentHand;
	protected final String name;
	private int chipCount = 0;
	private final int originalChipCount;
	protected final double aggressiveness;
	
	/**
	 * 
	 * @param name The name of the player to be created.
	 * @param count The initial chipcount of the player.
	 * @param agg How aggressive the player should be, a double (0, 2). Higher number means more aggressive.
	 */
	public AbstractPokerPlayer(String name, int count, double agg){
		this.chipCount = count;
		this.name = name;
		this.originalChipCount = count;
		if(agg<=0){
			agg = 0.1;
		}else if(agg>=2){
			agg = 1.9;
		}
		this.aggressiveness = 2-agg;
	}

	abstract public PokerAction makeDecision(Card[] table, int small, int big, 
			int amount, int potSize, int chipCount, int numPlayers, boolean allowedBet);

	@Override
	public PokerAction getDecision(Card[] table, int bigBlind, int smallBlind, 
			int amount, int potSize, int players, boolean allowedBet){
		PokerAction pAct = makeDecision(table, smallBlind, bigBlind, amount, potSize, 
				this.chipCount, players, allowedBet);
		switch (pAct.getAct()) {
		case BET:
			if(pAct.getAmount() > amount){
				this.chipCount -= pAct.getAmount();
			}else if (pAct.getAmount() == amount){
				this.chipCount -= amount;
				return new PokerAction(Action.CALL, amount);
			}else{
				throw new IllegalArgumentException("Can not bet with an amount " +
						"lower than the current needed amount. Player tried to bet" +
						" with an amount equal to " + pAct.getAmount() + " of " + amount + " " + this);
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public void getBlind(int sum){
		this.chipCount -= sum;
	}
	
	abstract public String getPhaseName();
	
	@Override
	public String toString(){
		return getName() + ", chip count: " + this.getChipCount() + ", Aggressiveness: " + this.aggressiveness;
	}
	
	public String getName(){
		return getPhaseName() + ", " + this.name;
	}
	
	public void reset(){
		this.chipCount = this.originalChipCount;
		this.currentHand = null;
	}

}
