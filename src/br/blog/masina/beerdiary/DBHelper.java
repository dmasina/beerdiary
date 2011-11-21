package br.blog.masina.beerdiary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	private static String dbName   = "BEERDIARY";
	private static String[] dbSQLs = new String[] {
		"CREATE TABLE beer ("
			+ "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
		    + "id_brewery INTEGER,"
		    + "id_style INTEGER,"
		    + "name TEXT,"
		    + "adv REAL,"
		    + "origin TEXT,"
		    + "description TEXT);",
		"CREATE TABLE style ("
			+ "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
			+ "type TEXT,"
			+ "adv_min REAL,"
			+ "adv_max REAL,"
			+ "description TEXT);",
		"CREATE TABLE review ("
			+ "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
			+ "id_beer INTEGER,"
			+ "date INTEGER,"
			+ "appearance TEXT,"
			+ "smell TEXT,"
			+ "initial_taste TEXT,"
			+ "thoughts TEXT,"
			+ "rate INTEGER,"
			+ "description TEXT);",
		"CREATE TABLE brewery ("
			+ "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
			+ "name TEXT,"
			+ "website TEXT,"
			+ "description TEXT);", 
		"CREATE TABLE brewery_state ("
			+ "id INTEGER,"
			+ "name TEXT,"
			+ "website TEXT,"
			+ "description TEXT);",
			"CREATE TABLE buddy ("
			+ "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
			+ "name TEXT,"
			+ "description TEXT);",
		"CREATE TABLE picture ("
			+ "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
			+ "file TEXT NOT NULL);",
		"CREATE TABLE buddy_picture ("
			+ "id_buddy INTEGER NOT NULL,"
			+ "id_picture INTEGER NOT NULL);",
		"CREATE TABLE review_beer ("
			+ "id_review INTEGER NOT NULL,"
			+ "id_beer INTEGER NOT NULL);",
		"CREATE TABLE beer_picture ("
			+ "id_beer INTEGER NOT NULL,"
			+ "id_picture INTEGER NOT NULL);",
		"CREATE TABLE brewery_picture ("
			+ "id_brewery INTEGER NOT NULL,"
			+ "id_picture INTEGER NOT NULL);"
	};
	
	public DBHelper( Context context ) {
		super( context, dbName, null, 1 );
	}

	@Override
	public void onCreate( SQLiteDatabase db ) {
		Log.i( dbName, "DBHelper.onCreate" );
		for ( int i = 0; i < dbSQLs.length; i++ ) {
			db.execSQL( dbSQLs[ i ] );
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i( dbName, "DBHelper.onUpgrade" );
	}
}
