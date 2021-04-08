package fr.eni.ecole.view;

import java.io.File;
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

import fr.eni.ecole.bo.Article;
import fr.eni.ecole.bo.Categorie;
import fr.eni.ecole.bo.Retrait;
import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.exception.BusinessException;

/**
 * Cette classe gère l'envoi de données d'affichage et l'update d'articles et de retraits dans la base de donnée
 */
@WebServlet("/UpdateSaleServlet")
public class UpdateSaleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BllCategorie cat = BllCategorie.getBllCategorie();
	private BllArticle article = BllArticle.getBllArticle();
	private BllRetrait retrait = BllRetrait.getBllRetrait();
       
    /* pré-remplissage des champs de l'article et du retrait */
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
		File folder = new File("C:/Users/mamib/Documents/cours_ENI/Modules/projet-troc-version-commune/encheres/WebContent/imagesArticles");
		File[] listeDesFichiers = folder.listFiles();
		String compare = "img_article_"+String.valueOf(art.getNumero())+".jpg";
		for(File f : listeDesFichiers) {
			if(f.getName().equals(compare)) {
				request.setAttribute("image", f.getName());
			}
		}
		request.setAttribute("errors", error.getErrors());
		request.setAttribute("categories", listeCat);
		request.getRequestDispatcher("/WEB-INF/updateVente.jsp").forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		BusinessException error = new BusinessException();
		HttpSession session = request.getSession();
		/* récupération des attributs de classe de l'article */
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
		/* construction de l'article à mettre à jour dans la bdd */
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
		/* récupération des attributs et construction du retrait à mettre à jour dans la bdd */
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
			/* passage en attributs de requêtes des éléments nécessaires au pré-remplissage des champs en cas d'erreur */
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
			File folder = new File("C:/Users/fraud et med/git/encheres/WebContent/imagesArticles");
			File[] listeDesFichiers = folder.listFiles();
			String compare = "img_article_"+String.valueOf(art.getNumero())+".jpg";
			for(File f : listeDesFichiers) {
				if(f.getName().equals(compare)) {
					request.setAttribute("image", f.getName());
				}
			}
			request.setAttribute("errors", error.getErrors());
			request.getRequestDispatcher("WEB-INF/updateVente.jsp").forward(request, response);
		}else {
		response.sendRedirect("/encheres");
		}
	}

}
