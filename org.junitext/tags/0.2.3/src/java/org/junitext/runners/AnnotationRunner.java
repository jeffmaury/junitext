/**
 * Copyright (C) 2006-2007, Jochen Hiller.
 */
package org.junitext.runners;

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
