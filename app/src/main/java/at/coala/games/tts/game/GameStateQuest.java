package at.coala.games.tts.game;

import android.content.Context;

import java.util.Collections;
import java.util.List;

import at.coala.games.tts.R;
import at.coala.games.tts.data.quest.Quest;

/**
 * Provides functionality for showing and evaluating quests.
 *
 * @author Klaus
 * @see GameState
 */
class GameStateQuest extends GameState {

    /**
     * Set with the quest text each round
     */
    private String quest_text;

    /**
     * Parses lines into a multiline-string. if string is null or empty, a
     * new string is created, else the first thing created is an empty line.
     * than every string in the list is inserted into a new line
     *
     * @param string String to add lines to.
     * @param list Strings to add.
     * @param context to access application data.
     * @return a new String with added lines.
     */
    private String addTextLn(String string, List<String> list, Context context) {
        if (string == null || string.length() == 0) string = "";
        else string += context.getString(R.string.nn);
        for (String s : list) {
            string += s + context.getString(R.string.n);
        }
        return string.substring(0,string.length() - context.getString(R.string.n).length());
    }

    /**
     * Sets the points for the game and returns the next State.
     *
     * @param button_is_go true if button is go_button, false if button is
     *                     no_button.
     * @return next GameState.
     */
    @Override
    GameState clickedButtonGoOrNo(boolean button_is_go) {
        GameDataManager.game.setPoints(
                GameDataManager.quest.categories,
                button_is_go,
                GameDataManager.settings.getCategoryProbability());
        return GameDataManager.readyState.onState();
    }

    /**
     * Returns the quest_text that should be shown on the go_button.
     *
     * @param context to access application data.
     * @return quest_text for button.
     * @see Context
     */
    @Override
    String getGoButtonText(Context context) { return context.getString(R.string.done_button); }

    /**
     * Returns the quest quest_text.
     *
     * @param context to access application data.
     * @return quest_text to show in main quest_text field.
     * @see Context
     */
    @Override
    String getMainText(Context context) {
        if (quest_text == null) {
            try {
                quest_text = GameDataManager.quest.quest_texts.get(
                        (int)(GameDataManager.getRandom()
                                * GameDataManager.quest.quest_texts.size()));
            } catch (IndexOutOfBoundsException e) {
                quest_text = GameDataManager.quest.quest_texts.get(
                        GameDataManager.quest.quest_texts.size()- 1);
            }
            if (GameDataManager.game.getPartner() != null) {
                quest_text = quest_text.replace(
                        context.getString(R.string.name_placeholder),
                        GameDataManager.game.getPartner());
            }
        }
        return quest_text;

    }

    /**
     * Some categories can be aborted. This method returns a quest_text to show
     * on the abort-Button, or null to make aborting impossible.
     *
     * @param context to access application data.
     * @return quest_text for button, or null if button should not be shown.
     * @see Context
     */
    @Override
    String getNoButtonText(Context context) {
        if (GameDataManager.game.questAllowsSkip() == Quest.SKIP_TRUE) {
            return context.getString(R.string.abort_button);
        }
        return super.getNoButtonText(context);
    }

    /**
     * Returns the quest_text that should be shown in the comment field beyond the
     * buttons.
     *
     * @param context to access application data.
     * @return quest_text to show in comment section.
     * @see Context
     */
    @Override
    String getSubText(Context context) {
        String sub_text = null;
        if (GameDataManager.quest.comments != null)
            sub_text = addTextLn(null, GameDataManager.quest.comments, context);
        List<String> rules = GameDataManager.quest.getRules(GameDataManager.rules);
        if (rules != null) sub_text = addTextLn(sub_text, rules, context);
        if (GameDataManager.quest.source != null)
            sub_text = addTextLn(
                    sub_text, Collections.singletonList(GameDataManager.quest.source), context);
        if (sub_text != null) return sub_text;
        return super.getSubText(context);
    }

    /**
     * Returns the player name.
     *
     * @param context to access application data.
     * @return quest_text to show in the title field.
     * @see Context
     */
    @Override
    String getTitle(Context context) {
        String player = GameDataManager.game.getPlayer();
        if (player != null) return player;
        return super.getTitle(context);
    }

    @Override
    GameState onState() {
        quest_text = null;
        return super.onState();
    }
}
