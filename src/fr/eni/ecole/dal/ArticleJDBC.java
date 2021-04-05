package fr.eni.ecole.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.bo.Article;
import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.exception.BusinessException;
import fr.eni.ecole.exception.Errors;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ArticleJDBC implements ArticleDAO {
	
	private CategorieDAO cat = DAOFactory.getCategorieDAO();
	private UtilisateurDAO util = DAOFactory.getUtilisateurDAO();
	
	private final String SELECT_ALL_ENCOURS = " SELECT no_article, nom_article, description, date_debut_encheres," + 
											  " date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie " + 
											  " FROM articles WHERE articles.date_debut_encheres <= now() and articles.date_fin_encheres > now()"; 
					
	private final String SELECT_DEBUT_FILTRE = "SELECT no_article, nom_article, description, date_debut_encheres,"
										+ " date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie "
										+ " FROM articles WHERE ";
	private final String SELECT_FILTRE_RADIO_VENTE = "articles.no_utilisateur = ";
	
	private final String SELECT_FILTRE_CHECKBOX_VENTE_ENCOURS=  " AND articles.date_debut_encheres <= now() AND date_fin_encheres > now() ";
	private final String SELECT_FILTRE_CHECKBOX_VENTE_ATTENTE= " AND articles.date_debut_encheres > now()";
	private final String SELECT_FILTRE_CHECKBOX_VENTE_CLOS= " AND articles.date_fin_encheres < now()";
	private final String SELECT_FILTRE_TEXTE= " AND articles.nom_article LIKE ";
	private final String SELECT_FILTRE_CATEGORIE= " AND articles.no_categorie = ";

	public void insert(Article a){
		try(Connection cx = Connect.getConnection()){
			PreparedStatement request = cx.prepareStatement("INSERT INTO articles (nom_article, description, date_debut_encheres, "
					+ "date_fin_encheres, prix_initial, no_utilisateur, no_categorie) "
					+ "VALUES(?,?,?,?,?,?,?)");
			request.setString(1, a.getNom());
			request.setString(2, a.getDescription());
			request.setDate(3, Date.valueOf(a.getDateDebutEncheres()));
			request.setDate(4, Date.valueOf(a.getDateFinEncheres()));
			request.setInt(5, a.getPrixInitial());
			request.setInt(6, a.getUtilisateur().getNumero());
			request.setInt(7, a.getCategorie().getNumero());
			request.executeUpdate();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public Article articleBuilder(ResultSet rs) {
		Article art = new Article();
		try {
			art.setNumero(rs.getInt("no_article"));
			art.setNom(rs.getString("nom_article"));
			art.setDescription(rs.getString("description"));
			art.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
			art.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
			art.setPrixInitial(rs.getInt("prix_initial"));
			art.setPrixVente(rs.getInt("prix_vente"));
			art.setUtilisateur(util.selectById((rs.getInt("no_utilisateur"))));
			art.setCategorie(cat.selectById(rs.getInt("no_categorie")));
			
			// setter l'etat de l'article
			
			if(rs.getDate("date_debut_encheres").toLocalDate().isBefore(LocalDate.now()) && rs.getDate("date_fin_encheres").toLocalDate().isAfter(LocalDate.now()))
			{
				art.setEtatVente("encours");
			}
			
			if( rs.getDate("date_fin_encheres").toLocalDate().isBefore(LocalDate.now()))
			{
				art.setEtatVente("fini");
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return art;
	}

	@Override
	public List<Article> selectAll() {
		List<Article> listeArticles = new ArrayList<Article>();
		Article art = new Article();
		try(Connection cx = Connect.getConnection()){
			PreparedStatement request = cx.prepareStatement(SELECT_ALL_ENCOURS);
			
			ResultSet rs = request.executeQuery();
			while(rs.next())
			{
				art = articleBuilder(rs);
				listeArticles.add(art);
			}
			
			art = articleBuilder(rs);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return listeArticles;
		
		
	}

	@Override
	public Article selectById(int id) {
		Article art = new Article();
		try(Connection cx = Connect.getConnection()){
			PreparedStatement request = cx.prepareStatement("SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie "
					+ "FROM articles WHERE no_article = ?");
			request.setInt(1, id);
			ResultSet rs = request.executeQuery();
			rs.next();
			art = articleBuilder(rs);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return art;
	}

	@Override
	public void update(Article item) {
		try(Connection cx = Connect.getConnection()){
			PreparedStatement request = cx.prepareStatement("UPDATE articles SET "
					+ "nom_article = ?, description = ?, date_debut_encheres = ?, "
					+ "date_fin_encheres = ?, prix_initial = ?, prix_vente = ?, "
					+ "no_utilisateur = ?, no_categorie = ? WHERE no_article = ?");
			request.setString(1, item.getNom());
			request.setString(2, item.getDescription());
			request.setDate(3, Date.valueOf(item.getDateDebutEncheres()));
			request.setDate(4, Date.valueOf(item.getDateFinEncheres()));
			request.setInt(5, item.getPrixInitial());
			request.setInt(6, item.getPrixVente());
			request.setInt(7, item.getUtilisateur().getNumero());
			request.setInt(8, item.getCategorie().getNumero());
			request.setInt(9, item.getNumero());
			request.executeUpdate();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public void delete(Article item) {
		try(Connection cx = Connect.getConnection()){
			PreparedStatement request = cx.prepareStatement("DELETE FROM articles WHERE no_article = ?");
			request.setInt(1, item.getNumero());
			request.executeUpdate();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	@Override
	public List<Article> selectByUser(Utilisateur utilisateur){
        List<Article> liste = new ArrayList<Article>();
        try(Connection cx = Connect.getConnection()){
            PreparedStatement request = cx.prepareStatement("SELECT no_article, "
                    + "nom_article, description, date_debut_encheres, "
                    + "date_fin_encheres, prix_initial, prix_vente, no_utilisateur, "
                    + "no_categorie FROM articles WHERE no_utilisateur = ? ORDER BY no_article");
            request.setInt(1, utilisateur.getNumero());
            ResultSet rs = request.executeQuery();
            while(rs.next()) {
                Article art = articleBuilder(rs);
                liste.add(art);
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return liste;
    }
	
	public List<Article> selectByFiltre(String filtreTexte, String filtreCategorie, String filtreRadio, String[] filtreCheckboxVente,String[] filtreCheckboxAchat, int userId) throws BusinessException {
		/**
		 * selection de la requête à executer selon la valeur des attributs issus des jsp
		 */

		Article art = new Article();
		List<Article> listeArticle = new ArrayList<Article>();


		StringBuilder requeteFinale = new StringBuilder();
		
		

		if(filtreRadio.equalsIgnoreCase("ventes")) 
		{
			requeteFinale.append(SELECT_DEBUT_FILTRE);
//			requeteFinale.append(SELECT_FILTRE_RADIO_VENTE + userId);
			requeteBuilderVente(requeteFinale, filtreTexte, filtreCategorie, filtreRadio, filtreCheckboxVente,  userId);
		}
		else if(filtreRadio.equalsIgnoreCase("achats"))
		{
			requeteBuilderAchat(requeteFinale, filtreTexte, filtreCategorie, filtreRadio, filtreCheckboxAchat);
		}
		else
		{
			requeteFinale.append(SELECT_ALL_ENCOURS);
			
			// ToDo: case to specify and implement
		}

		System.out.println(requeteFinale.toString());
		try(Connection cx = Connect.getConnection()){
			PreparedStatement request = cx.prepareStatement(requeteFinale.toString());

			ResultSet rs = request.executeQuery();
			while(rs.next())
			{
				art = articleBuilder(rs);
				listeArticle.add(art);
			}


		}catch(Exception e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.addError(Errors.SELECT_ENCHERE_NULL);
			throw be;

		}


		return listeArticle;
	}
	
	private void requeteBuilderVente(StringBuilder requete, String filtreTexte, String filtreCategorie, String filtreRadio, String[] filtreCheckboxVente, int userId) {
		
		
		appendRequeteFiltreCheckbox(requete, filtreCheckboxVente, userId);
			
		appendRequetefiltreCategorie(requete, filtreCategorie);
		appendRequeteFiltreTexte(requete, filtreTexte);
	}
	
	private void requeteBuilderAchat(StringBuilder requete, String filtreTexte, String filtreCategorie, String filtreRadio, String[] filtreCheckboxAchat) {
		
		
		
//		appendRequetefiltreCategorie(requete, filtreCategorie);
//		appendRequeteFiltreTexte(requete, filtreTexte);
//		appendRequeteFiltreCheckbox(requete, filtreCheckboxAchat);
		
	}
	
	
	
	private void appendRequetefiltreCategorie(StringBuilder requete, String filtreCategorie)
	{
		if(!filtreCategorie.equalsIgnoreCase("toutes")) 
		{
			requete.append(SELECT_FILTRE_CATEGORIE + filtreCategorie );
		}
	}
	
	private void appendRequeteFiltreTexte(StringBuilder requete, String filtreTexte)
	{
		if(!filtreTexte.isEmpty()) 
		{
			requete.append(SELECT_FILTRE_TEXTE +"'"+ "%"+filtreTexte+"%"+"'");
		}
	}
	
	private void appendRequeteFiltreCheckbox(StringBuilder requete,  String[] filtreCheckboxVente, int userId)
	{
		requete.append("(");
		
		for (int i = 0; i < filtreCheckboxVente.length; i++) {
			if(filtreCheckboxVente[i].equalsIgnoreCase("encours"))
			{
				if(i==0)
				{
					requete.append( SELECT_FILTRE_RADIO_VENTE + userId + SELECT_FILTRE_CHECKBOX_VENTE_ENCOURS);
				}
				else
				{
					requete.append(" OR " + SELECT_FILTRE_RADIO_VENTE + userId + SELECT_FILTRE_CHECKBOX_VENTE_ENCOURS);
					
				}
				
			}
			
			
			if(filtreCheckboxVente[i].equalsIgnoreCase("attente"))
			{
				if(i==0)
				{
					requete.append( SELECT_FILTRE_RADIO_VENTE + userId + SELECT_FILTRE_CHECKBOX_VENTE_ATTENTE);
				}
				else
				{
					requete.append(" OR " + SELECT_FILTRE_RADIO_VENTE + userId + SELECT_FILTRE_CHECKBOX_VENTE_ATTENTE);
					
				}
				
			}
			
			if(filtreCheckboxVente[i].equalsIgnoreCase("clos"))
			{
				if(i==0)
				{
					requete.append(SELECT_FILTRE_RADIO_VENTE + userId + SELECT_FILTRE_CHECKBOX_VENTE_CLOS);
				}
				else
				{
					requete.append(" OR " + SELECT_FILTRE_RADIO_VENTE + userId +SELECT_FILTRE_CHECKBOX_VENTE_CLOS);
					
				}
				
			}
		}
		
		requete.append(")");
	}
}
