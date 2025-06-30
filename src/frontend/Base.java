package frontend;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Abstract base class for frontend views that provides utility methods such as
 * showing a dialog and accessing the back card image.
 */
public abstract class Base {
	protected abstract void startGame();

	protected String getBackCardPath() {
		return "/cards/BACK.png";
	}

	protected void showDialog(JFrame frame, String title, String content, int width, int height) {
		// Create a modal dialog tied to the given frame
		JDialog dialog = new JDialog(frame, title, true);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setSize(width, height);
		dialog.setLocationRelativeTo(frame); // Center on the parent frame

		// Create a panel with custom styling
		JPanel panel = new JPanel();
		panel.setBackground(new Color(53, 101, 77));
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Format content with HTML for text wrapping and styling
		JLabel label = new JLabel("<html><body style='width:360px; font-family:Serif; font-size:13px; color:white;'>"
				+ content.replace("\n", "<br>") + "</body></html>");
		// Add label to panel and panel to dialog
		panel.add(label, BorderLayout.CENTER);
		dialog.add(panel);
		dialog.setVisible(true);
	}
}
