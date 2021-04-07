package fr.eni.ecole.view;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
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
import fr.eni.ecole.bll.BllUtilisateur;
import fr.eni.ecole.bo.Article;
import fr.eni.ecole.bo.Enchere;
import fr.eni.ecole.bo.Retrait;
import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.exception.BusinessException;

/**
 * Servlet implementation class AutreRemporteVenteServlet
 */
@WebServlet("/AutreRemporteVenteServlet")
public class AutreRemporteVenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BllArticle article = BllArticle.getBllArticle();
	private BllRetrait retrait = BllRetrait.getBllRetrait();
    private BllEnchere enchere = BllEnchere.getBllEnchere();
    private BllUtilisateur utilisateur = BllUtilisateur.getBllUtilisateur();
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		BusinessException error = new BusinessException();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
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
		String dateFin = art.getDateFinEncheres().format(dtf);
		request.setAttribute("dateFin", dateFin);
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
		request.setAttribute("enchere", ench);
		Utilisateur util = new Utilisateur();
		try {
			util = utilisateur.selectById(ench.getUtilisateur().getNumero());
		} catch (BusinessException e1) {
			for(String s : e1.getErrors()) {
				error.addError(s);
			}
			e1.printStackTrace();
		}
		request.setAttribute("errors", error.getErrors());
		request.setAttribute("utilisateur", util);
		request.getRequestDispatcher("/WEB-INF/autreRemporteVente.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/encheres");
	}

}
