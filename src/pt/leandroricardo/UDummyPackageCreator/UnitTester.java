package pt.leandroricardo.UDummyPackageCreator;

import java.util.Scanner;

public class UnitTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int op;
		Scanner in = new Scanner(System.in);
		DummyPackageOperator teste = new DummyPackageOperator("spec");
		
		
		System.out.println("Dummy Package Creator Unit Testor");
		
		
		do{
			System.out.println("1. Open an existing spec file");
			System.out.println("2. Create a default spec file");
			System.out.println("3. Edit spec file");
			System.out.println("4. Build package");
			System.out.println("5. Extract package");
			System.out.println("6. Load information");
			System.out.println("7. Edit changelog");
			System.out.println("8. Rebuild package");
			System.out.println("9. Send to PPA");
			System.out.println("0. Exit");
			
			op = in.nextInt(); in.nextLine();		
			
			switch(op){
				case 1:
					System.out.println("Please, write down the name of the existing file: ");
					teste.openSpecFile(in.nextLine());
					break;
				case 2:
					teste.createSpecFile();
					break;
				case 3:
					teste.editSpecFile();
					break;
				case 4:
					teste.buildPackage();
					break;
				case 5:
					teste.extractPackage();
					break;
				case 6:
					//teste.loadInformation();
					break;
				case 7:
					teste.editChangelog();
					break;
				case 8:
					teste.rebuildPackage();
					break;
				case 9:
					teste.sendToLaunchpad("ppa:deti-ua/deti-packages");
					break;
				case 0: break;
			}
		}while(op != 0);
		
		in.close();
	}

}
