package fr.eni.ecole.dal;



import fr.eni.ecole.bo.Categorie;

import fr.eni.ecole.exception.BusinessException;
/**
 * interface des composants d'accès aux données de la table categories - définit les méthodes implémentées en plus de celles de l'interface générique
 */
public interface CategorieDAO extends CRUD<Categorie> {
	
	Categorie selectByName(String nom) throws BusinessException;
	
}
