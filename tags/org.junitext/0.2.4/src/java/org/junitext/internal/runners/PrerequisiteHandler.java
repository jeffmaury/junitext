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
package org.junitext.internal.runners;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.StringTokenizer;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junitext.Prerequisite;

/**
 * Handler class for all prerequite handling.
 */
public class PrerequisiteHandler {

	// static methods

	public static String getClassName(Description description) {
		// ugly code: parse from string representation :(
		String name = description.getDisplayName();
		StringTokenizer tokenizer = new StringTokenizer(name, "(");
		// next token is: methodName, we ignore
		tokenizer.nextToken();
		String className = tokenizer.nextToken(")").substring(1);
		return className;
	}

	public static String getMethodName(Description description) {
		// ugly code: parse from string representation :(
		String name = description.getDisplayName();
		StringTokenizer tokenizer = new StringTokenizer(name, "(");
		String methodName = tokenizer.nextToken();
		return methodName;
	}

	// public methods

	public boolean shouldRun(Description description, Object fTest) {
		return shouldRun(description, fTest, null);
	}

	public boolean shouldRun(Description description, Object fTest,
			RunNotifier notifier) {
		try {
			Class<?> clazz = Class.forName(PrerequisiteHandler
					.getClassName(description));
			Method m = clazz.getMethod(PrerequisiteHandler
					.getMethodName(description));

			Prerequisite prereq = m.getAnnotation(Prerequisite.class);
			if (prereq != null) {
				// if prereq defined, handle it
				// no notifier available here
				return handleAnnotation(description, fTest, prereq, notifier);
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

	public boolean handleAnnotation(Description description, Object testObject,
			Annotation prereq, RunNotifier notifier) {
		try {
			boolean isFulfilled = checkPrerequisite(description, testObject,
					(Prerequisite) prereq);
			if (isFulfilled) {
				return true;
			} else {
				// to simplify, call fire testIgnore
				// see also {@link Prerequisite} design question 2
				if (notifier != null) {
					notifier.fireTestIgnored(description);
				}
				return false;
			}
		} catch (Exception e) {
			Exception internalEx = new Exception(
					"Internal error when checking prerequisite", e);
			if (notifier != null) {
				notifier.fireTestFailure(new Failure(description, internalEx));
			}
			return false;
		}
	}

	protected boolean checkPrerequisite(Description desc, Object testObject,
			Prerequisite prereq) throws Exception {
		String prereqName = prereq.requires();
		Class<?> classToRetrievePrerequisite = prereq.callee();

		// defaults to invoke of class method
		Object objectToInvoke = null;
		// if class is default value: use current instance
		if (Prerequisite.NoCallee.class == classToRetrievePrerequisite) {
			classToRetrievePrerequisite = testObject.getClass();
			objectToInvoke = testObject;
		}
		// test all supported reflection calls. Priority:
		// - description
		// - className, methodName
		// - void
		HandleState state = handleDescription(classToRetrievePrerequisite,
				prereqName, desc, objectToInvoke);
		if (state.equals(HandleState.NotHandled)) {
			state = handleClassNameMethodName(classToRetrievePrerequisite,
					prereqName, desc, objectToInvoke);
			if (state.equals(HandleState.NotHandled)) {
				state = handleVoidMethod(classToRetrievePrerequisite,
						prereqName, desc, objectToInvoke);
				// state = handleClassMethod();
			}
		}
		switch (state) {
		case False:
			return false;
		case True:
			return true;
		case NotHandled:
		default:
			// Hmm: compiler error? Will not understand, that enum is complete!
			// See Sun: 879666 - Compiler does not understand execution paths
			// for enum and switch statements
			throw new Exception(
					"Could not find prerequisite method with support Signatures.");
		}
	}

	/**
	 * Handle call to methods with signature
	 * {@code public boolean isSomething()}.
	 */
	protected HandleState handleVoidMethod(Class<?> clazz, String prereqName,
			Description desc, Object testObject) {
		try {
			// call method to object, must not have arguments
			Method methodToCall = clazz.getMethod(prereqName, new Class[] {});
			Object prereqRet = methodToCall.invoke(testObject, new Object[] {});
			if (prereqRet.getClass() != Boolean.class) {
				// wrong return type
				// System.out.println("Prerequisite is not valid, must return
				// boolean");
				return HandleState.NotHandled;
			}
			boolean b = ((Boolean) prereqRet).booleanValue();
			return b ? HandleState.True : HandleState.False;
		} catch (SecurityException e) {
			// ignore here
		} catch (NoSuchMethodException e) {
			// ignore here
		} catch (IllegalArgumentException e) {
			// ignore here
		} catch (IllegalAccessException e) {
			// ignore here
		} catch (InvocationTargetException e) {
			// ignore here
		}
		return HandleState.NotHandled;
	}

	/**
	 * Handle call to methods with signature
	 * {@code public boolean isSomething(String className, String methodName)}.
	 */
	protected HandleState handleClassNameMethodName(Class<?> clazz,
			String prereqName, Description description, Object testObject) {
		try {
			// call method to object, must not have arguments
			Method methodToCall = clazz.getMethod(prereqName, new Class[] {
					String.class, String.class });
			Object prereqRet = methodToCall.invoke(testObject, new Object[] {
					PrerequisiteHandler.getClassName(description),
					PrerequisiteHandler.getMethodName(description) });
			if (prereqRet.getClass() != Boolean.class) {
				// wrong return type
				// System.out.println("Prerequisite is not valid, must return
				// boolean");
				return HandleState.NotHandled;
			}
			boolean b = ((Boolean) prereqRet).booleanValue();
			return b ? HandleState.True : HandleState.False;
		} catch (SecurityException e) {
			// ignore here
		} catch (NoSuchMethodException e) {
			// ignore here
		} catch (IllegalArgumentException e) {
			// ignore here
		} catch (IllegalAccessException e) {
			// ignore here
		} catch (InvocationTargetException e) {
			// ignore here
		}
		return HandleState.NotHandled;
	}

	/**
	 * Handle call to methods with signature
	 * {@code public boolean isSomething(Description desc)}.
	 */
	protected HandleState handleDescription(Class<?> clazz, String prereqName,
			Description description, Object testObject) {
		try {
			// call method to object, must not have arguments
			Method methodToCall = clazz.getMethod(prereqName,
					new Class[] { Description.class });
			Object prereqRet = methodToCall.invoke(testObject,
					new Object[] { description });
			if (prereqRet.getClass() != Boolean.class) {
				// wrong return type
				// System.out.println("Prerequisite is not valid, must return
				// boolean");
				return HandleState.NotHandled;
			}
			boolean b = ((Boolean) prereqRet).booleanValue();
			return b ? HandleState.True : HandleState.False;
		} catch (SecurityException e) {
			// ignore here
		} catch (NoSuchMethodException e) {
			// ignore here
		} catch (IllegalArgumentException e) {
			// ignore here
		} catch (IllegalAccessException e) {
			// ignore here
		} catch (InvocationTargetException e) {
			// ignore here
		}
		return HandleState.NotHandled;
	}

	// inner classes

	private enum HandleState {
		False, True, NotHandled;
	}
}