package org.aop.base.annotation;

public enum ParamType {

	/**
	 * void method()
	 */
	NO_ARGUMENTS,
	
	/**
	 * void method(Object[] args)
	 */
	ARGUMENTS,
	
	/**
	 * void method(Object[] args, Object returnValue)
	 */
	ARGUMENTS_RETURN_VALUE,
	
	/**
	 * void method(Object target)
	 */
	TARGET,
	
	/**
	 * void method(Object target, Object[] args)
	 */
	TARGET_ARGUMENTS,
	
	/**
	 * void method(Object target, Object returnValue)
	 */
	TARGET_RETURN_VALUE,
	
	/**
	 * void method(Object target, Object[] args, Object returnValue)
	 */
	TARGET_ARGUMENTS_RETURN_VALUE,
	
	/**
	 * void method(Object target, Exception e)
	 */
	TARGET_EXCEPTION,
	
	/**
	 * void method(Object[] args, Exception e)
	 */
	ARGUMENTS_EXCEPTION,
	
	/**
	 * void method(Object target, Object[] args, Exception e)
	 */
	TARGET_ARGUMENTS_EXCEPTION
	
}
