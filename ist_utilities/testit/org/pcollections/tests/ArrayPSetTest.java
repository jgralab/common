package org.pcollections.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.pcollections.ArrayPSet;
import org.pcollections.ArrayPVector;
import org.pcollections.PSet;
import org.pcollections.PVector;

public class ArrayPSetTest {
	private PSet<String> u;

	@Before
	public void testInit() {
		u = ArrayPSet.empty();
		u = u.plus("a").plus("b").plus("c").plus("d");
	}

	@Test
	public void testEmpty() {
		PSet<String> v = ArrayPSet.empty();
		assertEquals(0, v.size());
		assertTrue(v.isEmpty());
		assertFalse(v.plus("a").isEmpty());
		assertFalse(u.isEmpty());
	}

	@Test
	public void testSize() {
		PVector<String> v = ArrayPVector.empty();
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

	@Test
	public void testIsEmpty() {
		PVector<String> v = ArrayPVector.empty();
		assertTrue(v.isEmpty());
		v = v.plus("a");
		assertFalse(v.isEmpty());
	}

	@Test
	public void testContains() {
		assertFalse(u.contains("x"));
		assertTrue(u.contains("a"));
		assertTrue(u.contains("b"));
		assertTrue(u.contains("c"));
		assertTrue(u.contains("d"));
	}

	@Test
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

	@Test
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

	@Test
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

	@Test
	public void testToArray() {
		String[] v = { "a", "b", "c", "d" };
		String[] w = {};
		w = u.toArray(w);
		assertTrue(Arrays.equals(v, w));
	}

	@Test
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

	@Test
	public void testIterator() {
		PSet<String> e = ArrayPSet.empty();
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

	@Test(expected = UnsupportedOperationException.class)
	public void testIterator1() {
		Iterator<String> i = u.iterator();
		i.remove();
	}

	@Test
	public void testHashCode() {
		assertEquals(0, ArrayPSet.empty().hashCode());
		PSet<String> other = ArrayPSet.empty();
		other = other.plus("a").plus("b").plus("c").plus("d");
		assertTrue(u.equals(other));
		assertTrue(other.equals(u));
		assertEquals(u.hashCode(), other.hashCode());
	}
}
