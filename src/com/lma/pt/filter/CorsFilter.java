package com.lma.pt.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lma.pt.service.Security;




public class CorsFilter implements Filter {

	private final String baseUrl = "http://localhost:8080/PersonalTrack/rest/";
	private final String allowedEntries = "token:runner/createRunner:";
	private final String testMethods = "test:";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, HEAD, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        String username = request.getHeader("user");
        String token = request.getHeader("token");
        boolean allowed = false; 
        for(String allowedEntry: allowedEntries.split(":")){

        	if(request.getRequestURL().toString().startsWith(baseUrl + allowedEntry))
        		allowed = true;
        }
        for(String testAllowed: testMethods.split(":")){
        	if(request.getRequestURL().toString().startsWith(baseUrl + testAllowed) && request.getHeader("testAllowed").equals("4562t4178248123y213218361728ygf1hiuhui"))
        		allowed = true;
        }
        if(!allowed)
        allowed = Security.getInstance().validateKey(token, username);
        if(!allowed){
        	response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        else{
        	filterChain.doFilter(request, response);	
        }
        
    }

    @Override
    public void destroy() {

    }
}
