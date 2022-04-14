package com.flynn.aop;
 
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

 
public class IOCTestAOP {
	@Test
	public void test01() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAop.class);
		
		MathCalculator mathCalculator = applicationContext.getBean(MathCalculator.class);
//		mathCalculator.div(10, 1);
		mathCalculator.div(10, 0);
		applicationContext.close();
	}
 
}