package no.ntnu.ai.simulator;

import java.util.HashMap;

import no.ntnu.ai.player.PokerHand;

/**
 * A class representing a simulation result
 *
 */
public class SimResult{
	
	private final PokerHand hand;
	private final ResultType type;
	private final HashMap<Integer, TestResult> result;
	
	public SimResult(PokerHand hand, ResultType type, HashMap<Integer, TestResult> res){
		this.hand = hand;
		this.type = type;
		this.result = res;
	}
	
	public PokerHand getHand() {
		return hand;
	}
	public ResultType getType() {
		return type;
	}
	public HashMap<Integer, TestResult> getResult() {
		return result;
	}
	
	@Override
	public String toString(){
		StringBuilder res = new StringBuilder();
		res.append(hand.getC1().getValue() + "," + hand.getC2().getValue());
		res.append("," + (hand.getC1().getSuit() == hand.getC2().getSuit() ? "S":"U"));
		for(Integer players : result.keySet()){
			res.append("," + players + ":" + result.get(players).getWinRatio());
		}
		return res.toString();
	}
	

}
