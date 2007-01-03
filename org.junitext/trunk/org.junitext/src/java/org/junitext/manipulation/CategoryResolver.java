/**
 * Copyright (C) 2006-2007, Jochen Hiller.
 */
package org.junitext.manipulation;

import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.junit.runner.Description;
import org.junitext.Category;

/**
 * Helper class to resolve a Category based on test descriptions.
 */
public class CategoryResolver {

	/**
	 * Gets the category for a given description.
	 * 
	 * @param desc
	 *            a test case description
	 * @return a category or null
	 */
	public static Category getCategory(Description desc) {
		try {
			// ugly code: parse from string representation :(
			String name = desc.getDisplayName();
			StringTokenizer tokenizer = new StringTokenizer(name, "(");
			String methodName = tokenizer.nextToken();
			String className = tokenizer.nextToken(")").substring(1);
			Class<?> clazz = Class.forName(className);
			Method m = clazz.getMethod(methodName);
			// may be null
			return m.getAnnotation(Category.class);
		} catch (NoSuchElementException e) {
			// should never happen
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// should never happen
			e.printStackTrace();
		} catch (SecurityException e) {
			// should never happen
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// should never happen
			e.printStackTrace();
		}
		// in all error cases: no category for description available
		return null;
	}
}