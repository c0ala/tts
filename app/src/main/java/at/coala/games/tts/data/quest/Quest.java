package at.coala.games.tts.data.quest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.coala.games.tts.data.Game;
import at.coala.games.tts.data.User;
import at.coala.games.tts.game.GameDataManager;

/**
 * This class stores and provides a quest and all data that comes with it.
 *
 * @author Klaus
 * @version 4
 * @since 01.01.1970
 */
public class Quest {

	/**
	 * A publicly accessible unmodifiable (as converted due to the constructor)
	 * list of final CATEGORY_ flags defined in this class.
	 *
	 * @see List
	 */
	public final List<Integer> categories;

	/**
	 * Flag describing a category to put on clothes. To make iteration over all
	 * categories possible, e.g. in an array, The first category starts with 0,
	 * no value is skipped and the sum of all categories is store in the
	 * constant CATEGORY_SUM, so iterating over all categories is possible.
	 */
	public static final int CATEGORY_CLOTHES_BACK_ON = 0;

	/**
	 * String representation of final flag CATEGORY_CLOTHES_BACK_ON.
	 */
	private static final String CATEGORY_CLOTHES_BACK_ON_STRING = "CLOTHES_BACK_ON";

	/**
	 * Flag describing a category to drink if you are or have got something. To
	 * make iteration over all categories possible, e.g. in an array, The first
	 * category starts with 0, no value is skipped and the sum of all
	 * categories is store in the constant CATEGORY_SUM, so iterating over all
	 * categories is possible.
	 */
	public static final int CATEGORY_CONDITION = CATEGORY_CLOTHES_BACK_ON + 1;

	/**
	 * String representation of final flag CATEGORY_CONDITION.
	 */
	private static final String CATEGORY_CONDITION_STRING = "CONDITION";

	/**
	 * Flag describing a category for dares. To make iteration over all
	 * categories possible, e.g. in an array, The first category starts with 0,
	 * no value is skipped and the sum of all categories is store in the
	 * constant CATEGORY_SUM, so iterating over all categories is possible.
	 */
	public static final int CATEGORY_DARE = CATEGORY_CONDITION + 1;

	/**
	 * String representation of final flag CATEGORY_DARE.
	 */
	private static final String CATEGORY_DARE_STRING = "DARE";

	/**
	 * Flag describing a category to do funny things. To make iteration over
	 * all categories possible, e.g. in an array, The first category starts
	 * with 0, no value is skipped and the sum of all categories is store in
	 * the constant CATEGORY_SUM, so iterating over all categories is
	 * possible.
	 */
	public static final int CATEGORY_DO_FUNNY_THINGS = CATEGORY_DARE + 1;

	/**
	 * String representation of final flag CATEGORY_DO_FUNNY_THINGS.
	 */
	private static final String CATEGORY_DO_FUNNY_THINGS_STRING = "DO_FUNNY_THINGS";

	/**
	 * Flag describing a category where at least somebody always has to drink.
	 * To make iteration over all categories possible, e.g. in an array, The
	 * first category starts with 0, no value is skipped and the sum of all
	 * categories is store in the constant CATEGORY_SUM, so iterating over all
	 * categories is possible.
	 */
	public static final int CATEGORY_DRINKING = CATEGORY_DO_FUNNY_THINGS + 1;

	/**
	 * String representation of final flag CATEGORY_DRINKING.
	 */
	private static final String CATEGORY_DRINKING_STRING = "DRINKING";

	/**
	 * Flag describing a category to kiss. To make iteration over all
	 * categories possible, e.g. in an array, The first category starts with 0,
	 * no value is skipped and the sum of all categories is store in the
	 * constant CATEGORY_SUM, so iterating over all categories is possible.
	 */
	public static final int CATEGORY_KISSING = CATEGORY_DRINKING + 1;

	/**
	 * String representation of final flag CATEGORY_KISSING.
	 */
	private static final String CATEGORY_KISSING_STRING = "KISSING";

