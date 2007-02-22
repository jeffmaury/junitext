/*******************************************************************************
 * Copyright (C) 2006-2007 Jochen Hiller and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License - v 1.0
 * which accompanies this distribution, and is available at
 * http://junitext.sourceforge.net/licenses/junitext-license.html
 * 
 * Contributors:
 *     Jochen Hiller - initial API and implementation
 ******************************************************************************/
package org.junitext.runner;

import junit.framework.JUnit4TestAdapter;
import junit.framework.JUnit4TestAdapterCache;
import junit.framework.TestResult;

import org.junit.runner.Description;
import org.junit.runner.Runner;

/**
 * JUnit4Adapter which supports annotation handling.
 * 
 * We have to create our own runner based on an annotation request. As runner in
 * superclass is private, we have to override all methods, referring fRunner.
 * 
 * TODO: ugly, cleanup with JUnit
 */
public class AnnotationHandlingJUnit4TestAdapter extends JUnit4TestAdapter {

	Runner ourRunner;

	JUnit4TestAdapterCache ourCache;

	public AnnotationHandlingJUnit4TestAdapter(Class<?> newTestClass) {
		this(newTestClass, JUnit4TestAdapterCache.getDefault());
	}

	public AnnotationHandlingJUnit4TestAdapter(final Class<?> newTestClass,
			JUnit4TestAdapterCache cache) {
		super(newTestClass, cache);
		ourCache = cache;
		ourRunner = AnnotationHandlingRequest.aClass(newTestClass).getRunner();
	}

	public int countTestCases() {
		return ourRunner.testCount();
	}

	public void run(TestResult result) {
		ourRunner.run(ourCache.getNotifier(result, this));
	}

	public Description getDescription() {
		return ourRunner.getDescription();
	}
}
