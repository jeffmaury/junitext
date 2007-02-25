/*******************************************************************************
 * Copyright (C) 2006-2007 Jochen Hiller and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License - v 1.0
 * which accompanies this distribution, and is available at
 * http://junitext.sourceforge.net/licenses/junitext-license.html
 * 
 * Contributors:
 *     Jochen Hiller - initial API and implementation
 *     Jim Hurne - initial XMLParameterizedRunner API and implementation 
 ******************************************************************************/
package org.junitext.runners;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.junit.internal.runners.CompositeRunner;
import org.junit.internal.runners.MethodValidator;
import org.junit.internal.runners.TestClassMethodsRunner;
import org.junit.internal.runners.TestClassRunner;
import org.junitext.XMLParameters;
import org.junitext.runners.parameters.factory.Parameter;
import org.junitext.runners.parameters.factory.ParameterFactory;
import org.junitext.runners.parameters.factory.ParameterList;

/**
 * A custom JUnit test runner that allows tests to be parameterized by JavaBeans
 * instanciated with data in an XML file.
 * 
 * @author Jim Hurne
 */
public class XMLParameterizedRunner extends TestClassRunner {
	// TODO Don't use any of the internal JUnit classes

	/**
	 * Runs each individual "test" created by the XML.
	 */
	private static class TestClassRunnerForXMLParameters extends
			TestClassMethodsRunner {
		/**
		 * The parameters for this particular test run.
		 */
		private final ParameterList parameterList;

		/**
		 * The number that this test run represents.
		 */
		private final int parameterListNumber;

		/**
		 * The constructor to use when creating the test.
		 */
		private final Constructor<?> constructor;

		/**
		 * Constructs a new <code>TestClassRunnerForXMLParameters</code>.
		 * 
		 * @param klass
		 *            the test class
		 * @param parameters
		 *            the parameters to use when constructing the test class
		 *            instance
		 * @param i
		 *            the parameter set number
		 */
		private TestClassRunnerForXMLParameters(Class<?> klass,
				ParameterList paremeterList, int i) throws Exception {
			super(klass);
			parameterList = paremeterList;
			parameterListNumber = i;
			constructor = getConstructor();
			validateConstructor();
		}

		/**
		 * Creates a new instance of the test class. This method overrides the
		 * <code>org.junit.internal.runners.TestClassMethodsRunner.createTest()</code>
		 * method and instead creates the test class using the parameterized
		 * constructor.
		 * 
		 * @see org.junit.internal.runners.TestClassMethodsRunner#createTest()
		 */
		@Override
		protected Object createTest() throws Exception {
			return constructor.newInstance(parameterList.getParameterObjects());
		}

		/**
		 * Gets the name of the test class for display in end-user runners. This
		 * method overrides the
		 * <code>org.junit.internal.runners.TestClassMethodsRunner.getName()</code>
		 * method to return a name that is based on the parameter set.
		 * 
		 * @see org.junit.internal.runners.TestClassMethodsRunner#getName()
		 */
		@Override
		protected String getName() {
			if (parameterList.getName() != null) {
				return String.format("[%s] %s", parameterListNumber,
						parameterList.getName());
			}
			return String.format("[%s]", parameterListNumber);
		}

		/**
		 * Gets the name of an individual test method. This method overrides the
		 * <code>org.junit.internal.runners.TestClassMethodsRunner.testName(java.lang.reflect.Method)</code>
		 * method to construct a name based on the parameter set number.
		 * 
		 * @see org.junit.internal.runners.TestClassMethodsRunner#testName(java.lang.reflect.Method)
		 */
		@Override
		protected String testName(final Method method) {
			return String.format("%s[%s]", method.getName(),
					parameterListNumber);
		}

		/**
		 * Finds and returns the constructor for the class under test. The
		 * constructor returned is the first constructor to have the
		 * <code>XMLBeanParamaters</code> annotation.
		 * 
		 * @return
		 */
		private Constructor<?> getConstructor() throws Exception {
			Constructor[] constructors = getTestClass().getConstructors();
			for (Constructor<?> constructor : constructors) {
				if (constructor.isAnnotationPresent(XMLParameters.class)) {
					return constructor;
				}
			}
			// We should never get here, as this should have been validated
			// previously.
			throw new Exception(
					"No constructor with the XMLParameters annotation exists for class "
							+ getName());
		}

		/**
		 * @throws Exception
		 */
		public void validateConstructor() throws Exception {
			Class[] constructorParams = constructor.getParameterTypes();
			final List<Parameter> parameters = parameterList.getParameters();
			if (constructorParams.length != parameters.size()) {
				throw new Exception(
						"For test set ["
								+ parameterListNumber
								+ "], the parameter set does not contain the right number of parameters for the constuctor ["
								+ constructor.toString() + "].");
			}

			for (int j = 0; j < parameters.size(); j++) {
				if (!parameters.get(j).getParameter().getClass().equals(
						constructorParams[j])) {
					throw new Exception("For test set ["
							+ parameterListNumber
							+ "], parameter ["
							+ j
							+ "] is not of the right type for constructor ["
							+ constructor.toString()
							+ "]. "
							+ "Expected ["
							+ constructorParams[j].toString()
							+ "] but was ["
							+ parameters.get(j).getParameter().getClass()
									.toString() + "].");
				}
			}
		}
	}

