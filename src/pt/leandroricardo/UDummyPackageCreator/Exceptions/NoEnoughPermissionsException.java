package pt.leandroricardo.UDummyPackageCreator.Exceptions;

public class NoEnoughPermissionsException extends Exception {
	private static final long serialVersionUID = -1842786413852853050L;

	public NoEnoughPermissionsException(String message){
		super(message);
	}
}
