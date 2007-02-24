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

import static org.junitext.runners.parameters.factory.DigesterParameterFactoryTestUtilities.assertParameterSetsEqual;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junitext.runners.Robot;

public class DigesterParameterFactoryMapsTest {
	private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
		+ "<tests>"
		+ "<test>"
		+ "<bean id=\"expectedRobot\" class=\"org.junitext.runners.Robot\" >"
		+ "<property name=\"name\" value=\"Daneel Olivaw\" />";

	private static final String XML_FOOTER = "</bean>" 
					+ "</test>"
					+ "</tests>";

	private DigesterParameterFactory testFactory;
	private Robot expectedRobot;
	private Robot friend;

	@Before
	public void createTestFactory() {
		testFactory = new DigesterParameterFactory();
		expectedRobot = new Robot();
		expectedRobot.setName("Daneel Olivaw");
		friend = new Robot();
		friend.setName("Johnny 5");
	}	
	
	@Test
	public void parseBeanWithMapWithStringKeyAsAttribute() throws Exception {
		// Create the "input" XML
		String inputString = XML_HEADER
				+ "<property name=\"mapOfFriends\" >"
				+ "<map>"
				+ "<entry key=\"Friend\">"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />"
				+ "</bean>" 
				+ "</entry>"
				+ "</map>" 
				+ "</property>" 
				+ XML_FOOTER;
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		Map<String, Robot> expectedMapFriends = new HashMap<String, Robot>();
		expectedMapFriends.put("Friend", friend);
		expectedRobot.setMapOfFriends(expectedMapFriends);

		List<ParameterSet> expectedParamSets = new ArrayList<ParameterSet>();
		expectedParamSets.add(new ParameterSet(null,
				new Object[] { expectedRobot }));

		// Run the test
		List<ParameterSet> actualParamSets = testFactory
				.createParameters(inputXml);

		// Verify the expected outcome
		assertParameterSetsEqual(expectedParamSets, actualParamSets);
	}
	
	@Test
	public void parseBeanWithMapWithStringKeyAsElement() throws Exception {
		// Create the "input" XML
		String inputString = XML_HEADER
				+ "<property name=\"mapOfFriends\" >"
				+ "<map>"
				+ "<entry>"
				+ "<key>"
				+ "<value>Friend</value>"
				+ "</key>"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />"
				+ "</bean>" 
				+ "</entry>"
				+ "</map>" 
				+ "</property>" 
				+ XML_FOOTER;
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		Map<String, Robot> expectedMapFriends = new HashMap<String, Robot>();
		expectedMapFriends.put("Friend", friend);
		expectedRobot.setMapOfFriends(expectedMapFriends);

		List<ParameterSet> expectedParamSets = new ArrayList<ParameterSet>();
		expectedParamSets.add(new ParameterSet(null,
				new Object[] { expectedRobot }));

		// Run the test
		List<ParameterSet> actualParamSets = testFactory
				.createParameters(inputXml);

		// Verify the expected outcome
		assertParameterSetsEqual(expectedParamSets, actualParamSets);
	}
	
	@Test
	public void parseBeanWithMapWithStringValueAsElement() throws Exception {
		// Create the "input" XML
		String inputString = XML_HEADER
				+ "<property name=\"mapWithStringKeyAndValue\" >"
				+ "<map>"
				+ "<entry key=\"Friend\">"
				+ "<value>String Value</value>"
				+ "</entry>"
				+ "</map>" 
				+ "</property>" 
				+ XML_FOOTER;
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		Map<String, String> expectedMap = new HashMap<String, String>();
		expectedMap.put("Friend", "String Value");
		expectedRobot.setMapWithStringKeyAndValue(expectedMap);

		List<ParameterSet> expectedParamSets = new ArrayList<ParameterSet>();
		expectedParamSets.add(new ParameterSet(null,
				new Object[] { expectedRobot }));

		// Run the test
		List<ParameterSet> actualParamSets = testFactory
				.createParameters(inputXml);

		// Verify the expected outcome
		assertParameterSetsEqual(expectedParamSets, actualParamSets);
	}
	
