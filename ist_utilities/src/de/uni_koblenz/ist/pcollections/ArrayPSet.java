package de.uni_koblenz.ist.pcollections;

import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.pcollections.PSet;
import org.pcollections.PVector;

public final class ArrayPSet<E> implements PSet<E>, Serializable {
	private static final long serialVersionUID = 5643294766821496614L;

	private static final int SIZELIMIT = 10;
	transient private SoftReference<HashSet<E>> entrySet;
	private PVector<E> entries;

	private ArrayPSet(PVector<E> entries) {
		this.entries = entries;
	}

	private static ArrayPSet<?> empty = new ArrayPSet<Object>(
			ArrayPVector.empty());

	@SuppressWarnings("unchecked")
	public static <T> ArrayPSet<T> empty() {
		return (ArrayPSet<T>) empty;
	}

	private HashSet<E> getEntrySet() {
		HashSet<E> es = null;
		if (entrySet != null) {
			es = entrySet.get();
		}
		if (es == null) {
			es = new HashSet<E>(this);
			entrySet = new SoftReference<HashSet<E>>(es);
		}
		return es;
	}

	@Override
	public int hashCode() {
		return entries.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj != null) {
			@SuppressWarnings("unchecked")
			ArrayPSet<? extends E> o = (ArrayPSet<? extends E>) obj;
			return o.entries.equals(entries);
		}
		return false;
	}

	@Override
	public boolean contains(Object o) {
		return size() <= SIZELIMIT ? entries.contains(o) : getEntrySet()
				.contains(o);
	}

	@Override
	public PSet<E> plus(E e) {
		return contains(e) ? this : new ArrayPSet<E>(entries.plus(e));
	}

	PSet<E> plusWithoutCheck(E e) {
		return new ArrayPSet<E>(entries.plus(e));
	}

	@Override
	public PSet<E> plusAll(Collection<? extends E> list) {
		if (list.isEmpty()) {
			return this;
		}
		PSet<E> r = this;
		for (E e : list) {
			r = r.plus(e);
		}
		return r;
	}

	@Override
	public PSet<E> minus(Object e) {
		return contains(e) ? minus(e) : this;
	}

	@Override
	public boolean add(E o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return entries.size();
	}

	@Override
	public boolean isEmpty() {
		return entries.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return entries.iterator();
	}

	@Override
	public Object[] toArray() {
		return entries.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return entries.toArray(a);
	}

	public PVector<E> toPVector() {
		return entries;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object o : c) {
			if (!contains(o)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public PSet<E> minusAll(Collection<?> list) {
		if (list.isEmpty()) {
			return this;
		}
		PSet<E> r = this;
		for (Object e : list) {
			r = r.minus(e);
		}
		return r;
	}

	@Override
	public String toString() {
		if (isEmpty()) {
			return "{}";
		}
		StringBuilder sb = new StringBuilder();
		String delim = "{";
		for (E e : entries) {
			sb.append(delim).append(e);
			delim = ", ";
		}
		return sb.append("}").toString();

	}
}