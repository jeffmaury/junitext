package org.junitext.manipulation;

import java.lang.reflect.Method;
import java.util.StringTokenizer;

import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;
import org.junitext.Prerequisite;

/**
 * A filter to support prerequisites.
 */
public class PrerequisiteFilter extends Filter {

	/** The test object. */
	private Object fTest;

	@Override
	public boolean shouldRun(Description description) {
		// only when test case
		if (description.isSuite()) {
			return true;
		}
		// ugly code: parse from string representation :(
		String name = description.getDisplayName();
		StringTokenizer tokenizer = new StringTokenizer(name, "(");
		String methodName = tokenizer.nextToken();
		String className = tokenizer.nextToken(")").substring(1);
		try {
			Class clazz = Class.forName(className);
			Method m = clazz.getMethod(methodName);

			Prerequisite prereq = m.getAnnotation(Prerequisite.class);
			if (prereq != null) {
				// if prereq defined, check it
				return checkPrerequisite(fTest, prereq);
			} else {
				// else shouldRun=true
				return true;
			}
		} catch (Exception e) {
			Exception internalEx = new Exception(
					"Internal error when checking prerequisite", e);
			// TODO: how can we indicate this as an error ? throw an exception ?
			// No notifier available
			// fNotifier.fireTestFailure(new Failure(methodDescription(fMethod),
			// internalEx));
			internalEx.printStackTrace();
			return false;
		}
	}

	@Override
	public String describe() {
		return "tests with prerequisites fulfilled";
	}

	private boolean checkPrerequisite(Object testObject, Prerequisite prereq)
			throws Exception {
		String prereqName = prereq.requires();
		Class classToRetrievePrerequisite = prereq.callee();

		// defaults to invoke of class method
		Object objectToInvoke = null;
		// if class is not set: use current test object
		if (Prerequisite.NoCallee.class == classToRetrievePrerequisite) {
			classToRetrievePrerequisite = testObject.getClass();
			objectToInvoke = testObject;
		}
		// call method to object, must not have arguments
		Method methodToCall = classToRetrievePrerequisite.getMethod(prereqName,
				new Class[] {});
		// propagate all reflection exceptions, will be handled as
		// internal errors
		Object prereqIsFulfilled = methodToCall.invoke(objectToInvoke,
				new Object[] {});
		if (prereqIsFulfilled.getClass() != Boolean.class) {
			throw new Exception(
					"Prerequisite is not valid, must return boolean");
		}
		return ((Boolean) prereqIsFulfilled).booleanValue();
	}
}
