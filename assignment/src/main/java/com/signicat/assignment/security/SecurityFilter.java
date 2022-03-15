package com.signicat.assignment.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;


@Component
public class SecurityFilter extends OncePerRequestFilter{

	@Autowired
	JwtUtil util;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)
					throws ServletException, IOException 
	{
		//read 'Bearer' token from authorization header
		String token=request.getHeader("Authorization");//.substring(7);
		String username=null;

		if(token!=null) {

			try {
				username=util.getUsername(token);
			}
			catch (IllegalArgumentException e) {
				System.out.println("Failed to get jwt token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has been expired");
			}

			//username shouldn't be empty and set security context 
			if(username!=null&&SecurityContextHolder.getContext().getAuthentication()==null) {

				UserDetails user=userDetailsService.loadUserByUsername(username);

				//validate token
				boolean isValid=util.validateToken(token, user.getUsername());

				if(isValid) {
					UsernamePasswordAuthenticationToken authToken=
							new UsernamePasswordAuthenticationToken(username, user.getPassword(),user.getAuthorities());

					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authToken);
				}

			}
		}
		filterChain.doFilter(request, response);


	}

}
