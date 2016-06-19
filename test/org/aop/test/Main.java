package org.aop.test;

import java.util.ArrayList;
import java.util.List;

import org.aop.utils.AopUtils;

public class Main {
	
	public static void main(String args[]) {
		
		Cat cat = new Cat();
		
		Animal animal = AopUtils.attach(Animal.class, cat, ()->{
			List<Object> list = new ArrayList<>();
			list.add(new CatAdviser());
			list.add(new LoggerAdviser());
			return list;
		});
		
		animal.eat("apple");
	}
}
