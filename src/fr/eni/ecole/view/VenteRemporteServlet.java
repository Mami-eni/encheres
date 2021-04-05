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
 * Servlet implementation class VenteRemporteServlet
 */
@WebServlet("/VenteRemporteServlet")
public class VenteRemporteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BllArticle article = BllArticle.getBllArticle();
	private BllRetrait retrait = BllRetrait.getBllRetrait();
    private BllEnchere enchere = BllEnchere.getBllEnchere();   
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Article art = new Article();
		try {
			art = article.selectById(32);
		} catch (BusinessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		request.setAttribute("article", art);
		Retrait ret = new Retrait();
		try {
			ret = retrait.selectByArticle(art);
		} catch (BusinessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		request.setAttribute("retrait", ret);
		List<Enchere> listeEncheres = new ArrayList<Enchere>();
		try {
			listeEncheres = enchere.selectByArticle(art);
		} catch (BusinessException e1) {
			// TODO Auto-generated catch block
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
		request.setAttribute("enchere", ench);
		request.getRequestDispatcher("/WEB-INF/venteRemporte.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doGet(request, response);
		response.sendRedirect("/encheres");
	}

}
