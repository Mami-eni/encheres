package fr.eni.ecole.view;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.ecole.bll.BllUtilisateur;
import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.exception.BusinessException;
import fr.eni.ecole.exception.Errors;

/**
 * Servlet implementation class ModifierServlet
 */
@WebServlet("/modifierUser")
public class ModifierUtilisateurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/modifierProfil.jsp");
			rd.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// Récupération de l'utilisateur de la session
		HttpSession session = request.getSession();
		
		Utilisateur user = new Utilisateur();
		
		if(null != session.getAttribute("user"))
		{
			user = (Utilisateur) (session.getAttribute("user"));
		}
		
		// recuperation choix bouton
		String choixSupprimerProfil = request.getParameter("supprimer");
		String choixEnregistrerModifications= request.getParameter("enregistrer");
		String choixCreerProfil = request.getParameter("creer");
		
		
		// construction user
		
		String pseudo = request.getParameter("pseudo");
		String mdp = request.getParameter("mdp");
		String confirmation = request.getParameter("confirmation");
		if (mdp.equals(confirmation)) {
			user.setMotDePasse(request.getParameter("mdp"));
			user.setPseudo(request.getParameter("pseudo"));
			user.setNom(request.getParameter("nom"));
			user.setPrenom(request.getParameter("prenom"));
			user.setTelephone(request.getParameter("telephone"));
			user.setCodePostal(request.getParameter("codePostal"));
			user.setEmail(request.getParameter("email"));
			user.setRue(request.getParameter("rue"));
			user.setVille(request.getParameter("ville"));
		}
		
		else {
			BusinessException be = new BusinessException();
			be.addError(Errors.CONFIRMATION_CORRESPONDE_PAS);
			request.setAttribute("errors", be.getErrors());
			request.getRequestDispatcher("/WEB-INF/modifierProfil.jsp").forward(request, response);
		}
		

		
		
		// Faire les modifications
		
		if(null!=choixSupprimerProfil)
		{
			
			try {
				BllUtilisateur.getBllUtilisateur().delete(user = (Utilisateur) (session.getAttribute("user")));
				response.sendRedirect("/encheres");
			} catch (BusinessException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		
		else if(null != choixEnregistrerModifications)
		{
			
				
				try {
					BllUtilisateur.getBllUtilisateur().update(user);
					RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/monProfil.jsp");
					rd.forward(request, response);
				} catch (BusinessException e) {
					e.printStackTrace();
					request.setAttribute("errors", e.getErrors());
					request.getRequestDispatcher("/WEB-INF/modifierProfil.jsp").forward(request, response);
				}
			
			
		}
		
		else if(null != choixCreerProfil)
		{
			
			try {
				BllUtilisateur.getBllUtilisateur().insert(user);
				
				user = BllUtilisateur.getBllUtilisateur().validateConnection(pseudo, mdp);
				session.setAttribute("user", user);
				request.getRequestDispatcher("/WEB-INF/accueil.jsp").forward(request, response);
				
			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("errors", e.getErrors());
				request.getRequestDispatcher("/WEB-INF/modifierProfil.jsp").forward(request, response);
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			
		}
		

		
	}
	
	
	

}
