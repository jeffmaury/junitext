package org.junitext.manipulation;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.internal.runners.InitializationError;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junitext.Prerequisite;

/**
 * Tests for the {@link PrerequisiteFilter} for JUnitExt.
 */
public class PrerequisiteFilterTest {

	public static class APrereqTest {
		@Test public void valid() {}
		@Prerequisite(requires = "isTrue") @Test public void willBeRun1() {}
		@Prerequisite(requires = "isFalse") @Test public void willNotBeRun1() {}
		@Prerequisite(requires = "isTrue", callee = APrereqTestHelper.class) @Test public void willBeRunViaHelper1() {}
		@Prerequisite(requires = "isFalse", callee = APrereqTestHelper.class) @Test public void willNotBeRunViaHelper1() {}
		public boolean isTrue() { return true; }
 		public boolean isFalse() { return false; }
	}
	
	public static class APrereqTestHelper {
		public static boolean isTrue() { return true; }
 		public static boolean isFalse() { return false; }
	}

	@Ignore ("TODO: does not work. Requires access to test instance object. Clarify with Kent Beck")
	@Test public void callWithFilter() throws InitializationError {
		JUnitCore core = new JUnitCore();
		Request req = Request.aClass(APrereqTest.class);
		Result result = core.run(req.filterWith(new PrerequisiteFilter()));
		// TODO: current result: 2, which is wrong, as instance call to isFalse() fails 
		assertEquals(1+2, result.getRunCount()); 
		assertEquals(0, result.getFailureCount());
		// TODO: current result: 0, which is wrong, as filter can not raise a failure
		assertEquals(2, result.getIgnoreCount());		
	}

	public static void main(String... args) {
		JUnitCore.main(PrerequisiteFilterTest.class.getName());
	}
}
