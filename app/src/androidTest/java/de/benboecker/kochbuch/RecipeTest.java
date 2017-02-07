package de.benboecker.kochbuch;

import android.support.test.runner.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.benboecker.kochbuch.model.Ingredient;
import de.benboecker.kochbuch.model.Recipe;
import io.realm.Realm;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Benni on 06.02.17.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeTest {


	@BeforeClass
	public static void deleteRealm() {
		Realm realm = Realm.getDefaultInstance();
		realm.beginTransaction();
		realm.deleteAll();
		realm.commitTransaction();
	}

	@Test
	public void recipeShareString() {
		Realm realm = Realm.getDefaultInstance();

		Recipe recipe = Recipe.newRecipe();

		realm.beginTransaction();
		recipe.setName("Nudeln mit Tomaten");

		Ingredient ingredientOne = Ingredient.newIngredient();
		ingredientOne.setName("Nudeln");
		ingredientOne.setUnit("Gramm");
		ingredientOne.setQuantity(200);
		realm.copyToRealmOrUpdate(ingredientOne);

		Ingredient ingredientTwo = Ingredient.newIngredient();
		ingredientTwo.setName("Passierte Tomaten");
		ingredientTwo.setUnit("Milliliter");
		ingredientTwo.setQuantity(250);
		realm.copyToRealmOrUpdate(ingredientTwo);

		recipe.getIngredients().add(ingredientOne);
		recipe.getIngredients().add(ingredientTwo);
		realm.commitTransaction();

		String expected = "Nudeln mit Tomaten\n- Nudeln (200 Gramm)\n- Passierte Tomaten (250 Milliliter)\n";
		String actual = recipe.getShareString();
		assertEquals(expected, actual);
	}

	@Test
	public void adjustPersons() {
		Realm realm = Realm.getDefaultInstance();

		Recipe recipe = Recipe.newRecipe();

		realm.beginTransaction();
		recipe.setName("Nudeln mit Tomaten");
		recipe.setNumberOfPersons(4);

		Ingredient ingredientOne = Ingredient.newIngredient();
		ingredientOne.setName("Nudeln");
		ingredientOne.setUnit("Gramm");
		ingredientOne.setQuantity(200);
		realm.copyToRealmOrUpdate(ingredientOne);

		recipe.getIngredients().add(ingredientOne);

		recipe.setNumberOfPersons(5);

		realm.commitTransaction();

		assertEquals(recipe.getIngredients().get(0).getQuantity(), 250);
	}

	@Test
	public void adjustPersons2() {
		Realm realm = Realm.getDefaultInstance();

		Recipe recipe = Recipe.newRecipe();

		realm.beginTransaction();
		recipe.setName("Nudeln mit Tomaten");

		Ingredient ingredientOne = Ingredient.newIngredient();
		ingredientOne.setName("Nudeln");
		ingredientOne.setUnit("Gramm");
		ingredientOne.setQuantity(250);
		realm.copyToRealmOrUpdate(ingredientOne);

		recipe.getIngredients().add(ingredientOne);

		recipe.setNumberOfPersons(5);

		realm.commitTransaction();

		assertEquals(250, recipe.getIngredients().get(0).getQuantity());
	}

	@Test
	public void adjustPersons3() {
		Realm realm = Realm.getDefaultInstance();

		Recipe recipe = Recipe.newRecipe();

		realm.beginTransaction();
		recipe.setName("Nudeln mit Tomaten");
		recipe.setNumberOfPersons(2);

		Ingredient ingredientOne = Ingredient.newIngredient();
		ingredientOne.setName("Paprika");
		ingredientOne.setUnit("Stück");
		ingredientOne.setQuantity(1);
		realm.copyToRealmOrUpdate(ingredientOne);

		recipe.getIngredients().add(ingredientOne);

		recipe.setNumberOfPersons(4);

		realm.commitTransaction();

		assertEquals(2, recipe.getIngredients().get(0).getQuantity());
	}
}
