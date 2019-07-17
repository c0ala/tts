package at.coala.games.tts.game;

import android.content.Context;

import at.coala.games.tts.R;
import at.coala.games.tts.data.User;
import at.coala.games.tts.data.quest.Quest;
import at.coala.games.tts.dba.DataAccess;

/**
 * Provides functionality for preparing a new quest.
 *
 * @author Klaus
 * @see GameState
 */
class GameStateReady extends GameState {

    private int level = -1;
    private int new_level = -1;

    /**
     * Returns the quest state if go_button is clicked, otherwise prepares
     * another quest.
     *
     * @param button_is_go true if button is go_button, false if button is
     *                     no_button.
     * @return next GameState.
     */
    @Override
    GameState clickedButtonGoOrNo(boolean button_is_go) {
        if (button_is_go) {
            if (GameDataManager.game.questAllowsDelete() == Quest.DELETE_TRUE) {
                GameDataManager.setDeleteFlag();
            }
            return GameDataManager.questState.onState();
        }
        else return this.onState();
    }

    /**
     * Returns the text that should be shown on the go_button.
     *
     * @param context to access application data.
     * @return text for button.
     * @see Context
     */
    @Override
    String getGoButtonText(Context context) { return context.getString(R.string.go); }

    /**
     * Returns the quest text.
     *
     * @param context to access application data.
     * @return text to show in main text field.
     * @see Context
     */
    @Override
    String getMainText(Context context) {
        if (GameDataManager.game.getPlayer() != null) {
            String main_text = context.getString(R.string.one_ready);
            if (GameDataManager.game.getPartner() != null) {
                main_text += context.getString(R.string.nn)
                        + (GameDataManager.game.getPartnerSex() == User.SEX_FEMALE
                            ? String.format(
                                    context.getString(R.string.partner_f),
                                    GameDataManager.game.getPartner())
                            : String.format(
                                    context.getString(R.string.partner_m),
                                    GameDataManager.game.getPartner()));
            }
            return main_text;
        }
        else if (GameDataManager.quest.categories.contains(Quest.CATEGORY_DARE)) {
            return context.getString(R.string.do_you_dare);
        }
        return context.getString(R.string.all_ready);
    }

    /**
     * if a single player is called, he can skip the quest, groups can not
     *
     * @param context to access application data.
     * @return text for button, or null if button should not be shown.
     * @see Context
     */
    @Override
    String getNoButtonText(Context context) {
        //if (GameDataManager.game.getPlayer() == null) return null;
        //return context.getString(R.string.skip);
        return null;
    }

    //TODO
    @Override
    String getPreTitle(Context context) {
        if (GameDataManager.game.getPlayer() == null) return super.getPreTitle(context);
        return context.getString(R.string.give_phone_to);
    }

    /**
     * Returns the text that should be shown in the comment field beyond the
     * buttons. Currently returns the default.
     *
     * @param context to access application data.
     * @return text to show in comment section.
     * @see Context
     */
    @Override
    String getSubText(Context context) {
        String sub_text = "";
        if (GameDataManager.game.getAvgLevel() > level) {
            new_level = GameDataManager.game.getAvgLevel();
            sub_text += ((new_level == 1 || level == -1)
                    ? "" : context.getString(R.string.new_game_level) + " ")
                    + String.format(context.getString(
                            R.string.quests_are_available),
                            GameDataManager.getLinkedQuestTextCount())
                    + context.getString(R.string.n);
        }
        sub_text +=String.format(
                context.getString(R.string.avg_level), GameDataManager.game.getAvgLevel());
        if (GameDataManager.game.getPlayer() != null) {
            sub_text += context.getString(R.string.n)
                    + String.format(
                            context.getString(R.string.player_level),
                            GameDataManager.game.getPlayerLevel());
        }
        sub_text += context.getString(R.string.n)
                + String.format(
                        context.getString(R.string.quest_level),
                        GameDataManager.game.getQuestLevel());
        return sub_text;
    }

    /**
     * Returns the player name, or the default if no specific player exists.
     *
     * @param context to access application data.
     * @return text to show in the title field.
     * @see Context
     */
    @Override
    String getTitle(Context context) {
        if (GameDataManager.game.getPlayer() != null) return GameDataManager.game.getPlayer();
        return context.getString(R.string.all_round);
    }

    /**
     * Prepares a quest.
     *
     * @return itself.
     */
    @Override
    GameState onState() {
        level = new_level;
        GameDataManager.prepareNextQuest();
        return super.onState();
    }

    /**
     * TODO
     *
     * @param context
     */
    @Override
    void saveGameData(Context context) {
        DataAccess.updateUserList(context, GameDataManager.game.getChangedUserList());
        DataAccess.updateGameData(
                context,
                GameDataManager.ATTRIBUTE_GAME_DATA_LAST_GAME_ACTION_TIMESTAMP,
                System.currentTimeMillis());
    }
}
