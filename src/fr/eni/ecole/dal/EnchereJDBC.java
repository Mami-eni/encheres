package fr.eni.ecole.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.bo.Article;
import fr.eni.ecole.bo.Enchere;
import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.exception.BusinessException;
import fr.eni.ecole.exception.Errors;

public class EnchereJDBC implements EnchereDAO {
	
	private final String SELECT_ALL_ENCOURS = "SELECT * FROM encheres"
			     							  + " INNER JOIN articles on articles.no_article = encheres.no_article" 
			     							  + " WHERE date_debut_encheres<= now() AND date_fin_encheres>now()";
	private final String SELECT_ALL_ENCOURS_FILTRE_TEXTE = "SELECT * FROM encheres"
			  + " INNER JOIN articles on articles.no_article = encheres.no_article" 
			  + " INNER JOIN categories on articles.no_categorie = categories.no_categorie"
			  + " where date_debut_encheres<= now() AND date_fin_encheres>now()  "
			  + " AND articles.nom_article LIKE ?";
	private final String SELECT_ALL_ENCOURS_FILTRE_CATEGORIE = "SELECT * FROM encheres"
			  + " INNER JOIN articles on articles.no_article = encheres.no_article" 
			  + " INNER JOIN categories on articles.no_categorie = categories.no_categorie"
			  + " where date_debut_encheres<= now() AND date_fin_encheres>now()"
			  + " AND categories.libelle = ?";
	private final String SELECT_ALL_ENCOURS_FILTRE_CATEGORIE_TEXTE = "SELECT * FROM encheres"
			  + " INNER JOIN articles on articles.no_article = encheres.no_article"
			  + " INNER JOIN categories on articles.no_categorie = categories.no_categorie"
			  + " where date_debut_encheres<= now() AND date_fin_encheres>now() "
			  + " AND articles.nom_article LIKE ? AND categories.libelle = ?";
			  

	
	private ArticleDAO art = DAOFactory.getArticleDAO();
	private UtilisateurDAO util = DAOFactory.getUtilisateurDAO();
	
	public void insert(Enchere ench) {
		try(Connection cx = Connect.getConnection()){
			PreparedStatement request = cx.prepareStatement("INSERT INTO encheres "
					+ "(date_enchere, montant_enchere, no_article, no_utilisateur) VALUES(?,?,?,?)");
			request.setTimestamp(1, ench.getDate());
			request.setInt(2, ench.getMontant());
			request.setInt(3, ench.getArticle().getNumero());
			request.setInt(4, ench.getUtilisateur().getNumero());
			request.executeUpdate();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public List<Enchere> selectByArticle(Article a) {
        List<Enchere> liste = new ArrayList<Enchere>();
       
        try(Connection cx = Connect.getConnection()){
            PreparedStatement request = cx.prepareStatement("SELECT no_enchere, date_enchere, montant_enchere, no_article, no_utilisateur"
                    + " FROM encheres WHERE no_article = ?");
            request.setInt(1, a.getNumero());
            ResultSet rs = request.executeQuery();
            while(rs.next()) {
            Enchere ench = enchereBuilder(rs);
            liste.add(ench);
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return liste;
    }
	
	public List<Enchere> selectByUser(Utilisateur util){
		List<Enchere> liste = new ArrayList<Enchere>();
		try(Connection cx = Connect.getConnection()){
			PreparedStatement request = cx.prepareStatement("SELECT no_enchere, date_enchere, montant_enchere, no_article, no_utilisateur "
					+ "FROM encheres WHERE no_utilisateur = ?");
			request.setInt(1, util.getNumero());
			ResultSet rs = request.executeQuery();
			while(rs.next()) {
				Enchere ench = enchereBuilder(rs);
				liste.add(ench);
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return liste;
	}
	
	public Enchere enchereBuilder(ResultSet rs) {
		Enchere ench = new Enchere();
		try {
			ench.setNumero(rs.getInt("no_enchere"));
			ench.setDate(rs.getTimestamp("date_enchere"));
			ench.setMontant(rs.getInt("montant_enchere"));
			ench.setArticle(art.selectById(rs.getInt("no_article")));
			ench.setUtilisateur(util.selectById(rs.getInt("no_utilisateur")));	
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return ench;
	}

	@Override
	public List<Enchere> selectAll() throws BusinessException {
		Enchere ench = new Enchere();
		List<Enchere> listeEnchere = new ArrayList<Enchere>();
		try(Connection cx = Connect.getConnection()){
			PreparedStatement request = cx.prepareStatement(SELECT_ALL_ENCOURS);
			
			ResultSet rs = request.executeQuery();
			while(rs.next())
			{
				ench = enchereBuilder(rs);
				listeEnchere.add(ench);
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.addError(Errors.SELECT_ENCHERE_NULL);
			throw be;
			
		}
		
		return listeEnchere;
	}
	
	@Override
	public List<Enchere> selectByFiltre(String filtreTexte, String filtreCategorie) throws BusinessException {
		Enchere ench = new Enchere();
		List<Enchere> listeEnchere = new ArrayList<Enchere>();
		String requeteChoisie;
		boolean isFiltreCategorie= false;
		boolean isFiltreTexte= false;
		boolean isFiltreTexteCategorie= false;
		boolean isNoFiltre= false;
		
		
		if(filtreTexte.isEmpty())
		{
			if(filtreCategorie.equalsIgnoreCase("toutes"))
			{
				requeteChoisie= SELECT_ALL_ENCOURS;
				isNoFiltre=true;
				
				// selectAll encours
			}
			else
			{
				requeteChoisie= SELECT_ALL_ENCOURS_FILTRE_CATEGORIE;
				isFiltreCategorie=true;
				// select by categorie		
			}
		}
		else
		{
			if(filtreCategorie.equalsIgnoreCase("toutes"))
			{
				requeteChoisie = SELECT_ALL_ENCOURS_FILTRE_TEXTE;
				isFiltreTexte=true;
				// select by name of article
			}
			else
			{
				requeteChoisie = SELECT_ALL_ENCOURS_FILTRE_CATEGORIE_TEXTE;
				isFiltreTexteCategorie=true;
				// select by categorie and name of article
				
			}
			
		}
			
		try(Connection cx = Connect.getConnection()){
			PreparedStatement request = cx.prepareStatement(requeteChoisie);
			
			
			
			if(isFiltreCategorie)
			{
				request.setString(1, filtreCategorie);
			}
			
			else if(isFiltreTexte)
			{
				request.setString(1, "%"+filtreTexte+"%");
			}
			
			else if(isFiltreTexteCategorie)
			{
				request.setString(1, "%"+filtreTexte+"%");
				request.setString(2, filtreCategorie);
			}
			
			ResultSet rs = request.executeQuery();
			while(rs.next())
			{
				ench = enchereBuilder(rs);
				listeEnchere.add(ench);
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.addError(Errors.SELECT_ENCHERE_NULL);
			throw be;
			
		}
		
		return listeEnchere;
	}

	@Override
	public Enchere selectById(int id) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void update(Enchere item) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void delete(Enchere item) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	
	

	
	
}
