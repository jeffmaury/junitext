/**
 * Copyright (C) 2006-2007, Jochen Hiller.
 */
package org.junitext.manipulation;

import java.util.Comparator;

import org.junit.runner.Description;
import org.junit.runner.manipulation.Sorter;
import org.junitext.Category;

/**
 * A sorter to support categories.
 */
public class CategorySorter extends Sorter {

	public CategorySorter() {
		super(new Comparator<Description>() {

			public int compare(Description obj1, Description obj2) {
				// only when test case
				if ((obj1.isSuite() || obj2.isSuite())) {
					return 0;
				}
				try {
					Category c1 = CategoryResolver.getCategory(obj1);
					String cat1 = c1 != null ? c1.value() : "";
					Category c2 = CategoryResolver.getCategory(obj2);
					String cat2 = c2 != null ? c2.value() : "";
					int rc = cat1.compareTo(cat2);
					// when same, sort via name
					if (rc == 0) {
						return obj1.getDisplayName().compareTo(
								obj2.getDisplayName());
					} else {
						return rc;
					}
				} catch (Exception e) {
					// should never happen
					e.printStackTrace();
					return 0;
				}
			}

		});
	}
}
