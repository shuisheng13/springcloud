package com.pactera.config.aop;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author zhaodong
 * @Date 14:30 2019/1/18
 */
@Component
@Aspect
@Slf4j
public class LoggerAop {

    @Pointcut("execution(* com.pactera.business.controller.*.*(..))")
    public void loggerReParam() {
    }


    @Before("loggerReParam()")
    public void before(JoinPoint joinPoint) {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        StringBuilder paramsBuf = new StringBuilder();
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames(); // 参数名
        if (arguments.length != argNames.length) {
            return;
        }
        for (int i = 0; i < arguments.length; i++) {
            paramsBuf.append(argNames[i] + ":" + arguments[i]);
            paramsBuf.append("\n");
        }
        log.info("\n"+"类名："+targetName+"\n" + "请求得方法名：" + methodName + "\n" + "参数名和参数为: " + "\n" + paramsBuf.toString());
    }
}