package de.benboecker.kochbuch.model;

/**
 * Created by Benni on 19.11.16.
 */

public class Recipe {
	private long id = 0;
	private String name = "";
	private long time = 0;
	private boolean isFavorite = false;
	private Integer defaultNumberOfPersons = 0;

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

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public boolean isFavorite() {
		return isFavorite;
	}

	public void setFavorite(boolean favorite) {
		isFavorite = favorite;
	}

	public Integer getDefaultNumberOfPersons() {
		return defaultNumberOfPersons;
	}

	public void setDefaultNumberOfPersons(Integer defaultNumberOfPersons) {
		this.defaultNumberOfPersons = defaultNumberOfPersons;
	}

	@Override
	public String toString() {
		return this.getName();
	}
}
