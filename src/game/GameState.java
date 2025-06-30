package game;

import java.util.List;
import java.io.Serializable;

/**
 * GameState represents the current state of the game. This class is
 * Serializable, meaning it can be saved and loaded by deserialization
 */
public class GameState implements Serializable {

	// Required for Serializable interface to maintain compatibility during
	// deserialization
	private static final long serialVersionUID = 1L;

	/**
	 * The main deck of cards from which players draw.
	 */
	public List<Card> deck;

	/**
	 * The player's current hand of cards.
	 */
	public List<Card> playerHand;

	/**
	 * The computer's current hand of cards.
	 */
	public List<Card> computerHand;

	/**
	 * The last card played by the player.
	 */
	public Card lastPlayerCard;

	/**
	 * The last card played by the computer.
	 */
	public Card lastComputerCard;
}