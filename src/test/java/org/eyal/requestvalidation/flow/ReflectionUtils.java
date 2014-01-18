package org.eyal.requestvalidation.flow;

import java.lang.reflect.Field;

public final class ReflectionUtils {
	@SuppressWarnings("unchecked")
	public static <T> T realObjectFromField(Class<?> clazz, String fieldName, Object object) {
		Field declaredField = accessibleField(clazz, fieldName);
		try {
			return (T) declaredField.get(object);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static Field accessibleField(Class<?> clazz, String fieldName) {
		try {
			Field declaredField = clazz.getDeclaredField(fieldName);
			declaredField.setAccessible(true);
			return declaredField;
		} catch (NoSuchFieldException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}
}
