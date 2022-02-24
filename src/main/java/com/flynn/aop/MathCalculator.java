package com.flynn.aop;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

public class MathCalculator {
 
	public int div(int i, int j) {
		System.out.println("业务内部方法执行");
		return i / j;
	}
 
}