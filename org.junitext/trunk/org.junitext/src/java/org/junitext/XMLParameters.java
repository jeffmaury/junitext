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
package org.junitext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junitext.runners.parameters.factory.DigesterParameterFactory;
import org.junitext.runners.parameters.factory.ParameterFactory;

/**
 * Marks a test class constructor as being paramaterized by the given XML file.
 * The <code>XMLParameters</code> annotation also optionally allows a user
 * to specify the
 * {@link org.junitext.runners.parameters.factory.ParameterFactory}
 * implementation to use when creating the parameters.
 * 
 * @author Jim Hurne
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.CONSTRUCTOR)
public @interface XMLParameters {
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
