package com.mobiquity.technicaltest.service.model;

public class Atm 
{
	private float distance;
	private String type;
	private String functionality;
	private Address address;
	private OpeningHour[] openingHours;
	
	public Atm() {
	}
	
	public Atm(float distance, String type, String functionality, Address address, OpeningHour[] openingHours) {
		super();
		this.distance = distance;
		this.type = type;
		this.functionality = functionality;
		this.address = address;
		this.openingHours = openingHours;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFunctionality() {
		return functionality;
	}

	public void setFunctionality(String functionality) {
		this.functionality = functionality;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public OpeningHour[] getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(OpeningHour[] openingHours) {
		this.openingHours = openingHours;
	}
}
