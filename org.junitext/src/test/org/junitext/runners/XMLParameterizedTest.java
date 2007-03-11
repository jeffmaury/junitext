/*******************************************************************************
 * Copyright (C) 2006-2007 Jochen Hiller and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License - v 1.0
 * which accompanies this distribution, and is available at
 * http://junitext.sourceforge.net/licenses/junitext-license.html
 * 
 * Contributors:
 *     Jochen Hiller - initial API and implementation
 *     Jim Hurne - initial XMLParameterizedRunner API and implementation 
 ******************************************************************************/
package org.junitext.runners;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junitext.XMLParameters;
import org.junitext.runners.XMLParameterizedRunner;
import org.junitext.runners.parameters.factory.Parameter;
import org.junitext.runners.parameters.factory.ParameterFactory;
import org.junitext.runners.parameters.factory.ParameterList;

public class XMLParameterizedTest {

	static public class DummyBeanFactory implements ParameterFactory {

		/**
		 * @see org.junitext.runners.parameters.factory.ParameterFactory#createParameters(java.lang.Class,
		 *      java.io.File)
		 */
		public List<ParameterList> createParameters(Class<?> klass, File xmlFile)
				throws Exception {
			ArrayList<ParameterList> params = new ArrayList<ParameterList>();

			ArrayList<Parameter> paramList = new ArrayList<Parameter>();
			paramList.add(new Parameter(new Robot("Daneel Olivaw", 134, "X24R",
					"Han Fastolfe")));
			paramList.add(new Parameter(new Robot("Daneel Olivaw", 134, "X24R",
					"Han Fastolfe")));
			params.add(new ParameterList("Equal Robots", paramList));

			paramList = new ArrayList<Parameter>();
			paramList.add(new Parameter(new Robot("Johnny 5", 5, "SAINT",
					"Nova Laboratories")));
			paramList.add(new Parameter(new Robot("Johnny 5", 5, "SAINT",
					"Nova Laboratories")));
			params.add(new ParameterList("Equal Robots", paramList));

			paramList = new ArrayList<Parameter>();
			paramList.add(new Parameter(new Robot("Johnny 5", 5, "SAINT",
					"Nova Laboratories")));
			paramList.add(new Parameter(new Robot("Daneel Olivaw", 134, "X24R",
					"Han Fastolfe")));
			params.add(new ParameterList("Unequal Robots", paramList));

			paramList = new ArrayList<Parameter>();
			paramList.add(new Parameter(new Robot("Daneel Olivaw", 134, "X24R",
					"Han Fastolfe")));
			paramList.add(new Parameter(new Robot("Johnny 5", 5, "SAINT",
					"Nova Laboratories")));
			params.add(new ParameterList("Unequal Robots", paramList));

			return params;
		}

	}

	// This is our "mock" test class for testing the parameterized XML
	@RunWith(XMLParameterizedRunner.class)
	static public class RobotTest {
		private Robot expected;

		private Robot actual;

		@XMLParameters(value = "Robots.xml", parameterFactory = DummyBeanFactory.class)
		public RobotTest(Robot expectedRobot, Robot actualRobot) {
			this.expected = expectedRobot;
			this.actual = actualRobot;
		}

		@Test
		public void robotTest() {
			assertEquals("The expected robot does not equal the actual robot.",
					expected.toString(), actual.toString());
		}
	}

	@Test
	public void count() {
		Result result = JUnitCore.runClasses(RobotTest.class);
		assertEquals("The expected number of test runs were not performed.", 4,
				result.getRunCount());
		assertEquals("The expected number of failures did not occurr.", 2,
				result.getFailureCount());
	}

	@Test
	public void failuresNamedCorrectly() {
		Result result = JUnitCore.runClasses(RobotTest.class);
		assertEquals(String.format("robotTest[2](%s)", RobotTest.class
				.getName()), result.getFailures().get(0).getTestHeader());
	}

	@Test
	public void countBeforeRun() throws Exception {
		Runner runner = Request.aClass(RobotTest.class).getRunner();
		assertEquals(4, runner.testCount());
	}

