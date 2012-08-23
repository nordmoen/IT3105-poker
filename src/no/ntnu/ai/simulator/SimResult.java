package no.ntnu.ai.simulator;

import java.io.Serializable;

import no.ntnu.ai.hands.PokerResult;
import no.ntnu.ai.player.PokerHand;

/**
 * A class representing a simulation result
 *
 */
@SuppressWarnings("serial")
public class SimResult implements Serializable {
	
	private final PokerHand hand;
	private final ResultType type;
	private final PokerResult result;
	private final int numPlayers;
	
	public SimResult(PokerHand hand, ResultType type, PokerResult res, int numPlayers){
		this.hand = hand;
		this.type = type;
		this.result = res;
		this.numPlayers = numPlayers;
	}
	
	public PokerHand getHand() {
		return hand;
	}
	public ResultType getType() {
		return type;
	}
	public PokerResult getResult() {
		return result;
	}

	public int getNumPlayers() {
		return numPlayers;
	}
	
	

}
