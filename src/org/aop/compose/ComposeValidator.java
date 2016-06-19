package org.aop.compose;

import java.lang.reflect.Method;

import org.aop.base.ProxyInfo;
import org.aop.base.annotation.ParamType;

public class ComposeValidator {

	public static boolean validate(ParamType[] supports, ParamType argumentType, Method method) {
		
		boolean result = false;
		
		for (ParamType support : supports) {
			if (support == argumentType) {
				result = true;
				break;
			}
		}
		
		if (!result) {
			return false;
		}
		
		int paramCount = method.getParameterCount();
		Class[] types = method.getParameterTypes();
		
		if (argumentType == ParamType.NO_ARGUMENTS) {
			if (paramCount == 0) {
				return true;
			}
		} else if (argumentType == ParamType.ARGUMENTS) {
			
			if (paramCount == 1 && method.getParameterTypes()[0] == Object[].class) {
				return true;
			}
			
		} else if (argumentType == ParamType.ARGUMENTS_EXCEPTION) {
			if (paramCount == 2 && types[0] == Object[].class && types[1] == Exception.class) {
				return true;
			}
		} else if (argumentType == ParamType.ARGUMENTS_RETURN_VALUE) {
			
			if (paramCount == 2 && types[0] == Object[].class && types[1] == Object.class) {
				return true;
			}
		} else if (argumentType == ParamType.TARGET) {
			if (paramCount == 1 && types[0] == ProxyInfo.class) {
				return true;
			}
		} else if (argumentType == ParamType.TARGET_ARGUMENTS) {
			if (paramCount == 2 && types[0] == ProxyInfo.class && types[1] == Object[].class) {
				return true;
			}
			
		} else if (argumentType == ParamType.TARGET_ARGUMENTS_EXCEPTION) {
			if (paramCount == 3 && types[0] == ProxyInfo.class && 
				types[1] == Object[].class && types[2] == Exception.class) {
				return true;
			}
		} else if (argumentType == ParamType.TARGET_ARGUMENTS_RETURN_VALUE) {
			if (paramCount == 3 && types[0] == ProxyInfo.class && 
				types[1] == Object[].class && types[2] == Object.class) {
				return true;
			}
		} else if (argumentType == ParamType.TARGET_EXCEPTION) {
			if (paramCount == 2 && types[0] == ProxyInfo.class &&
				types[1] == Exception.class) {
				return true;
			}
		} else if (argumentType == ParamType.TARGET_RETURN_VALUE) {
			
			if (paramCount == 2 && types[0] == ProxyInfo.class && types[1] == Object.class) {
				return true;
			}
			
		}
		return false;
		
	}
	
	
}
