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
package org.junitext.runners.parameters.factory;

/**
 * Represents a single test value.
 * 
 * @author Jim Hurne
 */
public class Parameter {

	private Object value;
	private String name;
	
	/**
	 * Creates an empty <code>Parameter</code>.
	 */
	public Parameter() {
	}

	/**
	 * Creates a new test <code>Paremter</code> with the given value. The name
	 * of this value is set to <code>null</code>.
	 * 
	 * @param value
	 *            the value to assign to this parameter
	 */
	public Parameter(Object value) {
		this.name = null;
		this.value = value;
	}

	/**
	 * Creates a new test <code>Parameter</code> with the given name and
	 * value.
	 * 
	 * @param name
	 *            the name to assign to this parameter
	 * @param value
	 *            the value to assign to this parameter
	 */
	public Parameter(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public Object getParameter() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setParameter(Object parameter) {
		this.value = parameter;
	}

}
