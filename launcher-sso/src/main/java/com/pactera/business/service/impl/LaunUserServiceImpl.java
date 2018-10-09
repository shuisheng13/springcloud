package com.pactera.business.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pactera.business.dao.LaunChannelMapper;
import com.pactera.business.dao.LaunUserMapper;
import com.pactera.business.service.LaunPermissionsService;
import com.pactera.business.service.LaunUserService;
import com.pactera.config.httpclien.RestTemplateClient;
import com.pactera.config.spring.SpringUtil;
import com.pactera.domain.LaunChannel;
import com.pactera.domain.LaunPermissions;
import com.pactera.domain.LaunUser;
import com.pactera.utlis.HStringUtlis;
import com.pactera.utlis.IdUtlis;
import com.pactera.utlis.TimeUtils;

import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class LaunUserServiceImpl implements LaunUserService {

	@Autowired
	private LaunUserMapper launUserMapper;
	@Autowired
	private LaunChannelMapper launChannelMapper;
	@Autowired
	private LaunPermissionsService launPermissionsService;

	@Override
	public LaunUser findUserByUserName(String userName) {
		if (HStringUtlis.isNotBlank(userName)) {
			Example example = new Example(LaunUser.class);
			example.createCriteria().andEqualTo("userName", userName);
			List<LaunUser> selectByExample = launUserMapper.selectByExample(example);
			if (selectByExample != null && selectByExample.size() > 0 && selectByExample.size() == 1) {
				LaunUser launUser = selectByExample.get(0);
				// 渠道用户登录时，封装渠道id
				if (launUser.getUserType() == 1) {
					LaunChannel channel = launChannelMapper.selectByPrimaryKey(launUser.getChannelId());
					launUser.setChannelIdStr(channel.getChannelId());
				}
				List<LaunPermissions> listPermissions = launPermissionsService.listPermissionsByUser(launUser);
				if (listPermissions == null || listPermissions.size() == 0) {
					launUser.setPassWord(null);
				} else {
					launUser.setListPermissions(listPermissions);
				}
				return launUser;
			}
		}
		return null;
	}

	@Override
	@Async
	public void saveUserLogs(LaunUser user, String ip) {
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("id", String.valueOf(IdUtlis.Id()));
		hashMap.put("userId", String.valueOf(user.getId()));
		hashMap.put("userName", String.valueOf(user.getUserName()));
		hashMap.put("channelId", String.valueOf(user.getChannelId()));
		hashMap.put("ip", ip);
		hashMap.put("date", TimeUtils.date2String(new Date()));
		hashMap.put("method", "/user/login");
		hashMap.put("methodType", "POST");
		hashMap.put("methodName", "用户登录");
		RestTemplateClient restTemplateClient = SpringUtil.getBean(RestTemplateClient.class);
		restTemplateClient.exchangeObject("http://logs/addStoreBrowseLogs", HttpMethod.POST, null, hashMap);
	}
}