	/**
	 * Flag describing a category to make some exercise or move around. To make
	 * iteration over all categories possible, e.g. in an array, The first
	 * category starts with 0, no value is skipped and the sum of all
	 * categories is store in the constant CATEGORY_SUM, so iterating over all
	 * categories is possible.
	 */
	public static final int CATEGORY_MOVE_IT = CATEGORY_KISSING + 1;

	/**
	 * String representation of final flag CATEGORY_MOVE_IT.
	 */
	private static final String CATEGORY_MOVE_IT_STRING = "MOVE_IT";

	/**
	 * Flag describing a category to do naughty stuff. To make iteration over
	 * all categories possible, e.g. in an array, The first category starts
	 * with 0, no value is skipped and the sum of all categories is store in
	 * the constant CATEGORY_SUM, so iterating over all categories is
	 * possible.
	 */
	public static final int CATEGORY_NAUGHTY = CATEGORY_MOVE_IT + 1;

	/**
	 * String representation of final flag CATEGORY_NAUGHTY.
	 */
	private static final String CATEGORY_NAUGHTY_STRING = "NAUGHTY";

	/**
	 * Flag describing a category to drink if you have or have not done
	 * something. To make iteration over all categories possible, e.g. in an
	 * array, The first category starts with 0, no value is skipped and the sum
	 * of all categories is store in the constant CATEGORY_SUM, so iterating
	 * over all categories is possible.
	 */
	public static final int CATEGORY_NEVER_HAVE_I = CATEGORY_NAUGHTY + 1;

	/**
	 * String representation of final flag CATEGORY_NEVER_HAVE_I.
	 */
	private static final String CATEGORY_NEVER_HAVE_I_STRING = "NEVER_HAVE_I";

	/**
	 * Flag describing a category to drink something without alcohol. To make
	 * iteration over all categories possible, e.g. in an array, The first
	 * category starts with 0, no value is skipped and the sum of all
	 * categories is store in the constant CATEGORY_SUM, so iterating over all
	 * categories is possible.
	 */
	public static final int CATEGORY_NON_ALK_DRINKING = CATEGORY_NEVER_HAVE_I + 1;

	/**
	 * String representation of final flag CATEGORY_NON_ALK_DRINKING.
	 */
	private static final String CATEGORY_NON_ALK_DRINKING_STRING = "NON_ALK_DRINKING";

	/**
	 * Flag describing a category to undress. To make iteration over all
	 * categories possible, e.g. in an array, The first category starts with 0,
	 * no value is skipped and the sum of all categories is store in the
	 * constant CATEGORY_SUM, so iterating over all categories is possible.
	 */
	public static final int CATEGORY_STRIP_CLOTHES = CATEGORY_NON_ALK_DRINKING + 1;

	/**
	 * String representation of final flag CATEGORY_STRIP_CLOTHES.
	 */
	private static final String CATEGORY_STRIP_CLOTHES_STRING = "STRIP_CLOTHES";

	/**
	 * A constant containing the sum of all categories. It contains the value
	 * last-category + 1 because categories start with 0.
	 */
	public static final int CATEGORY_SUM = CATEGORY_STRIP_CLOTHES + 1;

	/**
	 * A publicly accessible unmodifiable (as converted due to the constructor)
	 * list of comments, or null of none exist.
	 *
	 * @see List
	 */
	public final List<String> comments;

	/**
	 * A local list of different configurations.
	 *
	 * @see List
	 * @see QuestConfiguration
	 */
	private List<QuestConfiguration> conf;

	/**
	 * Flag describing a quest that can not set the delete flag.
	 */
	public static final int DELETE_FALSE = 0;

	/**
	 * String representation of final flag DELETE_FALSE.
	 */
	private static final String DELETE_FALSE_STRING = "false";

	/**
	 * Flag describing a quest that can set the delete flag.
	 */
	public static final int DELETE_TRUE = 1;

	/**
	 * String representation of final flag DELETE_TRUE.
	 */
	private static final String DELETE_TRUE_STRING = "true";

