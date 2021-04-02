package fr.eni.ecole.exception;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends Exception {
	private static final long serialVersionUID = 1L;

	//Ensemble des messages d'erreur de l'application
	private List<String> errors;
	public BusinessException() {
	}

	public void addError(String error) {
		if(errors == null) {
			errors = new ArrayList<String>();
		}
		errors.add(error);
	}
	
	public List<String> getErrors() {
		return errors;
	}
	
	
}
