package de.uni_koblenz.ist.utilities.gui;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StatusBar extends JPanel {
	JLabel statusLabel;

	public StatusBar() {
		setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		setBorder(BorderFactory.createEmptyBorder(4, 0, 2, 0));
		statusLabel = new JLabel("Welcome");
		add(statusLabel);
		statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 8));

	}

	public void setText(String text) {
		statusLabel.setText(text);
	}
}
