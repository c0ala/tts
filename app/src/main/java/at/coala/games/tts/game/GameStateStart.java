package at.coala.games.tts.game;

import android.content.Context;

import at.coala.games.tts.R;

/**
 * Provides functionality for resuming the game after going to settings.
 *
 * @author Klaus
 * @see GameState
 */
class GameStateStart extends GameState {

    /**
     * (Re)Sets the game and returns the next state.
     *
     * @param button_is_go true if button is go_button, false if button is
     *                     no_button.
     * @return next GameState.
     */
    @Override
    GameState clickedButtonGoOrNo(boolean button_is_go) {
        GameDataManager.setGame(true);
        return GameDataManager.readyState.onState();
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
     * Returns the text that should be shown in the main text field.
     *
     * @param context to access application data.
     * @return text to show in main text field.
     * @see Context
     */
    @Override
    String getMainText(Context context) { return context.getString(R.string.rules_text); }

    /**
     * Returns the text that should be shown in the sub text field.
     *
     * @param context to access application data.
     * @return text to show in main text field.
     * @see Context
     */
    @Override
    String getSubText(Context context) { return context.getString(R.string.rules_comments); }

    /**
     * Returns the text that should be shown as title.
     *
     * @param context to access application data.
     * @return text to show in main text field.
     * @see Context
     */
    @Override
    String getTitle(Context context) { return context.getString(R.string.rules); }

}
