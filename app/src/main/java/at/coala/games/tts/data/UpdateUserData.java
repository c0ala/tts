package at.coala.games.tts.data;

import java.util.List;

import android.content.Context;
import at.coala.games.tts.dba.DataAccess;
import at.coala.games.tts.game.GameDataManager;

/**
 * This class exist to help creating, deleting and updating User objects as it
 * provides methods that accesses the database, a list of users and the game
 * and alters them in only one call.
 *
 * @author Klaus
 * @see User
 */
public class UpdateUserData {

	/**
	 * TODO
	 *
	 * @param context
	 * @param user
     */
	public static void addPlayer(Context context, User user) {
		GameDataManager.addPlayer(user);
		DataAccess.updateUser(context, user.name, User.ATTRIBUTE_ACTIVE, true);
	}

	/**
	 * TODO
	 *
	 * @param context
	 * @param user
     */
	public static void removePlayer(Context context, User user) {
		GameDataManager.removePlayer(user);
		DataAccess.updateUser(context, user.name, User.ATTRIBUTE_ACTIVE, false);
	}

	/**
	 * Create a new user, add it to the userList, add it to the database and
	 * return the new user.
	 *
	 * @param context to use to open or create the database.
	 * @param userList a user list to add the User to.
	 * @param newUsername a user name.
	 * @param male takes a final User.SEX_ flag.
	 * @return the created User.
	 * @see Context
	 * @see List
	 * @see User
	 */
	public static User newUser(Context context, List<User> userList, String newUsername, int male) {
		User user = new User(newUsername, male);
		userList.add(user);
		DataAccess.addUser(context, newUsername, male);
		return user;
	}

	/**
	 * Removes the user from the userList, deletes the database entry and
	 * removes the user from the current game.
	 *
	 * @param context to use to open or create the database.
	 * @param userList a user list to remove the User from.
	 * @param position the position of the User in the list.
	 * @see Context
	 * @see List
	 * @see User
	 */
	public static void removeUser(Context context, List<User> userList, int position) {
		User user = userList.remove(position);
		DataAccess.deleteUser(context, user.name);
		GameDataManager.removePlayer(user);
	}
}
