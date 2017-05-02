package at.coala.games.tts.dba;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;

import org.xmlpull.v1.XmlPullParser;

import at.coala.games.tts.data.Settings;
import at.coala.games.tts.data.quest.QuestCollection;
import at.coala.games.tts.data.User;
import at.coala.games.tts.data.quest.RuleMap;
import at.coala.games.tts.dba.xml.QuestDataXMLAccess;
import at.coala.games.tts.game.GameDataManager;

/**
 * Provides methods to access persistent data. It can read and write Users into
 * a database and read quests and rules out of XML files.
 *
 * @author Klaus
 */
public class DataAccess {

	/**
	 * Field to access Settings SharedPreferences
	 */
	private static final String PREFERENCES_SETTING = "settings";

	//TODO
	private static final String PREFERENCES_GAME_DATA = "game_data";

	/**
	 * Add a new user into the database.
	 *
	 * @param context to use to open or create the database.
	 * @param name username.
	 * @param sex takes a final User.SEX_ flag.
	 * @see Context
	 * @see User
	 */
	public static void addUser(Context context, String name, int sex) {
		UserDataSQLHelper dbHelper = new UserDataSQLHelper(context);
		dbHelper.addUser(dbHelper.getWritableDatabase(), name, sex);
		dbHelper.close();
	}

	/**
	 * Remove a user from the database.
	 *
	 * @param context to use to open or create the database.
	 * @param name username.
	 * @see Context
	 */
	public static void deleteUser(Context context, String name) {
		UserDataSQLHelper dbHelper = new UserDataSQLHelper(context);
		dbHelper.deleteUser(dbHelper.getWritableDatabase(), name);
		dbHelper.close();
	}

	/**
	 * Reads a XML file and stores the quests and rules into the data-sets.
	 *
	 * @param quests collection needed to store quests.
	 * @param rules map to store the rules.
	 * @param lang_code a xsd:language code.
	 * @param dataStream a data stream with the XML to parse.
	 * @see InputStream
	 * @see QuestCollection
	 * @see RuleMap
	 */
	@SuppressWarnings("unused")
	public static void getQuests(QuestCollection quests, RuleMap rules, String lang_code, InputStream dataStream) {
		QuestDataXMLAccess xmlAccess = new QuestDataXMLAccess();
		try {
			xmlAccess.getQuests(quests, rules, lang_code, dataStream);
		} catch (IOException e) {
			/**
			 * It is ok to ignore this exception. This app will not collapse
			 * without parsing this file.
			 */
		}
	}

	/**
	 * Reads a XML file and stores the quests and rules into the data-sets.
	 *
	 * @param quests collection needed to store quests.
	 * @param rules map to store the rules.
	 * @param lang_code a xsd:language code.
	 * @param parser a pull-parser with the XML to parse.
	 * @see QuestCollection
	 * @see RuleMap
	 * @see XmlPullParser
	 */
	public static void getQuests(QuestCollection quests, RuleMap rules, String lang_code, XmlPullParser parser) {
		QuestDataXMLAccess xmlAccess = new QuestDataXMLAccess();
		try {
			xmlAccess.getQuests(quests, rules, lang_code, parser);
		} catch (IOException e) {
			/**
			 * It is ok to ignore this exception. This app will not collapse
			 * without parsing this file.
			 */
		}
	}

	/**
	 * Get a setting value in SharedPreferences.
	 *
	 * @param context the context of which to open the SharedPreferences.
	 * @param setting takes a Settings public final SETTINGS_ATTRIBUTE_
	 *                   constant.
	 * @param defValue the value to change the setting to.
	 * @return the stored value or, if none found, the defValue.
	 * @see Context
	 */
	public static boolean getSetting(Context context, int setting, boolean defValue) {
		SharedPreferences sp = context.getSharedPreferences(PREFERENCES_SETTING, Context.MODE_PRIVATE);
		return sp.getBoolean(Settings.getSettingsAttributeString(setting), defValue);
	}

	/**
	 * Get a setting value in SharedPreferences.
	 *
	 * @param context the context of which to open the SharedPreferences.
	 * @param setting takes a Settings public final SETTINGS_ATTRIBUTE_
	 *                   constant.
	 * @param defValue the value to change the setting to.
	 * @return the stored value or, if none found, the defValue.
	 * @see Context
	 */
	public static int getSetting(Context context, int setting, int defValue) {
		SharedPreferences sp = context.getSharedPreferences(PREFERENCES_SETTING, Context.MODE_PRIVATE);
		return sp.getInt(Settings.getSettingsAttributeString(setting), defValue);
	}

