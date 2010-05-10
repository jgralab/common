package de.uni_koblenz.newproject;

/**
 * Displays "Hello World!" on command line.
 * 
 * @author John Doe
 * @version 1.0
 */
public class HelloWorld {
	
	private static final String revision = "$Revision: 1 $";
	private static final String buildID = "1";
	private static final String version = "1.0";

	/**
	 * Default constructor.
	 * 
	 * @param void
	 */
	public HelloWorld(){}

	/**
	 * Returns the String "Hello World!".
	 * 
	 * @param void
	 * @return sentenceToSay
	 */
	public String sayHelloWorld() {
		String helloWorld = "Hello World!";
		return helloWorld;
	}
	
	/**
	 * Main method. Displays the String "Hello World!" on command line.
	 * 
	 * @param args
	 *            command line parameters
	 * @return void
	 */
	public static void main(String[] args) {
		HelloWorld hw = new HelloWorld();
		System.out.println(hw.sayHelloWorld());
	}

}
