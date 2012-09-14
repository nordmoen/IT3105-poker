package no.ntnu.ai.opponent;

import no.ntnu.ai.deck.Card;
import no.ntnu.ai.player.AbstractPokerPlayer;
import no.ntnu.ai.player.Action;
import no.ntnu.ai.player.PokerPlayer;

public class PokerContext {

	private final Card[] cards;
	private final TablePlayerSize players;
	private final int numPlayers;
	private final Action action;
	private final PokerPlayer player;
	private final PotOddsSize potOdds;

	public PokerContext(PokerPlayer p, Card[] commCards, int totalNumPlayers, int numPlayers, Action act, int callAmount, int potSize){
		this.player = p;
		if(commCards != null){
			this.cards = commCards;
		}else{
			this.cards = new Card[0];
		}
		this.players = this.getPlayerBucket(numPlayers, totalNumPlayers);
		this.action = act;
		this.potOdds = this.getOddsBucket(callAmount / (double) (callAmount + potSize));
		this.numPlayers = numPlayers;
	}

	public Card[] getCards() {
		return cards;
	}

	private boolean isRound(int numCards){
		return cards.length == numCards;
	}

	public boolean isPreflop(){
		return isRound(0);
	}

	public boolean isPostflop(){
		return isRound(3);
	}

	public boolean isPostTurn(){
		return isRound(4);
	}

	public boolean isPostRiver(){
		return isRound(5);
	}

	public int getNumPlayers() {
		return numPlayers;
	}

	public Action getAction() {
		return action;
	}

	public PokerPlayer getPlayer() {
		return player;
	}

	public PotOddsSize getPotOdds() {
		return potOdds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + cards.length;
		result = prime * result + ((players == null) ? 0 : players.hashCode());
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result + ((potOdds == null) ? 0 : potOdds.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PokerContext other = (PokerContext) obj;
		if (action != other.action)
			return false;
		if (cards.length != other.cards.length) // We are only interested in which round it is
			return false;
		if (players != other.players)
			return false;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		if (potOdds != other.potOdds)
			return false;
		return true;
	}

	private PotOddsSize getOddsBucket(double odds){
		//We could add more types below, but it needs to be somewhat
		//general so I think we should do some testing
		if(odds >= 0.45){
			return PotOddsSize.SMALL;
		}else if(odds >= 0.25){
			return PotOddsSize.MEDIUM;
		}else{
			return PotOddsSize.LARGE;
		}
	}
	private TablePlayerSize getPlayerBucket(double numPlayers, double totalPlayers){
		double playerRatio = numPlayers/totalPlayers;
		if(playerRatio <= 0.34){
			return TablePlayerSize.FEW;
		}else if (playerRatio <= 0.75){
			return TablePlayerSize.NORMAL;
		}else{
			return TablePlayerSize.MANY;
		}
	}

	private String getRoundName(){
		if(this.isPreflop()){
			return "Preflop";
		}else if(this.isPostflop()){
			return "Postflop";
		}else if(this.isPostTurn()){
			return "Post turn";
		}else{
			return "Post river";
		}
	}

	@Override
	public String toString(){
		return ((AbstractPokerPlayer) this.player).getName() + ", Action: " + 
				this.action + ", Round: " + this.getRoundName() + 
				", Pot odds: " + this.potOdds + 
				", Number of players still in play: " + this.players;
	}

}
