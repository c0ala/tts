package at.coala.games.tts.data;

import java.util.ArrayList;
import java.util.List;

import at.coala.games.tts.data.quest.Quest;
import at.coala.games.tts.game.GameDataManager;

/**
 * // TODO documentation of fields and methods
 *
 * @author Klaus
 */
public class Settings {

    //TODO
    public static final long MAX_MILLIS_TO_RESUME_GAME = 12 * 60 * 60 * 1000;

    /**
     * A constant holding the default allow_delete flag.
     */
    public static final int DEFAULT_DELETE = Quest.DELETE_FALSE;

    public static final int DEFAULT_FRIENDS = Game.FRIENDS_LOOSE;

    public static final int DEFAULT_LOCATION = Game.LOCATION_PRIVATE;

    /**
     * A constant holding the default partner.
     */
    public static final int DEFAULT_PARTNER = Quest.PARTNER_NO;

    /**
     * A constant holding the default player, a final Quest.PLAYER_ flag.
     */
    public static final int DEFAULT_PLAYER = Quest.PLAYER_ALL;

    public static final int DEFAULT_SKIP = Quest.SKIP_FALSE;

    /**
     * TODO
     * static fields and methods
     */

    private int[] default_probability = null;
    private int[] custom_probability = null;

    private static final int DEFAULT_PROBABILITY_CLOTHES_BACK_ON = 2;
    private static final int DEFAULT_PROBABILITY_CONDITION = 10;
    private static final int DEFAULT_PROBABILITY_DARE = 1;
    private static final int DEFAULT_PROBABILITY_DO_FUNNY_THINGS = 5;
    private static final int DEFAULT_PROBABILITY_DRINKING = 45;
    private static final int DEFAULT_PROBABILITY_KISSING = 5;
    private static final int DEFAULT_PROBABILITY_MOVE_IT = 1;
    private static final int DEFAULT_PROBABILITY_NAUGHTY = 10;
    private static final int DEFAULT_PROBABILITY_NEVER_HAVE_I = 15;
    private static final int DEFAULT_PROBABILITY_NON_ALK_DRINKING = 1;
    private static final int DEFAULT_PROBABILITY_STRIP = 5;

    private List<Integer> custom_requirements = new ArrayList<Integer>();

    private static List<Integer> default_requirements = new ArrayList<Integer>();

    private int friendship_level = Game.FRIENDS_LOOSE;

    private int location = Game.LOCATION_PRIVATE;

    // TODO documentation
    public static final int ATTRIBUTE_ALREADY_DRUNK = 0;
    public static final int ATTRIBUTE_CUSTOM_SETTINGS = ATTRIBUTE_ALREADY_DRUNK + 1;
    public static final int ATTRIBUTE_FRIENDSHIP_LEVEL = ATTRIBUTE_CUSTOM_SETTINGS + 1;
    public static final int ATTRIBUTE_GAME_LOCATION = ATTRIBUTE_FRIENDSHIP_LEVEL + 1;
    public static final int ATTRIBUTE_REQUIREMENT_CREAM = ATTRIBUTE_GAME_LOCATION + 1;
    public static final int ATTRIBUTE_REQUIREMENT_DANCE = ATTRIBUTE_REQUIREMENT_CREAM + 1;
    public static final int ATTRIBUTE_REQUIREMENT_ICE_CUBE = ATTRIBUTE_REQUIREMENT_DANCE + 1;
    public static final int ATTRIBUTE_REQUIREMENT_POOL = ATTRIBUTE_REQUIREMENT_ICE_CUBE + 1;

