package org.aop.test;

import org.aop.base.ProxyInfo;
import org.aop.base.annotation.AdviseType;
import org.aop.base.annotation.Adviser;
import org.aop.base.annotation.ParamType;


public class CatAdviser {

	@Adviser(methodName="eat", type=AdviseType.BEFORE, param=ParamType.NO_ARGUMENTS)
	public void processBefore() {
		System.out.println("before1");
	}
	
	@Adviser(methodName="eat", type=AdviseType.BEFORE, param=ParamType.ARGUMENTS)
	public void processBefore(Object[] args) {
		System.out.println("before2---" + args[0]);
	}
	
	@Adviser(methodName="eat", type=AdviseType.BEFORE, param=ParamType.TARGET)
	public void processBefore(ProxyInfo target) {
		System.out.println("before3---" + target.getClassName());
	}
	
	@Adviser(methodName="eat", type=AdviseType.BEFORE, param=ParamType.TARGET_ARGUMENTS)
	public void processBefore(ProxyInfo target, Object[] args) {
		System.out.println("before4---" + target.getClassName() + " args=" + args[0]);
	}
	
	@Adviser(methodName="eat", type=AdviseType.AFTER, param=ParamType.NO_ARGUMENTS)
	public void processAfter() {
		System.out.println("after1");
	}
	
	@Adviser(methodName="eat", type=AdviseType.AFTER, param=ParamType.TARGET)
	public void processAfter(ProxyInfo target) {
		System.out.println("after2---" + target.getClassName());
	}
	
	@Adviser(methodName="eat", type=AdviseType.AFTER, param=ParamType.TARGET_ARGUMENTS)
	public void processAfter(ProxyInfo target, Object[] args) {
		System.out.println("after3---" + target.getClassName() + " args=" + args[0]);
	}
	
	@Adviser(methodName="eat", type=AdviseType.AFTER, param=ParamType.ARGUMENTS_RETURN_VALUE)
	public void processAfter(Object[] args, Object returnValue) {
		System.out.println("after4--args=" + args[0] + "--return=" + returnValue);
	}
	
	@Adviser(methodName="eat", type=AdviseType.AFTER, param=ParamType.ARGUMENTS)
	public void processAfter(Object[] args) {
		System.out.println("after5--args=" + args[0]);
	}
	
	@Adviser(methodName="eat", type=AdviseType.AFTER, param=ParamType.TARGET_RETURN_VALUE)
	public void processAfter(ProxyInfo target, Object returnValue) {
		System.out.println("after6--" + target.getClassName());
	}
	
	@Adviser(methodName="eat", type=AdviseType.AFTER, param=ParamType.TARGET_ARGUMENTS_RETURN_VALUE)
	public void processAfter(ProxyInfo target, Object[] args, Object returnValue) {
		System.out.println("after7--" + target.getClassName());
	}
	
	@Adviser(methodName="eat", type=AdviseType.EXCEPTION, param=ParamType.ARGUMENTS_EXCEPTION)
	public void processCatch(Object[] args, Exception e) {
		System.out.println(e);
	}
	
}
