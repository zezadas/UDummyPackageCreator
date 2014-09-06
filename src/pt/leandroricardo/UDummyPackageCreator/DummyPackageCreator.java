package pt.leandroricardo.UDummyPackageCreator;

import pt.leandroricardo.UDummyPackageCreator.Exceptions.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class DummyPackageCreator {

	public static void main(String[] args) {
		String filename;
		String url;
		int op = -1;
		Scanner in = new Scanner(System.in);
		SpecFileOperator operator = null;
		File spec = null;
		DummyPackage cPackage = null;
		Boolean ready = false;
		
		try{
			/*Check operative system and main system preferences*/
			checkOperativeSystem();

			/*Main program flow*/
			do{
				System.out.println("UDummyPackageCreator Interactive Mode");
				System.out.println("1. Create default spec file");
				System.out.println("2. Open spec file");
				System.out.println("3. Edit spec file");
				System.out.println("4. Build package");
				System.out.println("5. Extract package");
				System.out.println("6. Edit package's changelog");
				System.out.println("7. Rebuild package");
				System.out.println("8. Send package to Launchpad");
				System.out.println("0. Exit");
				System.out.print(">");
				op = in.nextInt();
				in.nextLine();
				
				switch(op){
					case 1:	
						/*Query user for a file name*/
						System.out.println("Please, write out the spec file name:");
						System.out.print(">");
						
						filename = in.nextLine();
						
						spec = new File(filename);
						operator = new SpecFileOperator(spec);
						cPackage = operator.createDefaultSpecFile(spec);
						/*Checks if file exists, and if file e readable and writable*/
						isFileConsistent(spec);
						
						
						/*Verifies if the DummyPackage was successfully created*/
						ready = operator.getStatus();
						break;
					case 2:	
						/*Query user for a file name*/
						System.out.println("Please, write out the spec file name:");
						System.out.print(">");
						
						filename = in.nextLine();
						spec = new File(filename);
						
						/*Checks if file exists, and if file e readable and writable*/
						isFileConsistent(spec);
						operator = new SpecFileOperator(spec);
						cPackage = operator.openSpecFile(spec);
						
						/*Verifies if the DummyPackage was successfully created*/
						ready = operator.getStatus();
						break;
					case 3:
						if(ready){
							operator.editSpecFile(spec);
						}
						break;
					case 4:
						if(ready){
							operator.buildPackage(cPackage);
						}
						break;
					case 5:
						if(ready){
							operator.extractPackage(cPackage);
						}
						break;
					case 6:
						if(ready){
							operator.editChangelog(cPackage);
						}
						break;
					case 7:
						if(ready){
							operator.rebuildPackage(cPackage);
						}
						break;
					case 8:
						if(ready){
							/*Query user for the launchpad PPA*/
							System.out.println("Please, write out the spec file name:");
							System.out.print(">");
							
							url = in.nextLine();
							operator.sendToLauchpadPPA(url,cPackage);
						}
						break;
					case 0:	return;
				}
			}while(true);
			
		}catch(OperativeSystemException o){			//TODO Workarounds
			System.out.println(o);
		}catch(NoEnoughPermissionsException n){
			System.out.println(n);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		in.close();
	}
	
	/*
	 * 	checkOperativeSystem(): int
	 * 		This function check if the operative system is Ubuntu or a Debian Based distro.
	 * 		Linux Mint is not supported.
	 * 		
	 */
	public static void checkOperativeSystem() throws OperativeSystemException{		
		if(System.getProperty("os.name").equals("Linux")){
			//Verify if user is either using debian, ubuntu  or linux mint
			try {
				Process r =Runtime.getRuntime().exec("cat /etc/issue");
				Scanner in = new Scanner(r.getInputStream());
				String str;
				
				if((str = in.nextLine()) != null){
					if(!str.toLowerCase().contains("debian")
							&&	!str.toLowerCase().contains("ubuntu")
								&&	!str.toLowerCase().contains("mint")){
						in.close();
						throw new OperativeSystemException("The operative system is not supported");
					}
				}
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			throw new OperativeSystemException("The operative system is not Linux");
		}
	}
	
	/*
	 * 	checkStandardsVersion():
	 * 		This function will verify if the control file has a new version.
	 * 		It consists in:
	 * 		- Build the file using equivs-control;
	 * 		- Read it.
	 * 		- Find version;
	 * 		- Check if the string is greater (it is greater if the version is greater)
	 */
	public static void checkStandardsVersion(String incomingFileVersion) throws StandardsVersionException{
		String standardsVersion = null;
		
		try {
			//Build the file using equivs-control
			Runtime.getRuntime().exec("equivs-control version");
			
			//Read the file
			ProcessBuilder p = new ProcessBuilder("/bin/bash","-c","cat version | grep -i Standards");
			Process pp = p.start();
			Scanner in = new Scanner(pp.getInputStream());
			
			standardsVersion = in.nextLine().split("Standards-Version:")[1].trim();		//read version
			in.close();
			
			p = new ProcessBuilder("/bin/bash","-c","rm version");
			p.start();
			
			if(standardsVersion.compareToIgnoreCase(incomingFileVersion) < 0)
				throw new StandardsVersionException("The file version is older than equivs-control program.");
			else if(standardsVersion.compareToIgnoreCase(incomingFileVersion) > 0)
				throw new StandardsVersionException("The file version is newer than equivs-control program.");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 *	This function will verify if the a given file has enough permissions.
	 *	
	 */
	public static void isFileConsistent(File file) throws NoEnoughPermissionsException, FileNotFoundException{
		if(!file.exists()){
			throw new FileNotFoundException();
		}
		
		if(!file.canRead()){
			throw new NoEnoughPermissionsException("No enough reading permissions");
		}
		
		if(!file.canWrite()){
			throw new NoEnoughPermissionsException("No enough writing permissions");
		}
	}
	
	
}
