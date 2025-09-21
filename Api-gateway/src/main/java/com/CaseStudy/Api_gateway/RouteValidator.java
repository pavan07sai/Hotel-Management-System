package com.CaseStudy.Api_gateway;

import java.util.List;
import java.util.function.Predicate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
 
@Component
public class RouteValidator {
 
	
	
	
   private static final List<String> openApiEndpoints = List.of(
   		"/auth/login"
   );
   
//   private static final List<String> ownerApiEndpoints =List.of(
//		   "/guests/delete/.*",
//		   "/reservations/delete/.*",
//		   "rooms/delete/.*"
//		   );
   
   private static final List<String> managerApiEndpoints =List.of(
		   "/auth/register", 
		   "/guests/add","/guests/update/.*","/guests/getall","/guests/getby/.*",
		   "/reservations/reserve","/reservations/getall","/reservations/getby/.*","/reservations/update/.*",
		   "/rooms/add","/rooms/update/.*","/rooms/getall","/rooms/getby/.*"
		   );
   private static final List<String> receptionistApiEndpoints = List.of(
		   "/guests/add","/guests/getall","/guests/getby/.*",
		   "/reservations/reserve","/reservations/getall","/reservations/getby/.*",
		   "/rooms/getall","/rooms/getby/.*"
		   );
		  
   public boolean validateUsers(String role, ServerHttpRequest request) {
	   if(isManagerAuthorized.test(request) && (role.equals("MANAGER")|| role.equals("OWNER"))) {
		   return true;
	   }
	   if(isReceptionistAuthorized.test(request) && (role.equals("RECEPTIONIST")|| role.equals("OWNER"))) {
		   return true;
	   }
	   if(role.equals("OWNER")) {
		   return true;
	   }
	   return false;
   }
   // Predicate to check if the request is secured
   public Predicate<ServerHttpRequest> isSecured = request ->
       openApiEndpoints.stream().noneMatch(uri -> request.getURI().getPath().matches(uri));
       
       public Predicate<ServerHttpRequest> isManagerAuthorized = request ->
       managerApiEndpoints.stream().anyMatch(uri -> request.getURI().getPath().matches(uri));
       
       public Predicate<ServerHttpRequest> isReceptionistAuthorized = request ->
       receptionistApiEndpoints.stream().anyMatch(uri -> request.getURI().getPath().matches(uri));
       
//       public Predicate<ServerHttpRequest> isOwnerAuthorized = request ->
//       ownerApiEndpoints.stream().anyMatch(uri -> request.getURI().getPath().matches(uri));
}
