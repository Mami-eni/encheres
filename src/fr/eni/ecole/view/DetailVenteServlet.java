package fr.eni.ecole.view;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.ecole.bll.BllArticle;
import fr.eni.ecole.bll.BllEnchere;
import fr.eni.ecole.bll.BllRetrait;
import fr.eni.ecole.bll.BllUtilisateur;
import fr.eni.ecole.bo.Article;
import fr.eni.ecole.bo.Enchere;
import fr.eni.ecole.bo.Retrait;
import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.exception.BusinessException;

/**
 * Cette classe gère l'envoi de données d'affichage et l'insertion d'enchères dans la base de donnée
 */
@WebServlet("/DetailVenteServlet")
public class DetailVenteServlet extends HttpServlet implements ViewConstants {
	private static final long serialVersionUID = 1L;
	private BllArticle article = BllArticle.getBllArticle();
	private BllEnchere enchere = BllEnchere.getBllEnchere();
	private BllRetrait retrait = BllRetrait.getBllRetrait();
	private BllUtilisateur utilisateur = BllUtilisateur.getBllUtilisateur();

       
    /* passage en attributs de requête des éléments nécessaires à l'affichage des données de la page */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		BusinessException error = new BusinessException();
		if(request.getAttribute("errors") != null) {
			error = (BusinessException)request.getAttribute("errors");
		}
		HttpSession session = request.getSession();
		Utilisateur util = (Utilisateur)session.getAttribute("user");
		request.setAttribute("user", util);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		int idArticle = 0;
		if(request.getAttribute("article") == null) {
		idArticle = Integer.parseInt(request.getParameter("article"));
		}else {
		idArticle = (int)request.getAttribute("article");	
		}
		try {
		Article art = article.selectById(idArticle);
		request.setAttribute("article", art);
		List<Enchere> listeEncheres = enchere.selectByArticle(art);
		int montantMax = 0;
		Enchere ench = new Enchere();
		if(!listeEncheres.isEmpty()) {
		for(Enchere e : listeEncheres) {
			if(e.getMontant() > montantMax) {
				montantMax = e.getMontant();
				ench = e;
			}
		}
		}
		request.setAttribute("enchere", ench);
		Retrait ret = retrait.selectByArticle(art);
		request.setAttribute("retrait", ret);
		String dateFin = art.getDateFinEncheres().format(dtf);
		request.setAttribute("dateFin", dateFin);
		int enchereMin = 1;
		request.setAttribute("enchMin", enchereMin);
		File folder = new File(IMAGE_PATH);
		File[] listeDesFichiers = folder.listFiles();
		String compare = "img_article_"+String.valueOf(art.getNumero())+".jpg";
		
		if(null!=listeDesFichiers)
		{
			for(File f : listeDesFichiers) {
				if(f.getName().equals(compare)) {
					request.setAttribute("image", f.getName());
				}
			}
		}
		
		
		request.getRequestDispatcher("/WEB-INF/detailVente.jsp").forward(request, response);
		}catch(BusinessException e) {
			for(String s : e.getErrors()) {
				error.addError(s);
			}
			request.setAttribute("errors", error.getErrors());
			request.getRequestDispatcher("/WEB-INF/detailVente.jsp").forward(request, response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		BusinessException error = new BusinessException();
		HttpSession session = request.getSession();
		/* récupération des attributs de classe de l'enchère */
		Utilisateur util = new Utilisateur();
		util = (Utilisateur)session.getAttribute("user");
		Utilisateur user = new Utilisateur();
		try {
			user = utilisateur.selectById(util.getNumero());
		} catch (BusinessException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Article art = new Article();
		try {
			art = article.selectById(Integer.parseInt(request.getParameter("article")));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			for(String s : e.getErrors()) {
				error.addError(s);
			}
			e.printStackTrace();
		}
		
		// recherche meilleure enchère
		List<Enchere> listeEncheres = new ArrayList<Enchere>();
		try {
			listeEncheres = enchere.selectByArticle(art);
		} catch (BusinessException e2) {
			for(String s : e2.getErrors()) {
				error.addError(s);
			}
			e2.printStackTrace();
		}
		int montantMax = 0;
		Enchere ench = new Enchere();
		if(!listeEncheres.isEmpty()) {
		for(Enchere e : listeEncheres) {
			if(e.getMontant() > montantMax) {
				montantMax = e.getMontant();
				ench = e;
			}
		}
		}

				
		// recherche meilleur encherisseur
		Utilisateur bestEncherisseur = ench.getUtilisateur();
		
		// si best encherisseur different de user 
		// et si il a assez d'argent
		
		if(user.getNumero()!=bestEncherisseur.getNumero() && Integer.parseInt(request.getParameter("proposition")) <= user.getCredit())
		{
			// retirer l'argent de l'acheteur
			user.setCredit(user.getCredit() - Integer.parseInt(request.getParameter("proposition")));
			try {
				utilisateur.update(user);
			} catch (BusinessException e1) {
				for(String s : e1.getErrors()) {
					error.addError(s);
				}
				e1.printStackTrace();
			}
			
			
			// crediter le best encherisseur 
			bestEncherisseur.setCredit(bestEncherisseur.getCredit() + ench.getMontant());
			try {
				utilisateur.update(bestEncherisseur);
			} catch (BusinessException e1) {
				for(String s : e1.getErrors()) {
					error.addError(s);
				}
				e1.printStackTrace();
			}
			
			// creation enchère 
			Instant now = Instant.now();
			Timestamp dateEnchere = Timestamp.from(now);
			int montant = Integer.parseInt(request.getParameter("proposition"));
			/* construction de l'enchère à insérer dans la bdd */
			Enchere enche = new Enchere();
			enche.setDate(dateEnchere);
			enche.setMontant(montant);
			enche.setArticle(art);
			enche.setUtilisateur(user);
			
			try {
				enchere.insert(enche);
			} catch (BusinessException e) {
				for(String s : e.getErrors()) {
					error.addError(s);
				}
				request.setAttribute("errors", error);
				request.setAttribute("article", art.getNumero());
				doGet(request, response);
				e.printStackTrace();
			}
			
			response.sendRedirect("/encheres");	
			
		}
		
		else {
			//error.addError("Vous n'avez pas assez de points sur votre compte");
			//request.setAttribute("errors", error);
			doGet(request, response);
		}
	}

}
