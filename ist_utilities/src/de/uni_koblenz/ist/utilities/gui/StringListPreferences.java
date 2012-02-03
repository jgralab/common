package de.uni_koblenz.ist.utilities.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class StringListPreferences {
	protected ArrayList<String> entries;
	private Preferences prefs;
	private String key;

	public StringListPreferences(Preferences prefs, String key) {
		this.prefs = prefs;
		this.key = key;
	}

	public void setEntries(List<String> l) {
		entries.clear();
		entries.addAll(l);
		save();
	}

	public List<String> getEntries() {
		return Collections.unmodifiableList(entries);
	}

	public int size() {
		return entries.size();
	}

	public String get(int index) {
		return entries.get(index);
	}

	public boolean contains(String s) {
		return entries.contains(s);
	}

	public int indexOf(String s) {
		return entries.indexOf(s);
	}

	public void sort() {
		Collections.sort(entries);
		save();
	}

	public void add(int index, String s) {
		entries.add(index, s);
		save();
	}

	public void add(String s) {
		entries.add(s);
		save();
	}

	public void remove(String s) {
		while (entries.remove(s)) {
		}
		save();
	}

	public String remove(int index) {
		String s = entries.remove(index);
		save();
		return s;
	}

	public void clear() {
		entries.clear();
		save();
	}

	public void save() {
		int n = 0;
		while (n < entries.size()) {
			prefs.put(key + n, entries.get(n));
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

	public void load(int maxEntries) {
		entries = new ArrayList<String>();
		for (int n = 0; n < maxEntries; ++n) {
			String s = prefs.get(key + n, null);
			if (s == null) {
				break;
			}
			entries.add(s);
		}
	}

	public void load() {
		load(Integer.MAX_VALUE);
	}
}