	/**
	 * A local variable containing the minimum valid level. It is initialised
	 * to Integer.MAX_VALUE
	 *
	 * @see Integer
	 */
	private int minLevel = Integer.MAX_VALUE;

	/**
	 * Flag describing a quest where all players are partner.
	 */
	public static final int PARTNER_ALL = 0;

	/**
	 * String representation of final flag PARTNER_ALL.
	 */
	private static final String PARTNER_ALL_STRING = "ALL";

	/**
	 * Flag describing a quest where the partners hae to be the opposite sex
	 * as the first player.
	 */
	public static final int PARTNER_ALL_OPPOSITE = PARTNER_ALL + 1;

	/**
	 * String representation of final flag PARTNER_ALL_OPPOSITE.
	 */
	private static final String PARTNER_ALL_OPPOSITE_STRING = "ALL_OPPOSITE";

	/**
	 * Flag describing a quest with males as quest partners.
	 */
	public static final int PARTNER_BOYS = PARTNER_ALL_OPPOSITE + 1;

	/**
	 * String representation of final flag PARTNER_BOYS.
	 */
	private static final String PARTNER_BOYS_STRING = "BOYS";

	/**
	 * Flag describing a quest with a female as second player.
	 */
	public static final int PARTNER_FEMALE = PARTNER_BOYS + 1;

	/**
	 * String representation of final flag PARTNER_FEMALE.
	 */
	private static final String PARTNER_FEMALE_STRING = "FEMALE";

	/**
	 * Flag describing a quest with girls as quest partners.
	 */
	public static final int PARTNER_GIRLS = PARTNER_FEMALE + 1;

	/**
	 * String representation of final flag PARTNER_GIRLS.
	 */
	private static final String PARTNER_GIRLS_STRING = "GIRLS";

	/**
	 * Flag describing a quest with a male as second player.
	 */
	public static final int PARTNER_MALE = PARTNER_GIRLS + 1;

	/**
	 * String representation of final flag PARTNER_MALE.
	 */
	private static final String PARTNER_MALE_STRING = "MALE";

	/**
	 * Flag describing a quest without a second player.
	 */
	public static final int PARTNER_NO = PARTNER_MALE + 1;

	/**
	 * String representation of final flag PARTNER_NO.
	 */
	private static final String PARTNER_NO_STRING = "NO";

	/**
	 * Flag describing a quest where the second player has to be the opposite
	 * sex as the first player.
	 */
	public static final int PARTNER_OPPOSITE_SEX = PARTNER_NO + 1;

	/**
	 * String representation of final flag PARTNER_OPPOSITE_SEX.
	 */
	private static final String PARTNER_OPPOSITE_SEX_STRING = "OPPOSITE_SEX";

	/**
	 * Flag describing a quest with a second player.
	 */
	public static final int PARTNER_YES = PARTNER_OPPOSITE_SEX + 1;

	/**
	 * String representation of final flag PARTNER_YES.
	 */
	private static final String PARTNER_YES_STRING = "YES";

	/**
	 * Flag describing a quest for all players.
	 */
	public static final int PLAYER_ALL = 0;

	/**
	 * String representation of final flag PLAYER_ALL.
	 */
	private static final String PLAYER_ALL_STRING = "ALL";

	/**
	 * Flag describing a player that has to be female.
	 */
	public static final int PLAYER_A_FEMALE = PLAYER_ALL + 1;

	/**
	 * String representation of final flag PLAYER_A_FEMALE.
	 */
	private static final String PLAYER_A_FEMALE_STRING = "A_FEMALE";

	/**
	 * Flag describing a player that has to be male.
	 */
	public static final int PLAYER_A_MALE = PLAYER_A_FEMALE + 1;

	/**
	 * String representation of final flag PLAYER_A_MALE.
	 */
	private static final String PLAYER_A_MALE_STRING = "A_MALE";

