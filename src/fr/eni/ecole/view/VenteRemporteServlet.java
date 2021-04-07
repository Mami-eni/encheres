package fr.eni.ecole.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.ecole.bll.BllArticle;
import fr.eni.ecole.bll.BllEnchere;
import fr.eni.ecole.bll.BllRetrait;
import fr.eni.ecole.bo.Article;
import fr.eni.ecole.bo.Enchere;
import fr.eni.ecole.bo.Retrait;
import fr.eni.ecole.exception.BusinessException;

/**
 * Cette classe gère l'envoi de données d'affichage
 */
@WebServlet("/VenteRemporteServlet")
public class VenteRemporteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BllArticle article = BllArticle.getBllArticle();
	private BllRetrait retrait = BllRetrait.getBllRetrait();
    private BllEnchere enchere = BllEnchere.getBllEnchere();   
    
    /* passage en attributs de requête des éléments nécessaires à l'affichage des données de la page */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		BusinessException error = new BusinessException();
		int idArticle = Integer.parseInt(request.getParameter("article"));
		Article art = new Article();
		try {
			art = article.selectById(idArticle);
		} catch (BusinessException e1) {
			for(String s : e1.getErrors()) {
				error.addError(s);
			}
			e1.printStackTrace();
		}
		request.setAttribute("article", art);
		Retrait ret = new Retrait();
		try {
			ret = retrait.selectByArticle(art);
		} catch (BusinessException e1) {
			for(String s : e1.getErrors()) {
				error.addError(s);
			}
			e1.printStackTrace();
		}
		request.setAttribute("retrait", ret);
		List<Enchere> listeEncheres = new ArrayList<Enchere>();
		try {
			listeEncheres = enchere.selectByArticle(art);
		} catch (BusinessException e1) {
			for(String s : e1.getErrors()) {
				error.addError(s);
			}
			e1.printStackTrace();
		}
		int montantMax = 0;
		Enchere ench = new Enchere();
		for(Enchere e : listeEncheres) {
			if(e.getMontant() > montantMax) {
				montantMax = e.getMontant();
				ench = e;
			}
		}
		request.setAttribute("errors", error.getErrors());
		request.setAttribute("enchere", ench);
		request.getRequestDispatcher("/WEB-INF/venteRemporte.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/encheres");
	}

}
