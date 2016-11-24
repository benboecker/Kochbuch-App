package de.benboecker.kochbuch.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Created by Benni on 19.11.16.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "recipes.db";
	private static final int DATABASE_VERSION = 1;

	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		TableIngredient.onCreate(database);
		TableRecipe.onCreate(database);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(SQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");

		TableIngredient.onUpgrade(database);
		TableRecipe.onUpgrade(database);

		this.onCreate(database);
	}
}
