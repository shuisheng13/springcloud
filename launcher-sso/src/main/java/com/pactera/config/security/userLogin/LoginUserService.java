package com.pactera.config.security.userLogin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.pactera.business.service.LaunPermissionsService;
import com.pactera.business.service.LaunUserService;
import com.pactera.config.security.DataUser;
import com.pactera.domain.LaunUser;
import com.pactera.utlis.HStringUtlis;

@Component
public class LoginUserService implements UserDetailsService{
	
	@Autowired
	private LaunUserService launUserService;
	@Autowired
	private LaunPermissionsService launPermissionsService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String password=null;
		LaunUser findUserByUserName = launUserService.findUserByUserName(username);
		if(findUserByUserName!=null)password=findUserByUserName.getPassWord();
		DataUser myUser = new DataUser(username,password,findlistPermissions(findUserByUserName));
		myUser.setObject(findUserByUserName);
		return myUser;
	}
	
	private List<GrantedAuthority> findlistPermissions(LaunUser user){
		String listPermissionsByUserAll = launPermissionsService.listPermissionsByUserAll(user);
		if(HStringUtlis.isNotBlank(listPermissionsByUserAll)&& listPermissionsByUserAll.length()>0){
			return AuthorityUtils.commaSeparatedStringToAuthorityList(listPermissionsByUserAll);
		}
		return AuthorityUtils.commaSeparatedStringToAuthorityList("");
	}
}
