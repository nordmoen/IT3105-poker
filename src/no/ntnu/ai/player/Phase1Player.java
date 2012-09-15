package no.ntnu.ai.player;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.hands.PowerRating;

public class Phase1Player extends AbstractPokerPlayer {
	
	public Phase1Player(String name, int amount, double agg){
		super(name, amount, agg);
	}

	@Override
	public PokerAction makeDecision(Card[] table, int smallBlind, int bigBlind, 
			int amount,int potSize, int chipCount, int players, boolean allowedBet) {
		double random = (Math.random() * this.aggressiveness);
		if(table == null){
			if(random < 0.3 && allowedBet){
				return new PokerAction(Action.BET, bigBlind + amount);
			}else if(random < 0.7){
				return new PokerAction(Action.CALL, amount);
			}else{
				return new PokerAction(Action.FOLD);	
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
			}else if (simpleRating > 0.3 && allowedBet){
				return new PokerAction(Action.BET, bigBlind + amount);
			}else{
				return new PokerAction(Action.CALL, amount);
			}
		}
		
	}
	
	@Override
	public String getPhaseName(){
		return "Phase 1";
	}

}
