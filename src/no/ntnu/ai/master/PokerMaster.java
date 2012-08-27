package no.ntnu.ai.master;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.deck.Deck;
import no.ntnu.ai.player.PokerAction;
import no.ntnu.ai.player.PokerPlayer;
import no.ntnu.ai.table.PokerTable;

public class PokerMaster extends AbstractMaster {

	private final ArrayList<PokerPlayer> players = new ArrayList<PokerPlayer>();
	private final PokerTable table;

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
		for(int i = 0; i < numSims; i++){
			folded.clear();
			bets.clear();
			
			Deck deck = Deck.getInstance();
			
			//Deal cards to players
			this.dealCards(deck);
			for(int j = 0; j < table.size(); j++){
				this.table.getBetterAt(j).newHand(this.hands.get(j));
			}

			//Get blinds
			PokerPlayer smallBlind = this.table.getCurrentSmallBlindPlayer();
			smallBlind.getBlind(this.table.getSmallBlind());
			bets.put(smallBlind, this.table.getSmallBlind());

			PokerPlayer bigBlind = this.table.getCurrentBigBlindPlayer();
			smallBlind.getBlind(this.table.getBigBlind());
			bets.put(bigBlind, this.table.getBigBlind());

			//Pre-flop betting
			this.bettingRound(null, bets, folded);
			
			//TODO: Fix cases where there is a winner before showdown!
			
			//Deal flop
			this.dealFlop(deck);
			//Post-flop betting
			this.bettingRound(this.flop, bets, folded);
			
			//Deal turn
			this.dealTurn(deck);
			//Post-turn betting
			this.bettingRound(this.getCards(false), bets, folded);
			
			//Deal river
			this.dealRiver(deck);
			//Post-river betting
			this.bettingRound(this.getCards(true), bets, folded);
			
			boolean[] wins = this.declareWinner();
			//Need to show cards here
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
	
	private void bettingRound(Card[] cards, Map<PokerPlayer, Integer> bets, Map<PokerPlayer, Boolean> folded){
		while(!equalBets(bets)){
			for(int j = 0; j < table.size(); j++){
				PokerPlayer better = table.getBetterAt(j);
				if(folded.get(better) == null || !folded.get(better)){
					PokerAction act = better.getDecision(cards);
					int prevAmount = bets.get(better) == null ? 0 : bets.get(better);
					switch (act.getAct()) {
					case BET:
						bets.put(better, act.getAmount() + prevAmount); break;
					case CALL:
						bets.put(better, act.getAmount() + prevAmount); break;
					case FOLD:
						folded.put(better, true); break;
					}
				}

			}
		}
	}
}