	@Test
	public void parseBeanWithMapWithStringValueAsAttribute() throws Exception {
		// Create the "input" XML
		String inputString = XML_HEADER
			+ "<property name=\"mapWithStringKeyAndValue\" >"
			+ "<map>"
			+ "<entry key=\"Friend\" value=\"String Value\" />"
			+ "</map>" 
			+ "</property>" 
			+ XML_FOOTER;
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		Map<String, String> expectedMap = new HashMap<String, String>();
		expectedMap.put("Friend", "String Value");
		expectedRobot.setMapWithStringKeyAndValue(expectedMap);

		List<ParameterSet> expectedParamSets = new ArrayList<ParameterSet>();
		expectedParamSets.add(new ParameterSet(null,
				new Object[] { expectedRobot }));

		// Run the test
		List<ParameterSet> actualParamSets = testFactory
				.createParameters(inputXml);

		// Verify the expected outcome
		assertParameterSetsEqual(expectedParamSets, actualParamSets);
	}
	
	@Test
	public void parseBeanWithMapWithBeanAsKey() throws Exception {
		// Create the "input" XML
		String inputString = XML_HEADER
				+ "<property name=\"mapWithRobotKey\" >"
				+ "<map>"
				+ "<entry>"
				+ "<key>"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Marvin the Paranoid Android\" />"
				+ "</bean>" 
				+ "</key>"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />"
				+ "</bean>" 
				+ "</entry>"
				+ "</map>" 
				+ "</property>" 
				+ XML_FOOTER;
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));
		
		Robot key = new Robot();
		key.setName("Marvin the Paranoid Android");

		Map<Robot, Robot> expectedMapFriends = new HashMap<Robot, Robot>();
		expectedMapFriends.put(key, friend);
		expectedRobot.setMapWithRobotKey(expectedMapFriends);

		List<ParameterSet> expectedParamSets = new ArrayList<ParameterSet>();
		expectedParamSets.add(new ParameterSet(null,
				new Object[] { expectedRobot }));

		// Run the test
		List<ParameterSet> actualParamSets = testFactory
				.createParameters(inputXml);

		// Verify the expected outcome
		assertParameterSetsEqual(expectedParamSets, actualParamSets);
	}	
	
