package de.benboecker.kochbuch.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Benni on 19.11.16.
 */

public class CookingStep extends RealmObject {
	@PrimaryKey
	private long id = 0;
	private String description;
	private long number;

	public static CookingStep newCookingStep() {
		CookingStep cookingStep = new CookingStep();
		cookingStep.setId(RealmHelper.getNextID(CookingStep.class, "id"));
		return cookingStep;
	}


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
