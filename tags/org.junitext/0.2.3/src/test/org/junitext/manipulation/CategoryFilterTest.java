/**
 * Copyright (C) 2006-2007, Jochen Hiller.
 */
package org.junitext.manipulation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;
import org.junit.internal.runners.InitializationError;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junitext.Category;

/**
 * Tests for the {@link CategoryFilter} for JUnit.
 */
public class CategoryFilterTest {

	public static class ACategoryTest {
		@Category ("Z") @Test public void c12() {}
		@Category ("Y") @Test public void c11() {}
		@Category ("A") @Test public void c22() {}
		@Category ("A") @Test public void c21() {}
	}

	@Test
	public void callWithFilter() throws InitializationError {
		JUnitCore core = new JUnitCore();
		Request req = Request.aClass(ACategoryTest.class);
		Result result = core.run(req.filterWith(new CategoryFilter("A")));
		assertEquals(2, result.getRunCount());
		result = core.run(req.filterWith(new CategoryFilter("Y")));
		assertEquals(1, result.getRunCount());
		result = core.run(req.filterWith(new CategoryFilter("Z")));
		assertEquals(1, result.getRunCount());
	}

	@Test
	public void nullFilter() {
		JUnitCore core = new JUnitCore();
		Request req = Request.aClass(ACategoryTest.class);
		Result result = core.run(req.filterWith(new CategoryFilter(null)));
		// when filter is null, no match. Result in "NoTestsRemainingException",
		// so 1 failure, and 1 run
		// TODO: Hmm, is this an expected behaviour ?
		assertEquals(1, result.getFailureCount());
		assertEquals(0, result.getIgnoreCount());
		assertEquals(1, result.getRunCount());
	}

	/**
	 * Scenario behind this test case: Get all Categories before starting a test
	 * case, to provide a selection of categories to include/exclude.
	 */
	@Test
	public void getFilterCategoriesBeforeRun() {
		JUnitCore core = new JUnitCore();
		Request req = Request.aClass(ACategoryTest.class);
		Runner r = req.getRunner();

		Set<Category> cats = new HashSet<Category>();
		for (Description each : r.getDescription().getChildren()) {
			Category c = CategoryResolver.getCategory(each);
			if (c != null) {
				cats.add(c);
			}
		}
		// A is twice
		assertEquals (3, cats.size());
		// select Category "A"
		Category a = null;
		for (Iterator<Category> iter = cats.iterator() ; iter.hasNext() ; ) {
			Category c = (Category) iter.next();
			if ("A".equals(c.value())) {
				a = c;
			}
		}
		assertNotNull(a);
		assertEquals ("A", a.value());
		// and now run all tests
		Result res = core.run(req.filterWith(new CategoryFilter(a.value())));
		assertEquals(2, res.getRunCount());
	}

	public static void main(String... args) {
		JUnitCore.main(CategoryFilterTest.class.getName());
	}
}
