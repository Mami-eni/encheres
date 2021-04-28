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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.ecole.bo.Utilisateur;

import javax.servlet.DispatcherType;

/**
 * Servlet Filter implementation class ConditionFilter
 */
@WebFilter(dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ERROR }, urlPatterns = {	"/*" })

public class LoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	
		HttpServletRequest httpR = (HttpServletRequest) request;
//		HttpServletResponse httpResp = (HttpServletResponse) response;
		
		String servletPath = httpR.getServletPath();
//		System.out.println("servletPath : " + servletPath);
		
		//tester si un utilisateur est en session
		
		HttpSession session = httpR.getSession(false);
	
//		 user = (Utilisateur) session.getAttribute("user");
		
		
		 if(servletPath.contains("encheres")
				||servletPath.contains("accueil.jsp")
				|| servletPath.contains("inscription")
				|| servletPath.contains("connection")
				|| servletPath.contains("/font")
				|| servletPath.contains("/script")
				|| servletPath.contains("/images")
				
				|| servletPath.contains("/vendor")
			)
		{
			// pass the request along the filter chain
			chain.doFilter(request, response);
					
		}
		
		 else if(session==null ||  session.getAttribute("user")==null)
		{
			// Injecter l'URL demandé à l'origine
//			httpR.setAttribute("targetURL", httpR.getContextPath() + servletPath);
			httpR.getRequestDispatcher("/encheres").forward(httpR, response);
			
		}
		
		
		else
		{
			
			// Injecter l'URL demandé à l'origine
//						httpR.setAttribute("targetURL", httpR.getContextPath() + servletPath);
//						httpR.getRequestDispatcher("/WEB-INF/accueil.jsp").forward(httpR, response);
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
