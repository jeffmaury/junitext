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
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
				+ "<tests>" + "<test>" + "<value id=\"testString\">"
				+ expectedString + "</value>" + "</test>" + "</tests>";
		
		executeParseTest(testFactory, inputString, expectedString);
	}

	@Test
	public void parseInteger() throws Exception {
		Integer expectedInteger = 5;
		String inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
				+ "<tests>" + "<test>"
				+ "<value id=\"testInteger\" type=\"java.lang.Integer\">"
				+ expectedInteger + "</value>" + "</test>" + "</tests>";

		executeParseTest(testFactory, inputString, expectedInteger);
	}

	@Test
	public void parseShort() throws Exception {
		Short expectedShort = 5;
		String inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
				+ "<tests>" + "<test>"
				+ "<value id=\"testShort\" type=\"java.lang.Short\">"
				+ expectedShort + "</value>" + "</test>" + "</tests>";

		executeParseTest(testFactory, inputString, expectedShort);
	}

	@Test
	public void parseLong() throws Exception {
		Long expectedLong = 5L;
		String inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
				+ "<tests>" + "<test>"
				+ "<value id=\"testLong\" type=\"java.lang.Long\">"
				+ expectedLong + "</value>" + "</test>" + "</tests>";

		executeParseTest(testFactory, inputString, expectedLong);
	}

	@Test
	public void parseBoolean() throws Exception {
		Boolean expectedBoolean = false;
		String inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
				+ "<tests>" + "<test>"
				+ "<value id=\"testBoolean\" type=\"java.lang.Boolean\">"
				+ expectedBoolean + "</value>" + "</test>" + "</tests>";

		executeParseTest(testFactory, inputString, expectedBoolean);
	}
	
	@Test
	public void parseNullValue() throws Exception {
		String inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
			+ "<tests>" + "<test>"
			+ "<value id=\"testString\" type=\"java.lang.Boolean\">"
			+ "<null/></value>" + "</test>" + "</tests>";

		executeParseTest(testFactory, inputString, new Object[] {null});		
	}

	@Test
	public void parseBeanWithBeanPropertyUsingFullPropertySyntax()
			throws Exception {
		// Create the "input" XML
		String inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
				+ "<tests>"
				+ "<test>"
				+ "<bean id=\"expectedRobot\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\">" + "<value>Daneel Olivaw</value>"
				+ "</property>" + "<property name=\"id\">"
				+ "<value>134</value>" + "</property>"
				+ "<property name=\"model\">" + "<value>X24R</value>"
				+ "</property>" + "<property name=\"manufacturer\" >"
				+ "<value>Han Fastolfe</value>" + "</property>"
				+ "<property name=\"bestFriend\">"
				+ "<bean id=\"friend\" class=\"org.junitext.runners.Robot\" >"
				+ "<property name=\"name\">" + "<value>Johnny 5</value>"
				+ "</property>" + "<property name=\"id\">" + "<value>5</value>"
				+ "</property>" + "<property name=\"model\">"
				+ "<value>SAINT</value>" + "</property>"
				+ "<property name=\"manufacturer\">"
				+ "<value>Nova Laboratories</value>" + "</property>"
				+ "</bean>" + "</property>" + "</bean>" + "</test>"
				+ "</tests>";

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

		executeParseTest(testFactory, inputString, expectedRobot);
	}

	@Test
	public void parseBeanWithBeanPropertyUsingShortcutPropertySyntax()
			throws Exception {
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

		executeParseTest(testFactory, inputString, expectedRobot);
	}

	@Test
	public void setsParameterSetNameWhenIDAttributeProvided() throws Exception {
		String expectedName = "testName";
		String inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
				+ "<tests>" + "<test id=\"" + expectedName + "\" >"
				+ "<string id=\"testString\" value=\"test\" />" + "</test>"
				+ "</tests>";
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		// Run the test
		List<ParameterList> actualParamSets = testFactory
				.createParameters(inputXml);

		// Verify that the generated parameter set has the expected name
		assertEquals("The expected number of paremter sets was not generated.",
				1, actualParamSets.size());
		ParameterList actualSet = actualParamSets.get(0);
		assertEquals("The expected parameter set name was not assigned.",
				expectedName, actualSet.getName());
	}

	@Test
	public void setsParameterSetNameWhenNameAttributeProvided()
			throws Exception {
		String expectedName = "testName";
		String inputString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
				+ "<tests>" + "<test name=\"" + expectedName + "\" >"
				+ "<string id=\"testString\" value=\"test\" />" + "</test>"
				+ "</tests>";
		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		// Run the test
		List<ParameterList> actualParamSets = testFactory
				.createParameters(inputXml);

		// Verify that the generated parameter set has the expected name
		assertEquals("The expected number of paremter sets was not generated.",
				1, actualParamSets.size());
		ParameterList actualSet = actualParamSets.get(0);
		assertEquals("The expected parameter set name was not assigned.",
				expectedName, actualSet.getName());
	}
}
