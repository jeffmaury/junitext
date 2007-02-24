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

public class DigesterParameterFactoryMixedCollectionsTest {
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
	public void parseBeanWithListOfSets() throws Exception {
		// Create the "input" XML
		String inputString = XML_HEADER + "<property name=\"mixedList\">"
				+ "<list>" + "<set>"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />" + "</bean>"
				+ "</set>" + "</list>" + "</property>" + XML_FOOTER;
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		Set<Robot> expectedSet = new HashSet<Robot>();
		expectedSet.add(friend);

		List<Object> mixedList = new ArrayList<Object>();
		mixedList.add(expectedSet);

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
	public void parseBeanWithListOfMaps() throws Exception {
		// Create the "input" XML
		String inputString = XML_HEADER + "<property name=\"mixedList\">"
				+ "<list>" + "<map>" + "<entry key=\"Friend\">"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />" + "</bean>"
				+ "</entry>" + "</map>" + "</list>" + "</property>"
				+ XML_FOOTER;
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		Map<String, Robot> expectedMap = new HashMap<String, Robot>();
		expectedMap.put("Friend", friend);

		List<Object> mixedList = new ArrayList<Object>();
		mixedList.add(expectedMap);

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
	public void parseBeanWithSetOfLists() throws Exception {
		// Create the "input" XML
		String inputString = XML_HEADER + "<property name=\"mixedSet\">"
				+ "<set>" + "<list>"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />" + "</bean>"
				+ "</list>" + "</set>" + "</property>" + XML_FOOTER;
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		List<Robot> expectedList = new ArrayList<Robot>();
		expectedList.add(friend);

		Set<Object> mixedSet = new HashSet<Object>();
		mixedSet.add(expectedList);

		expectedRobot.setMixedSet(mixedSet);

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
	public void parseBeanWithSetOfMaps() throws Exception {
		// Create the "input" XML
		String inputString = XML_HEADER + "<property name=\"mixedSet\">"
				+ "<set>" + "<map>" + "<entry key=\"Friend\">"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />" + "</bean>"
				+ "</entry>" + "</map>" + "</set>" + "</property>" + XML_FOOTER;
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		Map<String, Robot> expectedMap = new HashMap<String, Robot>();
		expectedMap.put("Friend", friend);

		Set<Object> mixedSet = new HashSet<Object>();
		mixedSet.add(expectedMap);

		expectedRobot.setMixedSet(mixedSet);

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
	public void parseBeanWithMapOfListValues() throws Exception {
		// Create the "input" XML
		String inputString = XML_HEADER + "<property name=\"mixedMap\">"
				+ "<map>" + "<entry key=\"key\">" + "<list>"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />" + "</bean>"
				+ "</list>" + "</entry>" + "</map>" + "</property>"
				+ XML_FOOTER;
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		List<Robot> expected = new ArrayList<Robot>();
		expected.add(friend);

		Map<Object, Object> mixedMap = new HashMap<Object, Object>();
		mixedMap.put("key", expected);
		expectedRobot.setMixedMap(mixedMap);

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
	public void parseBeanWithMapOfSetValues() throws Exception {
		// Create the "input" XML
		String inputString = XML_HEADER + "<property name=\"mixedMap\">"
				+ "<map>" + "<entry key=\"key\">" + "<set>"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />" + "</bean>"
				+ "</set>" + "</entry>" + "</map>" + "</property>" + XML_FOOTER;
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		Set<Robot> expected = new HashSet<Robot>();
		expected.add(friend);

		Map<Object, Object> mixedMap = new HashMap<Object, Object>();
		mixedMap.put("key", expected);
		expectedRobot.setMixedMap(mixedMap);

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
	public void parseBeanWithMapOfListKeys() throws Exception {
		// Create the "input" XML
		String inputString = XML_HEADER + "<property name=\"mixedMap\">"
				+ "<map>" + "<entry>" + "<key>" + "<list>"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />" + "</bean>"
				+ "</list>" + "</key>" + "<value>value</value>" + "</entry>"
				+ "</map>" + "</property>" + XML_FOOTER;
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		List<Robot> expected = new ArrayList<Robot>();
		expected.add(friend);

		Map<Object, Object> mixedMap = new HashMap<Object, Object>();
		mixedMap.put(expected, "value");
		expectedRobot.setMixedMap(mixedMap);

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
	public void parseBeanWithMapOfSetKeys() throws Exception {
		// Create the "input" XML
		String inputString = XML_HEADER + "<property name=\"mixedMap\">"
				+ "<map>" + "<entry>" + "<key>" + "<set>"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />" + "</bean>"
				+ "</set>" + "</key>" + "<value>value</value>" + "</entry>"
				+ "</map>" + "</property>" + XML_FOOTER;
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		Set<Robot> expected = new HashSet<Robot>();
		expected.add(friend);

		Map<Object, Object> mixedMap = new HashMap<Object, Object>();
		mixedMap.put(expected, "value");
		expectedRobot.setMixedMap(mixedMap);

		List<ParameterSet> expectedParamSets = new ArrayList<ParameterSet>();
		expectedParamSets.add(new ParameterSet(null,
				new Object[] { expectedRobot }));

		// Run the test
		List<ParameterSet> actualParamSets = testFactory
				.createParameters(inputXml);

		// Verify the expected outcome
		assertParameterSetsEqual(expectedParamSets, actualParamSets);
	}

}