	/**
	 * Flag describing a quest that needs males to be played.
	 */
	public static final int PLAYER_BOYS = PLAYER_A_MALE + 1;

	/**
	 * String representation of final flag PLAYER_BOYS.
	 */
	private static final String PLAYER_BOYS_STRING = "BOYS";

	/**
	 * Flag describing a quest that needs females to be played.
	 */
	public static final int PLAYER_GIRLS = PLAYER_BOYS + 1;

	/**
	 * String representation of final flag PLAYER_GIRLS.
	 */
	private static final String PLAYER_GIRLS_STRING = "GIRLS";

	/**
	 * Flag describing a quest that has to have one player.
	 */
	public static final int PLAYER_ONE = PLAYER_GIRLS + 1;

	/**
	 * String representation of final flag PLAYER_ONE.
	 */
	private static final String PLAYER_ONE_STRING = "ONE";

	/**
	 * A local list of different configurations, or null if none exist.
	 *
	 * @see List
	 * @see QuestConfiguration
	 */
	private List<QuestConfiguration> public_conf;

	/**
	 * A publicly accessible unmodifiable (as converted due to the constructor)
	 * list of quest texts, each describing the same quest.
	 *
	 * @see List
	 */
	public final List<String> quest_texts;

	/**
	 * A publicly accessible unmodifiable (as converted due to the constructor)
	 * list of final REQUIREMENT_ flags defined in this class, or null if no
	 * requirements are needed.
	 */
	public final List<Integer> requirements;

	/**
	 * Flag to use cream or something comparable.
	 */
	public static final int REQUIREMENT_CREAM = 0;

	/**
	 * String representation of final flag REQUIREMENT_ICE_CUBE.
	 */
	private static final String REQUIREMENT_CREAM_STRING = "CREAM";

	/**
	 * Flag to use if something to dance (club, dance floor) is required.
	 */
	public static final int REQUIREMENT_DANCE = REQUIREMENT_CREAM + 1;

	/**
	 * String representation of final flag REQUIREMENT_DANCE.
	 */
	private static final String REQUIREMENT_DANCE_STRING = "DANCE";

	/**
	 * Flag to use ice cubes.
	 */
	public static final int REQUIREMENT_ICE_CUBE = REQUIREMENT_DANCE + 1;

	/**
	 * String representation of final flag REQUIREMENT_ICE_CUBE.
	 */
	private static final String REQUIREMENT_ICE_CUBE_STRING = "ICE_CUBE";

	/**
	 * Flag to use something you can swim in.
	 */
	public static final int REQUIREMENT_POOL = REQUIREMENT_ICE_CUBE + 1;

	/**
	 * String representation of final flag REQUIREMENT_POLL.
	 */
	private static final String REQUIREMENT_POOL_STRING = "POOL";

	/**
	 * A publicly accessible unmodifiable (as converted due to the constructor)
	 * list of comments, or null of none exist.
	 *
	 * @see List
	 * @see RuleMap
	 */
	public final List<String> rule_ids;

	/**
	 * Flag describing a quest that can not be aborted.
	 */
	public static final int SKIP_FALSE = 0;

	/**
	 * String representation of final flag SKIP_FALSE.
	 */
	private static final String SKIP_FALSE_STRING = "false";

	/**
	 * Flag describing a quest that can be aborted.
	 */
	public static final int SKIP_TRUE = 1;

	/**
	 * String representation of final flag SKIP_TRUE.
	 */
	private static final String SKIP_TRUE_STRING = "true";

	/**
	 * A public field containing the source, or null if no exists.
	 */
	public final String source;

