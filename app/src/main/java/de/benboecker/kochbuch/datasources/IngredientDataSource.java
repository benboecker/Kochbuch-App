package de.benboecker.kochbuch.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import de.benboecker.kochbuch.database.SQLiteHelper;
import de.benboecker.kochbuch.database.TableIngredient;
import de.benboecker.kochbuch.model.Ingredient;

/**
 * Created by Benni on 19.11.16.
 */

public class IngredientDataSource {

	private SQLiteDatabase database;
	private SQLiteHelper dbHelper;

	public IngredientDataSource(Context context) {
		this.dbHelper = new SQLiteHelper(context);
	}

	public void open() throws SQLException {
		this.database = this.dbHelper.getWritableDatabase();
	}

	public void close() {
		this.dbHelper.close();
	}

	public Ingredient createIngredient(String ingredient) {
		ContentValues values = new ContentValues();
		values.put(TableIngredient.COLUMN_NAME, ingredient);
		long insertId = this.database.insert(TableIngredient.TABLE_NAME, null, values);
		Cursor cursor = this.database.query(TableIngredient.TABLE_NAME, TableIngredient.allColumns, TableIngredient.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Ingredient newComment = cursorToIngredient(cursor);
		cursor.close();
		return newComment;
	}

	public void deleteIngredient(Ingredient ingredient) {
		long id = ingredient.getId();
		if (this.database.delete(TableIngredient.TABLE_NAME, TableIngredient.COLUMN_ID + " = " + id, null) != 1) {
			System.out.println("Comment deleted with id: " + id);
		}
	}

	public List<Ingredient> getAllIngredients() {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();

		Cursor cursor = this.database.query(TableIngredient.TABLE_NAME, TableIngredient.allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Ingredient ingredient = cursorToIngredient(cursor);
			ingredients.add(ingredient);
			cursor.moveToNext();
		}

		cursor.close();
		return ingredients;
	}

	private Ingredient cursorToIngredient(Cursor cursor) {
		Ingredient ingredient = new Ingredient();
		ingredient.setId(cursor.getLong(0));
		ingredient.setName(cursor.getString(1));
		return ingredient;
	}

}
