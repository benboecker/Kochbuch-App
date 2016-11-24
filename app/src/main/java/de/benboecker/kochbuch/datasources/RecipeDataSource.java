package de.benboecker.kochbuch.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import de.benboecker.kochbuch.database.SQLiteHelper;
import de.benboecker.kochbuch.database.TableRecipe;
import de.benboecker.kochbuch.model.Recipe;

/**
 * Created by Benni on 19.11.16.
 */

public class RecipeDataSource {

	private SQLiteDatabase database;
	private SQLiteHelper dbHelper;

	public RecipeDataSource(Context context) {
		this.dbHelper = new SQLiteHelper(context);
	}

	public void open() throws SQLException {
		this.database = this.dbHelper.getWritableDatabase();
	}

	public void close() {
		this.dbHelper.close();
	}

	public Recipe createRecipe(String name) {
		ContentValues values = new ContentValues();
		values.put(TableRecipe.COLUMN_NAME, name);

		long insertId = this.database.insert(TableRecipe.TABLE_NAME, null, values);
		Cursor cursor = this.database.query(TableRecipe.TABLE_NAME, TableRecipe.allColumns, TableRecipe.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Recipe newRecipe = this.cursorToRecipe(cursor);
		cursor.close();
		return newRecipe;
	}

	public void deleteRecipe(Recipe recipe) {
		long id = recipe.getId();
		if (this.database.delete(TableRecipe.TABLE_NAME, TableRecipe.COLUMN_ID + " = " + id, null) != 1) {
			System.out.println("Comment deleted with id: " + id);
		}
	}

	public List<Recipe> getAllRecipes() {
		List<Recipe> recipes = new ArrayList();

		Cursor cursor = this.database.query(TableRecipe.TABLE_NAME, TableRecipe.allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Recipe recipe = this.cursorToRecipe(cursor);
			recipes.add(recipe);
			cursor.moveToNext();
		}

		cursor.close();
		return recipes;
	}

	private Recipe cursorToRecipe(Cursor cursor) {
		Recipe recipe = new Recipe();
		recipe.setId(cursor.getLong(0));
		recipe.setName(cursor.getString(1));
		return recipe;
	}
}
