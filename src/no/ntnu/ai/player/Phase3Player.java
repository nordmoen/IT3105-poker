package no.ntnu.ai.player;

import no.ntnu.ai.hands.PowerRating;


public class Phase3Player extends Phase2Player{
	//TODO: needs opponentModeler here and in constructor

	public Phase3Player(String name, int count, String filename) {
		super(name, count, filename);
	}

	
	@Override
	protected int calculateBet(int amount, int chipCount, int big, double chance, int potSize) {
		//TODO: this needs to actually calculate something
		return amount+big;
	}
	
	

	@Override
	public String getPhaseName(){
		return "Phase 3";
	}

}
