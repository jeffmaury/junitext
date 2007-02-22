package org.junitext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Test cases can be assigned to a <b>Category</b>, e.g. database tests, long running tests, etc. 
 * <p>
 * Each test case annotated with <code>@Category</code> can be used when running tests in these ways:
 * </p>
 * <ul>
 * <li>Sort via categories</li>
 * <li>Filter via categories</li>
 * </ul>
 * <p>
 * Example how to sort via category:
 * </p>
 * <p>
 * <code>
 * &nbsp;&nbsp;JUnitCore core = new JUnitCore();<br />
 * &nbsp;&nbsp;Request request = Request.aClass(MyTest.class);<br />
 * &nbsp;&nbsp;Result result = core.run(request.sortWith(new CategorySorter()));<br />
 * </code>
 * </p>
 * <p>
 * Example how to filter via category:
 * </p>
 * <p>
 * <code>
 * &nbsp;&nbsp;JUnitCore core = new JUnitCore();<br />
 * &nbsp;&nbsp;Request request = Request.aClass(MyTest.class);<br />
 * &nbsp;&nbsp;Result result = core.run(request.filterWith(new CategoryFilter("db tests")));<br />
 * </code>
 * </p>
 * @see org.junitext.manipulation.CategorySorter  
 * @see org.junitext.manipulation.CategoryFilter  
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Category {

	/** The category name. */
	String value();
}