	/**
	 * The constructor takes every data needed to create a quest. This is the
	 * only way to insert the data into final fields. Even lists are converted
	 * into an unmodifiable list.
	 *
	 * @param categories takes a list of final CATEGORY_ flags defined in this
	 *                      class.
	 * @param requirements takes a list of final REQUIREMENT_ flag defined in
	 *                        this class, or NULL
	 * @param source a possible source, or NULL
	 * @param quest_texts a list of quest texts, each describing the same
	 *                       quest.
	 * @param comments, a possible list of comments, or NULL
	 * @param rule_ids, a possible list of rule_ids, or NULL
	 * @param conf a list of configurations
	 * @param public_conf a possible list of configurations for public use, or
	 *                       NULL
	 * @see List
	 * @see QuestConfiguration
	 * @see RuleMap
	 */
	Quest(List<Integer> categories,
		  List<Integer> requirements,
		  String source,
		  List<String> quest_texts,
		  List<String> comments,
		  List<String> rule_ids,
		  List<QuestConfiguration> conf,
		  List<QuestConfiguration> public_conf) {
		this.categories = Collections.unmodifiableList(categories);
		this.requirements = (requirements == null) ? null : Collections.unmodifiableList(requirements);
		this.source = source;
		this.quest_texts = Collections.unmodifiableList(quest_texts);
		this.comments = (comments == null) ? null : Collections.unmodifiableList(comments);
		this.rule_ids = (rule_ids == null ? null : Collections.unmodifiableList(rule_ids));
		this.conf = conf;
		this.public_conf = public_conf;
	}

	/**
	 * Returns a configuration, chooses one randomly if more possible
	 * configurations exist for this level. If the location is public but no
	 * public configuration exists, a private one is chosen.
	 *
	 * @param location takes a final Game.GAME_ flag.
	 * @param level level to check that the returned configurations level is
	 *                 lower.
	 * @return the chosen configuration.
	 * @see QuestConfiguration
	 */
	public QuestConfiguration getConfiguration(int location, int level) {
		double random = GameDataManager.getRandom();
		if (location == Game.LOCATION_PRIVATE || public_conf == null) {
			if (conf.size() == 1) return conf.get(0);
			List<QuestConfiguration> valid_conf = new ArrayList<QuestConfiguration>();
			//noinspection Convert2streamapi
			for (QuestConfiguration c : conf) {
				if (c.level <= level) valid_conf.add(c);
			}
			return valid_conf.get((int)Math.round(random*(double)(valid_conf.size() -1)));
		} else {
			if (conf.size() == 1) return public_conf.get(0);
			List<QuestConfiguration> valid_conf = new ArrayList<QuestConfiguration>();
			//noinspection Convert2streamapi
			for (QuestConfiguration c : public_conf) {
				if (c.level <= level) valid_conf.add(c);
			}
			return valid_conf.get((int)Math.round(random*(double)(valid_conf.size() -1)));
		}
	}

	/**
	 * Returns the lowest level that is set during a call of
	 * isValid(). If that method was never called, or there is no
	 * valid level, Integer.MAX_INT will be returned.
	 *
	 * @return the lowest valid level.
	 */
	int getMinLevel() { return minLevel; }

	/**
	 * Takes a map with rules and returns a list with all rules that can be
	 * matched witch the ids stored for this quest.
	 *
	 * @param rule_map a RuleMap.
	 * @return list of applied rules.
	 * @see List
	 * @see RuleMap
	 */
	public List<String> getRules(RuleMap rule_map) {
		if (rule_ids == null) return null;
		List<String> rules = new ArrayList<String>();
		//noinspection Convert2streamapi
		for (String id : rule_ids) rules.add(rule_map.getRule(id));
		return Collections.unmodifiableList(rules);
	}

	/**
	 * This method checks if the game is valid on a certain location with a
	 * certain friendship level, sets the lowest valid level that can be
	 * received with getMinLevel(). For that it gets a list of configurations
	 * and calls the private method validFriendshipLevel().
	 *
	 * @param location takes a final Game.GAME_ flag.
	 * @param friendship_level takes a final Game.FRIENDS_ flag.
	 * @param all_players_are null, or a restriction to valid against only one
	 *                           sex set with a final User.SEX_ flag.
	 * @return true if it is a valid Quest; false if not.
	 * @see	Game
	 */
	boolean isValid(int location, int friendship_level, Integer all_players_are, List<Integer> requirements) {
		minLevel = Integer.MAX_VALUE;
		if (this.requirements != null && requirements != null) {
			for (int r : this.requirements) {
				if (!requirements.contains(r)) return false;
			}
		}
		if (location == Game.LOCATION_PRIVATE || public_conf == null) {
			return validFriendshipLevel(conf, friendship_level, all_players_are);
		} else {
			return validFriendshipLevel(public_conf, friendship_level, all_players_are);
		}
	}

