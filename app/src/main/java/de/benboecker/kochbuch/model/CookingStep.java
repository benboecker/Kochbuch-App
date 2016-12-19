package de.benboecker.kochbuch.model;

import io.realm.RealmObject;

/**
 * Created by Benni on 19.11.16.
 */

public class CookingStep extends RealmObject {
	private long id = 0;
	private String description;
	private long number;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String name) {
		this.description = name;
	}
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}
}
