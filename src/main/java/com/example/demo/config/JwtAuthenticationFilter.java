package com.example.demo.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.CustomeUserDetailsService;
import com.example.demo.util.JwtUtil;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	CustomeUserDetailsService customeUserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String requestTokeheader = request.getHeader("Authorization");
		String username=null;
		String jwtToken=null;
		
		if(requestTokeheader!=null && requestTokeheader.startsWith("Bearer ")) {
			jwtToken=requestTokeheader.substring(7);
			
			try {
				username=this.jwtUtil.extractUsername(jwtToken);
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
			
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				
				UserDetails userDetails = this.customeUserDetailsService.loadUserByUsername(username);
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	
			usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			
			}
			
			else {
				System.out.println("token is not validated");
			}
			
			
		}
		filterChain.doFilter(request, response);
	}

}
