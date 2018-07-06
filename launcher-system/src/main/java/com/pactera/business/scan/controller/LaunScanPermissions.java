package com.pactera.business.scan.controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pactera.business.scan.service.LaunScanPermissionsService;
import com.pactera.domain.LaunPermissions;
import com.pactera.result.ResultData;
import com.pactera.utlis.HStringUtlis;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
public class LaunScanPermissions {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private LaunScanPermissionsService launScanPermissionsService;

	@GetMapping("scanPermissions")
	@SuppressWarnings("deprecation")
	public ResponseEntity<ResultData> scanPermissions() {
		List<LaunPermissions> list = new ArrayList<LaunPermissions>();
		Map<String, Object> map = applicationContext.getBeansWithAnnotation(RestController.class);
		for (Map.Entry<String, Object> m : map.entrySet()) {
			LaunPermissions launPermissions = new LaunPermissions();
			if (m.getKey().equalsIgnoreCase("LaunScanPermissions")) {
				break;
			}
			Class<?> superclass = m.getValue().getClass().getSuperclass();
			Api annotation = superclass.getAnnotation(Api.class);
			if (annotation != null) {
				String description = annotation.description();
				int hashCode = description.hashCode();
				Long id = HStringUtlis.autoCompeleteVal(new Long(Math.abs(hashCode)));
				launPermissions.setId(id);
				launPermissions.setName(description);
				launPermissions.setIntroducs(description);
				launPermissions.setParentId(0L);
				launPermissions.setLevels(1);
				launPermissions.setStatus(0);
//				launScanPermissionsService.save(launPermissions);
				// System.out.println("一级菜单:" + description);

			}

			RequestMapping requestMapping = superclass.getAnnotation(RequestMapping.class);
			String benPath = "";
			if (requestMapping != null) {
				String[] value = requestMapping.value();
				if (value != null && value.length > 0) {
					benPath = value[0];
				}
			}
			if (HStringUtlis.isNotBlank(benPath) && !benPath.startsWith("/")) {
				benPath = "/" + benPath;
			}
			Method[] methods = superclass.getMethods();
			if (methods != null && methods.length > 0) {
				for (Method method : methods) {
					String methodDescribe = "";
					ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
					if (apiOperation != null) {
						methodDescribe = apiOperation.value();
					} else {
						break;
					}
					String methodName = "";
					GetMapping getMapping = method.getAnnotation(GetMapping.class);
					if (getMapping != null) {
						String[] value = getMapping.value();
						if (value != null && value.length > 0) {
							methodName = value[0];
						}
					}
					PostMapping postMapping = method.getAnnotation(PostMapping.class);
					if (postMapping != null) {
						String[] value = postMapping.value();
						if (value != null && value.length > 0) {
							methodName = value[0];
						}
					}
					if (HStringUtlis.isNotBlank(methodName) && !methodName.startsWith("/")) {
						methodName = "/" + methodName;
					}
					if (HStringUtlis.isBlank(methodName)) {
						methodName = "/";
					}
					LaunPermissions launPermissions2 = new LaunPermissions();
					String resources = benPath + methodName;
					int hashCode = resources.hashCode();
					Long id = HStringUtlis.autoCompeleteVal(new Long(Math.abs(hashCode)));
					launPermissions2.setId(id);
					launPermissions2.setName(methodDescribe);
					launPermissions2.setIntroducs(methodDescribe);
//					launPermissions2.setParentId(launPermissions.getId());
					launPermissions2.setResources(resources);
					launPermissions2.setLevels(3);
					launPermissions2.setStatus(0);
					launScanPermissionsService.save(launPermissions2);
					// System.out.println("二级菜单:" + methodDescribe + ":" +
					// benPath + methodName);
					// 二级菜单:添加应用:application/
					// 二级菜单:获取应用列表:application/
					// 二级菜单:根据应用id获取海报详情:application/findPosterByAppId
					// 二级菜单:上传海报图片:application/uploadPoster
					// 二级菜单:上传应用:application/upload
					// 二级菜单:保存海报详情:application/savePoster
				}
			}
		}
		return ResponseEntity.ok(new ResultData(list));
	}
}
