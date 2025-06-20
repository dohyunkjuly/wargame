package game;

import java.util.List;
import java.io.Serializable;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
	public List<Card> deck;
    public List<Card> playerHand;
    public List<Card> computerHand;
    public Card lastPlayerCard;
    public Card lastComputerCard;
}
