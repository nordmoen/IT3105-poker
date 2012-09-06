package no.ntnu.ai.player;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.hands.HandStrength;
import no.ntnu.ai.simulator.RolloutStats;

public class Phase2Player extends AbstractPokerPlayer{
	private final RolloutStats stats;

	public Phase2Player(String name, int count, String filename) {
		super(name, count);
		this.stats = RolloutStats.getInstance(filename);
	}

	@Override
	public PokerAction makeDecision(Card[] table, int small, int big,
			int amount, int potSize, int chipCount, int numPlayers, boolean allowedBet) {
		if(table == null){
			double winOdds = stats.getStat(numPlayers, currentHand);
			if(winOdds > 0.7 && allowedBet){
				return new PokerAction(Action.BET, calculateBet(amount, chipCount, big, winOdds, potSize));
			}else if(winOdds > 0.3){
				return new PokerAction(Action.CALL, amount);
			}else{
				return new PokerAction(Action.FOLD);
			}
		}else{
			double hs = HandStrength.calculateHandStrength(currentHand, table, numPlayers-1);
			boolean shouldFold = (hs*(potSize + amount) - amount) <= 0;
			if(hs > 0.7 && allowedBet && !shouldFold){
				return new PokerAction(Action.BET, calculateBet(amount, chipCount, big, hs, potSize));
			}else if(hs > 0.3 && !shouldFold){
				return new PokerAction(Action.CALL, amount);
			}else{
				return new PokerAction(Action.FOLD);
			}
		}
	}

	protected int calculateBet(int amount, int chipCount, int big, double chance, int potSize) {
		int expected = (int) (chance*(potSize + amount) - amount);
		return expected;
	}

	
	@Override
	public String getPhaseName(){
		return "Phase 2";
	}

}
