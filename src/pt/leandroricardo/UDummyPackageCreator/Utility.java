package pt.leandroricardo.UDummyPackageCreator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import pt.leandroricardo.UDummyPackageCreator.Exceptions.NoEnoughPermissionsException;

/**
 *
 * This class (Utility) offers utilities for defensive programming
 *
 */
public class Utility {
    
    /**
     * This function checks if the distro is Debian or Ubuntu
     * @return boolean value that represents if it is Debian/Ubunt or not
     */
    public static boolean isDebianOrUbuntu() {
        Scanner in;
        
        try {
            Process r = Runtime.getRuntime().exec("cat /etc/issue");
            in = new Scanner(r.getInputStream());
            String str;

            if ((str = in.nextLine()) != null) {
                if (!str.toLowerCase().contains("debian")
                        && !str.toLowerCase().contains("ubuntu")) {
                    in.close();
                    return false;
                }
            }
            in.close();
        } catch (IOException e) {
        }

        return true;
    }

    /**
     *	This function will verify if the control file has a new version.
     * 	It consists in build the file using equivs-control, read it, find version and check if the string is greater 
     *  (it is greater if the version is greater)
     * 
     *  @param incomingFileVersion, incoming file version, as a string
     *  @return status, Positive value if the the outside file's version is bigger
     *                  Negative value if the the outside file's version is bigger
     *                  Zero if version fits
     */
    public static int checkStandardsVersion(String incomingFileVersion){
        String standardsVersion;
        Scanner in;
        int status = 0;
        
        try {
            //Build the file using equivs-control
            Runtime.getRuntime().exec("equivs-control version");

            //Read the file
            ProcessBuilder p = new ProcessBuilder("/bin/bash", "-c", "cat version | grep -i Standards");
            Process pp = p.start();
            
            in = new Scanner(pp.getInputStream());

            standardsVersion = in.nextLine().split("Standards-Version:")[1].trim();
            in.close();

            p = new ProcessBuilder("/bin/bash", "-c", "rm version");
            p.start();

            if (standardsVersion.compareToIgnoreCase(incomingFileVersion) < 0) {
                status = -1;
            } else if (standardsVersion.compareToIgnoreCase(incomingFileVersion) > 0) {
                status = 1;
            } else {
                status = 0;
            }
        } catch (IOException e) {
        }
        
        return status;
    }

    /**
     *	This function verifies if the a given file has enough permissions.
     *	@param file Represents the input file to be tested
     *  @return Boolean value
     *  @throws NoEnoughPermissionsException
     *  @throws FileNotFoundException
     */
    public static void isFileConsistent(File file) throws NoEnoughPermissionsException, FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        if (!file.canRead()) {
            throw new NoEnoughPermissionsException("No enough reading permissions");
        }

        if (!file.canWrite()) {
            throw new NoEnoughPermissionsException("No enough writing permissions");
        }
    }
}
