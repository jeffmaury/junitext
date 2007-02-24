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
import java.util.Map;
import java.util.Set;

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

	private Map<String, Robot> mapOfFriends;
	private Map<Robot, Robot> mapWithRobotKey;
	private Map<String, String> mapWithStringKeyAndValue;
	private Map<String, List<Robot>> mapWithListsOfRobots;
	private Map<Object, Object> mixedMap;

	private Set<Robot> setOfFriends;
	private Set<Set<Robot>> setOfSets;
	private Set<Set<Set<Robot>>> threeLevelSetofFriends;
	private Set<Object> mixedSet;

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

	/**
	 * @return the mapOfFriends
	 */
	public Map<String, Robot> getMapOfFriends() {
		return mapOfFriends;
	}

	/**
	 * @param mapOfFriends
	 *            the mapOfFriends to set
	 */
	public void setMapOfFriends(Map<String, Robot> mapOfFriends) {
		this.mapOfFriends = mapOfFriends;
	}

	/**
	 * @return the mapWithRobotKey
	 */
	public Map<Robot, Robot> getMapWithRobotKey() {
		return mapWithRobotKey;
	}

	/**
	 * @param mapWithRobotKey
	 *            the mapWithRobotKey to set
	 */
	public void setMapWithRobotKey(Map<Robot, Robot> mapWithRobotKey) {
		this.mapWithRobotKey = mapWithRobotKey;
	}

	/**
	 * @return the mapWithStringKeyAndValue
	 */
	public Map<String, String> getMapWithStringKeyAndValue() {
		return mapWithStringKeyAndValue;
	}

	/**
	 * @param mapWithStringKeyAndValue
	 *            the mapWithStringKeyAndValue to set
	 */
	public void setMapWithStringKeyAndValue(Map<String, String> mapWithString) {
		this.mapWithStringKeyAndValue = mapWithString;
	}

	/**
	 * @return the mapWithListsOfRobots
	 */
	public Map<String, List<Robot>> getMapWithListsOfRobots() {
		return mapWithListsOfRobots;
	}

	/**
	 * @param mapWithListsOfRobots
	 *            the mapWithListsOfRobots to set
	 */
	public void setMapWithListsOfRobots(
			Map<String, List<Robot>> mapWithListsOfRobots) {
		this.mapWithListsOfRobots = mapWithListsOfRobots;
	}

	/**
	 * @return the mixedMap
	 */
	public Map<Object, Object> getMixedMap() {
		return mixedMap;
	}

	/**
	 * @param mixedMap
	 *            the mixedMap to set
	 */
	public void setMixedMap(Map<Object, Object> mixedMap) {
		this.mixedMap = mixedMap;
	}

	/**
	 * @return the setOfFriends
	 */
	public Set<Robot> getSetOfFriends() {
		return setOfFriends;
	}

	/**
	 * @param setOfFriends
	 *            the setOfFriends to set
	 */
	public void setSetOfFriends(Set<Robot> setOfFriends) {
		this.setOfFriends = setOfFriends;
	}

	/**
	 * @return the mixedSet
	 */
	public Set<Object> getMixedSet() {
		return mixedSet;
	}

	/**
	 * @param mixedSet
	 *            the mixedSet to set
	 */
	public void setMixedSet(Set<Object> mixedSet) {
		this.mixedSet = mixedSet;
	}

	/**
	 * @return the setOfSets
	 */
	public Set<Set<Robot>> getSetOfSets() {
		return setOfSets;
	}

	/**
	 * @param setOfSets
	 *            the setOfSets to set
	 */
	public void setSetOfSets(Set<Set<Robot>> setofSets) {
		this.setOfSets = setofSets;
	}

	/**
	 * @return the threeLevelSetofFriends
	 */
	public Set<Set<Set<Robot>>> getThreeLevelSetofFriends() {
		return threeLevelSetofFriends;
	}

	/**
	 * @param threeLevelSetofFriends
	 *            the threeLevelSetofFriends to set
	 */
	public void setThreeLevelSetofFriends(
			Set<Set<Set<Robot>>> threeLevelSetofFriends) {
		this.threeLevelSetofFriends = threeLevelSetofFriends;
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

		newString.append(" mapOfFriends: [");
		newString.append(mapOfFriends);
		newString.append("] ");

		newString.append(" mapWithRobotKey: [");
		newString.append(mapWithRobotKey);
		newString.append("] ");

		newString.append(" mapWithStringKeyAndValue: [");
		newString.append(mapWithStringKeyAndValue);
		newString.append("] ");

		newString.append(" mapWithListsOfRobots: [");
		newString.append(mapWithListsOfRobots);
		newString.append("] ");

		newString.append(" mixedMap: [");
		newString.append(mixedMap);
		newString.append("] ");

		newString.append(" setOfFriends: [");
		newString.append(setOfFriends);
		newString.append("] ");

		newString.append(" setOfSets: [");
		newString.append(setOfSets);
		newString.append("] ");

		newString.append(" threeLevelSetofFriends: [");
		newString.append(threeLevelSetofFriends);
		newString.append("] ");

		newString.append(" mixedSet: [");
		newString.append(mixedSet);
		newString.append("] ");

		return newString.toString();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + Arrays.hashCode(arrayOfFriends);
		result = PRIME * result
				+ ((bestFriend == null) ? 0 : bestFriend.hashCode());
		result = PRIME * result + ((friends == null) ? 0 : friends.hashCode());
		result = PRIME * result + (int) (id ^ (id >>> 32));
		result = PRIME * result
				+ ((listsOfFriends == null) ? 0 : listsOfFriends.hashCode());
		result = PRIME * result
				+ ((manufacturer == null) ? 0 : manufacturer.hashCode());
		result = PRIME * result
				+ ((mapOfFriends == null) ? 0 : mapOfFriends.hashCode());
		result = PRIME
				* result
				+ ((mapWithListsOfRobots == null) ? 0 : mapWithListsOfRobots
						.hashCode());
		result = PRIME * result
				+ ((mapWithRobotKey == null) ? 0 : mapWithRobotKey.hashCode());
		result = PRIME
				* result
				+ ((mapWithStringKeyAndValue == null) ? 0
						: mapWithStringKeyAndValue.hashCode());
		result = PRIME * result
				+ ((mixedList == null) ? 0 : mixedList.hashCode());
		result = PRIME * result
				+ ((mixedMap == null) ? 0 : mixedMap.hashCode());
		result = PRIME * result
				+ ((mixedSet == null) ? 0 : mixedSet.hashCode());
		result = PRIME * result + ((model == null) ? 0 : model.hashCode());
		result = PRIME * result
				+ Arrays.hashCode(multiDimensionalArrayOfStrings);
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		result = PRIME * result
				+ ((setOfFriends == null) ? 0 : setOfFriends.hashCode());
		result = PRIME * result
				+ ((setOfSets == null) ? 0 : setOfSets.hashCode());
		result = PRIME
				* result
				+ ((threeLevelListOfFriends == null) ? 0
						: threeLevelListOfFriends.hashCode());
		result = PRIME
				* result
				+ ((threeLevelSetofFriends == null) ? 0
						: threeLevelSetofFriends.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Robot other = (Robot) obj;
		if (!Arrays.equals(arrayOfFriends, other.arrayOfFriends))
			return false;
		if (bestFriend == null) {
			if (other.bestFriend != null)
				return false;
		} else if (!bestFriend.equals(other.bestFriend))
			return false;
		if (friends == null) {
			if (other.friends != null)
				return false;
		} else if (!friends.equals(other.friends))
			return false;
		if (id != other.id)
			return false;
		if (listsOfFriends == null) {
			if (other.listsOfFriends != null)
				return false;
		} else if (!listsOfFriends.equals(other.listsOfFriends))
			return false;
		if (manufacturer == null) {
			if (other.manufacturer != null)
				return false;
		} else if (!manufacturer.equals(other.manufacturer))
			return false;
		if (mapOfFriends == null) {
			if (other.mapOfFriends != null)
				return false;
		} else if (!mapOfFriends.equals(other.mapOfFriends))
			return false;
		if (mapWithListsOfRobots == null) {
			if (other.mapWithListsOfRobots != null)
				return false;
		} else if (!mapWithListsOfRobots.equals(other.mapWithListsOfRobots))
			return false;
		if (mapWithRobotKey == null) {
			if (other.mapWithRobotKey != null)
				return false;
		} else if (!mapWithRobotKey.equals(other.mapWithRobotKey))
			return false;
		if (mapWithStringKeyAndValue == null) {
			if (other.mapWithStringKeyAndValue != null)
				return false;
		} else if (!mapWithStringKeyAndValue
				.equals(other.mapWithStringKeyAndValue))
			return false;
		if (mixedList == null) {
			if (other.mixedList != null)
				return false;
		} else if (!mixedList.equals(other.mixedList))
			return false;
		if (mixedMap == null) {
			if (other.mixedMap != null)
				return false;
		} else if (!mixedMap.equals(other.mixedMap))
			return false;
		if (mixedSet == null) {
			if (other.mixedSet != null)
				return false;
		} else if (!mixedSet.equals(other.mixedSet))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (!Arrays.equals(multiDimensionalArrayOfStrings,
				other.multiDimensionalArrayOfStrings))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (setOfFriends == null) {
			if (other.setOfFriends != null)
				return false;
		} else if (!setOfFriends.equals(other.setOfFriends))
			return false;
		if (setOfSets == null) {
			if (other.setOfSets != null)
				return false;
		} else if (!setOfSets.equals(other.setOfSets))
			return false;
		if (threeLevelListOfFriends == null) {
			if (other.threeLevelListOfFriends != null)
				return false;
		} else if (!threeLevelListOfFriends
				.equals(other.threeLevelListOfFriends))
			return false;
		if (threeLevelSetofFriends == null) {
			if (other.threeLevelSetofFriends != null)
				return false;
		} else if (!threeLevelSetofFriends.equals(other.threeLevelSetofFriends))
			return false;
		return true;
	}
}
