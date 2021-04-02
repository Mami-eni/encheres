package fr.eni.ecole.view;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.ecole.bll.BllUtilisateur;
import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.exception.BusinessException;

/**
 * Servlet implementation class InscriptionServlet
 */
@WebServlet("/inscription")
public class InscriptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/inscriptionForm.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Utilisateur user = new Utilisateur();
		user.setPseudo(request.getParameter("pseudo"));
		user.setNom(request.getParameter("nom"));

		user.setPrenom(request.getParameter("prenom"));
		user.setTelephone(request.getParameter("telephone"));
		user.setCodePostal(request.getParameter("codePostal"));
		user.setMotDePasse(request.getParameter("mdp"));
		user.setEmail(request.getParameter("email"));
		user.setRue(request.getParameter("rue"));
		user.setVille(request.getParameter("ville"));

//		String confirmation = request.getParameter("confirmation");

		try {
			BllUtilisateur.getBllUtilisateur().insert(user);
		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("errors", e.getErrors());
			request.getRequestDispatcher("/WEB-INF/inscriptionForm.jsp").forward(request, response);
		}
		
//			Transmettre les informations pour la page de welcome
		request.getRequestDispatcher("/WEB-INF/inscriptionReussi.jsp").forward(request, response);

	}

}
