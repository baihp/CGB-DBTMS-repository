package com.cy.pj.common.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class SpringShiroConfig {
	//一般用于整合第三方bean资源
	@Bean("securityManager")
	public SecurityManager newSecurityManager() {
		DefaultWebSecurityManager sManager = new DefaultWebSecurityManager();
		return sManager;
	}
	public ShiroFilterFactoryBean newShiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean sfBean = new ShiroFilterFactoryBean();
		sfBean.setSecurityManager(securityManager);
		
		return sfBean;
		
	}
	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor newLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	@DependsOn("lifecycleBeanPostProcessor")
	@Bean
	public DefaultAdvisorAutoProxyCreator newDefaultAdvisorAutoProxyCreator() {
		return new DefaultAdvisorAutoProxyCreator();
	}
	@Bean
	public AuthorizationAttributeSourceAdvisor newAuthorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor aSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		aSourceAdvisor.setSecurityManager(securityManager);
		
		return aSourceAdvisor;
	}
}
