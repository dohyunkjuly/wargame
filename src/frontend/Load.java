package frontend;

import game.*;
import javax.swing.*;
import java.awt.*;

public class Load extends JPanel {
	private JLabel title = new JLabel("Select a Saved File");
	private JButton backButton = new JButton("Back to Lobby");

	public Load(JFrame frame, Lobby lobbyPanel, Game game, Save save, JPanel gamePanel, JLabel messageLabel,
			Runnable onGameLoaded) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
		setBackground(new Color(53, 101, 77));

		title.setFont(new Font("Serif", Font.BOLD, 28));
		title.setForeground(Color.WHITE);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(title);
		add(Box.createVerticalStrut(30)); // Add 30 pixels to the next component

		String[] saves = save.listSaves();

		for (String saveName : saves) {
			JButton saveButton = new JButton(saveName) {
				/* ---------- rounded, gradient background ---------- */
				@Override
				protected void paintComponent(Graphics g) {
					Graphics2D g2 = (Graphics2D) g.create();
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

					GradientPaint gp = new GradientPaint(0, 0, new Color(255, 255, 255, 230), // lighter top
							0, getHeight(), new Color(225, 225, 225, 180)); // darker bottom
					g2.setPaint(gp);
					g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

					super.paintComponent(g); // draw the label text
					g2.dispose();
				}

				@Override
				protected void paintBorder(Graphics g) {
					Graphics2D g2 = (Graphics2D) g.create();
					g2.setColor(new Color(255, 255, 255, 180)); // soft outline
					g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
					g2.dispose();
				}
			};
			saveButton.setContentAreaFilled(false); // show our custom paint
			saveButton.setFocusPainted(false); // hide default focus box
			saveButton.setOpaque(false); // transparent background

			saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			saveButton.setMaximumSize(new Dimension(300, 40));
			saveButton.addActionListener(e -> {
				GameState state = save.loadGame(saveName);
				if (state != null) {
					game.loadFromState(state);
					frame.getContentPane().removeAll();
					frame.add(gamePanel);
					frame.revalidate();
					frame.repaint();
					messageLabel.setText("");
					gamePanel.repaint();
					onGameLoaded.run();
				} else {
					JOptionPane.showMessageDialog(frame, "Failed to load " + saveName);
				}
			});
			add(Box.createVerticalStrut(10)); // Add 10 pixels to the next component
			add(saveButton);
		}

		add(Box.createVerticalStrut(30));

		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		backButton.setMaximumSize(new Dimension(200, 40));
		backButton.setFont(new Font("Serif", Font.BOLD, 18));
		backButton.addActionListener(e -> {
			frame.getContentPane().removeAll();
			frame.add(lobbyPanel);
			frame.revalidate();
			frame.repaint();
		});

		add(Box.createVerticalGlue()); // Pushes everything above upwards
		add(backButton);
		add(Box.createVerticalStrut(50)); // 50 pixels of space at the bottom
	}
}
