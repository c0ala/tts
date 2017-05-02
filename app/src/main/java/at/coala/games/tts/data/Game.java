package at.coala.games.tts.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import at.coala.games.tts.data.quest.Quest;
import at.coala.games.tts.data.quest.QuestConfiguration;
import at.coala.games.tts.game.GameDataManager;

/**
 * This class stores and maintains the players through a game.
 *
 * @author Klaus
 * @see User
 */
public class Game {

    /**
     * A local variable containing the average level of all player.
     */
    private double avg_level = 1.0;

    /**
     * A local variable containing the current quest configuration
     */
    private QuestConfiguration conf;

    /**
     * A local list containing the current second player or partner, or none if
     * no specific partner exists.
     *
     * @see User
     */
    private User currentPartner;

    /**
     * A local list containing the current player, or none if no specific
     * player exists.
     *
     * @see User
     */
    private User currentPlayer;

    /**
     * Flag setting the friendship to a very close and intimate one.
     */
    public static final int FRIENDS_BENEFITS = 0;

    /**
     * String representation of final flag FRIENDS_BENEFITS.
     */
    private static final String FRIENDS_BENEFITS_STRING = "BENEFITS";

    /**
     * Flag setting the friendship to a good one.
     */
    public static final int FRIENDS_GOOD = FRIENDS_BENEFITS + 1;

    /**
     * String representation of final flag FRIENDS_GOOD.
     */
    private static final String FRIENDS_GOOD_STRING = "GOOD";

    /**
     * Flag setting the friendship to a loose level.
     */
    public static final int FRIENDS_LOOSE = FRIENDS_GOOD + 1;

    /**
     * String representation of final flag FRIENDS_LOOSE.
     */
    private static final String FRIENDS_LOOSE_STRING = "LOOSE";

    /**
     * A local array counting the rounds since the last time a quest was part
     * of a quest of the specific category. Each position can be accessed with
     * the Quest.CATEGORY_ flag.
     *
     * @see Quest
     */
    private double[] last_call = new double[Quest.CATEGORY_SUM];

    /**
     * Flag setting the location to a private one.
     */
    public static final int LOCATION_PRIVATE = 0;

    /**
     * Flag setting the location to somewhere public.
     */
    public static final int LOCATION_PUBLIC = LOCATION_PRIVATE + 1;

    /**
     * A local variable containing the player count.
     */
    @Deprecated
    private double player_count = 0.0;

    /**
     * TODO
     */
    private List<User> player_all = new LinkedList<>();

    /**
     * A local list of all female players
     *
     * @see List
     * @see User
     */
    @Deprecated
    private List<User> player_f = new LinkedList<>();

    /**
     * A local list of all female players
     *
     * @see List
     * @see User
     */
    @Deprecated
    private List<User> player_m = new LinkedList<>();

    /**
     * TODO
     */
    private int[] adjustment;

    /**
     * TODO
     */
    private List<User> changedUserList = new LinkedList<>();

    /**
     * Create a new Game and set the category_probability to default values.
     *
     * @param probability TODO
     */
    public Game(int[] probability) {
        for (int i = 0; i < last_call.length; i++) last_call[i] = probability[i];
        adjustment = new int[Quest.CATEGORY_SUM];
    }

    /**
     * Add a new user to the game.
     *
     * @param player user to add.
     * @see User
     */
    public void addPlayer(User player) {
        /**
        if (player.sex == User.SEX_FEMALE) {
            if (!player_f.contains(player)) player_f.add(player);
        } else {
            if (!player_m.contains(player)) player_m.add(player);
        }
         */
        if (!player_all.contains(player)) player_all.add(player);
    }

