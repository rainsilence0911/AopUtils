package org.aop.base;

import java.util.List;

@FunctionalInterface
public interface AdviserFactory {
	List<?> create();
}
