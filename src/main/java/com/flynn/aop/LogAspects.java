package com.flynn.aop;
 
import java.util.Arrays;
 
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
 
@Aspect
public class LogAspects {
	
	//抽取公共的切入点表达式
	//1、本类引用
	//2、其他的切面引用
//	@Pointcut("execution(public int com.flynn.aop.MathCalculator.*(..))")
//	private void pointCut(){};
	
	//@Before在目标方法之前切入；切入点表达式（指定在哪个方法切入）
	//JoinPoint一定要出现在参数列表的第一位
//	@Before(value = "pointCut()")
//	public void logStart(JoinPoint joinpoint) {
//		System.out.println("切面注解:@Before :logStart>>>> 增强方法:"+joinpoint.getSignature().getName()+"()>>>> 方法参数:"+Arrays.toString(joinpoint.getArgs()));
//	}
//
//	@After(value ="com.flynn.aop.LogAspects.pointCut()")
//	public void logEnd(JoinPoint joinpoint) {
//		System.out.println("切面注解:@After  :logEnd>>>>> 增强方法 异常依然会执行:"+joinpoint.getSignature().getName()+"()>>>> 方法参数:"+Arrays.toString(joinpoint.getArgs()));
//	}
//
//	@AfterReturning(value ="execution(public int com.flynn.aop.MathCalculator.*(..))",returning="object")
//	public void logReturn(Object object) {
//		System.out.println("切面注解:@AfterReturning :logReturn>>>>, 增强方法抛出异常不会执行, 增强方法返回值"+object);
//	}
//
//	@AfterThrowing(value = "execution(public int com.flynn.aop.MathCalculator.*(..))",throwing = "object")
//	public void logException(Exception object) {
//		System.out.println("切面注解:@AfterThrowing  :logException>>>>, 增强方法异常返回异常信息:"+object);
//	}


	@After(value ="execution(* com.flynn.aop.MathCalculator.div(..))")
	public void logEnd(JoinPoint joinpoint) {
		System.out.println("切面注解:@After  :logEnd>>>>> 增强方法 异常依然会执行:"+joinpoint.getSignature().getName()+"()>>>> 方法参数:"+Arrays.toString(joinpoint.getArgs()));
	}


}