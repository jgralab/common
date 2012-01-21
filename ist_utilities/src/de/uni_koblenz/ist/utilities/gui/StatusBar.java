package de.uni_koblenz.ist.utilities.gui;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StatusBar extends JPanel {
	protected SwingApplication app;
	protected JLabel statusLabel;

	public StatusBar(SwingApplication app) {
		this.app = app;
		setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		setBorder(BorderFactory.createEmptyBorder(4, 0, 2, 0));
		statusLabel = new JLabel(app.getMessage(
				"Application.StatusBar.Greeting", "Welcome")); //$NON-NLS-1$  //$NON-NLS-2$
		add(statusLabel);
		statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 8));

	}

	public void setText(String text) {
		statusLabel.setText(text);
	}
}
