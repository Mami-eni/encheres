package fr.eni.ecole.view;

import java.io.IOException;
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
import fr.eni.ecole.bll.BllEnchere;
import fr.eni.ecole.bo.Article;
import fr.eni.ecole.bo.Categorie;
import fr.eni.ecole.bo.Enchere;
import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.exception.BusinessException;

/**
 * Servlet traitant l'affichage des enchères selon différents filtres
 */
@WebServlet("/encheres")
public class ListeEncheresServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BllCategorie managerCategorie;
//	private BllEnchere managerEnchere;
	private BllArticle managerArticle;
    
   
    
    
@Override
public void init() throws ServletException {
	
	super.init();
	
	managerCategorie = BllCategorie.getBllCategorie();
//	managerEnchere = BllEnchere.getBllEnchere();
	managerArticle= BllArticle.getBllArticle();
}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		List<Categorie> listeCategorie = new ArrayList<Categorie>();
		List<Article> listeArticles = new ArrayList<Article>();

		try {
			listeCategorie = managerCategorie.selectAll();
			request.setAttribute("listeCategorie", listeCategorie);
		} catch (BusinessException e) {
			request.setAttribute("errors", e.getErrors());
		}
		try {
			listeArticles = managerArticle.selectAll(); 
			request.setAttribute("listeArticles", listeArticles);
		} catch (BusinessException e) {
			
			request.setAttribute("errors", e.getErrors());
		}


		
		String id_article = request.getParameter("article");
		String id_vendeur = request.getParameter("vendeur");
		
		
		request.getRequestDispatcher("/WEB-INF/accueil.jsp").forward(request, response);
		
	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession(false);
		Utilisateur user = (Utilisateur) session.getAttribute("user");
		int userId=0;
		if(!(user==null))
		{
			 userId = user.getNumero();
		}
		
		
		List<Article> listeArticles = new ArrayList<Article>();
		String filtreTexte = request.getParameter("search");
		
		
		String filtreCategorie= request.getParameter("categories");

		
	
		
		
		String [] filtreCheckboxVente = request.getParameterValues("flitreCheckboxVente");
		String [] filtreCheckboxAchat = request.getParameterValues("flitreCheckboxAchat");

		String filtreRadio = request.getParameter("filtreRadio");
		
	
		
		/**
		 * Retour sur la page d'accueil si aucun filtrage n'est réalisé 
		 */
		
		if(filtreTexte.isEmpty() && filtreCategorie.equalsIgnoreCase("toutes") && filtreCheckboxVente==null && filtreCheckboxAchat==null)
		{
			doGet(request, response);
		}
		else
			
		{
			try {
				listeArticles= managerArticle.selectByFiltre(filtreTexte, filtreCategorie, filtreRadio, filtreCheckboxVente, filtreCheckboxAchat, userId);
				request.setAttribute("listeArticles", listeArticles);
			} catch (BusinessException e) {
				
				e.printStackTrace();
				request.setAttribute("errors", e.getErrors());
			}
			
			request.getRequestDispatcher("/WEB-INF/accueil.jsp").forward(request, response);
		}
		
		
		
	}

}
