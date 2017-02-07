package de.benboecker.kochbuch.model;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;

import io.realm.Realm;
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
	private boolean isVegetarian = false;
	private boolean isVegan = false;

	private int numberOfPersons = 0;
	private String imageFileName = "";

	RealmList<Ingredient> ingredients;
	RealmList<CookingStep> steps;
	RealmList<Tag> tags;

	public static Recipe newRecipe() {
		Recipe recipe = null;

		Realm realm = Realm.getDefaultInstance();
		realm.beginTransaction();
		recipe = realm.createObject(Recipe.class, RealmHelper.getNextID(Recipe.class, "id"));
		realm.commitTransaction();

		return recipe;
	}

	public Bitmap getImage(Activity activity) {

		Bitmap bitmap = null;

		try {
			File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

			File imageFile = new File(storageDir + imageFileName);
			Uri uri = Uri.fromFile(imageFile);
			bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;
	}

	public String getShareString() {
		String ingredientList = name + "\n";

		for (Ingredient ingredient : ingredients) {
			ingredientList += "- " + ingredient.getName() + " (" + ingredient.getQuantity() + " " + ingredient.getUnit() + ")\n";
		}

		return ingredientList;
	}

	public void setNumberOfPersons(int numberOfPersons) {
		if (this.numberOfPersons == 0) {
			this.numberOfPersons = numberOfPersons;
			return;
		}

		for (Ingredient ingredient : ingredients) {
			float oldQuantity = ingredient.getQuantity();
			float oldPersons = this.getNumberOfPersons();
			float quotient = oldQuantity / oldPersons;
			float newQuantity = quotient * numberOfPersons;
			ingredient.setQuantity((long)newQuantity);
		}

		this.numberOfPersons = numberOfPersons;
	}


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
	public int getNumberOfPersons() {
		return numberOfPersons;
	}
	public RealmList<Ingredient> getIngredients() {
		return ingredients;
	}
	public void setIngredients(RealmList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public boolean isVegetarian() {
		return isVegetarian;
	}

	public void setVegetarian(boolean vegetarian) {
		isVegetarian = vegetarian;
	}

	public boolean isVegan() {
		return isVegan;
	}

	public void setVegan(boolean vegan) {
		isVegan = vegan;
	}
}
