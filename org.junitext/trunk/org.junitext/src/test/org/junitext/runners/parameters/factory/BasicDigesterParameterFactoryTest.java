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
package org.junitext.runners.parameters.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junitext.runners.Robot;

public class BasicDigesterParameterFactoryTest {

	private DigesterParameterFactory testFactory;
	
	@Before
	public void createTestFactory() {
		testFactory = new DigesterParameterFactory();
	}
	
	@Test
	public void parseString() throws Exception {
		String expectedString = "This is the expected string value.";
		String inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
			 + "<tests>"
			 + "<test>"
			 + "<string id=\"testString\" value=\""+ expectedString + "\" />"
			 + "</test>"
			 + "</tests>";
		InputStream inputXml = new ByteArrayInputStream(inputString.getBytes("UTF-8"));
		
		//Run the test
		List<Object[]> parmSets = testFactory.createParameters(inputXml);
		
		//Verify the correct object structure was created
		// Verify that the correct object structure was created.
		assertEquals("The correct number of paramter sets were not created.",
				1, parmSets.size());

		Object[] parmSet = parmSets.get(0);
		assertEquals("The correct number of objects were not created.", 1,
				parmSet.length);

		assertTrue("The first object is not a String.",
				parmSet[0] instanceof String);

		// Verify the parameter
		assertEquals("The String does not equal the expected String.",
				expectedString, parmSet[0]);		
	}
	
	@Test
	public void parseInteger() throws Exception {
		Integer expectedInteger = 5;
		String inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
			 + "<tests>"
			 + "<test>"
			 + "<int id=\"testInteger\" value=\""+ expectedInteger + "\" />"
			 + "</test>"
			 + "</tests>";
		InputStream inputXml = new ByteArrayInputStream(inputString.getBytes("UTF-8"));
		
		//Run the test
		List<Object[]> parmSets = testFactory.createParameters(inputXml);
		
		//Verify the correct object structure was created
		// Verify that the correct object structure was created.
		assertEquals("The correct number of paramter sets were not created.",
				1, parmSets.size());

		Object[] parmSet = parmSets.get(0);
		assertEquals("The correct number of objects were not created.", 1,
				parmSet.length);

		assertTrue("The first object is not an Integer.",
				parmSet[0] instanceof Integer);

		// Verify the parameter
		assertEquals("The Integer does not equal the expected Integer.",
				expectedInteger, parmSet[0]);		
	}
	
	@Test
	public void parseShort() throws Exception {
		Short expectedShort = 5;
		String inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
			 + "<tests>"
			 + "<test>"
			 + "<short id=\"testShort\" value=\""+ expectedShort + "\" />"
			 + "</test>"
			 + "</tests>";
		InputStream inputXml = new ByteArrayInputStream(inputString.getBytes("UTF-8"));
		
		//Run the test
		List<Object[]> parmSets = testFactory.createParameters(inputXml);
		
		//Verify the correct object structure was created
		// Verify that the correct object structure was created.
		assertEquals("The correct number of paramter sets were not created.",
				1, parmSets.size());

		Object[] parmSet = parmSets.get(0);
		assertEquals("The correct number of objects were not created.", 1,
				parmSet.length);

		assertTrue("The first object is not a Short.",
				parmSet[0] instanceof Short);

		// Verify the parameter
		assertEquals("The Short does not equal the expected Short.",
				expectedShort, parmSet[0]);		
	}
	
	@Test
	public void parseLong() throws Exception {
		Long expectedLong = 5L;
		String inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
			 + "<tests>"
			 + "<test>"
			 + "<long id=\"testLong\" value=\""+ expectedLong + "\" />"
			 + "</test>"
			 + "</tests>";
		InputStream inputXml = new ByteArrayInputStream(inputString.getBytes("UTF-8"));
		
		//Run the test
		List<Object[]> parmSets = testFactory.createParameters(inputXml);
		
		//Verify the correct object structure was created
		// Verify that the correct object structure was created.
		assertEquals("The correct number of paramter sets were not created.",
				1, parmSets.size());

		Object[] parmSet = parmSets.get(0);
		assertEquals("The correct number of objects were not created.", 1,
				parmSet.length);

		assertTrue("The first object is not a Long.",
				parmSet[0] instanceof Long);

		// Verify the parameter
		assertEquals("The Long does not equal the expected Long.",
				expectedLong, parmSet[0]);		
	}	
	
