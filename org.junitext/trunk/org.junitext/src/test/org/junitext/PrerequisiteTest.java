package org.junitext;

import static org.junit.Assert.assertEquals;
import junit.framework.JUnit4TestAdapter;
import junit.framework.TestResult;

import org.junit.Test;
import org.junit.internal.runners.InitializationError;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junitext.Prerequisite;
import org.junitext.runner.AnnotationHandlingJUnit4TestAdapter;
import org.junitext.runner.AnnotationHandlingRequest;
import org.junitext.runners.AnnotationRunner;

/**
 * Tests for the {@link Prerequisite} annotation for JUnit.
 */
public class PrerequisiteTest {

	public static class APrereqTest {
		@Test public void valid() {}
		@Prerequisite(requires = "isTrue") @Test public void willBeRun1() {}
		@Prerequisite(requires = "isFalse") @Test public void willNotBeRun1() {}
		@Prerequisite(requires = "isTRUE") @Test public void willBeRun2() {}
		@Prerequisite(requires = "isFALSE") @Test public void willNotBeRun2() {}
		@Prerequisite(requires = "isTrue", callee = APrereqTestHelper.class) @Test public void willBeRunViaHelper1() {}
		@Prerequisite(requires = "isFalse", callee = APrereqTestHelper.class) @Test public void willNotBeRunViaHelper1() {}
		@Prerequisite(requires = "isTRUE", callee = APrereqTestHelper.class) @Test public void willBeRunViaHelper2() {}
		@Prerequisite(requires = "isFALSE", callee = APrereqTestHelper.class) @Test public void willNotBeRunViaHelper2() {}
		public boolean isTrue() { return true; }
 		public boolean isFalse() { return false; }
 		public Boolean isTRUE() { return Boolean.TRUE; }
 		public Boolean isFALSE() { return Boolean.FALSE; }
	}

	public static class APrereqTestHelper {
		public static boolean isTrue() { return true; }
 		public static boolean isFalse() { return false; }
 		public static Boolean isTRUE() { return Boolean.TRUE; }
 		public static Boolean isFALSE() { return Boolean.FALSE; }
	}

	@Test public void prerequsitesRunner() {
		JUnitCore core = new JUnitCore();
		Request req = AnnotationHandlingRequest.aClass(APrereqTest.class);
		Result result = core.run(req);
		assertEquals(1+4, result.getRunCount());
		assertEquals(0, result.getFailureCount());
		assertEquals(4, result.getIgnoreCount());
	}

	@Test public void prerequsitesRunnerCompatibility() {
		TestResult result = new TestResult();
		JUnit4TestAdapter adapter = new AnnotationHandlingJUnit4TestAdapter(APrereqTest.class);
		adapter.run(result);
		assertEquals(1+4, result.runCount());
		assertEquals(0, result.errorCount());
		assertEquals(0, result.failureCount());
	}

	public static class APrereqInternalFailTest {
		@Prerequisite(requires = "isNotAccessible")	@Test public void willNotBeRun1() {}
		@Prerequisite(requires = "hasArguments") @Test public void willNotBeRun2() {}
		@Prerequisite(requires = "wrongReturnValue") @Test public void willNotBeRun3() {}
		@Prerequisite(requires = "isNotAccessible", callee = APrereqFailHelper.class) @Test public void willNotBeRunViaHelper1() {}
		@Prerequisite(requires = "hasArguments", callee = APrereqFailHelper.class) @Test public void willNotBeRunViaHelper2() {}
		@Prerequisite(requires = "wrongReturnValue", callee = APrereqFailHelper.class) @Test public void willNotBeRunViaHelper3() {}
		protected boolean isNotAccessible() { return true; }
		public boolean hasArguments(Object o) { return true; }
		public Object wrongReturnValue() { return new Object(); }
	}

	public static class APrereqFailHelper {
		protected static boolean isNotAccessible() { return true; }
		public static boolean hasArguments(Object o) { return true; }
		public static Object wrongReturnValue() { return new Object(); }
	}

	@Test public void prerequsitesRunnerInternalFail() {
		JUnitCore core = new JUnitCore();
		Request req = AnnotationHandlingRequest.aClass(APrereqInternalFailTest.class);
		Result result = core.run(req);
		assertEquals(0, result.getRunCount());
		assertEquals(6, result.getFailureCount());
		assertEquals(0, result.getIgnoreCount());
	}

	@Test public void prerequsitesRunnerInternalFailCompatibility() {
		TestResult result = new TestResult();
		JUnit4TestAdapter adapter = new AnnotationHandlingJUnit4TestAdapter(APrereqInternalFailTest.class);
		adapter.run(result);
		assertEquals(0, result.runCount());
		assertEquals(6, result.errorCount());
		// internal errors are reported as failures
		assertEquals(0, result.failureCount());
	}

	public static class APrereqTestWithoutRunWith {
		@Test public void valid() {}
		@Prerequisite(requires = "isTrue") @Test public void willBeRun1() {}
		@Prerequisite(requires = "isFalse") @Test public void willNotBeRun1() {}
		public boolean isTrue() { return true; }
 		public boolean isFalse() { return false; }
	}
	
	@Test public void prerequisitesRunWithoutRunWith() throws InitializationError {
		JUnitCore core = new JUnitCore();
		Request req = AnnotationHandlingRequest.aClass(APrereqTestWithoutRunWith.class);
		Result result = core.run(req);
		assertEquals(1+1, result.getRunCount());
		assertEquals(0, result.getFailureCount());
		assertEquals(1, result.getIgnoreCount());		
	}

	@RunWith(AnnotationRunner.class)
	public static class APrereqTestWithRunWith {
		@Test public void valid() {}
		@Prerequisite(requires = "isTrue") @Test public void willBeRun1() {}
		@Prerequisite(requires = "isFalse") @Test public void willNotBeRun1() {}
		public boolean isTrue() { return true; }
 		public boolean isFalse() { return false; }
	}
	
	@Test public void prerequisitesRunWithRunWith() throws InitializationError {
		JUnitCore core = new JUnitCore();
		Request req = Request.aClass(APrereqTestWithRunWith.class);
		Result result = core.run(req);
		assertEquals(1+1, result.getRunCount());
		assertEquals(0, result.getFailureCount());
		assertEquals(1, result.getIgnoreCount());		
	}

	public static void main(String... args) {
		JUnitCore.main(PrerequisiteTest.class.getName());
	}
}
