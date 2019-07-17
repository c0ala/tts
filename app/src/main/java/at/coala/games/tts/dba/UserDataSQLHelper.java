package at.coala.games.tts.dba;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import at.coala.games.tts.data.User;

/**
 * Provides methods to access the user database, extending the
 * SQLiteOpenHelper.
 *
 * @author Klaus
 * @version 3.1
 * @since 01.01.1970.
 * @see SQLiteOpenHelper
 */
class UserDataSQLHelper extends SQLiteOpenHelper {

	/**
	 * Create a new database access.
	 *
	 * @param context to use to open or create the database.
	 * @see Context
	 */
	UserDataSQLHelper(Context context) {
		super(context, "game.db", null, 15);
	}

	/**
	 * Add a new user into the database.
	 *
	 * @param db database to add the user.
	 * @param name username.
	 * @param sex takes a final User.SEX_ flag.
	 * @see SQLiteDatabase
	 * @see User
	 */
	void addUser(SQLiteDatabase db, String name, int sex) {
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("sex", sex);
		db.insert("user", null, values);
	}

	/**
	 * Remove a user from the database.
	 *
	 * @param db database to remove the user.
	 * @param name username.
	 * @see SQLiteDatabase
	 */
	void deleteUser(SQLiteDatabase db, String name) {
		db.delete("user", "name = ?", new String[]{name});
	}

	/**
	 * Get all users in the database.
	 *
	 * @param db the database.
	 * TODO
	 * @see List
	 * @see SQLiteDatabase
	 * @see User
	 */
	void getUsers(SQLiteDatabase db, List<User> userListActive, List<User> userListInactive) {
		Cursor cursor =db.rawQuery(
				"SELECT name, sex, active, level, clothes, points FROM user",
				null);
		if (cursor.moveToFirst()) {
			do {
				switch (cursor.getInt(2)) {
					case 0:
						userListInactive.add(new User(
								cursor.getString(0),
								cursor.getInt(1),
								cursor.getInt(3),
								cursor.getInt(4),
								cursor.getDouble(5)));
						break;
					case 1:
						userListActive.add(new User(
								cursor.getString(0),
								cursor.getInt(1),
								cursor.getInt(3),
								cursor.getInt(4),
								cursor.getDouble(5)));
						break;
				}
			} while(cursor.moveToNext());
		}
		cursor.close();
	}

	//TODO final fields für db parameter

	/**
	 * Called when the database is created for the first time. This is where
	 * the creation of tables and the initial population of the tables happen.
	 *
	 * @param db the database.
	 * @see SQLiteDatabase
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS user (" +
				"name TEXT PRIMARY KEY NOT NULL, " +
				"sex INTEGER NOT NULL, " +
				"active INTEGER NOT NULL DEFAULT 1, " +
				"level INTEGER NOT NULL DEFAULT 1, " +
				"clothes INTEGER NOT NULL DEFAULT 0, " +
				"points DOUBLE NOT NULL DEFAULT 0.0)");
	}

	/**
	 * Called when the database needs to be upgraded. If the database is older
	 * than the current version, tables are dropped and onCreate() is called.
	 *
	 * @param db the database.
	 * @param oldVersion the old database version.
	 * @param newVersion the new database version.
	 * @see SQLiteDatabase
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion < newVersion) {
			db.execSQL("DROP TABLE IF EXISTS user");
			onCreate(db);
		}
	}

	//TODO
	void updateUserActive(SQLiteDatabase db, String name, boolean active) {
		ContentValues values = new ContentValues();
		values.put("active", (active ? 1 : 0));
		db.update("user", values, "name = ?", new String[]{name});
	}

	//TODO
	void updateUserClothes(SQLiteDatabase db, String name, int clothes) {
		ContentValues values = new ContentValues();
		values.put("clothes", clothes);
		db.update("user", values, "name = ?", new String[]{name});
	}

	//TODO
	void updateUserLevel(SQLiteDatabase db, String name, int level) {
		ContentValues values = new ContentValues();
		values.put("level", level);
		db.update("user", values, "name = ?", new String[]{name});
	}

	//TODO
	void updateUserPoints(SQLiteDatabase db, String name, double points) {
		ContentValues values = new ContentValues();
		values.put("points", points);
		db.update("user", values, "name = ?", new String[]{name});
	}

	//TODO
	void updateUserData(SQLiteDatabase db, User user) {
		ContentValues values = new ContentValues();
		values.put("clothes", user.getClothes());
		values.put("level", user.getLevel());
		values.put("points", user.getPoints());
		db.update("user", values, "name = ?", new String[]{user.name});
	}
}
