package de.benboecker.kochbuch.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Benni on 19.11.16.
 */

public class Recipe extends RealmObject{
	@PrimaryKey
	private long id = 0;
	private String name = "";
	private long time = 0;
	private boolean isFavorite = false;
	private int defaultNumberOfPersons = 0;
	RealmList<Ingredient> ingredients;
	RealmList<CookingStep> steps;
	RealmList<Tag> tags;


	public RealmList<CookingStep> getSteps() {
		return steps;
	}
	public void setSteps(RealmList<CookingStep> steps) {
		this.steps = steps;
	}
	public RealmList<Tag> getTags() {
		return tags;
	}
	public void setTags(RealmList<Tag> tags) {
		this.tags = tags;
	}
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
	public int getDefaultNumberOfPersons() {
		return defaultNumberOfPersons;
	}
	public void setDefaultNumberOfPersons(int defaultNumberOfPersons) {
		this.defaultNumberOfPersons = defaultNumberOfPersons;
	}
	public RealmList<Ingredient> getIngredients() {
		return ingredients;
	}
	public void setIngredients(RealmList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}


}
