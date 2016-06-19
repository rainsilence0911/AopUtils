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
