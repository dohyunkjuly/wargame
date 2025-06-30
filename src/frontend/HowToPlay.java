package frontend;

import javax.swing.*;
import java.awt.*;

public class HowToPlay extends JPanel {
	private JButton backButton = new JButton("Back to Lobby");
	private JLabel title = new JLabel("How to Play");
	private JLabel imageLabel = new JLabel();

	public HowToPlay(JFrame frame, Lobby lobbyPanel) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(new Color(53, 101, 77));
		title.setFont(new Font("Serif", Font.BOLD, 28));
		title.setForeground(Color.WHITE);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);

		try {
			ImageIcon icon = new ImageIcon(getClass().getResource("/cards/howtoplay.png"));
			Image img = icon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
			imageLabel.setIcon(new ImageIcon(img));
		} catch (Exception e) {
			e.printStackTrace();
		}

		imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
		JTextArea rules = new JTextArea("WAR CARD GAME RULES:\n\n" + "• Each player draws one card in each round.\n"
				+ "• The higher card wins and takes both cards.\n" + "• If cards are equal, a 'war' begins.\n"
				+ "• In a war, each player places two cards face down.\n"
				+ "• Then places one card face up to battle.\n" + "• The higher face-up card wins all the cards.\n"
				+ "• Game ends when one player has all the cards.");

		rules.setFont(new Font("Serif", Font.PLAIN, 20));
		rules.setForeground(Color.WHITE);
		rules.setBackground(new Color(53, 101, 77));
		rules.setEditable(false);
		rules.setLineWrap(true);
		rules.setWrapStyleWord(true);
		rules.setAlignmentX(Component.CENTER_ALIGNMENT);
		rules.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		backButton.setMaximumSize(new Dimension(200, 40));
		backButton.setFont(new Font("Serif", Font.BOLD, 18));
		backButton.addActionListener(e -> {
			frame.getContentPane().removeAll();
			frame.add(lobbyPanel);
			frame.revalidate();
			frame.repaint();
		});

		add(Box.createVerticalStrut(20));
		add(title);
		add(Box.createVerticalStrut(10));
		add(imageLabel);
		add(Box.createVerticalStrut(5));
		add(rules);
		add(Box.createVerticalGlue()); // Pushes everything above upwards
		add(backButton);
		add(Box.createVerticalStrut(50)); // 50 pixels of space at the bottom
	}
}
