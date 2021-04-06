package fr.eni.ecole.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.dal.Connect;
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
		// V�rifier s'il y a un id et mdp dans les cookies
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
//		if (!"".equals(login) || !"".equals(password)) {
//			try {
//				Utilisateur user = BllUtilisateur.getBllUtilisateur().validateConnection(login, password);
//			} catch (BusinessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		// s'il y a user + password je cr�e un utilisateur

		//

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
		// cr�ation de session pour l'utilisateur actuel
		HttpSession session = request.getSession();
		String rememberMe = request.getParameter("remember-me");
		try {
			// Cherche l'utilisateur
			Utilisateur user = BllUtilisateur.getBllUtilisateur().validateConnection(login, password);

			if ("on".equals(rememberMe)) {
				addCookie(response, "login", login, 24 * 60 * 60);
			}
			// Retenir l'utilisateur pour toute la session
			if (user != null) {
				session.setAttribute("user", user);
				request.getRequestDispatcher("/WEB-INF/monProfil.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("/WEB-INF/connectionUtilisateur.jsp").forward(request, response);
			}
			
		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("errors", e.getErrors());
			request.getRequestDispatcher("/WEB-INF/connectionUtilisateur.jsp").forward(request, response);
		}
	}

	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	public static void removeCookie(HttpServletResponse response, String name) {
		addCookie(response, name, null, 0);
	}

}
