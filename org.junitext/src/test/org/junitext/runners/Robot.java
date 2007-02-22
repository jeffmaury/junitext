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
package org.junitext.runners;

import java.util.Arrays;
import java.util.List;

/**
 * Mock bean that is used in the unit tests for the XMLParameterizedRunner.
 * 
 * @author Jim Hurne
 */
public class Robot {
	private String name;
	private long id;
	private String model;
	private String manufacturer;
	private Robot bestFriend;
	private Robot[] arrayOfFriends;
	private String[][] multiDimensionalArrayOfStrings;

	private List<Object> mixedList;

	private List<Robot> friends;
	private List<List<Robot>> listsOfFriends;
	private List<List<List<Robot>>> threeLevelListOfFriends;

	public Robot() {

	}

	public Robot(String name, long id, String model, String manufacturer) {
		this.name = name;
		this.id = id;
		this.model = model;
		this.manufacturer = manufacturer;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Robot> getFriends() {
		return friends;
	}

	public void setFriends(List<Robot> friends) {
		this.friends = friends;
	}

	/**
	 * @return the listsOfFriends
	 */
	public List<List<Robot>> getListsOfFriends() {
		return listsOfFriends;
	}

	/**
	 * @param listsOfFriends
	 *            the listsOfFriends to set
	 */
	public void setListsOfFriends(List<List<Robot>> listsOfFriends) {
		this.listsOfFriends = listsOfFriends;
	}

	/**
	 * @return the threeLevelListOfFriends
	 */
	public List<List<List<Robot>>> getThreeLevelListOfFriends() {
		return threeLevelListOfFriends;
	}

	/**
	 * @param threeLevelListOfFriends
	 *            the threeLevelListOfFriends to set
	 */
	public void setThreeLevelListOfFriends(
			List<List<List<Robot>>> threeLevelListOfFriends) {
		this.threeLevelListOfFriends = threeLevelListOfFriends;
	}

	/**
	 * @return the bestFriend
	 */
	public Robot getBestFriend() {
		return bestFriend;
	}

	/**
	 * @param bestFriend
	 *            the bestFriend to set
	 */
	public void setBestFriend(Robot bestFriend) {
		this.bestFriend = bestFriend;
	}

	/**
	 * @return the mixedList
	 */
	public List<Object> getMixedList() {
		return mixedList;
	}

	/**
	 * @param mixedList
	 *            the mixedList to set
	 */
	public void setMixedList(List<Object> mixedList) {
		this.mixedList = mixedList;
	}

	/**
	 * @return the arrayOfFriends
	 */
	public Robot[] getArrayOfFriends() {
		return arrayOfFriends;
	}

	/**
	 * @param arrayOfFriends
	 *            the arrayOfFriends to set
	 */
	public void setArrayOfFriends(Robot[] arrayOfFriends) {
		this.arrayOfFriends = arrayOfFriends;
	}

	/**
	 * @return the multiDimensionalArrayOfStrings
	 */
	public String[][] getMultiDimensionalArrayOfStrings() {
		return multiDimensionalArrayOfStrings;
	}

	/**
	 * @param multiDimensionalArrayOfStrings
	 *            the multiDimensionalArrayOfStrings to set
	 */
	public void setMultiDimensionalArrayOfStrings(
			String[][] multidimentionalArrayOfStrings) {
		this.multiDimensionalArrayOfStrings = multidimentionalArrayOfStrings;
	}

	@Override
	public String toString() {
		StringBuilder newString = new StringBuilder();
		newString.append("Robot ").append(name).append(": ");
		newString.append("Id: [").append(id).append("] ");
		newString.append("manufacturer: [").append(manufacturer).append("] ");
		newString.append("model: [").append(model).append("] ");
		newString.append("bestFriend: [").append(bestFriend).append("] ");

		newString.append("mixedList: {").append(mixedList).append("} ");
		newString.append("friends: {").append(friends).append("} ");
		newString.append("listsOfFriends: {").append(listsOfFriends).append(
				"} ");
		newString.append("threeLevelListOfFriends: {").append(
				threeLevelListOfFriends).append("} ");
		newString.append("arrayOfFriends: {").append(
				Arrays.toString(arrayOfFriends)).append("} ");

		newString.append("multidementionalArrayOfStrings: {");
		newString.append(Arrays.deepToString(multiDimensionalArrayOfStrings));
		newString.append("} ");

		return newString.toString();
	}
}