	/**
	 * @author Jim Hurne
	 */
	private static class RunAllXMLParameterMethods extends CompositeRunner {
		private final Class<?> fKlass;

		private final List<ParameterList> parametersList;

		public RunAllXMLParameterMethods(Class<?> klass) throws Exception {
			super(klass.getName());
			fKlass = klass;
			int i = 0;
			parametersList = getParametersList();
			for (final ParameterList parameterList : parametersList) {
				super.add(new TestClassRunnerForXMLParameters(klass,
						parameterList, i++));
			}
		}

		@SuppressWarnings("unchecked")
		private List<ParameterList> getParametersList()
				throws IllegalAccessException, InvocationTargetException,
				Exception {
			XMLParameters annotation = getParameterAnnotation();
			ParameterFactory parameterFactory = annotation.parameterFactory()
					.newInstance();
			return parameterFactory.createParameters(fKlass,
					lookupXmlFile(annotation.value()));
		}

		private File lookupXmlFile(String fileName) {
			try {
				URL fileURL = getClass().getResource(fileName);
				if (fileURL == null) {
					// Don't do anything here...just return null
					// This way we can let the XMLBeanFactorys decide what to do
					// if the file is not found
					// or is invalid.
					return null;
				}
				return new File(new URI(fileURL.toString()));
			} catch (URISyntaxException e) {
				// Don't do anything here...just return null
				// This way we can let the XMLBeanFactorys decide what to do if
				// the file is not found
				// or is invalid.
				return null;
			}
		}

		@SuppressWarnings("unchecked")
		private XMLParameters getParameterAnnotation() throws Exception {
			for (Constructor constructor : fKlass.getConstructors()) {
				if (constructor.isAnnotationPresent(XMLParameters.class)) {
					return (XMLParameters) constructor
							.getAnnotation(XMLParameters.class);
				}
			}
			throw new Exception(
					"No constructor with the XMLParameters annotation exists for class "
							+ getName());
		}
	}

	/**
	 * Constructs a new <code>XMLBeanParamterizedRunner</code>.
	 * 
	 * @param klass
	 *            the test class
	 * @throws Exception
	 *             if there is a problem constructing the new test runner
	 */
	public XMLParameterizedRunner(final Class<?> klass) throws Exception {
		super(klass, new RunAllXMLParameterMethods(klass));
	}

	/**
	 * Validates that the methods of the test class are all valid. This
	 * overriden version
	 * <code>org.junit.internal.runners.TestClassRunner.validate(org.junit.internal.runners.MethodValidator)</code>
	 * drops the "parameterless constructor" requirement.
	 * 
	 * @see org.junit.internal.runners.TestClassRunner#validate(org.junit.internal.runners.MethodValidator)
	 */
	@Override
	protected void validate(MethodValidator methodValidator) {
		// We need to override the validate method becuase the test class
		// no longer requires a parameterless constructor
		methodValidator.validateStaticMethods();
		methodValidator.validateInstanceMethods();
	}
}
