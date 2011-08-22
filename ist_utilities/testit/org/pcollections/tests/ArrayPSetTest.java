package org.pcollections.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.pcollections.ArrayPSet;

public class ArrayPSetTest {
	@Test
	public void hashCodeTest() {
		assertEquals(0, ArrayPSet.empty().hashCode());
	}
}
