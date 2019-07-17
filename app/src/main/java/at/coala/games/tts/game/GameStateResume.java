package at.coala.games.tts.game;

import android.content.Context;

import at.coala.games.tts.R;

/**
 * Provides functionality for resuming the game after going to settings.
 *
 * @author Klaus
 * @see GameState
 */
class GameStateResume extends GameState {

    /**
     * Returns the start state if go_button is clicked, otherwise returns the
     * resume state.
     *
     * @param button_is_go true if button is go_button, false if button is
     *                     no_button.
     * @return next GameState.
     */
    @Override
    GameState clickedButtonGoOrNo(boolean button_is_go) {
        if (button_is_go) {
            GameDataManager.setGame(false);
            return GameDataManager.readyState.onState();
        }
        else {
            return new GameStateStart().onState();
        }
    }

    /**
     * Returns the text that should be shown on the go_button.
     *
     * @param context to access application data.
     * @return text for button.
     * @see Context
     */
    @Override
    String getGoButtonText(Context context) { return context.getString(R.string.resume_button); }

    /**
     * Returns the text that should be shown in the main text field.
     *
     * @param context to access application data.
     * @return text to show in main text field.
     * @see Context
     */
    @Override
    String getMainText(Context context) { return context.getString(R.string.on_resume); }

    /**
     * Returns the text that should be shown on the go_button.
     *
     * @param context to access application data.
     * @return text for button.
     * @see Context
     */
    @Override
    String getNoButtonText(Context context) { return context.getString(R.string.new_game); }
}
