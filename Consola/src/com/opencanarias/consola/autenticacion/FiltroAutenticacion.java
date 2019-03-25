package com.opencanarias.consola.autenticacion;

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

import org.jboss.logging.Logger;

@WebFilter(filterName="FiltroAut", urlPatterns = {"*.xhtml"} )
public class FiltroAutenticacion implements Filter {
	private static Logger logger = Logger.getLogger(FiltroAutenticacion.class);
	
	@Override
	public void doFilter(ServletRequest requ, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		try {
			HttpServletRequest rq = (HttpServletRequest) requ;
			HttpServletResponse rs = (HttpServletResponse) resp;
			HttpSession ss = rq.getSession(false);
			String reqURI =  rq.getRequestURI();
			if (reqURI.indexOf("/login.xhtml")>=0
					|| (ss != null && ss.getAttribute("username")!=null)
					|| reqURI.indexOf("/public/")>=0
					|| reqURI.contains("java.faces.resource")
					) {
				chain.doFilter(requ, resp);
			}else {
				rs.sendRedirect(rq.getContextPath()+"/login.xhtml");
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
	@Override
	public void destroy() {
		
	}

}
