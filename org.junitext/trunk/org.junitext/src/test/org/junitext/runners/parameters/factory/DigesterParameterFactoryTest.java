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
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junitext.runners.Robot;

public class DigesterParameterFactoryTest {

	private DigesterParameterFactory testFactory;

	@Before
	public void createTestFactory() {
		testFactory = new DigesterParameterFactory();
	}

	@Test
	public void parseString() throws Exception {
		String expectedString = "This is the expected string value.";
		String inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
				+ "<tests>" + "<test>" + "<string id=\"testString\" value=\""
				+ expectedString + "\" />" + "</test>" + "</tests>";
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		List<ParameterSet> expectedParamSets = new ArrayList<ParameterSet>();
		expectedParamSets.add(new ParameterSet(null,
				new Object[] { expectedString }));

		// Run the test
		List<ParameterSet> actualParamSets = testFactory
				.createParameters(inputXml);

		// Verify the expected outcome
		assertParameterSetsEqual(expectedParamSets, actualParamSets);
	}

	@Test
	public void parseInteger() throws Exception {
		Integer expectedInteger = 5;
		String inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
				+ "<tests>" + "<test>" + "<int id=\"testInteger\" value=\""
				+ expectedInteger + "\" />" + "</test>" + "</tests>";
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		List<ParameterSet> expectedParamSets = new ArrayList<ParameterSet>();
		expectedParamSets.add(new ParameterSet(null,
				new Object[] { expectedInteger }));

		// Run the test
		List<ParameterSet> actualParamSets = testFactory
				.createParameters(inputXml);

		// Verify the expected outcome
		assertParameterSetsEqual(expectedParamSets, actualParamSets);
	}

	@Test
	public void parseShort() throws Exception {
		Short expectedShort = 5;
		String inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
				+ "<tests>" + "<test>" + "<short id=\"testShort\" value=\""
				+ expectedShort + "\" />" + "</test>" + "</tests>";
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		List<ParameterSet> expectedParamSets = new ArrayList<ParameterSet>();
		expectedParamSets.add(new ParameterSet(null,
				new Object[] { expectedShort }));

		// Run the test
		List<ParameterSet> actualParamSets = testFactory
				.createParameters(inputXml);

		// Verify the expected outcome
		assertParameterSetsEqual(expectedParamSets, actualParamSets);
	}

	@Test
	public void parseLong() throws Exception {
		Long expectedLong = 5L;
		String inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
				+ "<tests>" + "<test>" + "<long id=\"testLong\" value=\""
				+ expectedLong + "\" />" + "</test>" + "</tests>";
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		List<ParameterSet> expectedParamSets = new ArrayList<ParameterSet>();
		expectedParamSets.add(new ParameterSet(null,
				new Object[] { expectedLong }));

		// Run the test
		List<ParameterSet> actualParamSets = testFactory
				.createParameters(inputXml);

		// Verify the expected outcome
		assertParameterSetsEqual(expectedParamSets, actualParamSets);
	}

	@Test
	public void parseBoolean() throws Exception {
		Boolean expectedBoolean = false;
		String inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
				+ "<tests>" + "<test>" + "<boolean id=\"testString\" value=\""
				+ expectedBoolean + "\" />" + "</test>" + "</tests>";
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		List<ParameterSet> expectedParamSets = new ArrayList<ParameterSet>();
		expectedParamSets.add(new ParameterSet(null,
				new Object[] { expectedBoolean }));

		// Run the test
		List<ParameterSet> actualParamSets = testFactory
				.createParameters(inputXml);

		// Verify the expected outcome
		assertParameterSetsEqual(expectedParamSets, actualParamSets);
	}

	@Test
	public void parseBeanWithBeanProperty() throws Exception {
		// Create the "input" XML
		String inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
				+ "<tests>"
				+ "<test>"
				+ "<bean id=\"expectedRobot\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Daneel Olivaw\" />"
				+ "<property name=\"id\" value=\"134\" />"
				+ "<property name=\"model\" value=\"X24R\" />"
				+ "<property name=\"manufacturer\" value=\"Han Fastolfe\" />"
				+ "<property name=\"bestFriend\">"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\" value=\"Johnny 5\" />"
				+ "<property name=\"id\" value=\"5\" />"
				+ "<property name=\"model\" value=\"SAINT\" />"
				+ "<property name=\"manufacturer\" value=\"Nova Laboratories\" />"
				+ "</bean>" + "</property>" + "</bean>" + "</test>"
				+ "</tests>";
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

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

		expectedRobot.setBestFriend(friend);

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
