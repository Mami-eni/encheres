package fr.eni.ecole.view;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.ecole.bll.BllArticle;
import fr.eni.ecole.bll.BllCategorie;
import fr.eni.ecole.bll.BllRetrait;
import fr.eni.ecole.bll.BllUtilisateur;
import fr.eni.ecole.bo.Article;
import fr.eni.ecole.bo.Categorie;
import fr.eni.ecole.bo.Retrait;
import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.exception.BusinessException;

/**
 * Servlet implementation class UpdateSaleServlet
 */
@WebServlet("/UpdateSaleServlet")
public class UpdateSaleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BllUtilisateur util = BllUtilisateur.getBllUtilisateur();
	private BllCategorie cat = BllCategorie.getBllCategorie();
	private BllArticle article = BllArticle.getBllArticle();
	private BllRetrait retrait = BllRetrait.getBllRetrait();
       
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		BusinessException error = new BusinessException();
		Article art = new Article();
		int articleId = Integer.parseInt(request.getParameter("article"));
		try {
			art = article.selectById(articleId);
		} catch (BusinessException e) {
			for(String s : e.getErrors()) {
				error.addError(s);
			}
			e.printStackTrace();
		}
		session.setAttribute("articleId", art.getNumero());
		request.setAttribute("article", art);
		String debut = art.getDateDebutEncheres().toString();
		String fin = art.getDateFinEncheres().toString();
		request.setAttribute("dateDebut", debut);
		request.setAttribute("dateFin", fin);
		Retrait ret = new Retrait();
		try {
			ret = retrait.selectByArticle(art);
		} catch (BusinessException e) {
			for(String s : e.getErrors()) {
				error.addError(s);
			}
			e.printStackTrace();
		}
		request.setAttribute("retrait", ret);
		List<Categorie> listeCat = new ArrayList<Categorie>();
		try {
			listeCat = cat.selectAll();
		} catch (BusinessException e) {
			for(String s : e.getErrors()) {
				error.addError(s);
			}
			e.printStackTrace();
		}
		request.setAttribute("errors", error.getErrors());
		request.setAttribute("categories", listeCat);
		request.getRequestDispatcher("/WEB-INF/updateVente.jsp").forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		BusinessException error = new BusinessException();
		HttpSession session = request.getSession();
		Utilisateur util = (Utilisateur)session.getAttribute("user");
		int id = (int)session.getAttribute("articleId");
		String nom = request.getParameter("article");
		String description = request.getParameter("description");
		String categorie = request.getParameter("selectcat");
		Categorie cate = new Categorie();
				try {
					cate = cat.selectById(Integer.parseInt(categorie));
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (BusinessException e1) {
					for(String s : e1.getErrors()) {
						error.addError(s);
					}
					e1.printStackTrace();
				}
		String miseAPrix = request.getParameter("map");
		LocalDate debutEnchere = LocalDate.parse(request.getParameter("debutEnchere"));
		LocalDate finEnchere = LocalDate.parse(request.getParameter("finEnchere"));
		
		Article art = new Article();
		art.setNumero(id);
		art.setNom(nom);
		art.setDescription(description);
		art.setDateDebutEncheres(debutEnchere);
		art.setDateFinEncheres(finEnchere);
		art.setPrixInitial(Integer.parseInt(miseAPrix));
		art.setUtilisateur(util);
		art.setCategorie(cate);
		
		try {
			article.update(art);
		} catch (BusinessException e) {
			for(String s : e.getErrors()) {
				error.addError(s);
			}
			e.printStackTrace();
		}
		
		Retrait ret = new Retrait();
		ret.setArticle(art);
		ret.setRue(request.getParameter("rue"));
		ret.setCodePostal(request.getParameter("cp"));
		ret.setVille(request.getParameter("ville"));
		
		try {
			retrait.update(ret);
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
			request.setAttribute("dateDebut", debutEnchere.toString());
			request.setAttribute("dateFin", finEnchere.toString());
			request.setAttribute("errors", error.getErrors());
			request.getRequestDispatcher("WEB-INF/updateVente.jsp").forward(request, response);
		}else {
		response.sendRedirect("/encheres");
		}
	}

}