	/**
	 * This method checks if the game is valid on a certain location with a
	 * certain friendship level and sets the lowest valid level that can be
	 * received with getMinLevel(). It can also limit the validation to
	 * quests that only need a certain sex, or to be exact: quests that do not
	 * need an other sex.
	 *
	 * @param conf list of possible configurations.
	 * @param friendship_level takes a final Game.FRIENDS_ flag.
	 * @param all_players_are null, or a restriction to valid against only one
	 *                           sex set with a final User.SEX_ flag.
	 * @return true if it is a valid Quest; false if not.
	 * @see Game
	 * @see List
	 * @see	QuestConfiguration
	 */
	private boolean validFriendshipLevel(
			List<QuestConfiguration> conf, int friendship_level, Integer all_players_are) {
		boolean valid = false;
		for (QuestConfiguration c : conf) {
			if (c.level != 0 &&
					(friendship_level == c.friendship_level
							|| c.friendship_level == Game.FRIENDS_LOOSE
							|| friendship_level == Game.FRIENDS_BENEFITS)
					&& (all_players_are == null
					|| (validPlayer(c.player, all_players_are)) && validPartner(c.partner, all_players_are))) {
				if (c.level < minLevel) minLevel = c.level;
				valid = true;
			}
		}
		return valid;
	}

	/**
	 * Returns if the quest is suitable for this arguments.
	 *
	 * @param partner takes a final PARTNER_ flag defined in this class.
	 * @param all_players_are null, or a restriction to valid against only one
	 *                           sex set with a final User.SEX_ flag.
	 * @return true if this quest ist suitable; false otherwise.
	 * @see User
	 */
	private boolean validPartner(int partner, int all_players_are) {
		if (partner == PARTNER_NO || partner == PARTNER_YES || partner == PARTNER_ALL) return true;
		switch (all_players_are) {
			case User.SEX_FEMALE:
				return partner == PARTNER_FEMALE || partner == PARTNER_GIRLS;
			case User.SEX_MALE:
				return partner == PARTNER_MALE || partner == PARTNER_BOYS;
		}
		return false;
	}

	/**
	 * Returns if the quest is suitable for this arguments.
	 *
	 * @param player takes a final PLAYER_ flag defined in this class.
	 * @param all_players_are null, or a restriction to valid against only one
	 *                           sex set with a final User.SEX_ flag.
	 * @return true if this quest ist suitable; false otherwise.
	 * @see User
	 */
	private boolean validPlayer(int player, int all_players_are) {
		if (player == PLAYER_ONE || player == PLAYER_ALL) return true;
		switch (all_players_are) {
			case User.SEX_FEMALE:
				return player == PLAYER_A_FEMALE || player == PLAYER_GIRLS;
			case User.SEX_MALE:
				return player == PLAYER_A_MALE || player == PLAYER_BOYS;
		}
		return false;
	}