	@Test
	public void parseBoolean() throws Exception {
		Boolean expectedBoolean = false;
		String inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
			 + "<tests>"
			 + "<test>"
			 + "<boolean id=\"testString\" value=\""+ expectedBoolean + "\" />"
			 + "</test>"
			 + "</tests>";
		InputStream inputXml = new ByteArrayInputStream(inputString.getBytes("UTF-8"));
		
		//Run the test
		List<Object[]> parmSets = testFactory.createParameters(inputXml);
		
		//Verify the correct object structure was created
		// Verify that the correct object structure was created.
		assertEquals("The correct number of paramter sets were not created.",
				1, parmSets.size());

		Object[] parmSet = parmSets.get(0);
		assertEquals("The correct number of objects were not created.", 1,
				parmSet.length);

		assertTrue("The first object is not a Boolean.",
				parmSet[0] instanceof Boolean);

		// Verify the parameter
		assertEquals("The Boolean does not equal the expected Boolean.",
				expectedBoolean, parmSet[0]);		
	}	
	
		
	@Test
	public void parseBeanWithList() throws Exception {
		//Create the "input" XML
		String inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
				+ "<tests>"
				+ "<test>"
				+ "<bean id=\"expectedRobot\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Daneel Olivaw\" />"
				+ "<property name=\"id\" value=\"134\" />"
				+ "<property name=\"model\" value=\"X24R\" />"
				+ "<property name=\"manufacturer\" value=\"Han Fastolfe\" />"
				+ "<property name=\"friends\">"
				+ "<list>"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />"
				+ "<property name=\"id\" value=\"5\" />"
				+ "<property name=\"model\" value=\"SAINT\" />"
				+ "<property name=\"manufacturer\" value=\"Nova Laboratories\" />"
				+ "</bean>" 
				+ "</list>" 
				+ "</property>" 
				+ "</bean>" 
				+ "</test>"
				+ "</tests>";
		InputStream inputXml = new ByteArrayInputStream(inputString.getBytes("UTF-8"));
		
		// Create the expected parameter set
		Robot expectedRobot = new Robot();
		expectedRobot.setName("Daneel Olivaw");
		expectedRobot.setId(134);
		expectedRobot.setManufacturer("Han Fastolfe");
		expectedRobot.setModel("X24R");

		Robot friend = new Robot();
		friend.setName("Johnny 5");
		friend.setId(5);
		friend.setManufacturer("Nova Laboratories");
		friend.setModel("SAINT");

		List<Robot> expectedFriends = new ArrayList<Robot>();
		expectedFriends.add(friend);
		expectedRobot.setFriends(expectedFriends);

		List<Object[]> parmSets = testFactory.createParameters(inputXml);

		// Verify that the correct object structure was created.
		assertEquals("The correct number of paramter sets were not created.",
				1, parmSets.size());

		Object[] parmSet = parmSets.get(0);
		assertEquals("The correct number of beans were not created.", 1,
				parmSet.length);

		assertTrue("The first bean is not a Robot.",
				parmSet[0] instanceof Robot);

		// Verify the robot
		assertEquals("The robot does not equal the expected robot.",
				expectedRobot.toString(), parmSet[0].toString());
	}
	
	@Test
	public void parseBeanWithAListOfLists() throws Exception {
		//Create the "input" XML
		String inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
				+ "<tests>"
				+ "<test>"
				+ "<bean id=\"expectedRobot\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Daneel Olivaw\" />"
				+ "<property name=\"listsOfFriends\">"
				+ "<list>"
				+ "<list>"				
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />"
				+ "</bean>"
				+ "</list>"				
				+ "</list>" 
				+ "</property>" 
				+ "</bean>" 
				+ "</test>"
				+ "</tests>";
		InputStream inputXml = new ByteArrayInputStream(inputString.getBytes("UTF-8"));
		
		// Create the expected parameter set
		Robot expectedRobot = new Robot();
		expectedRobot.setName("Daneel Olivaw");

		Robot friend = new Robot();
		friend.setName("Johnny 5");

		List<Robot> expectedFriends = new ArrayList<Robot>();
		expectedFriends.add(friend);
		List<List<Robot>> listOfFriends = new ArrayList<List<Robot>>();
		listOfFriends.add(expectedFriends);
		expectedRobot.setListsOfFriends(listOfFriends);

		List<Object[]> parmSets = testFactory.createParameters(inputXml);

		// Verify that the correct object structure was created.
		assertEquals("The correct number of paramter sets were not created.",
				1, parmSets.size());

		Object[] parmSet = parmSets.get(0);
		assertEquals("The correct number of beans were not created.", 1,
				parmSet.length);

		assertTrue("The first bean is not a Robot.",
				parmSet[0] instanceof Robot);

		// Verify the robot
		assertEquals("The robot does not equal the expected robot.",
				expectedRobot.toString(), parmSet[0].toString());
	}
	
	
	@Test
	public void parseBeanWithAListOfListsOfLists() throws Exception {
		//Create the "input" XML
		String inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
				+ "<tests>"
				+ "<test>"
				+ "<bean id=\"expectedRobot\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Daneel Olivaw\" />"
				+ "<property name=\"threeLevelListOfFriends\">"
				+ "<list>"
				+ "<list>"
				+ "<list>"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />"
				+ "</bean>"
				+ "</list>"				
				+ "</list>"				
				+ "</list>" 
				+ "</property>" 
				+ "</bean>" 
				+ "</test>"
				+ "</tests>";
		InputStream inputXml = new ByteArrayInputStream(inputString.getBytes("UTF-8"));
		
		// Create the expected parameter set
		Robot expectedRobot = new Robot();
		expectedRobot.setName("Daneel Olivaw");

		Robot friend = new Robot();
		friend.setName("Johnny 5");

		List<Robot> expectedFriends = new ArrayList<Robot>();
		expectedFriends.add(friend);
		List<List<Robot>> listOfFriends = new ArrayList<List<Robot>>();
		listOfFriends.add(expectedFriends);
		
		List<List<List<Robot>>> thirdLevelList = new ArrayList<List<List<Robot>>>();
		thirdLevelList.add(listOfFriends);
		
		expectedRobot.setThreeLevelListOfFriends(thirdLevelList);

		List<Object[]> parmSets = testFactory.createParameters(inputXml);

		// Verify that the correct object structure was created.
		assertEquals("The correct number of paramter sets were not created.",
				1, parmSets.size());

		Object[] parmSet = parmSets.get(0);
		assertEquals("The correct number of beans were not created.", 1,
				parmSet.length);

		assertTrue("The first bean is not a Robot.",
				parmSet[0] instanceof Robot);

		// Verify the robot
		assertEquals("The robot does not equal the expected robot.",
				expectedRobot.toString(), parmSet[0].toString());
	}
	
	
}
