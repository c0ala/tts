package at.coala.games.tts.data.quest;

import java.util.ArrayList;
import java.util.List;

import at.coala.games.tts.data.Game;
import at.coala.games.tts.data.Settings;

/**
 * This QuestCreator provides a simple way to create new Quest elements. To
 * prepare a new Quest call startNewQuest() with all necessary arguments. After
 * that you can add more data with additional add-methods or craft the data to
 * a quest with craftQuest().
 *
 * @author Klaus
 * @see Quest
 */
public class QuestCreator {

    /**
     * A local list of final Quest.CATEGORY_ flags.
     *
     * @see List
     * @see Quest
     */
    private List<Integer> categories;

    /**
     * A local list of comments, or null of none exist for the prepared quest.
     *
     * @see List
     */
    private List<String> comments;

    /**
     * A local list of configurations, or null of none exist for the prepared
     * quest.
     *
     * @see List
     * @see QuestConfiguration
     */
    private List<QuestConfiguration> conf;

    /**
     * A local list of configurations for public locations, or null of none
     * exist for the prepared quest.
     *
     * @see List
     * @see QuestConfiguration
     */
    private List<QuestConfiguration> public_conf;

    /**
     * A local variable containing the requirements for the prepared quest, or
     * null of none exist.
     */
    private List<Integer> requirements;

    /**
     * A local list of rule ids, or null of none exist for the prepared quest.
     *
     * @see List
     * @see RuleMap
     */
    private List<String> rule_ids;

    /**
     * A local variable containing the source for the prepared quest, or null
     * if no exists.
     */
    private String source;

    /**
     * A local variable containing the quest text for the prepared quest.
     */

    /**
     * A local list of quest texts.
     *
     * @see List
     */
    private List<String> text;

    /**
     * Flag set true if a quest is prepared currently, false if not.
     */
    private boolean quest_in_preparation = false;

    /**
     * Aborts the preparation. craftQuest() will return null after this call.
     */
    public void abort() { quest_in_preparation = false; }

    /**
     * TODO
     *
     * @param category
     */
    public void addCategory(int category) {
        categories.add(category);
    }

    /**
     * Adds a comment to the quest prepared currently.
     *
     * @param comment a comment.
     */
    public void addComment(String comment) {
        if (comments == null) comments = new ArrayList<String>(1);
        comments.add(comment);
    }

    /**
     * Adds an additional configuration to the quest prepared currently. For
     * null-arguments the values stored in the last added configuration for the
     * specific location will be used. The first public location configuration
     * falls back to the last private one in case of null-arguments.
     *
     * @param location takes a final Game.LOCATION_ flag.
     * @param level should be an Integer between 1 and 10.
     * @param player takes a String representation of the final Quest.PLAYER_
     *               flag, or can be null due to a default value.
     * @param partner takes a String representation of the final Quest.PARTNER_
     *                flag, or can be null due to a default value.
     * @param friendship_level takes a String representation of the final
     *                         Game.FRIENDS_ flag, or can be null due to a
     *                         default value.
     * @param skip takes a String representation of the final Quest.SKIP_ flag,
     *             or can be null due to a default value.
     * @param allow_delete takes a String representation of the final
     *                     Quest.DELETE_ flag, or can be null due to a default
     *                     value.
     * @see Game
     * @see Quest
     */
    public void addConfiguration(int location, int level, Integer player, Integer partner, Integer friendship_level, Integer skip, Integer allow_delete) {
        if (location == Game.LOCATION_PUBLIC) {
            if (public_conf == null) {
                public_conf = new ArrayList<QuestConfiguration>();
                addConfiguration(public_conf, level, player, partner, friendship_level, skip, allow_delete, conf.get(conf.size() - 1));
            } else {
                addConfiguration(public_conf, level, player, partner, friendship_level, skip, allow_delete, public_conf.get(public_conf.size() - 1));
            }
        } else {
            addConfiguration(conf, level, player, partner, friendship_level, skip, allow_delete, conf.get(conf.size() - 1));
        }
    }

