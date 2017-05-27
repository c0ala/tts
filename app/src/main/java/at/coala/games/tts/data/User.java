package at.coala.games.tts.data;

import java.util.List;

import at.coala.games.tts.data.quest.Quest;

/**
 * This class stores all data regarding a User and used for the game. It keeps
 * track on how long ago this user was assigned to a quest of a certain
 * category and processes and stores the level and points this user gains.
 *
 * @author Klaus
 */
public class User {

	/**
	 * A private constant holding the average number of clothes a female user
	 * is wearing.
	 */
	@SuppressWarnings("FieldCanBeLocal")
	private static int AVG_CLOTHES_COUNT_F = 5;

	/**
	 * A private constant holding the average number of clothes a male user is
	 * wearing.
	 */
	@SuppressWarnings("FieldCanBeLocal")
	private static int AVG_CLOTHES_COUNT_M = 4;

	/**
	 * A local variable counting the pieces of clothes the player already put
	 * on (positive value) or off (negative value).
	 */
	private int clothes = 0;

	/**
	 * A local array counting the rounds since the last time the user was part
	 * of a quest of the specific category. Each position can be accessed with
	 * the Quest.CATEGORY_ flag.
	 *
	 * @see Quest
	 */
	private double[] last_call = new double[Quest.CATEGORY_SUM];

	/**
	 * A local variable containing the current level this user has reached. It
	 * starts with 1.
	 */
	private int level = 1;

	/**
	 * if this flag is set, getLastCall() will return very small weights, as if
	 * the player was muted.
	 */
	private boolean mute = false;

	/**
	 * A public field containing the username
	 */
	public final String name;

	/**
	 * A public field containing the sex of this user. It takes one of the
	 * final SEX_ flags defined in this class.
	 */
	public final int sex;

	/**
	 * A local variable containing the points already reached in this level.
	 */
	private double points = 0.0;

	/**
	 * Flag setting the sex to female.
	 */
	public static final int SEX_FEMALE = 0;

	/**
	 * Flag setting the sex to male.
	 */
	public static final int SEX_MALE = SEX_FEMALE + 1;

	/**
	 * Constant describing the user database attribute active.
	 */
	public static final int ATTRIBUTE_ACTIVE = 0;

	/**
	 * Constant describing the user database attribute clothes.
	 */
	public static final int ATTRIBUTE_CLOTHES = ATTRIBUTE_ACTIVE + 1;

	/**
	 * Constant describing the user database attribute level.
	 */
	public static final int ATTRIBUTE_LEVEL = ATTRIBUTE_CLOTHES + 1;

	/**
	 * Constant describing the user database attribute points.
	 */
	public static final int ATTRIBUTE_POINTS = ATTRIBUTE_LEVEL + 1;

	/**
	 * Create a new User.
	 *
	 * @param name name of the user.
	 * @param sex takes a final SEX_ flag defined in this class.
	 */
	public User(String name, int sex) {
		this.name = name;
		this.sex = sex;
		for (int i = 0; i < last_call.length; i++) last_call[i] = 2.1;
	}

	/**
	 * TODO
	 *
	 * @param name
	 * @param sex
	 * @param level
	 * @param clothes
     * @param points
     */
	public User(String name, int sex, int level, int clothes, double points) {
		this.name = name;
		this.sex = sex;
		this.level = level;
		this.clothes = clothes;
		this.points = points;
	}

	/**
	 * The main method for adding points. For each gained level it is harder to
	 * get points. Also a higher quest_level means more points to gain. The
	 * used formula is [avg_points * (1 + (quest_level/user_level)) /
	 * (user_level + 1)]. If this user reaches the boundary of 100
	 * points, he gets into the next level, the points start again with 0, and
	 * the return value is true.
	 *
	 * @param avg_points points this user can get or loose under normal
	 *                      conditions.
	 * @param quest_level a level that should be between 1 and 10
	 * @param full_points true to add the full points, false to only add half of them
	 * @return true if the user reached a new level; false otherwise.
	 */
	private boolean addPoints(double avg_points, int quest_level, boolean full_points) {
		points += ( ((avg_points * (1.0 + ((double)quest_level / (double)level))) / (double)(level + 1)) /(full_points ? 1 : 4) );
		if (points >= 100.0) {
			level += 1;
			points = 0.0;
			return true;
		} return false;
	}

	/**
	 * Indicates whether some other object is "equal to" this User. Overrides
	 * the method of java.lang.Object.
	 *
	 * @param obj the reference object with which to compare.
	 * @return true if this object is the same as the obj argument; false
	 * otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof User && equals((User) obj);
	}

	/**
	 * Indicates whether some other User is "equal to" this User.
	 *
	 * @param that the reference object with which to compare.
	 * @return true if this User is the same as the input argument; false
	 * otherwise.
	 */
	public boolean equals(User that) {
		return that != null && this.name.equals(that.name);
	}

