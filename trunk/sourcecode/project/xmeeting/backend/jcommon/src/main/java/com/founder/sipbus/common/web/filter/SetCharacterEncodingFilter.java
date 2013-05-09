/*
 * 
 * $Revision: 1.4 $ 
 * $Author: Ziven_Lu $  
 * $Date: 2006/11/01 07:12:40 $ 
*/
package com.founder.sipbus.common.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class SetCharacterEncodingFilter implements Filter {
	private Log log = LogFactory.getLog(SetCharacterEncodingFilter.class);

	/**
	 * The default character encoding to set for requests that pass through
	 * this filter.
	 */
	protected String encoding = null;

	/**
	 * The filter configuration object we are associated with. If this value
	 * is null, this filter instance is not currently configured.
	 */
	protected FilterConfig filterConfig = null;

	//-------------------------------------------------------------------destroy
	/**
	 * Take this filter out of service.
	 */
	public void destroy() {
		if(log.isDebugEnabled()){
			log.debug("DEBUG -   init(...) - started");
		}
		this.encoding = null;
		this.filterConfig = null;
    	if(log.isDebugEnabled()){
			log.debug("DEBUG -   init(...) - ending");
		}
	}

	//------------------------------------------------------------------doFilter
	/**
	 * Select and set (if specified) the character encoding to be used to
	 * interpret request parameters for this request.
	 *
	 * @param request The servlet request we are processing
	 * @param response The servlet response we are creating
	 * @param chain The filter chain we are processing
	 *
	 * @exception IOException if an input/output error occurs
	 * @exception ServletException if a servlet error occurs
	 */
	public void doFilter(
		ServletRequest request,
		ServletResponse response,
		FilterChain chain)
		throws IOException, ServletException {
		if(log.isDebugEnabled()){
			log.debug("DEBUG -   doFilter(...) - started");
		}
		// Conditionally select and set the character encoding to be used
		if ((request.getCharacterEncoding() == null)) {
			String encoding = selectEncoding(request);
			if (encoding != null) {
				if(log.isDebugEnabled()){
					log.debug("DEBUG -   doFilter(...) - Encoding: "+ encoding);
				}
				request.setCharacterEncoding(encoding);
				response.setContentType("charset=" + encoding);
			}
		}	
		if(log.isDebugEnabled()){
			log.debug("DEBUG -   doFilter(...) - ending");
		}
		// Pass control on to the next filter
		chain.doFilter(request, response); 
	}

	//----------------------------------------------------------------------init
	/**
	 * Place this filter into service.
	 * @param filterConfig The filter configuration object
	 * @throws ServletException
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		if(log.isDebugEnabled()){
			log.debug("DEBUG -   init(...) - started");
		}
		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getServletContext().getInitParameter("encoding");
//		this.encoding = filterConfig.getInitParameter("encoding");
		if(log.isDebugEnabled()){
			log.debug("DEBUG -   init(...) - ending");
		}
	}

	//------------------------------------------------------------selectEncoding
	/**
	 * Select an appropriate character encoding to be used, based on the
	 * characteristics of the current request and/or filter initialization
	 * parameters. If no character encoding should be set, return
	 * <code>null</code>.
	 * <p>
	 * The default implementation unconditionally returns the value configured
	 * by the <strong>encoding</strong> initialization parameter for this
	 * filter.
	 *
	 * @param request The servlet request we are processing
	 * @return String
	 */
	protected String selectEncoding(ServletRequest request) {
		return (this.encoding);
	}

} //EOC
