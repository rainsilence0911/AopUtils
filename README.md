# Introduction
AopUtils是由Java动态代理实现的轻量级Aop工具包。对第三方库完全没有依赖


## 包的依赖关系
AopUtils非常注重代码的质量，为了方便的整合到OSGi中，包与包之间不存在循环依赖。包的层次结构如下


```

// 下层依赖上层
// base中提供了供用户使用的所有接口和enum常量
org.aop.base

// compose中提供了实现
org.aop.compose

// utils包则是对外接口
org.aop.utils

// 如在OSGi中使用则需要暴露base包和utils包

```

## Use case

```
Cat cat = new Cat();
Animal animal = AopUtils.attach(Animal.class, cat, ()->{
	// 这里是切面adviser的工厂方法
	List<Object> list = new ArrayList<>();
	list.add(new CatAdviser());
	list.add(new LoggerAdviser());
	return list;
});

// 执行CatAdviser中 type=BEFORE的方法
// 执行LoggerAdviser type=BEFORE的方法
// 执行animal.eat
// 执行LoggerAdviser type=AFTER的方法
// 执行CatAdviser中 type=AFTER的方法
animal.eat("apple");

```

### Interface

```
@param targetInterface 代理对象的接口，会作为返回类型
@param targetInstance 代理对象实例
@param adviserFactory 切面拦截器（函数式接口）
AopUtils.attach(Class<T> targetInterface, Object targetInstance, AdviserFactory adviserFactory)

@param targetInterface 代理对象的接口，会作为返回类型
@param targetInstance 代理对象实例
@param adviserFactory 切面拦截器列表
AopUtils.attach(Class<T> targetInterface, Object targetInstance, List<?> adviserList)

```

### How to define a adviser?
AopUtils工具包全面拥抱函数式编程的理念。只需要用Annotation(org.aop.base.annotation.Adviser)来描述切面,且用来描述切面的只能是方法（Method）
让我们来看下上面例子中提到的LoggerAdviser的源码
```
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
```

		其中Adviser有三个属性
		1.methodName: 用来描述代理对象的方法名称，支持用正则
		2.type： 切面类型。这里支持三种类型
		         BEFORE： 会在目标方法执行前顺序执行
		         AFTER：会在目标方法执行后倒序执行
		         EXCEPTION：会在目标方法发生Exception后顺序执行
		3.param：这个参数用来描述切面方法的param个数以及类型，AopUtils支持十种切面接口
```
		package org.aop.base.annotation;

		public enum ParamType {

			/**
			 * void method()
			 */
			NONE,
			
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

```


