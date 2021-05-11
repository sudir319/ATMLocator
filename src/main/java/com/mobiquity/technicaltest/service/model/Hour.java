package com.mobiquity.technicaltest.service.model;

public class Hour {
	private String hourFrom;
	private String hourTo;
	
	public Hour() {
	}
	
	public Hour(String hourFrom, String hourTo) {
		super();
		this.hourFrom = hourFrom;
		this.hourTo = hourTo;
	}

	public String getHourFrom() {
		return hourFrom;
	}

	public void setHourFrom(String hourFrom) {
		this.hourFrom = hourFrom;
	}

	public String getHourTo() {
		return hourTo;
	}

	public void setHourTo(String hourTo) {
		this.hourTo = hourTo;
	}
}