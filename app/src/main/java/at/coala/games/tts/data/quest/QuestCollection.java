package at.coala.games.tts.data.quest;

import java.util.List;

import at.coala.games.tts.game.GameDataManager;

/**
 * The collection stores a large number of quests. It provides different
 * settings to improve the linking and return a proper random Quest element.
 * This new QuestCollection replaces the QuestList used in the Alpha-Version.
 * The most important methods are addQuest() to add new quests and
 * getRandomQuest() which is the only way to receive quests at the moment.
 *
 * @author Klaus
 * @see Quest
 */
public class QuestCollection {

    /**
     * A local array containing the first linked element of the specific
     * category. Each position can be accessed with the Quest.CATEGORY_ flag.
     *
     * @see Quest
     * @see at.coala.games.tts.data.quest.QuestCollection.QuestCollectionElement
     */
    private QuestCollectionElement[] firstLinked = new QuestCollectionElement[Quest.CATEGORY_SUM];

    /**
     * A local array containing the first valid element of the specific
     * category. Each position can be accessed with the Quest.CATEGORY_ flag.
     *
     * @see Quest
     * @see at.coala.games.tts.data.quest.QuestCollection.QuestCollectionElement
     */
    private QuestCollectionElement[] firstValid = new QuestCollectionElement[Quest.CATEGORY_SUM];

    /**
     * A local array containing the first element of the specific category.
     * Each position can be accessed with the Quest.CATEGORY_ flag.
     *
     * @see Quest
     * @see at.coala.games.tts.data.quest.QuestCollection.QuestCollectionElement
     */
    private QuestCollectionElement[] head = new QuestCollectionElement[Quest.CATEGORY_SUM];

    /**
     * A local array containing the last element of the specific category. Each
     * position can be accessed with the Quest.CATEGORY_ flag.
     *
     * @see Quest
     * @see at.coala.games.tts.data.quest.QuestCollection.QuestCollectionElement
     */
    private QuestCollectionElement[] last = new QuestCollectionElement[Quest.CATEGORY_SUM];

    /**
     * The quest last returned.
     *
     * @see at.coala.games.tts.data.quest.QuestCollection.QuestCollectionElement
     */
    private QuestCollectionElement last_returned = null;

    /**
     * A local variable containing the current level;
     */
    private int level = 0;

    /**
     * A local array containing the number of linked elements for the specific
     * category. Each position can be accessed with the Quest.CATEGORY_ flag.
     *
     * @see Quest
     */
    private int[] linked_size = new int[Quest.CATEGORY_SUM];

    /**
     * A local array containing the number of linked quest texts for the
     * specific category. Each position can be accessed with the
     * Quest.CATEGORY_ flag.
     *
     * @see Quest
     */
    private int[] linked_quest_text_count = new int[Quest.CATEGORY_SUM];

    /**
     * A local array containing the number of valid quest texts for the
     * specific category. Each position can be accessed with the
     * Quest.CATEGORY_ flag.
     *
     * @see Quest
     */
    private int[] valid_quest_text_count = new int[Quest.CATEGORY_SUM];


    /**
     * Add a Quest element.
     *
     * @param quest the quest to add.
     */
    public void addQuest(Quest quest) {
        QuestCollectionElement newElem = new QuestCollectionElement(quest);
        for (int c : quest.categories) {
            if (head[c] == null) {
                head[c] = newElem;
                last[c] = head[c];
            } else {
                last[c].next[c] = newElem;
                last[c] = last[c].next[c];
            }
        }
    }

    /**
     * Get the count of all linked quest texts.
     *
     * @return the quest text count.
     */
    public int getLinkedQuestTextCount() {
        int count = 0;
        for (int s : linked_quest_text_count) count += s;
        return count;
    }

    /**
     * This method returns a random quest with regard to the level argument and
     * current validation, or null if no suitable quest could be found.
     *
     * @param level the new level, should be an Integer between 1 and 10
     * @param category takes a final Quest.CATEGORY_ flag
     * @return a quest of the specified category, or null if no valid quest could be found
     * @see Quest
     */
    public Quest getRandomQuest(int level, int category) {
        if (this.level != level) {
            this.level = level;
            linkCollection();
        }
        last_returned = firstLinked[category];
        if (last_returned == null) return null;
        double random = GameDataManager.getRandom() * (double)(linked_size[category]);
        for (int i = 1; i < Math.round(random); i++) {
            last_returned = last_returned.linked[category];
        }
        return last_returned.elem;
    }

