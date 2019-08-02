package com.cy.pj.sys.service.impl;
import java.util.List;
import java.util.UUID;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.util.PageUtil;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.service.SysUserService;
import com.cy.pj.sys.vo.SysUserDeptVo;
@Service 
public class SysUserServiceImpl implements SysUserService {
	@Autowired 
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Override
	public int saveObject(SysUser entity, Integer[] roleIds) {
		//1.参数校验
		if(entity==null)
		throw new ServiceException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
		throw new ServiceException("用户名不能为空");
	    if(StringUtils.isEmpty(entity.getPassword()))
	    throw new ServiceException("密码不能为空");
	    if(roleIds==null || roleIds.length==0)
	    throw new ServiceException("至少要为用户分配角色");
	    //2.对密码进行加密
	    String salt=UUID.randomUUID().toString();
	    SimpleHash sh=
	    new SimpleHash("MD5", //algorithmName算法
	    		entity.getPassword(),//source 未加密的密码
	    		salt,//salt
	    		1);//hashIterations加密次数
        entity.setSalt(salt);
        entity.setPassword(sh.toHex());
        //3.将对象写入到数据库
        int rows=sysUserDao.insertObject(entity);
        sysUserRoleDao.insertObjects(entity.getId(), roleIds);
		return rows;
	}
	@Override
	public int validById(Integer id, 
			Integer valid, 
			String modifiedUser) {
		//1.参数校验
		if(id==null||id<1)
		throw new IllegalArgumentException("id值不正确");
		if(valid!=1&&valid!=0)
	    throw new IllegalArgumentException("状态值不正确");
		//2.修改状态
		int rows=sysUserDao.validById(id, valid, modifiedUser);
		//3.返回结果
		if(rows==0)
		throw new ServiceException("记录可能已经不存在");
		return rows;
	}
	@Override
	public PageObject<SysUserDeptVo> findPageObjects(
		String username, Integer pageCurrent) {
		//1.参数校验
		if(pageCurrent==null||pageCurrent<1)
		throw new IllegalArgumentException("页码不正确");
		//2.查询总记录数并进行校验
		int rowCount=sysUserDao.getRowCount(username);
		if(rowCount==0)
		throw new ServiceException("记录不存在");
		//3.查询当前页要呈现的记录
		//3.1页面大小,例如每页最多显示3条
		int pageSize=PageUtil.getPageSize();
		//3.2当前页起始位置
		int startIndex=PageUtil.getStartIndex(pageCurrent);
		List<SysUserDeptVo> records=
		sysUserDao.findPageObjects(username,
				startIndex,pageSize);
		//4.对查询结果进行计算和封装并返回
		return PageUtil.newPageObject(
	    pageCurrent, rowCount, pageSize, records);
	}

}
