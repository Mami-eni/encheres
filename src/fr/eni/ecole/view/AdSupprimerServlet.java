package fr.eni.ecole.view;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.ecole.bll.BllUtilisateur;
import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.exception.BusinessException;

/**
 * Servlet implementation class AdSupprimerServlet
 */
@WebServlet("/adSupprime")
public class AdSupprimerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idUser = Integer.parseInt(request.getParameter("vendeur"));
		
		try {
			Utilisateur user = BllUtilisateur.getBllUtilisateur().selectById(idUser);
			BllUtilisateur.getBllUtilisateur().delete(user);
			response.sendRedirect("/encheres");
		} catch (BusinessException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
