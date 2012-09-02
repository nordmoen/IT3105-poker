package no.ntnu.ai.hands;

import java.util.List;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.player.PokerHand;

public class HandStrengthRunner implements Runnable{
	
	private final PowerRating powerRating;
	private final Card[] comCards;
	private final List<PokerHand> hands;
	private final double[] results = new double[3]; //0 = wins, 1 = ties, 2 = loses;
	private boolean done = false;

	public HandStrengthRunner(List<PokerHand> hands, Card[] comCards, PowerRating pow){
		this.hands = hands;
		this.comCards = comCards;
		this.powerRating = pow;
	}

	@Override
	public void run() {
		for(PokerHand h : hands){
			PowerRating pow = new PowerRating(h, comCards);
			
			int comp = powerRating.compareTo(pow);
			
			if(comp == 0){
				results[1]++;
			}else if(comp > 0){
				results[0]++;
			}else{
				results[2]++;
			}
		}
		done = true;
	}
	
	private double getResult(int i){
		if(!done){
			throw new IllegalAccessError("Tried to access a result before " +
					"it is calculated");
		}
		return results[i];
	}
	
	public double getWins(){
		return getResult(0);
	}
	
	public double getTies(){
		return getResult(1);
	}
	public double getLoses(){
		return getResult(2);
	}
	
}

