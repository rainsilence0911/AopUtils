package org.aop.compose;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.aop.base.ProxyInfo;
import org.aop.base.annotation.AdviseType;
import org.aop.base.annotation.Adviser;
import org.aop.base.annotation.ParamType;

class ComposeHandler {
	
	private Object adviser;
	
	private static final ParamType[] BEFORE_PARAM_SUPPORT = new ParamType[] {
		ParamType.NONE,
		ParamType.ARGUMENTS,
		ParamType.TARGET,
		ParamType.TARGET_ARGUMENTS
	};
	
	private static final ParamType[] AFTER_PARAM_SUPPORT = new ParamType[] {
		ParamType.NONE,
		ParamType.ARGUMENTS, 
		ParamType.TARGET,
		ParamType.TARGET_ARGUMENTS,
		ParamType.TARGET_RETURN_VALUE,
		ParamType.TARGET_ARGUMENTS_RETURN_VALUE,
		ParamType.ARGUMENTS_RETURN_VALUE
	};
	
	private static final ParamType[] CATCH_PARAM_SUPPORT = new ParamType[] {
		ParamType.TARGET_EXCEPTION,
		ParamType.ARGUMENTS_EXCEPTION,
		ParamType.TARGET_ARGUMENTS_EXCEPTION
	};
	
	private Map<String, List<MethodHolder>> beforeMapping = new HashMap<>();
	
	private Map<String, List<MethodHolder>> afterMapping = new HashMap<>();
	
	private Map<String, List<MethodHolder>> catchMapping = new HashMap<>();
	
	
	public ComposeHandler(Class cls, Object adviser) 
			throws InstantiationException, IllegalAccessException {
		
		Method[] methods = adviser.getClass().getDeclaredMethods();

		Method[] targetMethods = cls.getDeclaredMethods();
		
		for (Method m : methods) {
			Adviser adviserAnnotation = m.getAnnotation(Adviser.class);
			if (adviserAnnotation == null) {
				continue;
			}
			
			String methodPattern = adviserAnnotation.methodName();
			
			ParamType paramType = adviserAnnotation.param();
			AdviseType type = adviserAnnotation.type();
			
			for (Method targetMethod : targetMethods) {
				
				String methodName = targetMethod.getName();
				
				if (!Pattern.matches(methodPattern, methodName)) {
					continue;
				}
				
				String genericName = getMethodName(targetMethod);
				
				if (type == AdviseType.BEFORE) {
					initializeBeforeHandler(genericName, m, paramType);
				} else if (type == AdviseType.AFTER) {
					initializeAfterHandler(genericName, m, paramType);
				} else {
					initializeCatchHandler(genericName, m, paramType);
				}
			}
		}
		
		this.adviser = adviser;
		
	}
	
	private void initializeMapping(Map<String, List<MethodHolder>> mapping, 
			MethodHolder holder, String methodName) {
		
		if (!mapping.containsKey(methodName)) {
			mapping.put(methodName, new ArrayList<MethodHolder>());
		}
		
		mapping.get(methodName).add(holder);
	}
	
	private void initializeBeforeHandler(String methodName, Method m, ParamType paramType) {
		
		if (!ComposeValidator.validate(BEFORE_PARAM_SUPPORT, paramType, m)) {
			System.err.println(m.getName() + " is out of scope");
			return;
		}
		initializeMapping(beforeMapping, new MethodHolder(paramType, m), methodName);
	}
	private void initializeAfterHandler(String methodName, Method m, ParamType paramType) {
		
		if (!ComposeValidator.validate(AFTER_PARAM_SUPPORT, paramType, m)) {
			System.err.println(m.getName() + " is out of scope");
			return;
		}
		
		initializeMapping(afterMapping, new MethodHolder(paramType, m), methodName);
	}
	private void initializeCatchHandler(String methodName, Method m, ParamType paramType) {
		
		if (!ComposeValidator.validate(CATCH_PARAM_SUPPORT, paramType, m)) {
			System.err.println(m.getName() + " is out of scope");
			return;
		}
		
		initializeMapping(catchMapping, new MethodHolder(paramType, m), methodName);
	}
	
	public void processBefore(Method targetMethod, ProxyInfo target, Object[] args) 
		throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<MethodHolder> holderList = beforeMapping.get(getMethodName(targetMethod));
		
		execute(holderList, target, args, null, null);
		
	}
	
	public void processAfter(Method targetMethod, ProxyInfo target, Object[] args, Object returnValue) 
		throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<MethodHolder> holderList = afterMapping.get(getMethodName(targetMethod));
		execute(holderList, target, args, returnValue, null);
	}
	
	public void processCatch(Method targetMethod, ProxyInfo target, Object[] args, Exception ex) 
		throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<MethodHolder> holderList = catchMapping.get(getMethodName(targetMethod));
		execute(holderList, target, args, null, ex);
		
	}
	
	protected String getMethodName(Method m) {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append(m.getName()).append("(");
		
		Class[] types = m.getParameterTypes();
		
		for (int index = 0; index < types.length; index++) {
			
			if (index != 0) {
				builder.append(",");
			}
			
			builder.append(types[index].getName());
		}
		
		builder.append(")");
		
		return builder.toString();
	}
	
	private void execute(List<MethodHolder> holderList, ProxyInfo target, Object[] args, Object returnValue, Exception e) 
		throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		if (holderList == null) {
			return;
		}
		
		for (MethodHolder holder : holderList) {
			Method m = holder.getMethod();
			
			ParamType type = holder.getParamType();
			
			if (type == ParamType.NONE) {
				m.invoke(adviser);
			} else if (type == ParamType.ARGUMENTS) {
				m.invoke(adviser, new Object[] { args });
			} else if (type == ParamType.ARGUMENTS_EXCEPTION) {
				m.invoke(adviser, new Object[] { args, e });
			} else if (type == ParamType.ARGUMENTS_RETURN_VALUE) {
				m.invoke(adviser, new Object[] { args, returnValue });
			} else if (type == ParamType.TARGET) {
				m.invoke(adviser, new Object[] { target });
			} else if (type == ParamType.TARGET_ARGUMENTS) {
				m.invoke(adviser, new Object[] { target, args });
			} else if (type == ParamType.TARGET_ARGUMENTS_EXCEPTION) {
				m.invoke(adviser, new Object[] { target, args, e });
			} else if (type == ParamType.TARGET_ARGUMENTS_RETURN_VALUE) {
				m.invoke(adviser, new Object[] { target, args, returnValue });
			} else if (type == ParamType.TARGET_EXCEPTION) {
				m.invoke(adviser, new Object[] { target, e });
			} else if (type == ParamType.TARGET_RETURN_VALUE) {
				m.invoke(adviser, new Object[] { target, returnValue });
			}
		}
	}
}
