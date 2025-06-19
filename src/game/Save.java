package game;
import java.io.*;
import java.util.Arrays;
import java.util.Comparator;

public class Save {
    private static final String SAVE_DIR = "saves/";
    private static final int MAX_SAVES = 5;

    
    public Save() {
        File dir = new File(SAVE_DIR);
        if (!dir.exists()) dir.mkdir();
    }

    
    public void saveGame(GameState state) {
        File[] saveFiles = new File(SAVE_DIR).listFiles((d, name) -> name.endsWith(".dat"));

        if (saveFiles != null && saveFiles.length >= MAX_SAVES) {
            Arrays.sort(saveFiles, Comparator.comparingLong(File::lastModified));
            saveFiles[0].delete(); // delete the oldest
        }

        String fileName = "save_" + System.currentTimeMillis() + ".dat";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_DIR + fileName))) {
            out.writeObject(state);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] listSaves() {
        File dir = new File(SAVE_DIR);
        File[] saves = dir.listFiles((d, name) -> name.endsWith(".dat"));

        if (saves != null && saves.length > 0) {
            String[] fileNames = new String[saves.length];
            for (int i = 0; i < saves.length; i++) {
                fileNames[i] = saves[i].getName();
            }
            return fileNames;
        } else {
            return new String[0]; // Return empty array if no saves found
        }
    }

    public GameState loadGame(String filename) {
        File file = new File(SAVE_DIR + filename);
        if (!file.exists()) {
            System.out.println("Save not found.");
            return null;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (GameState) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}

