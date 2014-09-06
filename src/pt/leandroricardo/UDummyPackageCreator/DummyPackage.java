package pt.leandroricardo.UDummyPackageCreator;

/*
 * 		Class Name:		DummyPackage
 * 		Version:		14.09.01
 * 		Description:	This java class implements an abstraction for dummy packages.
 * 						The  main reason of this abstraction is to serialize this object in future releases.
 * 		
 */

public class DummyPackage { 
	
	private String name;					/*Package's name*/
	private String version;					/*Package's version*/
	private String controlFileName;			/*Package's control file name*/
	private String folderName;				/*Package's folder name*/
	
	public DummyPackage(String controlFileName,String name, String version){
		this.name = name;
		this.version = version;
		this.controlFileName = controlFileName;
		
		folderName = name + "-" + version;
	}

	public String getName(){
		return name;
	}
	
	public String getVersion(){
		return version;
	}

	public String getControlFileName() {
		return controlFileName;
	}
	
	public String getFolderName() {
		return folderName;
	}

}
