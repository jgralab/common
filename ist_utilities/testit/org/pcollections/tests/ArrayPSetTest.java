package org.pcollections.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
	public void hashCodeTest() {
		assertEquals(0, ArrayPSet.empty().hashCode());
	}
}
