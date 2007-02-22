package org.junitext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Test cases can be dependent of <b>prerequisites</b>, e.g. be online in Internet,
 * have a database available, have database filled with specific test data, etc.
 * <p>
 * In these cases it can be very annoying to <code>@Ignore</code> the test cases, because it may be different for each individual run.
 * For this requirement, the <b><code>@Prerequisite</code></b> annotation can be used.
 * </p>
 * <p>
 * Each test case annotated with <code>@Prerequisite</code> will be checked, whether the condition is fulfilled. If
 * not, the test case will be marked as ignored.
 * </p>
 * <p>
 * For example:<br />
 * <code>
 * &nbsp;&nbsp;@Prerequisite (requires="isDBAvailable")&nbsp;<br />
 * &nbsp;&nbsp;@Test public void doDBTest() {<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;...<br />
 * &nbsp;&nbsp;}<br />
 * </code>
 * </p>
 * <p>
 * The method <code>isDBAvailable</code> must be implemented with following signature:
 * <ul>
 * <li>must be callable from TestRunner, so must be <code>public</code>.
 * <li>must be callable either from tested object, or from a static helper class.
 * <li>must return a <code>boolean</code> or <code>Boolean</code> value.
 * </ul>
 * </p>
 * <p>
 * For example:<br />
 * <code>
 * &nbsp;&nbsp;public boolean isDBAvailable() {<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;boolean available = ...;<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;return available;<br />
 * &nbsp;&nbsp;}<br />
 * </code>
 * </p>
 * 
 * <p>
 * This method can also be provided by a static helper class, when specifiying a <code>callee</code> attribute to annotation.
 * </p>
 * <p>
 * For example:<br />
 * <code>
 * &nbsp;&nbsp;@Prerequisite (requires="isDBAvailable", callee=DBHelper.class)<br />
 * &nbsp;&nbsp;@Test public void doDBTest() {<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;...<br />
 * &nbsp;&nbsp;}<br />
 * &nbsp;&nbsp;public class DBHelper {<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;public static boolean isDBAvailable() {<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;boolean available = ...;<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;return available;<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br />
 * &nbsp;&nbsp;}<br />
 * </code>
 * </p>
 * 
 * TODO: Design question 1: <code>@Prerequisite</code> can also be made an extension to <code>@Ignore</code> eg.<br />
 * <code>@Ignore (values="Check for database available", when="!isDBAvailable")</code>
 * TODO: Design question 2: Current implementation handles a not fulfilled prerequisite as specified with
 * <code>@Ignore</code>. Means, you cannot later distinguish between <code>@Ignore</code> and
 * <code>@Prerequisite</code> runs.
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
	 * Attribute for the method to be called for the prerequsite.
	 * Will does NOT have a default name.
	 * @return the method name as string
	 */
	String requires();

	/**
	 * The class to be called. The default value <code>NoCallee</code> means, call the test class itself.  
	 * @return the class to be called when checking prerequisite
	 */
	Class callee() default NoCallee.class;
}
