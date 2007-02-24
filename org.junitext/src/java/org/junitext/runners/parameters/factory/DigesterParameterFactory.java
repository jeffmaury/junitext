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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
	public List<ParameterSet> createParameters(Class<?> testClass, File xmlFile)
			throws Exception {

		InputStream xmlInput = new FileInputStream(xmlFile);
		return createParameters(xmlInput);
	}

	/**
	 * Creates sets of test parameters by parsing input XML using the Jakarta
	 * commons-digester.
	 * 
	 * @param xmlInput
	 *            an <code>InputStream</code> containing the XML to parse
	 * @return the sets of test parameters
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<ParameterSet> createParameters(InputStream xmlInput)
			throws Exception {
		// Step 1 - Create the digester
		Digester digester = new Digester();
		digester.setValidating(false);

		// Step 2 - Register rules

		registerBaseRules(digester);
		registerValueObjectRules(digester);
		registerCollectionRules(digester);

		// Step 3 - Run the digester against the given input file
		List<List<?>> generatedParmSets = (List<List<?>>) digester
				.parse(xmlInput);

		// Step 4- Convert our list of lists to a list of object arrays
		List<ParameterSet> returnParmSet = new ArrayList<ParameterSet>();
		for (List<?> parmSet : generatedParmSets) {
			returnParmSet.add(new ParameterSet(null, parmSet.toArray()));
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

		// -- Basic Bean Rules

		// Create an object for any "beans" that are encountered
		// We use the wildcard so that any bean tag at any nesting level is
		// handled
		digester.addObjectCreate("*/bean", "java.lang.Object", "class");

		// Set the properties on the beans based on the
		// <property name="" value="" > tag
		digester.addSetProperty("*/bean/property", "name", "value");

		// Add an object set previously with the CallParamRule to the bean
		// property. This rule is used by the collections as well as properties
		// that expect beans
		SetPropertyWithParameterRule setPropertyWithObject = new SetPropertyWithParameterRule(
				"name");
		digester.addRule("*/bean/property", setPropertyWithObject);

		// Add the bean to the parameter set (or to a <list>)
		digester.addSetNext("*/bean", "add");

		// --Rules for bean-based properties

		// Create beans that are associated with properties
		// We have to do this becuase the more-specific pattern overrides
		// the less specific "*/bean" pattern.
		digester.addObjectCreate("*/bean/property/bean", "java.lang.Object",
				"class");

		// Add nested beans as a parameter so that they can be
		// added to the properties of a parent bean
		digester.addCallParam("*/bean/property/bean", 0, true);
	}

	private void registerValueObjectRules(Digester digester) {
		// All of the basic object rules work the same. If we encounter
		// a basic object, then record the "value" of the basic object
		// as a parameter that is used to call the add method on the current
		// <test> list.

		// -- Rules for Strings
		digester.addCallMethod("tests/test/string", "add", 1,
				new Class[] { String.class });
		digester.addCallParam("tests/test/string", 0, "value");

		// -- Rules for Integers
		digester.addCallMethod("tests/test/int", "add", 1,
				new Class[] { Integer.class });
		digester.addCallParam("tests/test/int", 0, "value");

		// -- Rules for the Shorts
		digester.addCallMethod("tests/test/short", "add", 1,
				new Class[] { Short.class });
		digester.addCallParam("tests/test/short", 0, "value");

		// -- Rules for the Longs
		digester.addCallMethod("tests/test/long", "add", 1,
				new Class[] { Long.class });
		digester.addCallParam("tests/test/long", 0, "value");

		// -- Rules for the Booleans
		digester.addCallMethod("tests/test/boolean", "add", 1,
				new Class[] { Boolean.class });
		digester.addCallParam("tests/test/boolean", 0, "value");
	}

	private void registerCollectionRules(Digester digester) {
		registerListRules(digester);
		registerSetRules(digester);
		registerMapRules(digester);
	}

	private void registerListRules(Digester digester) {
		// NOTE: Most of the rules for lists are already defined under
		// registerBaseRules. This is because the <tests> element is also
		// a list, so the general rules there also get applied for lists as
		// bean properties.

		// -- Rules for List Processing
		// Create an ArrayList for each <list> element
		digester.addObjectCreate("*/list", ArrayList.class);

		// Add the newly created ArrayLists as a parameter so that it can be
		// added to the properties of the parent bean (or added to another
		// collection)
		digester.addCallParam("*/list", 0, true);

		// -- Rules for basic values nested under lists
		digester.addCallMethod("*/list/value", "add", 1);
		digester.addCallParam("*/list/value", 0);

		// -- Rules for lists nested under lists
		// Create an ArrayList for each nested <list> element
		digester.addObjectCreate("*/list/list", ArrayList.class);
		// If the list is nested within another list, add it to the parent list
		digester.addSetNext("*/list/list", "add");

		// -- Rules for sets nested under lists
		// Create an HashSet for each nested <set> element
		digester.addObjectCreate("*/list/set", HashSet.class);
		// If the set is nested within a list, add it to the parent list
		digester.addSetNext("*/list/set", "add");

		// -- Rules for maps nested under lists
		// Create an HashSet for each nested <map> element
		digester.addObjectCreate("*/list/map", HashMap.class);
		// If the map is nested within a list, add it to the parent list
		digester.addSetNext("*/list/map", "add");
	}

	private void registerSetRules(Digester digester) {
		// NOTE: Most of the rules for sets are already defined under
		// registerBaseRules. This is because the <tests> element is also
		// a list, and list and sets have similar method names,
		// so the general rules there also get applied for sets as
		// bean properties.

		// -- Rules for Set Processing
		// Create a HashSet for each <set> element
		digester.addObjectCreate("*/set", HashSet.class);

		// Add the newly created Set as a parameter so that it can be
		// added to the properties of the parent bean (or added to another
		// collection)
		digester.addCallParam("*/set", 0, true);

		// -- Rules for basic values nested under sets
		digester.addCallMethod("*/set/value", "add", 1);
		digester.addCallParam("*/set/value", 0);

		// -- Rules for sets nested under sets
		// Create a HashSet for each nested <set> element
		digester.addObjectCreate("*/set/set", HashSet.class);
		// If the set is nested within another set, add it to the parent set
		digester.addSetNext("*/set/set", "add");

		// -- Rules for lists nested under sets
		// Create an ArrayList for each nested <list> element
		digester.addObjectCreate("*/set/list", ArrayList.class);
		// If the set is nested within a set, add it to the parent set
		digester.addSetNext("*/set/list", "add");

		// -- Rules for maps nested under sets
		// Create an HashMap for each nested <map> element
		digester.addObjectCreate("*/set/map", HashMap.class);
		// If the map is nested within a set, add it to the parent set
		digester.addSetNext("*/set/map", "add");

	}

	private void registerMapRules(Digester digester) {
		// -- Base Map Rules
		digester.addObjectCreate("*/map", HashMap.class);

		// Add the newly created Map as a parameter so that it can be
		// added to the properties of the parent bean (or added to another
		// collection)
		digester.addCallParam("*/map", 0, true);

		// -- Entry rules
		digester.addCallMethod("*/map/entry", "put", 2);

		// -- Key rules
		// Handle cases where the key is specified as an attribute on the entry
		digester.addCallParam("*/map/entry", 0, "key");
		// Handle casses where the key is specified as a child element on the
		// entry
		digester.addCallParam("*/map/entry/key/value", 0);

		// Override some of the bean rules, as we want to do something different
		// here
		digester.addObjectCreate("*/map/entry/key/bean", "java.lang.Object",
				"class");

		// Add the bean to be used as the key for the current map entry
		digester.addCallParam("*/map/entry/key/bean", 0, true);

		// -- Value rules
		// Handle cases where the value is specified as an attribute on the
		// entry
		digester.addCallParam("*/map/entry", 1, "value");
		// Handle cases where the valie is a string and is specified as an
		// element
		digester.addCallParam("*/map/entry/value", 1);

		// Override some of the bean rules, as we want to do something different
		// here
		digester.addObjectCreate("*/map/entry/bean", "java.lang.Object",
				"class");

		// Add beans as parameters for value objects
		digester.addCallParam("*/map/entry/bean", 1, true);

		// -- Maps nested in Entry rules
		// Create a HashMap for each nested <map> element
		digester.addObjectCreate("*/map/entry/map", HashMap.class);
		// If the map is nested within an entry of a map, treat it as the
		// value of the entry
		digester.addCallParam("*/map/entry/map", 1, true);

		// -- Lists nested in Entry rules
		// Create an ArrayList for each nested <list> element
		digester.addObjectCreate("*/map/entry/list", ArrayList.class);
		// If the list is nested within an entry of a map, treat it as the
		// value of the entry
		digester.addCallParam("*/map/entry/list", 1, true);

		// -- Sets nested in Entry rules
		// Create an ArrayList for each nested <list> element
		digester.addObjectCreate("*/map/entry/set", HashSet.class);
		// If the set is nested within an entry of a map, treat it as the
		// value of the entry
		digester.addCallParam("*/map/entry/set", 1, true);
	}
}
