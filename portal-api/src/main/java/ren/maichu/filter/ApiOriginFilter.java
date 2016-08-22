package ren.maichu.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class ApiOriginFilter implements Filter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(ApiOriginFilter.class);

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (logger.isDebugEnabled()) {
			logger.debug("ServletRequest, ServletResponse, FilterChain - start"); //$NON-NLS-1$
		}

		// if(((HttpServletRequest)request).getRequestURI().startsWith("/api")){
		HttpServletResponse res = (HttpServletResponse) response;
		res.addHeader("Access-Control-Allow-Origin", "*");
		res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
		res.addHeader("Access-Control-Allow-Headers", "Content-Type");
		// }

		chain.doFilter(request, response);

		if (logger.isDebugEnabled()) {
			logger.debug("ServletRequest, ServletResponse, FilterChain - end"); //$NON-NLS-1$
		}
	}

	public void destroy() {
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}
}