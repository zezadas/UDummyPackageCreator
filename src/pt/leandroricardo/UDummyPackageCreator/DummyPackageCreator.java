package pt.leandroricardo.UDummyPackageCreator;

import java.io.File;
import java.io.FileNotFoundException;

import pt.leandroricardo.UDummyPackageCreator.Exceptions.NoEnoughPermissionsException;
import pt.leandroricardo.UDummyPackageCreator.Exceptions.OperativeSystemException;

public class DummyPackageCreator {
    public static void main(String[] args){
        
    	 
         File specFile;
         SpecFileOperator operator = new SpecFileOperator();
         DummyPackage dp;
         String url = "";
         char funct;
        
        /**************************************             System verification             *******************************************/
       
        try{
            /*Check operative system*/
            if (!System.getProperty("os.name").equals("Linux")) {
               throw new OperativeSystemException("This operative system is not supported.");
            }

            /*Check distribution*/
            if (!Utility.isDebianOrUbuntu()) {
               throw new OperativeSystemException("The distribution \" " + Utility.getDistro() + " \" is not supported.");
            }
        } catch (OperativeSystemException ex) {
            System.out.println(ex.getMessage());
            return;
        }
        
        /**************************************         End of system verification            *******************************************/
        
        /**************************************       Check running path permissions          *******************************************/
        
        File dir = new File(".");
        
        try {
			Utility.isFileConsistent(dir);
		} catch (FileNotFoundException | NoEnoughPermissionsException e1) {
			System.out.println(e1.getMessage());
		}
        
        /**************************************     End of running path permissions check     *******************************************/
        
        /**************************************        	     Argument validation              *******************************************/
        
        /*Check number of arguments*/
        if(args.length != 2 && args.length != 3){
        	System.out.println("Missing arguments");
        	showHelp();
        	return;
        }
        
        /*Check first argument: options*/
        if(args[0].length() == 0 && args[0].startsWith("-")){
        	System.out.println("Bad usage. You need to use an option first.");
            showHelp();
            return;
        }
        
        /*Update function choice*/
        funct = args[0].charAt(1);
        
        /*Check second argument: file*/
        if(args[1].length() != 0){
        	specFile = new File(args[1]);
        	
        	if(specFile.exists()){
        		dp = operator.loadDummyPackage(specFile);
        	}
        	else{
        		dp = operator.createDefaultSpecFile(specFile);
        	}
        }
        else{
        	System.out.println("File not specified.");
        	return;
        }
        
        /*Check server's URL - if the number of arguments is valid*/
        if(funct == 'g'){
        	if(args.length == 3){
        		if(!args[2].startsWith("ppa") && !args[2].startsWith("http")){
            		System.out.println("Bad server URL. Please, try again");
                    showHelp();
                    return;
            	}
        		url = args[2];
        	}
        	else{
        		System.out.println("No URL found.");
                showHelp();
                return;
        	}
        }
        
        /**************************************         End of argument validation            *******************************************/
        
        /**************************************           Start of the program flow           *******************************************/
               
        switch(funct){
                case 'g':
                    dp = build(operator, specFile);
                    operator.sendToLauchpadPPA(url, dp);
                    
                    System.out.println("Done.");
                    break;
                case 'b':
                    dp = build(operator, specFile);
                    
                    System.out.println("Done.");
                    break;
                default:
                    System.out.println("Wrong option.");
                    showHelp();
            }
    }
    
    /**************************************           End of the program flow           *******************************************/
    
    public static void showHelp(){
        System.out.println("UDummyPackageCreator");
        System.out.println("Usage: java -jar UDummyPackageCreator OPTION FILE [URL] ");
        System.out.println("-b    Build package only");
        System.out.println("-g    Build package and send");
        System.out.println("This program will create a specfile if the given file doesn't exist.");
    }
    
    public static DummyPackage build(SpecFileOperator operator, File specFile) {
        DummyPackage dp;
        
        //operator.createDefaultSpecFile(specFile);
        //operator.openSpecFile(specFile);
        operator.editSpecFile(specFile);

        /*load dummy package object*/
        dp = operator.loadDummyPackage(specFile);

        operator.buildPackage(dp);
        operator.extractPackage(dp);
        operator.extractPackage(dp);
        operator.editChangelog(dp);
        operator.rebuildPackage(dp);
        
        return dp;
    }
}
