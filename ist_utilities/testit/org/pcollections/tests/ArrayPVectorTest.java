package org.pcollections.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.pcollections.ArrayPVector;
import org.pcollections.PVector;

public class ArrayPVectorTest extends TestCase {
	private PVector<String> e = ArrayPVector.empty();
	private PVector<String> u = e.plus("a").plus("b").plus("c").plus("d");

	public ArrayPVectorTest() {
	}

	public ArrayPVectorTest(String name) {
		super(name);
	}

	public void testIsEmpty() {
		assertTrue(e.isEmpty());
		PVector<String> v = ArrayPVector.empty();
		assertEquals(0, v.size());
		assertTrue(v.isEmpty());
		assertFalse(v.plus("a").isEmpty());
		assertFalse(u.isEmpty());
	}

	public void testSize() {
		PVector<String> v = e;
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
		assertFalse(e.contains("a"));
		assertFalse(u.contains("x"));
		assertTrue(u.contains("a"));
		assertTrue(u.contains("b"));
		assertTrue(u.contains("c"));
		assertTrue(u.contains("d"));
	}

	public void testContainsAll() {
		List<String> l = new ArrayList<String>();
		assertTrue(u.containsAll(l));
		l.add("a");
		l.add("b");
		l.add("c");
		assertTrue(u.containsAll(l));
		l.add("x");
		assertFalse(u.containsAll(l));
		l.add("d");
		l.remove("x");
		assertTrue(u.containsAll(l));
	}

