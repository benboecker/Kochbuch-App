package de.benboecker.kochbuch.model;

/**
 * Created by Benni on 19.11.16.
 */

public class CookingStep {
	private long id = 0;
	private String description;

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
}
