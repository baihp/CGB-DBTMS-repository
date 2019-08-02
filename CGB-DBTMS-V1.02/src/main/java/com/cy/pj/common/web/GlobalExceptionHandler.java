package com.cy.pj.common.web;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cy.pj.common.vo.JsonResult;
import lombok.extern.slf4j.Slf4j;
/**
 * 此注解修饰的类为一个全局异常处理类
 * 当控制层出现异常时:
 * 1)首先检测当前controller是否有异常处理方法
 * 2)其次检测全局异常处理类是否有异常处理方法
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * @ExceptionHandler 描述的方法为一个异常处理方法
     * @param e
     * @return
     */
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
    public JsonResult doHandleRuntimeException(
			RuntimeException e) {
    	e.printStackTrace();//控制台
        log.error(e.getMessage());
		return new JsonResult(e);
	}
}
