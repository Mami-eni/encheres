package fr.eni.ecole.bll;


import java.util.List;

import fr.eni.ecole.bo.Categorie;
import fr.eni.ecole.dal.CategorieDAO;
import fr.eni.ecole.dal.DAOFactory;
import fr.eni.ecole.exception.BusinessException;

public class BllCategorie {
	
	private static BllCategorie instance;
	private static CategorieDAO categorie;
	
	private BllCategorie() {
		categorie = DAOFactory.getCategorieDAO();
	}
	
	public static BllCategorie getBllCategorie() {
		if(instance == null) {
			instance = new BllCategorie();
		}
		return instance;
	}
	
	public List<Categorie> selectAll() throws BusinessException{
		return categorie.selectAll();
	}
	
	public Categorie selectByName(String nom) throws BusinessException{
		return categorie.selectByName(nom);
	}
	
	public Categorie selectById(int id) throws BusinessException{
		return categorie.selectById(id);
	}
	
	
}
