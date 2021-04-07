package fr.eni.ecole.dal;
/**
 * cette classe génère des instances des interfaces de la couche DAL - permet d'accéder à toutes les méthodes de la couche DAL à l'exception des builders.
 */
public class DAOFactory {
	
	public static ArticleDAO getArticleDAO() {
		return new ArticleJDBC();
	}
	public static CategorieDAO getCategorieDAO() {
		return new CategorieJDBC();
	}
	public static EnchereDAO getEnchereDAO() {
		return new EnchereJDBC();
	}
	public static RetraitDAO getRetraitDAO() {
		return new RetraitJDBC();
	}
	public static UtilisateurDAO getUtilisateurDAO() {
		return new UtilisateurJDBC();
	}

}
