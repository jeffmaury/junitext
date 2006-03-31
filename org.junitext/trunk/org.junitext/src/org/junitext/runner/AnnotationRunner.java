package org.junitext.runner;

import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.TestClassRunner;
import org.junitext.internal.runners.AnnotationHandlingTestClassMethodsRunner;

/**
 * Test runner which supports annotation handling.
 */
public class AnnotationRunner extends TestClassRunner {

	public AnnotationRunner(Class<?> clazz) throws InitializationError {
		super(clazz, new AnnotationHandlingTestClassMethodsRunner(clazz));
	}
}
