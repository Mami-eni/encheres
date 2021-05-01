package fr.eni.ecole.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;


import javax.servlet.DispatcherType;

/**
 * Filtre permettant de limiter l'accès de certaines pages tant que l'utilisateur n'est pas connecté
 */
@WebFilter(dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ERROR }, urlPatterns = {	"/*" })

public class LoginFilter implements Filter {

   

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	
		HttpServletRequest httpR = (HttpServletRequest) request;

		
		String servletPath = httpR.getServletPath();
		
		HttpSession session = httpR.getSession(false);

				
		 if(servletPath.contains("encheres")
				|| servletPath.contains("accueil.jsp")
				|| servletPath.contains("modifierProfil")
				|| servletPath.contains("modifierUser")
				|| servletPath.contains("connection")
				|| servletPath.contains("/font")
				|| servletPath.contains("/script")
				|| servletPath.contains("/images")
				|| servletPath.contains("/img")
				|| servletPath.contains("/vendor")
			)
		{
		
			chain.doFilter(request, response);
					
		}
		
		 else if(session==null ||  session.getAttribute("user")==null)
		{

			httpR.getRequestDispatcher("/encheres").forward(httpR, response);
		}
		
		
		else
		{		

			chain.doFilter(request, response);
		}

		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
