package pt.leandroricardo.UDummyPackageCreator;

/*
 * 		Class Name:		DummyPackage
 * 		Version:		14.09.01
 * 		Description:	This java class implements an abstraction for dummy packages.
 * 		
 * 		Changelog:		First Version									2014/09/01
 */

import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;


public class DummyPackage { 
	
	private String specFileName;		//	Output from equivs-control program
	
	private String packageName;			//	Name of the package
	private String version;				//	Version of the package
	
	public DummyPackage(String specFileName){
		//this.setSpecFileName(specFileName);
	}
	
	public DummyPackage(){
		//this.setSpecFileName("spec");		//	Spec is the default file name
	}

	
	
}
