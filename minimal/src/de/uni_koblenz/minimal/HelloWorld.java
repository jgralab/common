package de.uni_koblenz.minimal;

import de.uni_koblenz.minimal.schema.MinimalGraph;
import de.uni_koblenz.minimal.schema.MinimalSchema;
import de.uni_koblenz.minimal.schema.Node;

public class HelloWorld {
	public MinimalGraph createHelloWorldGraph() {
		MinimalGraph out = MinimalSchema.instance().createMinimalGraph();
		Node v1 = out.createNode();
		v1.set_label("Hello");
		Node v2 = out.createNode();
		v2.set_label(" World!\n");
		out.createLink(v1, v2);
		return out;
	}
	
	public static void main(String[] args) {
		HelloWorld hw = new HelloWorld();
		MinimalGraph g = hw.createHelloWorldGraph();
		for(Node current : g.getNodeVertices()){
			System.out.print(current.get_label());
		}
		System.out.flush();
	}
}
