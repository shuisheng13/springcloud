package com.pactera.config.security;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.pactera.constant.ConstantUtlis;
import com.pactera.domain.LaunUser;
import com.pactera.utlis.JsonUtils;

/**
 * @description: user通用类
 * @author:woqu
 * @since:2018年5月24日 下午7:22:05
 */
public class UserUtlis {

	public static String userName() {
		User securityUser = securityUser();
		return securityUser != null ? securityUser.getUsername() : null;
	}

	public static Set<String> listPermissions() {
		User securityUser = securityUser();
		Collection<GrantedAuthority> authorities = securityUser.getAuthorities();
		if (authorities == null || authorities.size() == 0) {
			return null;
		}
		return AuthorityUtils.authorityListToSet(authorities);
	}

	public static LaunUser launUser() {
		User securityUser = securityUser();
		String jsonString = JsonUtils.ObjectToJson(securityUser);
		Map<?, ?> readValue = JsonUtils.JsonToMap(jsonString);
		String objectToJson = JsonUtils.ObjectToJson(readValue.get("object"));
		return JsonUtils.jsonToClass(objectToJson, LaunUser.class);
	}

	private static User securityUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal == null || ConstantUtlis.ANONY_MOUS_USER.equalsIgnoreCase(principal.toString())) {
			return null;
		}
		User user = (User) principal;
		return user;
	}

}
