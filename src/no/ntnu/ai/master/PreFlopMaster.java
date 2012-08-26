package no.ntnu.ai.master;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.deck.Deck;
import no.ntnu.ai.player.PokerHand;
import no.ntnu.ai.simulator.TestResult;

public class PreFlopMaster extends AbstractMaster{
	private final PokerHand mycards;
	private int numOtherPlayers;

	public PreFlopMaster(PokerHand mycards, int numPlayers) {
		super(numPlayers);
		this.numOtherPlayers = numPlayers -1;
		this.mycards = mycards;
	}

	public PreFlopMaster(PokerHand mycards){
		this(mycards, 1);
	}

	public void addPlayer(){
		if(numPlayers > 9){
			throw new IllegalStateException("To many players!");
		}
		this.numPlayers += 1;
		this.numOtherPlayers += 1;
	}

	@Override
	public void dealCards(Deck deck1){
		Card[] cards = deck1.dealCards(numOtherPlayers*2);
		for(int i=0; i<numOtherPlayers; i++){
			hands.add(new PokerHand(cards[i], cards[numOtherPlayers+i]));
		}
		this.dealtCards = true;
	}

	public TestResult simulate(int numSims, Deck deck) throws CloneNotSupportedException{
		int[] res = new int[3];
		for(int i=0; i<numSims; i++){
			this.hands.add(this.mycards);
			Deck dealDeck = (Deck) deck.clone();
			dealDeck.stdShuffle();

			dealCards(dealDeck);
			dealFlop(dealDeck);
			dealTurn(dealDeck);
			dealRiver(dealDeck);

			boolean[] result = this.declareWinner();
			boolean tie = false;
			if(result[0]){
				for(int j=1; j<numPlayers && !tie; j++){ //Stop to loop once we get a tie
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
			this.hands.clear();
		}
		return new TestResult(res[0], res[1], res[2]);
	}




}
