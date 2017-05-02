package at.coala.games.tts.game;

import java.util.Random;

import android.content.Context;

import at.coala.games.tts.R;
import at.coala.games.tts.data.Game;
import at.coala.games.tts.data.Settings;
import at.coala.games.tts.data.quest.Quest;
import at.coala.games.tts.data.quest.QuestCollection;
import at.coala.games.tts.data.quest.RuleMap;
import at.coala.games.tts.data.User;
import at.coala.games.tts.dba.DataAccess;

/**
 * This class manages and controls all game data for this app. All fields and
 * methods are static, so they can be accessed without having an object around.
 *
 * @author Klaus
 */
public class GameDataManager {

	/**
	 * TODO
	 */
	public static final int ATTRIBUTE_GAME_DATA_LAST_GAME_ACTION_TIMESTAMP = 0;

	/**
	 * TODO
	 */
	private static final String ATTRIBUTE_GAME_DATA_LAST_GAME_ACTION_TIMESTAMP_STRING = "last_game_action_timestamp";

	/**
	 * A field storing the current game state.
	 *
	 * @see GameState
	 */
	static GameState currentState = new GameStateStart();

	/**
	 * A field storing all game data.
	 *
	 * @see Game
	 */
	static Game game;

	/**
	 * A field containing the current quest.
	 *
	 * @see Quest
	 */
	static Quest quest;

	/**
	 * A private collection storing all quests.
	 *
	 * @see QuestCollection
	 */
	private static QuestCollection quests;

	/**
	 * The state during quests.
	 *
	 * @see GameState
	 */
	static GameState questState;

	/**
	 * A private number generator.
	 *
	 * @see Random
	 */
	private static Random r = new Random();

	/**
	 * The state between quests, providing new quests.
	 *
	 * @see GameState
	 */
	static GameState readyState;

	/**
	 * A map with rules accessible by identifiers.
	 *
	 * @see RuleMap
	 */
	static RuleMap rules;

	/**
	 * Settings for this game.
	 */
	static Settings settings;

	/**
	 * Add a new user to the game.
	 *
	 * @param player user to add.
	 * @see User
	 */
	public static void addPlayer(User player) { game.addPlayer(player); }

	/**
	 * Adds a requirement and marks the settings as changed.
	 *
	 * @param requirement takes a final Quest.REQUIREMENT_ flag.
	 * @see Quest
	 */
	public static void addRequirement(int requirement) {
		settings.addRequirement(requirement);
		validateQuestCollection();
	}

	/**
	 * Should be called if go_button or no_button is called. Calls the current
	 * GameState object and changes the state.
	 *
	 * @param button_is_go true if button is go_button, false if button is
	 *                     no_button.
	 * @see GameState
	 */
	public static void clickedButtonIsGo(boolean button_is_go) { currentState = currentState.clickedButtonGoOrNo(button_is_go); }

	/**
	 * Returns true if this game contains the specified user.
	 *
	 * @param user object whose presence in this game is to be tested.
	 * @return true if this list contains the specified element; else
	 * otherwise.
	 * @see User
	 */
	public static boolean containsUser(User user) { return game.containsUser(user); }

	/**
	 * Returns if there are enough players to start a game.
	 *
	 * @return true if enough players are in the game; false otherwise.
	 */
	public static boolean enoughPlayer() { return game.enoughPlayer(); }

	/**
	 * Returns the text that should be shown on the go_button.
	 *
	 * @param context to access application data.
	 * @return text for button.
	 * @see Context
	 */
	public static String getGoButtonText(Context context) { return currentState.getGoButtonText(context); }

	/**
	 * Get the count of all linked quest texts.
	 *
	 * @return the quest text count.
	 */
	static int getLinkedQuestTextCount() { return quests.getLinkedQuestTextCount(); }

	/**
	 * Returns the text that should be shown in the main text field.
	 *
	 * @param context to access application data.
	 * @return text to show in main text field.
	 * @see Context
	 */
	public static String getMainText(Context context) { return currentState.getMainText(context); }

	/**
	 * Returns the text that should be shown on the no_button. If not
	 * overridden, returns null.
	 *
	 * @param context to access application data.
	 * @return text for button, or null if button should not be shown.
	 * @see Context
	 */
	public static String getNoButtonText(Context context) { return currentState.getNoButtonText(context); }

	/**
	 * Returns the number of players selected for this game.
	 *
	 * @return the number of players.
     */
	public static int getPlayerCount() { return game != null ? game.getPlayerCount() : 0; }

	//TODO
	public static String getPreTitle(Context context) { return currentState.getPreTitle(context); }

	/**
	 * Calls nextDouble() with internal number generator.
	 *
	 * @return a double between 0 and 1.
	 */
	public static double getRandom() { return r.nextDouble(); }

