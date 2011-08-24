package org.pcollections.tests;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.pcollections.ArrayPMap;
import org.pcollections.PMap;

public class ArrayPMapTest extends TestCase {
	private PMap<String, Integer> e = ArrayPMap.empty();
	private PMap<String, Integer> u = e.plus("a", 1).plus("b", 2).plus("c", 3)
			.plus("d", 4);;

	public ArrayPMapTest() {
	}

	public ArrayPMapTest(String name) {
		super(name);
	}

	public void testIsEmpty() {
		assertTrue(e.isEmpty());
		assertFalse(e.plus("a", 1).isEmpty());
		assertFalse(u.isEmpty());
		assertTrue(e.plus("a", 1).minus("a").isEmpty());
	}

	public void testSize() {
		assertEquals(0, e.size());
		assertEquals(4, u.size());
	}

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

	public void testGet() {
		assertNull(e.get("a"));
		assertEquals((Integer) 1, u.get("a"));
		assertEquals((Integer) 2, u.get("b"));
		assertEquals((Integer) 3, u.get("c"));
		assertEquals((Integer) 4, u.get("d"));
		assertNull(u.get("x"));
	}

	public void testMinus() {
		PMap<String, Integer> v = u.minus("a");
		assertEquals(3, v.size());
		assertFalse(v.containsKey("a"));
		assertNull(v.get("a"));
		assertEquals((Integer) 2, v.get("b"));
		assertEquals((Integer) 3, v.get("c"));
		assertEquals((Integer) 4, v.get("d"));
	}

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

	public void testIterator1() {
		try {
			Iterator<SimpleImmutableEntry<String, Integer>> i = ((ArrayPMap<String, Integer>) u)
					.iterator();
			i.remove();
			fail();
		} catch (UnsupportedOperationException e) {
			// is exception expected
		}
	}

	public void testMinusAll() {
		List<String> l = new ArrayList<String>();
		assertEquals(u, u.minusAll(l));
		assertEquals(e, e.minusAll(l));
		l.add("a");
		l.add("d");
		PMap<String, Integer> w = e.plus("b", 2).plus("c", 3);
		assertEquals(w, u.minusAll(l));
		l.add("b");
		l.add("c");
		assertEquals(e, u.minusAll(l));
		l.clear();
		l.add("x");
		assertEquals(u, u.minusAll(l));

	}

	public void testContainsKey() {
		assertFalse(e.containsKey("x"));
		assertFalse(u.containsKey("x"));
		assertTrue(u.containsKey("a"));
		assertTrue(u.containsKey("b"));
		assertTrue(u.containsKey("c"));
		assertTrue(u.containsKey("d"));
	}

	public void testContainsValue() {
		assertFalse(e.containsValue(99));
		assertFalse(u.containsValue(99));
		assertTrue(u.containsValue(1));
		assertTrue(u.containsValue(2));
		assertTrue(u.containsValue(3));
		assertTrue(u.containsValue(4));
	}

	public void testKeySet() {
		Set<String> k = e.keySet();
		assertTrue(k.isEmpty());
		k = u.keySet();
		assertEquals(4, k.size());
		HashSet<String> v = new HashSet<String>();
		v.add("a");
		v.add("b");
		v.add("c");
		v.add("d");
		assertEquals(v, k);
	}

	public void testValues() {
		Collection<Integer> l = e.values();
		assertTrue(l.isEmpty());

		l = u.values();
		Collection<Integer> v = new ArrayList<Integer>();
		v.add(1);
		v.add(2);
		v.add(3);
		v.add(4);
		assertEquals(v, l);
	}

	public void testEntrySet() {
		assertTrue(e.entrySet().isEmpty());

		Map<String, Integer> v = new HashMap<String, Integer>();
		v.put("a", 1);
		v.put("b", 2);
		v.put("c", 3);
		v.put("d", 4);
		assertEquals(v.entrySet(), u.entrySet());
	}

	public void testEquals() {
		assertFalse(u.equals(null));

		Map<String, Integer> v = new HashMap<String, Integer>();
		assertTrue(e.equals(v));

		v.put("a", 1);
		assertFalse(e.equals(v));
		assertFalse(u.equals(v));

		v.put("b", 2);
		v.put("c", 3);
		v.put("d", 4);
		assertFalse(e.equals(v));
		assertTrue(u.equals(v));

		PMap<String, Integer> w = e.plus("a", 1).plus("b", 2).plus("c", 3);
		assertFalse(w.equals(u));
		assertFalse(u.equals(w));
		w = w.plus("d", 4);
		assertTrue(w.equals(u));
		assertTrue(u.equals(w));
		w = w.plus("e", 5);
		assertFalse(w.equals(u));
		assertFalse(u.equals(w));
	}

	public void testHashCode() {
		assertEquals(0, ArrayPMap.empty().hashCode());
		PMap<String, Integer> v = e;
		v = v.plus("a", 1).plus("b", 2).plus("c", 3).plus("d", 4);
		assertEquals(u, v);
		assertEquals(v, u);
		assertEquals(u.hashCode(), v.hashCode());

		Map<String, Integer> w = new HashMap<String, Integer>();
		assertEquals(e, w);
		assertEquals(w, e);
		assertEquals(w.hashCode(), e.hashCode());

		w.put("a", 1);
		w.put("b", 2);
		w.put("c", 3);
		w.put("d", 4);
		assertEquals(u, w);
		assertEquals(w, u);
		assertEquals(w.hashCode(), u.hashCode());
	}
}
