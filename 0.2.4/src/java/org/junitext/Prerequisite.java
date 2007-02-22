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
 * Test cases can be dependent of <b>prerequisites</b>, e.g. be online in
 * Internet, have a database available, have database filled with specific test
 * data, etc.
 * <p>
 * For these cases it can be very annoying to {@code @Ignore}</code> the test
 * cases, because it may be different for each individual run. For this
 * requirement, the <b>{@code @Prerequisite}</b> annotation can be used.
 * </p>
 * <p>
 * Each test case annotated with {@code @Prerequisite}</code> will be checked,
 * whether the condition is fulfilled. If not, the test case will be marked as
 * ignored.
 * </p>
 * <p>
 * For example:
 * </p>
 * 
 * <pre>
 * {@literal @}Prerequisite(requires=&quot;isDBAvailable&quot;)
 * {@literal @}Test public void doDBTest() {
 *   // ...
 * }
 * </pre>
 * 
 * <p>
 * The method called (here: {@code isDBAvailable}) must be implemented with
 * following signature:
 * <ul>
 * <li>must be callable from TestRunner, so must be {@code public}.
 * <li>must be callable either from tested object, or from a static helper
 * class.
 * <li>must return a {@code boolean} or {@code Boolean} value.
 * <li>Arguments may be (searched in this order):
 * <ul>
 * <li>a test description, e.g.<br />
 * {@code public boolean isDBAvailable (Description testDescription);}. See
 * {@link org.junit.runner.Description}</li>
 * <li>the current test class and method name, e.g. <br />
 * {@code public boolean isDBAvailable (String className, String methodName);}</li>
 * <li>no arguments, e.g.<br />
 * {@code public boolean isDBAvailable ();}</li>
 * </ul>
 * </li>
 * </ul>
 * </p>
 * <p>
 * Some examples:
 * </p>
 * 
 * <pre>
 *    public boolean isNonProductionTest(Description desc) {
 *      // all production tests start with &quot;prod&quot; 
 *      return !desc.getDisplayName().startsWith(&quot;prod&quot;);
 *    }
 *    
 *    public boolean isActiveTest(String className, String methodName) {
 *      // for some reason, SpecialBsinessTest.test100() has to be taken out of test suite
 *      if ((&quot;SpecialBusinessTest&quot;.equals(className)) &amp;&amp; (&quot;test100&quot;.equals(methodName))) {
 *        return false;
 *      }
 *      return true;
 *    }
 *    
 *    public boolean isDBAvailable() {
 *      // we check, whether DB is available
 *      boolean available = ...;
 *      return available;
 *    }
 * </pre>
 * 
 * <p>
 * This method can also be provided by a static helper class, when specifiying a
 * {@code callee} attribute to annotation.
 * </p>
 * <p>
 * For example:
 * </p>
 * 
 * <pre>
 *    {@literal @}Prerequisite (requires=&quot;isDBAvailable&quot;, callee=DBHelper.class)
 *    {@literal @}Test public void doDBTest() {
 *      ...
 *    }
 *    public class DBHelper {
 *      public static boolean isDBAvailable() {
 *        boolean available = ...;
 *        return available;
 *      }
 *    }
 * </pre>
 * 
 * TODO: Design question 1: {@code @Prerequisite} can also be made an extension
 * to {@code @Ignore} e.g.
 * {@code @Ignore (values="Check for database available", when="!isDBAvailable")}<br />
 * TODO: Design question 2: Current implementation handles a not fulfilled
 * prerequisite as specified with {@code @Ignore}. Means, you cannot later
 * distinguish between {@code @Ignore} and {@code @Prerequisite} runs.<br />
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Prerequisite {

	/** Helper class to recognize a not set callee. */
	static class NoCallee extends Object {
		private static final long serialVersionUID = 1L;

		private NoCallee() {
		}
	}

	/**
	 * Attribute for the method to be called for the prerequsite. Will does NOT
	 * have a default name.
	 * 
	 * @return the method name as string
	 */
	String requires();

	/**
	 * The class to be called. The default value {@code NoCallee} means, call
	 * the test class itself.
	 * 
	 * @return the class to be called when checking prerequisite
	 */
	Class<?> callee() default NoCallee.class;
}
