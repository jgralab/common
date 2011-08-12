package de.uni_koblenz.ist.collections;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

class SmallMap<K, V> implements Map<K, V> {
	// static class Pair<K, V> implements Map.Entry<K, V> {
	// K key;
	// V value;
	//
	// public Pair(K k, V v) {
	// key = k;
	// value = v;
	// }
	//
	// @Override
	// public K getKey() {
	// return key;
	// }
	//
	// @Override
	// public V getValue() {
	// return value;
	// }
	//
	// @Override
	// public V setValue(V value) {
	// V old = value;
	// this.value = value;
	// return old;
	// }
	// }

	K[] keys;
	V[] values;
	int size;

	public SmallMap() {
		this(10);
		// l = new ArrayList<Pair<K, V>>();
	}

	@SuppressWarnings("unchecked")
	public SmallMap(int n) {
		// l = new ArrayList<Pair<K, V>>(n);
		keys = (K[]) new Object[n];
		values = (V[]) new Object[n];
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean containsKey(Object key) {
		for (int i = 0; i < size; ++i) {
			if (keys[i].equals(key)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		for (int i = 0; i < size; ++i) {
			if (values[i].equals(value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public V get(Object key) {
		for (int i = 0; i < size; ++i) {
			if (keys[i].equals(key)) {
				return values[i];
			}
		}
		return null;
	}

	@Override
	public V put(K key, V value) {
		for (int i = 0; i < size; ++i) {
			if (keys[i].equals(key)) {
				V old = values[i];
				values[i] = value;
				return old;
			}
		}
		keys[size] = key;
		values[size] = value;
		++size;
		return null;
	}

	@Override
	public V remove(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
			put(e.getKey(), e.getValue());
		}
	}

	@Override
	public void clear() {
		size = 0;
	}

	@Override
	public Set<K> keySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<V> values() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException();
	}
}