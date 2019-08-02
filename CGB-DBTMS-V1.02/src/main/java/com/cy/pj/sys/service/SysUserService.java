package com.cy.pj.sys.service;

import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.vo.SysUserDeptVo;

public interface SysUserService 
   extends PageService<SysUserDeptVo>{
	
	int saveObject(SysUser entity,
			Integer[]roleIds);
	
	int validById(Integer id,
			Integer valid,
			String modifiedUser);

}
