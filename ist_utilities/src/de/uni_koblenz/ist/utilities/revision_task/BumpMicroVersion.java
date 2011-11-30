package de.uni_koblenz.ist.utilities.revision_task;

public class BumpMicroVersion extends RetrieveVersion {
	@Override
	public void execute() {
		readProperties();
		micro = Integer.toString(Integer.parseInt(micro) + 1);
		saveProperties();
		writePomFile();
	}
}
