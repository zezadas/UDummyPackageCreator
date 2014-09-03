package pt.leandroricardo.UDummyPackageCreator;

import pt.leandroricardo.UDummyPackageCreator.Exceptions.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class DummyPackageCreator {

	/*
	 * 		DummyPackageCreator
	 * 		
	 * 		Changelog:	First commit				2014/09/02
	 * 					First integrity checks
	 */
	public static void main(String[] args) {
		String [] config = {"","","",""};
		String term;
		String secTerm;
		String homePath;
		String userName;
		
		/*Integrity checks*/
		try{
			/*Check operative system and main system preferences*/
			checkOperativeSystem();
			
			try {
				config = readConfigurationFile();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			/*Check permissions*/
			checkPermissions();
			
			/*Register attributes*/
			term = config[0];
			secTerm = config[1];
			homePath = config[2];
			userName = config[3];
			
			/*A second validation in terminal application*/
			if(term.equals("")){
				if(secTerm.equals("")){
					term = "xterm";						/* XTerm is available in almost every distros*/
				}
				else{
					term = secTerm;
				}
			}
			
		}catch(OperativeSystemException o){
			System.out.println(o);
		}catch(NoEnoughPermissionsException n){
			System.out.println(n);
		}
		
		/*Main program flow*/
		
		
	}
	
	/*
	 * 	readConfigurationFile():
	 * 		This feature is not fully completed yet, altough I've already chosen a format:
	 * 		When this program is installed, a script called config-gen.sh will write out a file separated by '\n'
	 * 		Format:
	 * 			<colorterminal>			> will be used for execute interactive commands
	 * 			<term>					> will be used for backup, if $COLORTERMINAL is not defined
	 * 			<user's home path>		> reserved for future features
	 * 			<username>				> reserved for future features
	 * 		
	 * 		It's not the best configuration utily, but it does the job without human intervention
	 * 					
	 */
	public static String[] readConfigurationFile() throws FileNotFoundException{
		String [] list = {"","","",""};
		
		File config = new File("config");
		Scanner sc;
		
		if(!config.exists()){
			throw new FileNotFoundException();
		}
		
		sc = new Scanner(config);
		
		for(int  i = 0; i < list.length; i++){
			if((list[i] = sc.nextLine()).matches("<>")){
				list[i] = "";
			}
			else{
				list[i] = list[i].split("<")[1].split(">")[0];
			}
		}
		
		sc.close();
		return list;
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
	public static void standardsVersion(String incomingFileVersion) throws StandardsVersionException{
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
	 * 	checkPermissions():
	 * 		This function will verify if the working directory has enough permissions.
	 * 		Only reading and writing permissions are needed.		
	 */
	public static void checkPermissions() throws NoEnoughPermissionsException{
		File wd = new File(".");
		
		if(!wd.canRead()){
			throw new NoEnoughPermissionsException("No enough reading permissions");
		}
		
		if(!wd.canWrite()){
			throw new NoEnoughPermissionsException("No enough writing permissions");
		}
	}
	
	
}
