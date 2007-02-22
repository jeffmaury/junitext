package org.junitext.manipulation;

import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;
import org.junitext.Category;

/**
 * A filter to support categories. Will run tests matching a given category.
 */
public class CategoryFilter extends Filter {

	private String category;

	public CategoryFilter(String c) {
		this.category = c;
	}

	@Override
	public boolean shouldRun(Description description) {
		Category c = CategoryResolver.getCategory(description);
		if (c != null) {
			return c.value().equals(category);
		} else {
			return true;
		}
	}

	@Override
	public String describe() {
		return "tests for category " + String.valueOf(category);
	}
}
