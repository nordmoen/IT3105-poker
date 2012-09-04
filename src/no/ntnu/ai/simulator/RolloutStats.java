package no.ntnu.ai.simulator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import no.ntnu.ai.config.Config;
import no.ntnu.ai.hands.HandTuple;
import no.ntnu.ai.player.PokerHand;

public class RolloutStats {

	private static Map<String, RolloutStats> instances = new HashMap<String, RolloutStats>();
	private final Map<Integer, Map<HandTuple, Double>> stats;

	private RolloutStats(String name){
		stats = new HashMap<Integer, Map<HandTuple, Double>>();
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

					int c1Value = Integer.parseInt(res[0]);
					int c2Value = Integer.parseInt(res[1]);
					boolean suited = res[2].equals("S");

					for(int i = 3; i < res.length; i++){
						String[] splited = res[i].split(":");
						int j = Integer.parseInt(splited[0]);
						double val = Double.parseDouble(splited[1]);
						if(!stats.containsKey(j)){
							stats.put(j, new HashMap<HandTuple, Double>());
						}
						stats.get(j).put(new HandTuple(c1Value, c2Value, suited), val);
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
		Double val = this.stats.get(players).get(new HandTuple(hand.getC1().getValue(), 
				hand.getC2().getValue(), hand.isSuited()));
		if(val == null){
			return this.stats.get(players).get(new HandTuple(hand.getC2().getValue(), 
					hand.getC1().getValue(), hand.isSuited()));
		}else{
			return val;
		}
	}

	/**
	 * Get an RolloutStats instance with the name equal to the file one wants to
	 * get the statistics from. There is a default where name is "" where 
	 * Config.ROLLOUT_FILE_NAME is used which get the name from the command line
	 * argument "-DsimFile=value"
	 * @param name - The name of the file to get the instance for
	 * @return - A RolloutStats instance which has read the file and compiled the
	 * statistics.
	 */
	public static RolloutStats getInstance(String name){
		if(!instances.containsKey(name)){
			if(name.equals("")){
				instances.put("", new RolloutStats(Config.ROLLOUT_FILE_NAME));
			}else{
				instances.put(name, new RolloutStats(name));
			}
		}
		return instances.get(name);
	}

}