/*
	@Test
	public void parseBeanWithAListOfLists() throws Exception {
		// Create the "input" XML
		String inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
				+ "<tests>"
				+ "<test>"
				+ "<bean id=\"expectedRobot\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Daneel Olivaw\" />"
				+ "<property name=\"listsOfFriends\">" + "<list>" + "<list>"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />" + "</bean>"
				+ "</list>" + "</list>" + "</property>" + "</bean>" + "</test>"
				+ "</tests>";
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

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

		List<ParameterSet> expectedParamSets = new ArrayList<ParameterSet>();
		expectedParamSets.add(new ParameterSet(null,
				new Object[] { expectedRobot }));

		// Run the test
		List<ParameterSet> actualParamSets = testFactory
				.createParameters(inputXml);

		// Verify the expected outcome
		assertParameterSetsEqual(expectedParamSets, actualParamSets);
	}

	@Test
	public void parseBeanWithAListOfListsOfLists() throws Exception {
		// Create the "input" XML
		String inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
				+ "<tests>"
				+ "<test>"
				+ "<bean id=\"expectedRobot\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Daneel Olivaw\" />"
				+ "<property name=\"threeLevelListOfFriends\">" + "<list>"
				+ "<list>" + "<list>"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />" + "</bean>"
				+ "</list>" + "</list>" + "</list>" + "</property>" + "</bean>"
				+ "</test>" + "</tests>";
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

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

		List<ParameterSet> expectedParamSets = new ArrayList<ParameterSet>();
		expectedParamSets.add(new ParameterSet(null,
				new Object[] { expectedRobot }));

		// Run the test
		List<ParameterSet> actualParamSets = testFactory
				.createParameters(inputXml);

		// Verify the expected outcome
		assertParameterSetsEqual(expectedParamSets, actualParamSets);
	}

	@Test
	public void parseBeanWithAListOfValueObjects() throws Exception {

		// NOTE: This test only tests that a list of value objects contains
		// Strings. DigesterParameterFactory does not yet take advantage of
		// Generics to infer the correct list type from the bean's property
		// (like Spring is capable of doing).

		// Create the "input" XML
		String inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
				+ "<tests><test>"
				+ "<bean id=\"expectedRobot\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Daneel Olivaw\" />"
				+ "<property name=\"mixedList\">" + "<list>"
				+ "<value>A string value</value>" + "<value>10232</value>"
				+ "<value>false</value>" + "</list></property></bean>"
				+ "</test></tests>";
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		// Create the expected parameter set
		Robot expectedRobot = new Robot();
		expectedRobot.setName("Daneel Olivaw");

		Robot friend = new Robot();
		friend.setName("Johnny 5");

		List<Object> mixedList = new ArrayList<Object>();
		mixedList.add("A string value");
		mixedList.add("10232");
		mixedList.add("false");

		expectedRobot.setMixedList(mixedList);

		List<ParameterSet> expectedParamSets = new ArrayList<ParameterSet>();
		expectedParamSets.add(new ParameterSet(null,
				new Object[] { expectedRobot }));

		// Run the test
		List<ParameterSet> actualParamSets = testFactory
				.createParameters(inputXml);

		// Verify the expected outcome
		assertParameterSetsEqual(expectedParamSets, actualParamSets);
	}

	@Test
	public void parseBeanWithAnArrayOfBeans() throws Exception {
		// Create the "input" XML
		String inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
				+ "<tests>"
				+ "<test>"
				+ "<bean id=\"expectedRobot\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Daneel Olivaw\" />"
				+ "<property name=\"arrayOfFriends\">" + "<list>"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />" + "</bean>"
				+ "</list>" + "</property>" + "</bean>" + "</test>"
				+ "</tests>";
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		// Create the expected parameter set
		Robot expectedRobot = new Robot();
		expectedRobot.setName("Daneel Olivaw");

		Robot friend = new Robot();
		friend.setName("Johnny 5");

		List<Robot> expectedFriends = new ArrayList<Robot>();
		expectedFriends.add(friend);
		Robot[] arrayOfFriends = { friend };
		expectedRobot.setArrayOfFriends(arrayOfFriends);

		List<ParameterSet> expectedParamSets = new ArrayList<ParameterSet>();
		expectedParamSets.add(new ParameterSet(null,
				new Object[] { expectedRobot }));

		// Run the test
		List<ParameterSet> actualParamSets = testFactory
				.createParameters(inputXml);

		// Verify the expected outcome
		assertParameterSetsEqual(expectedParamSets, actualParamSets);
	}

	@Test
	public void parseBeanWithAMultidimentionalArrayOfStrings() throws Exception {
		// Create the "input" XML
		String expectedStringA = "One expected string";
		String expectedStringB = "Another expected string";
		String expectedStringC = "One last expected string";
		String inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
				+ "<tests>"
				+ "<test>"
				+ "<bean id=\"expectedRobot\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Daneel Olivaw\" />"
				+ "<property name=\"multiDimensionalArrayOfStrings\">"
				+ "<list>" + "<list>" + "<value>" + expectedStringA
				+ "</value>" + "<value>" + expectedStringB + "</value>"
				+ "</list>" + "<list>" + "<value>" + expectedStringC
				+ "</value>" + "</list>" + "</list>" + "</property>"
				+ "</bean>" + "</test>" + "</tests>";
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		// Create the expected parameter set
		Robot expectedRobot = new Robot();
		expectedRobot.setName("Daneel Olivaw");

		String[][] multiDimensionalArrayOfStrings = {
				{ expectedStringA, expectedStringB }, { expectedStringC } };
		expectedRobot
				.setMultiDimensionalArrayOfStrings(multiDimensionalArrayOfStrings);

		List<ParameterSet> expectedParamSets = new ArrayList<ParameterSet>();
		expectedParamSets.add(new ParameterSet(null,
				new Object[] { expectedRobot }));

		// Run the test
		List<ParameterSet> actualParamSets = testFactory
				.createParameters(inputXml);

		// Verify the expected outcome
		assertParameterSetsEqual(expectedParamSets, actualParamSets);
	}
*/
}