package com.cy.pj.sys.controller;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.service.SysUserService;

@Controller
@RequestMapping("/user/")
public class SysUserController {
	@Autowired
	private SysUserService sysUserService;
	@RequestMapping("doUserListUI")
	public String doUserListUI() {
		return "sys/user_list";
	}
	@RequestMapping("doUserEditUI")
	public String doUserEditUI() {
		return "sys/user_edit";
	}
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(
			SysUser entity,
			Integer[] roleIds) {
		sysUserService.saveObject(entity, roleIds);
		return new JsonResult("insert ok");
	}

	@RequestMapping("doValidById")
	@ResponseBody
	public JsonResult doValidById(
			Integer id,Integer valid) {
		sysUserService.validById(id,
				valid,"admin");
		return new JsonResult("update ok");
	}

	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(
			String username,
			Integer pageCurrent) {
		return new JsonResult(sysUserService.findPageObjects(username, pageCurrent));
	}
	@RequestMapping("doLogin")
	@ResponseBody
	public JsonResult doLogin(String username,String password){
		//1.获取Subject对象
		Subject subject=SecurityUtils.getSubject();
		//2.通过Subject提交用户信息,交给shiro框架进行认证操作
		//2.1对用户进行封装
		UsernamePasswordToken token=
				new UsernamePasswordToken(
						username,//身份信息
						password);//凭证信息
		//2.2对用户信息进行身份认证
		subject.login(token);
		//分析:
		//1)token会传给shiro的SecurityManager
		//2)SecurityManager将token传递给认证管理器
		//3)认证管理器会将token传递给realm
		return new JsonResult("login ok");
	}
}
