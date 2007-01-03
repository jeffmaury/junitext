/**
 * Copyright (C) 2006-2007, Jochen Hiller.
 */
package org.junitext.runner;

import org.junit.internal.runners.InitializationError;
import org.junit.runner.Request;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junitext.runners.AnnotationRunner;

/**
 * Request which supports annotation handling of test cases.
 */
public class AnnotationHandlingRequest {

	public static Request aClass(final Class<?> clazz) {
		return new Request() {

			@Override
			public Runner getRunner() {
				try {
					return new AnnotationRunner(clazz);
				} catch (InitializationError e) {
					return Request.errorReport(Filter.class, new Exception(String
							.format("Error creating AnnotationHandler for class %s ", clazz.getName()
									))).getRunner();
				}
			}
		};
	}
}
