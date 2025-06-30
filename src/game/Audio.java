package game;

import javax.sound.sampled.*;

import java.io.BufferedInputStream;
import java.io.InputStream;

public class Audio {
	public void playCardSound() {
		playSound("/audio/cardsound.wav");
	}

	public void playVictorySound() {
		playSound("/audio/victorysound.wav");
	}

	public void playGameOverSound() {
		playSound("/audio/gameoversound.wav");
	}

	private void playSound(String path) {
		try {
			// Load the sound file as a stream from the path
			InputStream audioSrc = getClass().getResourceAsStream(path);
			if (audioSrc == null) {
				System.out.println("Sound file not found!");
				return;
			}
			// Add buffering to input stream
			InputStream bufferedIn = new BufferedInputStream(audioSrc);

			// Convert the buffered stream into an AudioInputStream for playback
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);

			// Obtain a Clip object capable of playing back audio
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
