package fr.eni.ecole.bll;

import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.exception.*;
import fr.eni.ecole.util.Constants;
import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.dal.DAOFactory;
import fr.eni.ecole.dal.UtilisateurDAO;

public class BllUtilisateur {

	private static BllUtilisateur instance;
	private static UtilisateurDAO utilisateur;

	private BllUtilisateur() {
		utilisateur = DAOFactory.getUtilisateurDAO();
	}

	public static BllUtilisateur getBllUtilisateur() {
		if (instance == null) {
			instance = new BllUtilisateur();
		}
		return instance;
	}

	public Utilisateur selectById(int id) throws BusinessException {
		return utilisateur.selectById(id);
	}

	public void insert(Utilisateur item) throws BusinessException {
		BusinessException be = new BusinessException();
		if(inputControl(item,be)) {
			utilisateur.insert(item);
		} else {
			throw be;
		}
	}

	public void delete(Utilisateur item) throws BusinessException {
		utilisateur.delete(item);
	}

	public void update(Utilisateur item) throws BusinessException {
		BusinessException be = new BusinessException();
		if(inputControl(item,be)) {
			utilisateur.update(item);
		} else {
			throw be;
		}
	}

	public boolean inputControl(Utilisateur item, BusinessException be) throws BusinessException {
		boolean result = true;
		if (!item.getCodePostal().matches(Constants.REGEX_CODE_POSTAL)) {
			be.addError(Errors.CODE_POSTAL_INVALIDE);
			result = false;
		}
		if (!item.getTelephone().matches(Constants.REGEX_TELEPHONE)) {
			be.addError(Errors.NOMBRE_TELEPHONE_INVALIDE);
			result = false;
		}
		if (item.getPseudo().isEmpty() || item.getPseudo().length() < 6) {
			be.addError(Errors.PSEUDO_INSUFFISANT);
			result = false;
		}
		if (!item.getMotDePasse().matches(Constants.REGEX_PWD)) {
			be.addError(Errors.MOT_DE_PASSE_INVALIDE);
			return false;
		}
		return result;
	}

	/**
	 * Permet de valider le login / password tranmis par l'affichage
	 * 
	 * @param login
	 * @param password
	 * @return
	 * @throws BusinessException
	 * @throws ClassNotFoundException 
	 */
	public Utilisateur validateConnection(String login, String password) throws BusinessException, ClassNotFoundException {
		// Appelle de la couche DAL
		return utilisateur.find(login, password);
	}

}
