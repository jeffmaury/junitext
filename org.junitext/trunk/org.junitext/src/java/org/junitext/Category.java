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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Test cases can be assigned to a <b>Category</b>, e.g. database tests, long
 * running tests, etc.
 * <p>
 * Each test case annotated with {@code @Category} can be used when running
 * tests in these ways:
 * </p>
 * <ul>
 * <li>Sort via categories</li>
 * <li>Filter via categories</li>
 * </ul>
 * <p>
 * Example how to sort via category:
 * </p>
 * 
 * <pre>
 * JUnitCore core = new JUnitCore();
 * Request request = Request.aClass(MyTest.class);
 * Result result = core.run(request.sortWith(new CategorySorter()));
 * </pre>
 * 
 * <p>
 * Example how to filter via category:
 * </p>
 * 
 * <pre>
 * JUnitCore core = new JUnitCore();
 * Request request = Request.aClass(MyTest.class);
 * Result result = core.run(request.filterWith(new CategoryFilter(&quot;db tests&quot;)));
 * </pre>
 * 
 * @see org.junitext.manipulation.CategorySorter
 * @see org.junitext.manipulation.CategoryFilter
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Category {

	/** The category name. */
	String value();
}
