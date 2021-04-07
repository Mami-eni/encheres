package fr.eni.ecole.tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.eni.ecole.bo.Categorie;
import fr.eni.ecole.dal.CategorieDAO;
import fr.eni.ecole.dal.Connect;
import fr.eni.ecole.dal.DAOFactory;
import fr.eni.ecole.exception.BusinessException;

public class testCategorieJDBC {
	

	@Before
	public void setUp() throws Exception {
		Connect.DEV_MODE = false;
	}

	@After
	public void tearDown() throws Exception {
		Connect.DEV_MODE = true;
	}

	@Test
	public void testSelectAll() throws BusinessException {
		CategorieDAO cat = DAOFactory.getCategorieDAO();
		List<Categorie> liste = cat.selectAll();
	}

	@Test
	public void testSelectByName() throws BusinessException {
		CategorieDAO cat = DAOFactory.getCategorieDAO();
		Categorie test = cat.selectByName("informatique");
	}

	@Test
	public void testSelectById() throws BusinessException {
		CategorieDAO cat = DAOFactory.getCategorieDAO();
		Categorie test = cat.selectById(3);
	}

}
