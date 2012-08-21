package no.ntnu.ai.players;

public class PokerAction {
	private final Action act;
	private final int amount;

	public PokerAction(Action act, int amount){
		if(amount > 0 || act != Action.BET){
			this.act = act;
			this.amount = amount;
		}else{
			throw new IllegalArgumentException("Can't bet with amount equal to: " + amount);
		}
	}
	
	public PokerAction(Action act){
		this(act, 0);
	}

	public Action getAct() {
		return act;
	}

	public int getAmount() {
		return amount;
	}

}
