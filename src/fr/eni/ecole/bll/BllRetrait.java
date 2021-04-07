package fr.eni.ecole.bll;


import fr.eni.ecole.util.Constants;

import fr.eni.ecole.bo.Article;
import fr.eni.ecole.bo.Retrait;
import fr.eni.ecole.dal.DAOFactory;
import fr.eni.ecole.dal.RetraitDAO;
import fr.eni.ecole.exception.BusinessException;
import fr.eni.ecole.exception.Errors;
/**
 * Cette classe contient la logique métier avant l'appel aux méthodes de la couche DAL qui accèdent à la table retraits de la base de données
 */
public class BllRetrait {

	private static BllRetrait instance;
	private static RetraitDAO retrait;
	
	private BllRetrait() {
		retrait = DAOFactory.getRetraitDAO();
	}
	
	public static BllRetrait getBllRetrait() {
		if(instance == null) {
			instance = new BllRetrait();
		}
		return instance;
	}
	
	public void insert(Retrait item) throws BusinessException {
		/* vérification du format du code postal */
		if(item.getCodePostal().matches(Constants.REGEX_CODE_POSTAL)) {
		retrait.insert(item);
		}
		else {
			BusinessException error = new BusinessException();
			error.addError(Errors.REGLE_CODE_POSTAL);
			throw error;
		}
	}
	
	public void update(Retrait item) throws BusinessException {
		retrait.update(item);
	}
	
	public void delete(Retrait item) throws BusinessException {
		retrait.delete(item);
	}
	
	public Retrait selectByArticle(Article a) throws BusinessException{
		return retrait.selectByArticle(a);
	}
	
	
}
