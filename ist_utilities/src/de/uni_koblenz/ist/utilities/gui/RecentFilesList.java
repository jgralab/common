package de.uni_koblenz.ist.utilities.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public abstract class RecentFilesList {
	private ArrayList<String> filenames;
	private int maxEntries;
	private Preferences prefs;
	private String key;
	private JMenu menu;

	public RecentFilesList(Preferences prefs, String key, int maxEntries,
			JMenu menu) {
		this.prefs = prefs;
		this.key = key;
		this.maxEntries = maxEntries;
		this.menu = menu;
		filenames = new ArrayList<String>(maxEntries);
		for (int n = 0; n < maxEntries; ++n) {
			String s = prefs.get(key + n, null);
			if (s == null) {
				break;
			}
			filenames.add(s);
		}
		updateMenu();
	}

	public void rememberFile(File f) {
		if (f == null) {
			return;
		}
		try {
			String name = f.getCanonicalPath();
			int i = filenames.indexOf(name);
			if (i == 0) {
				return;
			}
			if (i > 0) {
				filenames.remove(i);
			}
			while (filenames.size() >= maxEntries) {
				filenames.remove(filenames.size() - 1);
			}
			filenames.add(0, name);
			updateMenu();
			save();
		} catch (IOException e) {
		}
	}

	private void save() {
		int n = 0;
		while (n < filenames.size()) {
			prefs.put(key + n, filenames.get(n));
			++n;
		}
		while (prefs.get(key + n, null) != null) {
			prefs.remove(key + n);
			++n;
		}
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
		}
	}

	public void clear() {
		filenames.clear();
		updateMenu();
		save();
	}

	private void updateMenu() {
		while (menu.getItemCount() > 2) {
			menu.remove(menu.getItem(0));
		}
		for (int n = 0; n < filenames.size(); ++n) {
			menu.add(new RecentMenuItem(filenames.get(n)), n);
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
