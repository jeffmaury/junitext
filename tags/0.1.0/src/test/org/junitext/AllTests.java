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

@RunWith(Suite.class)
@SuiteClasses( { PrerequisiteTest.class, CategoryFilterTest.class,
		CategorySorterTest.class, PrerequisiteFilterTest.class })
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