package fr.eni.ecole.dal;




import fr.eni.ecole.bo.Article;
import fr.eni.ecole.bo.Retrait;
import fr.eni.ecole.exception.BusinessException;
/**
 * interface des composants d'accès aux données de la table retraits - définit les méthodes implémentées en plus de celles de l'interface générique
 */

public interface RetraitDAO extends CRUD<Retrait> {
	
	
	Retrait selectByArticle(Article a) throws BusinessException;
}
