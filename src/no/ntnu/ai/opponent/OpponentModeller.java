package no.ntnu.ai.opponent;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import no.ntnu.ai.hands.HandStrength;
import no.ntnu.ai.player.PokerHand;
import no.ntnu.ai.player.PokerPlayer;
import no.ntnu.ai.simulator.RolloutStats;

public class OpponentModeller {
	
	private static OpponentModeller mod;
	
	private final Map<PokerContext, List<Double>> contexts;
	private final Map<PokerContext, Boolean> calculationFresh;
	private final Map<PokerContext, Double> handStrengthAvg;
	private final Map<PokerContext, Double> handStrengthDev;
	private final Set<PokerContext> roundContexts;
	private long numRounds, numContexts, numShowdowns = 0;
	
	private OpponentModeller(){
		this.contexts = new HashMap<PokerContext, List<Double>>();
		this.calculationFresh = new HashMap<PokerContext, Boolean>();
		this.handStrengthAvg = new HashMap<PokerContext, Double>();
		this.handStrengthDev = new HashMap<PokerContext, Double>();
		this.roundContexts = new HashSet<PokerContext>();
	}
	
	public void printDebugInfo(boolean err){
		PrintStream p = err ? System.err : System.out;
		p.println("------ Opponent modeller ------");
		p.println("This modeller has seen " + numRounds + " rounds of Texas Hold 'Em");
		p.println("During those rounds it has gotten " + numContexts + " contexts");
		p.println("There have been " + numShowdowns + " showdowns");
		p.println("Those showdowns has led to " + contexts.size() + " contexts added");
		p.println("Data gathered consist of:");
		p.println("PokerContext:\t\tAvg: Std dev:");
		for(PokerContext pc : contexts.keySet()){
			p.println(pc + "\t\t" + this.getAvgHandStrength(pc) + 
					", " + this.getStdDevHandStrength(pc));
		}
		p.println("-------------------------------");
		
	}
	
	public boolean addContext(PokerContext c){
		numContexts++;
		return this.roundContexts.add(c);
	}
	
	private void addHandStrength(PokerContext c, double handStrength){
		if(!this.contexts.containsKey(c)){
			this.contexts.put(c, new ArrayList<Double>());
		}
		this.contexts.get(c).add(handStrength);
	}
	
	public void showdown(List<PokerPlayer> players, List<PokerHand> hands){
		for(int i = 0; i < players.size(); i++){
			for(PokerContext pc : roundContexts){
				if(pc.getPlayer().equals(players.get(i))){
					this.calculationFresh.put(pc, false);
					if(!pc.isPreflop()){
						this.addHandStrength(pc, 
								HandStrength.calculateHandStrength(hands.get(i), 
										pc.getCards(), pc.getNumPlayers()));
					}else{
						this.addHandStrength(pc, 
								RolloutStats.getInstance("").getStat(pc.getNumPlayers(), hands.get(i)));
					}
				}
			}
		}
		numShowdowns++;
		this.roundContexts.clear();
	}
	
	public void nextRound(){
		numRounds++;
		this.roundContexts.clear();
	}
	
	private double getHandStrength(PokerContext c, Map<PokerContext, Double> map){
		if(contexts.containsKey(c)){
			if(!this.calculationFresh.get(c)){
				this.calculateAvgHandStrength(c);
				this.calculationFresh.put(c, true);
			}
			return map.get(c);
		}
		return -3.1415;
	}
	
	public double getAvgHandStrength(PokerContext c){
		return this.getHandStrength(c, this.handStrengthAvg);
	}
	
	public double getStdDevHandStrength(PokerContext c){
		return this.getHandStrength(c, this.handStrengthDev);
	}
	
	private void calculateAvgHandStrength(PokerContext c){
		//Calculate the average hand strength and standard deviation
		double sum = 0;
		double sumSq = 0;
		for(double d : this.contexts.get(c)){
			sum += d;
		}
		sum /= this.contexts.get(c).size();
		for(double d : this.contexts.get(c)){
			sumSq = Math.pow(d - sum, 2);
		}
		sumSq /= this.contexts.get(c).size();
		sumSq = Math.sqrt(sumSq);
		
		this.handStrengthAvg.put(c, sum);
		this.handStrengthDev.put(c, sumSq);
	}

	
	public static OpponentModeller getInstance(){
		if(mod == null){
			mod = new OpponentModeller();
		}
		return mod;
	}
}
