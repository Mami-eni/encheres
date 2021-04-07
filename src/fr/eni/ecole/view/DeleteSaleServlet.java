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
import fr.eni.ecole.bll.BllCategorie;
import fr.eni.ecole.bll.BllRetrait;
import fr.eni.ecole.bo.Article;
import fr.eni.ecole.bo.Categorie;
import fr.eni.ecole.bo.Retrait;
import fr.eni.ecole.exception.BusinessException;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/DeleteSaleServlet")
public class DeleteSaleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BllArticle article = BllArticle.getBllArticle();
    private BllRetrait retrait = BllRetrait.getBllRetrait();
    private BllCategorie cat = BllCategorie.getBllCategorie();
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		BusinessException error = new BusinessException();
		int articleId = Integer.parseInt(request.getParameter("article"));
		Article art = new Article();
		try {
			art = article.selectById(articleId);
		} catch (BusinessException e) {
			for(String s : e.getErrors()) {
				error.addError(s);
			}
			e.printStackTrace();
		}
		Retrait ret = new Retrait();
		try {
			ret = retrait.selectByArticle(art);
		} catch (BusinessException e) {
			for(String s : e.getErrors()) {
				error.addError(s);
			}
			e.printStackTrace();
		}
		try {
			retrait.delete(ret);
		} catch (BusinessException e) {
			for(String s : e.getErrors()) {
				error.addError(s);
			}
			e.printStackTrace();
		}
		try {
			article.delete(art);
		} catch (BusinessException e) {
			for(String s : e.getErrors()) {
				error.addError(s);
			}
			e.printStackTrace();
		}
		if(error.getErrors() != null) {
			List<Categorie> listeCat = new ArrayList<Categorie>();
			try {
				listeCat = cat.selectAll();
			} catch (BusinessException e) {
				for(String s : e.getErrors()) {
					error.addError(s);
				}
				e.printStackTrace();
			}
			request.setAttribute("categories", listeCat);
			request.setAttribute("article", art);
			request.setAttribute("retrait", ret);
			request.setAttribute("dateDebut", art.getDateDebutEncheres().toString());
			request.setAttribute("dateFin", art.getDateFinEncheres().toString());
			request.setAttribute("errors", error.getErrors());
			request.getRequestDispatcher("WEB-INF/updateVente.jsp").forward(request, response);
		}else {
		response.sendRedirect("/encheres");
		}
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
