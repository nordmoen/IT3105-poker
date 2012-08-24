package no.ntnu.ai.master;

import java.util.ArrayList;
import java.util.HashMap;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.deck.Deck;
import no.ntnu.ai.hands.PowerRating;
import no.ntnu.ai.hands.PowerUtils;
import no.ntnu.ai.player.PokerHand;

public abstract class AbstractMaster {
	protected int numPlayers;
	protected Card[] flop = new Card[3];
	protected Card turn;
	protected Card river;
	protected final ArrayList<PokerHand> hands = new ArrayList<PokerHand>();
	protected boolean dealtCards = false;
	protected boolean dealtFlop = false;
	protected boolean dealtTurn = false;
	protected boolean dealtRiver = false;
	protected HashMap<PowerRating, Integer> handPower = new HashMap<PowerRating, Integer>();
	
	public AbstractMaster(int numPlayers){
		this.numPlayers = numPlayers;
	}
	
	
	public void dealCards(Deck deck1){
		Card[] cards = deck1.dealCards(numPlayers*2);
		for(int i=0; i<numPlayers; i++){
			hands.add(new PokerHand(cards[i], cards[numPlayers+i]));
		}
		this.dealtCards = true;
	}
	
	public void dealFlop(Deck deck1){
		if(!this.dealtCards){
			throw new IllegalStateException("Must deal cards before the flop.");
		}
		deck1.dealCard();
		this.flop = deck1.dealCards(3);
		this.dealtFlop = true;
	}
	
	public void dealTurn(Deck deck1){
		if(!this.dealtFlop){
			throw new IllegalStateException("Must deal flop before the turn.");
		}
		deck1.dealCard();
		this.turn = deck1.dealCard();
		this.dealtTurn = true;
	}
	
	public void dealRiver(Deck deck1){
		if(!this.dealtTurn){
			throw new IllegalStateException("Must deal turn before the river.");
		}
		deck1.dealCard();
		this.river = deck1.dealCard();
		this.dealtRiver = true;
	}
	
	public boolean[] declareWinner(){
		if(!this.dealtRiver){
			throw new IllegalStateException("Must deal the river before declaring a winner.");
		}
		boolean[] winners = new boolean[numPlayers];
		PowerRating[] prs = new PowerRating[numPlayers];
		Card[] cards = new Card[7];
		cards[0] = flop[0];
		cards[1] = flop[1];
		cards[2] = flop[2];
		cards[3] = this.turn;
		cards[4] = this.river;
		for(int i=0; i<numPlayers; i++){
			cards[5] = hands.get(i).getC1();
			cards[6] = hands.get(i).getC2();
			
			PowerRating p = new PowerRating(cards);
			
			prs[i] = p;
			
			handPower.put(p, i);
		}
		
		PowerRating winner = PowerUtils.max(prs);
		for(int i=0; i<numPlayers; i++){
			if(prs[i].equals(winner)){
				winners[i] = true;
			}
		}
		handPower.clear();
		return winners;
	}

}
