package no.ntnu.ai.players;

import no.ntnu.ai.deck.Card;


public abstract class AbstractPokerPlayer implements PokerPlayer {
	
	protected PokerHand currentHand;
	protected int chipCount = 0;
	
	abstract public PokerAction makeDecision(Card[] table);
	
	@Override
	public PokerAction getDecision(Card[] table){
		PokerAction pAct = makeDecision(table);
		switch (pAct.getAct()) {
		case BET:
			chipCount -= pAct.getAmount();
			break;
		case CALL:
			if(pAct.getAmount() != 0){
				chipCount -= pAct.getAmount();
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

}