    /**
     * Adds a configuration to the list parameter. For null-arguments the
     * values stored in the committed QuestConfiguration are used.
     *
     * @param list list to add the configuration.
     * @param level should be an Integer between 1 and 10.
     * @param player takes a String representation of the final Quest.PLAYER_
     *               flag, or can be null due to a default value.
     * @param partner takes a String representation of the final Quest.PARTNER_
     *                flag, or can be null due to a default value.
     * @param friendship_level takes a String representation of the final
     *                         Game.FRIENDS_ flag, or can be null due to a
     *                         default value.
     * @param skip takes a String representation of the final Quest.SKIP_ flag,
     *             or can be null due to a default value.
     * @param allow_delete takes a String representation of the final
     *                     Quest.DELETE_ flag, or can be null due to a default
     *                     value.
     * @param conf the configuration to fall back in case of null-arguments.
     * @see Game
     * @see List
     * @see Quest
     * @see QuestConfiguration
     */
    private void addConfiguration(
            List<QuestConfiguration> list,
            int level,
            Integer player,
            Integer partner,
            Integer friendship_level,
            Integer skip,
            Integer allow_delete,
            QuestConfiguration conf) {
        list.add(new QuestConfiguration(
                level,
                (player == null) ? conf.player: player,
                (partner == null) ? conf.partner : partner,
                (friendship_level == null) ? conf.friendship_level : friendship_level,
                (skip == null) ? conf.skip : skip,
                (allow_delete == null) ? conf.allow_delete : allow_delete));
    }

    /**
     * TODO
     *
     * @param requirement
     */
    public void addRequirement(int requirement) {
        if (requirements == null) requirements = new ArrayList<Integer>(1);
        requirements.add(requirement);
    }

    /**
     * Adds an id for an existing quest prepared currently.
     *
     * @param rule_id a rule id.
     * @see RuleMap
     */
    public void addRuleId(String rule_id) {
        if (rule_ids == null) rule_ids = new ArrayList<String>(1);
        rule_ids.add(rule_id);
    }

    /**
     * Adds a source to the quest prepared currently.
     *
     * @param source a source.
     */
    public void addSource(String source) { this.source = source; }

    /**
     * Adds an alternative quest text for an existing quest prepared currently.
     *
     * @param text an alternative quest text.
     */
    public void addText(String text) {
        this.text.add(text);
    }

    /**
     * Crafts the Quest element with set data and configuration and returns it.
     * A Quest can only be returned once.
     *
     * @return the crafted Quest, or null if no Quest was prepared yet or the
     * prepared Quest was returned previously.
     * @see Quest
     */
    public Quest craftQuest() {
        if (quest_in_preparation) {
            quest_in_preparation = false;
            return new Quest(categories, requirements, source, text, comments, rule_ids, conf, public_conf);
        } else return null;
    }

    /**
     * Returns if a quest is in preparation currently.
     *
     * @return false before the first call and after every call of
     * craftQuest(); true after every call of startNewQuest();
     */
    public boolean getInPreparation() { return quest_in_preparation; }

    /**
     * Clears all previous data and prepares a new quest. For some arguments
     * default values exist, so null is a valid argument for everything but
     * category, text and level. Data for the first configuration (level,
     * player, partner, friendship_level, skip, allow_delete) is stored in an
     * private location configuration and filled, if arguments are null, with
     * default values (for more information to default values, see the
     * including classes of each flag).
     *
     * @param category takes a final Quest.CATEGORY_ flag.
     * @param text the text describing what to do in this quest, must not be
     *             null.
     * @param level should be an Integer between 1 and 10.
     * @param player takes a String representation of the final Quest.PLAYER_
     *               flag, or can be null due to a default value.
     * @param partner takes a String representation of the final Quest.PARTNER_
     *                flag, or can be null due to a default value.
     * @param friendship_level takes a String representation of the final
     *                         Game.FRIENDS_ flag, or can be null due to a
     *                         default value.
     * @param skip takes a String representation of the final Quest.SKIP_ flag,
     *             or can be null due to a default value.
     * @param allow_delete takes a String representation of the final
     *                     Quest.DELETE_ flag, or can be null due to a default
     *                     value.
     * @see Game
     * @see Quest
     */
    public void startNewQuest(int category, String text, int level, Integer player, Integer partner, Integer friendship_level, Integer skip, Integer allow_delete) {
        categories = new ArrayList<Integer>(1);
        categories.add(category);
        this.text = new ArrayList<String>(1);
        this.text.add(text);
        conf = new ArrayList<QuestConfiguration>(1);
        conf.add(new QuestConfiguration(
                level,
                (player == null) ? Settings.DEFAULT_PLAYER : player,
                (partner == null) ? Settings.DEFAULT_PARTNER : partner,
                (friendship_level == null) ? Settings.DEFAULT_FRIENDS : friendship_level,
                (skip == null) ? Settings.DEFAULT_SKIP : skip,
                (allow_delete == null) ? Settings.DEFAULT_DELETE : allow_delete));
        source = null;
        comments = null;
        rule_ids = null;
        public_conf = null;
        requirements = null;

        quest_in_preparation = true;
    }
}
