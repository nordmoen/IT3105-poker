package no.ntnu.ai.hands;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.player.PokerHand;

public class HandStrengthRunner implements Callable<double[]>{

	private final PowerRating powerRating;
	private final Card[] comCards;
	private final List<PokerHand> hands;
	private final double[] results = new double[3]; //0 = wins, 1 = ties, 2 = loses;

	public HandStrengthRunner(final List<PokerHand> hands, final Card[] comCards, final PokerHand hand){
		this.hands = hands;
		this.comCards = comCards;
		this.powerRating = new PowerRating(hand, comCards);
	}

	@Override
	public double[] call() {
		for(PokerHand h : hands){
			PowerRating pow = new PowerRating(h, comCards);

			try{
				int comp = powerRating.compareTo(pow);
				if(comp == 0){
					results[1]++;
				}else if(comp > 0){
					results[0]++;
				}else{
					results[2]++;
				}
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("Com cards: " + Arrays.toString(comCards));
				System.out.println("My hand: " + powerRating);
				System.out.println("Other hand: " + pow);
			}

		}
		return results;
	}

}

