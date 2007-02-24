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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junitext.runners.Robot;

public class DigesterParameterFactorySetsTest {
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
	public void parseBeanWithSet() throws Exception {
		// Create the "input" XML
		String inputString = XML_HEADER + "<property name=\"setOfFriends\">"
				+ "<set>"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />" + "</bean>"
				+ "</set>" + "</property>" + XML_FOOTER;
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		Set<Robot> expectedSet = new HashSet<Robot>();
		expectedSet.add(friend);
		expectedRobot.setSetOfFriends(expectedSet);

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
	public void parseBeanWithSetOfSets() throws Exception {
		// Create the "input" XML
		String inputString = XML_HEADER + "<property name=\"setOfSets\">"
				+ "<set>" + "<set>"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />" + "</bean>"
				+ "</set>" + "</set>" + "</property>" + XML_FOOTER;
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		Set<Robot> innerSet = new HashSet<Robot>();
		innerSet.add(friend);
		Set<Set<Robot>> expectedSet = new HashSet<Set<Robot>>();
		expectedSet.add(innerSet);
		expectedRobot.setSetOfSets(expectedSet);

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
	public void parseBeanWithSetOfSetsOfSets() throws Exception {
		// Create the "input" XML
		String inputString = XML_HEADER
				+ "<property name=\"threeLevelSetofFriends\">" + "<set>"
				+ "<set>" + "<set>"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />" + "</bean>"
				+ "</set>" + "</set>" + "</set>" + "</property>" + XML_FOOTER;
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		Set<Robot> innerSet = new HashSet<Robot>();
		innerSet.add(friend);

		Set<Set<Robot>> outerSet = new HashSet<Set<Robot>>();
		outerSet.add(innerSet);

		Set<Set<Set<Robot>>> expectedSet = new HashSet<Set<Set<Robot>>>();
		expectedSet.add(outerSet);
		expectedRobot.setThreeLevelSetofFriends(expectedSet);

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
	public void parseBeanWithASetOfValueObjects() throws Exception {

		// NOTE: This test only tests that a list of value objects contains
		// Strings. DigesterParameterFactory does not yet take advantage of
		// Generics to infer the correct list type from the bean's property
		// (like Spring is capable of doing).

		// Create the "input" XML
		String inputString = XML_HEADER + "<property name=\"mixedSet\">"
				+ "<set>" + "<value>A string value</value>"
				+ "<value>10232</value>" + "<value>false</value>"
				+ "</set></property>" + XML_FOOTER;
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		Set<Object> mixedSet = new HashSet<Object>();
		mixedSet.add("A string value");
		mixedSet.add("10232");
		mixedSet.add("false");

		expectedRobot.setMixedSet(mixedSet);

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