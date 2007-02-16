package org.junitext.runners;

import static org.junit.Assert.assertEquals;

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
import org.junitext.runners.XMLBeanParameterizedRunner;
import org.junitext.runners.parameters.factory.ParameterFactory;

public class XMLBeanParameterizedTestTest {
	
	static public class DummyBeanFactory implements ParameterFactory {

		public List<Object[]> createParameters(Class<?> klass, File xmlFile) throws Exception {		
			ArrayList<Object[]> data = new ArrayList<Object[]>();
			
			ArrayList<Robot> dataSet = new ArrayList<Robot>();
			dataSet.add(new Robot("Daniel", 134, "X24R", "Asimov"));
			dataSet.add(new Robot("Daniel", 134, "X24R", "Asimov"));
			data.add(dataSet.toArray());
			
			dataSet = new ArrayList<Robot>();
			dataSet.add(new Robot("Jonny Five", 134, "SDG293", "Hollywood"));
			dataSet.add(new Robot("Jonny Five", 134, "SDG293", "Hollywood"));
			data.add(dataSet.toArray());
					
			dataSet = new ArrayList<Robot>();
			dataSet.add(new Robot("Jonny Five", 134, "SDG293", "Hollywood"));
			dataSet.add(new Robot("Daniel", 134, "X25R", "Asimov"));
			data.add(dataSet.toArray());

			dataSet = new ArrayList<Robot>();
			dataSet.add(new Robot("Daniel", 134, "X25R", "Asimov"));
			dataSet.add(new Robot("Jonny Five", 134, "SDG293", "Hollywood"));
			data.add(dataSet.toArray());
			
			return data;
		}
		
	}
	
	//This is our "mock" test class for testing the parameterized XML
	@RunWith(XMLBeanParameterizedRunner.class)
	static public class RobotTest {
		private Robot expected;
		private Robot actual;
		
		@XMLBeanParameters(value="Robots.xml", beanFactory=DummyBeanFactory.class)
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
		assertEquals("The expected number of test runs were not performed.", 4, result.getRunCount());
		assertEquals("The expected number of failures did not occurr.", 2, result.getFailureCount());
	}
	
	@Test
	public void failuresNamedCorrectly() {
		Result result= JUnitCore.runClasses(RobotTest.class);
		assertEquals(
				String.format("robotTest[2](%s)", RobotTest.class.getName()),
				result.getFailures().get(0).getTestHeader());
	}

	@Test
	public void countBeforeRun() throws Exception {
		Runner runner= Request.aClass(RobotTest.class).getRunner();
		assertEquals(4, runner.testCount());
	}

	@Test
	public void plansNamedCorrectly() throws Exception {
		Runner runner= Request.aClass(RobotTest.class).getRunner();
		Description description= runner.getDescription();
		assertEquals("[0]", description.getChildren().get(0).getDisplayName());
	}

	private static String fLog;

	@RunWith(XMLBeanParameterizedRunner.class)
	static public class BeforeAndAfter {
		@BeforeClass
		public static void before() {
			fLog+= "before ";
		}
		@AfterClass
		public static void after() {
			fLog+= "after ";
		}
		
		@XMLBeanParameters(value="Robots.xml", beanFactory=DummyBeanFactory.class)
		public BeforeAndAfter(Robot expectedRobot, Robot actualRobot) {
		}		
	}

	@Test
	public void beforeAndAfterClassAreRun() {
		fLog= "";
		JUnitCore.runClasses(BeforeAndAfter.class);
		assertEquals("before after ", fLog);
	}

	@RunWith(XMLBeanParameterizedRunner.class)
	static public class EmptyTest {
		@BeforeClass
		public static void before() {
			fLog+= "before ";
		}

		@AfterClass
		public static void after() {
			fLog+= "after ";
		}
	}

	@Test
	public void validateClassCatchesNoXMLBeanParameters() {
		Result result= JUnitCore.runClasses(EmptyTest.class);
		assertEquals(1, result.getFailureCount());
	}

	@RunWith(XMLBeanParameterizedRunner.class)
	static public class IncorrectTest {
		@Test
		public int test() {
			return 0;
		}

		@XMLBeanParameters(value="Robots.xml", beanFactory=DummyBeanFactory.class)
		public IncorrectTest(Robot expectedRobot, Robot actualRobot) {
		}
	}

	@Test
	public void failuresAddedForBadTestMethod() throws Exception {
		Result result= JUnitCore.runClasses(IncorrectTest.class);
		assertEquals(1, result.getFailureCount());
	}
	
	@RunWith(XMLBeanParameterizedRunner.class)
	static public class IncorrectNumberOfParameters {
		
		@XMLBeanParameters(value="Robots.xml", beanFactory=DummyBeanFactory.class)
		public IncorrectNumberOfParameters(Robot robot) {	
		}
	}
	
	@Test
	public void validateClassCatchesIncorrectNumberOfParameters() {
		Result result= JUnitCore.runClasses(EmptyTest.class);
		assertEquals(1, result.getFailureCount());
	}
	
	@RunWith(XMLBeanParameterizedRunner.class)
	static public class IncorrectParameterTypes {
		
		@XMLBeanParameters(value="Robots.xml", beanFactory=DummyBeanFactory.class)
		public IncorrectParameterTypes(Robot robot, String anotherArgument) {	
		}
	}
	
	@Test
	public void validateClassCatchesIncorrectTypeOfParameters() {
		Result result= JUnitCore.runClasses(EmptyTest.class);
		assertEquals(1, result.getFailureCount());
	}	
}
