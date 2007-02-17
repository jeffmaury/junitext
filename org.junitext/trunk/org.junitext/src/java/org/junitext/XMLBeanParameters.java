/**
 * Copyright (C) 2006-2007, Jochen Hiller.
 */
package org.junitext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junitext.runners.parameters.factory.DigesterParameterFactory;
import org.junitext.runners.parameters.factory.ParameterFactory;

/**
 * Marks a test class constructor as being paramaterized by the given XML file.
 * The <code>XMLBeanParameters</code> annotation also optionally allows a user
 * to specify the
 * {@link org.junitext.runners.parameters.factory.ParameterFactory}
 * implementation to use when creating the JavaBeans.
 * 
 * @author Jim Hurne
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.CONSTRUCTOR)
public @interface XMLBeanParameters {
	/**
	 * The XML File to create the JavaBeans from.
	 * 
	 * @return
	 */
	String value();

	/**
	 * Specifies the
	 * {@link org.junitext.runners.parameters.factory.ParameterFactory}
	 * implementation to use when creating the beans. If this parameter is not
	 * specified, then the default is to use
	 * {@link org.junitext.runners.parameters.factory.DigesterParameterFactory}.
	 * 
	 * @return
	 */
	Class<? extends ParameterFactory> beanFactory() 
	                                     default DigesterParameterFactory.class;
}
