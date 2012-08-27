package no.ntnu.ai.master;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.deck.Deck;
import no.ntnu.ai.player.AbstractPokerPlayer;
import no.ntnu.ai.player.PokerAction;
import no.ntnu.ai.player.PokerPlayer;
import no.ntnu.ai.table.PokerTable;

public class PokerMaster extends AbstractMaster {

	private final ArrayList<PokerPlayer> players = new ArrayList<PokerPlayer>();
	private final PokerTable table;
	private final static Logger log = Logger.getLogger(PokerMaster.class.toString());

	public PokerMaster(int numPlayers, int small, int big) {
		super(numPlayers);
		this.table = new PokerTable(small, big);
	}

	public PokerMaster(List<PokerPlayer> players, int small, int big){
		this(players.size(), small, big);
		this.players.addAll(players);
		this.table.addAll(this.players);
	}

	public void simulate(int numSims){
		Map<PokerPlayer, Integer> bets = new HashMap<PokerPlayer, Integer>();
		Map<PokerPlayer, Boolean> folded = new HashMap<PokerPlayer, Boolean>();

		log.fine("------Texas Hold 'Em------");
		log.fine("There are " + this.numPlayers + " players");
		log.fine("Simulating " + numSims + " poker rounds");

		for(int i = 0; i < numSims; i++){
			log.fine("Round " + i + " starting");
			folded.clear();
			bets.clear();

			Deck deck = Deck.getInstance();

			//Deal cards to players
			log.fine("Dealing cards to all players");
			this.dealCards(deck);
			for(int j = 0; j < table.size(); j++){
				this.table.getBetterAt(j).newHand(this.hands.get(j));
			}

			//Get blinds
			PokerPlayer smallBlind = this.table.getCurrentSmallBlindPlayer();
			smallBlind.getBlind(this.table.getSmallBlind());
			bets.put(smallBlind, this.table.getSmallBlind());
			log.fine("Small blind: " + this.table.getSmallBlind());

			PokerPlayer bigBlind = this.table.getCurrentBigBlindPlayer();
			smallBlind.getBlind(this.table.getBigBlind());
			bets.put(bigBlind, this.table.getBigBlind());
			log.fine("Big blind: " + this.table.getBigBlind());

			//Pre-flop betting
			log.fine("Pre-flop betting");
			this.bettingRound(null, bets, folded);

			//TODO: Fix cases where there is a winner before showdown!

			//Deal flop
			log.fine("Dealing flop");
			this.dealFlop(deck);
			log.fine("Flop: " + Arrays.toString(this.flop));
			//Post-flop betting
			log.fine("Post-flop betting");
			this.bettingRound(this.flop, bets, folded);

			PokerPlayer winner = this.winner(bets, folded);
			if(winner != null){
				AbstractPokerPlayer wp = (AbstractPokerPlayer) winner;
				log.fine(winner + " won the game!");
				wp.giveChips(potSum(bets));
				continue;
			}

			//Deal turn
			log.fine("Dealing turn");
			this.dealTurn(deck);
			log.fine("Turn: " + this.turn);
			//Post-turn betting
			log.fine("Post-turn betting");
			this.bettingRound(this.getCards(false), bets, folded);

			winner = this.winner(bets, folded);
			if(winner != null){
				AbstractPokerPlayer wp = (AbstractPokerPlayer) winner;
				log.fine(winner + " won the game!");
				wp.giveChips(potSum(bets));
				continue;
			}

			//Deal river
			log.fine("Dealing river");
			this.dealRiver(deck);
			log.fine("River: " + this.river);
			//Post-river betting
			log.fine("Post-river betting");
			this.bettingRound(this.getCards(true), bets, folded);


			ArrayList<PokerPlayer> winners = new ArrayList<PokerPlayer>();
			for(PokerPlayer p : bets.keySet()){
				if(!folded.containsKey(p) || !folded.get(p)){
					winners.add(p);
				}else{
					this.hands.remove(p.getHand());
				}
			}
			if(winners.size() > 1){
				//Need to show cards here
			}
			boolean[] wins = this.declareWinner();
		}
	}

	private boolean equalBets(Map<PokerPlayer, Integer> bets){
		if(bets.isEmpty()){
			return true;
		}else{
			int bet = -1;
			for(PokerPlayer p : bets.keySet()){
				if(bet == -1){
					bet = bets.get(p);
				}else{
					if(bets.get(p) != bet)
						return false;
				}
			}
			return true;
		}
	}

	private int potSum(Map<PokerPlayer, Integer> bets){
		int sum = 0;
		for(Integer i : bets.values()){
			sum += i;
		}
		return sum;
	}

	private Card[] getCards(boolean river){
		Card[] cs = null;
		if(river){
			cs = new Card[5];
		}else{
			cs = new Card[4];
		}
		cs[0] = this.flop[0];
		cs[1] = this.flop[1];
		cs[2] = this.flop[2];
		cs[3] = this.turn;
		if(river){
			cs[4] = this.river;
		}

		return cs;		
	}

	private PokerPlayer winner(Map<PokerPlayer, Integer> bets, Map<PokerPlayer, Boolean> folded){
		if(bets.size() == folded.size() + 1){
			for(PokerPlayer p : folded.keySet()){
				if(!bets.containsKey(p)){
					return p;
				}
			}
		}
		return null;
	}

	private void bettingRound(Card[] cards, Map<PokerPlayer, Integer> bets, Map<PokerPlayer, Boolean> folded){
		while(!equalBets(bets)){
			for(int j = 0; j < table.size(); j++){
				PokerPlayer better = table.getBetterAt(j);
				if(folded.get(better) == null || !folded.get(better)){
					PokerAction act = better.getDecision(cards);
					int prevAmount = bets.get(better) == null ? 0 : bets.get(better);
					switch (act.getAct()) {
					case BET:
						log.fine(better + ": Betted " + act.getAmount());
						bets.put(better, act.getAmount() + prevAmount); break;
					case CALL:
						log.fine(better + ": Called");
						bets.put(better, act.getAmount() + prevAmount); break;
					case FOLD:
						log.fine(better + ": Folded");
						folded.put(better, true); break;
					}
				}

			}
		}
	}
}
