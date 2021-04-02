package fr.eni.ecole.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.bo.Article;
import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.exception.BusinessException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ArticleJDBC implements ArticleDAO {
	
	private CategorieDAO cat = DAOFactory.getCategorieDAO();
	private UtilisateurDAO util = DAOFactory.getUtilisateurDAO();

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
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return art;
	}

	@Override
	public List<Article> selectAll() {
		// TODO Auto-generated method stub
		return null;
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
}
