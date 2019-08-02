package com.cy.pj.common.aspect;


import org.apache.ibatis.binding.MapperMethod.MethodSignature;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.entity.SysLog;

import lombok.extern.slf4j.Slf4j;
/**
 * @Aspect 切面
 * @author 000
 *
 */
@Aspect
@Service
@Slf4j
public class SysLogAspect {
	@Autowired
	private SysLogDao sysLogDao;
	@Pointcut("bean(sysUserServiceImpl)")
	public void logPointCut() {}
	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		//System.out.println("around.before");
		//log.info("around.before");
		long startTime = System.currentTimeMillis();
		Object result = pjp.proceed();
		//System.out.println("around.after");
		//log.info("around.after");
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		log.info("方法执行的总时长:totalTime=" +totalTime);
		saveSysLog(pjp,totalTime);
		return result;
	}
	private void saveSysLog(ProceedingJoinPoint point, long totalTime) {
		/** 获取用户行为信息 */
		MethodSignature ms = (MethodSignature) point.getSignature();
		/** 封装用户行为信息 */
		SysLog log = new SysLog();
//		log.setIp(ip);
//		log.setUsername(username);
//		log.setOperation(operation);
//		log.setParams(point.getArgs().toString());
//		log.setCreatedTime(new Date());
//		log.setTime(time);
		/** */
		sysLogDao.insertObject(log);
	}
}
