package pt.leandroricardo.UDummyPackageCreator;

import java.io.IOException;

public class SpecFileOperator {
	/*
	 * 	FileOperator:
	 * 		This class will implement operations with spec files.
	 * 		- Open an existing spec file
	 * 		- Create a default spec file
	 * 		- Edit a spec file using the user favourite text editor
	 * 		- Build the package
	 * 		- Extract the package
	 * 		- Load package folder name (load information)
	 * 		- Rebuild package
	 * 		- Send file to launchpad PPA
	 */
	
	public static void openSpecFile(String filename){
		ProcessBuilder p = new ProcessBuilder();
	}
	
	public static void createDefaultSpecFile(String filename){
		ProcessBuilder p = new ProcessBuilder("equivs-control",filename);	
		
		try {
			//start process
			p.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void editSpecFile(String term, String filename){
		String xparam = "-x";
		
		/*in xterm, the argument to execute is different*/
		if(term.equals("xterm")){			
			xparam = "-e";
		}
		
		ProcessBuilder p = new ProcessBuilder(term,xparam,"nano",filename);
		
		try {
			//start process
			p.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void buidPackage(){
		
	}
	
	public static void extractPackage(){
		
	}
	
	public static String loadFolderName(){
		return "";
	}
	
	public static void rebuildPackage(){
		
	}
	
	public static void sendToLauchpadPPA(){
		
	}
	

}
