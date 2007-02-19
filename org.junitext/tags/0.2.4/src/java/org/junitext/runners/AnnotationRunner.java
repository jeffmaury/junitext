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
