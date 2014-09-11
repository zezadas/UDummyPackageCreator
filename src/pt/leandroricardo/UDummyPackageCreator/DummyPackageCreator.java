package pt.leandroricardo.UDummyPackageCreator;

import pt.leandroricardo.UDummyPackageCreator.Exceptions.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/*
 *	This class is the main frontend.
 */
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

        try {
            /*Check operative system*/
            if (!System.getProperty("os.name").equals("Linux")) {
                throw new OperativeSystemException("This operative system is not supported.");
            }

            /*Check distribution*/
            if (!Utility.isDebianOrUbuntu()) {
                throw new OperativeSystemException("This distribution is not supported.");
            }

            /*Main program flow*/
            do {
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

                switch (op) {
                    case 1:
                        /*Query user for a file name*/
                        System.out.println("Please, write out the spec file name:");
                        System.out.print(">");

                        filename = in.nextLine();

                        spec = new File(filename);
                        operator = new SpecFileOperator(spec);
                        cPackage = operator.createDefaultSpecFile(spec);
                        /*Checks if file exists, and if file e readable and writable*/
                        Utility.isFileConsistent(spec);

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
                        Utility.isFileConsistent(spec);
                        operator = new SpecFileOperator(spec);
                        cPackage = operator.openSpecFile(spec);

                        /*Verifies if the DummyPackage was successfully created*/
                        ready = operator.getStatus();
                        break;
                    case 3:
                        if (ready) {
                            operator.editSpecFile(spec);
                        }
                        break;
                    case 4:
                        if (ready) {
                            operator.buildPackage(cPackage);
                        }
                        break;
                    case 5:
                        if (ready) {
                            operator.extractPackage(cPackage);
                        }
                        break;
                    case 6:
                        if (ready) {
                            operator.editChangelog(cPackage);
                        }
                        break;
                    case 7:
                        if (ready) {
                            operator.rebuildPackage(cPackage);
                        }
                        break;
                    case 8:
                        if (ready) {
                            /*Query user for the launchpad PPA*/
                            System.out.println("Please, write out the spec file name:");
                            System.out.print(">");

                            url = in.nextLine();
                            operator.sendToLauchpadPPA(url, cPackage);
                        }
                        break;
                    case 0:
                        return;
                }
            } while (true);
        } catch (NoEnoughPermissionsException n) {
            System.out.println("Check file permission using \"ls -l\" and change it using \"chmod +rw .\"");
            System.out.println("Will now exit.");
            System.exit(-1);
        } catch (FileNotFoundException e) {
            System.out.println("The filename wasn't found. Please, try again.");
        } catch (OperativeSystemException ex) {
            System.out.println(ex.getMessage());
        }
        in.close();
    }

}
