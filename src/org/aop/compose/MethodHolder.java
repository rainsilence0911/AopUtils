package org.aop.compose;

import java.lang.reflect.Method;

import org.aop.base.annotation.ParamType;

class MethodHolder {
	
	private ParamType paramType;
	
	private Method m;

	public MethodHolder(ParamType paramType, Method m) {
		this.m = m;
		this.paramType = paramType;
	}
	
	public ParamType getParamType() {
		return paramType;
	}

	public Method getMethod() {
		return m;
	}
}
