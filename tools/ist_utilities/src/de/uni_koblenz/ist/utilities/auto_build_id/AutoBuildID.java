package de.uni_koblenz.ist.utilities.auto_build_id;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.ListIterator;


public class AutoBuildID {
	
	private LongOpt[] longOptions;
	
	// look but don't touch, both values are updated automatically
// 	private final String revision = "$Revision: 4522 $";
// 	private final String buildID = "30";
	//
	// to use this information inside the text place $rev for the revision information
	// and $bid for the build id
	
	private final String[] info =   {"Helptext",
					 "TODO!"
					};
	
	public AutoBuildID(String[] args){
		createLongOptions();
		processArguments(args);
	}
	
	private void createLongOptions() {
		longOptions = new LongOpt[2];
		
		longOptions[0] = new LongOpt("modify", LongOpt.REQUIRED_ARGUMENT, null, 'm');
		longOptions[1] = new LongOpt("info", LongOpt.REQUIRED_ARGUMENT, null, 'i');
	}
	
	private void processArguments(String[] args){
		Getopt getopt = new Getopt("AutoBuildID", args, "im:", longOptions);
		int option;
		String optionArg;
				
		/*if no command line arguments are specified, create an "-i" argument in
		 *order to invoke printInfo()
		 */
		if (args.length == 0)
			getopt.setArgv(new String[] {"-i"});

		while ((option = getopt.getopt()) != -1) {
			switch(option) {
				case 'i':
					printInfo();
					break;
				case 'm':
					optionArg = getopt.getOptarg();
					File inputFile = new File (optionArg);
					System.out.println("Input-file is " + inputFile.toString());
					File outputFile = new File (optionArg + ".out");
					FileReader fr;
					FileWriter fw;
					BufferedReader br;
					BufferedWriter bw;
					ArrayList<String> listOfFile = new ArrayList<String>(250);
					
					try{
						fr = new FileReader(inputFile);
						br = new BufferedReader(fr);
						String line;
						
// 						System.out.println("Reading input file.");
						
// 						copy the file to a list by line
						while ((line = br.readLine()) != null){
							listOfFile.add(line);
						}
						
						br.close();
						fr.close();
						
// 						System.out.println("Input file read.");
						
// 						write the whole list into a new file
						fw = new FileWriter(outputFile);
						bw = new BufferedWriter(fw);
						
// 						System.out.println("Checking for build-ID and writing tempfile.");
						
						ListIterator<String> i = listOfFile.listIterator();
						String[] subLines;
						int wl = 0;
						while (i.hasNext()){
							line = i.next();
							wl++;
							// check for the build-id and update it
							if (line.matches(".*String buildID = \"[0-9]+\";$")){
								subLines = line.split("\"");
								System.out.println("Changed build-ID in line " + wl + ".");
								int newBid = Integer.parseInt(subLines[1]) + 1;
								line = subLines[0] + "\"" + newBid + "\"" + subLines[2];
							}
							
							bw.write(line,0,line.length());
							bw.newLine();
						}
						
						bw.close();
						fw.close();
						
						inputFile.delete();
						outputFile.renameTo(inputFile);
						outputFile.deleteOnExit();
// 						System.out.println("Input file changed.");
					}
					
					catch(FileNotFoundException e){
						e.printStackTrace();
					}
					catch(IOException e){
						e.printStackTrace();
						
					}
					
					break;
			}
		}
	}
	
	public static void main(String[] args) {
		AutoBuildID aid = new AutoBuildID(args);
	}
	
	private void printInfo(){
		System.out.println(toString());
	}
	
// 	private String addInfo(String inputLine){
// 		String outputLine = inputLine;
// 		String revString = revision.replace("$R","R").replace(" $","");
// 		
// 		outputLine = outputLine.replace("$rev",revString);
// 		outputLine = outputLine.replace("$bid","Build ID: " + buildID);
// 		
// 		return outputLine;
// 	}
	
	public String toString(){
		StringBuffer output = new StringBuffer(1024);
		output.append('\n');
		for (int i = 0; (i < info.length); i++){
			output.append(' ');
// 			output.append(addInfo(info[i]));
			output.append(info[i]);
			output.append('\n');
		}
		return output.toString();
	}
}