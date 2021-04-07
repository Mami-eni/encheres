package fr.eni.ecole.dal;
/**
 * Interface DAO pour la class utilisateur
 */
import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.exception.BusinessException;

public interface UtilisateurDAO extends CRUD<Utilisateur> {
	Utilisateur find (String pseudo, String motDePasse) throws BusinessException, ClassNotFoundException;
}
