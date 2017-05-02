package at.coala.games.tts.data.quest;

/**
 * A collection of related configurations.
 *
 * @author Klaus
 */
public class QuestConfiguration {

    /**
     * A public field containing the skip approval of this quest. It takes one
     * of the final Quest.DELETE_ flags defined in this class.
     *
     * @see Quest
     */
    public final int allow_delete;

    /**
     * A public field containing one of the final Game.FRIENDS_ flags.
     *
     * @see at.coala.games.tts.data.Game
     */
    public final int friendship_level;

    /**
     * A public field containing the level of this configuration.
     */
    public final int level;

    /**
     * A public field containing one of the final Quest.PARTNER_ flags.
     *
     * @see Quest
     */
    public final int partner;

    /**
     * A public field containing one of the final Quest.PLAYER_ flags.
     *
     * @see Quest
     */
    public final int player;

    /**
     * A public field containing the skip approval of this quest. It takes one
     * of the final Quest.SKIP_ flags defined in this class.
     *
     * @see Quest
     */
    public final int skip;

    /**
     * Creates a new QuestConfiguration.
     *
     * @param level should be an Integer between 1 and 10 and describes the
     *              level for this configuration.
     * @param player takes a final Quest.PLAYER_ flag.
     * @param partner takes a final Quest.PARTNER_ flag.
     * @param friendship_level takes a final Game.FRIENDS_ flag.
     * @param skip takes a final Quest.SKIP_ flag.
     * @param allow_delete takes a final Quest.DELETE_ flag.
     * @see Quest
     * @see at.coala.games.tts.data.Game
     */
    QuestConfiguration(int level, int player, int partner, int friendship_level, int skip, int allow_delete) {
        this.level = level;
        this.player = player;
        this.partner = partner;
        this.friendship_level = friendship_level;
        this.skip = skip;
        this.allow_delete = allow_delete;
    }
}
