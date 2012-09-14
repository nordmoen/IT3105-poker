package no.ntnu.ai.opponent;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import no.ntnu.ai.hands.HandStrength;
import no.ntnu.ai.player.Action;
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
	private final Set<PokerPlayer> roundFolded;
	private long numRounds, numContexts, numShowdowns = 0;
	
	private OpponentModeller(){
		this.contexts = new HashMap<PokerContext, List<Double>>();
		this.calculationFresh = new HashMap<PokerContext, Boolean>();
		this.handStrengthAvg = new HashMap<PokerContext, Double>();
		this.handStrengthDev = new HashMap<PokerContext, Double>();
		this.roundContexts = new HashSet<PokerContext>();
		this.roundFolded = new HashSet<PokerPlayer>();
	}
	
	/**
	 * Print debug information about this opponent modeler to either out or err
	 * @param err - Whether or not to use System.err or System.out
	 */
	public void printDebugInfo(boolean err){
		PrintStream p = err ? System.err : System.out;
		p.println("------ Opponent modeller ------");
		p.println("This modeller has seen " + numRounds + " rounds of Texas Hold 'Em");
		p.println("During those rounds it has gotten " + numContexts + " contexts");
		p.println("There have been " + numShowdowns + " showdowns");
		p.println("Those showdowns has led to " + contexts.size() + " contexts added");
		p.println("Data gathered consist of:");
		String spaces = "";
		for(int i = 0; i < contexts.keySet().iterator().next().toString().length(); i++){
			spaces += " ";
		}
		p.println("PokerContext:" + spaces + "Avg: Std dev: Number seen:");
		for(PokerContext pc : contexts.keySet()){
			p.println(pc + "\t\t" + this.getAvgHandStrength(pc) + 
					", " + this.getStdDevHandStrength(pc) + ", " + contexts.get(pc).size());
		}
		p.println("-------------------------------");
		
	}
	
	/**
	 * Add a context to the currently seen context, if the player in the context
	 * ends up in a showdown this round the context will be added to all the seen
	 * contexts.
	 * @param c - The context to preliminary add
	 * @return - Returns true if the set did not already contain the context
	 */
	public boolean addContext(PokerContext c){
		numContexts++;
		if(c.getAction() == Action.FOLD){
			this.roundFolded.add(c.getPlayer());
		}
		return this.roundContexts.add(c);
	}
	
	private void addHandStrength(PokerContext c, double handStrength){
		if(!this.contexts.containsKey(c)){
			this.contexts.put(c, new ArrayList<Double>());
		}
		this.contexts.get(c).add(handStrength);
	}
	
	/**
	 * Tell the opponent modeler that some players have reach a showdown. The modeler
	 * will then search through the current contexts to find the contexts from the
	 * players in the showdown and adds them to the seen context along with the
	 * hand strength of the given situation. 
	 * @param players - A list of players which has reached the showdown along with
	 * their private cards.
	 */
	public void showdown(Map<PokerPlayer, PokerHand> players){
		for(PokerPlayer p : players.keySet()){
			for(PokerContext pc : roundContexts){
				if(pc.getPlayer().equals(p)){
					this.calculationFresh.put(pc, false);
					if(!pc.isPreflop()){
						this.addHandStrength(pc, 
								HandStrength.calculateHandStrength(players.get(p), 
										pc.getCards(), pc.getNumPlayers()));
					}else{
						this.addHandStrength(pc, 
								RolloutStats.getInstance("").getStat(pc.getNumPlayers(), players.get(p)));
					}
				}
			}
		}
		numShowdowns++;
		this.clearBetweenRounds();
	}
	
	/**
	 * Indicate that the next round was reached, this will clear the currently seen
	 * contexts without doing anything to them
	 */
	public void nextRound(){
		numRounds++;
		this.clearBetweenRounds();
	}
	
	private void clearBetweenRounds(){
		this.roundContexts.clear();
		this.roundFolded.clear();
	}
	
	private double getHandStrength(PokerContext c, Map<PokerContext, Double> map){
		if(contexts.containsKey(c)){
			if(!this.calculationFresh.get(c)){
				this.calculateAvgHandStrength(c);
				this.calculationFresh.put(c, true);
			}
			return map.get(c);
		}
		return -Math.PI;
	}
	
	/**
	 * Get the average hand strength of a given PokerContext
	 * @param c - The context
	 * @return - Either a positive value between [0, 1) or -PI to indicate no values for the context
	 */
	public double getAvgHandStrength(PokerContext c){
		return this.getHandStrength(c, this.handStrengthAvg);
	}
	
	public Map<PokerContext, List<Double>> getContextForPlayer(PokerPlayer p){
		Map<PokerContext, List<Double>> res = new HashMap<PokerContext, List<Double>>();
		for(PokerContext c : this.contexts.keySet()){
			if(c.getPlayer().equals(p)){
				res.put(c, this.contexts.get(c));
			}
		}
		return res;
	}
	
	/**
	 * Get the standard deviation of a given poker context, this is a method
	 * which can be used to see how much a given context has deviated
	 * @param c - The poker context
	 * @return - A positive double or -PI if no values are assosiated with the context
	 */
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
	
	/**
	 * Get the contexts seen in this round of play with contexts only from not
	 * folded players
	 * @return - A set of {@link PokerContext}s
	 */
	public Set<PokerContext> getRoundContexts(PokerPlayer player){
		Set<PokerContext> res = new HashSet<PokerContext>(this.roundContexts);
		for(PokerPlayer p : this.roundFolded){
			for(PokerContext pc : this.roundContexts){
				if(pc.getPlayer().equals(p)){
					res.remove(pc);
				}
				if(pc.getPlayer() == player){
					res.remove(pc);
				}
			}
		}
		return res;
	}

	
	public static OpponentModeller getInstance(){
		if(mod == null){
			mod = new OpponentModeller();
		}
		return mod;
	}
}
