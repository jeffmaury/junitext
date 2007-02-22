/**
 * Copyright (C) 2006-2007, Jochen Hiller.
 */
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
