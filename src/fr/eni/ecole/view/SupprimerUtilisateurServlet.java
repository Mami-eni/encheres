package fr.eni.ecole.view;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.ecole.bll.BllUtilisateur;
import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.exception.BusinessException;

/**
 * Servlet implementation class SupprimerUtilisateurServlet
 */
@WebServlet("/supprimerUser")
public class SupprimerUtilisateurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//vérifie s'il y a un utilisateur connecté
		Cookie[] cookies = request.getCookies();
		String login = null;
		String password = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie c = cookies[i];
				if ("login".equals(c.getName())) {
					login = c.getValue();
					
					request.setAttribute("login", login);
				}
				if ("password".equals(c.getName())) {
					password = c.getValue();
					request.setAttribute("password", password);
				}
			}
		}
		try {
			System.out.println(login + password);
			Utilisateur user = BllUtilisateur.getBllUtilisateur().validateConnection(login, password);
			System.out.println(user);
			BllUtilisateur.getBllUtilisateur().delete(user);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
