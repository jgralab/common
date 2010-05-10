package de.uni_koblenz.newprojecttest;

import de.uni_koblenz.newproject.HelloWorld;
import junit.framework.TestCase;
import junit.framework.AssertionFailedError;

/**
 * Tests methods of class Hello World.
 * 
 * @author John Doe
 * @version 1.0
 */
public class HelloWorldTest extends TestCase {
	
	/**
	 * Default constructor.
	 * 
	 * @param void
	 */
	public HelloWorldTest(){}

	/**
	 * Test, if the method sayHelloWorld() returns the String "Hello World!".
	 * 
	 * @param void
	 * @return void
	 */
	public void testSayHelloWorld(){
		HelloWorld hw = new HelloWorld();
		assert(hw != null);
		assertEquals("Hello World!", hw.sayHelloWorld());
	}

}