    /**
     * Get the count of all valid quest texts.
     *
     * @return the quest text count.
     */
    public int getValidQuestTextCount() {
        int count = 0;
        for (int s : valid_quest_text_count) count += s;
        return count;
    }

    /**
     * Links quests with regard to the current level and validation. If settings
     * have changed, this method first calls validateCollection().
     */
    private void linkCollection() {
        for (int i = 0; i <  firstLinked.length; i++) {
            QuestCollectionElement next = firstValid[i];
            while (next != null && (next.elem.getMinLevel() > level || next.deleted)) next = next.valid[i];
            if (next != null) {
                firstLinked[i] = next;
                linked_size[i] = 1;
                linked_quest_text_count[i] = next.elem.quest_texts.size();
                QuestCollectionElement lastLinked = next;
                while (next.valid[i] != null) {
                    next = next.valid[i];
                    if (next.elem.getMinLevel() <= level && !next.deleted) {
                        lastLinked.linked[i] = next;
                        lastLinked = next;
                        linked_size[i]++;
                        linked_quest_text_count[i] += next.elem.quest_texts.size();
                    }
                    next.linked[i] = null;
                }
            } else {
                firstLinked[i] = null;
                linked_size[i] = 0;
                linked_quest_text_count[i] = 0;
            }
        }
    }

    /**
     * Sets the delete flag to not asked the current question again.
     */
    public void setDeleteFlag() {
        if (last_returned != null) {
            last_returned.deleted = true;
            linkCollection();
        }
    }

    /**
     * Validates these collection against the settings.
     *
     * @param location
     * @param friendship_level
     * @param all_players_are
     * @param requirements
     */
    public void validateCollection(int location, int friendship_level, Integer all_players_are, List<Integer> requirements) {
        for (int i = 0; i <  firstValid.length; i++) {
            QuestCollectionElement next = head[i];
            while (next != null && !next.elem.isValid(location, friendship_level, all_players_are, requirements)) {
                next = next.next[i];
            }
            if (next != null) {
                firstValid[i] = next;
                valid_quest_text_count[i] = next.elem.quest_texts.size();
                QuestCollectionElement lastValid = next;
                while (next.next[i] != null) {
                    next = next.next[i];
                    if (next.elem.isValid(location, friendship_level, all_players_are, requirements)) {
                        lastValid.valid[i] = next;
                        lastValid = next;
                        valid_quest_text_count[i] += next.elem.quest_texts.size();
                    }
                    next.valid[i] = null;
                }
            } else {
                firstValid[i] = null;
                valid_quest_text_count[i] = 0;
            }
        }
    }

    /**
     * This class contains an element for the QuestCollection. It also contains
     * links to the next element and a not yet implemented delete flag.
     */
    class QuestCollectionElement {

        /**
         * The nested Quest element.
         *
         * @see Quest
         */
        final Quest elem;

        /**
         * Flag set true if this element should be ignored, false otherwise.
         */
        boolean deleted = false;

        /**
         * Field containing the next linked element in this collection, or null
         * if no next linked element exists.
         */
        QuestCollectionElement[] linked = new QuestCollectionElement[Quest.CATEGORY_SUM];

        /**
         * Field containing the next element in this collection, or null if no
         * next element exists.
         */
        QuestCollectionElement[] next = new QuestCollectionElement[Quest.CATEGORY_SUM];

        /**
         * Field containing the next valid element in this collection, or null
         * if no next valid element exists.
         */
        QuestCollectionElement[] valid = new QuestCollectionElement[Quest.CATEGORY_SUM];


        /**
         * Creates a new QuestCollection element.
         *
         * @param elem a Quest.
         * @see Quest
         */
        QuestCollectionElement(Quest elem) {
            this.elem = elem;
        }
    }
}
