package de.uni_koblenz.minimaltest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import de.uni_koblenz.minimal.HelloWorld;
import de.uni_koblenz.minimal.schema.MinimalGraph;
import de.uni_koblenz.minimal.schema.Node;

public class HelloWorldTest {

	@Test
	public void testCreateHelloWordlGraph() {
		HelloWorld hw = new HelloWorld();
		MinimalGraph g = hw.createHelloWorldGraph();
		assertEquals(2, g.getVCount());
		assertEquals(1, g.getECount());
		assertEquals("Hello", ((Node) g.getVertex(1)).get_label());
		assertEquals(" World!\n", ((Node) g.getVertex(2)).get_label());
	}
	
	@Test
	public void failOnPurpose(){
		fail("This was expected, believe it or not.");
	}
}
