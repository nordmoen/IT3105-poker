package no.ntnu.ai.simulator;

public class TestResult {
	private final double wins;
	private final double ties;
	private final double losses;

	public TestResult(int win, int tie, int lose){
		this.wins = win;
		this.ties = tie;
		this.losses = lose;
	}

	public double getWins() {
		return wins;
	}

	public double getTies() {
		return ties;
	}

	public double getLosses() {
		return losses;
	}

	public double getWinRatio(){
		return (wins + ties/2) / (wins + ties + losses);
	}

	@Override
	public String toString(){
		String str = "This test resulted in " + this.getWins() + " wins, " + this.getTies() + 
				" ties and " + this.getLosses() + " losses.";
		return str;
	}

}
