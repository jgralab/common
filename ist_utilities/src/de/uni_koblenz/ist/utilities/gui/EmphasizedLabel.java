package de.uni_koblenz.ist.utilities.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Window;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class EmphasizedLabel extends JLabel {
	private static final long serialVersionUID = 881191039458402594L;

	private boolean fUseEmphasisColor;

	public static final Color OS_X_EMPHASIZED_FONT_COLOR = new Color(255, 255,
			255, 110);
	public static final Color OS_X_EMPHASIZED_FOCUSED_FONT_COLOR = new Color(
			0x000000);
	public static final Color OS_X_EMPHASIZED_UNFOCUSED_FONT_COLOR = new Color(
			0x3f3f3f);

	public EmphasizedLabel(String text) {
		super(text);
	}

	@Override
	public Dimension getPreferredSize() {
		Dimension d = super.getPreferredSize();
		d.height += 1;
		return d;
	}

	@Override
	public Color getForeground() {
		Color retVal;
		Window window = SwingUtilities.getWindowAncestor(this);
		boolean hasFoucs = window != null && window.isFocused();

		if (fUseEmphasisColor) {
			retVal = OS_X_EMPHASIZED_FONT_COLOR;
		} else if (hasFoucs) {
			retVal = OS_X_EMPHASIZED_FOCUSED_FONT_COLOR;
		} else {
			retVal = OS_X_EMPHASIZED_UNFOCUSED_FONT_COLOR;
		}

		return retVal;
	}

	@Override
	protected void paintComponent(Graphics g) {
		fUseEmphasisColor = true;
		g.translate(0, 1);
		super.paintComponent(g);
		g.translate(0, -1);
		fUseEmphasisColor = false;
		super.paintComponent(g);
	}
}