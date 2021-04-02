package fr.eni.ecole.dal;

public class DAOFactory {
	
	private static ArticleDAO article;
	private static CategorieDAO categorie;
	private static EnchereDAO enchere;
	private static RetraitDAO retrait;
	private static UtilisateurDAO utilisateur;
	
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
