package no.ntnu.ai.player;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.hands.PowerRating;

public class Phase1Player extends AbstractPokerPlayer {
	
	public Phase1Player(String name, int amount){
		super(name, amount);
	}

	@Override
	public PokerAction makeDecision(Card[] table, int smallBlind, int bigBlind, int amount, int chipCount) {
		double random = Math.random();
		if(table == null){
			if(random>0.7){
				return new PokerAction(Action.FOLD);
			}else if(random >0.3){
				return new PokerAction(Action.CALL, amount);
			}else{
				return new PokerAction(Action.BET, bigBlind + amount);
			}
		}else{
			Card[] cards = new Card[table.length+2];
			cards[0] = this.currentHand.getC1();
			cards[1] = this.currentHand.getC2();
			for(int i=0; i<table.length; i++){
				cards[i+2] = table[i];
			}
			PowerRating myHand = new PowerRating(cards);
			double simpleRating = (double)(myHand.getRank().ordinal() + 1) / 9;
			if(random*random > simpleRating){
				return new PokerAction(Action.FOLD);
			}else if (simpleRating > 0.3){
				//TODO: need to know how many chips we need to bet and subtract that amount from our chipcount.
				// the players that have payed blinds/called/betted have a different amount of chips needed to bet/call.
				
				return new PokerAction(Action.BET, bigBlind + amount);
			}else{
				return new PokerAction(Action.CALL, amount);
			}
		}
		
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
	
	@Override
	public String toString(){
		return "Phase 1, " + this.name + ", chip count: " + super.getChipCount();
	}

}
