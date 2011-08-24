package org.pcollections.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.pcollections.ArrayPSet;
import org.pcollections.HashTreePSet;
import org.pcollections.PSet;
import org.pcollections.PVector;

public class ArrayPSetTest extends TestCase {
	private PSet<String> e = ArrayPSet.empty();
	private PSet<String> u = e.plus("a").plus("b").plus("c").plus("d");

	public ArrayPSetTest() {
	}

	public ArrayPSetTest(String name) {
		super(name);
	}

	public void testIsEmpty() {
		assertFalse(u.isEmpty());
		assertTrue(e.isEmpty());
		PSet<String> v = ArrayPSet.empty();
		assertEquals(0, v.size());
		assertTrue(v.isEmpty());
		assertFalse(v.plus("a").isEmpty());
	}

	public void testEquals() {
		HashSet<String> v = new HashSet<String>();
		assertTrue(e.equals(v));
		assertTrue(v.equals(e));
		v.add("a");
		v.add("b");
		v.add("c");
		v.add("d");
		assertTrue(v.equals(u));
		assertTrue(u.equals(v));
		assertFalse(u.equals(null));
		v.remove("b");
		assertFalse(v.equals(u));
		assertFalse(u.equals(v));

		PSet<String> w = HashTreePSet.empty();
		w = w.plus("a").plus("b").plus("c").plus("d");
		assertTrue(u.equals(w));
		assertTrue(w.equals(u));
		w = w.minus("a");
		assertFalse(u.equals(w));
		assertFalse(w.equals(u));

		w = e.plus("a").plus("b").plus("c").plus("d");
		assertTrue(u.equals(w));
		assertTrue(w.equals(u));
		w = w.minus("a");
		assertFalse(u.equals(w));
		assertFalse(w.equals(u));
	}

	public void testSize() {
		PSet<String> v = e;
		assertEquals(0, v.size());
		v = v.plus("a");
		assertEquals(1, v.size());
		v = v.plus("b");
		assertEquals(2, v.size());
		v = v.plus("c");
		assertEquals(3, v.size());
		v = v.plus("d");
		assertEquals(4, v.size());
	}

	public void testContains() {
		assertFalse(e.contains("x"));
		assertFalse(u.contains("x"));
		assertTrue(u.contains("a"));
		assertTrue(u.contains("b"));
		assertTrue(u.contains("c"));
		assertTrue(u.contains("d"));
	}

	public void testContainsAll() {
		List<String> l = new ArrayList<String>();
		assertTrue(e.containsAll(l));
		assertTrue(u.containsAll(l));
		l.add("a");
		assertFalse(e.containsAll(l));
		l.add("b");
		l.add("c");
		assertTrue(u.containsAll(l));
		l.add("x");
		assertFalse(u.containsAll(l));
		l.add("d");
		l.remove("x");
		assertTrue(u.containsAll(l));
	}

	public void testMinusObject() {
		PSet<String> v = ArrayPSet.empty();
		v = v.plus("x");
		v = v.plus("a");
		v = v.plus("b");
		v = v.plus("c");
		v = v.plus("d");
		v = v.plus("a");
		v = v.plus("b");
		v = v.plus("c");
		v = v.plus("d");
		assertEquals(5, v.size());
		PSet<String> o = v;
		v = v.minus("y");
		assertEquals(o, v);
		v = v.minus("x");
		assertEquals(4, v.size());
		assertFalse(v.contains("x"));
		v = v.minus("d");
		assertEquals(3, v.size());
		v = v.minus("d");
		assertEquals(3, v.size());
		assertFalse(v.contains("d"));
	}

	public void testMinus() {
		assertEquals(4, u.size());
		PSet<String> v = u.minus("x");
		assertEquals(u, v);
		v = v.minus("b");
		assertTrue(u.contains("b"));
		assertFalse(v.contains("b"));
		assertEquals(4, u.size());
		assertEquals(3, v.size());
		v = v.minus("a");
		assertFalse(v.contains("a"));
		assertEquals(2, v.size());
		v = v.minus("d");
		assertFalse(v.contains("d"));
		assertEquals(1, v.size());
		v = v.minus("c");
		assertFalse(v.contains("c"));
		assertEquals(0, v.size());
		assertTrue(v.equals(ArrayPSet.empty()));
	}

	public void testPlusAll() {
		PSet<String> v = ArrayPSet.empty();
		v = v.plus("a").plus("x").plus("y");
		u = u.plusAll(v);
		assertEquals(6, u.size());
		assertTrue(u.contains("a"));
		assertTrue(u.contains("b"));
		assertTrue(u.contains("c"));
		assertTrue(u.contains("d"));
		assertTrue(u.contains("x"));
		assertTrue(u.contains("y"));
	}

	public void testToArray() {
		String[] v = { "a", "b", "c", "d" };
		String[] w = {};
		w = u.toArray(w);
		assertTrue(Arrays.equals(v, w));
	}

	public void testToArray1() {
		String[] v = { "a", "b", "c", "d" };
		Object[] w = u.toArray();
		assertTrue(Arrays.equals(v, w));
	}

	public void testToPVector() {
		PVector<String> v = ((ArrayPSet<String>) e).toPVector();
		assertTrue(v.isEmpty());
		v = ((ArrayPSet<String>) u).toPVector();
		assertEquals(4, v.size());
		assertEquals("a", v.get(0));
		assertEquals("b", v.get(1));
		assertEquals("c", v.get(2));
		assertEquals("d", v.get(3));
	}

	public void testMinusAll() {
		PSet<String> v = ArrayPSet.empty();
		v = v.plus("a").plus("c").plus("y");
		u = u.minusAll(v);
		assertEquals(2, u.size());
		assertFalse(u.contains("a"));
		assertTrue(u.contains("b"));
		assertFalse(u.contains("c"));
		assertTrue(u.contains("d"));
		assertFalse(u.contains("y"));
	}

	public void testIterator() {
		Iterator<String> i = e.iterator();
		assertFalse(i.hasNext());
		i = u.iterator();
		assertTrue(i.hasNext());
		assertEquals("a", i.next());
		assertTrue(i.hasNext());
		assertEquals("b", i.next());
		assertTrue(i.hasNext());
		assertEquals("c", i.next());
		assertTrue(i.hasNext());
		assertEquals("d", i.next());
		assertFalse(i.hasNext());
	}

	public void testIterator1() {
		try {
			Iterator<String> i = u.iterator();
			i.remove();
			fail();
		} catch (UnsupportedOperationException e) {
			// exception is expected
		}
	}

	public void testHashCode() {
		assertEquals(0, ArrayPSet.empty().hashCode());
		PSet<String> other = ArrayPSet.empty();
		other = other.plus("a").plus("b").plus("c").plus("d");
		assertEquals(u, other);
		assertEquals(other, u);
		assertEquals(u.hashCode(), other.hashCode());

		HashSet<String> v = new HashSet<String>();
		assertEquals(e, v);
		assertEquals(v, e);
		assertEquals(v.hashCode(), e.hashCode());

		v.add("a");
		v.add("b");
		v.add("c");
		v.add("d");
		assertEquals(v, u);
		assertEquals(u, v);
		assertEquals(v.hashCode(), u.hashCode());
	}
}
