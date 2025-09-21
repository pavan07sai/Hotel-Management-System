package com.CaseStudy.Api_gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
 
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
 
 
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
 
	@Autowired
	private RouteValidator validator;
	
	@Autowired
	private JwtUtil jwtUtil;
	
   public AuthenticationFilter() {
       super(Config.class);
   }
 
   @Override
   public GatewayFilter apply(Config config) {
       return (exchange, chain) -> {
           ServerHttpRequest request = exchange.getRequest();
 
           if (validator.isSecured.test(request)) {
               if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                   throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing Authorization Header");
               }
 
               String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
 
               if (authHeader != null && authHeader.startsWith("Bearer ")) {
                   String token = authHeader.substring(7);
 
                   try {
                       if (jwtUtil.validateToken(token)) {
                    	  
                           throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid JWT Token");
                       }
                       String role = jwtUtil.extractRole(token);
//                       System.out.println(role);
                       boolean flag = validator.validateUsers(role,request);
//                       System.out.println(flag+":"+request.getURI().getPath());
                       if(!flag) {
                    	   throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "access denied"); 
                       }
                   } catch (Exception e) {
                       throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid JWT Token", e);
                   }
               } else {
            	   System.out.println("Token must start with 'Bearer '");
                   throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token must start with 'Bearer '");
               }
           }
 
           ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
               .header(HttpHeaders.AUTHORIZATION, request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
               .build();
         System.out.println("Validated");
           return chain.filter(exchange.mutate().request(modifiedRequest).build());
       };
   }
 
 
public static class Config {
 
}
 
}