package no.ntnu.ai.master;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.deck.Deck;
import no.ntnu.ai.opponent.OpponentModeller;
import no.ntnu.ai.opponent.PokerContext;
import no.ntnu.ai.player.AbstractPokerPlayer;
import no.ntnu.ai.player.PokerAction;
import no.ntnu.ai.player.PokerHand;
import no.ntnu.ai.player.PokerPlayer;
import no.ntnu.ai.table.PokerTable;

public class PokerMaster extends AbstractMaster {

	private final ArrayList<PokerPlayer> players = new ArrayList<PokerPlayer>();
	private final PokerTable table;
	private final OpponentModeller mod = OpponentModeller.getInstance();
	private final boolean addContext;

	public PokerMaster(int numPlayers, PokerTable table, boolean addc) {
		super(numPlayers);
		this.table = table;
		this.addContext = addc;
	}

	public PokerMaster(List<PokerPlayer> players, PokerTable table, boolean addc){
		this(players.size(), table, addc);
		this.players.addAll(players);
		this.table.addAll(players);
	}

	public boolean singleWin(Map<PokerPlayer, Integer> bets, Map<PokerPlayer, Boolean> folded, Card[] cards2){
		PokerPlayer winner = this.winner(folded);
		if(winner != null){
			AbstractPokerPlayer wp = (AbstractPokerPlayer) winner;
			wp.giveChips(potSum(bets));
			return true;
		}
		return false;
	}

	public void simulate(int numSims){
		Map<PokerPlayer, Integer> bets = new HashMap<PokerPlayer, Integer>();
		Map<PokerPlayer, Boolean> folded = new HashMap<PokerPlayer, Boolean>();

		if(!addContext){
			System.out.println("------Texas Hold 'Em------");
			System.out.println("There are " + this.numPlayers + " players");
			System.out.println("Simulating " + numSims + " poker rounds");
		}

		for(int i = 0; i < numSims; i++){
			this.table.nextRound();
			this.reset();
			if(addContext){
				this.mod.nextRound();
			}
			
			folded.clear();
			bets.clear();

			Deck deck = Deck.getInstance((int)(Math.random()*Integer.MAX_VALUE), (int)(Math.random()*4) + 3);
			deck.stdShuffle();

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
			bigBlind.getBlind(this.table.getBigBlind());
			bets.put(bigBlind, this.table.getBigBlind());

			//Pre-flop betting
			this.bettingRound(null, bets, folded);

			if(singleWin(bets, folded, null)){
				continue;
			}


			//Deal flop
			this.dealFlop(deck);
			//Post-flop betting
			this.bettingRound(this.flop, bets, folded);

			if(singleWin(bets, folded, flop)){
				continue;
			}

			//Deal turn
			this.dealTurn(deck);
			//Post-turn betting
			this.bettingRound(this.getCards(false), bets, folded);

			if(singleWin(bets, folded, getCards(false))){
				continue;
			}

			//Deal river
			this.dealRiver(deck);
			//Post-river betting
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
				if(addContext){
					Map<PokerPlayer, PokerHand> winnerHands = new HashMap<PokerPlayer, PokerHand>();
					for(PokerPlayer p : winners){
						winnerHands.put(p, p.getHand());
					}
					this.mod.showdown(winnerHands);
				}
			}
			ArrayList<PokerPlayer> win = this.declareWinner(winners);
			if(win.size() > 1){
				int split = potSum(bets) / win.size();
				for(PokerPlayer p : win){
					((AbstractPokerPlayer) p).giveChips(split);
				}
			}else{
				((AbstractPokerPlayer) win.get(0)).giveChips(potSum(bets));
			}

		}
		if(addContext)
			this.mod.printDebugInfo(false);
		System.out.println("After " + numSims + " rounds the status is:");
		for(PokerPlayer p : this.players){
			System.out.println(p);
		}
	}

	private boolean equalBets(Map<PokerPlayer, Integer> bets, Map<PokerPlayer, Boolean> folded){
		if(bets.isEmpty()){
			return true;
		}else{
			int bet = -1;
			for(PokerPlayer p : bets.keySet()){
				if(folded.get(p) == null || !folded.get(p)){
					if(bet == -1){
						bet = bets.get(p);
					}else{
						if(bets.get(p) != bet)
							return false;
					}
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

	private PokerPlayer winner(Map<PokerPlayer, Boolean> folded){
		if(folded.size() == this.players.size() -1){
			for(PokerPlayer p : players){
				if(!folded.containsKey(p)){
					return p;
				}
			}
		}
		return null;
	}

	private void bettingRound(Card[] cards, Map<PokerPlayer, Integer> bets, Map<PokerPlayer, Boolean> folded){
		PokerPlayer lastBetter = null;
		boolean started = false;
		int betRounds = 0;
		while(!equalBets(bets, folded) || !started){
			betRounds ++;
			started = true;
			for(int j = 0; j < table.size(); j++){
				PokerPlayer better = table.getBetterAt(j);
				if(folded.get(better) == null || !folded.get(better)){
					if(folded.size() == players.size() -1){
						return;
					}
					if(better != lastBetter){
						int amount = java.util.Collections.max(bets.values()) - (bets.get(better) == null ? 0: bets.get(better));
						PokerAction act = better.getDecision(cards, 
								table.getSmallBlind(), table.getBigBlind(), 
								amount, this.potSum(bets), players.size() - folded.size(), (betRounds <= 2));
						int prevAmount = bets.get(better) == null ? 0 : bets.get(better);
						switch (act.getAct()) {
						case BET:
							bets.put(better, act.getAmount() + prevAmount); 
							lastBetter = better; break;
						case CALL:
							bets.put(better, act.getAmount() + prevAmount); break;
						case FOLD:
							folded.put(better, true); break;
						}
						if(addContext){
							this.mod.addContext(new PokerContext(better, cards, 
									players.size() - folded.size(), act.getAct(), 
									amount, this.potSum(bets)));
						}
					}else{
						break;
					}
				}
			}
		}
	}
}
