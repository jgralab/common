package de.uni_koblenz.ist.utilities.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public abstract class RecentFilesList extends StringListPreferences {
	private int maxEntries;
	private JMenu menu;
	private int initialItemCount;

	public RecentFilesList(Preferences prefs, String key, int maxEntries,
			JMenu menu) {
		super(prefs, key);
		this.maxEntries = maxEntries;
		this.menu = menu;
		initialItemCount = menu.getItemCount();
		load(maxEntries);
		updateMenu();
	}

	public void rememberFile(File f) {
		if (f == null) {
			return;
		}
		try {
			String name = f.getCanonicalPath();
			int i = entries.indexOf(name);
			if (i == 0) {
				return;
			}
			if (i > 0) {
				entries.remove(i);
			}
			while (entries.size() >= maxEntries) {
				entries.remove(size() - 1);
			}
			entries.add(0, name);
			updateMenu();
			save();
		} catch (IOException e) {
		}
	}

	private void updateMenu() {
		while (menu.getItemCount() > initialItemCount) {
			menu.remove(menu.getItem(0));
		}
		for (int n = 0; n < entries.size(); ++n) {
			menu.add(new RecentMenuItem(entries.get(n)), n);
		}
	}

	private class RecentMenuItem extends JMenuItem {
		private static final long serialVersionUID = 6317371073043983226L;
		String filename;

		public RecentMenuItem(String n) {
			super(n);
			filename = n;
			addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					openRecentFile(new File(filename));
				}
			});
		}
	}

	public abstract void openRecentFile(File file);
}
