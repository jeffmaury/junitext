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

import static org.junitext.runners.parameters.factory.DigesterParameterFactoryTestUtilities.executeParseTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junitext.runners.Robot;

public class DigesterParameterFactoryListsTest {
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
	public void parseBeanWithList() throws Exception {
		// Create the "input" XML
		String inputString = XML_HEADER + "<property name=\"friends\">"
				+ "<list>"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />" + "</bean>"
				+ "</list>" + "</property>" + XML_FOOTER;

		List<Robot> expectedFriends = new ArrayList<Robot>();
		expectedFriends.add(friend);
		expectedRobot.setFriends(expectedFriends);

		executeParseTest(testFactory, inputString, expectedRobot);
	}

	@Test
	public void parseBeanWithAListOfLists() throws Exception {
		// Create the "input" XML
		String inputString = XML_HEADER + "<property name=\"listsOfFriends\">"
				+ "<list>" + "<list>"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />" + "</bean>"
				+ "</list>" + "</list>" + "</property>" + XML_FOOTER;

		List<Robot> expectedFriends = new ArrayList<Robot>();
		expectedFriends.add(friend);
		List<List<Robot>> listOfFriends = new ArrayList<List<Robot>>();
		listOfFriends.add(expectedFriends);
		expectedRobot.setListsOfFriends(listOfFriends);

		executeParseTest(testFactory, inputString, expectedRobot);
	}

	@Test
	public void parseBeanWithAListOfListsOfLists() throws Exception {
		// Create the "input" XML
		String inputString = XML_HEADER
				+ "<property name=\"threeLevelListOfFriends\">" + "<list>"
				+ "<list>" + "<list>"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />" + "</bean>"
				+ "</list>" + "</list>" + "</list>" + "</property>"
				+ XML_FOOTER;

		List<Robot> expectedFriends = new ArrayList<Robot>();
		expectedFriends.add(friend);
		List<List<Robot>> listOfFriends = new ArrayList<List<Robot>>();
		listOfFriends.add(expectedFriends);

		List<List<List<Robot>>> thirdLevelList = new ArrayList<List<List<Robot>>>();
		thirdLevelList.add(listOfFriends);

		expectedRobot.setThreeLevelListOfFriends(thirdLevelList);

		executeParseTest(testFactory, inputString, expectedRobot);
	}

	@Test
	public void parseBeanWithAListOfValueObjects() throws Exception {

		// NOTE: This test only tests that a list of value objects contains
		// Strings. DigesterParameterFactory does not yet take advantage of
		// Generics to infer the correct list type from the bean's property
		// (like Spring is capable of doing).

		// Create the "input" XML
		String inputString = XML_HEADER + "<property name=\"mixedList\">"
				+ "<list>" + "<value>A string value</value>"
				+ "<value type=\"java.lang.Integer\">10232</value>" 
				+ "<value type=\"java.lang.Boolean\">false</value>"
				+ "<value><null/></value>"
				+ "<value></value>"
				+ "</list></property>" + XML_FOOTER;

		List<Object> mixedList = new ArrayList<Object>();
		mixedList.add("A string value");
		mixedList.add(new Integer(10232));
		mixedList.add(new Boolean(false));
		mixedList.add(null);
		mixedList.add("");

		expectedRobot.setMixedList(mixedList);

		executeParseTest(testFactory, inputString, expectedRobot);
	}

	@Test
	public void parseBeanWithAnArrayOfBeans() throws Exception {
		// Create the "input" XML
		String inputString = XML_HEADER + "<property name=\"arrayOfFriends\">"
				+ "<list>"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />" + "</bean>"
				+ "</list>" + "</property>" + XML_FOOTER;

		List<Robot> expectedFriends = new ArrayList<Robot>();
		expectedFriends.add(friend);
		Robot[] arrayOfFriends = { friend };
		expectedRobot.setArrayOfFriends(arrayOfFriends);

		executeParseTest(testFactory, inputString, expectedRobot);
	}

	@Test
	public void parseBeanWithAMultidimentionalArrayOfStrings() throws Exception {
		// Create the "input" XML
		String expectedStringA = "One expected string";
		String expectedStringB = "Another expected string";
		String expectedStringC = "One last expected string";
		String inputString = XML_HEADER
				+ "<property name=\"multiDimensionalArrayOfStrings\">"
				+ "<list>" + "<list>" + "<value>" + expectedStringA
				+ "</value>" + "<value>" + expectedStringB + "</value>"
				+ "</list>" + "<list>" + "<value>" + expectedStringC
				+ "</value>" + "</list>" + "</list>" + "</property>"
				+ XML_FOOTER;

		String[][] multiDimensionalArrayOfStrings = {
				{ expectedStringA, expectedStringB }, { expectedStringC } };
		expectedRobot
				.setMultiDimensionalArrayOfStrings(multiDimensionalArrayOfStrings);

		executeParseTest(testFactory, inputString, expectedRobot);
	}
}