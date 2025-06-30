package frontend;

import game.Card;
import game.Game;
import game.GameState;
import game.Save;
import game.Audio;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import entities.RoundStatus;

public class War extends Base {
	private int cardWidth = 132;
	private int cardHeight = 184;
	private boolean isWar = false;
	private Game game = new Game();
	private Save save = new Save();
	private Audio audio = new Audio();
	private JFrame frame = new JFrame();
	private JButton dealButton = new JButton("Deal");
	// ==== MenuBar Components ====
	private JMenuBar menuBar = new JMenuBar();
	private JMenu file = new JMenu("File");
	private JMenu help = new JMenu("Help");
	private JMenu home = new JMenu("Home");
	private JMenuItem saveGame = new JMenuItem("Save Game");
	private JMenuItem loadGame = new JMenuItem("Load Game");
	private JMenuItem exitGame = new JMenuItem("Exit");
	private JMenuItem restartGame = new JMenuItem("Restart Game");
	private JMenuItem backToLobby = new JMenuItem("Back To Lobby");
	private JMenuItem about = new JMenuItem("About");
	// ==== MenuBar Components ====
	private Lobby lobbyPanel = new Lobby();
	private JPanel bottomPanel = new JPanel();
	private JLabel messageLabel = new JLabel("", SwingConstants.CENTER);
	private JPanel gamePanel = new JPanel() {
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			try {
				Image backCard = new ImageIcon(getClass().getResource(getBackCardPath())).getImage();
				g.drawImage(backCard, 122, 100, cardWidth, cardHeight, null);
				g.drawImage(backCard, 310, 100, cardWidth, cardHeight, null);

				// Score Board
				g.setFont(new Font("Serif", Font.BOLD, 18));
				g.setColor(Color.WHITE);

				g.drawString("Computer: " + game.getComputerCardCount(), 310, 90);
				g.drawString("Player: " + game.getPlayerCardCount(), 122, 90);
				if (isWar) {
					// Draw player war pile
					for (int i = 0; i < game.getPlayerWarPile().size(); i++) {
						Card card = game.getPlayerWarPile().get(i);
						String path;
						if (i % 3 == 0) {
							path = (card == null) ? getBackCardPath() : card.getImagePath();
						} else {
							path = getBackCardPath(); // face-down
						}
						Image img = new ImageIcon(getClass().getResource(path)).getImage();
						g.drawImage(img, 122, 125 + (i * 25), cardWidth, cardHeight, null);
					}
					// Draw computer war pile
					for (int i = 0; i < game.getComputerWarPile().size(); i++) {
						Card card = game.getComputerWarPile().get(i);
						String path;
						if (i % 3 == 0) {
							path = (card == null) ? getBackCardPath() : card.getImagePath();
						} else {
							path = getBackCardPath(); // face-down
						}
						Image img = new ImageIcon(getClass().getResource(path)).getImage();
						g.drawImage(img, 310, 125 + (i * 25), cardWidth, cardHeight, null);
					}
				} else {
					// Player
					Card playerLastCard = game.getLastPlayerCard();
					String playerCardPath = (playerLastCard == null) ? getBackCardPath()
							: playerLastCard.getImagePath();
					Image playCard = new ImageIcon(getClass().getResource(playerCardPath)).getImage();
					g.drawImage(playCard, 122, 125, cardWidth, cardHeight, null);

					// Computer
					Card computerLastCard = game.getLastComputerCard();
					String computerCardPath = (computerLastCard == null) ? getBackCardPath()
							: computerLastCard.getImagePath();
					Image computerCard = new ImageIcon(getClass().getResource(computerCardPath)).getImage();
					g.drawImage(computerCard, 310, 125, cardWidth, cardHeight, null);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	private void handleExitingGamePanel() {
		saveGame.setEnabled(false);
		loadGame.setEnabled(false);
		restartGame.setEnabled(false);
		backToLobby.setEnabled(false);
	}

	private void handleEnteringGamePanel() {
		saveGame.setEnabled(true);
		loadGame.setEnabled(true);
		restartGame.setEnabled(true);
		backToLobby.setEnabled(true);
		dealButton.setEnabled(true);
		saveGame.setEnabled(true);
	}

	@Override
	public void startGame() {
		frame.setVisible(true);
		frame.setSize(600, 700);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// First start the lobby
		frame.add(lobbyPanel);
		game.startGame();

		gamePanel.setBackground(new Color(53, 101, 77));
		gamePanel.setLayout(new BorderLayout());

		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
		bottomPanel.setBackground(new Color(53, 101, 77)); // Match game background

		// Center everything horizontally
		messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		dealButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		dealButton.setMaximumSize(new Dimension(200, 30)); // Set the size for deal button
		messageLabel.setForeground(new Color(255, 223, 186));
		messageLabel.setFont(new Font("Serif", Font.BOLD, 20));

		bottomPanel.add(messageLabel);
		bottomPanel.add(Box.createVerticalStrut(20)); // 20 pixels between message label and deal button
		bottomPanel.add(dealButton);
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0)); // top, left, bottom, right
		gamePanel.add(bottomPanel, BorderLayout.PAGE_END);

		// MenuBar
		frame.setJMenuBar(menuBar);
		menuBar.add(file);
		menuBar.add(home);
		menuBar.add(help);
		// file menu
		file.add(saveGame);
		file.add(loadGame);
		file.addSeparator();
		file.add(exitGame);
		// home menu
		home.add(restartGame);
		home.add(backToLobby);
		// help menu
		help.add(about);

		// Starting with the lobby page
		// Hence disable the following action from menu bar:
		// - saveGame
		// - loadGame
		// - restartGame
		// - backToLobby
		saveGame.setEnabled(false);
		loadGame.setEnabled(false);
		restartGame.setEnabled(false);
		backToLobby.setEnabled(false);

		lobbyPanel.getStartButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.remove(lobbyPanel);
				frame.add(gamePanel);
				frame.revalidate();
				frame.repaint();
				handleEnteringGamePanel();
			}
		});
		lobbyPanel.getAboutButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				About aboutPanel = new About(frame, lobbyPanel);
				frame.getContentPane().removeAll(); // Remove existing panels
				frame.add(aboutPanel);
				frame.revalidate();
				frame.repaint();
			}
		});
		lobbyPanel.getLoadButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] saves = save.listSaves();
				if (saves.length == 0) {
					JOptionPane.showMessageDialog(frame, "No saved games found.");
					return;
				}
				Load loadGamePanel = new Load(frame, lobbyPanel, game, save, gamePanel, messageLabel,
						() -> handleEnteringGamePanel());
				frame.getContentPane().removeAll();
				frame.add(loadGamePanel);
				dealButton.setEnabled(true);
				frame.revalidate();
				frame.repaint();
			}
		});
		lobbyPanel.getHowToPlayButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				HowToPlay howToPlayPanel = new HowToPlay(frame, lobbyPanel);
				frame.getContentPane().removeAll();
				frame.add(howToPlayPanel);
				frame.revalidate();
				frame.repaint();
			}
		});
		restartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.resetGame();
				dealButton.setEnabled(true); // For the case when restarting from game over
				saveGame.setEnabled(true); // For the case when restarting from game over
				gamePanel.repaint(); // Update UI to reflect changes
				messageLabel.setText(""); // reset the messageLabel
			}
		});
		backToLobby.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				messageLabel.setText(""); // reset the messageLabel
				game.resetGame();
				dealButton.setEnabled(true);
				frame.remove(gamePanel);
				frame.add(lobbyPanel);
				frame.revalidate();
				frame.repaint();
				handleExitingGamePanel();
			}
		});
		saveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameState state = game.saveCurrentState();
				save.saveGame(state);
				showDialog(frame, "Game Saved", "Game State has been successfully saved", 420, 100);
			}
		});
		loadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] saves = save.listSaves();

				if (saves.length == 0) {
					showDialog(frame, "Load Game", "No save files found. Please Save Game First", 420, 100);
					return;
				}
				// Let user choose from available saves
				String selectedSave = (String) JOptionPane.showInputDialog(frame, "Select a save file to load:",
						"Load Game", JOptionPane.PLAIN_MESSAGE, null, saves, saves[0]);

				if (selectedSave != null) {
					GameState loadedState = save.loadGame(selectedSave);
					if (loadedState != null) {
						game = new Game(); // Recreating the game instance)
						game.loadFromState(loadedState); // Update game from loaded data
						handleEnteringGamePanel();
						showDialog(frame, "Game Loaded", "Game state loaded successfully", 420, 100);
						messageLabel.setText(""); // reset the messageLabel
						gamePanel.repaint(); // Update UI to reflect changes
					} else {
						showDialog(frame, "Load Failed", "Failed to load the selected save file", 420, 100);
					}
				}
			}
		});
		// Exit
		exitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?",
						"Exit Confirmation", JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		dealButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				audio.playCardSound();

				Card comCard = game.drawComputerCard();
				Card plaCard = game.drawPlayerCard();

				int comVal = comCard.getNumericValue();
				int plaVal = plaCard.getNumericValue();

				if (plaVal > comVal) {
					game.addCardsToPlayer(plaCard, comCard);
					messageLabel.setText("You won the round!");
				} else if (comVal > plaVal) {
					game.addCardsToComputer(plaCard, comCard);
					messageLabel.setText("You lost the round!");
				} else {
					// WAR
					messageLabel.setText("WAR!");
					RoundStatus status = game.handleWar();
					isWar = true;
					switch (status) {
					case PLAYER_WINS -> {
						audio.playVictorySound();
						messageLabel.setText("Game Over You WIN!");
						dealButton.setEnabled(false);
						saveGame.setEnabled(false);
						gamePanel.repaint();
						showDialog(frame, "Game Over", "Computer ran out of cards. You WIN!", 420, 100);
						isWar = false; // Set it back to false
						return; // End the Game
					}
					case COMPUTER_WINS -> {
						audio.playGameOverSound();
						messageLabel.setText("Game Over You LOSE!");
						dealButton.setEnabled(false);
						saveGame.setEnabled(false);
						gamePanel.repaint();
						showDialog(frame, "Game Over", "You ran out of cards. You LOSE!", 420, 100);
						isWar = false; // Set it back to false
						return; // End the Game
					}
					case PLAYER_WINS_ROUND -> messageLabel.setText("You won the war!");
					case COMPUTER_WINS_ROUND -> messageLabel.setText("You lost the war!");
					}
				}
				// After Dealing the Card Check the Card count declare winner if there is one.
				if (game.getPlayerCardCount() < 1 || game.getComputerCardCount() < 1) {
					boolean playerLost = game.getPlayerCardCount() < 1;
					String text = playerLost ? "You LOSE!" : "You WIN!";
					if (playerLost == true) {
						audio.playGameOverSound();
					}
					if (playerLost == false) {
						audio.playVictorySound();
					}

					messageLabel.setText("Game Over " + text);
					dealButton.setEnabled(false);
					saveGame.setEnabled(false);
					gamePanel.repaint();
					showDialog(frame, "Game Over", text, 420, 100);
					return;
				}
				gamePanel.repaint();
				isWar = false; // After repainting set it back to false
			}
		});
		about.addActionListener(e -> {
			showDialog(frame, "About US", "Group Members:\n\n" + "• Dohyun Kim\n" + "• Mohd Zainul Abedeen Idrisi\n"
					+ "• Abdullah Rashid\n" + "• Yuriy Rizoboyev\n" + "• Ha Jin Lim\n" + "• Mouaz Kashif Shahzad\n",
					420, 320);
		});
	}
}
