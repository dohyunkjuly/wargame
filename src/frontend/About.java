package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class About extends JPanel {
	private JLabel title = new JLabel("About Us");
	private int cardWidth = 132;
	private int cardHeight = 184;
	private int x = 420; // Starting x position in terms of pixel for the cards display
	private int y = 40; // Starting y position in terms of pixel for the cards display
	private double angle = Math.toRadians(-15);
	private JButton backButton = new JButton("Back to Lobby");
	private String[] cardPaths = { "/cards/6-C.png", "/cards/8-H.png", "/cards/A-S.png", "/cards/10-D.png",
			"/cards/K-C.png", "/cards/2-H.png" };

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g.create();
		try {
			Image[] images = new Image[cardPaths.length];
			for (int i = 0; i < 6; i++) {
				// Load the 6 images
				images[i] = new ImageIcon(getClass().getResource(cardPaths[i])).getImage();
			}

			for (int i = 0; i < 6; i++) {
				// For odd 15 degrees & For even -15 degrees
				double currentAngle = (i % 2 == 0) ? -angle : angle;
				int currentY = y + i * 60; // 60 Pixels below every picture
				AffineTransform old = g2d.getTransform();
				g2d.translate(x + cardWidth / 2, currentY + cardHeight / 2); // Moves origin from (0,0) to x +cardWitdh
																				// /2 ....
				g2d.rotate(currentAngle);
				g2d.drawImage(images[i], -cardWidth / 2, -cardHeight / 2, cardWidth, cardHeight, null);
				// reset the transformation for the next loop
				g2d.setTransform(old);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			g2d.dispose();
		}
	}

	public About(JFrame frame, Lobby lobbyPanel) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(new Color(53, 101, 77));

		title.setFont(new Font("Serif", Font.BOLD, 36));
		title.setForeground(Color.WHITE);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);

		JTextArea info = new JTextArea(
				"PROJECT: War Card Game\n" + "UNIVERSITY: University of Europe\n" + "COURSE: Backend Programming A\n\n"
						+ "GROUP MEMBERS:\n" + "- Dohyun Kim\n" + "- Mohd Zainul Aabedeen Idrisi\n"
						+ "- Abdullah Rashid\n" + "- Ha Jin Lim\n" + "- Mouaz Kashif Shahzad\n" + "- Yuriy Rizoboyev");
		info.setFont(new Font("Serif", Font.PLAIN, 20));
		info.setForeground(Color.WHITE);
		info.setBackground(new Color(53, 101, 77));
		info.setEditable(false);
		info.setAlignmentX(Component.CENTER_ALIGNMENT);
		info.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
		info.setOpaque(false);

		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		backButton.setMaximumSize(new Dimension(200, 40));
		backButton.setFont(new Font("Serif", Font.BOLD, 18));
		backButton.addActionListener(e -> {
			frame.getContentPane().removeAll();
			frame.add(lobbyPanel);
			frame.revalidate();
			frame.repaint();
		});

		add(Box.createVerticalStrut(30));
		add(title);
		add(Box.createVerticalStrut(20));
		add(info);
		add(Box.createVerticalGlue());
		add(backButton);
		add(Box.createVerticalStrut(50));
	}
}