	/**
	 * TODO
	 *
	 * @param category_string
	 * @return
     */
	public static Integer getCategoryField(String category_string) {
		if (category_string == null) return null;
		switch (category_string) {
			case CATEGORY_CLOTHES_BACK_ON_STRING:
				return CATEGORY_CLOTHES_BACK_ON;
			case CATEGORY_CONDITION_STRING:
				return CATEGORY_CONDITION;
			case CATEGORY_DARE_STRING:
				return CATEGORY_DARE;
			case CATEGORY_DO_FUNNY_THINGS_STRING:
				return CATEGORY_DO_FUNNY_THINGS;
			case CATEGORY_DRINKING_STRING:
				return CATEGORY_DRINKING;
			case CATEGORY_KISSING_STRING:
				return CATEGORY_KISSING;
			case CATEGORY_MOVE_IT_STRING:
				return CATEGORY_MOVE_IT;
			case CATEGORY_NAUGHTY_STRING:
				return CATEGORY_NAUGHTY;
			case CATEGORY_NEVER_HAVE_I_STRING:
				return CATEGORY_NEVER_HAVE_I;
			case CATEGORY_NON_ALK_DRINKING_STRING:
				return CATEGORY_NON_ALK_DRINKING;
			case CATEGORY_STRIP_CLOTHES_STRING:
				return CATEGORY_STRIP_CLOTHES;
			default:
				return null;
		}
	}

	/**
	 * TODO
	 *
	 * @param delete_string
	 * @return
     */
	public static Integer getDeleteField(String delete_string) {
		if (delete_string == null) return null;
		switch (delete_string) {
			case DELETE_FALSE_STRING:
				return DELETE_FALSE;
			case DELETE_TRUE_STRING:
				return DELETE_TRUE;
			default:
				return null;
		}
	}

	/**
	 * TODO
	 *
	 * @param partner_string
	 * @return
     */
	public static Integer getPartnerField(String partner_string) {
		if (partner_string == null) return null;
		switch (partner_string) {
			case PARTNER_ALL_STRING:
				return PARTNER_ALL;
			case PARTNER_ALL_OPPOSITE_STRING:
				return PARTNER_ALL_OPPOSITE;
			case PARTNER_BOYS_STRING:
				return PARTNER_BOYS;
			case PARTNER_FEMALE_STRING:
				return PARTNER_FEMALE;
			case PARTNER_GIRLS_STRING:
				return PARTNER_GIRLS;
			case PARTNER_MALE_STRING:
				return PARTNER_MALE;
			case PARTNER_NO_STRING:
				return PARTNER_NO;
			case PARTNER_OPPOSITE_SEX_STRING:
				return PARTNER_OPPOSITE_SEX;
			case PARTNER_YES_STRING:
				return PARTNER_YES;
			default:
				return null;
		}
	}

	/**
	 * TODO
	 *
	 * @param player_string
	 * @return
     */
	public static Integer getPlayerField(String player_string) {
		if (player_string == null) return null;
		switch (player_string) {
			case PLAYER_ALL_STRING:
				return PLAYER_ALL;
			case PLAYER_A_FEMALE_STRING:
				return PLAYER_A_FEMALE;
			case PLAYER_A_MALE_STRING:
				return PLAYER_A_MALE;
			case PLAYER_BOYS_STRING:
				return PLAYER_BOYS;
			case PLAYER_GIRLS_STRING:
				return PLAYER_GIRLS;
			case PLAYER_ONE_STRING:
				return PLAYER_ONE;
			default:
				return null;
		}
	}

	/**
	 * TODO
	 *
	 * @param requirement_string
	 * @return
     */
	public static Integer getRequirementField(String requirement_string) {
		if (requirement_string == null) return null;
		switch (requirement_string) {
			case REQUIREMENT_CREAM_STRING:
				return REQUIREMENT_CREAM;
			case REQUIREMENT_DANCE_STRING:
				return REQUIREMENT_DANCE;
			case REQUIREMENT_ICE_CUBE_STRING:
				return REQUIREMENT_ICE_CUBE;
			case REQUIREMENT_POOL_STRING:
				return REQUIREMENT_POOL;
			default:
				return null;
		}
	}

	/**
	 * TODO
	 *
	 * @param skip_string
	 * @return
     */
	public static Integer getSkipField(String skip_string) {
		if (skip_string == null) return null;
		switch (skip_string) {
			case SKIP_FALSE_STRING:
				return SKIP_FALSE;
			case SKIP_TRUE_STRING:
				return SKIP_TRUE;
			default:
				return null;
		}
	}
}