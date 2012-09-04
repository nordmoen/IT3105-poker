package no.ntnu.ai.player;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.hands.HandStrength;
import no.ntnu.ai.simulator.RolloutStats;

public class Phase2Player extends AbstractPokerPlayer{
	protected final int numPlayers;
	private final String fileName;

	public Phase2Player(String name, int count, int numPlayers, String filename) {
		super(name, count);
		this.numPlayers = numPlayers;
		this.fileName = filename;
	}

	@Override
	public PokerAction makeDecision(Card[] table, int small, int big,
			int amount, int chipCount) {
		if(table == null){
			double winOdds = RolloutStats.getInstance(fileName).getStat(numPlayers, currentHand);
			double test = logicTest(winOdds);
			if(test > 0.7){
				return new PokerAction(Action.BET, calculateBet(amount, chipCount, big, winOdds));
			}else if(test > 0.3){
				return new PokerAction(Action.CALL, amount);
			}else{
				return new PokerAction(Action.FOLD);
			}
		}else{
			double hs = HandStrength.calculateHandStrength(currentHand, table, numPlayers-1);
			double test = logicTest(hs);
			if(test > 0.7){
				return new PokerAction(Action.BET, calculateBet(amount, chipCount, big, hs));
			}else if(test > 0.3){
				return new PokerAction(Action.CALL, amount);
			}else{
				return new PokerAction(Action.FOLD);
			}
		}
	}

	protected int calculateBet(int amount, int chipCount, int big, double chance) {
		//TODO: this needs to actually calculate something
		return amount+big;
	}

	protected double logicTest(double odds){
		//TODO: need actual test here
		return 0.0;
	}
	
	@Override
	public String getPhaseName(){
		return "Phase 2";
	}

}
