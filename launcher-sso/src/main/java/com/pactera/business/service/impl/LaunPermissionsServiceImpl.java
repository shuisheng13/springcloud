package com.pactera.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pactera.business.dao.LaunPermissionsMapper;
import com.pactera.business.service.LaunPermissionsService;
import com.pactera.domain.LaunPermissions;
import com.pactera.domain.LaunUser;
import com.pactera.utlis.HStringUtlis;

import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class LaunPermissionsServiceImpl implements LaunPermissionsService {

	@Autowired
	private LaunPermissionsMapper launPermissionsMapper;

	@Override
	public List<LaunPermissions> listPermissionsByUser(LaunUser user) {
		List<LaunPermissions> arrayList = new ArrayList<LaunPermissions>();
		Integer userType = user.getUserType();
		if (userType != null && userType == 0) {
			arrayList = findPermissionsAll();
		} else {
			arrayList = findPermissionsByUserId(user.getId());
		}
		arrayList=deleteChannelPermissions(arrayList,userType);
		return arrayList;
	}

	@Override
	public String listPermissionsByUserAll(LaunUser user) {
		Integer userType = user.getUserType();
		if (userType != null && userType == 0) {
			List<LaunPermissions> listPermissions = findPermissionsByAll();
			return listPermissionsToStrings(listPermissions);
		} else {
			return listPermissionsToStrings(findPermissionsByUserIdAndChild(user.getId()));
		}
	}

	@Override
	public List<LaunPermissions> findPermissionsAll() {
		Example example = new Example(LaunPermissions.class);
		example.createCriteria().andEqualTo("parentId", 0L);
		List<LaunPermissions> selectByExample = launPermissionsMapper.selectByExample(example);
		if (selectByExample != null && selectByExample.size() > 0) {
			for (LaunPermissions launPermissions : selectByExample) {
				Example example2 = new Example(LaunPermissions.class);
				example2.createCriteria().andEqualTo("parentId", launPermissions.getId());
				List<LaunPermissions> selectByExample2 = launPermissionsMapper.selectByExample(example2);
				if (selectByExample2 != null && selectByExample2.size() > 0) {
					launPermissions.setListPermissions(selectByExample2);
				}
			}
		}
		return selectByExample;
	}

	@Override
	public List<LaunPermissions> findPermissionsByUserId(Long id) {
		List<LaunPermissions> listUserPermissions = new ArrayList<LaunPermissions>();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("id", id);
		// 获取用户的权限
		List<LaunPermissions> findPermissionsByUserId = launPermissionsMapper.findPermissionsByUserId(hashMap);
		if (findPermissionsByUserId != null && findPermissionsByUserId.size() > 0) {
			// 获取所有权限
			List<LaunPermissions> findPermissionsAll = findPermissionsAll();
			for (LaunPermissions launPermissions : findPermissionsAll) {
				List<LaunPermissions> listPermissionst = new ArrayList<LaunPermissions>();
				List<LaunPermissions> listPermissions = launPermissions.getListPermissions();
				for (LaunPermissions launPermissionsa : listPermissions) {
					for (LaunPermissions launPermissionsb : findPermissionsByUserId) {
						Long resourcesa = launPermissionsa.getId();
						Long resourcesb = launPermissionsb.getId();
						if (String.valueOf(resourcesa).equalsIgnoreCase(String.valueOf(resourcesb))) {
							listPermissionst.add(launPermissionsb);
						}
					}
				}
				if (listPermissionst != null && listPermissionst.size() > 0) {
					launPermissions.setListPermissions(listPermissionst);
					listUserPermissions.add(launPermissions);
				}
			}
		}
		return listUserPermissions;
	}

	private List<LaunPermissions> findPermissionsByAll() {
		Example example = new Example(LaunPermissions.class);
		example.createCriteria().andEqualTo("levels", 3);
		return launPermissionsMapper.selectByExample(example);
	}

	private List<LaunPermissions> findPermissionsByUserIdAndChild(Long userId) {
		List<LaunPermissions> listPermissions=new ArrayList<LaunPermissions>();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("id", userId);
		// 获取用户的权限 
		List<LaunPermissions> findPermissionsByUserId = launPermissionsMapper.findPermissionsByUserId(hashMap);
		if (findPermissionsByUserId != null && findPermissionsByUserId.size() > 0) {
			for (LaunPermissions launPermissions : findPermissionsByUserId) {
				String subPersion = launPermissions.getSubPersion();
				if (HStringUtlis.isNotBlank(subPersion) && subPersion.length() > 0) {
					String[] split = subPersion.split(",");
					if (split != null && split.length > 0) {
						for (String str : split) {
							LaunPermissions selectByPrimaryKey = launPermissionsMapper
									.selectByPrimaryKey(new Long(str));
							listPermissions.add(selectByPrimaryKey);
						}
					}
				}
			}
		}
		return listPermissions;
	}

//	private String listPermissionsToString(List<LaunPermissions> listPermissions) {
//		StringBuffer stringBuffer = new StringBuffer();
//		if (listPermissions != null && listPermissions.size() > 0) {
//			for (LaunPermissions launPermissions : listPermissions) {
//				List<LaunPermissions> listPermissions2 = launPermissions.getListPermissions();
//				if (listPermissions2 != null && listPermissions2.size() > 0) {
//					for (LaunPermissions launPermissions2 : listPermissions2) {
//						String resources = launPermissions2.getResources();
//						if (HStringUtlis.isNotBlank(resources) && resources.length() > 0) {
//							stringBuffer.append(resources + ",");
//						}
//					}
//				}
//			}
//		}
//		if (stringBuffer != null && stringBuffer.length() > 0) {
//			return stringBuffer.substring(0, stringBuffer.length() - 1);
//		}
//		return null;
//	}

	private String listPermissionsToStrings(List<LaunPermissions> listPermissions) {
		StringBuffer stringBuffer = new StringBuffer();
		if (listPermissions != null && listPermissions.size() > 0) {
			for (LaunPermissions launPermissions : listPermissions) {
				String resources = launPermissions.getResources();
				if (HStringUtlis.isNotBlank(resources) && resources.length() > 0) {
					stringBuffer.append(resources + ",");
				}
			}
		}
		if (stringBuffer != null && stringBuffer.length() > 0) {
			return stringBuffer.substring(0, stringBuffer.length() - 1);
		}
		return null;
	}
	
	private List<LaunPermissions> deleteChannelPermissions(List<LaunPermissions> arrayList, Integer userType) {
		List<LaunPermissions> list= new ArrayList<LaunPermissions>();
		if((userType==null || userType==1) && arrayList!=null && arrayList.size()>0){
			for (int i = 0; i < arrayList.size(); i++) {
				LaunPermissions launPermissions = arrayList.get(i);
				Long id = launPermissions.getId();
				String name = launPermissions.getName();
				if(id!=4 && !name.equals("渠道管理")){
					list.add(launPermissions);
				}
			}
		}else{
			return arrayList;
		}
		return list;
	}

}
