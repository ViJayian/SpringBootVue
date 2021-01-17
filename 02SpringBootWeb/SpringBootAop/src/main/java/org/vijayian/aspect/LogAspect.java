package org.vijayian.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * aspect
 *
 * @author ViJay
 * @date 2021-01-17
 */
@Aspect
@Component
public class LogAspect {
    //>> TODO 三个*的意思 任意返回值，任意类，任意方法 ..代表任意参数 .
    @Pointcut("execution(* org.vijayian.service.*.*(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void before(JoinPoint jp) {
        System.out.println("before 执行...");
    }

    @After("pointcut()")
    public void after(JoinPoint jp) {
        System.out.println("after 执行...");
    }

    @AfterReturning(value = "pointcut()", returning = "result")
    public void afterReturn(JoinPoint jp, Object result) {
        System.out.println("afterReturn 执行...");
        System.out.println("返回值为：" + result);
    }

    @AfterThrowing(value = "pointcut()", throwing = "e")
    public void afterThrow(JoinPoint jp, Exception e) {
        System.out.println("afterThrow 执行...");
        System.out.println(e.getMessage());
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("around 执行...");
        return jp.proceed();
    }
}
