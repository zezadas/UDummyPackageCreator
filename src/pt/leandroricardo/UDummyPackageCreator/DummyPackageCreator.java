package pt.leandroricardo.UDummyPackageCreator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import pt.leandroricardo.UDummyPackageCreator.Exceptions.NoEnoughPermissionsException;
import pt.leandroricardo.UDummyPackageCreator.Exceptions.OperativeSystemException;

/**
 * DummyPackageCreator
 * @author lricardo
 */
public class DummyPackageCreator {
    public static void main(String[] args){
        
        
        /**************************************             System verification             *******************************************/
       
    //        try{
    //            /*Check operative system*/
    //            if (!System.getProperty("os.name").equals("Linux")) {
    //               throw new OperativeSystemException("This operative system is not supported.");
    //            }
    //
    //            /*Check distribution*/
    //            if (!Utility.isDebianOrUbuntu()) {
    //               throw new OperativeSystemException("The distribution \" " + Utility.getDistro() + " \" is not supported.");
    //            }
    //        } catch (OperativeSystemException ex) {
    //            System.out.println(ex.getMessage());
    //            //return;
    //        }
        
        /*Read terminal parameters and run program*/
        if(args.length == 2 && args[0].length() == 2){
            
        }
        else{
            showHelp();
            return;
        }
        /**************************************         End of system verification            *******************************************/
        
        /**************************************           Start of the program flow           *******************************************/
        String filename = args[1];              //Spec file name
        File specFile = new File(filename);         //Represents the spec file that exists in the filesystem
        SpecFileOperator operator;
        DummyPackage dp;
        
//        /*Verifies file consistency*/
//        try {
//            Utility.isFileConsistent(specFile);
//        } catch (NoEnoughPermissionsException | FileNotFoundException e) {
//            System.out.println(e.getMessage());
//            return;
//        }
        
        /*Initalizes spec file operator*/
        operator = new SpecFileOperator(specFile);
        
        switch(args[0].charAt(1)){
                case 'g':
                    
                    
                    break;
                case 'b':
                    operator.createDefaultSpecFile(specFile);
                    //operator.openSpecFile(specFile);
                    operator.editSpecFile(specFile);
                    
                    /*load dummy package object*/
                    dp = operator.loadDummyPackage(specFile);
                    
                    operator.buildPackage(dp);
                    operator.extractPackage(dp);
                    operator.extractPackage(dp);
                    operator.rebuildPackage(dp);
                    
                    break;
                default:
                    System.out.println("Wrong option.");
                    showHelp();
            }
    }
    
    public static void showHelp(){
        System.out.println("UDummyPackageCreator");
        System.out.println("Usage: udummy [OPTION] ... [FILE]");
        System.out.println("-b    Build package from the start (creating a new spec file)");
        //System.out.println("-g    Build package from spec file and send");
        
    }
}