	/**
	 * Returns the rounds since the last call of this category for this player.
	 * If a category is Quest.CATEGORY_STRIP_CLOTHES and this user has not lost
	 * his clothes, than the weight for Quest.CATEGORY_STRIP_CLOTHES is
	 * multiplied with [AVG_CLOTHES_COUNT_ - clothes] so that this player gets
	 * a gap to these who have already lost some clothes. If category is
	 * Quest.CATEGORY_CLOTHES_BACK_ON and the clothes counter is positive, than
	 * the weight is divided through the clothes count.
	 *
	 * @param categories takes a list of final Quest.CATEGORY_ flags.
	 * @return a non_negative weight.
	 * @see List
	 * @see Quest
	 */
	double getLastCall(List<Integer> categories) {
		if (mute) {
			mute = false;
			return 1.0;
		}
		double weight = 0.0;
		for (int category : categories) {
			if (category == Quest.CATEGORY_STRIP_CLOTHES) {
				int current_clothes = (sex == User.SEX_FEMALE ? AVG_CLOTHES_COUNT_F : AVG_CLOTHES_COUNT_M) + clothes;
				if (current_clothes > 0) {
					weight += last_call[category] * (double) current_clothes;
				} else if (current_clothes < 0) {
					weight += last_call[category] / (double) current_clothes;
				} else weight += last_call[category];
			} else if (category == Quest.CATEGORY_CLOTHES_BACK_ON && clothes > 0) {
				weight += last_call[category] / (double) clothes;
			} else weight += last_call[category];
		}
		return weight;
	}

	/**
	 * Returns the current level this User has reached.
	 *
	 * @return the current level.
	 */
	public int getLevel() { return level; }

	/**
	 * TODO
	 *
	 * @return
     */
	public double getPoints() { return points; }

	/**
	 * TODO
	 *
	 * @return
     */
	public int getClothes() { return clothes; }

	/**
	 * Resets all game data for a new game.
	 */
	void resetGame() {
		clothes = 0;
		level = 1;
		points = 0.0;
	}

	/**
	 * This method resets the counting since the last time this user was part
	 * of a quest of this category and increments the counter for this
	 * category. This method should be called once every round.
	 *
	 * @param categories takes a list of final Quest.CATEGORY_ flags.
	 * @param reset true to reset the count for this category; false otherwise.
	 * @param full_reset true to reset the count fully; false to only half it.
	 * @see List
	 * @see Quest
	 */
	void setLastCall(List<Integer> categories, boolean reset, boolean full_reset) {
		for (int i = 0; i < last_call.length; i++) last_call[i]++;
		if (reset) {
			for (int category : categories) {
				if (full_reset) {
					last_call[category] = 0.0;
					mute = true;
				} else last_call[category] /= 2.0;
			}
		}
	}

	/**
	 * This method sets points gained by this player. For each category and
	 * quest_level the points differ. Quests normally are rated positive if
	 * done and negative if skipped. Only Quests of Quests.CATEGORY_CLOTHES_BACK_ON
	 * are rated negative if done and positive if skipped. The calculating takes place in
	 *
	 * @param categories takes a list of final Quest.CATEGORY_ flags.
	 * @param quest_level a level that should be between 1 and 10
	 * @param full_points true to add the full points; false to only add half of them
	 * @param success true if the quest was done; false if it was skipped
	 * @return true if the user reached a new level; false otherwise.
	 * @see Quest
	 */
	boolean setPoints(List<Integer> categories, int quest_level, boolean full_points, boolean success) {
		double points = 0.0;
		for (int category : categories) {
			/**
			 * TODO
			 * points can be changed in settings //alternative: //points as final fields
			 * match with Game.getCategory() to optimize
			 */
			switch (category) {
				case Quest.CATEGORY_CLOTHES_BACK_ON:
					clothes++;
					points += success ? -100.0 : 100.0;
					break;
				case Quest.CATEGORY_CONDITION:
					points += success ? 50.0 : -50.0;
					break;
				case Quest.CATEGORY_DO_FUNNY_THINGS:
					points += success ? 25.0 : -25.0;
					break;
				case Quest.CATEGORY_DRINKING:
					points += success ? 25.0 : -25.0;
					break;
				case Quest.CATEGORY_KISSING:
					points += success ? 50.0 : -50.0;
					break;
				case Quest.CATEGORY_MOVE_IT:
					points += success ? 75.0 : -75.0;
					break;
				case Quest.CATEGORY_NAUGHTY:
					points += success ? 75.0 : -75.0;
					break;
				case Quest.CATEGORY_NEVER_HAVE_I:
					points += success ? 50.0 : -50.0;
					break;
				case Quest.CATEGORY_NON_ALK_DRINKING:
					points += 10.0 * (double)level;
					break;
				case Quest.CATEGORY_DARE:
					points += success ? 100.0 : -100.0;
					break;
				case Quest.CATEGORY_STRIP_CLOTHES:
					if (full_points) clothes--;
					points += success ? 100.0 : -100.0;
					break;
			}
		}
		points /= categories.size();
		return addPoints(points, quest_level, full_points);
	}

	/**
	 * TODO documentation
	 */
	void startDrunk() {
		level = level > 4 ? level : 4;
	}
}
