/*
 * IST Utilities
 * (c) 2006-2009 Institute for Software Technology
 *               University of Koblenz-Landau, Germany
 *
 *               ist@uni-koblenz.de
 *
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package de.uni_koblenz.ist.utilities.auto_build_id;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import de.uni_koblenz.ist.utilities.option_handler.OptionHandler;

public class AutoBuildID {

	private String filename;

	// look but don't touch, both values are updated automatically
	// private final String revision = "$Revision: 4522 $";
	// private final String buildID = "31";
	//
	// to use this information inside the text place $rev for the revision
	// information
	// and $bid for the build id

	public AutoBuildID(String filename) {
		this.filename = filename;
	}

	private static CommandLine processCommandLine(String[] args) {
		// Creates a OptionHandler.
		String toolString = "java " + AutoBuildID.class.getName();
		String versionString = "1.0";
		OptionHandler oh = new OptionHandler(toolString, versionString);
		Option modify = new Option("m", "modify", true,
				"(required): Modifies the given Java source file.");
		modify.setRequired(true);
		modify.setArgName("filename");
		oh.addOption(modify);

		return oh.parse(args);
	}

	public static void main(String[] args) {
		CommandLine cl = processCommandLine(args);
		AutoBuildID abid = new AutoBuildID(cl.getOptionValue("m"));
		abid.process();
	}

	public void process() {
		File inputFile = new File(filename);
		System.out.println("Auto build ID");
		System.out.println("Input-file is " + inputFile.toString());
		File outputFile = new File(filename + ".out");
		FileReader fr;
		FileWriter fw;
		BufferedReader br;
		BufferedWriter bw;
		ArrayList<String> listOfFile = new ArrayList<String>(250);

		try {
			fr = new FileReader(inputFile);
			br = new BufferedReader(fr);
			String line;
			
			// copy the file to a list by line
			while ((line = br.readLine()) != null) {
				listOfFile.add(line);
			}

			br.close();
			fr.close();
			
			// write the whole list into a new file
			fw = new FileWriter(outputFile);
			bw = new BufferedWriter(fw);

			ListIterator<String> i = listOfFile.listIterator();
			String[] subLines;
			int wl = 0;
			while (i.hasNext()) {
				line = i.next();
				wl++;
				// check for the build-id and update it
				if (line.matches(".*String buildID = \"[0-9]+\";$")) {
					subLines = line.split("\"");
					System.out.println("Changed build-ID in line " + wl + ".");
					int newBid = Integer.parseInt(subLines[1]) + 1;
					line = subLines[0] + "\"" + newBid + "\"" + subLines[2];
				}

				bw.write(line, 0, line.length());
				bw.newLine();
			}

			bw.close();
			fw.close();

			inputFile.delete();
			outputFile.renameTo(inputFile);
			outputFile.deleteOnExit();
			System.out.println("Fini.");
		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
