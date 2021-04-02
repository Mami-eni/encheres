package fr.eni.ecole.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.sun.org.apache.xml.internal.utils.UnImplNode;

import fr.eni.ecole.exception.BusinessException;
import fr.eni.ecole.bo.Utilisateur;

public class UtilisateurJDBC implements UtilisateurDAO {

	private final int CREDIT_OFFERT = 100;
	public Utilisateur utilisateurBuilder(ResultSet rs) {
		Utilisateur util = new Utilisateur();
		try {
			util.setNumero(rs.getInt("no_utilisateur"));
			util.setPseudo(rs.getString("pseudo"));
			util.setNom(rs.getString("nom"));
			util.setPrenom(rs.getString("prenom"));
			util.setEmail(rs.getString("email"));
			util.setTelephone(rs.getString("telephone"));
			util.setRue(rs.getString("rue"));
			util.setCodePostal(rs.getString("code_postal"));
			util.setVille(rs.getString("ville"));
			util.setMotDePasse(rs.getString("mot_de_passe"));
			util.setCredit(rs.getInt("credit"));
			util.setAdministrateur(rs.getBoolean("administrateur"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return util;
	}

	@Override
	public void insert(Utilisateur item) {
		try(Connection cx = Connect.getConnection()){
			PreparedStatement request = cx.prepareStatement("INSERT INTO utilisateurs (pseudo, nom, prenom, "
					+ "email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) "
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			request.setString(1, item.getPseudo());
			request.setString(2, item.getNom());
			request.setString(3, item.getPrenom());
			
			request.setString(4, item.getEmail());
			request.setString(5, item.getTelephone()) ;
			request.setString(6, item.getRue()) ;
			
			request.setString(7, item.getCodePostal());
			request.setString(8, item.getVille());
			request.setString(9, item.getMotDePasse());
			
			request.setInt(10, CREDIT_OFFERT); //crédit offert pour un nouveau utilisateur
			request.setBoolean(11, item.isAdministrateur());
			
			request.executeUpdate();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}	}

	@Override
	public List<Utilisateur> selectAll() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Utilisateur selectById(int id) {
		Utilisateur util = new Utilisateur();
		try (Connection cx = Connect.getConnection()) {
			PreparedStatement request = cx.prepareStatement("SELECT no_utilisateur, pseudo, nom, "
					+ "prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur "
					+ "FROM utilisateurs WHERE no_utilisateur = ?");
			request.setInt(1, id);
			ResultSet rs = request.executeQuery();
			rs.next();
			util = utilisateurBuilder(rs);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return util;
	}

	@Override
	public void update(Utilisateur item) {
		try (Connection cx = Connect.getConnection()) {
			PreparedStatement request = cx.prepareStatement("UPDATE utilisateurs SET "
					+ "pseudo=?, nom=?, "
					+ "prenom=?, email=?, telephone=?, rue=?,"
					+ "code_postal=?, ville=?, mot_de_passe=?"
					+ "WHERE no_utilisateur=?");
			
			request.setString(1, item.getPseudo());
			request.setString(2, item.getNom());
			request.setString(3, item.getPrenom());
			
			request.setString(4, item.getEmail());
			request.setString(5, item.getTelephone()) ;
			request.setString(6, item.getRue()) ;
			
			request.setString(7, item.getCodePostal());
			request.setString(8, item.getVille());
			request.setString(9, item.getMotDePasse());
			
			request.setInt(10, item.getNumero());

			request.executeUpdate();
		}catch(Exception e) {
				System.out.println(e.getMessage());
			}
	}

	/**
	 * Methode non utilisée.
	 */
	@Override
	public void delete(Utilisateur item) {
		try (Connection cx = Connect.getConnection()){
			PreparedStatement request = cx.prepareStatement("DELETE FROM utilisateurs WHERE no_utilisateur=?");
			request.setInt(1, item.getNumero());
			request.executeUpdate();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	/**
	 * Permet de trouver un utilisateur grâce à son pseudo et mot de passe
	 * @param login
	 * @param password
	 * @return
	 * @throws BusinessException
	 */
	public Utilisateur find(String pseudo, String motDePasse) throws BusinessException {
		try (Connection cx = Connect.getConnection()) {
			PreparedStatement pstmt = cx.prepareStatement("SELECT no_utilisateur, pseudo, nom,"  
										+ "prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur "  
										+ "FROM utilisateurs WHERE pseudo=? AND mot_de_passe=?");
			pstmt.setString(1, pseudo);
			pstmt.setString(2, motDePasse);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				Utilisateur u = utilisateurBuilder(rs);
				return u;
			} else {
				// Utilisateur non trouvé
				BusinessException be = new BusinessException();
				be.addError("Pseudo ou Mot de passe inconnu");
				throw be;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			e.getMessage();
			BusinessException be = new BusinessException();
			be.addError("ERROR DB - " + e.getMessage());
			throw be;

		}
	}
}
