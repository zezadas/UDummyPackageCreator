package pt.leandroricardo.UDummyPackageCreator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/*
 *	This class is used to create dummy Debian Packages, using equivs-control spec files.
 * 	
 * 	@see		Process
 * 	@see		ProcessBuilder
 * 	@see		File
 */
public class SpecFileOperator {

    boolean ready;

    public SpecFileOperator(File specfile) {
        /*initialize status*/
        this.setStatus(false);
    }
    
    /*
     *	This function return the object status. 
     *
     *	The SpecFileOperator object may be ready to use, or, not ready.
     *	@return		boolean value that represents the status
     */
    public boolean getStatus() {
        return ready;
    }

    private void setStatus(boolean status) {
        ready = status;
    }

    /**
     *
     * Public methods
     *
     *
     */
//	/**
//	 * 	This function opens a default spec file. Opening means to create a DummyPackage Object by loading package's name and 
//	 * version from the specified file.
//	 * 	Sets the object to the state ready.
//	 * 	@param			The File Object that represents the spec file
//	 *      @return			DummyPackage Object, ready to be used
//	 */
//	public DummyPackage openSpecFile(File specfile) {
//		DummyPackage e = operator.loadDummyPackage(specfile);
//		this.setStatus(true);
//		return e;
//	}
    /*
     * 	This function creates a default spec file using equivs-control.
     * 	@param			The File Object that represents the spec file
     * 	@return 		DummyPackage Object, ready to be used
     */
    public void createDefaultSpecFile(File specfile) {
        ProcessBuilder pb = new ProcessBuilder("equivs-control", specfile.getName());

        try {
            Process p = pb.start();
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function reads the spec file and loads it's information.
     *
     * @param	The File Object that represents the spec file
     * @return	The DummyPackage object that represents the Dummy Package base
     * and loaded information
     */
    public DummyPackage loadDummyPackage(File specfile) {
        String packageName = "dummy-package";
        String packageVersion = "1.0";

        try {
            /*Load spec file information*/
            Scanner in = new Scanner(specfile);
            String line;

            while (in.hasNextLine()) {
                line = in.nextLine();

                if (line.contains("Package: ") && line.charAt(0) != '#') {
                    packageName = line.split("Package: ")[1].trim();
                }

                if (line.contains("Version: ") && line.charAt(0) != '#' && !line.contains("Standards-Version")) {
                    packageVersion = line.split("Version: ")[1].trim();
                }
            }

            in.close();
        } catch (FileNotFoundException f) {
            System.out.println(f);
        }

        return new DummyPackage(specfile.getName(), packageName, packageVersion);
    }

    /**
     * This function opens a text editor to edit the given file. The chosen text
     * editor is VIM.
     *
     * @param	The File Object that represents the spec file
     */
    public void editSpecFile(File specfile) {
        ProcessBuilder pb = new ProcessBuilder("vim", specfile.getName());

        pb.redirectInput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);

        try {
            Process p = pb.start();
            /*Wait until user complete the file edition*/
            p.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function builds the package executing equivs-build.
     *
     * @param	DummyPackage object that contains all needed information
     */
    public void buildPackage(DummyPackage d) {
        ProcessBuilder pb = new ProcessBuilder("equivs-build", "--full", d.getControlFileName());

        pb.redirectInput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);

        try {
            /*Starts the process*/
            Process p = pb.start();

            p.waitFor();

        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * This function extracts the package executing dpkg-source.
     *
     * @param	DummyPackage object that contains all needed information
     */
    public void extractPackage(DummyPackage d) {
        ProcessBuilder pb = new ProcessBuilder("dpkg-source", "-x", d.getName() + "_" + d.getVersion() + ".dsc");

        pb.redirectInput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);

        try {
            /*Starts the process*/
            Process p = pb.start();

            p.waitFor();

        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * This function opens the changelog file with the text editor
     *
     * @param	DummyPackage object that contains all needed information
     */
    public void editChangelog(DummyPackage d) {
        ProcessBuilder pb = new ProcessBuilder("vim", d.getFolderName() + "/debian/changelog");

        pb.redirectInput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);

        try {
            Process p = pb.start();
            /*Wait until user complete the file edition*/
            p.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function rebuild the debian package executing debuild.
     *
     * @param	DummyPackage object that contains all needed information
     */
    public void rebuildPackage(DummyPackage d) {
        ProcessBuilder pb = new ProcessBuilder("debuild", "-S");
        /*Sets directory to the new directory*/
        pb.directory(new File(d.getFolderName()));

        try {
            Process p = pb.start();
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * This function sends the package to the PPA.
     *
     * @param	URL of PPA
     */
    public void sendToLauchpadPPA(String url, DummyPackage d) {
        ProcessBuilder pb = new ProcessBuilder("dput", url, d.getName() + "_" + d.getVersion() + "_" + "source.changes");

        // TODO Fix me
        pb.redirectInput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);

        try {
            Process p = pb.start();
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
