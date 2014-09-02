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
		
		/*Integrity checks*/
		try{
			/*Check operative system and main system preferences*/
			checkOperativeSystem();
			
			/*Check permissions*/
			checkPermissions();
			
		}catch(OperativeSystemException o){
			System.out.println(o);
		}catch(NoEnoughPermissionsException n){
			System.out.println(n);
		}
		
		/*Main program flow*/
		
		
	}
	
	/*
	 * 	checkOperativeSystem(): int
	 * 		This function check if the operative system is Ubuntu or a Debian Based distro.
	 * 		Linux Mint is not supported.
	 * 		
	 */
	public static void checkOperativeSystem() throws OperativeSystemException{		
		if(System.getProperty("os.name").equals("Linux")){
			//Get environment variables
			Map<String,String> env = System.getenv();
			
			//Verify if user is either using debian or ubuntu
			if(!env.get("DESKTOP_SESSION").contains("ubuntu") && 
					!!env.get("DESKTOP_SESSION").contains("debian")){
				throw new OperativeSystemException("The distro " + env.get("DESKTOP_SESSION") + " is not supported.");
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
