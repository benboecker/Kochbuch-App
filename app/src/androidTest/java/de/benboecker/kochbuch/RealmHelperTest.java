package de.benboecker.kochbuch;

import android.support.test.runner.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.benboecker.kochbuch.model.Ingredient;
import de.benboecker.kochbuch.model.RealmHelper;
import io.realm.Realm;
import io.realm.RealmResults;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Benni on 19.12.16.
 */
@RunWith(AndroidJUnit4.class)
public class RealmHelperTest {

	/*@BeforeClass
	public void removeTestIngredients() {
		Realm realm = Realm.getDefaultInstance();

		RealmResults<Ingredient> ingredients = realm.where(Ingredient.class).equalTo("identifier", 999_999).findAll();
		RealmResults<Ingredient> ingredients2 = realm.where(Ingredient.class).equalTo("identifier", 1_000_000).findAll();

		realm.beginTransaction();
		ingredients.deleteAllFromRealm();
		ingredients2.deleteAllFromRealm();
		realm.commitTransaction();

	}*/

	@Test
	public void getNextIndex() {
		/*long expectedID = 1_000_000;

		Realm realm = Realm.getDefaultInstance();
		realm.beginTransaction();
		realm.createObject(Ingredient.class, expectedID - 1);
		Ingredient ingredientTwo = realm.createObject(Ingredient.class, RealmHelper.getNextID(Ingredient.class, "id"));

		realm.commitTransaction();

		assertEquals(expectedID, ingredientTwo.getId());*/
	}


}
