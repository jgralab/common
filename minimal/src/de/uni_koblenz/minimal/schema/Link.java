/*
 * This code was generated automatically.
 * Do NOT edit this file, changes will be lost.
 * Instead, change and commit the underlying schema.
 */

package de.uni_koblenz.minimal.schema;

import de.uni_koblenz.jgralab.EdgeDirection;
/**
 * FromVertexClass: Node
 * FromRoleName : source
 * ToVertexClass: Node
 * ToRoleName : target
 */

public interface Link extends de.uni_koblenz.jgralab.Edge {

	/**
	 * @return the next de.uni_koblenz.minimal.schema.Link edge in the global edge sequence
	 */
	public de.uni_koblenz.minimal.schema.Link getNextLinkInGraph();

	/**
	 * @return the next edge of class de.uni_koblenz.minimal.schema.Link at the "this" vertex
	 */
	public de.uni_koblenz.minimal.schema.Link getNextLink();

	/**
	 * @return the next edge of class de.uni_koblenz.minimal.schema.Link at the "this" vertex
	 * @param orientation the orientation of the edge
	 */
	public de.uni_koblenz.minimal.schema.Link getNextLink(EdgeDirection orientation);
}
