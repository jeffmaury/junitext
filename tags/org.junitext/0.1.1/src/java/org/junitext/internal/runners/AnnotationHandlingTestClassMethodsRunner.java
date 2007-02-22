package org.junitext.internal.runners;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.junit.internal.runners.TestClassMethodsRunner;
import org.junit.internal.runners.TestMethodRunner;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junitext.Prerequisite;

/**
 * Internal runner for calling test methods.
 */
public class AnnotationHandlingTestClassMethodsRunner extends
		TestClassMethodsRunner {

	static class PrerequisiteHandler {

		public boolean handleAnnotation(Annotation prereq, Object testObject,
				Description description, RunNotifier notifier) {
			try {
				boolean isFulfilled = checkPrerequisite(testObject,
						(Prerequisite) prereq);
				if (isFulfilled) {
					return true;
				} else {
					// to simplify, call fire testIgnore
					// see also {@link Prerequisite} design question 2
					notifier.fireTestIgnored(description);
					return false;
				}
			} catch (Exception e) {
				Exception internalEx = new Exception(
						"Internal error when checking prerequisite", e);
				notifier.fireTestFailure(new Failure(description, internalEx));
				return false;
			}
		}

		private boolean checkPrerequisite(Object testObject, Prerequisite prereq)
				throws Exception {
			String prereqName = prereq.requires();
			Class classToRetrievePrerequisite = prereq.callee();

			Object objectToInvoke = null; // defaults to invoke of
			// class method
			// if class is default value: use current instance
			if (Prerequisite.NoCallee.class == classToRetrievePrerequisite) {
				classToRetrievePrerequisite = testObject.getClass();
				objectToInvoke = testObject;
			}
			// call method to object, must not have arguments
			Method methodToCall = classToRetrievePrerequisite.getMethod(
					prereqName, new Class[] {});
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

	private Method fMethod;

	private Object fTest;

	private RunNotifier fNotifier;

	public AnnotationHandlingTestClassMethodsRunner(Class<?> clazz) {
		super(clazz);
	}

	@Override
	protected TestMethodRunner createMethodRunner(Object test, Method method,
			RunNotifier notifier) {
		fMethod = method;
		fTest = test;
		fNotifier = notifier;
		return new TestMethodRunner(test, method, notifier,
				methodDescription(method)) {
			@Override
			public void run() {
				Prerequisite prereq = fMethod.getAnnotation(Prerequisite.class);
				if (prereq != null) {
					boolean fulfilled = new PrerequisiteHandler()
							.handleAnnotation(prereq, fTest,
									methodDescription(fMethod), fNotifier);
					if (!fulfilled) {
						return;
					}
				}
				super.run();
			}
		};
	}
}