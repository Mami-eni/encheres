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
		// R�cup�ration de l'utilisateur de la session
		HttpSession session = request.getSession();
		Utilisateur user = (Utilisateur) (session.getAttribute("user"));
		// Faire les modifications

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
			try {
				BllUtilisateur.getBllUtilisateur().update(user);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/monProfil.jsp");
				rd.forward(request, response);
			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("errors", e.getErrors());
				request.getRequestDispatcher("/WEB-INF/modifierProfil.jsp").forward(request, response);
			}
		} else {
			BusinessException be = new BusinessException();
			be.addError(Errors.CONFIRMATION_CORRESPONDE_PAS);
			request.setAttribute("errors", be.getErrors());
			request.getRequestDispatcher("/WEB-INF/modifierProfil.jsp").forward(request, response);
		}
	}

}
