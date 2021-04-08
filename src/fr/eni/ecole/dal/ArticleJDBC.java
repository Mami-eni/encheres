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
/**
 * cette classe implémente toutes les méthodes d'accès à la table articles de la base de données
 */

public class ArticleJDBC implements ArticleDAO {
	
	private CategorieDAO cat = DAOFactory.getCategorieDAO();
	private UtilisateurDAO util = DAOFactory.getUtilisateurDAO();
	
	
	private final String SELECT_ALL_ENCOURS = " SELECT no_article, nom_article, description, date_debut_encheres," + 
											  " date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie " + 
											  " FROM articles WHERE articles.date_debut_encheres <= now() and articles.date_fin_encheres > now()"; 
					
	private final String SELECT_DEBUT_FILTRE_VENTE = "SELECT no_article, nom_article, description, date_debut_encheres,"
										+ " date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie "
										+ " FROM articles WHERE ";
	
	private final String SELECT_DEBUT_FILTRE_ACHAT = "SELECT DISTINCT articles.no_article, articles.nom_article, articles.description, articles.date_debut_encheres,"
													+ " articles.date_fin_encheres, articles.prix_initial, articles.prix_vente, articles.no_utilisateur, articles.no_categorie "
													+ " FROM articles";
	
	private final String SELECT_FILTRE_RADIO_VENTE = " articles.no_utilisateur = ";
	
	private final String SELECT_FILTRE_CHECKBOX_VENTE_ENCOURS=  " AND articles.date_debut_encheres <= now() AND date_fin_encheres > now() ";
	private final String SELECT_FILTRE_CHECKBOX_VENTE_ATTENTE= " AND articles.date_debut_encheres > now()";
	private final String SELECT_FILTRE_CHECKBOX_VENTE_CLOS= " AND articles.date_fin_encheres < now()";
	
	private final String SELECT_FILTRE_TEXTE= " AND articles.nom_article LIKE ";
	private final String SELECT_FILTRE_CATEGORIE= " AND articles.no_categorie = ";
	
	private final String SELECT_FILTRE_CHECKBOX_ACHAT_OUVERT = " AND articles.date_debut_encheres <=now() " + " AND articles.date_fin_encheres > now() ";
	
	private final String SELECT_FILTRE_CHECKBOX_ACHAT_MES_ENCHERES = " JOIN encheres on articles.no_article = encheres.no_article"
															+ " WHERE ( articles.date_debut_encheres <=now()";
																	
	private final String SELECT_FILTRE_CHECKBOX_ACHAT_MES_ENCHERES_CLOSES = " JOIN encheres on articles.no_article = encheres.no_article"
																			+ " WHERE ( articles.date_fin_encheres <now()";
	
															
	public void insert(Article a) throws BusinessException{
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
			BusinessException be = new BusinessException();
			be.addError(Errors.ERREUR_INSERT);
			throw be;
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


			if((rs.getDate("date_debut_encheres").toLocalDate().isBefore(LocalDate.now()) || rs.getDate("date_debut_encheres").toLocalDate().isEqual(LocalDate.now()) )  && rs.getDate("date_fin_encheres").toLocalDate().isAfter(LocalDate.now()))
			{
				art.setEtatVente("encours");
			}
			
						
			else if( rs.getDate("date_fin_encheres").toLocalDate().isBefore(LocalDate.now()) || rs.getDate("date_fin_encheres").toLocalDate().isEqual(LocalDate.now()))
			{
				art.setEtatVente("fini");
			}
			
			else if( rs.getDate("date_debut_encheres").toLocalDate().isAfter(LocalDate.now()))
			{
				art.setEtatVente("non_debuté");
			}

			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return art;
	}

	@Override
	public List<Article> selectAll() throws BusinessException {
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
			

		}catch(Exception e) {
			System.out.println(e.getMessage());
			BusinessException be = new BusinessException();
			be.addError(Errors.ERREUR_SELECT);
			throw be;
		}
		
