package org.aop.utils;

import java.lang.reflect.Proxy;
import java.util.List;

import org.aop.base.AdviserFactory;
import org.aop.compose.Composer;

public class AopUtils {

	public static<T> T attach(Class<T> targetInterface, Object targetInstance, AdviserFactory adviserFactory) {
		
		List<?> adviserList = adviserFactory.create();
		
		return attach(targetInterface, targetInstance, adviserList);
	}
	
	public static<T> T attach(Class<T> targetInterface, Object targetInstance, List<?> adviserList) {
		
		T target = null;
		
		try {
			target = (T) Proxy.newProxyInstance(targetInstance.getClass().getClassLoader(), 
					new Class[] {targetInterface}, new Composer(targetInstance, adviserList));
		} catch (IllegalArgumentException | InstantiationException
				| IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		
		return target;
		
	}
	
}
