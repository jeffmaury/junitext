package org.junitext.runners;

import java.util.List;

public class Robot {
	private String name;
	private long id;
	private String model;
	private String manufacturer;
	private List<Robot> friends;
	
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
	
	@Override
	public String toString() {
		StringBuffer newString = new StringBuffer();
		newString.append("Robot ").append(name).append(": ");
		newString.append("Id: [").append(id).append("] ");
		newString.append("manufacturer: [").append(manufacturer).append("] ");
		newString.append("model: [").append(model).append("] ");
		if(friends != null) {
			newString.append("\n\tfriends: ");
			for(Robot friend: friends) {
				newString.append("\n\t[").append(friend.toString()).append("] ");
			}
			newString.append(" ");
		}
		
		return newString.toString();
	}

	public List<Robot> getFriends() {
		return friends;
	}

	public void setFriends(List<Robot> friends) {
		this.friends = friends;
	}
}
