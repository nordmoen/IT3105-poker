package no.ntnu.ai.hands;

import java.util.ArrayList;
import java.util.List;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.deck.CardUtils;
import no.ntnu.ai.deck.Deck;
import no.ntnu.ai.player.PokerHand;

public class HandStrength {
	
	/**
	 * Calculate the hand strength of a given poker hand with a given set of
	 * community cards and calculate the strength compared to a certain number 
	 * of opponents. This method will try to do the calculation in parallel
	 * @param hand - The hand to check the strength off
	 * @param comCards - The community cards on the table
	 * @param numOppns - The number of opponents to calculate against
	 * @return - A value between [0, 1) giving strength of the hand
	 */
	public static double calculateHandStrength(PokerHand hand, Card[] comCards, int numOppns){
		int numCores = Runtime.getRuntime().availableProcessors();
		
		Deck d = Deck.getInstance();
		d.remove(hand.getC1());
		d.remove(hand.getC2());
		for(Card c : comCards){
			d.remove(c);
		}
		
		
		List<PokerHand> otherHands = CardUtils.permuteDeck(d);
		List<List<PokerHand>> splitOther = CardUtils.splitList(otherHands, numCores);
		
		List<HandStrengthRunner> runners = new ArrayList<HandStrengthRunner>();
		PowerRating pow = new PowerRating(hand, comCards);
		for(List<PokerHand> ls : splitOther){			
			runners.add(new HandStrengthRunner(ls, comCards, pow)); //Add to threads
		}
		
		List<Thread> threads = new ArrayList<Thread>();
		for(Runnable r : runners){
			Thread t = new Thread(r);
			threads.add(t);
			t.start();
		}
		
		for(Thread t : threads){
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		double[] wins = new double[3]; //0 = Wins, 1 = ties and 2 = loses
		for(HandStrengthRunner hsr : runners){
			wins[0] += hsr.getWins();
			wins[1] += hsr.getTies();
			wins[2] += hsr.getLoses();
		}
		
		double a = (wins[0] + wins[1]/2) / (wins[0] + wins[1] + wins[2]);
		
		return Math.pow(a, numOppns);
	}
	
}
