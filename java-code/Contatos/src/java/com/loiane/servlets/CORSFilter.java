/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loiane.servlets;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Loiane Groner
 * http://loiane.com
 */
@WebFilter(urlPatterns = {"/Contact"})
public class CORSFilter implements Filter {

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        System.out.println("filter");
        // Specify domains from which requests are allowed
        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");
        // Specify which request methods are allowed
        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        // Additional headers which may be sent along with the CORS request
        // The X-Requested-With header allows jQuery requests to go through
        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers", "X-Requested-With");
        // Set the age to 1 day to improve speed/caching.
        ((HttpServletResponse) response).addHeader("Access-Control-Max-Age", "86400");
        chain.doFilter(request, response);

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub
    }
}
