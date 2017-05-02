package at.coala.games.tts.game;

import android.content.Context;

import at.coala.games.tts.R;

/**
 * Abstract class providing an environment for subclasses to supply different
 * actions for different states. Simply override the methods to to change the
 * functionality.
 *
 * @author Klaus
 */
abstract class GameState {

    /**
     * Should be called if go_button or no_button is called. after finalising
     * this GameState, the next GameState is returned. if onState() is
     * overridden, best practise would be to simply write
     * "return MyNextGameState.onState()" into the body of this method.
     *
     * @param button_is_go true if button is go_button, false if button is
     *                     no_button.
     * @return next GameState.
     */
    abstract GameState clickedButtonGoOrNo(boolean button_is_go);

    /**
     * Returns the text that should be shown on the go_button. If not
     * overridden, returns a possibly empty default String.
     *
     * @param context to access application data.
     * @return text for button.
     * @see Context
     */
    String getGoButtonText(Context context) {
        return context.getString(R.string.empty);
    }

    /**
     * Returns the text that should be shown in the main text field. If not
     * overridden, returns a possibly empty default String.
     *
     * @param context to access application data.
     * @return text to show in main text field.
     * @see Context
     */
    String getMainText(Context context) {
        return context.getString(R.string.empty);
    }

    /**
     * Returns the text that should be shown on the no_button. If not
     * overridden, returns null.
     *
     * @param context to access application data.
     * @return text for button, or null if button should not be shown.
     * @see Context
     */
    String getNoButtonText(Context context) {
        return null;
    }

    //TODO
    String getPreTitle(Context context) {
        return "";
    }

    /**
     * Returns the text that should be shown in the comment field beyond the
     * buttons. This field has a smaller font size. If not overridden, returns
     * a possibly empty default String.
     *
     * @param context to access application data.
     * @return text to show in comment section.
     * @see Context
     */
    String getSubText(Context context) {
        return context.getString(R.string.empty);
    }

    /**
     * Returns the text that should be shown in the above the text field or as
     * title. This field has a larger font size. If not overridden, returns a
     * possibly empty default String.
     *
     * @param context to access application data.
     * @return text to show in the title field.
     * @see Context
     */
    String getTitle(Context context) {
        return context.getString(R.string.empty);
    }

    /**
     * Should be called if this is the new GameState to initialise and set new
     * values. It returns itself, or on override can redirect to another state.
     *
     * @return itself.
     */
    GameState onState() {
        return this;
    }

    //TODO
    void saveGameData(Context context) {
        //TODO
    }
}