	/**
	 * Returns the text that should be shown in the comment field beyond the
	 * buttons.
	 *
	 * @param context to access application data.
	 * @return text to show in comment section.
	 * @see Context
	 */
	public static String getSubText(Context context) { return currentState.getSubText(context); }

	/**
	 * Returns the text that should be shown in the above the text field or as
	 * title.
	 *
	 * @param context to access application data.
	 * @return text to show in the title field.
	 * @see Context
	 */
	public static String getTitle(Context context) { return currentState.getTitle(context); }

	/**
	 * Get the count of all valid quest texts.
	 *
	 * @return the quest text count.
	 */
	public static int getValidQuestTextCount() { return quests.getValidQuestTextCount(); }

	/**
	 * This method should be called on creation. It sets static available game
	 * and quest data.
	 *
	 * @param context to access application data.
	 */
	public static void onCreate(Context context) {
		/**
		 * TODO
		 * define default reqs somewhere else
		 */
		quests = new QuestCollection();
		rules = new RuleMap();
		settings = new Settings();

		/**
		 * TODO
		 * if slowing down: fetch data asynchron in thread
		 */

		//uncomment for testing the time.
		//long startTime1 = System.currentTimeMillis();
		DataAccess.getQuests(quests, rules, "DE", context.getResources().getXml(R.xml.tts_data));
		//long stopTime1 = System.currentTimeMillis();
		//long startTime2 = System.currentTimeMillis();
		//DataAccess.getQuests(quests, rules, "DE", context.getResources().openRawResource(R.raw.tts_data));
		//long stopTime2 = System.currentTimeMillis();
		//System.out.println(stopTime1 - startTime1); //last test: 167, 202, 301, 286, 164, 221 ~ 224
		//System.out.println(stopTime2 - startTime2); //last test: 447, 478, 451, 465, 486, 481 ~ 468

		game = new Game(settings.getCategoryProbability());
	}

	static void validateQuestCollection() {
		quests.validateCollection(settings.getLocation(), settings.getFriendshipLevel(), game.allPlayersAre(), settings.getRequirements());
	}

	/**
	 * Chooses the next Quest and matches it with compatible Users.
	 */
	static void prepareNextQuest() {
		do {
			quest = quests.getRandomQuest(game.getAvgLevel(), game.returnCategory(settings.getCategoryProbability()));
		} while (quest == null);
		boolean success = game.findPlayer(quest.categories, quest.getConfiguration(settings.getLocation(), game.getAvgLevel()));
		if (!success) prepareNextQuest();
	}

	/**
	 * Removes a user from the Game.
	 *
	 * @param player to remove.
	 * @see User
	 */
	public static void removePlayer(User player) { game.removePlayer(player); }

	/**
	 * Removes a requirement and marks the settings as changed.
	 *
	 * @param requirement takes a final Quest.REQUIREMENT_ flag.
	 * @see Quest
	 */
	public static void removeRequirement(int requirement) {
		settings.removeRequirement(requirement);
		validateQuestCollection();
	}

	/**
	 * Prepares all data for a new game.
	 */
	static void setGame(boolean reset) {
		r = new Random();
		readyState = new GameStateReady();
		questState = new GameStateQuest();

		quest = null;

		game.setGame(reset);
	}

	/**
	 * Sets the delete flag to not asked the current question again.
	 */
	public static void setDeleteFlag() { quests.setDeleteFlag(); }

	/**
	 * Set a new friendship level and marks the settings as changed.
	 *
	 * @param friendship_level takes a final User.FRIENDS_ flag
	 * @see User
	 */
	public static void setFriendshipLevel(int friendship_level) {
		settings.setFriendshipLevel(friendship_level);
		validateQuestCollection();
	}

	/**
	 * Set a new location and marks the settings as changed.
	 *
	 * @param location takes a final Game.LOCATION_ flag.
	 * @see Game
	 */
	public static void setGameLocation(int location) {
		settings.setLocation(location);
		validateQuestCollection();
	}

	/**
	 * Should be called if during the game the settings are called or a saved
	 * game can be resumed.
	 */
	public static void toResume() { currentState = new GameStateResume(); }

	//TODO
	public static void saveGameData(Context context) { currentState.saveGameData(context); }

	//TODO
	public static String getGameDataAttributeString(int gameData_attribute) {
		switch (gameData_attribute) {
			case ATTRIBUTE_GAME_DATA_LAST_GAME_ACTION_TIMESTAMP:
				return ATTRIBUTE_GAME_DATA_LAST_GAME_ACTION_TIMESTAMP_STRING;
			default:
				return null;
		}
	}
}
