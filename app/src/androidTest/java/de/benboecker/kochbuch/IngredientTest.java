package de.benboecker.kochbuch;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.benboecker.kochbuch.model.Ingredient;
import io.realm.Realm;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by Benni on 19.12.16.
 */

@RunWith(AndroidJUnit4.class)
public class IngredientTest {

	@BeforeClass
	public static void deleteRealm() {
		Realm realm = Realm.getDefaultInstance();
		realm.beginTransaction();
		realm.deleteAll();
		realm.commitTransaction();
	}

	@Test
	public void newIngredientNotNull() {
		Ingredient ingredient = Ingredient.newIngredient();

		assertNotNull(ingredient);
	}

	@Test
	public void newIngredientUniquePrimaryKey() {
		Ingredient ingredient = Ingredient.newIngredient();
		Ingredient ingredientTwo = Ingredient.newIngredient();

		/// Angelegt mit unterschiedlichen IDs
		Assert.assertNotEquals(ingredient.getId(), ingredientTwo.getId());
	}

}
