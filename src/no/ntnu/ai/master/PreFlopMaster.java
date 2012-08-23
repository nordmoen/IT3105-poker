package no.ntnu.ai.master;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.deck.Deck;
import no.ntnu.ai.player.PokerHand;
import no.ntnu.ai.simulator.TestResult;

public class PreFlopMaster extends AbstractMaster{
	private final PokerHand mycards;
	private int numOtherPlayers;

	public PreFlopMaster(PokerHand mycards, Deck deck, int numPlayers) {
		super(deck, numPlayers);
		this.numOtherPlayers = numPlayers -1;
		this.mycards = mycards;
	}
	
	public PreFlopMaster(PokerHand mycards, Deck deck){
		this(mycards, deck, 1);
	}
	
	public boolean addPlayer(){
		if(numPlayers > 9){
			return false;
		}else{
			this.numPlayers++;
			this.numOtherPlayers++;
			return true;
		}
	}
	
	@Override
	public void dealCards(){
		Card[] cards = deck.dealCards(numOtherPlayers*2);
		for(int i=0; i<numOtherPlayers; i++){
			hands.add(new PokerHand(cards[i], cards[numOtherPlayers+i]));
		}
		this.dealtCards = true;
	}
	
	public TestResult simulate(int numSims) throws CloneNotSupportedException{
		int[] res = new int[3];
		for(int i=0; i<numSims; i++){
			this.hands.add(this.mycards);
			Deck dealDeck = (Deck) this.deck.clone();
			
			dealDeck.stdShuffle();
			dealCards();
			dealFlop();
			dealTurn();
			dealRiver();
			
			boolean[] result = this.declareWinner();
			boolean tie = false;
			if(result[0]){
				for(int j=1; j<numPlayers; j++){
					if(result[j]){
						tie = true;
					}
				}
				if(tie){
					res[1]++;
				}else{
					res[0]++;
				}
			}else{
				res[2]++;
			}
		}
		return new TestResult(res[0], res[1], res[2]);
	}
	
	
	

}
