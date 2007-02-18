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
package org.junitext.manipulation;

import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;
import org.junitext.internal.runners.PrerequisiteHandler;

/**
 * A filter to support prerequisites.
 */
public class PrerequisiteFilter extends Filter {

	/** The test object. */
	private Object fTest;

	@Override
	public boolean shouldRun(Description description) {
		// only when test case
		if (description.isSuite()) {
			return true;
		}
		PrerequisiteHandler handler = new PrerequisiteHandler();
		return handler.shouldRun(description, fTest);
	}

	@Override
	public String describe() {
		return "tests with prerequisites fulfilled";
	}
}