    // TODO documentation
    private static final String ATTRIBUTE_STRING_ALREADY_DRUNK = "already_drunk";
    private static final String ATTRIBUTE_STRING_CUSTOM_SETTINGS = "custom_settings";
    private static final String ATTRIBUTE_STRING_FRIENDSHIP_LEVEL = "friendship_level";
    private static final String ATTRIBUTE_STRING_GAME_LOCATION = "game_location";
    private static final String ATTRIBUTE_STRING_REQUIREMENT_CREAM = "requirement_cream";
    private static final String ATTRIBUTE_STRING_REQUIREMENT_DANCE = "requirement_dance";
    private static final String ATTRIBUTE_STRING_REQUIREMENT_ICE_CUBE = "requirement_ice_cube";
    private static final String ATTRIBUTE_STRING_REQUIREMENT_POOL = "requirement_pool";

    /**
     * TODO
     *
     */
    public Settings() {

    }

    /**
     * TODO
     *
     * @param friendship_level
     */
    public void setFriendshipLevel(int friendship_level) {
        this.friendship_level = friendship_level;
        calculateCategoryProbability();
    }

    /**
     * TODO
     *
     * @return
     */
    public int getFriendshipLevel() { return friendship_level; }

    /**
     * TODO
     *
     * @return
     */
    public int[] getCategoryProbability() {
        if (custom_probability != null) return custom_probability;
        if (default_probability == null) calculateCategoryProbability();
        return default_probability;
    }

    /**
     * TODO
     */
    private void calculateCategoryProbability() {
        int pc = GameDataManager.getPlayerCount();

        if (default_probability == null) default_probability = new int[Quest.CATEGORY_SUM];
        default_probability[Quest.CATEGORY_CLOTHES_BACK_ON] = DEFAULT_PROBABILITY_CLOTHES_BACK_ON
                + (friendship_level == Game.FRIENDS_BENEFITS
                ? 1 : 0);

        default_probability[Quest.CATEGORY_CONDITION] = DEFAULT_PROBABILITY_CONDITION
                + (friendship_level == Game.FRIENDS_BENEFITS
                ? -5 : 0);

        default_probability[Quest.CATEGORY_DARE] = DEFAULT_PROBABILITY_DARE
                + (friendship_level == Game.FRIENDS_BENEFITS
                ? (pc > 9 ? 0 : pc > 7 ? 1 : pc > 5 ? 2 : pc > 3 ? 3 : 4) : 0);

        default_probability[Quest.CATEGORY_DO_FUNNY_THINGS] = DEFAULT_PROBABILITY_DO_FUNNY_THINGS
                + (friendship_level == Game.FRIENDS_LOOSE
                ? 5 : friendship_level == Game.FRIENDS_BENEFITS
                ? -5 : 0);

        default_probability[Quest.CATEGORY_DRINKING] = DEFAULT_PROBABILITY_DRINKING;

        default_probability[Quest.CATEGORY_KISSING] = DEFAULT_PROBABILITY_KISSING
                + (friendship_level == Game.FRIENDS_GOOD
                ? (pc > 9 ? 4 : pc > 7 ? 3 : pc > 5 ? 2 : pc > 3 ? 1 : 0) : friendship_level == Game.FRIENDS_BENEFITS
                ? (pc > 9 ? 5 : pc > 7 ? 4 : pc > 5 ? 3 : pc > 3 ? 2 : 1) : 0);

        default_probability[Quest.CATEGORY_MOVE_IT] = DEFAULT_PROBABILITY_MOVE_IT;

        default_probability[Quest.CATEGORY_NAUGHTY] = DEFAULT_PROBABILITY_NAUGHTY
                + (friendship_level == Game.FRIENDS_LOOSE
                ? (pc > 9 ? -9 : pc > 7 ? -8 : pc > 5 ? -7 : pc > 3 ? -6 : -5) : friendship_level == Game.FRIENDS_GOOD
                ? (pc > 9 ? -8 : pc > 7 ? -6 : pc > 5 ? -4 : pc > 3 ? -2 : 0) : (pc > 9 ? 6 : pc > 7 ? 7 : pc > 5 ? 8 : pc > 3 ? 9 : 10));

        default_probability[Quest.CATEGORY_NEVER_HAVE_I] = DEFAULT_PROBABILITY_NEVER_HAVE_I
                + (friendship_level == Game.FRIENDS_LOOSE
                ? (pc > 9 ? 4 : pc > 7 ? 3 : pc > 5 ? 2 : pc > 3 ? 1 : 0) : friendship_level == Game.FRIENDS_BENEFITS
                ? -5 : 0);

        default_probability[Quest.CATEGORY_NON_ALK_DRINKING] = DEFAULT_PROBABILITY_NON_ALK_DRINKING;

        default_probability[Quest.CATEGORY_STRIP_CLOTHES] = DEFAULT_PROBABILITY_STRIP
                + (friendship_level == Game.FRIENDS_GOOD
                ? (pc > 9 ? 4 : pc > 7 ? 3 : pc > 5 ? 2 : pc > 3 ? 1 : 0) : friendship_level == Game.FRIENDS_BENEFITS
                ? (pc > 9 ? 5 : pc > 7 ? 4 : pc > 5 ? 3 : pc > 3 ? 2 : 1) : 0);
    }

