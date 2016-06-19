package org.aop.compose;

import org.aop.base.ProxyInfo;

public class ProxyInfoImpl implements ProxyInfo {

	private String className;
	
	private String methodName;

	public ProxyInfoImpl(String className, String methodName) {
		this.className = className;
		this.methodName = methodName;
	}
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
}
