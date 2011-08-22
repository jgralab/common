package org.pcollections.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.pcollections.ArrayPMap;

public class ArrayPMapTest {
	@Test
	public void hashCodeTest() {
		assertEquals(0, ArrayPMap.empty().hashCode());
	}
}
