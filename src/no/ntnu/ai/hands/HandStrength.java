package no.ntnu.ai.hands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.deck.CardUtils;
import no.ntnu.ai.deck.Deck;
import no.ntnu.ai.player.PokerHand;

public class HandStrength {
	
	private static ExecutorService pool;

	/**
	 * Calculate the hand strength of a given poker hand with a given set of
	 * community cards and calculate the strength compared to a certain number 
	 * of opponents. This method will try to do the calculation in parallel
	 * @param hand - The hand to check the strength off
	 * @param comCards - The community cards on the table
	 * @param numOppns - The number of opponents to calculate against
	 * @return - A value between (0, 1] giving strength of the hand
	 */
	public static synchronized double calculateHandStrength(final PokerHand hand, final Card[] comCards, final int numOppns){
		int numCores = Runtime.getRuntime().availableProcessors();

		Deck d = Deck.getInstance();
		for(Card c : comCards){
			d.remove(c);
		}
		d.remove(hand.getC1());
		d.remove(hand.getC2());

		List<PokerHand> otherHands = CardUtils.permuteDeck(d);
		List<List<PokerHand>> splitOther = CardUtils.splitList(otherHands, numCores);
		pool = Executors.newFixedThreadPool(numCores);

		Set<Future<double[]>> results = new HashSet<Future<double[]>>();
		for(List<PokerHand> ls : splitOther){			
			results.add(pool.submit(new HandStrengthRunner(ls, comCards, hand)));
		}

		double[] wins = new double[3]; //0 = Wins, 1 = ties and 2 = loses
		try {
			for(Future<double[]> fut : results){
				double[] res;
				res = fut.get();
				wins[0] += res[0];
				wins[1] += res[1];
				wins[2] += res[2];
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		double a = (wins[0] + wins[1]/2) / (wins[0] + wins[1] + wins[2]);
		
		pool.shutdown();
		
		return Math.pow(a, numOppns);
	}

	public static double advCalculateHandStrength(PokerHand hand, Card[] comCards, int numOppns){
		int numCores = Runtime.getRuntime().availableProcessors();

		Deck d = Deck.getInstance();
		d.remove(hand.getC1());
		d.remove(hand.getC2());
		for(Card c : comCards){
			d.remove(c);
		}

		Card[] cs = new Card[5];

		for(int i = 0; i < comCards.length; i++){
			cs[i] = comCards[i];
		}
		Set<Future<double[]>> results = createHandStrengthRunners(hand, cs, d, numCores);

		double[] wins = new double[3]; //0 = Wins, 1 = ties and 2 = loses
		try {
			for(Future<double[]> fut : results){
				double[] res;
				res = fut.get();
				wins[0] += res[0];
				wins[1] += res[1];
				wins[2] += res[2];
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		double a = (wins[0] + wins[1]/2) / (wins[0] + wins[1] + wins[2]);
		
		pool.shutdown();

		return Math.pow(a, numOppns);
	}

	private static Set<Future<double[]>> createHandStrengthRunners(PokerHand hand, Card[] cs, Deck d, int numToSplit){
		pool = Executors.newFixedThreadPool(numToSplit);
		Set<Future<double[]>> results = new HashSet<Future<double[]>>();
		if(d.size() == 47){
			for(int i = 0; i < d.size(); i++){
				for(int j = i + 1; j < d.size(); j++){
					Deck d1 = (Deck) d.clone();
					cs[3] = d.get(i);
					cs[4] = d.get(j);
					d1.remove(cs[3]);
					d1.remove(cs[4]);

					List<PokerHand> otherHands = CardUtils.permuteDeck(d1);
					List<List<PokerHand>> splitOtherHands = CardUtils.splitList(otherHands, numToSplit);
					for(List<PokerHand> ls : splitOtherHands){
						results.add(pool.submit(new HandStrengthRunner(ls, cs, hand)));
					}
				}
			}
		}else if(d.size() == 46){
			for(int i = 0; i < d.size(); i++){
				Deck d1 = (Deck)d.clone();
				cs[4] = d1.get(i);
				d1.remove(cs[4]);
				
				List<PokerHand> otherHands = CardUtils.permuteDeck(d1);
				List<List<PokerHand>> splitOtherHands = CardUtils.splitList(otherHands, numToSplit);
				for(List<PokerHand> ls : splitOtherHands){
					results.add(pool.submit(new HandStrengthRunner(ls, cs, hand)));
				}
			}
		}else{			
			List<PokerHand> otherHands = CardUtils.permuteDeck(d);
			List<List<PokerHand>> splitOtherHands = CardUtils.splitList(otherHands, numToSplit);
			for(List<PokerHand> ls : splitOtherHands){
				results.add(pool.submit(new HandStrengthRunner(ls, cs, hand)));
			}
		}
		return results;
	}
}