		return listeArticles;
		
		
	}

	@Override
	public Article selectById(int id) throws BusinessException {
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
			BusinessException be = new BusinessException();
			be.addError(Errors.ERREUR_SELECT);
			throw be;
		}
		return art;
	}

	@Override
	public void update(Article item) throws BusinessException {
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
			BusinessException be = new BusinessException();
			be.addError(Errors.ERREUR_UPDATE);
			throw be;
		}	
	}
	
	public void setPrixVente(Article item) throws BusinessException {
		try(Connection cx = Connect.getConnection()){
			PreparedStatement request = cx.prepareStatement("UPDATE articles SET prix_vente = ? WHERE no_article = ?");
			request.setInt(1, item.getPrixVente());
			request.setInt(2, item.getNumero());
			request.executeUpdate();
		}catch(Exception e) {
			System.out.println(e.getMessage());
			BusinessException be = new BusinessException();
			be.addError(Errors.ERREUR_UPDATE);
			throw be;
		}	
	}

	@Override
	public void delete(Article item) throws BusinessException {
		try(Connection cx = Connect.getConnection()){
			PreparedStatement request = cx.prepareStatement("DELETE FROM articles WHERE no_article = ?");
			request.setInt(1, item.getNumero());
			request.executeUpdate();
		}catch(Exception e) {
			System.out.println(e.getMessage());
			BusinessException be = new BusinessException();
			be.addError(Errors.ERREUR_DELETE);
			throw be;
		}
		
	}
	
	@Override
	public List<Article> selectByUser(Utilisateur utilisateur) throws BusinessException{
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
            BusinessException be = new BusinessException();
			be.addError(Errors.ERREUR_SELECT);
			throw be;
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
		
		final String  SELECT_FILTRE_CHECKBOX_ACHAT_CUMUL = " join encheres ON articles.no_article = encheres.no_article " 
														  + " WHERE ( articles.no_utilisateur <> " + userId
														  +	" AND articles.date_debut_encheres <= now() "
														  + " AND articles.date_fin_encheres > now() "
														  + " OR articles.no_utilisateur <> " + userId
														  + " AND articles.date_debut_encheres <=now() AND articles.date_fin_encheres < now() "
														  + " AND encheres.no_utilisateur = " + userId;

		if(("ventes").equalsIgnoreCase(filtreRadio)) 
		{
			requeteFinale.append(SELECT_DEBUT_FILTRE_VENTE);

			requeteBuilderVente(requeteFinale, filtreTexte, filtreCategorie, filtreRadio, filtreCheckboxVente,  userId);
		}
		else if(("achats").equalsIgnoreCase(filtreRadio))
		{
			requeteFinale.append(SELECT_DEBUT_FILTRE_ACHAT);
			requeteBuilderAchat(requeteFinale, filtreTexte, filtreCategorie, filtreRadio, filtreCheckboxAchat, userId, SELECT_FILTRE_CHECKBOX_ACHAT_CUMUL);
		}
		else
		{
			requeteFinale.append(SELECT_ALL_ENCOURS);
			appendRequetefiltreCategorie(requeteFinale,  filtreCategorie);
			appendRequeteFiltreTexte( requeteFinale, filtreTexte);
			
		}
		// affichage requetes 
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
			be.addError(Errors.SELECT_FILTRE_NULL);
			throw be;

		}


		return listeArticle;
	}
	
	private void requeteBuilderVente(StringBuilder requete, String filtreTexte, String filtreCategorie, String filtreRadio, String[] filtreCheckboxVente, int userId) {
		
		
		appendRequeteFiltreCheckboxVente(requete, filtreCheckboxVente, userId);
			
		appendRequetefiltreCategorie(requete, filtreCategorie);
		appendRequeteFiltreTexte(requete, filtreTexte);
	}
	
	private void requeteBuilderAchat(StringBuilder requete, String filtreTexte, String filtreCategorie, String filtreRadio, String[] filtreCheckboxAchat, int userId, String SELECT_FILTRE_CHECKBOX_ACHAT_CUMUL) {
		
		
		appendRequeteFiltreCheckboxAchat(requete, filtreCheckboxAchat, userId, SELECT_FILTRE_CHECKBOX_ACHAT_CUMUL);
		appendRequetefiltreCategorie(requete, filtreCategorie);
		appendRequeteFiltreTexte(requete, filtreTexte);

		
	}
	
	
	
	private void appendRequetefiltreCategorie(StringBuilder requete, String filtreCategorie)
	{
		if(!("toutes").equalsIgnoreCase(filtreCategorie)) 
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
	
	private void appendRequeteFiltreCheckboxVente(StringBuilder requete,  String[] filtreCheckboxVente, int userId)
	{
		requete.append("(");
		
		for (int i = 0; i < filtreCheckboxVente.length; i++) {
			if(("encours").equalsIgnoreCase(filtreCheckboxVente[i]))
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
			
			
			if(("attente").equalsIgnoreCase(filtreCheckboxVente[i]))
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
			
			if(("clos").equalsIgnoreCase(filtreCheckboxVente[i]))
			{
				if(i==0)
				{
					requete.append(SELECT_FILTRE_RADIO_VENTE + userId + SELECT_FILTRE_CHECKBOX_VENTE_CLOS);
				}
				else
				{
					requete.append(" OR " + SELECT_FILTRE_RADIO_VENTE + userId + SELECT_FILTRE_CHECKBOX_VENTE_CLOS);
					
				}
				
			}
		}
		
		requete.append(")");
	}
	
	
	
	private void appendRequeteFiltreCheckboxAchat(StringBuilder requete,  String[] filtreCheckboxAchat, int userId,String SELECT_FILTRE_CHECKBOX_ACHAT_CUMUL)
	{

		
		if(1==filtreCheckboxAchat.length)
		{
			switch (filtreCheckboxAchat[0]) {
			case "ouvertes":
				requete.append( " WHERE ( articles.no_utilisateur <> " + userId + SELECT_FILTRE_CHECKBOX_ACHAT_OUVERT);
				break;
			case "mesEncheres":
				requete.append( SELECT_FILTRE_CHECKBOX_ACHAT_MES_ENCHERES + " AND encheres.no_utilisateur = " + userId);
				break;
				
			case "enchereObtenues":
				requete.append(SELECT_FILTRE_CHECKBOX_ACHAT_MES_ENCHERES_CLOSES + " AND encheres.no_utilisateur = " + userId );
				break;
			default:
				break;
			}
		}
			
			else if(2==filtreCheckboxAchat.length)
			{
				if("ouvertes".equalsIgnoreCase(filtreCheckboxAchat[0]) && "mesEncheres".equalsIgnoreCase(filtreCheckboxAchat[1]))
				{
					requete.append(SELECT_FILTRE_CHECKBOX_ACHAT_CUMUL);
				}
				
				else if("ouvertes".equalsIgnoreCase(filtreCheckboxAchat[0]) && "enchereObtenues".equalsIgnoreCase(filtreCheckboxAchat[1]))
					
				{
					requete.append(SELECT_FILTRE_CHECKBOX_ACHAT_CUMUL);			
				}
				
				else if("mesEncheres".equalsIgnoreCase(filtreCheckboxAchat[0]) && "enchereObtenues".equalsIgnoreCase(filtreCheckboxAchat[1]))
				{
					requete.append( SELECT_FILTRE_CHECKBOX_ACHAT_MES_ENCHERES + " AND encheres.no_utilisateur = " + userId);
				}
				
				
			}
		
			else if(3==filtreCheckboxAchat.length)
			{
				requete.append( SELECT_FILTRE_CHECKBOX_ACHAT_CUMUL );			

			}
			
		
		
		requete.append(" ) ");
	}
	
	
	
}
