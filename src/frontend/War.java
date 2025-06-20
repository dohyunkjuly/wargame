package frontend;

import game.Card;
import game.Game;
import game.GameState;
import game.Save;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class War {
	private int cardWidth = 132;
	private int cardHeight = 184;
	private Game game = new Game();
	private Save save = new Save();
    private JButton dealButton = new JButton("Deal");
	private JFrame frame = new JFrame();
	private JPanel buttonPanel = new JPanel();
	private JPanel gamePanel = new JPanel() {
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			try {
				Image backCard = new ImageIcon(getClass().getResource(getBackCardPath())).getImage();
                g.drawImage(backCard, 122, 100, cardWidth, cardHeight, null);
                g.drawImage(backCard, 310, 100, cardWidth, cardHeight, null);

				// Player
				Card playerLastCard = game.getLastPlayerCard();
	            String playerCardPath = (playerLastCard == null) ? getBackCardPath() : playerLastCard.getImagePath();	
                Image playCard = new ImageIcon(getClass().getResource(playerCardPath)).getImage();
                g.drawImage(playCard, 122, 150, cardWidth, cardHeight, null);
                
                // Computer
                Card computerLastCard = game.getLastComputerCard();
                String computerCardPath = (computerLastCard == null) ? getBackCardPath() : computerLastCard.getImagePath();
                Image computerCard = new ImageIcon(getClass().getResource(computerCardPath)).getImage();
                g.drawImage(computerCard, 310, 150, cardWidth, cardHeight, null);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	public String getBackCardPath() {
		return "/cards/BACK.png";
	}
	
	// Helper Function
	private void showDialog(String title, String content) {
	    JDialog dialog = new JDialog(frame, title, true);
	    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	    dialog.setSize(420, 300);
	    dialog.setLocationRelativeTo(frame);

	    // Create a panel with background color and padding
	    JPanel panel = new JPanel();
	    panel.setBackground(new Color(53, 101, 77)); // Light gray
	    panel.setLayout(new BorderLayout());
	    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

	    JLabel label = new JLabel("<html><body style='width:360px; font-family:Serif; font-size:13px; color:white;'>"
	            + content.replace("\n", "<br>")
	            + "</body></html>");

	    panel.add(label, BorderLayout.CENTER);
	    dialog.add(panel);

	    dialog.setVisible(true);
	}

	// ==== Frames and Panels =====
	public void startWarGame() {
		game.startGame();
		
		frame.setVisible(true);
		frame.setSize(600, 700);
		frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        gamePanel.setBackground(new Color(53, 101, 77));
        gamePanel.setLayout(new BorderLayout());
        
        
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(53, 101, 77)); 
        dealButton.setPreferredSize(new Dimension(200, 30)); 
        buttonPanel.add(dealButton);
        
        gamePanel.add(buttonPanel, BorderLayout.PAGE_END);
        // MenuBar
        JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		JMenu file = new JMenu("File");
		menuBar.add(file);
		JMenu edit = new JMenu("Edit");
		menuBar.add(edit);
		JMenu help = new JMenu("Help");
		menuBar.add(help);
		
	    JMenuItem howToPlay = new JMenuItem("How to Play");
	    JMenuItem projectDetails = new JMenuItem("Project Details");

	    help.add(howToPlay);
	    help.add(projectDetails);
	    
		dealButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Card comCard = game.drawComputerCard();
				Card plaCard = game.drawPlayerCard();
				// Sample Code
				if (comCard == null) {
					System.out.println("You Win");
				}
				else if(plaCard == null) {
					System.out.println("You Lose");
				}
                gamePanel.repaint();
			}
		});  
	    howToPlay.addActionListener(e -> {
	        showDialog("How to Play",
	            "WAR CARD GAME RULES:\n\n"
	            + "• Each player draws one card per round.\n"
	            + "• The player with the higher card wins the round.\n"
	            + "• If the cards are equal, a 'war' is triggered with more cards.\n"
	            + "• The game ends when one player owns all the cards."
	        );
	    });
	    projectDetails.addActionListener(e -> {
	        showDialog("Project Details",
	            "War Card Game Project\n\n"
	            + "Project Members:\n"
	            + "1. Dohyun Kim\n"
	            + "2. Zain\n"
	            + "3. Abdullah\n"
	            + "4. Mouaiz\n"
	            + "5. Youri\n"
	            + "6. Ha Jin\n"
	        );
	    });
	    frame.add(gamePanel);
	}
}
