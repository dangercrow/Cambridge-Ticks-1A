package uk.ac.cam.sy321.tick3star;

public class BadFormatException extends Exception{
	private static final long serialVersionUID = -3705890097746857146L;

	public BadFormatException(String errorMessage){
		super(errorMessage);
	}
}
