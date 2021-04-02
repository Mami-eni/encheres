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
 * Servlet implementation class NewSaleServlet
 */
@WebServlet("/NewSaleServlet")
public class NewSaleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BllCategorie cat = BllCategorie.getBllCategorie();
	private BllUtilisateur util = BllUtilisateur.getBllUtilisateur();
	private BllArticle article = BllArticle.getBllArticle();
	private BllRetrait retrait = BllRetrait.getBllRetrait();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		List<Categorie> listeCat = new ArrayList<Categorie>();
		try {
			listeCat = cat.selectAll();
		} catch (BusinessException e) {
			request.setAttribute("erreur", e.getErrors());
			e.printStackTrace();
		}
		request.setAttribute("categories", listeCat);
		request.getRequestDispatcher("/WEB-INF/nouvelleVente.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BusinessException error = new BusinessException();
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		Utilisateur util = (Utilisateur) session.getAttribute("user");
		String nom = request.getParameter("article");
		String description = request.getParameter("description");
		String categorie = request.getParameter("selectcat");
		Categorie cate = new Categorie();
		try {
			cate = cat.selectById(Integer.parseInt(categorie));
		} catch (BusinessException e1) {
			for(String s: e1.getErrors()) {
				error.addError(s);
			}
			e1.printStackTrace();
		}
		String miseAPrix = request.getParameter("map");
		LocalDate debutEnchere = LocalDate.parse(request.getParameter("debutEnchere"));
		LocalDate finEnchere = LocalDate.parse(request.getParameter("finEnchere"));
		int articleId = 0;
		Article art = new Article();
		art.setNom(nom);
		art.setDescription(description);
		art.setDateDebutEncheres(debutEnchere);
		art.setDateFinEncheres(finEnchere);
		art.setPrixInitial(Integer.parseInt(miseAPrix));
		art.setUtilisateur(util);
		art.setCategorie(cate);
		try {
			article.insert(art);
			List<Article> listeArticles = article.selectByUser(util);
			for (Article a : listeArticles) {
				articleId = a.getNumero();
			}
			art.setNumero(articleId);
			Retrait ret = new Retrait();
			ret.setArticle(art);
			ret.setRue(request.getParameter("rue"));
			ret.setCodePostal(request.getParameter("cp"));
			ret.setVille(request.getParameter("ville"));
			retrait.insert(ret);
		} catch (BusinessException e) {
			for(String s: e.getErrors()) {
				error.addError(s);
			}
			List<Categorie> listeCat = new ArrayList<Categorie>();
			try {
				listeCat = cat.selectAll();
			} catch (BusinessException e1) {
				for(String s : e1.getErrors()) {
					error.addError(s);
				}
				e1.printStackTrace();
			}
			request.setAttribute("categories", listeCat);
			request.setAttribute("article", art);
			request.setAttribute("map", miseAPrix);
			request.setAttribute("dateDebut", debutEnchere.toString());
			request.setAttribute("dateFin", finEnchere.toString());
			request.setAttribute("rue", request.getParameter("rue"));
			request.setAttribute("cp", request.getParameter("cp"));
			request.setAttribute("ville", request.getParameter("ville"));
			try {
				article.delete(art);
			} catch (BusinessException e1) {
				for(String s : e1.getErrors()) {
					error.addError(s);
				}
			}
			request.setAttribute("erreur", error.getErrors());
			request.getRequestDispatcher("WEB-INF/nouvelleVente.jsp").forward(request, response);
		}

		response.sendRedirect("/encheres");

	}

}
