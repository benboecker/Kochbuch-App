package de.benboecker.kochbuch.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Benni on 19.11.16.
 */

public class TableRecipe {
	public static final String COLUMN_ID = "_id";
	public static final String TABLE_NAME = "recipe";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_TIME = "time";
	public static final String COLUMN_IS_FAVOURITE = "is_favorite";
	public static final String COLUMN_DEFAULT_PERSONS = "default_persons";

	public static String[] allColumns = { COLUMN_ID, COLUMN_NAME, COLUMN_TIME, COLUMN_IS_FAVOURITE, COLUMN_DEFAULT_PERSONS };

	private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "( "
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_NAME + " TEXT NOT NULL, "
			+ COLUMN_TIME + " INTEGER, "
			+ COLUMN_IS_FAVOURITE + " INTEGER, "
			+ COLUMN_DEFAULT_PERSONS + " INTEGER"
			+ ");";

	private static final String DROP_TABLE = "DROP TABLE IF EXISTS "
			+ TABLE_NAME;

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(TableRecipe.CREATE_TABLE);
	}

	public static void onUpgrade(SQLiteDatabase database) {
		database.execSQL(TableRecipe.DROP_TABLE);
	}
}
