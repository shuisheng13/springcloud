package com.pactera.business.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.pactera.business.dao.LaunChannelMapper;
import com.pactera.business.dao.LaunThemeMapper;
import com.pactera.business.dao.LaunUserMapper;
import com.pactera.business.service.LaunChannelService;
import com.pactera.business.service.LaunPermissionsService;
import com.pactera.config.exception.DataStoreException;
import com.pactera.config.exception.status.ErrorStatus;
import com.pactera.config.security.UserUtlis;
import com.pactera.constant.ConstantUtlis;
import com.pactera.domain.LaunChannel;
import com.pactera.domain.LaunPermissions;
import com.pactera.domain.LaunThemeAdministration;
import com.pactera.domain.LaunUser;
import com.pactera.utlis.HStringUtlis;
import com.pactera.utlis.IdUtlis;
import com.pactera.utlis.JsonUtils;
import com.pactera.vo.LaunAuthChannelVo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @description: 渠道的实现类
 * @author:woqu
 * @since:2018年5月24日 下午7:09:46
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LaunChannelServiceImpl implements LaunChannelService {

	@Autowired
	private LaunUserMapper launUserMapper;

	@Autowired
	private LaunChannelMapper launChannelMapper;

	@Autowired
	private LaunPermissionsService launPermissionsService;

	@Autowired
	private LaunThemeMapper launThemeMapper;

	@Override
	public Integer saveChannel(LaunChannel channel, Long userId, String[] permissionids) {
		Integer checkRepeatChannel = checkRepeatChannel(channel.getName(), channel.getChannelId());
		if (checkRepeatChannel != null && checkRepeatChannel > 0) {
			throw new DataStoreException(ErrorStatus.CHANNEL_MORE);
		}
		// 保存渠道信息
		Long channelId = IdUtlis.Id();
		channel.setId(channelId);
		channel.setUserId(userId);
		channel.setCreateDate(new Date());
		channel.setChannelStatus(0);
		launChannelMapper.insertSelective(channel);
		launPermissionsService.savePermissionsByChannelId(userId, channelId, permissionids);
		return ConstantUtlis.SUCCESS;
	}

	@Override
	public Integer deleteChannel(Long id) {
		// 删除渠道信息（假删除）
		LaunChannel selectByPrimaryKey = launChannelMapper.selectByPrimaryKey(id);
		if (selectByPrimaryKey != null) {
			selectByPrimaryKey.setUserId(null);
			selectByPrimaryKey.setChannelStatus(1);
			launChannelMapper.updateByPrimaryKey(selectByPrimaryKey);
		}
		// 删除渠道的权限
		launPermissionsService.deletePermissionsByChannelId(id);
		// 修改渠道用户的信息
		Example example = new Example(LaunUser.class);
		example.createCriteria().andEqualTo("channelId", id);
		List<LaunUser> selectByExample = launUserMapper.selectByExample(example);
		if (selectByExample != null && selectByExample.size() > 0) {
			for (LaunUser launUser : selectByExample) {
				launUser.setChannelId(null);
				launUser.setUserType(null);
				launUserMapper.updateByPrimaryKey(launUser);
			}
		}
		return ConstantUtlis.SUCCESS;
	}

	@Override
	public List<LaunAuthChannelVo> findChannelList(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Example example = new Example(LaunChannel.class);
		example.setOrderByClause("create_date desc");
		example.createCriteria().andIsNotNull("userId").andNotEqualTo("channelStatus", 1);
		// LaunUser launUser = UserUtlis.launUser();
		// Integer userType = launUser.getUserType();
		// if(userType!=null && userType==1){
		// example.createCriteria().andIsNotNull("userId").andNotEqualTo("channelStatus",1).andEqualTo("userId",
		// launUser.getId());
		// }else if(userType!=null && (userType==0 || userType==2)){
		// example.createCriteria().andIsNotNull("userId").andNotEqualTo("channelStatus",1);
		// }else{
		// return new ArrayList<LaunChannel>();
		// }
		List<LaunChannel> selectByExample = launChannelMapper.selectByExample(example);
		List<LaunAuthChannelVo> authChannelVos = new ArrayList<>();
		if (selectByExample != null && selectByExample.size() > 0) {
			for (int i = 0; i < selectByExample.size(); i++) {
				LaunChannel launChannel = selectByExample.get(i);
				Long id = launChannel.getId();
				List<LaunPermissions> findPermissionsByChannelId = launPermissionsService
						.findPermissionsByChannelId(id);
				if (findPermissionsByChannelId == null || findPermissionsByChannelId.size() == 0) {
					findPermissionsByChannelId = UserUtlis.launUser().getListPermissions();
				}
				launChannel.setListPermissions(findPermissionsByChannelId);
				LaunAuthChannelVo authChannelVo = new LaunAuthChannelVo();
				authChannelVo.setId(launChannel.getId());
				authChannelVo.setName(launChannel.getName());
				authChannelVo.setListPermissions(findPermissionsByChannelId);
				authChannelVo.setLogo(launChannel.getLogo());
				authChannelVos.add(authChannelVo);
			}
		}
		return authChannelVos;
	}

	@Override
	public Integer updateChannel(LaunChannel channel, Long userId, String[] permissionids) {
		if (userId == null) {
			throw new DataStoreException(ErrorStatus.USER_ISNULL);
		}
		// 添加更新时间
		channel.setUpdateDate(new Date());
		// 修改渠道信息
		launChannelMapper.updateByPrimaryKeySelective(channel);
		// 删除原来的渠道管理员
		Long id = channel.getId();
		Example example = new Example(LaunUser.class);
		example.createCriteria().andEqualTo("channelId", id).andEqualTo("userType", 1);
		List<LaunUser> selectByExample = launUserMapper.selectByExample(example);
		if (selectByExample != null && selectByExample.size() > 0) {
			for (LaunUser launUser : selectByExample) {
				launPermissionsService.deletePermissionsByUserId(launUser.getId());
				launUser.setChannelId(null);
				launUser.setUserType(null);
				launUserMapper.updateByPrimaryKey(launUser);
			}
		}
		// 修改用户为渠道管理员
		LaunUser launUser = new LaunUser();
		launUser.setId(userId);
		launUser.setChannelId(id);
		launUser.setUserType(1);
		launUserMapper.updateByPrimaryKeySelective(launUser);
		// 修改用户的权限
		launPermissionsService.updatePermissionsByUserId(userId, permissionids);
		return ConstantUtlis.SUCCESS;
	}

	@Override
	public Integer checkRepeatChannel(String channelName, String channelId) {
		Example example = new Example(LaunChannel.class);
		Criteria createCriteria = example.createCriteria();
		// 过滤掉删除的渠道
		createCriteria.andNotEqualTo("channelStatus", 1);
		if (HStringUtlis.isNotBlank(channelName) && channelName.length() > 0) {
			createCriteria.andEqualTo("name", channelName);
		}
		if (HStringUtlis.isNotBlank(channelId) && channelId.length() > 0) {
			createCriteria.andNotEqualTo("channelId", channelId);
		}
		return launChannelMapper.selectCountByExample(example);
	}

	@Override
	public LaunChannel findChannelById(Long id) {
		LaunChannel launChannel = launChannelMapper.selectByPrimaryKey(id);
		Example example = new Example(LaunUser.class);
		example.createCriteria().andEqualTo("channelId", id).andEqualTo("userType", 1);
		List<LaunUser> selectByExample = launUserMapper.selectByExample(example);
		if (selectByExample != null && selectByExample.size() > 0) {
			LaunUser launUser = selectByExample.get(0);
			launChannel.setUser(launUser);
			List<LaunPermissions> findPermissionsByUserId = launPermissionsService
					.findPermissionsByUserId(launUser.getId());
			launChannel.setListPermissions(findPermissionsByUserId);
		}
		return launChannel;
	}

	@Override
	public String getChannelId() {
		String channelId = HStringUtlis.ChannelId();
		Example example = new Example(LaunChannel.class);
		example.createCriteria().andEqualTo("channelId", channelId);
		int selectCountByExample = launChannelMapper.selectCountByExample(example);
		if (selectCountByExample == 0) {
			return channelId;
		} else {
			return getChannelId();
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Integer updateChannelList(String channelList) {
		if (HStringUtlis.isNoneBlank(channelList) && channelList.length() > 0) {
			List<Map> jsonToList = JsonUtils.jsonToList(channelList, Map.class);
			if (jsonToList != null && jsonToList.size() > 0) {
				for (Map map : jsonToList) {
					Long id = new Long(String.valueOf(map.get("id")));
					String permissionsStr = String.valueOf(map.get("permissions"));
					String[] permissions = JsonUtils.jsonToClass(permissionsStr, String[].class);
					launPermissionsService.updatePermissionsByChannelId(id, permissions);
				}
			}
		}
		return ConstantUtlis.SUCCESS;
	}

	@Override
	public List<LaunChannel> findChannelAll() {
		Example example = new Example(LaunChannel.class);
		example.createCriteria().andIsNotNull("userId").andNotEqualTo("channelStatus", 1);
		return launChannelMapper.selectByExample(example);
	}

	@Override
	public List<LaunUser> findUserByChannelId(Long channelId) {
		if (channelId == null) {
			return null;
		}
		Example example = new Example(LaunUser.class);
		example.createCriteria().andEqualTo("channelId", channelId);
		return launUserMapper.selectByExample(example);
	}

	@Override
	public Integer deleteChannelCue(Long id) {
		Example example = new Example(LaunThemeAdministration.class);
		example.createCriteria().andEqualTo("creatorChannelId", id).andEqualTo("status", 2);
		List<LaunThemeAdministration> selectByExample = launThemeMapper.selectByExample(example);
		return selectByExample.size();
	}

}
