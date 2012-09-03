package no.ntnu.ai.player;


public class Phase3Player extends Phase2Player{
	//TODO: needs opponentModeler here and in constructor

	public Phase3Player(String name, int count, int numPlayers, String filename) {
		super(name, count, numPlayers, filename);
	}

	
	@Override
	protected int calculateBet(int amount, int chipCount, int big, double chance) {
		//TODO: this needs to actually calculate something
		return amount+big;
	}
	
	@Override
	protected double logicTest(double odds){
		//TODO: need actual test here
		return 0.0;
	}


}
