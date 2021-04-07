/**
 * 
 */
package fr.eni.ecole.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.dal.Connect;
import fr.eni.ecole.dal.DAOFactory;
import fr.eni.ecole.dal.UtilisateurDAO;
import fr.eni.ecole.exception.BusinessException;

/**
 * @author sun
 *
 */
public class UtilisateurJDBCTest {
	Utilisateur user ;
	UtilisateurDAO dao;
	List<Utilisateur> utilisateurs;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Connect.DEV_MODE = false;
		user = new Utilisateur();
		dao = DAOFactory.getUtilisateurDAO();
		utilisateurs = dao.selectAll();
		user.setAdministrateur(false);
		user.setCodePostal("00000");
		user.setCredit(0);
		user.setEmail("asda@asdas.com");
		user.setMotDePasse("mdp");
		user.setNom("null");
		user.setTelephone("012345678");
		user.setPrenom("pointer");
		user.setPseudo("abcd");
		user.setRue("12 av Paris");
		user.setVille("Rennes");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		Connect.DEV_MODE = true;

	}

	/**
	 * Test method for {@link fr.eni.ecole.dal.UtilisateurJDBC#insert(fr.eni.ecole.bo.Utilisateur)}.
	 */
	@Test
	public void testInsert() {
				
		try {
			int tailleAvant = utilisateurs.size();
			dao.insert(user);
			List<Utilisateur> utilisateursApres = dao.selectAll();
			int tailleApres = utilisateursApres.size();
			assertEquals(1, tailleApres - tailleAvant);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	/**
	 * Test method for {@link fr.eni.ecole.dal.UtilisateurJDBC#selectAll()}.
	 */
	@Test
	public void testSelectAll() {
		try {
			int tailleAvant = utilisateurs.size();
			dao.insert(user);
			List<Utilisateur> utilisateursApres = dao.selectAll();
			int tailleApres = utilisateursApres.size();
			assertEquals(1, tailleApres - tailleAvant);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link fr.eni.ecole.dal.UtilisateurJDBC#selectById(int)}.
	 */
	@Test
	public void testSelectById() {
		try {
			Utilisateur user1 = utilisateurs.get(0);
			Utilisateur userTest = dao.selectById(user1.getNumero());
			assertEquals(user1, userTest);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link fr.eni.ecole.dal.UtilisateurJDBC#update(fr.eni.ecole.bo.Utilisateur)}.
	 */
	@Test
	public void testUpdate() {
		Utilisateur userToUpdate = utilisateurs.get(0);
		userToUpdate.setNom("Cao-Son");
		try {
			dao.update(userToUpdate);
			Utilisateur userAfterUpdate = dao.selectById(userToUpdate.getNumero());
			assertEquals(userToUpdate, userAfterUpdate);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link fr.eni.ecole.dal.UtilisateurJDBC#delete(fr.eni.ecole.bo.Utilisateur)}.
	 */
	@Test
	public void testDelete() {
		
		try {
			int tailleAvant = utilisateurs.size();
			dao.delete(utilisateurs.get(0));
			List<Utilisateur> utilisateursApres = dao.selectAll();
			int tailleApres = utilisateursApres.size();
			assertEquals(1, tailleAvant - tailleApres);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}


}