	@Test
	public void plansNamedCorrectly() throws Exception {
		Runner runner = Request.aClass(RobotTest.class).getRunner();
		Description description = runner.getDescription();
		assertEquals("[0] Equal Robots", description.getChildren().get(0)
				.getDisplayName());
	}

	private static String fLog;

	@RunWith(XMLParameterizedRunner.class)
	static public class BeforeAndAfter {
		@BeforeClass
		public static void before() {
			fLog += "before ";
		}

		@AfterClass
		public static void after() {
			fLog += "after ";
		}

		@XMLParameters(value = "Robots.xml", parameterFactory = DummyBeanFactory.class)
		public BeforeAndAfter(Robot expectedRobot, Robot actualRobot) {
		}
	}

	@Test
	public void beforeAndAfterClassAreRun() {
		fLog = "";
		JUnitCore.runClasses(BeforeAndAfter.class);
		assertEquals("before after ", fLog);
	}

	@RunWith(XMLParameterizedRunner.class)
	static public class EmptyTest {
		@BeforeClass
		public static void before() {
			fLog += "before ";
		}

		@AfterClass
		public static void after() {
			fLog += "after ";
		}
	}

	@Test
	public void validateClassCatchesNoXMLBeanParameters() {
		Result result = JUnitCore.runClasses(EmptyTest.class);
		assertEquals(1, result.getFailureCount());
	}

	@RunWith(XMLParameterizedRunner.class)
	static public class IncorrectTest {
		@Test
		public int test() {
			return 0;
		}

		@XMLParameters(value = "Robots.xml", parameterFactory = DummyBeanFactory.class)
		public IncorrectTest(Robot expectedRobot, Robot actualRobot) {
		}
	}

	@Test
	public void failuresAddedForBadTestMethod() throws Exception {
		Result result = JUnitCore.runClasses(IncorrectTest.class);
		assertEquals(1, result.getFailureCount());
	}

	@RunWith(XMLParameterizedRunner.class)
	static public class IncorrectNumberOfParameters {

		@XMLParameters(value = "Robots.xml", parameterFactory = DummyBeanFactory.class)
		public IncorrectNumberOfParameters(Robot robot) {
		}
	}

	@Test
	public void validateClassCatchesIncorrectNumberOfParameters() {
		Result result = JUnitCore.runClasses(EmptyTest.class);
		assertEquals(1, result.getFailureCount());
	}

	@RunWith(XMLParameterizedRunner.class)
	static public class IncorrectParameterTypes {

		@XMLParameters(value = "Robots.xml", parameterFactory = DummyBeanFactory.class)
		public IncorrectParameterTypes(Robot robot, String anotherArgument) {
		}
	}

	@Test
	public void validateClassCatchesIncorrectTypeOfParameters() {
		Result result = JUnitCore.runClasses(EmptyTest.class);
		assertEquals(1, result.getFailureCount());
	}

	/**
	 * Mock parameter factory that will create a parameter list of value objects
	 * which need to be unboxed.
	 */
	static public class ValueObjectParameterFactory implements ParameterFactory {

		/**
		 * Returns a parameter list of primative values.
		 * 
		 * @see org.junitext.runners.parameters.factory.ParameterFactory#createParameters(java.lang.Class,
		 *      java.io.File)
		 */
		public List<ParameterList> createParameters(Class<?> testClass,
				File xmlFile) throws Exception {
			ArrayList<ParameterList> params = new ArrayList<ParameterList>();

			ArrayList<Parameter> dataSet = new ArrayList<Parameter>();
			dataSet.add(new Parameter(new Boolean(true)));
			dataSet.add(new Parameter(new Byte((byte) 1)));
			dataSet.add(new Parameter(new Character('a')));
			dataSet.add(new Parameter(new Short((short) 2)));
			dataSet.add(new Parameter(new Integer(3)));
			dataSet.add(new Parameter(new Long(100L)));
			dataSet.add(new Parameter(new Float(2.0f)));
			dataSet.add(new Parameter(new Double(3.14)));
			params.add(new ParameterList("Primatives", dataSet));

			return params;
		}
	}

	// This is our "mock" test class for testing un-boxing of primatives
	@RunWith(XMLParameterizedRunner.class)
	static public class ParameterizedWithPrimatives {

