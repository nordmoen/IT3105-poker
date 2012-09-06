package no.ntnu.ai.player;

public class PokerAction {
	private final Action act;
	private final int amount;

	public PokerAction(Action act, int amount){
		this.act = act;
		this.amount = amount;
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
