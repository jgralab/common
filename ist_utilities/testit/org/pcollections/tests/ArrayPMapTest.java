package org.pcollections.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.pcollections.ArrayPMap;
import org.pcollections.PMap;

public class ArrayPMapTest {
	private PMap<String, Integer> u;
	private PMap<String, Integer> e;

	@Before
	public void testInit() {
		e = u = ArrayPMap.empty();
		u = u.plus("a", 1).plus("b", 2).plus("c", 3).plus("d", 4);
	}

	@Test
	public void testEmpty() {
		assertTrue(e.isEmpty());
		assertFalse(e.plus("a", 1).isEmpty());
		assertFalse(u.isEmpty());
		assertTrue(e.plus("a", 1).minus("a").isEmpty());
	}

	@Test
	public void testSize() {
		assertEquals(0, e.size());
		assertEquals(4, u.size());
	}

	@Test
	public void testPlus() {
		PMap<String, Integer> v = e.plus("a", 1);
		assertEquals(1, v.size());
		assertEquals((Integer) 1, v.get("a"));

		u = u.plus("e", 5);
		assertEquals(5, u.size());
		assertEquals((Integer) 1, u.get("a"));
		assertEquals((Integer) 2, u.get("b"));
		assertEquals((Integer) 3, u.get("c"));
		assertEquals((Integer) 4, u.get("d"));
		assertEquals((Integer) 5, u.get("e"));

		u = u.plus("c", 42);
		assertEquals(5, u.size());
		assertEquals((Integer) 1, u.get("a"));
		assertEquals((Integer) 2, u.get("b"));
		assertEquals((Integer) 42, u.get("c"));
		assertEquals((Integer) 4, u.get("d"));
		assertEquals((Integer) 5, u.get("e"));
	}

	@Test
	public void testGet() {
		assertNull(e.get("a"));
		assertEquals((Integer) 1, u.get("a"));
		assertEquals((Integer) 2, u.get("b"));
		assertEquals((Integer) 3, u.get("c"));
		assertEquals((Integer) 4, u.get("d"));
		assertNull(u.get("x"));
	}

	@Test
	public void testMinus() {
		PMap<String, Integer> v = u.minus("a");
		assertEquals(3, v.size());
		assertFalse(v.containsKey("a"));
		assertNull(v.get("a"));
		assertEquals((Integer) 2, v.get("b"));
		assertEquals((Integer) 3, v.get("c"));
		assertEquals((Integer) 4, v.get("d"));
	}

	@Test
	public void testIterator() {
		Iterator<SimpleImmutableEntry<String, Integer>> i = ((ArrayPMap<String, Integer>) e)
				.iterator();
		assertFalse(i.hasNext());
		i = ((ArrayPMap<String, Integer>) u).iterator();
		assertTrue(i.hasNext());
		SimpleImmutableEntry<String, Integer> x = i.next();
		assertEquals("a", x.getKey());
		assertEquals((Integer) 1, x.getValue());

		assertTrue(i.hasNext());
		x = i.next();
		assertEquals("b", x.getKey());
		assertEquals((Integer) 2, x.getValue());

		assertTrue(i.hasNext());
		x = i.next();
		assertEquals("c", x.getKey());
		assertEquals((Integer) 3, x.getValue());

		assertTrue(i.hasNext());
		x = i.next();
		assertEquals("d", x.getKey());
		assertEquals((Integer) 4, x.getValue());

		assertFalse(i.hasNext());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testIterator1() {
		Iterator<SimpleImmutableEntry<String, Integer>> i = ((ArrayPMap<String, Integer>) u)
				.iterator();
		i.remove();
	}

	@Test
	public void testHashCode() {
		assertEquals(0, ArrayPMap.empty().hashCode());
	}
}
