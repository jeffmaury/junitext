package org.junitext;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.internal.runners.InitializationError;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junitext.runners.AnnotationRunner;

public class PrerequisiteHandlerTest {

	@RunWith(AnnotationRunner.class)
	public static class APrereqTestHandlerDescription {
		@Prerequisite(requires = "isActive") @Test public void willBeRun() {}
		public boolean isActive(Description desc) { return true; }
	}
	@RunWith(AnnotationRunner.class)
	public static class APrereqTestHandlerClassNameMethodName {
		@Prerequisite(requires = "isActive") @Test public void willBeRun() {}
		public boolean isActive(String className, String methodName) { return true; }
	}
	@RunWith(AnnotationRunner.class)
	public static class APrereqTestHandlerVoid {
		@Prerequisite(requires = "isActive") @Test public void willBeRun() {}
		public boolean isActive() { return true; }
	}
	
	@Test public void prerequisitesHandlersSimple() throws InitializationError {
		JUnitCore core = new JUnitCore();
		Result result;
		result = core.run(Request.aClass(APrereqTestHandlerDescription.class));
		assertEquals(1, result.getRunCount());
		assertEquals(0, result.getFailureCount());
		assertEquals(0, result.getIgnoreCount());		
		result = core.run(Request.aClass(APrereqTestHandlerClassNameMethodName.class));
		assertEquals(1, result.getRunCount());
		assertEquals(0, result.getFailureCount());
		assertEquals(0, result.getIgnoreCount());		
		result = core.run(Request.aClass(APrereqTestHandlerVoid.class));
		assertEquals(1, result.getRunCount());
		assertEquals(0, result.getFailureCount());
		assertEquals(0, result.getIgnoreCount());		
	}
	
	@RunWith(AnnotationRunner.class)
	public static class APrereqTestHandlerNotFound {
		@Test public void willBeRun() {}
		@Prerequisite(requires = "isActive1") @Test public void willNotBeRun1() {}
		@Prerequisite(requires = "isActive2") @Test public void willNotBeRun2() {}
		@Prerequisite(requires = "isActive3") @Test public void willNotBeRun3() {}
		@Prerequisite(requires = "isActive4") @Test public void willNotBeRun4() {}
		// wrong return types
		public Object isActive1(Description d) { return null; }
		public void isActive1(String c, String m) {}
		public String isActive1() { return ""; }
		public void isActive2() {}
		// wrong visibility
		protected boolean isActive3() { return true; }
		// wrong number of arguments
		public boolean isActive4(String c) { return true; }
		public boolean isActive4(String c, String m, String x) { return true; }
	}
	
	@Test public void prerequisitesHandlersNotFound() throws InitializationError {
		JUnitCore core = new JUnitCore();
		Result result;
		result = core.run(Request.aClass(APrereqTestHandlerNotFound.class));
		assertEquals(1, result.getRunCount());
		assertEquals(4, result.getFailureCount());
		assertEquals(0, result.getIgnoreCount());		
	}
	
	@RunWith(AnnotationRunner.class)
	public static class APrereqTestHandlerFound {
		@Test public void willBeRun() {}
		@Prerequisite(requires = "isActive1") @Test public void willNotBeRun1() {}
		@Prerequisite(requires = "isActive1") @Test public void willBeRun1() {}
		public boolean isActive1(Description d) { return d.getDisplayName().startsWith("willBeRun1"); }
	}
	
	@Test public void prerequisitesHandlersFound() throws InitializationError {
		JUnitCore core = new JUnitCore();
		Result result;
		result = core.run(Request.aClass(APrereqTestHandlerFound.class));
		assertEquals(2, result.getRunCount());
		assertEquals(0, result.getFailureCount());
		assertEquals(1, result.getIgnoreCount());		
	}
	
	public static void main(String... args) {
		JUnitCore.main(PrerequisiteHandlerTest.class.getName());
	}
}
