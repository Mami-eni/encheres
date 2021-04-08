package fr.eni.ecole.view;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import fr.eni.ecole.bll.BllArticle;
import fr.eni.ecole.bll.BllCategorie;
import fr.eni.ecole.bll.BllRetrait;
import fr.eni.ecole.bo.Article;
import fr.eni.ecole.bo.Categorie;

import fr.eni.ecole.bo.Retrait;
import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.exception.BusinessException;

/**
 * Cette classe gère l'envoi de données d'affichage et l'insertion d'articles et de retraits dans la base de donnée
 */
@WebServlet("/NewSaleServlet")
@MultipartConfig
public class NewSaleServlet extends HttpServlet implements ViewConstants {
	private static final long serialVersionUID = 1L;
	private BllCategorie cat = BllCategorie.getBllCategorie();
	private BllArticle article = BllArticle.getBllArticle();
	private BllRetrait retrait = BllRetrait.getBllRetrait();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		/* récupération de la liste des catégories */
		List<Categorie> listeCat = new ArrayList<Categorie>();
		try {
			listeCat = cat.selectAll();
		} catch (BusinessException e) {
			request.setAttribute("errors", e.getErrors());
			e.printStackTrace();
		}
		request.setAttribute("categories", listeCat);
		request.getRequestDispatcher("/WEB-INF/nouvelleVente.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		BusinessException error = new BusinessException();
		HttpSession session = request.getSession();
		/* récupération des attributs de classe de l'article */
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
		/* Construction de l'article à insérer dans la bdd */
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
			/* récupération du numéro d'article généré lors de l'insertion dans la bdd */
			List<Article> listeArticles = article.selectByUser(util);
			for (Article a : listeArticles) {
				articleId = a.getNumero();
			}
			art.setNumero(articleId);
			/* récupération de l'image */
			Part filePart = request.getPart("upload");
			if(filePart.getSize() != 0) {
			InputStream fileContent = filePart.getInputStream();
			File folder = new File(IMAGE_PATH);
			String image = "img_article_"+String.valueOf(art.getNumero())+".jpg";
			File file = new File(folder, image);
			Files.copy(fileContent, file.toPath());
			fileContent.close();
			}
			/* récupération et construction du retrait à insérer dans la bdd */
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
			/* passage en attributs de requête de tous les éléments nécessaires au pré-remplissage des champs saisis précédemment en cas d'erreur */
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
			/* Suppression de l'article inséré dans la bdd en cas d'erreur */
			try {
				article.delete(art);
			} catch (BusinessException e1) {
				for(String s : e1.getErrors()) {
					error.addError(s);
				}
			}
			File folder = new File(IMAGE_PATH);
			File[] listeDesFichiers = folder.listFiles();
			String compare = "img_article_"+String.valueOf(art.getNumero())+".jpg";
			for(File f : listeDesFichiers) {
				if(f.getName().equals(compare)) {
					request.setAttribute("image", f.getName());
				}
			}
			request.setAttribute("errors", error.getErrors());
			request.getRequestDispatcher("WEB-INF/nouvelleVente.jsp").forward(request, response);
		}

		response.sendRedirect("/encheres");

	}

}
