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

import java.util.List;

/**
 * @author Jim Hurne
 * 
 */
public class DigesterParameterFactoryTestUtilities {

	/**
	 * Asserts that two lists of parameter sets are equal. Individual objects in
	 * a given parameter set are compared using the <code>toString()</code>
	 * method.
	 * 
	 * @param expectedParamSets
	 * @param acutalParamSets
	 */
	public static void assertParameterSetsEqual(
			List<ParameterSet> expectedParamSets,
			List<ParameterSet> acutalParamSets) {
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
				assertEquals(
						"The actual parameter object is not of the expected type.",
						expectedParamSet[k].getClass(), actualParamSet[k]
								.getClass());
				assertEquals(
						"The actual parameter object does not equal the expected parameter object.",
						expectedParamSet[k].toString(), actualParamSet[k]
								.toString());
			}
		}
	}
}
