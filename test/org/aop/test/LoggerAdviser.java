package org.aop.test;

import org.aop.base.ProxyInfo;
import org.aop.base.annotation.AdviseType;
import org.aop.base.annotation.Adviser;
import org.aop.base.annotation.ParamType;

public class LoggerAdviser {

	@Adviser(methodName="[A-Za-z]*", type=AdviseType.BEFORE, param=ParamType.TARGET)
	public void processBefore(ProxyInfo targetInfo) {
		System.out.println("start----- " + targetInfo.getClassName() + "." + targetInfo.getMethodName());
	}
	
	@Adviser(methodName="[A-Za-z]*", type=AdviseType.AFTER, param=ParamType.TARGET)
	public void processAfter(ProxyInfo targetInfo) {
		System.out.println("end------ " + targetInfo.getClassName() + "." + targetInfo.getMethodName());
	}
	
}
