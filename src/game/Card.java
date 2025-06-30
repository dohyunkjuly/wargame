package game;

import java.io.Serializable;

public class Card implements Serializable {
	private static final long serialVersionUID = 1L;
	private String value; // A,J,Q,K,1,2,3 ...
	private String suit; // C,D,S,H

	public Card(String value, String suit) {
		this.value = value;
		this.suit = suit;
	}

	public String getImagePath() {
		return "/cards/" + toString() + ".png";
	}

	@Override
	public String toString() {
		return value + "-" + suit;
	}

	public int getNumericValue() {
		switch (value) {
		case "A":
			return 14;
		case "K":
			return 13;
		case "Q":
			return 12;
		case "J":
			return 11;
		default:
			return Integer.parseInt(value);
		}
	}
}