	public void testEquals() {
		List<String> v = new ArrayList<String>();
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

		PVector<String> w = e.plus("a").plus("b").plus("c").plus("d");
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
			assertTrue(i.hasNext());
			i.remove();
			fail();
		} catch (UnsupportedOperationException e) {
			// exception is expected
		}
	}

	public void testGet() {
		assertEquals("a", u.get(0));
		assertEquals("b", u.get(1));
		assertEquals("c", u.get(2));
		assertEquals("d", u.get(3));
	}

	public void testGet0() {
		try {
			e.get(0);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// exception is expected
		}
	}

	public void testGet1() {
		try {
			u.get(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// exception is expected
		}
	}

	public void testGet2() {
		try {
			u.get(4);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// exception is expected
		}
	}

	public void testIndexOf() {
		PVector<String> v = e;
		v = v.plus("a");
		v = v.plus("b");
		v = v.plus("c");
		v = v.plus("d");
		v = v.plus("a");
		v = v.plus("b");
		v = v.plus("c");
		v = v.plus("d");
		v = v.plus("x");
		assertEquals(-1, v.indexOf("y"));
		assertEquals(0, v.indexOf("a"));
		assertEquals(1, v.indexOf("b"));
		assertEquals(2, v.indexOf("c"));
		assertEquals(3, v.indexOf("d"));
		assertEquals(8, v.indexOf("x"));
	}

	public void testLastIndexOf() {
		PVector<String> v = ArrayPVector.empty();
		v = v.plus("x");
		v = v.plus("a");
		v = v.plus("b");
		v = v.plus("c");
		v = v.plus("d");
		v = v.plus("a");
		v = v.plus("b");
		v = v.plus("c");
		v = v.plus("d");
		assertEquals(-1, v.lastIndexOf("y"));
		assertEquals(5, v.lastIndexOf("a"));
		assertEquals(6, v.lastIndexOf("b"));
		assertEquals(7, v.lastIndexOf("c"));
		assertEquals(8, v.lastIndexOf("d"));
		assertEquals(0, v.lastIndexOf("x"));
	}

	public void testMinusObject() {
		PVector<String> v = ArrayPVector.empty();
		v = v.plus("x");
		v = v.plus("a");
		v = v.plus("b");
		v = v.plus("c");
		v = v.plus("d");
		v = v.plus("a");
		v = v.plus("b");
		v = v.plus("c");
		v = v.plus("d");
		assertEquals(9, v.size());
		PVector<String> o = v;
		v = v.minus("y");
		assertEquals(o, v);
		v = v.minus("x");
		assertEquals(8, v.size());
		assertFalse(v.contains("x"));
		assertEquals(0, v.indexOf("a"));
		v = v.minus("d");
		assertEquals(7, v.size());
		assertEquals(6, v.indexOf("d"));
		v = v.minus("d");
		assertEquals(6, v.size());
		assertFalse(v.contains("d"));
	}

	public void testMinusInt() {
		PVector<String> v = ArrayPVector.empty();
		v = v.plus("x");
		v = v.plus("a");
		v = v.plus("b");
		v = v.plus("c");
		v = v.plus("d");
		v = v.plus("a");
		v = v.plus("b");
		v = v.plus("c");
		v = v.plus("d");
		assertEquals(9, v.size());
		v = v.minus(4);
		assertEquals(8, v.size());
		assertEquals(7, v.indexOf("d"));
		v = v.minus(0);
		assertEquals(7, v.size());
		assertEquals(0, v.indexOf("a"));
		assertFalse(v.contains("x"));
		v = v.minus(6);
		assertEquals(6, v.size());
		assertFalse(v.contains("d"));
	}

	public void testPlusE() {
		PVector<String> v = ArrayPVector.empty();
		v = v.plus("a");
		assertEquals(1, v.size());
		assertEquals("a", v.get(0));
		v = v.plus("b");
		v = v.plus("c");
		v = v.plus("d");
		v = v.plus("a");
		assertEquals(5, v.size());
		assertEquals("a", v.get(0));
		assertEquals("b", v.get(1));
		assertEquals("c", v.get(2));
		assertEquals("d", v.get(3));
		assertEquals("a", v.get(4));
	}

	public void testPlusAllCollectionOfQextendsE() {
		PVector<String> v = ArrayPVector.empty();
		v = v.plusAll(u);
		assertEquals(4, v.size());
		assertEquals("a", v.get(0));
		assertEquals("b", v.get(1));
		assertEquals("c", v.get(2));
		assertEquals("d", v.get(3));
		v = v.plusAll(u);
		assertEquals(8, v.size());
		assertEquals("a", v.get(0));
		assertEquals("b", v.get(1));
		assertEquals("c", v.get(2));
		assertEquals("d", v.get(3));
		assertEquals("a", v.get(4));
		assertEquals("b", v.get(5));
		assertEquals("c", v.get(6));
		assertEquals("d", v.get(7));

		List<String> l = Arrays.asList();
		v = ArrayPVector.empty();
		v = v.plusAll(l);
		assertEquals(ArrayPVector.empty(), v);
	}

	public void testPlusAllIntCollectionOfQextendsE() {
		List<String> l = new ArrayList<String>();
		PVector<String> v = u.plusAll(0, l);
		assertEquals(u, v);
		v = e.plusAll(0, l);
		assertEquals(e, v);
		l.add("x");
		l.add("y");

		v = e.plusAll(0, l);
		assertEquals(2, v.size());
		assertEquals("x", v.get(0));
		assertEquals("y", v.get(1));

		v = u.plusAll(0, l);
		assertEquals(6, v.size());
		assertEquals("x", v.get(0));
		assertEquals("y", v.get(1));
		assertEquals("a", v.get(2));
		assertEquals("b", v.get(3));
		assertEquals("c", v.get(4));
		assertEquals("d", v.get(5));

		v = u.plusAll(u.size(), l);
		assertEquals(6, v.size());
		assertEquals("a", v.get(0));
		assertEquals("b", v.get(1));
		assertEquals("c", v.get(2));
		assertEquals("d", v.get(3));
		assertEquals("x", v.get(4));
		assertEquals("y", v.get(5));

		v = u.plusAll(2, l);
		assertEquals(6, v.size());
		assertEquals("a", v.get(0));
		assertEquals("b", v.get(1));
		assertEquals("x", v.get(2));
		assertEquals("y", v.get(3));
		assertEquals("c", v.get(4));
		assertEquals("d", v.get(5));
	}

	public void testPlusAllIntCollectionOfQextendsE1() {
		try {
			List<String> l = new ArrayList<String>();
			l.add("x");
			l.add("y");
			u.plusAll(-1, l);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// exception is expected
		}
	}

	public void testPlusAllIntCollectionOfQextendsE2() {
		try {
			List<String> l = new ArrayList<String>();
			l.add("x");
			l.add("y");
			u.plusAll(u.size() + 1, l);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// exception is expected
		}
	}

	public void testMinusAllCollectionOfQextendsE() {

		PVector<String> w = ArrayPVector.empty();
		w = w.plus("a").plus("a").plus("b").plus("d").plus("x");

		PVector<String> v = u.plusAll(u);
		assertEquals(8, v.size());

		v = v.minusAll(w);

		assertEquals(4, v.size());
		assertEquals("c", v.get(0));
		assertEquals("b", v.get(1));
		assertEquals("c", v.get(2));
		assertEquals("d", v.get(3));
	}

	public void testSubList() {
		PVector<String> v = ArrayPVector.empty();
		v = v.plus("x");
		v = v.plus("a");
		v = v.plus("b");
		v = v.plus("c");
		v = v.plus("d");
		v = v.plus("a");
		v = v.plus("b");
		v = v.plus("c");
		v = v.plus("d");

		PVector<String> s = v.subList(1, 5);
		assertEquals(u, s);

		s = s.plus("y");
		assertEquals(9, v.size());
		assertEquals(5, s.size());

		s = v.subList(0, 9);
		assertEquals(v, s);

		s = v.subList(4, 4);
		assertEquals(ArrayPVector.empty(), s);
	}

	public void testSubList0() {
		try {
			u = u.subList(-1, 3);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// exception is expected
		}
	}

	public void testSubList1() {
		try {
			u = u.subList(0, 5);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// exception is expected
		}
	}

	public void testSubList2() {
		try {
			u = u.subList(3, 2);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// exception is expected
		}
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

	public void testWith() {
		PVector<String> w = u.with(1, "x");

		assertEquals(4, u.size());
		assertEquals("a", u.get(0));
		assertEquals("b", u.get(1));
		assertEquals("c", u.get(2));
		assertEquals("d", u.get(3));

		assertEquals(4, w.size());
		assertEquals("a", w.get(0));
		assertEquals("x", w.get(1));
		assertEquals("c", w.get(2));
		assertEquals("d", w.get(3));
	}

	public void testWith0() {
		try {
			u = u.with(-1, "x");
			fail();
		} catch (IndexOutOfBoundsException e) {
			// exception is expected
		}
	}

	public void testWith1() {
		try {
			u = u.with(4, "x");
			fail();
		} catch (IndexOutOfBoundsException e) {
			// exception is expected
		}
	}

	public void testHashCode() {
		assertEquals(1, ArrayPVector.empty().hashCode());
		PVector<String> other = ArrayPVector.empty();
		other = other.plus("a").plus("b").plus("c").plus("d");
		assertTrue(u.equals(other));
		assertTrue(other.equals(u));
		assertEquals(u.hashCode(), other.hashCode());

		List<String> v = new ArrayList<String>();
		assertEquals(v, e);
		assertEquals(e, v);
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
