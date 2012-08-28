package no.ntnu.ai.player;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.hands.PowerRating;
import no.ntnu.ai.table.PokerTable;

public class Phase1Player extends AbstractPokerPlayer {
	
	private PokerTable pTable;
	
	public Phase1Player(PokerTable table){
		this.chipCount = 1000;
		this.pTable = table;
	}

	@Override
	public PokerAction makeDecision(Card[] table) {
		double random = Math.random();
		if(table == null){
			if(random>0.7){
				return new PokerAction(Action.FOLD);
			}else if(random >0.3){
				return new PokerAction(Action.CALL);
			}else{
				return new PokerAction(Action.BET, pTable.getBigBlind());
			}
		}else{
			Card[] cards = new Card[table.length+2];
			cards[0] = this.currentHand.getC1();
			cards[1] = this.currentHand.getC2();
			for(int i=0; i<table.length; i++){
				cards[i+2] = table[i];
			}
			PowerRating myHand = new PowerRating(cards);
			double simpleRating = (myHand.getRank().ordinal() + 1) / 9;
			if(random < simpleRating){
				return new PokerAction(Action.FOLD);
			}else if (simpleRating > 0.3){
				//TODO: need to know how many chips we need to bet and subtract that amount from our chipcount.
				// the players that have payed blinds/called/betted have a different amount of chips needed to bet/call.
				
				return new PokerAction(Action.BET, pTable.getBigBlind());
			}else{
				return new PokerAction(Action.CALL);
			}
		}
		
	}

	@Override
	public boolean playerEquals(AbstractPokerPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PowerRating showCards(Card[] table) {
		Card[] cards = new Card[table.length +2];
		cards[0] = this.currentHand.getC1();
		cards[1] = this.currentHand.getC2();
		for(int i=0; i<table.length; i++){
			cards[i+2] = table[i];
		}
		PowerRating pwr = new PowerRating(cards);
		return pwr;
	}

}
