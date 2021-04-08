package fr.eni.ecole.view;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import fr.eni.ecole.exception.Errors;

/**
 * Servlet traitant l'affichage des enchères selon différents filtres
 */
@WebServlet("/encheres")
public class ListeEncheresServlet extends HttpServlet implements ViewConstants {
	private static final long serialVersionUID = 1L;
	private BllCategorie managerCategorie;
	private BllEnchere managerEnchere;
	private BllArticle managerArticle;




	@Override
	public void init() throws ServletException {

		super.init();

		managerCategorie = BllCategorie.getBllCategorie();
		managerEnchere = BllEnchere.getBllEnchere();
		managerArticle= BllArticle.getBllArticle();
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		Map<Integer, Integer> meilleuresEncheresArticles = new HashMap<Integer, Integer>();
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

			/* Pour chaque liste d'articles issue du resultat de recherche, selection de toutes les enchères qui y sont liées et detection l'enchère la plus haute 
			 * Si un utilisateur a réalisé au moins une première surenchère sur cet article, le montant maximal de l'enchère est récupéré, sinon cette est affectée de la valeur initiale de l'article
			*/
			
			for (Article article : listeArticles)
			{

				List<Enchere> listeEncheres = managerEnchere.selectByArticle(article);
				int montantMax = 0;
				Enchere ench = new Enchere();
				if(!listeEncheres.isEmpty())
				{
					for(Enchere e : listeEncheres) 
					{
						if(e.getMontant() > montantMax)
						{
							montantMax = e.getMontant();
							ench = e;
						}

					}

				}

				else
				{
					montantMax = article.getPrixInitial();
				}

				meilleuresEncheresArticles.put(article.getNumero(), montantMax);

			}
			request.setAttribute("meilleureEnchere", meilleuresEncheresArticles);
			request.setAttribute("listeArticles", listeArticles);
		} catch (BusinessException e) {

			request.setAttribute("errors", e.getErrors());
		}
		
		File folder = new File("C:/Users/fraud et med/git/encheres/WebContent/imagesArticles");
		File[] listeDesFichiers = folder.listFiles();
		request.setAttribute("listeImages", listeDesFichiers);

		request.getRequestDispatcher("/WEB-INF/accueil.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		BusinessException error = new BusinessException();
		Map<Integer, Integer> meilleuresEncheresArticles = new HashMap<Integer, Integer>();
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



		
		 // Retour sur la page d'accueil si aucune recherche n'est effectuée
		

		if(filtreTexte.isEmpty() && "toutes".equalsIgnoreCase(filtreCategorie) && null==filtreCheckboxVente && null==filtreCheckboxAchat)
		{
			doGet(request, response);
		}
		else

		{
			try {
				listeArticles= managerArticle.selectByFiltre(filtreTexte, filtreCategorie, filtreRadio, filtreCheckboxVente, filtreCheckboxAchat, userId);

		
				
				for (Article article : listeArticles)
				{

					List<Enchere> listeEncheres = managerEnchere.selectByArticle(article);
					int montantMax = 0;
					Enchere ench = new Enchere();
					if(!listeEncheres.isEmpty())
					{
						for(Enchere e : listeEncheres) 
						{
							if(e.getMontant() > montantMax)
							{
								montantMax = e.getMontant();
								ench = e;
							}

						}

					}

					else
					{
						montantMax = article.getPrixInitial();
					}

					meilleuresEncheresArticles.put(article.getNumero(), montantMax);

				}


			} catch (BusinessException e) {

				e.printStackTrace();
				for(String s : e.getErrors()) {
					error.addError(s);
				}

			}
			
			File folder = new File(IMAGE_PATH);
			File[] listeDesFichiers = folder.listFiles();
			request.setAttribute("listeImages", listeDesFichiers);
			request.setAttribute("errors", error.getErrors());
			request.setAttribute("meilleureEnchere", meilleuresEncheresArticles);
			request.setAttribute("listeArticles", listeArticles);

			request.getRequestDispatcher("/WEB-INF/accueil.jsp").forward(request, response);
		}



	}



}
