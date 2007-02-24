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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junitext.runners.Robot;

public class DigesterParameterFactoryMapsTest {
	private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
			+ "<tests>"
			+ "<test>"
			+ "<bean id=\"expectedRobot\" class=\"org.junitext.runners.Robot\" >"
			+ "<property name=\"name\" value=\"Daneel Olivaw\" />";

	private static final String XML_FOOTER = "</bean></test></tests>";

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
		String inputString = XML_HEADER + "<property name=\"mapOfFriends\" >"
				+ "<map>" + "<entry key=\"Friend\">"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />" + "</bean>"
				+ "</entry>" + "</map>" + "</property>" + XML_FOOTER;
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		Map<String, Robot> expectedMapFriends = new HashMap<String, Robot>();
		expectedMapFriends.put("Friend", friend);
		expectedRobot.setMapOfFriends(expectedMapFriends);

		List<ParameterList> expectedParamSets = new ArrayList<ParameterList>();
		expectedParamSets.add(new ParameterList(null,
				new Object[] { expectedRobot }));

		// Run the test
		List<ParameterList> actualParamSets = testFactory
				.createParameters(inputXml);

		// Verify the expected outcome
		assertParameterSetsEqual(expectedParamSets, actualParamSets);
	}

	@Test
	public void parseBeanWithMapWithStringKeyAsElement() throws Exception {
		// Create the "input" XML
		String inputString = XML_HEADER + "<property name=\"mapOfFriends\" >"
				+ "<map>" + "<entry>" + "<key>" + "<value>Friend</value>"
				+ "</key>"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />" + "</bean>"
				+ "</entry>" + "</map>" + "</property>" + XML_FOOTER;
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		Map<String, Robot> expectedMapFriends = new HashMap<String, Robot>();
		expectedMapFriends.put("Friend", friend);
		expectedRobot.setMapOfFriends(expectedMapFriends);

		List<ParameterList> expectedParamSets = new ArrayList<ParameterList>();
		expectedParamSets.add(new ParameterList(null,
				new Object[] { expectedRobot }));

		// Run the test
		List<ParameterList> actualParamSets = testFactory
				.createParameters(inputXml);

		// Verify the expected outcome
		assertParameterSetsEqual(expectedParamSets, actualParamSets);
	}

	@Test
	public void parseBeanWithMapWithStringValueAsElement() throws Exception {
		// Create the "input" XML
		String inputString = XML_HEADER
				+ "<property name=\"mapWithStringKeyAndValue\" >" + "<map>"
				+ "<entry key=\"Friend\">" + "<value>String Value</value>"
				+ "</entry>" + "</map>" + "</property>" + XML_FOOTER;
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		Map<String, String> expectedMap = new HashMap<String, String>();
		expectedMap.put("Friend", "String Value");
		expectedRobot.setMapWithStringKeyAndValue(expectedMap);

		List<ParameterList> expectedParamSets = new ArrayList<ParameterList>();
		expectedParamSets.add(new ParameterList(null,
				new Object[] { expectedRobot }));

		// Run the test
		List<ParameterList> actualParamSets = testFactory
				.createParameters(inputXml);

		// Verify the expected outcome
		assertParameterSetsEqual(expectedParamSets, actualParamSets);
	}

	@Test
	public void parseBeanWithMapWithStringValueAsAttribute() throws Exception {
		// Create the "input" XML
		String inputString = XML_HEADER
				+ "<property name=\"mapWithStringKeyAndValue\" >" + "<map>"
				+ "<entry key=\"Friend\" value=\"String Value\" />" + "</map>"
				+ "</property>" + XML_FOOTER;
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		Map<String, String> expectedMap = new HashMap<String, String>();
		expectedMap.put("Friend", "String Value");
		expectedRobot.setMapWithStringKeyAndValue(expectedMap);

		List<ParameterList> expectedParamSets = new ArrayList<ParameterList>();
		expectedParamSets.add(new ParameterList(null,
				new Object[] { expectedRobot }));

		// Run the test
		List<ParameterList> actualParamSets = testFactory
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
				+ "</bean>" + "</key>"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />" + "</bean>"
				+ "</entry>" + "</map>" + "</property>" + XML_FOOTER;
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		Robot key = new Robot();
		key.setName("Marvin the Paranoid Android");

		Map<Robot, Robot> expectedMapFriends = new HashMap<Robot, Robot>();
		expectedMapFriends.put(key, friend);
		expectedRobot.setMapWithRobotKey(expectedMapFriends);

		List<ParameterList> expectedParamSets = new ArrayList<ParameterList>();
		expectedParamSets.add(new ParameterList(null,
				new Object[] { expectedRobot }));

		// Run the test
		List<ParameterList> actualParamSets = testFactory
				.createParameters(inputXml);

		// Verify the expected outcome
		assertParameterSetsEqual(expectedParamSets, actualParamSets);
	}

	@Test
	public void parseBeanWithMapOfMapValues() throws Exception {
		// Create the "input" XML
		String inputString = XML_HEADER + "<property name=\"mixedMap\">"
				+ "<map>" + "<entry key=\"key\">" + "<map>"
				+ "<entry key=\"innerKey\">"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />" + "</bean>"
				+ "</entry>" + "</map>" + "</entry>" + "</map>" + "</property>"
				+ XML_FOOTER;
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		Map<String, Robot> innerMap = new HashMap<String, Robot>();
		innerMap.put("innerKey", friend);

		Map<Object, Object> mixedMap = new HashMap<Object, Object>();
		mixedMap.put("key", innerMap);
		expectedRobot.setMixedMap(mixedMap);

		List<ParameterList> expectedParamSets = new ArrayList<ParameterList>();
		expectedParamSets.add(new ParameterList(null,
				new Object[] { expectedRobot }));

		// Run the test
		List<ParameterList> actualParamSets = testFactory
				.createParameters(inputXml);

		// Verify the expected outcome
		assertParameterSetsEqual(expectedParamSets, actualParamSets);
	}

	@Test
	public void parseBeanWithMapOfMapKeys() throws Exception {
		// Create the "input" XML
		String inputString = XML_HEADER + "<property name=\"mixedMap\">"
				+ "<map>" + "<entry>" + "<key>" + "<map>"
				+ "<entry key=\"innerKey\">"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />" + "</bean>"
				+ "</entry>" + "</map>" + "</key>" + "<value>value</value>"
				+ "</entry>" + "</map>" + "</property>" + XML_FOOTER;
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		Map<String, Robot> innerMap = new HashMap<String, Robot>();
		innerMap.put("innerKey", friend);

		Map<Object, Object> mixedMap = new HashMap<Object, Object>();
		mixedMap.put(innerMap, "value");
		expectedRobot.setMixedMap(mixedMap);

		List<ParameterList> expectedParamSets = new ArrayList<ParameterList>();
		expectedParamSets.add(new ParameterList(null,
				new Object[] { expectedRobot }));

		// Run the test
		List<ParameterList> actualParamSets = testFactory
				.createParameters(inputXml);

		// Verify the expected outcome
		assertParameterSetsEqual(expectedParamSets, actualParamSets);
	}
}