		private boolean booleanValue;
		private byte byteValue;
		private char charValue;
		private short shortValue;
		private int intValue;
		private long longValue;
		private float floatValue;
		private double doubleValue;

		@XMLParameters(value = "Robots.xml", parameterFactory = ValueObjectParameterFactory.class)
		public ParameterizedWithPrimatives(boolean booleanValue, byte byteValue,
				char charValue, short shortValue, int intValue, long longValue,
				float floatValue, double doubleValue) {
			
			this.booleanValue = booleanValue;
			this.byteValue = byteValue;
			this.charValue = charValue;
			this.shortValue = shortValue;
			this.intValue = intValue;
			this.longValue = longValue;
			this.floatValue = floatValue;
			this.doubleValue = doubleValue;
		}
		
		@Test
		public void allPrimativesEqualExpected() throws Exception {
			assertEquals(booleanValue, true);
			assertEquals(byteValue, (byte) 1);
			assertEquals(charValue, 'a');
			assertEquals(shortValue, (short) 2);
			assertEquals(intValue, 3);
			assertEquals(longValue, 100L);
			assertEquals(floatValue, 2.0f);
			assertEquals(doubleValue, 3.14);
		}
	}
	
	@Test
	public void unboxesPrimatives() throws Exception {
		Result result = JUnitCore.runClasses(ParameterizedWithPrimatives.class);
		assertEquals("The primative test did not run.", 1, result.getRunCount());
		List<Failure> failures = result.getFailures();
		for(Failure failure : failures) {
			fail("The primative test failed: " + failure);
		}
	}
	
	
	/**
	 * Mock parameter factory that will create a parameter list of nulls.
	 */
	static public class NullParameterFactory implements ParameterFactory {

		/**
		 * Returns a parameter list of primative values.
		 * 
		 * @see org.junitext.runners.parameters.factory.ParameterFactory#createParameters(java.lang.Class,
		 *      java.io.File)
		 */
		public List<ParameterList> createParameters(Class<?> testClass,
				File xmlFile) throws Exception {
			ArrayList<ParameterList> params = new ArrayList<ParameterList>();

			ArrayList<Parameter> dataSet = new ArrayList<Parameter>();
			dataSet.add(new Parameter(null));
			dataSet.add(new Parameter(null));
			params.add(new ParameterList("Primatives", dataSet));

			return params;
		}
	}

	// This is our "mock" test class for testing nulls
	@RunWith(XMLParameterizedRunner.class)
	static public class ParameterizedWithNull {

		private Robot nullRobot;
		private String nullString;

		@XMLParameters(value = "Robots.xml", parameterFactory = NullParameterFactory.class)
		public ParameterizedWithNull(Robot nullRobot, String nullString) {			
			this.nullRobot = nullRobot;
			this.nullString = nullString;
		}
		
		@Test
		public void allObjectsAreNull() throws Exception {
			assertNull(nullRobot);
			assertNull(nullString);
		}
	}
	
	@Test
	public void passesNullValues() throws Exception {
		Result result = JUnitCore.runClasses(ParameterizedWithNull.class);
		assertEquals("The null test did not run.", 1, result.getRunCount());
		List<Failure> failures = result.getFailures();
		for(Failure failure : failures) {
			fail("The null test failed: " + failure);
		}
	}	
	
	
	// This is our "mock" test class for testing that XMLParameterizedRunner 
	// correctly validates that null cannot be passed for a primative 
	// constructor parameter 
	@RunWith(XMLParameterizedRunner.class)
	static public class ParameterizedWithNullPrimative {
		@XMLParameters(value = "Robots.xml", parameterFactory = NullParameterFactory.class)
		public ParameterizedWithNullPrimative(Robot nullRobot, int intParam) {			
		}
	}
	
	@Test
	public void validateClassCatchesNullForPrimativeParameter() {
		Result result = JUnitCore.runClasses(ParameterizedWithNullPrimative.class);
		assertEquals(1, result.getFailureCount());
		Failure failure = result.getFailures().get(0);
		assertTrue("The description of the failure is not correct", failure.getDescription().getDisplayName().startsWith("initializationError0"));
	}
}
