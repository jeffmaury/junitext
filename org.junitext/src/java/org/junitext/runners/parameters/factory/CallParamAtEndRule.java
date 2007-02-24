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

import org.apache.commons.digester.CallParamRule;
import org.xml.sax.Attributes;

/**
 * Adds a parameter to the parameter stack when the <strong>end</strong>
 * element is encountered. This rule is identical to the
 * <code>CallParamRule</code> except that all processing is done when the
 * <code>end</code> element is encountered.
 * 
 * @author Jim Hurne
 */
public class CallParamAtEndRule extends CallParamRule {

	private static final String ATTRIBUTES_STACK = "org.junitext.runners.parameters.factory."
			+ "CallParamAtEndRule.attributes";

	private static final String BODY_TEXT_STACK = "org.junitext.runners.parameters.factory."
			+ "CallParamAtEndRule.bodyText";

	/**
	 * Construct a "call parameter at end" rule that will save the body text of
	 * this element as the parameter value.
	 * <p>
	 * Note that if the element is empty the an empty string is passed to the
	 * target method, not null. And if automatic type conversion is being
	 * applied (ie if the target function takes something other than a string as
	 * a parameter) then the conversion will fail if the converter class does
	 * not accept an empty string as valid input.
	 * </p>
	 * 
	 * @param paramIndex
	 *            The zero-relative parameter number
	 * @see org.apache.commons.digester.CallParamRule#CallParamRule(int)
	 */
	public CallParamAtEndRule(int paramIndex) {
		super(paramIndex);
	}

	/**
	 * Construct a "call parameter at end" rule.
	 * 
	 * @param paramIndex
	 *            The zero-relative parameter number
	 * @param fromStack
	 *            should this parameter be taken from the top of the stack?
	 * @see org.apache.commons.digester.CallParamRule#CallParamRule(int,
	 *      boolean)
	 */
	public CallParamAtEndRule(int paramIndex, boolean fromStack) {
		super(paramIndex, fromStack);
	}

	/**
	 * Constructs a "call parameter at end" rule which sets a parameter from the
	 * stack. If the stack contains too few objects, then the parameter will be
	 * set to null.
	 * 
	 * @param paramIndex
	 *            The zero-relative parameter number
	 * @param stackIndex
	 *            the index of the object which will be passed as a parameter.
	 *            The zeroth object is the top of the stack, 1 is the next
	 *            object down and so on.
	 * @see org.apache.commons.digester.CallParamRule#CallParamRule(int, int)
	 */
	public CallParamAtEndRule(int paramIndex, int stackIndex) {
		super(paramIndex, stackIndex);
	}

	/**
	 * Construct a "call parameter" rule that will save the value of the
	 * specified attribute as the parameter value.
	 * 
	 * @param paramIndex
	 *            The zero-relative parameter number
	 * @param attributeName
	 *            The name of the attribute to save
	 * @see org.apache.commons.digester.CallParamRule#CallParamRule(int, String)
	 */
	public CallParamAtEndRule(int paramIndex, String attributeName) {
		super(paramIndex, attributeName);
	}

	/**
	 * Saves the attributes to be used when the end is called.
	 * 
	 * @param attributes
	 *            the attributes for the current element
	 * @see org.apache.commons.digester.CallParamRule#begin(org.xml.sax.Attributes)
	 */
	@Override
	public void begin(Attributes attributes) throws Exception {
		// Save the attributes for later
		digester.push(ATTRIBUTES_STACK, attributes);
	}

	/**
	 * Saves the body text for use when processing is performed when end is
	 * called.
	 * 
	 * @param bodyText
	 *            the body text to save
	 * @see org.apache.commons.digester.CallParamRule#body(java.lang.String)
	 */
	@Override
	public void body(String bodyText) throws Exception {
		// Save body text for later use
		digester.push(BODY_TEXT_STACK, bodyText);
	}

	/**
	 * Performs <code>CallParamRule</code> processing.
	 * 
	 * @param namespace
	 *            the namespace of the element
	 * @param name
	 *            the name of the element
	 * @see org.apache.commons.digester.CallParamRule#end(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void end(String namespace, String name) {
		// Get the element attributes and body text
		Attributes attributes = (Attributes) digester.pop(ATTRIBUTES_STACK);
		String bodyText = (String) digester.pop(BODY_TEXT_STACK);

		// Call all of the CallParamRule methods
		try {
			super.begin(attributes);
			super.body(bodyText);
			super.end(namespace, name);
		} catch (Exception e) {
			// Sigh...
			throw new RuntimeException(e);
		}
	}

	/**
	 * Prints a string representation of this rule.
	 * 
	 * @see org.apache.commons.digester.CallParamRule#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("CallParamAtEndRule[");
		sb.append(super.toString());
		sb.append("]");
		return sb.toString();
	}

}