    /**
     * Checks if male and female players are currently in this game and returns
     * a final User.SEX_ flag if there are only male or only female players
     * playing this game. Of both sexes, or no players, are playing the return
     * is null.
     *
     * @return a final User.SEX_flag, or null if both sexes or nobody plays.
     * @see User
     */
    public Integer allPlayersAre() {
        /**
        if (player_f.size() < 1) {
            if (player_m.size() > 0) {
                return User.SEX_MALE;
            }
        } else if (player_m.size() < 1) {
            return User.SEX_FEMALE;
        }
         */
        int fem_count = 0;
        for (User u : player_all)
            if (u.sex == User.SEX_FEMALE) fem_count++;
        if (fem_count < 1) {
            if (player_all.size() > 0) return User.SEX_MALE;
        } else if (fem_count == player_all.size()) return User.SEX_FEMALE;
        return null;
    }

    /**
     * Returns true if this game contains the specified user.
     *
     * @param user object whose presence in this game is to be tested.
     * @return true if this list contains the specified element; else
     * otherwise.
     * @see User
     */
    public boolean containsUser(User user) {
        /**
        if (user.sex == User.SEX_FEMALE) return player_f.contains(user);
        else return player_m.contains(user);
         */
        return player_all.contains(user);
    }

    /**
     * Returns if there are enough players to start a game. Currently the
     * minimum is two players.
     *
     * @return true if enough players are in the game; false otherwise.
     */
    public boolean enoughPlayer() {
        return getPlayerCount() >= 2;
    }

    /**
     * TODO
     *
     * @return
     */
    public int getPlayerCount() {
        /**
        player_count = player_f.size() + player_m.size();
        return (int)Math.round(player_count);
         */
        return player_all.size();
    }

    /**
     * Finds a new player and partner best suitable for this category and quest
     * configuration. For that this method calls some private methods.
     *
     * @param categories takes a list of final Quest.CATEGORY_ flags.
     * @param conf takes a quest configuration.
     * @see List
     * @see Quest
     * @see QuestConfiguration
     */
    public boolean findPlayer(List<Integer> categories, QuestConfiguration conf) {
        for (int i : categories) last_call[i] = 0.0;
        this.conf = conf;
        setPlayer(categories);
        if (currentPlayer == null) {
            currentPartner = null;
            return true;
        } else {
            setPartner(categories);
            return (currentPlayer != currentPartner);
        }
    }

    /**
     * Returns the rounded current average level of all players.
     *
     * @return a level.
     */
    public int getAvgLevel() {
        return (int) Math.round(avg_level);
    }

    /**
     * Get the current Partner.
     *
     * @return the current partner or null if no exists.
     */
    public String getPartner() {
        if(currentPartner == null) return null;
        return currentPartner.name;
    }

    /**
     *
     * @return
     */
    public Integer getPartnerSex() {
        if (currentPartner == null) return null;
        return currentPartner.sex;
    }

    /**
     * Get the current Player.
     *
     * @return the current player or null if no exists.
     */
    public String getPlayer() {
        if (currentPlayer == null) return null;
        return currentPlayer.name;
    }

    /**
     * Get the current Player.
     *
     * @return the current player or null if no exists.
     */
    public int getPlayerLevel() {
        if (currentPlayer == null) return getAvgLevel();
        return currentPlayer.getLevel();
    }

    /**
     *
     * @return the level defined in the current quest configuration.
     */
    public int getQuestLevel() {
        return conf.level;
    }

    public List<User> getChangedUserList() {
        List<User> returnList = Collections.unmodifiableList(new ArrayList<>(changedUserList));
        changedUserList.clear();
        return returnList;
    }

    /**
     * A flag describing the deletion approval of this quest.
     *
     * @return a final Quest.DELETE_ flag.
     * @see Quest
     */
    public int questAllowsDelete() { return conf.allow_delete; }

    /**
     * A flag describing the skip approval of this quest.
     *
     * @return a final Quest.SKIP_ flag.
     * @see Quest
     */
    public int questAllowsSkip() { return conf.skip; }

