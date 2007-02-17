/**
 * Copyright (C) 2006-2007, Jochen Hiller.
 */
package org.junitext.samples;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junitext.XMLBeanParameters;
import org.junitext.runners.XMLBeanParameterizedRunner;

@RunWith(XMLBeanParameterizedRunner.class)
public class ParameterizedTestViaXMLBeanParameterizer {
	private Robot expectedRobot;

	private Robot actualRobot;

	@XMLBeanParameters("/org/junitext/samples/Robots.xml")
	public ParameterizedTestViaXMLBeanParameterizer(Robot expected, Robot actual) {
		this.expectedRobot = expected;
		this.actualRobot = actual;
	}

	@Test
	public void testRobotData() {
		assertEquals("The expected robot does not equal the actual robot.",
				expectedRobot.toString(), actualRobot.toString());
	}

}
