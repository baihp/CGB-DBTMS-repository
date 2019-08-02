package com.cy.pj.sys.dao;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserRoleDao {
	 /**
	  * 保存用户和角色的关系数据
	  * @param userId
	  * @param roleIds
	  * @return
	  */
	 int insertObjects(
			 @Param("userId")Integer userId,
			 @Param("roleIds")Integer[] roleIds);
	 @Delete("delete from sys_user_roles where role_id=#{roleId}")
	 int deleteObjectsByRoleId(Integer roleId);
}
