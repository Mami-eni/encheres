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
	

	private ArticleDAO art = DAOFactory.getArticleDAO();
	private UtilisateurDAO util = DAOFactory.getUtilisateurDAO();
	
	public void insert(Enchere ench) throws BusinessException {
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
			BusinessException be = new BusinessException();
			be.addError("Insertion dans la base de données impossible");
			throw be;
		}
	}
	
	public List<Enchere> selectByArticle(Article a) throws BusinessException {
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
            BusinessException be = new BusinessException();
			be.addError("Sélection impossible");
			throw be;
        }
        return liste;
    }
	
	public List<Enchere> selectByUser(Utilisateur util) throws BusinessException{
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
			BusinessException be = new BusinessException();
			be.addError("Sélection impossible");
			throw be;
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
			PreparedStatement request = cx.prepareStatement("SELECT no_enchere, date_enchere, montant_enchere, no_article, no_utilisateur "
															+ "FROM encheres ");
			
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
	public Enchere selectById(int id) throws BusinessException {
		Enchere enchere = new Enchere();
		try(Connection cx = Connect.getConnection()){
			PreparedStatement request = cx.prepareStatement("SELECT no_enchere, date_enchere, montant_enchere, no_article, no_utilisateur FROM encheres WHERE no_enchere = ?");
			request.setInt(1, id);
			ResultSet rs = request.executeQuery();
			rs.next();
			enchere = enchereBuilder(rs);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			BusinessException be = new BusinessException();
			be.addError("Sélection impossible");
			throw be;
		}
		return enchere;
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
