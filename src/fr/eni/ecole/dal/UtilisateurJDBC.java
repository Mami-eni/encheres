package fr.eni.ecole.dal;
/**
 * Connexion avec la table utilisateurs sur la base de donnée
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.exception.BusinessException;
import fr.eni.ecole.bo.Utilisateur;

public class UtilisateurJDBC implements UtilisateurDAO {

	private final int CREDIT_OFFERT = 100;

	/**
	 * Permet de créer un utilisateur à partir d'une requête SQL
	 * 
	 * @param id
	 * @return Utilisateur
	 * @throws BusinessException
	 */
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
	/**
	 * Permet de trouver un utilisateur grâce à son id de BDD
	 * 
	 * @param item
	 * @throws BusinessException
	 */
	@Override
	public void insert(Utilisateur item) throws BusinessException {
		try (Connection cx = Connect.getConnection()) {
			PreparedStatement request = cx.prepareStatement("INSERT INTO utilisateurs (pseudo, nom, prenom, "
					+ "email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) "
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			request.setString(1, item.getPseudo());
			request.setString(2, item.getNom());
			request.setString(3, item.getPrenom());

			request.setString(4, item.getEmail());
			request.setString(5, item.getTelephone());
			request.setString(6, item.getRue());

			request.setString(7, item.getCodePostal());
			request.setString(8, item.getVille());
			request.setString(9, item.getMotDePasse());

			request.setInt(10, CREDIT_OFFERT); // cr�dit offert pour un nouveau utilisateur
			request.setBoolean(11, item.isAdministrateur());

			request.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			BusinessException be = new BusinessException();
			be.addError("Insertion dans la base de données impossible");
			throw be;
		}
	}

	/**
	 * Permet de trouver tous les utilisateurs dans la BDD
	 * 
	 * @return List<Utilisateur>
	 * @throws BusinessException
	 */
	@Override
	public List<Utilisateur> selectAll() throws BusinessException {
		List<Utilisateur> users = new ArrayList<Utilisateur>();
		try (Connection cx = Connect.getConnection()) {
			Statement request = cx.createStatement();
			ResultSet rs = request.executeQuery("SELECT no_utilisateur, pseudo, nom, "
					+ "prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur "
					+ "FROM utilisateurs");
			Utilisateur currentUser = new Utilisateur();
			while (rs.next()) {
				currentUser = utilisateurBuilder(rs);
				users.add(currentUser);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			BusinessException be = new BusinessException();
			be.addError("Sélection impossible");
			throw be;
		}
		return users;
	}

	/**
	 * Permet de trouver un utilisateur grâce à son id de BDD
	 * 
	 * @param id
	 * @return Utilisateur
	 * @throws BusinessException
	 */
	@Override
	public Utilisateur selectById(int id) throws BusinessException {
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
			BusinessException be = new BusinessException();
			be.addError("Sélection impossible");
			throw be;
		}
		return util;
	}
	
	/**
	 * Mise à jour des données d'un utilisateur
	 * 
	 * @param item
	 * @throws BusinessException
	 */
	@Override
	public void update(Utilisateur item) throws BusinessException {
		try (Connection cx = Connect.getConnection()) {

			PreparedStatement request = cx.prepareStatement(
					"UPDATE utilisateurs SET " + "pseudo=?, nom=?, " + "prenom=?, email=?, telephone=?, rue=?,"
							+ "code_postal=?, ville=?, mot_de_passe=?" + "WHERE no_utilisateur=?");

			request.setString(1, item.getPseudo());
			request.setString(2, item.getNom());
			request.setString(3, item.getPrenom());

			request.setString(4, item.getEmail());
			request.setString(5, item.getTelephone());
			request.setString(6, item.getRue());

			request.setString(7, item.getCodePostal());
			request.setString(8, item.getVille());
			request.setString(9, item.getMotDePasse());

			request.setInt(10, item.getNumero());

			request.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			BusinessException be = new BusinessException();
			be.addError("Mise à jour impossible");
			throw be;
		}
	}

	/**
	 * Supprimer un utilisateur
	 * 
	 * @param item
	 * @throws BusinessException
	 */
	@Override
	public void delete(Utilisateur item) throws BusinessException {
		try (Connection cx = Connect.getConnection()) {
			//delete l'utilisateur
			PreparedStatement requestUtilisateur = cx.prepareStatement("DELETE FROM utilisateurs WHERE no_utilisateur=?");
			requestUtilisateur.setInt(1, item.getNumero());
			requestUtilisateur.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			BusinessException be = new BusinessException();
			be.addError("Suppression impossible");
			throw be;
		}
	}

	/**
	 * Permet de trouver un utilisateur gr�ce � son pseudo et mot de passe
	 * 
	 * @param login
	 * @param password
	 * @return Utilisateur
	 * @throws BusinessException
	 * @throws ClassNotFoundException 
	 */
	@Override
	public Utilisateur find(String pseudo, String motDePasse) throws BusinessException, ClassNotFoundException {
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
				// Utilisateur non trouv�
				BusinessException be = new BusinessException();
				be.addError("Pseudo ou Mot de passe inconnu");
				throw be;
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			BusinessException be = new BusinessException();
			be.addError("Pseudo ou Mot de passe inconnu");
			throw be;

		}
	}
}
