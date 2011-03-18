package de.uni_koblenz.ist.utilities.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class ContextMenuListener extends MouseAdapter implements
		MouseListener {

	@Override
	public void mousePressed(MouseEvent e) {
		if (!SwingApplication.RUNS_ON_WINDOWS && e.isPopupTrigger()) {
			handleContextMenuClick(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (SwingApplication.RUNS_ON_WINDOWS && e.isPopupTrigger()) {
			handleContextMenuClick(e);
		}
	}

	public abstract void handleContextMenuClick(MouseEvent e);
}
