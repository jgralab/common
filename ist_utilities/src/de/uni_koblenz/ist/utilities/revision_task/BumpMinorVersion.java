package de.uni_koblenz.ist.utilities.revision_task;

public class BumpMinorVersion extends RetrieveVersion {

	@Override
	public void execute() {
		readProperties();
		minor = Integer.toString(Integer.parseInt(minor) + 1);
		micro = "0";
		saveProperties();
		writePomFile();
	}
}
