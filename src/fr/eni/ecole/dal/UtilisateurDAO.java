package fr.eni.ecole.dal;

import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.exception.BusinessException;

public interface UtilisateurDAO extends CRUD<Utilisateur> {
	Utilisateur find (String pseudo, String motDePasse) throws BusinessException, ClassNotFoundException;
}
