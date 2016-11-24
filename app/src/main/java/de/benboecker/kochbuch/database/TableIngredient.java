package de.benboecker.kochbuch.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Benni on 19.11.16.
 */

public class TableIngredient {
	public static final String COLUMN_ID = "_id";
	public static final String TABLE_NAME = "ingredient";
	public static final String COLUMN_NAME = "name";

	public static String[] allColumns = { COLUMN_ID, COLUMN_NAME };

	private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "( "
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_NAME + " text not null"
			+ ");";

	private static final String DROP_TABLE = "DROP TABLE IF EXISTS "
			+ TABLE_NAME;

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_TABLE);
	}

	public static void onUpgrade(SQLiteDatabase database) {
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	}
}
