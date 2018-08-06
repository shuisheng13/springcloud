package com.pactera.config.interceptor;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.pactera.config.exception.DataStoreException;
import com.pactera.config.exception.status.ErrorStatus;
import com.pactera.config.spring.SpringUtil;
import com.pactera.utlis.IdUtlis;
import com.pactera.utlis.IpUtlis;
import com.pactera.utlis.JsonUtils;
import com.pactera.utlis.TimeUtils;

import io.swagger.annotations.ApiOperation;

public class LogsInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// addLogs(handler,request);
		Boolean permissionsValidation = permissionsValidation(request);
		if (permissionsValidation) {
			// 添加日志
			addLogs(handler, request);
		} else {
			throw new DataStoreException(HttpStatus.FORBIDDEN, ErrorStatus.NOT_PERMISSIONS);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView model)
			throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception e)
			throws Exception {
	}

	public Boolean permissionsValidation(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		if (requestURI.contains("widget/export"))
			return true;
		if(requestURI.startsWith("/statistics"))return true;
		if(requestURI.contains("swagger") || requestURI.equals("/v2/api-docs"))return true;
		User securityUser = securityUser();
		if(securityUser==null)return false;
		Collection<GrantedAuthority> authorities = securityUser.getAuthorities();
		if (authorities == null || authorities.size() == 0)
			return false;
		Set<String> authorityListToSet = AuthorityUtils.authorityListToSet(authorities);
		if (authorityListToSet == null || authorityListToSet.size() == 0)
			return false;
		if (authorityListToSet.contains(requestURI))
			return true;
		return false;
	}

	public void addLogs(Object handler, HttpServletRequest request) {
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("id", String.valueOf(IdUtlis.Id()));
		try {
			if (handler instanceof HandlerMethod) {
				Map<?, ?> user = getUser();
				if (user != null && user.size() > 0) {
					String userId = String.valueOf(user.get("id"));
					hashMap.put("userId", userId);
					String userName = String.valueOf(user.get("userName"));
					hashMap.put("userName", userName);
					String channelId = String.valueOf(user.get("channelId"));
					hashMap.put("channelId", channelId);
				}
				String ip = IpUtlis.getIpAddr(request);
				hashMap.put("ip", ip);
				hashMap.put("date", TimeUtils.date2String(new Date()));
				HandlerMethod handlerMethod = (HandlerMethod) handler;
				hashMap.put("method", request.getRequestURI());
				hashMap.put("methodType", request.getMethod());
				Method method = handlerMethod.getMethod();
				ApiOperation annotation = method.getAnnotation(ApiOperation.class);
				if (annotation != null)
					hashMap.put("methodName", annotation.value());
			}
			LogsService logsService = SpringUtil.getBean(LogsService.class);
			logsService.saveUserLogs(hashMap);
		} catch (Exception e) {
			System.out.println("异常信息：" + e.getMessage());
		} finally {
			System.out.println("日志：" + JsonUtils.ObjectToJson(hashMap));
		}
	}

	public Map<?, ?> getUser() {
		User securityUser = securityUser();
		if (securityUser == null)
			return null;
		String jsonString = JsonUtils.ObjectToJson(securityUser);
		Map<?, ?> readValue = JsonUtils.JsonToMap(jsonString);
		String objectToJson = JsonUtils.ObjectToJson(readValue.get("object"));
		return JsonUtils.JsonToMap(objectToJson);
	}

	private static User securityUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null)
			return null;
		Object principal = authentication.getPrincipal();
		if (principal == null || "anonymousUser".equalsIgnoreCase(principal.toString()))
			return null;
		User user = (User) principal;
		return user;
	}

}
