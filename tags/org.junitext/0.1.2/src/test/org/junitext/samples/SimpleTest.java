package org.junitext.samples;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.internal.runners.InitializationError;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junitext.Category;
import org.junitext.Prerequisite;
import org.junitext.internal.runners.CategoryTextListener;
import org.junitext.runners.AnnotationRunner;

/**
 * Some simple tests for prerequisites and categories.
 */
@RunWith(AnnotationRunner.class)
public class SimpleTest {

	@Prerequisite(requires = "doMathTests")
	@Category("math tests")
	@Test
	public void divideByZero() {
		int zero = 0;
		int result = 8 / zero;
		result++; // avoid warning for not using result
	}

	@Prerequisite(requires = "doEqualsTests", callee = SimpleTest.class)
	@Category("equal tests")
	@Test
	public void testEquals() {
		assertEquals(12, 12);
		assertEquals(12L, 12L);
		assertEquals(new Long(12), new Long(12));

		assertEquals("Size", 12, 12);
		assertEquals("Capacity", 12.0, 11.99, 0.2);
	}

	public boolean doMathTests() {
		return false;
	}

	public static boolean doEqualsTests() {
		return true;
	}

	public static void main(String... args) throws InitializationError {
		JUnitCore core = new JUnitCore();
		// use for categories special listener, give some statistics
		core.addListener(new CategoryTextListener(System.out));
		core.run(SimpleTest.class);
	}
}