package org.aop.compose;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aop.base.AdviserFactory;
import org.aop.base.ProxyInfo;

public class Composer implements InvocationHandler {

	private Object target;
	private List<ComposeHandler> composerList = new ArrayList<>();
	
	public Composer(Object target, List<?> advisers) 
		throws InstantiationException, IllegalAccessException {
		
		this.target = target;
		
		for (Object adviser : advisers) {
			composerList.add(new ComposeHandler(target.getClass(), adviser));
		}
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		
		ProxyInfo info = new ProxyInfoImpl(target.getClass().getName(), method.getName());
		
		for (ComposeHandler composer : composerList) {
			composer.processBefore(method, info, args);
		}
		
		Object value = null;
		
		try {
			value = method.invoke(target, args);
			
			for (int index = composerList.size() - 1; index >= 0; index--) {
				composerList.get(index).processAfter(method, info, args, value);
			}
			
		} catch(Exception e) {
			for (ComposeHandler composer : composerList) {
				composer.processCatch(method, info, args, e);
			}
		}
		
		return value;
	}
	
}
