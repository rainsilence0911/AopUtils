# Introduction
AopUtils是由Java动态代理实现的轻量级Aop工具包。对第三方库完全没有依赖

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
AopUtils工具包只支持用Annotation来描述切面.