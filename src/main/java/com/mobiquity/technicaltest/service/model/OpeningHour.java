package com.mobiquity.technicaltest.service.model;

public class OpeningHour {
	private int dayOfWeek;
	private Hour[] hours;
	
	public OpeningHour() {
	}
	
	public OpeningHour(int dayOfWeek, Hour[] hours) {
		super();
		this.dayOfWeek = dayOfWeek;
		this.hours = hours;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public Hour[] getHours() {
		return hours;
	}

	public void setHours(Hour[] hours) {
		this.hours = hours;
	}
}
