/**
 * Copyright (C) 2006-2007, Jochen Hiller.
 */
package org.junitext.runners.parameters.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester.Digester;

/**
 * Creates sets of test parameters by parsing input XML using the Jakarta 
 * commons-digester.
 * 
 * @author Jim Hurne
 */
public class DigesterParameterFactory implements ParameterFactory {

	/**
	 * @see org.junitext.runners.parameters.factory.ParameterFactory#createParameters(java.lang.Class,
	 *      java.io.File)
	 */
	public List<Object[]> createParameters(Class<?> testClass, File xmlFile)
			throws Exception {
		
		InputStream xmlInput = new FileInputStream(xmlFile);
		return createParameters(xmlInput);
	}
	
	/**
	 * Creates sets of test parameters by parsing input XML using the Jakarta 
	 * commons-digester.
	 * 
	 * @param xmlInput an <code>InputStream</code> containing the XML to parse
	 * @return the sets of test parameters
	 * @throws Exception
	 */
	public List<Object[]> createParameters(InputStream xmlInput) throws Exception {
		// Step 1 - Create the digester
		Digester digester = new Digester();
		digester.setValidating(false);

		// Step 2 - Register rules

		registerBaseRules(digester);
		registerPrimativeRules(digester);
		registerCollectionRules(digester);
		

		// Step 3 - Run the digester against the given input file
		List<List> generatedParmSets = (List<List>) digester.parse(xmlInput);

		// Step 4- Convert our list of lists to a list of object arrays
		List<Object[]> returnParmSet = new ArrayList<Object[]>();
		for (List parmSet : generatedParmSets) {
			returnParmSet.add(parmSet.toArray());
		}

		return returnParmSet;		
	}
	
	private void registerBaseRules(Digester digester) {
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
	}
	
	private void registerPrimativeRules(Digester digester) {
		//-- Rules for Strings
		digester.addCallMethod("tests/test/string", "add", 1, new Class[] { String.class });
		digester.addCallParam("tests/test/string", 0, "value");
		
		//-- Rules for Integer primatives
		digester.addCallMethod("tests/test/int", "add", 1, new Class[] { Integer.class });
		digester.addCallParam("tests/test/int", 0, "value");

		//-- Rules for the Short primative
		digester.addCallMethod("tests/test/short", "add", 1, new Class[] { Short.class });
		digester.addCallParam("tests/test/short", 0, "value");

		//-- Rules for the Long primative
		digester.addCallMethod("tests/test/long", "add", 1, new Class[] { Long.class });
		digester.addCallParam("tests/test/long", 0, "value");
		
		//-- Rules for the Boolean primative
		digester.addCallMethod("tests/test/boolean", "add", 1, new Class[] { Boolean.class });
		digester.addCallParam("tests/test/boolean", 0, "value");	
	}
	
	private void registerCollectionRules(Digester digester) {
		//-- Rules for List Processing
		// Create an ArrayList for each nested <list> element
		digester.addObjectCreate("*/bean/property/list", ArrayList.class);
		
		// Add a created ArrayList as a parameter so that it can be added
		// to the properties of the parent bean.
		digester.addCallParam("*/bean/property/list", 0, true);

		// Add the object (whatever it happens to be) set previously with 
		// the CallParamRule to the bean property
		SetPropertyWithParameterRule setPropertyWithObject = new SetPropertyWithParameterRule(
				"name");
		digester.addRule("*/bean/property", setPropertyWithObject);
	}

}
