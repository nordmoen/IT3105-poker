package no.ntnu.ai.player;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.hands.HandStrength;



public class Phase3Player extends Phase2Player{
	
	public Phase3Player(String name, int count, String filename) {
		super(name, count, filename);
	}


	@Override
	public PokerAction makeDecision(Card[] table, int small, int big,
			int amount, int potSize, int chipCount, int numPlayers, boolean allowedBet) {
		double random = Math.random()/2;
		if(table == null){
			double winOdds = stats.getStat(numPlayers, currentHand);
			if(winOdds > 0.7 && allowedBet){
				return new PokerAction(Action.BET, calculateBet(amount, chipCount, big, winOdds, potSize, numPlayers));
			}else if(winOdds > 0.3){
				return new PokerAction(Action.CALL, amount);
			}else{
				return new PokerAction(Action.FOLD);
			}
		}else{
			double hs = HandStrength.calculateHandStrength(currentHand, table, numPlayers-1);
//			System.out.println("numplayers = " + numPlayers);
//			System.out.println(this + " : " + hs);
			boolean shouldFold = (hs*(potSize) - amount) <= 0;
			if(hs + random > 0.9 && allowedBet && !shouldFold){
				return new PokerAction(Action.BET, calculateBet(amount, chipCount, big, hs, potSize, numPlayers));
			}else if(hs + random > 0.4 && !shouldFold){
				return new PokerAction(Action.CALL, amount);
			}else{
				return new PokerAction(Action.FOLD);
			}
		}
	}
	@Override
	protected int calculateBet(int amount, int chipCount, int big, double chance, int potSize, int numPlayers) {
		//TODO: this needs to actually calculate something
		return amount+big;
	}



	@Override
	public String getPhaseName(){
		return "Phase 3";
	}

}