    /**
     * Removes a user from the Game.
     *
     * @param player to remove.
     * @see User
     */
    public void removePlayer(User player) {
        /**
        if (player.sex == User.SEX_FEMALE) player_f.remove(player);
        else player_m.remove(player);
         */
        while (player_all.contains(player)) player_all.remove(player);
    }

    /**
     * Resets all game data for a new game.
     */
    public void setGame(boolean reset) {
        avg_level = 0.0;

        conf = null;
        currentPartner = null;
        currentPlayer = null;
        //noinspection Convert2streamapi
        //for (User f : player_f) f.setGame();
        //noinspection Convert2streamapi
        //for (User m : player_m) m.setGame();

        if (reset) {
            for (User u : player_all) u.resetGame();
            changedUserList.addAll(player_all);
        }

        for (User u : player_all) avg_level += u.getLevel();
        avg_level /= (double)player_all.size();
    }

    /**
     * Notifies the user that he is chosen for the quest and can reset his
     * counter of last call for this category. If set is true all other
     * players are notified about the new round and that they were not chosen.
     * If user is null all players are chosen and notified and set will be
     * ignored.
     *
     * @param categories takes a list of final Quest.CATEGORY_ flags.
     * @param user the user to reset, or null if all users should reset this
     *             category.
     * @param set true to notify all users about a new round, false to only
     *            reset one user.
     * @see List
     * @see Quest
     * @see User
     */
    private void resetLastCall(List<Integer> categories, User user, boolean set) {
        if (user == null) setLastCall(categories, true);
        else {
            user.setLastCall(categories, true, true);
            if (set) {
                setLastCall(categories, false);
            }
        }
    }

    /**
     * Returns a new category applying to the category probabilities.
     *
     * @param category_probability
     * @return a final Quest.CATEGORY_ flag.
     * @see Quest
     */
    public int returnCategory(int[] category_probability) {
        double[] weight = new double[last_call.length];
        weight[0] = last_call[0] * (double)category_probability[0];
        for (int i = 1; i < last_call.length; i++) {
            weight[i] = weight[i - 1] + (last_call[i] * (double)category_probability[i]);
        }
        double random = GameDataManager.getRandom() * weight[weight.length -1];
        for (int c = 0; c < weight.length; c++) {
            if (weight[c] >= random) return c;
        }
        /**
         * should never ever happen.
         */
        return -1;
    }

    /**
     * TODO
     *
     * @param categories
     * @param user_sex
     * @return
     */
    private User searchUser(List<Integer> categories, Integer user_sex) {
        if (player_all.size() > 0) {
            double[] weight = new double[player_all.size()];
            if (user_sex == null || user_sex == player_all.get(0).sex)
                weight[0] = Math.pow(player_all.get(0).getLastCall(categories)+2, 5);
            else weight[0] = 0;
            for (int i = 1; i < player_all.size(); i++) {
                if (user_sex == null || user_sex == player_all.get(i).sex)
                    weight[i] = weight[i - 1] + Math.pow(player_all.get(i).getLastCall(categories)+2, Math.max(Math.round(5 - i), 1));
                else weight[i] = weight[i - 1];
            }
            double random = GameDataManager.getRandom() * weight[weight.length -1];
            for (int i = 0; i < weight.length; i++) {
                if (random < weight[i]) return player_all.get(i);
            }
        }
        return null;
    }

    /**
     * This method finds the user who needs a quest of this category most. To
     * look for a user in two different lists, user
     * searchUser(category, list, list, double) which provides the same
     * functionality.
     *
     * @param categories takes a list of final Quest.CATEGORY_ flag.
     * @param players a list of users to choose from.
     * @return the chosen user, or null if no suitable player was found.
     * @see List
     * @see Quest
     * @see User
     */
    @Deprecated
    private User searchUser(List<Integer> categories, List<User> players) {
        if (players.size() > 0) {
            double[] weight = new double[players.size()];
            weight[0] = Math.pow(players.get(0).getLastCall(categories)+2, 5);
            for (int i = 1; i < players.size(); i++) {
                weight[i] = weight[i - 1] + Math.pow(players.get(i).getLastCall(categories)+2, Math.max(Math.round(5 - i), 1));
            }
            double random = GameDataManager.getRandom() * weight[weight.length -1];
            for (int i = 0; i < weight.length; i++) {
                if (random < weight[i]) return players.get(i);
            }
        }
        return null;
    }

