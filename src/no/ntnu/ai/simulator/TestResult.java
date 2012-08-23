package no.ntnu.ai.simulator;

public class TestResult {
	private int wins;
	private int ties;
	private int losses;
	
	public TestResult(int win, int tie, int lose){
		this.wins = win;
		this.ties = tie;
		this.losses = lose;
	}

	public int getWins() {
		return wins;
	}

	public int getTies() {
		return ties;
	}

	public int getLosses() {
		return losses;
	}
	
	@Override
	public String toString(){
		String str = "This test resulted in " + this.getWins() + " wins, " + this.getTies() + 
		" ties and " + this.getLosses() + " losses.";
		return str;
	}

}
