/*
 * This code was generated automatically.
 * Do NOT edit this file, changes will be lost.
 * Instead, change and commit the underlying schema.
 */

package de.uni_koblenz.minimal.schema.impl.std;

import de.uni_koblenz.jgralab.impl.std.ReversedEdgeImpl;
import de.uni_koblenz.jgralab.impl.std.EdgeImpl;

import de.uni_koblenz.jgralab.EdgeDirection;
import de.uni_koblenz.jgralab.Graph;
import de.uni_koblenz.jgralab.GraphIO;
import de.uni_koblenz.jgralab.GraphIOException;

import java.io.IOException;

public class ReversedLinkImpl extends ReversedEdgeImpl implements de.uni_koblenz.jgralab.Edge, de.uni_koblenz.minimal.schema.Link {

	ReversedLinkImpl(EdgeImpl e, Graph g) {
		super(e, g);
	}

	public final de.uni_koblenz.jgralab.schema.AttributedElementClass getAttributedElementClass() {
		return de.uni_koblenz.minimal.schema.MinimalSchema.instance().ec_Link;
	}

	public void readAttributeValues(GraphIO io) throws GraphIOException {
		throw new GraphIOException("Can not call readAttributeValues for reversed Edges.");
	}

	public void readAttributeValueFromString(String attributeName, String value) throws GraphIOException, NoSuchFieldException {
		throw new GraphIOException("Can not call readAttributeValuesFromString for reversed Edges.");
	}

	public void writeAttributeValues(GraphIO io) throws GraphIOException, IOException {
		throw new GraphIOException("Can not call writeAttributeValues for reversed Edges.");
	}

	public String writeAttributeValueToString(String _attributeName) throws IOException, GraphIOException, NoSuchFieldException {
		throw new GraphIOException("Can not call writeAttributeValueToString for reversed Edges.");
	}

	public de.uni_koblenz.minimal.schema.Link getNextLinkInGraph() {
		return ((de.uni_koblenz.minimal.schema.Link)normalEdge).getNextLinkInGraph();
	}

	public de.uni_koblenz.minimal.schema.Link getNextLink() {
		return (de.uni_koblenz.minimal.schema.Link)getNextEdgeOfClass(de.uni_koblenz.minimal.schema.Link.class);
	}

	public de.uni_koblenz.minimal.schema.Link getNextLink(EdgeDirection orientation) {
		return (de.uni_koblenz.minimal.schema.Link)getNextEdgeOfClass(de.uni_koblenz.minimal.schema.Link.class, orientation);
	}

	public de.uni_koblenz.minimal.schema.Link getNextLink(boolean noSubClasses) {
		return (de.uni_koblenz.minimal.schema.Link)getNextEdgeOfClass(de.uni_koblenz.minimal.schema.Link.class, noSubClasses);
	}

	public de.uni_koblenz.minimal.schema.Link getNextLink(EdgeDirection orientation, boolean noSubClasses) {
		return (de.uni_koblenz.minimal.schema.Link)getNextEdgeOfClass(de.uni_koblenz.minimal.schema.Link.class, orientation, noSubClasses);
	}
}