    /**
     * TODO
     *
     * @param location
     */
    public void setLocation(int location) { this.location = location; }

    /**
     * TODO
     *
     * @return
     */
    public int getLocation() { return location; }

    /**
     * TODO
     *
     * @param requirement
     */
    public void addRequirement(int requirement) {
        if (!custom_requirements.contains(requirement)) custom_requirements.add(requirement);
    }

    /**
     * TODO
     *
     * @param requirement
     */
    public void removeRequirement(int requirement) {
        while (custom_requirements.contains(requirement))
            custom_requirements.remove(Integer.valueOf(requirement));
    }

    /**
     * TODO
     *
     * @return
     */
    public List<Integer> getRequirements() {
        List<Integer> ret = new ArrayList<>();
        for (int i : custom_requirements) ret.add(i);
        return ret;
    }

    /**
     * TODO
     *
     * @return
     */
    public static List<Integer> getRequirementsDefault() {
        List<Integer> ret = new ArrayList<>();
        for (int i : default_requirements) ret.add(i);
        return ret;
    }

    /**
     * TODO
     *
     * @param setting_attribute_field
     * @return
     */
    public static String getSettingsAttributeString(int setting_attribute_field) {
        switch (setting_attribute_field) {
            case ATTRIBUTE_CUSTOM_SETTINGS:
                return ATTRIBUTE_STRING_CUSTOM_SETTINGS;
            case ATTRIBUTE_FRIENDSHIP_LEVEL:
                return ATTRIBUTE_STRING_FRIENDSHIP_LEVEL;
            case ATTRIBUTE_GAME_LOCATION:
                return ATTRIBUTE_STRING_GAME_LOCATION;
            case ATTRIBUTE_REQUIREMENT_CREAM:
                return ATTRIBUTE_STRING_REQUIREMENT_CREAM;
            case ATTRIBUTE_REQUIREMENT_ICE_CUBE:
                return ATTRIBUTE_STRING_REQUIREMENT_ICE_CUBE;
            case ATTRIBUTE_REQUIREMENT_POOL:
                return ATTRIBUTE_STRING_REQUIREMENT_POOL;
            case ATTRIBUTE_REQUIREMENT_DANCE:
                return ATTRIBUTE_STRING_REQUIREMENT_DANCE;
            default:
                return null;
        }
    }

    /**
     * TODO
     *
     * @param requirements_field
     * @return
     */
    public static Integer getSettingsAttributeRequirementInt(int requirements_field) {
        switch (requirements_field) {
            case Quest.REQUIREMENT_CREAM:
                return ATTRIBUTE_REQUIREMENT_CREAM;
            case Quest.REQUIREMENT_DANCE:
                return ATTRIBUTE_REQUIREMENT_DANCE;
            case Quest.REQUIREMENT_ICE_CUBE:
                return ATTRIBUTE_REQUIREMENT_ICE_CUBE;
            case Quest.REQUIREMENT_POOL:
                return ATTRIBUTE_REQUIREMENT_POOL;
            default:
                return null;
        }
    }
}
