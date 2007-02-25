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
import static org.junit.Assert.assertNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jim Hurne
 * 
 */
public class DigesterParameterFactoryTestUtilities {

	/**
	 * Runs a <code>DigesterParameterFactory</code> test using the given input
	 * string and a single expected test parameter. After the parsing has been
	 * completed, the results of the test are verified against the given test
	 * parameter.
	 * <p>
	 * This convenience method is the equivalent of calling:
	 * </p>
	 * <p>
	 * <code>exectueParseTest(testFactory, inputString, new Object[] { expectedParam })
	 * </code>
	 * </p>
	 * 
	 * @param testFactory
	 *            the test <code>DigesterParameterFactory</code> to run the
	 *            test with.
	 * @param inputString
	 *            the test input XML in the form of a <code>String</code>.
	 * @param expectedParam
	 *            the singly expected test parameter
	 * @throws Exception
	 */
	public static void executeParseTest(DigesterParameterFactory testFactory,
			String inputString, Object expectedParam) throws Exception {

		executeParseTest(testFactory, inputString,
				new Object[] { expectedParam });
	}

	/**
	 * Runs a <code>DigesterParameterFactory</code> test using the given input
	 * string and expected test parameters. After the parsing has been
	 * completed, the results of the test are verified against the given
	 * parameters.
	 * 
	 * @param testFactory
	 *            the test <code>DigesterParameterFactory</code> to run the
	 *            test with.
	 * @param inputString
	 *            the test input XML in the form of a <code>String</code>.
	 * @param expectedParams
	 *            an object array of expected test parameters.
	 * @throws Exception
	 */
	public static void executeParseTest(DigesterParameterFactory testFactory,
			String inputString, Object[] expectedParams) throws Exception {

		InputStream inputXml = new ByteArrayInputStream(inputString
				.getBytes("UTF-8"));

		List<ParameterList> expectedParamLists = new ArrayList<ParameterList>();
		expectedParamLists.add(new ParameterList(null, expectedParams));

		// Run the test
		List<ParameterList> actualParamSets = testFactory
				.createParameters(inputXml);

		// Verify the expected outcome
		assertParameterSetsEqual(expectedParamLists, actualParamSets);
	}

	/**
	 * Asserts that two lists of parameter sets are equal. Individual objects in
	 * a given parameter set are compared using the <code>toString()</code>
	 * method.
	 * 
	 * @param expectedParamSets
	 * @param acutalParamSets
	 */
	public static void assertParameterSetsEqual(
			List<ParameterList> expectedParamSets,
			List<ParameterList> acutalParamSets) {
		assertEquals("The correct number of paramter sets were not created.",
				expectedParamSets.size(), acutalParamSets.size());

		for (int j = 0; j < expectedParamSets.size(); j++) {
			Object[] expectedParamSet = expectedParamSets.get(j)
					.getParameterObjects();
			Object[] actualParamSet = acutalParamSets.get(j)
					.getParameterObjects();

			assertEquals("The correct number of objects were not created.",
					expectedParamSet.length, actualParamSet.length);

			for (int k = 0; k < expectedParamSet.length; k++) {
				if (expectedParamSet[k] == null) {
					assertNull(
							"The actual parameter object is not null when it expected to be.",
							actualParamSet[k]);
				} else {
					assertEquals(
							"The actual parameter object is not of the expected type.",
							expectedParamSet[k].getClass(), actualParamSet[k]
									.getClass());
					assertEquals(
							"The actual parameter object does not equal the expected parameter object.",
							expectedParamSet[k].toString(), actualParamSet[k]
									.toString());

					// Make double-sure that the two objects are equal. This is
					// important becuase the string representation of two
					// objects
					// may be equal while the two objects themselves are not.
					assertEquals(
							"The actual parameter object does not equal the expected parameter object.",
							expectedParamSet[k], actualParamSet[k]);
				}
			}
		}
	}
}
