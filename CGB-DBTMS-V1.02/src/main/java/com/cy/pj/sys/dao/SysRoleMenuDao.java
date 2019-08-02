package com.cy.pj.sys.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**负责sys_role_menus关系表中的数据的操作*/
@Mapper
public interface SysRoleMenuDao {

	 //@Select("select menu_id from sys_role_menus where role_id=#{roleId}")
	 //List<Integer> findMenuIdsByRoleId(Integer roleId);
	  
	 /**保存角色和菜单的关系数据
	  * @param roleId 角色id
	  * @param menuids 多个菜单id*/
	 int insertObjects(
			 @Param("roleId")Integer roleId,
			 @Param("menuIds")Integer[]menuIds);
	
	 @Delete("delete from sys_role_menus where role_id=#{role_id}")
	 int deleteObjectsByRoleId(Integer roleId);
}
