package game;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;

public class Save {
	private final String SAVE_DIR = "saves/";
	private final int MAX_SAVES = 5;

	public Save() {
		File dir = new File(SAVE_DIR);
		if (!dir.exists())
			dir.mkdir();
	}

	public void saveGame(GameState state) {
		File dir = new File(SAVE_DIR);
		File[] files = dir.listFiles((d, name) -> name.endsWith(".dat"));

		// Keep only the most recent (MAX_SAVES - 1) saves before adding new one
		if (files != null && files.length >= MAX_SAVES) {
			Arrays.sort(files, Comparator.comparingLong(File::lastModified));
			files[0].delete(); // delete the oldest file
		}

		// 2025-06-27 at 16-45-30
		String time = new SimpleDateFormat("yyyy-MM-dd 'at' HH-mm-ss").format(new java.util.Date());
		String fileName = time + ".dat";

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
		// Construct the full file path and load file
		File file = new File(SAVE_DIR + filename);
		if (!file.exists()) {
			System.out.println("Save not found.");
			return null;
		}
		// FileInputStream reads the raw bytes from a file
		// ObjectInputStream reads the raw bytes and make them as java objects
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
			// Deserialize the GameState object from the file
			return (GameState) in.readObject(); // Type Casting to GameState
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}