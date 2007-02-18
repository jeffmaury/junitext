/*******************************************************************************
 * Copyright (C) 2006-2007 Jochen Hiller and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License - v 1.0
 * which accompanies this distribution, and is available at
 * http://junitext.sourceforge.net/licenses/junitext-license.html
 * 
 * Contributors:
 *     Jochen Hiller - initial API and implementation
 ******************************************************************************/
package org.junitext;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.junitext.manipulation.CategoryFilterTest;
import org.junitext.manipulation.CategorySorterTest;
import org.junitext.manipulation.PrerequisiteFilterTest;
import org.junitext.runners.XMLParameterizedTest;
import org.junitext.runners.parameters.factory.BasicDigesterParameterFactoryTest;

@RunWith(Suite.class)
@SuiteClasses( { PrerequisiteTest.class, PrerequisiteHandlerTest.class, CategoryFilterTest.class,
		CategorySorterTest.class, PrerequisiteFilterTest.class, XMLParameterizedTest.class,
		BasicDigesterParameterFactoryTest.class})
public class AllTests {
	public static Test suite() {
		return new JUnit4TestAdapter(AllTests.class);
	}

	public static void main(String... args) throws InitializationError {
		JUnitCore core = new JUnitCore();
		core.addListener(new TextListener());
		core.run(AllTests.class);
	}
}
