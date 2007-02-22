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
package org.junitext.samples;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.internal.runners.InitializationError;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junitext.Category;
import org.junitext.Prerequisite;
import org.junitext.internal.runners.CategoryTextListener;
import org.junitext.runners.AnnotationRunner;

/**
 * Some samples for prerequisites and categories.
 */
@RunWith(AnnotationRunner.class)
public class PrerequisiteCategorySample {

	@Prerequisite(requires = "doMathTests")
	@Category("math tests")
	@Test
	public void divideByZero() {
		int zero = 0;
		int result = 8 / zero;
		result++; // avoid warning for not using result
	}

	@Prerequisite(requires = "doEqualsTests", callee = PrerequisiteCategorySample.class)
	@Category("equal tests")
	@Test
	public void testEquals() {
		assertEquals(12, 12);
		assertEquals(12L, 12L);
		assertEquals(new Long(12), new Long(12));

		assertEquals("Size", 12, 12);
		assertEquals("Capacity", 12.0, 11.99, 0.2);
	}

	@Prerequisite(requires = "doSomeTest")
	@Test
	public void testSome1() {
		assertEquals(42, 42);
	}

	@Prerequisite(requires = "doSomeTest")
	@Test
	public void testSome2() {
		assertEquals(42, 12);
	}

	public boolean doMathTests() {
		return false;
	}

	public static boolean doEqualsTests() {
		return true;
	}

	/**
	 * Callback with signature boolean doSomeTest(Description description). Will
	 * be searched first.
	 */
	public static boolean doSomeTest(Description desc) {
		return desc.getDisplayName().startsWith("testSome1");
	}

	/**
	 * Callback with signature boolean doSomeTest(String className, String
	 * methodName). Will be searched second.
	 */
	public static boolean doSomeTest(String className, String testName) {
		return "testSome1".equals(testName);
	}

	/**
	 * Callback with signature boolean doSomeTest(). Will be search as last
	 * option.
	 */
	public static boolean doSomeTest() {
		return true;
	}

	public static void main(String... args) throws InitializationError {
		JUnitCore core = new JUnitCore();
		// use for categories special listener, give some statistics
		core.addListener(new CategoryTextListener(System.out));
		core.run(PrerequisiteCategorySample.class);
	}
}