package no.ntnu.ai.player;

import java.util.Set;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.hands.HandStrength;
import no.ntnu.ai.opponent.OpponentModeller;
import no.ntnu.ai.opponent.PokerContext;



public class Phase3Player extends Phase2Player{
	private final OpponentModeller opMod;
	private final double aggressiveness;

	/**
	 * 
	 * @param name = name for this player
	 * @param count = initial chipcount
	 * @param filename = filename of the preflop calculations
	 * @param agg = a positive double telling how aggressively the player should play, higher number means less aggressively
	 */
	public Phase3Player(String name, int count, String filename, double agg) {
		super(name, count, filename);
		this.opMod = OpponentModeller.getInstance();
		this.aggressiveness = agg;
	}


	@Override
	public PokerAction makeDecision(Card[] table, int small, int big,
			int amount, int potSize, int chipCount, int numPlayers, boolean allowedBet) {
//		Set<PokerContext> contexts = null;
		Set<PokerContext> contexts = opMod.getRoundContexts();
		double contextOdds = 0;
		double aggDev;
		PokerContext highestContext = null;
		if(!contexts.isEmpty()){
			for(PokerContext c:contexts){
				double test = opMod.getAvgHandStrength(c);
				if (test>contextOdds){
					contextOdds = test;
					highestContext = c;
				}
			}
			aggDev = aggressiveness*opMod.getStdDevHandStrength(highestContext);
		}else{
			contextOdds = 0.9;
			aggDev = 0.01;
		}
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
			if(hs > (contextOdds + aggDev) && allowedBet && !shouldFold){
				//System.out.println(this.getName() + ", BET: " + new PowerRating(this.currentHand, table));
				return new PokerAction(Action.BET, calculateBet(amount, chipCount, big, hs, potSize, numPlayers));
			}else if(hs > (contextOdds) && !shouldFold){
				//System.out.println(this.getName() + ", CALL: " + new PowerRating(this.currentHand, table));
				return new PokerAction(Action.CALL, amount);
			}else{
				//System.out.println(this.getName() + ", FOLD: " + new PowerRating(this.currentHand, table));
				return new PokerAction(Action.FOLD);
			}
		}
	}
	@Override
	protected int calculateBet(int amount, int chipCount, int big, double chance, int potSize, int numPlayers) {
		//TODO: this should maybe use opponentmodelling.
		return super.calculateBet(amount, chipCount, big, chance, potSize, numPlayers);
	}



	@Override
	public String getPhaseName(){
		return "Phase 3";
	}
}
