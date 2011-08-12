package de.uni_koblenz.ist.pcollections;

import java.io.Serializable;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.pcollections.PMap;
import org.pcollections.PSet;
import org.pcollections.PVector;

public final class ArrayPMap<K, V> implements PMap<K, V>,
		Iterable<SimpleImmutableEntry<K, V>>, Serializable {
	private static final long serialVersionUID = -7101801297307300984L;

	private ArrayPSet<K> keys;
	private PVector<V> values;

	private ArrayPMap(PSet<K> keys, PVector<V> values) {
		this.keys = (ArrayPSet<K>) keys;
		this.values = values;
	}

	@Override
	public int hashCode() {
		return keys.hashCode() ^ values.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof ArrayPMap) {
			@SuppressWarnings("unchecked")
			ArrayPMap<? extends K, ? extends V> o = (ArrayPMap<? extends K, ? extends V>) obj;
			return o.keys.equals(keys) && o.values.equals(values);
		}
		return false;
	}

	private static ArrayPMap<?, ?> empty = new ArrayPMap<Object, Object>(
			ArrayPSet.empty(), ArrayPVector.empty());

	@SuppressWarnings("unchecked")
	public static <T, U> ArrayPMap<T, U> empty() {
		return (ArrayPMap<T, U>) empty;
	}

	@Override
	public int size() {
		return keys.size();
	}

	@Override
	public boolean isEmpty() {
		return keys.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return keys.contains(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return values.contains(value);
	}

	@Override
	public V get(Object key) {
		int i = keys.toPVector().indexOf(key);
		return (i >= 0) ? values.get(i) : null;
	}

	@Deprecated
	@Override
	public V put(K key, V value) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	@Override
	public V remove(Object key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<K> keySet() {
		return keys;
	}

	@Override
	public Collection<V> values() {
		return values;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		PSet<Map.Entry<K, V>> entries = ArrayPSet.empty();
		Iterator<V> val = values.iterator();
		for (K key : keys) {
			entries = entries.plus(new SimpleImmutableEntry<K, V>(key, val
					.next()));
		}
		return entries;
	}

	@Override
	public ArrayPMap<K, V> minus(Object key) {
		int i = keys.toPVector().indexOf(key);
		return i < 0 ? this : new ArrayPMap<K, V>(keys.minus(key),
				values.minus(i));
	}

	@Override
	public ArrayPMap<K, V> minusAll(Collection<?> l) {
		if (l.isEmpty()) {
			return this;
		}
		ArrayPMap<K, V> result = this;
		for (Object k : l) {
			result = result.minus(k);
		}
		return result;
	}

	@Override
	public ArrayPMap<K, V> plus(K key, V value) {
		int i = keys.toPVector().indexOf(key);
		if (i >= 0) {
			return new ArrayPMap<K, V>(keys, values.with(i, value));
		} else {
			return new ArrayPMap<K, V>(keys.plusWithoutCheck(key),
					values.plus(value));
		}
	}

	@Deprecated
	@Override
	public ArrayPMap<K, V> plusAll(Map<? extends K, ? extends V> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Iterator<V> val = values.iterator();
		String delim = "<";
		for (K key : keys) {
			sb.append(delim).append(key).append("=").append(val.next());
			delim = ", ";
		}
		return sb.append(">").toString();
	}

	@Override
	public Iterator<SimpleImmutableEntry<K, V>> iterator() {
		return new ArrayPMapIterator();
	}

	private final class ArrayPMapIterator implements
			Iterator<SimpleImmutableEntry<K, V>> {
		Iterator<K> ki = keys.iterator();
		Iterator<V> vi = values.iterator();

		@Override
		public boolean hasNext() {
			return ki.hasNext();
		}

		@Override
		public SimpleImmutableEntry<K, V> next() {
			return new SimpleImmutableEntry<K, V>(ki.next(), vi.next());
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}