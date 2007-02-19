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
package org.junitext.internal.runners;

import java.lang.reflect.Method;

import org.junit.internal.runners.TestClassMethodsRunner;
import org.junit.internal.runners.TestMethodRunner;
import org.junit.runner.notification.RunNotifier;
import org.junitext.Prerequisite;

/**
 * Internal runner for calling test methods.
 */
public class AnnotationHandlingTestClassMethodsRunner extends
		TestClassMethodsRunner {

	private Method fMethod;

	private Object fTest;

	private RunNotifier fNotifier;

	public AnnotationHandlingTestClassMethodsRunner(Class<?> clazz) {
		super(clazz);
	}

	@Override
	protected TestMethodRunner createMethodRunner(Object test, Method method,
			RunNotifier notifier) {
		fMethod = method;
		fTest = test;
		fNotifier = notifier;
		return new TestMethodRunner(test, method, notifier,
				methodDescription(method)) {
			@Override
			public void run() {
				// check for Prerequisite annotation
				Prerequisite prereq = fMethod.getAnnotation(Prerequisite.class);
				if (prereq != null) {
					boolean fulfilled = new PrerequisiteHandler()
							.handleAnnotation(methodDescription(fMethod),
									fTest, prereq, fNotifier);
					if (!fulfilled) {
						return;
					}
				}
				super.run();
			}
		};
	}
}