    /**
     * This method finds the user who needs a quest of this category most. To
     * look for a user in two different lists, user
     * searchUser(category, list, list, double) which provides the same
     * functionality.
     *
     * @param categories takes a list of final Quest.CATEGORY_ flags.
     * @param players1 a list of users to choose from.
     * @param players2 a second list of users to choose from.
     * @return the chosen user, or null if no suitable player was found.
     * @see List
     * @see Quest
     * @see User
     */
    @Deprecated
    private User searchUser(List<Integer> categories, List<User> players1, List<User> players2) {
        if (players1.size() < 1) return searchUser(categories, players2);
        else if (players2.size() < 1) return searchUser(categories, players1);
        double[] weight = new double[players1.size() + players2.size()];
        weight[0] = Math.pow(players1.get(0).getLastCall(categories)+2, 5);
        for (int i = 1; i < players1.size(); i++) {
            weight[i] = weight[i - 1] + Math.pow(players1.get(i).getLastCall(categories)+2, Math.max(Math.round(5 - i), 1));
        }
        for (int i = 0; i < players2.size(); i++) {
            weight[i + players1.size()] = weight[i - 1 + players1.size()] + Math.pow(players2.get(i).getLastCall(categories)+2, Math.max(Math.round(5 - i), 1));
        }
        double random = GameDataManager.getRandom() * weight[weight.length -1];
        for (int i = 0; i < weight.length; i++) {
            if (random < weight[i]) {
                if (i < players1.size()) return  players1.get(i);
                return players2.get(i - players1.size());
            }
        }
        return null;
    }

    /**
     * Notifies all users about a new quest and to notify or reset his last
     * call counter.
     *
     * @param categories takes a list of final Quest.CATEGORY_ flags.
     * @param reset true to reset the counter, false to increment.
     * @see List
     */
    private void setLastCall(List<Integer> categories, boolean reset) {
        /**
        for (User f : player_f) f.setLastCall(categories, reset, false);
        for (User m : player_m) m.setLastCall(categories, reset, false);
         */
        for (User u : player_all) u.setLastCall(categories, reset, false);
    }

    /**
     * Set a new partner who matches the configuration and wants to get this
     * quest, or sets the partner null if no (specific) user is needed. This
     * method needs the configurations set through findUser(), so make sure to
     * call that method first.
     *
     * TODO exception if partner is needed but not found
     *
     * @param categories takes a list of final Quest.CATEGORY_ flags.
     * @see List
     * @see Quest
     */
    private void setPartner(List<Integer> categories) {
        switch (conf.partner) {
            case Quest.PARTNER_NO:
            case Quest.PARTNER_GIRLS:
            case Quest.PARTNER_BOYS:
            case Quest.PARTNER_ALL_OPPOSITE:
            case Quest.PARTNER_ALL:
                currentPartner = null;
                break;
            case Quest.PARTNER_FEMALE:
                //currentPartner = searchUser(categories, player_f);
                currentPartner = searchUser(categories, User.SEX_FEMALE);
                break;
            case Quest.PARTNER_MALE:
                //currentPartner = searchUser(categories, player_m);
                currentPartner = searchUser(categories, User.SEX_MALE);
                break;
            case Quest.PARTNER_OPPOSITE_SEX:
                //currentPartner = searchUser(categories, (currentPlayer.sex == User.SEX_FEMALE ? player_m : player_f));
                currentPartner = searchUser(categories, (currentPlayer.sex == User.SEX_FEMALE ? User.SEX_MALE : User.SEX_FEMALE));
                break;
            case Quest.PARTNER_YES:
                //currentPartner = searchUser(categories, player_f, player_m);
                currentPartner = searchUser(categories, (Integer)null);
                break;
        }
        if (conf.partner != Quest.PARTNER_NO) {
            resetLastCall(categories, currentPartner, true);
        }
    }

