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

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

/**
 * Digester rule which creates a "value" object based on the given type in a
 * named attribute. The body text of the matching element is used to initialize
 * the value object.
 * <p>
 * If the type attribute is not specified by the element, then it is assumed
 * that the type of the value object is <code>String</code>.
 * </p>
 * <p>
 * When the end tag is reached, the created object will be popped. Note that
 * this means that if you are going to do anything with value objects created
 * with this rule, it has to be done after this rules body text call is made and
 * BEFORE this rule's end method is called. Thus the order in which the rules
 * are defined is very important for this rule to be effective.
 * </p>
 * 
 * @author Jim Hurne
 * 
 */
public class CreateValueObjectRule extends Rule {

	private static final String VALUE_OBJECT_TYPE_STACK = "org.junitext.runners.parameters.factory."
			+ "CreateValueObjectRule.valueObjectType";

	private final String typeAttributeName;

	/**
	 * Creates a new <code>CreateValueObjectRule</code> with the given type
	 * attribute name.
	 * 
	 * @param typeAttributeName
	 *            the name of the attribute to get the value objects type.
	 */
	public CreateValueObjectRule(String typeAttributeName) {
		this.typeAttributeName = typeAttributeName;
	}

	/**
	 * @see org.apache.commons.digester.Rule#begin(java.lang.String,
	 *      java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void begin(String namespace, String elementName,
			Attributes attributes) throws Exception {

		// Look to see if the type of the value object has been defined
		String valueType = null;
		for (int j = 0; j < attributes.getLength(); j++) {
			String attrName = attributes.getLocalName(j);
			if (attrName.equals("")) {
				attrName = attributes.getQName(j);
			}
			if (attrName.equals(typeAttributeName)) {
				// We found the attribute that defines the
				// property to set
				valueType = attributes.getValue(j);
			}
		}

		if (valueType == null) {
			// The value type was not specified, so default to String
			valueType = String.class.getName();
		}

		// Push the value type onto the value type stack
		digester.push(VALUE_OBJECT_TYPE_STACK, valueType);
	}

	/**
	 * @see org.apache.commons.digester.Rule#body(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public void body(String namespace, String name, String bodyText)
			throws Exception {
		// Get defined value type, and the class object representing the value
		// type
		String valueType = (String) digester.pop(VALUE_OBJECT_TYPE_STACK);
		Class<?> valueTypeClass = Class.forName(valueType);

		if (digester.getLogger().isDebugEnabled()) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("[CreateValueObjectRule]{");
			logMessage.append(digester.getMatch());
			logMessage.append("} Converting bodyText [");
			logMessage.append(bodyText);
			logMessage.append("] to a [");
			logMessage.append(valueType);
			logMessage.append("]");
			digester.getLogger().debug(logMessage.toString());
		}

		// Create the value object using the body text performing the necessary
		// conversion
		Object valueObject = ConvertUtils.convert(bodyText, valueTypeClass);
		digester.push(valueObject);
	}

	/**
	 * @see org.apache.commons.digester.Rule#end(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void end(String namespace, String name) throws Exception {
		// Pop the value object off the stack.
		if (digester.getLogger().isDebugEnabled()) {
			digester.getLogger().debug(
					"[CreateValueObjectRule] Popping value object");
		}
		digester.pop();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("CreateValueObjectRule[typeAttribute=");
		sb.append(typeAttributeName);
		sb.append("]");
		return sb.toString();
	}
}
