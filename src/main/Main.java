package main;

import game.Game;
import game.GameState;
import game.Save;
import frontend.War;
public class Main {
    public static void main(String[] args) {
    	War warGame = new War();
    	warGame.startWarGame();
    	
        // Create game and initialize
        Game game = new Game();
        
        // Sample code for Saving the current state
        Save save = new Save();
        GameState state = game.saveCurrentState();
        save.saveGame(state);

        // List available saves
//        String[] files = save.listSaves();
//        for (int i = 0; i < files.length; i++) {
//            System.out.println(files[i]);
//        }
//        if (files.length == 0) {
//            System.out.println("No saves to load.");
//            return;
//        }
//        // Load the first save file (or let user choose one)
//        GameState loadedState = save.loadGame(files[0]);
//        // Create a new Game instance and populate it with loaded state
//        if (loadedState != null) {
//            Game game = new Game();
//            game.loadFromState(loadedState);
//            System.out.println("Loaded game:");
//            System.out.println("Player cards: " + game.getPlayerCardCount());
//            System.out.println("Computer cards: " + game.getComputerCardCount());
//            System.out.println("Last player card: " + game.getLastPlayerCard());
//            System.out.println("Last computer card: " + game.getLastComputerCard());
//        } else {
//            System.out.println("Failed to load game state.");
//        }
    }
}
