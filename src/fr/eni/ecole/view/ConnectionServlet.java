package fr.eni.ecole.view;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.ecole.bo.Utilisateur;

import fr.eni.ecole.exception.BusinessException;
import fr.eni.ecole.bll.*;

/**
 * Servlet implementation class ConnectionServlet
 */
@WebServlet("/connection")
public class ConnectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Vérifier s'il y a un id et mdp dans les cookies
		Cookie[] cookies = request.getCookies();
		String login;
		String password;

		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie c = cookies[i];
				if ("login".equals(c.getName())) {
					login = c.getValue();
					request.setAttribute("login", login);
				}
			}
		}

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/connectionUtilisateur.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		// creation de session pour l'utilisateur actuel
		HttpSession session = request.getSession();
		String rememberMe = request.getParameter("remember-me");
		try {
			// Chercher l'utilisateur
			Utilisateur user = BllUtilisateur.getBllUtilisateur().validateConnection(login, password);
			// Si connection réussi, retenir l'utilisateur pour la session
			if (user != null) {
				session.setAttribute("user", user);
				request.getRequestDispatcher("/WEB-INF/monProfil.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("/WEB-INF/connectionUtilisateur.jsp").forward(request, response);
			}
			// Si l'utilisateur veut garder son login, créer un cookie
			if ("on".equals(rememberMe)) {
				addCookie(response, "login", login, 24 * 60 * 60);
			}
		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("errors", e.getErrors());
			request.getRequestDispatcher("/WEB-INF/connectionUtilisateur.jsp").forward(request, response);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
 	* Ajouter un Cookie
 	* @param response
 	* @param name
 	* @param value
 	* @param maxAge
 	*/
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

}
