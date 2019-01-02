package com.pactera.config.security;

public class UserUtlis {

	//public static String USER_NAME() {
	//	User securityUser = securityUser();
	//	return securityUser!=null?securityUser.getUsername():null;
	//}
	//
	//public static Set<String> listPermissions(){
	//	User securityUser = securityUser();
	//	Collection<GrantedAuthority> authorities = securityUser.getAuthorities();
	//	if(authorities==null || authorities.size()==0)return null;
	//	return AuthorityUtils.authorityListToSet(authorities);
	//}
	//
	//public static LaunUser launUser(){
	//	User securityUser = securityUser();
	//	String jsonString = JsonUtils.ObjectToJson(securityUser);
	//	Map<?, ?> readValue = JsonUtils.JsonToMap(jsonString);
	//	String objectToJson = JsonUtils.ObjectToJson(readValue.get("object"));
	//	return JsonUtils.jsonToClass(objectToJson, LaunUser.class);
	//}
	//
	//private static User securityUser(){
	//	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	//	if (authentication == null)return null;
	//	Object principal = authentication.getPrincipal();
	//	if (principal == null || "anonymousUser".equalsIgnoreCase(principal.toString()))return null;
	//	User user = (User) principal;
	//	return user;
	//}

}