	/**
	 * Get all users in the database into two different lists depending on if
	 * they are selected for the current game or not. Both lists are not
	 * connected with the database. Changes on the database or a list will not
	 * affect the other parts.
	 *
	 * @param context the database.
	 * @param userListActive a list with all users selected for active game.
	 * @param userListInactive a list with all users not selected for active
	 *                            game.
	 * @see Context
	 * @see List
	 * @see User
	 */
	public static void getUsers(Context context, List<User> userListActive, List<User> userListInactive) {
		UserDataSQLHelper dbHelper = new UserDataSQLHelper(context);
		dbHelper.getUsers(dbHelper.getReadableDatabase(), userListActive, userListInactive);
		dbHelper.close();
	}

	/**
	 * Updates a setting value in SharedPreferences.
	 *
	 * @param context the context of which to open the SharedPreferences.
	 * @param setting takes a Settings public final SETTINGS_ATTRIBUTE_
	 *                   constant.
	 * @param value the value to change the setting to.
	 * @see Context
	 */
	public static void updateSetting(Context context, int setting, boolean value) {
		SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_SETTING, Context.MODE_PRIVATE).edit();
		editor.putBoolean(Settings.getSettingsAttributeString(setting), value);
		editor.apply();
	}

	/**
	 * Updates a setting value in SharedPreferences.
	 *
	 * @param context the context of which to open the SharedPreferences.
	 * @param setting takes a Settings public final SETTINGS_ATTRIBUTE_
	 *                   constant.
	 * @param value the value to change the setting to.
	 * @see Context
	 */
	public static void updateSetting(Context context, int setting, int value) {
		SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_SETTING, Context.MODE_PRIVATE).edit();
		editor.putInt(Settings.getSettingsAttributeString(setting), value);
		editor.apply();
	}

	/**
	 * Update user account data.
	 *
	 * @param context to use to open or create the database.
	 * @param name username.
	 * @param user_data_key takes a public final User.ATTRIBUTE_ constant to
	 *                         identify which value to update.
	 * @param value the value to change.
	 * @see Context
	 * @see User
	 */
	public static void updateUser(Context context, String name, int user_data_key, boolean value) {
		UserDataSQLHelper dbHelper = new UserDataSQLHelper(context);
		switch (user_data_key) {
			case User.ATTRIBUTE_ACTIVE:
				dbHelper.updateUserActive(dbHelper.getWritableDatabase(), name, value);
				break;
			default:
				break;
		}
		dbHelper.close();
	}

	/**
	 * Update user account data.
	 *
	 * @param context to use to open or create the database.
	 * @param name username.
	 * @param user_data_key takes a public final User.ATTRIBUTE_ constant to
	 *                         identify which value to update.
	 * @param value the value to change.
	 * @see Context
	 * @see User
	 */
	public static void updateUser(Context context, String name, int user_data_key, double value) {
		UserDataSQLHelper dbHelper = new UserDataSQLHelper(context);
		switch (user_data_key) {
			case User.ATTRIBUTE_POINTS:
				dbHelper.updateUserPoints(dbHelper.getWritableDatabase(), name, value);
				break;
			default:
				break;
		}
		dbHelper.close();
	}

	/**
	 * Update user account data.
	 *
	 * @param context to use to open or create the database.
	 * @param name username.
	 * @param user_data_key takes a public final User.ATTRIBUTE_ constant to
	 *                         identify which value to update.
	 * @param value the value to change.
	 * @see Context
	 * @see User
	 */
	public static void updateUser(Context context, String name, int user_data_key, int value) {
		UserDataSQLHelper dbHelper = new UserDataSQLHelper(context);
		switch (user_data_key) {
			case User.ATTRIBUTE_CLOTHES:
				dbHelper.updateUserClothes(dbHelper.getWritableDatabase(), name, value);
				break;
			case User.ATTRIBUTE_LEVEL:
				dbHelper.updateUserLevel(dbHelper.getWritableDatabase(), name, value);
				break;
			default:
				break;
		}
		dbHelper.close();
	}

	//TODO
	public static void updateUserList(Context context, List<User> userList) {
		UserDataSQLHelper dbHelper = new UserDataSQLHelper(context);
		for (User u : userList) {
			dbHelper.updateUserData(dbHelper.getWritableDatabase(), u);
		}
		dbHelper.close();
	}

	//TODO
	public static void updateGameData(Context context, int gameData, long value) {
		SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_GAME_DATA, Context.MODE_PRIVATE).edit();
		editor.putLong(GameDataManager.getGameDataAttributeString(gameData), value);
		editor.apply();
	}

	//TODO
	public static long getGameData(Context context, int gameData, long defValue) {
		SharedPreferences sp = context.getSharedPreferences(PREFERENCES_GAME_DATA, Context.MODE_PRIVATE);
		return sp.getLong(GameDataManager.getGameDataAttributeString(gameData), defValue);
	}
}
