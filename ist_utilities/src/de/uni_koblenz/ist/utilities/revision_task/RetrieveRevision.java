package de.uni_koblenz.ist.utilities.revision_task;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNInfo;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCClient;

public class RetrieveRevision extends Task {

	private enum VersionControl {
		SVN, HG, NONE;
	}

	@Override
	public void execute() {
		File baseDir = getProject().getBaseDir();
		File[] ls = baseDir.listFiles();
		VersionControl control = VersionControl.NONE;
		for (File currentFile : ls) {
			if (currentFile.isDirectory()
					&& currentFile.getName().startsWith(".svn")) {
				control = VersionControl.SVN;
				break;
			}
		}
		switch (control) {
		case SVN:
			try {
				SVNClientManager svnClientManager = SVNClientManager
						.newInstance();
				SVNWCClient wcClient = svnClientManager.getWCClient();
				SVNInfo info = wcClient.doInfo(baseDir, SVNRevision.WORKING);
				long revision = info.getRevision().getNumber();
				System.out.println("Revision: " + revision);

				getProject()
						.setNewProperty("revision", Long.toString(revision));
			} catch (SVNException e) {
				throw new BuildException(e);
			}
			break;
		case HG:
			// TODO implement
			// break;
		default:
			System.err.println("Warning: no version control detected!");
			getProject().setProperty("revision", "unknown");
		}
	}
}
