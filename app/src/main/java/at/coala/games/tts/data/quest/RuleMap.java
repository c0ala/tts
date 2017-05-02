package at.coala.games.tts.data.quest;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores key-value-pairs of rule ids and rule texts.
 *
 * @author Klaus
 */
public class RuleMap {

    /**
     * The data structure storing a rule id as key and the rule text as value.
     *
     * @see Map
     */
    private Map<String, String> map = new HashMap<String, String>();

    /**
     * Adds a rule to the map.
     *
     * @param id the id/key.
     * @param text the rule text/value.
     */
    public void addRule(String id, String text) { map.put(id, text); }

    /**
     * Returns the rule associated with this id.
     *
     * @param id the id/key for the wanted rule.
     * @return the rule text, or null if the rule could not be found.
     */
    public String getRule(String id) { return map.get(id); }
}
