package fr.eni.ecole.exception;

import java.util.ArrayList;
import java.util.List;
/**
 * Cette classe génère des exceptions possédant une liste de String en attribut
 */
public class BusinessException extends Exception {
	private static final long serialVersionUID = 1L;

	//Ensemble des messages d'erreur de l'application
	private List<String> errors;
	public BusinessException() {
	}
	/* permet l'ajout d'un message d'erreur dans la liste */
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
