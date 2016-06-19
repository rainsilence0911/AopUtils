package org.aop.test;


public class Cat implements Animal {
	
	public void eat() {
		System.out.println("eating fish");
	}
	
	public void eat(String food) {
		System.out.println("-----cat is eating " + food);
		//throw new RuntimeException("give me more food");
	}

}
