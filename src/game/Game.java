package game;

import java.util.ArrayList;

public class Game {
	private ArrayList<Card> deck = new ArrayList<>();
	private ArrayList<Card> playerHand = new ArrayList<>();
	private ArrayList<Card> computerHand = new ArrayList<>();
    private Card lastPlayerCard = null;
    private Card lastComputerCard = null;
    
    public void startGame() {
    	// First Initialize Decks for both player and computer
        initializeDeck();
        shuffleDeck();
        initializeHands();
        
        // Initialize the hands for both player and computer
        //lastPlayerCard = playerHand.remove(0);
        //lastComputerCard = computerHand.remove(0);
    }
    
	public void initializeDeck() {
		deck.clear();
		String[] value = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] type = {"C", "D", "H", "S"};
        for(String v : value) {
            for (String s : type) {
                deck.add(new Card(v, s));
            }
        }
	}
	
	public void shuffleDeck() {
		for(int i = 0; i <deck.size(); i++ ) {
			Card temp = deck.get(i);
			int j = i + (int) (Math.random() * (deck.size() - i));
			deck.set(i, deck.get(j));
			deck.set(j, temp);
		}
	}
	
	public void initializeHands() {
		playerHand.clear();
		computerHand.clear();
		for(int i =0; i < deck.size(); i++) {
			if (i % 2 == 0) {
				playerHand.add(deck.get(i));
			}else {
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
    
    public Card getLastPlayerCard() {
        return lastPlayerCard;
    }

    public Card getLastComputerCard() {
        return lastComputerCard;
    }

    public int getPlayerCardCount() {
        return playerHand.size();
    }

    public int getComputerCardCount() {
        return computerHand.size();
    }

}
