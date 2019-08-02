package com.cy.pj.sys.service.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.entity.SysUser;

public class ShiroUserRealm extends AuthorizingRealm{
	@Autowired
	private SysUserDao sysUserDao;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		SysUser user = sysUserDao.findUserByUserName(username);
		if (user == null) {
			throw new UnknownAccountException();
		}
		if (user.getValid() == 0) {
			throw new LockedAccountException();
		}
		ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
				user, 
				user.getPassword(), 
				credentialsSalt, 
				getName());
		return info;
	}
}

