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

import java.lang.reflect.Array;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

/**
 * Sets a property on the object on top of the stack based on a single parameter
 * (set with the <code>CallParamRule</code>).
 * 
 * @author Jim Hurne
 */
public class SetPropertyWithParameterRule extends Rule {
	private static final String PROPERTY_NAME_STACK = "org.junitext.runners.parameters.factory."
			+ "SetPropertyWithParameterRule.propertyNames";

	private String propertyAttribute;

	public SetPropertyWithParameterRule(String attribute) {
		propertyAttribute = attribute;
	}

	/**
	 * @see org.apache.commons.digester.Rule#begin(java.lang.String,
	 *      java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void begin(String namespace, String elementName,
			Attributes attributes) throws Exception {
		// get the name of the property to set on the bean
		String propertyName = "";
		for (int j = 0; j < attributes.getLength(); j++) {
			String attrName = attributes.getLocalName(j);
			if (attrName.equals("")) {
				attrName = attributes.getQName(j);
			}
			if (attrName.equals(propertyAttribute)) {
				// We found the attribute that defines the
				// property to set
				propertyName = attributes.getValue(j);
			}
		}

		// Push the name of the property we are going to set onto a named stack.
		// We use a stack because an instance variable would be reset on nested
		// elements
		digester.push(PROPERTY_NAME_STACK, propertyName);

		// Push a single-element array onto the parameter stack to capture
		// parameters
		Object[] parameters = new Object[1];
		digester.pushParams(parameters);
	}

	/**
	 * @see org.apache.commons.digester.Rule#end(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void end(String arg0, String arg1) throws Exception {

		// Get the name of the property that we are going to set
		// This was pushed onto the stack in the begin method.
		String propertyName = (String) digester.pop(PROPERTY_NAME_STACK);

		// get the parameters
		Object[] parameters = (Object[]) digester.popParams();

		// Get the parent bean to on which we are setting the property
		Object parentBean = digester.peek(0);

		// Only attempt to set the property if an object parameter was set
		// This is so that property tags that have a value are not overwritten.
		if (parameters[0] != null) {

			if (digester.getLogger().isDebugEnabled()) {
				StringBuilder logMessage = new StringBuilder();
				logMessage.append("[SetPropertyWithParameterRule]{");
				logMessage.append(digester.getMatch());
				logMessage.append("} Setting ");
				logMessage.append(parentBean.getClass().getName());
				logMessage.append(".");
				logMessage.append(propertyName);
				logMessage.append(" = ");
				logMessage.append(parameters[0]);
				digester.getLogger().debug(logMessage.toString());
			}

			if (parameters[0] instanceof List) {
				// List objects may also represent array properties. Check if
				// the property is an array instead of a list.
				Class<?> propertyClass = PropertyUtils.getPropertyType(
						parentBean, propertyName);
				if (propertyClass.isArray()) {
					// It is an array...so we need to convert our list to an
					// array of the correct type.
					parameters[0] = listToArray((List) parameters[0],
							propertyClass.getComponentType());
				}
			}

			// Attempt to set the property on the parentBean using the given
			// object
			BeanUtils.setProperty(parentBean, propertyName, parameters[0]);
		}
	}

	/**
	 * Converts the given list to an array of the given component type.
	 * 
	 * @param parameterAsList
	 *            the <code>List</code> to convert to an array
	 * @param componentType
	 *            specifies the type of array to create
	 * @return
	 */
	private Object listToArray(List<?> parameterAsList, Class<?> componentType) {
		// This is a tricky bit of Java reflection. Basically, it is not
		// possible to use the List.toArray(T[]) method because the type of the
		// array is not known at compile time. Instead, we need to create a
		// brand new instance of the array with the given component type of the
		// same size as the list. Then all we need to do is to copy the list
		// elements into the array.
		Object newArray = Array.newInstance(componentType, parameterAsList
				.size());
		for (int j = 0; j < parameterAsList.size(); j++) {
			Array.set(newArray, j, parameterAsList.get(j));
		}
		return newArray;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("SetPropertyWithParameterRule[propertyAttribute=");
		sb.append(propertyAttribute);
		sb.append("]");
		return sb.toString();
	}
}
