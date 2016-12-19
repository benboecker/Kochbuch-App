package de.benboecker.kochbuch.model;

import io.realm.RealmObject;

/**
 * Created by Benni on 19.11.16.
 */

public class Tag extends RealmObject {
	private long id = 0;
	private String name = "";



	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
