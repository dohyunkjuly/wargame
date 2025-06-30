package game;

import java.util.ArrayList;
import java.util.List;

import entities.RoundStatus;

public class Game {
	private ArrayList<Card> deck = new ArrayList<>();
	private ArrayList<Card> playerHand = new ArrayList<>();
	private ArrayList<Card> computerHand = new ArrayList<>();
	private ArrayList<Card> playerWarPile = new ArrayList<>();
	private ArrayList<Card> computerWarPile = new ArrayList<>();

	private Card lastPlayerCard = null;
	private Card lastComputerCard = null;

	public void resetGame() {
		deck.clear();
		playerHand.clear();
		computerHand.clear();
		playerWarPile.clear();
		computerWarPile.clear();
		lastPlayerCard = null;
		lastComputerCard = null;
		startGame();
	}

	public void startGame() {
		// First Initialize Decks for both player and computer
		initializeDeck();
		shuffleDeck();
		initializeHands();
	}

	public void initializeDeck() {
		deck.clear();
		String[] value = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
		String[] type = { "C", "D", "H", "S" };
		for (String v : value) {
			for (String s : type) {
				deck.add(new Card(v, s));
			}
		}
	}

	public void shuffleDeck() {
		for (int i = 0; i < deck.size(); i++) {
			Card temp = deck.get(i);
			int j = i + (int) (Math.random() * (deck.size() - i));
			deck.set(i, deck.get(j));
			deck.set(j, temp);
		}
	}

	public void initializeHands() {
		playerHand.clear();
		computerHand.clear();
		for (int i = 0; i < deck.size(); i++) {
			if (i % 2 == 0) {
				playerHand.add(deck.get(i));
			} else {
				computerHand.add(deck.get(i));
			}
		}
	}

	public Card drawPlayerCard() {
		if (!playerHand.isEmpty()) {
			lastPlayerCard = playerHand.remove(0);
			return lastPlayerCard;
		}
		return null; // Means computer wins
	}

	public Card drawComputerCard() {
		if (!computerHand.isEmpty()) {
			lastComputerCard = computerHand.remove(0);
			return lastComputerCard;
		}
		return null; // Means player wins
	}

	public void loadFromState(GameState state) {
		this.deck = new ArrayList<>(state.deck);
		this.playerHand = new ArrayList<>(state.playerHand);
		this.computerHand = new ArrayList<>(state.computerHand);
		this.lastPlayerCard = state.lastPlayerCard;
		this.lastComputerCard = state.lastComputerCard;
	}

	public GameState saveCurrentState() {
		GameState state = new GameState();
		state.deck = new ArrayList<>(this.deck);
		state.playerHand = new ArrayList<>(this.playerHand);
		state.computerHand = new ArrayList<>(this.computerHand);
		state.lastPlayerCard = this.lastPlayerCard;
		state.lastComputerCard = this.lastComputerCard;
		return state;
	}

	public RoundStatus handleWar() {
		playerWarPile.clear();
		computerWarPile.clear();
		// Start war with the last tied cards
		playerWarPile.add(this.lastPlayerCard);
		computerWarPile.add(this.lastComputerCard);

		while (true) {
			if (getPlayerCardCount() < 3)
				return RoundStatus.COMPUTER_WINS;
			if (getComputerCardCount() < 3)
				return RoundStatus.PLAYER_WINS;

			// Each player places 2 face-down cards
			for (int i = 0; i < 2; i++) {
				playerWarPile.add(drawPlayerCard());
				computerWarPile.add(drawComputerCard());
			}

			// One face-up card each
			Card playerFaceUp = drawPlayerCard();
			Card computerFaceUp = drawComputerCard();

			playerWarPile.add(playerFaceUp);
			computerWarPile.add(computerFaceUp);

			setLastPlayerCard(playerFaceUp);
			setLastComputerCard(computerFaceUp);

			int playerValue = playerFaceUp.getNumericValue();
			int computerValue = computerFaceUp.getNumericValue();

			if (playerValue > computerValue) {
				addCardsToPlayer(playerWarPile); // add all cards to player
				addCardsToPlayer(computerWarPile);
				break;
			} else if (computerValue > playerValue) {
				addCardsToComputer(playerWarPile); // add all cards to computer
				addCardsToComputer(computerWarPile);
				break;
			} else {
				// Tie again — continue the war
				continue;
			}
		}
		return (lastPlayerCard.getNumericValue() > lastComputerCard.getNumericValue()) ? RoundStatus.PLAYER_WINS_ROUND
				: RoundStatus.COMPUTER_WINS_ROUND;
	}

	public void addCardsToPlayer(Card playerCard, Card computerCard) {
		playerHand.add(playerCard);
		playerHand.add(computerCard);
	}

	public void addCardsToComputer(Card playerCard, Card computerCard) {
		computerHand.add(playerCard);
		computerHand.add(computerCard);
	}

	public void addCardsToPlayer(Card card) {
		playerHand.add(card);
	}

	public void addCardsToComputer(Card card) {
		computerHand.add(card);
	}

	public void addCardsToPlayer(List<Card> cards) {
		playerHand.addAll(cards);
	}

	public void addCardsToComputer(List<Card> cards) {
		computerHand.addAll(cards);
	}

	public void setLastPlayerCard(Card card) {
		this.lastPlayerCard = card;
	}

	public void setLastComputerCard(Card card) {
		this.lastComputerCard = card;
	}

	public Card getLastPlayerCard() {
		return lastPlayerCard;
	}

	public Card getLastComputerCard() {
		return lastComputerCard;
	}

	public ArrayList<Card> getComputerWarPile() {
		return computerWarPile;
	}

	public ArrayList<Card> getPlayerWarPile() {
		return playerWarPile;
	}

	public int getPlayerCardCount() {
		return playerHand.size();
	}

	public int getComputerCardCount() {
		return computerHand.size();
	}

}
