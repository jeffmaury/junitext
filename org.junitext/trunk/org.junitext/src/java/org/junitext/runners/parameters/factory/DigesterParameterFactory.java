/**
 * Copyright (C) 2006-2007, Jochen Hiller.
 */
package org.junitext.runners.parameters.factory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester.Digester;

/**
 * @author Jim Hurne
 * 
 */
public class DigesterParameterFactory implements ParameterFactory {

	/**
	 * @see org.junitext.runners.parameters.factory.ParameterFactory#createParameters(java.lang.Class,
	 *      java.io.File)
	 */
	public List<Object[]> createParameters(Class<?> testClass, File xmlFile)
			throws Exception {

		// Step 1 - Create the digester
		Digester digester = new Digester();
		digester.setValidating(false);

		// Step 2 - Register rules

		// Add a list for the top level <tests> element
		digester.addObjectCreate("tests", java.util.ArrayList.class);

		// Add a list for each <test> element
		digester.addObjectCreate("tests/test", java.util.ArrayList.class);

		// Add the <test> list to the top level <tests> list
		digester.addSetNext("tests/test", "add");

		// Create an object for any "beans" that are encountered
		// We use the wildcard so that any bean tag at any nesting level is
		// handled
		digester.addObjectCreate("*/bean", "java.lang.Object", "class");

		// Set the properties on the beans based on the 
		// <property name="" value="" > tag
		digester.addSetProperty("*/bean/property", "name", "value");

		// Add the bean to the parameter set
		digester.addSetNext("*/bean", "add");

		// Create an ArrayList for each nested <list> element
		digester.addObjectCreate("*/bean/property/list", ArrayList.class);
		digester.addCallParam("*/bean/property/list", 0, true);

		// Add the property list to the bean
		// digester.addSetNext("*/bean/property/list", "setFriends");
		SetPropertyWithParameterRule setPropertyWithObject = new SetPropertyWithParameterRule(
				"name");
		digester.addRule("*/bean/property", setPropertyWithObject);

		// Step 3 - Run the digester against the given input file
		List<List> generatedParmSets = (List<List>) digester.parse(xmlFile);

		// Step 4- Convert our list of lists to a list of object arrays
		List<Object[]> returnParmSet = new ArrayList<Object[]>();
		for (List parmSet : generatedParmSets) {
			returnParmSet.add(parmSet.toArray());
		}

		return returnParmSet;
	}

}
