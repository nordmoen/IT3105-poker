package no.ntnu.ai.simulator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.deck.Suit;
import no.ntnu.ai.player.PokerHand;

public class RolloutStats {

	private static Map<String, RolloutStats> instances = new HashMap<String, RolloutStats>();
	private final Map<Integer, Map<PokerHand, Double>> stats;
	private final Suit suit1 = Suit.SPADES;
	private final Suit suit2 = Suit.HEARTS;

	private RolloutStats(String name){
		stats = new HashMap<Integer, Map<PokerHand,Double>>();
		this.readFile(name);
	}

	private void readFile(String filename){
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String input = "";
			String[] res;
			do{
				input = in.readLine();
				if(input != null){
					res = input.split(",");

					Card c1 = new Card(Integer.parseInt(res[0]), suit1);
					Suit s;
					if(res[2].equals("S")){
						s = suit1;
					}else{
						s = suit2;
					}
					Card c2 = new Card(Integer.parseInt(res[1]), s);

					for(int i = 3; i < res.length; i++){
						String[] splited = res[i].split(":");
						int j = Integer.parseInt(splited[0]);
						if(!stats.containsKey(j)){
							stats.put(j, new HashMap<PokerHand, Double>());
						}
						double val = Double.parseDouble(splited[1]);
						stats.get(j).put(new PokerHand(c1, c2), val);
						stats.get(j).put(new PokerHand(c2, c1), val);
					}
				}

			}while(input != null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public double getStat(int players, PokerHand hand){
		Card c1 = new Card(hand.getC1().getValue(), suit1);
		Card c2 = new Card(hand.getC2().getValue(), (hand.isSuited() ? suit1 : suit2));
		PokerHand h1 = new PokerHand(c1, c2);
		return this.stats.get(players).get(h1);
	}

	public static RolloutStats getInstance(String name){
		if(!instances.containsKey(name)){
			instances.put(name, new RolloutStats(name));
		}
		return instances.get(name);
	}

}