    /**
     * Set a new player who matches the configuration and wants to get this
     * quest, or sets the player null if no specific user is needed. This
     * method needs the configurations set through findUser(), so make sure to
     * call that method first.
     *
     * TODO exception if partner is needed but not found
     *
     * @param categories takes a list of final Quest.CATEGORY_ flags.
     * @see List
     * @see Quest
     */
    private void setPlayer(List<Integer> categories) {
        switch (conf.player) {
            case Quest.PLAYER_A_FEMALE:
                //currentPlayer = searchUser(categories, player_f);
                currentPlayer = searchUser(categories, User.SEX_FEMALE);
                break;
            case Quest.PLAYER_A_MALE:
                //currentPlayer = searchUser(categories, player_m);
                currentPlayer = searchUser(categories, User.SEX_MALE);
                break;
            case Quest.PLAYER_ONE:
                //currentPlayer = searchUser(categories, player_f, player_m);
                currentPlayer = searchUser(categories, (Integer)null);
                break;
            case Quest.PLAYER_GIRLS:
            case Quest.PLAYER_BOYS:
            case Quest.PLAYER_ALL:
                currentPlayer = null;
                break;
        }
        resetLastCall(categories, currentPlayer, false);
        if (currentPlayer != null) {
            removePlayer(currentPlayer);
            addPlayer(currentPlayer);
        }
    }

    /**
     * The points of all user involved in the current quest, as defined by the
     * quest configuration, gets set with a new value. The success parameter
     * tells if the quest was completed successfully or was skipped. It does
     * not tell if the user points increase or decrease. If a user reaches the
     * next level the average level will be updated. This method needs the
     * configurations set through findUser(), so make sure to call that method
     * first.
     *
     * @param categories takes a list of final Quest.CATEGORY_ flags.
     * @param success true if the result was positive, false if not.
     * @see List
     * @see Quest
     */
    public void setPoints(List<Integer> categories, boolean success, int[] category_probability) {
        double level_up = 0.0;
        if (currentPlayer != null && (currentPartner != null || conf.partner == Quest.PARTNER_NO)) {
            changedUserList.add(currentPlayer);
            if (currentPlayer.setPoints(categories, conf.level, true, success)) level_up++;
            if (currentPartner != null) {
                changedUserList.add(currentPartner);
                if (currentPartner.setPoints(categories, conf.level, true, success)) level_up++;
            }
        } else {
            /**
            for (User f : player_f) {
                if (f.setPoints(categories, conf.level, false, success)) level_up++;
            }
            for (User m : player_m) {
                if (m.setPoints(categories, conf.level, false, success)) level_up++;
            }
             */
            changedUserList.addAll(player_all);
            for (User u : player_all) {
                if (u.setPoints(categories, conf.level, false, success)) level_up++;
            }
        }
        //avg_level += (level_up / player_count);
        avg_level += (level_up / player_all.size());

        for (int i = 0; i < last_call.length; i++) {
            if (categories.contains(i)) last_call[i] = 0.0;
            last_call[i] += category_probability[i] + adjustment[i];
        }
    }

    /**
     * TODO
     *
     * @param friends_string
     * @return
     */
    public static Integer getFriendsField(String friends_string) {
        if (friends_string == null) return null;
        switch (friends_string) {
            case FRIENDS_BENEFITS_STRING:
                return FRIENDS_BENEFITS;
            case FRIENDS_GOOD_STRING:
                return FRIENDS_GOOD;
            case FRIENDS_LOOSE_STRING:
                return FRIENDS_LOOSE;
            default:
                return null;
        }
    }
}
