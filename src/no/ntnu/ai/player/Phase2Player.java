package no.ntnu.ai.player;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.hands.HandStrength;
import no.ntnu.ai.simulator.RolloutStats;

public class Phase2Player extends AbstractPokerPlayer{
	protected final RolloutStats stats;

	public Phase2Player(String name, int count, String filename) {
		super(name, count);
		this.stats = RolloutStats.getInstance(filename);
	}

	@Override
	public PokerAction makeDecision(Card[] table, int small, int big,
			int amount, int potSize, int chipCount, int numPlayers, boolean allowedBet) {
		double random = Math.random()/4;
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
			if(hs + random > 0.7 && allowedBet && !shouldFold){
				return new PokerAction(Action.BET, calculateBet(amount, chipCount, big, hs, potSize, numPlayers));
			}else if(hs + random > 0.3 && !shouldFold){
				return new PokerAction(Action.CALL, amount);
			}else{
				return new PokerAction(Action.FOLD);
			}
		}
	}

	protected int calculateBet(int amount, int chipCount, int big, double chance, int potSize, int numPlayers) {
		int expected = (int) (chance*(potSize) - amount);
		if(chipCount < expected){
			return amount;
		}
//		if(expected > chipCount){
//			return Math.abs(chipCount) + amount;
//		}
		return (int) ((chance*expected)/((chipCount>0?chipCount : 1) * numPlayers)) + amount;
//		return (expected/2) + amount;
//		return (int) (chance*(chipCount - amount > 0? (chipCount-amount)/numPlayers: 0) + amount);
	}

	
	@Override
	public String getPhaseName(){
		return "Phase 2";
	}

}
