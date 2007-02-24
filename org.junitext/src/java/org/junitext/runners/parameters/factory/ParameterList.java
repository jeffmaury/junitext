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

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of test parameters.
 * 
 * @author Jim Hurne
 */
public class ParameterList {

	private String name;
	private List<Parameter> parameters;

	/**
	 * Creates an empty <code>ParameterList</code>.
	 */
	public ParameterList() {
		parameters = new ArrayList<Parameter>();
	}

	/**
	 * Creates a new <code>ParameterList</code> with the given name and the
	 * given list of parameters.
	 * 
	 * @param name
	 *            the name to assign to this <code>ParameterList</code>
	 * @param parameters
	 *            the parameters to assign to this <code>ParameterList</code>
	 */
	public ParameterList(String name, List<Parameter> parameters) {
		this.name = name;
		this.parameters = parameters;
	}

	/**
	 * Creates a new <code>ParameterList</code> with the given name from an
	 * array of <code>Object</code>s. This constructor will automatically
	 * create a <code>List&lt;Parameter&lt;</code> where each parameter has no
	 * name (<code>null</code>).
	 * 
	 * @param name
	 * @param parameters
	 */
	public ParameterList(String name, Object[] parameters) {
		this.parameters = new ArrayList<Parameter>();
		for (Object param : parameters) {
			this.parameters.add(new Parameter(param));
		}
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
	 * @return the parameters
	 */
	public List<Parameter> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters
	 *            the parameters to set
	 */
	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	/**
	 * Adds a parameter to this <code>ParameterList</code>.
	 * 
	 * @param parameterToAdd
	 */
	public void add(Parameter parameterToAdd) {
		this.parameters.add(parameterToAdd);
	}

	/**
	 * Adds an arbitrary object as a parameter to this
	 * <code>ParameterList</code>.
	 * 
	 * @param parameterToAdd
	 */
	public void add(Object parameterToAdd) {
		this.parameters.add(new Parameter(parameterToAdd));
	}

	/**
	 * Returns the parameters contained in this <code>ParameterList</code> as
	 * an array of objects.
	 * 
	 * @return the parameters as an array of objects
	 */
	public Object[] getParameterObjects() {
		Object[] objects = new Object[parameters.size()];
		for (int j = 0; j < parameters.size(); j++) {
			objects[j] = parameters.get(j).getParameter();
		}
		return objects;
	}
}
