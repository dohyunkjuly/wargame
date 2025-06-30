package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;

public class Lobby extends JPanel {
	private JButton startButton = new JButton("Start Game");
	private JButton loadButton = new JButton("Load Game");
	private JButton aboutButton = new JButton("About Us");
	private JButton howToPlayButton = new JButton("How to Play");

	private Image[] cardImages;
	private final int cardWidth = 110;
	private final int cardHeight = 130;

	public Lobby() {
		setBackground(new Color(53, 101, 77));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JLabel title = new JLabel("WAR CARD GAME");
		title.setFont(new Font("Georgia", Font.BOLD, 36));
		title.setForeground(Color.WHITE);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);

		startButton = createButton("Start Game");
		loadButton = createButton("Load Game");
		aboutButton = createButton("About Us");
		howToPlayButton = createButton("How To Play");

		add(Box.createVerticalGlue());
		add(title);
		add(Box.createRigidArea(new Dimension(0, 40)));
		add(startButton);
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(loadButton);
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(aboutButton);
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(howToPlayButton);
		add(Box.createVerticalGlue());

		loadCardImages();
	}

	private void loadCardImages() {
		String[] cardPaths = { "/cards/6-C.png", "/cards/8-H.png", "/cards/A-S.png", "/cards/10-D.png",
				"/cards/K-C.png", "/cards/BACK.png", "/cards/J-C.png", "/cards/9-D.png", "/cards/Q-H.png" };
		cardImages = new Image[cardPaths.length];
		for (int i = 0; i < cardPaths.length; i++) {
			cardImages[i] = new ImageIcon(getClass().getResource(cardPaths[i])).getImage();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (cardImages == null)
			return;

		Graphics2D g2d = (Graphics2D) g.create();
		// Tops
		drawCard(g2d, 13, 17, -15, cardImages[0]);
		drawCard(g2d, 130, 20, -15, cardImages[1]);
		drawCard(g2d, 334, 0, 10, cardImages[2]);
		drawCard(g2d, 367, 5, -5, cardImages[8]);
		drawCard(g2d, 423, 2, -3, cardImages[6]);
		drawCard(g2d, 90, 7, 7, cardImages[5]);
		drawCard(g2d, 320, 13, 15, cardImages[2]);
		drawCard(g2d, 200, 15, 10, cardImages[1]);
		drawCard(g2d, 260, 16, 6, cardImages[6]);
		drawCard(g2d, 23, 20, 1, cardImages[8]);
		drawCard(g2d, 230, 12, 3, cardImages[5]);
		drawCard(g2d, 190, 8, 0, cardImages[3]);
		drawCard(g2d, 386, 5, -6, cardImages[2]);
		drawCard(g2d, 53, 7, -13, cardImages[6]);
		drawCard(g2d, 450, 12, -10, cardImages[3]);
		// Bottoms
		drawCard(g2d, 53, getHeight() - 143, 12, cardImages[4]);
		drawCard(g2d, 220, getHeight() - 140, -15, cardImages[5]);
		drawCard(g2d, 340, getHeight() - 135, 8, cardImages[6]);
		drawCard(g2d, 180, getHeight() - 130, -7, cardImages[0]);
		drawCard(g2d, 23, getHeight() - 145, 10, cardImages[1]);
		drawCard(g2d, 360, getHeight() - 138, -12, cardImages[2]);
		drawCard(g2d, 120, getHeight() - 150, 6, cardImages[3]);
		drawCard(g2d, 432, getHeight() - 145, -9, cardImages[8]);
		drawCard(g2d, 12, getHeight() - 150, -3, cardImages[2]);
		drawCard(g2d, 394, getHeight() - 148, 5, cardImages[3]);
		drawCard(g2d, 293, getHeight() - 145, 10, cardImages[0]);
		drawCard(g2d, 321, getHeight() - 150, 2, cardImages[1]);
		drawCard(g2d, 363, getHeight() - 143, 0, cardImages[5]);
		drawCard(g2d, 469, getHeight() - 130, 12, cardImages[6]);
		drawCard(g2d, 63, getHeight() - 150, -15, cardImages[3]);

		g2d.dispose();
	}

	private void drawCard(Graphics2D g2d, int x, int y, int angleDeg, Image img) {
		if (img == null)
			return;

		AffineTransform old = g2d.getTransform();
		g2d.translate(x + cardWidth / 2, y + cardHeight / 2);
		g2d.rotate(Math.toRadians(angleDeg));
		g2d.drawImage(img, -cardWidth / 2, -cardHeight / 2, cardWidth, cardHeight, null);
		g2d.setTransform(old);
	}

	private JButton createButton(String text) {
		JButton button = new JButton(text);
		button.setFont(new Font("Serif", Font.PLAIN, 16));
		button.setFocusPainted(false);
		button.setForeground(Color.BLACK);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setMaximumSize(new Dimension(200, 40));
		return button;
	}

	// === Getters ====
	public JButton getStartButton() {
		return startButton;
	}

	public JButton getLoadButton() {
		return loadButton;
	}

	public JButton getAboutButton() {
		return aboutButton;
	}

	public JButton getHowToPlayButton() {
		return howToPlayButton;
	}
}
