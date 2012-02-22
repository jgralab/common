package de.uni_koblenz.ist.utilities.ant;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class CreateMissingPackageDocumentation extends Task {
	private File srcDir;

	public void setSrcDir(File srcDir) {
		this.srcDir = srcDir;
	}

	@Override
	public void execute() {
		if (srcDir == null) {
			throw new BuildException("No source Folder specified.");
		}
		if (!(srcDir.exists() && srcDir.isDirectory())) {
			throw new BuildException("No valid source Folder specified.");
		}
		try {
			processDirectory(srcDir);
		} catch (Exception e) {
			throw new BuildException(e);
		}
	}

	private void processDirectory(File dir) throws IOException {
		File documentation = new File(dir.getAbsoluteFile() + File.separator
				+ "package-info.java");
		File[] content = dir.listFiles();
		boolean containsFiles = false;
		for (File current : content) {
			if (current.isDirectory()) {
				processDirectory(current);
			}
			if (!containsFiles && current.isFile()) {
				containsFiles = true;
			}
		}
		if (containsFiles && !documentation.exists()) {
			createDocumentation(dir, documentation);
		}
	}

	private void createDocumentation(File dir, File documentation)
			throws IOException {
		String packageString = computePackage(dir);
		if (packageString.contains("-")) {
			return;
		}
		System.out.println("Creating " + documentation.getName()
				+ " for package " + packageString);
		PrintWriter out = new PrintWriter(documentation);
		out.println("/**");
		out.println(" * TODO [documentation] write documentation for this package.");
		out.println(" */");
		out.println();
		out.print("package ");
		out.print(packageString);
		out.println(";");
		out.flush();
		out.close();
	}

	private String computePackage(File dir) {
		String[] prefix = srcDir.getAbsolutePath().split(File.separator);
		String[] currentPath = dir.getAbsolutePath().split(File.separator);
		String packageString = null;
		StringBuilder builder = new StringBuilder();
		for (int i = prefix.length; i < currentPath.length; i++) {
			if (i > prefix.length) {
				builder.append(".");
			}
			builder.append(currentPath[i]);
		}
		packageString = builder.toString();
		return packageString;
	}

	public static void main(String[] args) {
		CreateMissingPackageDocumentation o = new CreateMissingPackageDocumentation();
		o.setSrcDir(new File("../jgralab/src"));
		o.execute();
		System.out.println("Fini.");

	}

}
