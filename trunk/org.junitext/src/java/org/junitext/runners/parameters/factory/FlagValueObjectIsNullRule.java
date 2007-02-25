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

import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

/**
 * Digester rule that flags <code>CreateValueObjectRule</code> that it 
 * should create a <code>null</code> value object.
 * <p>
 * This rule should be used within a nested {@link org.junitext.runners.parameters.factory.CreateValueObjectRule}.
 * However, if it is not, then this rule will safely do nothing.
 * </p>
 * 
 * @author Jim Hurne
 */
public class FlagValueObjectIsNullRule extends Rule {
	
	/*
	 * @see org.apache.commons.digester.Rule#begin(java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void begin(String namespace, String name, Attributes attributes) throws Exception {
		
		//If this rule is nested within a CreateValueObjectRule...
		if(digester.peek(CreateValueObjectRule.VALUE_OBJECT_IS_NULL) != null) {
			
			if(digester.getLogger().isDebugEnabled()) {
				StringBuilder sb = new StringBuilder();
				sb.append("[FlagValueObjectIsNullRule]{");
				sb.append(digester.getMatch());
				sb.append("} Flagging CreateValueObjectRule that the value ");
				sb.append("object should be null.");
				digester.getLogger().debug(sb.toString());
			}
			
			//Discard the previous value
			digester.pop(CreateValueObjectRule.VALUE_OBJECT_IS_NULL);
			
			//Set the isNull flag to true
			digester.push(CreateValueObjectRule.VALUE_OBJECT_IS_NULL, true);
		}		
	}
	
	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FlagValueObjectIsNullRule";
	}	
	
}
