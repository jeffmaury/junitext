/**
 * Copyright (C) 2006-2007, Jochen Hiller.
 */
package org.junitext.samples;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junitext.XMLParameters;
import org.junitext.runners.XMLParameterizedRunner;

@RunWith(XMLParameterizedRunner.class)
public class XMLParameterizedSampleTest {
	private String expectedName;

	private String actualName;

	@XMLParameters("/org/junitext/samples/Data.xml")
	public XMLParameterizedSampleTest(String expected, String actual) {
		this.expectedName = expected;
		this.actualName = actual;
	}

	@Test
	public void namesAreEqual() {
		assertEquals("The expected name does not equal the actual name.",
				expectedName, actualName);
	}
}
