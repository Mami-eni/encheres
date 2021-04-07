package fr.eni.ecole.dal;

import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.exception.BusinessException;
/**
 * interface des composants d'accès aux données de la table utilisateurs - définit les méthodes implémentées en plus de celles de l'interface générique
 */
public interface UtilisateurDAO extends CRUD<Utilisateur> {
	Utilisateur find (String pseudo, String motDePasse) throws BusinessException, ClassNotFoundException;
}
