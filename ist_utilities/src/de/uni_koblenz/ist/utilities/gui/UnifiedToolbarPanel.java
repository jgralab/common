package de.uni_koblenz.ist.utilities.gui;

import java.awt.Color;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class UnifiedToolbarPanel extends JPanel {
	private static final long serialVersionUID = 1825519489284594986L;

	public static final Color OS_X_UNIFIED_TOOLBAR_FOCUSED_BOTTOM_COLOR = new Color(
			64, 64, 64);

	public static final Color OS_X_UNIFIED_TOOLBAR_UNFOCUSED_BORDER_COLOR = new Color(
			135, 135, 135);

	public UnifiedToolbarPanel() {
		// make the component transparent
		setOpaque(false);
		// create an empty border around the panel
		// note the border below is created using JGoodies Forms
		setBorder(BorderFactory.createEmptyBorder(3, 3, 1, 3));
	}

	@Override
	public Border getBorder() {
		Window window = SwingUtilities.getWindowAncestor(this);
		return window != null && window.isFocused() ? BorderFactory
				.createMatteBorder(0, 0, 1, 0,
						OS_X_UNIFIED_TOOLBAR_FOCUSED_BOTTOM_COLOR)
				: BorderFactory.createMatteBorder(0, 0, 1, 0,
						OS_X_UNIFIED_TOOLBAR_UNFOCUSED_BORDER_COLOR);
	}
}