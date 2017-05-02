package at.coala.games.tts.dba.xml.helper;

import org.xml.sax.Attributes;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import at.coala.games.tts.data.Game;
import at.coala.games.tts.data.quest.Quest;
import at.coala.games.tts.data.quest.QuestCollection;
import at.coala.games.tts.data.quest.QuestCreator;
import at.coala.games.tts.dba.xml.QuestDataXMLAccess;

/**
 * A Helper to extract configurations and additional information out of XML
 * apply_rule, conf, public_conf and source elements and finally creates a new
 * Quest element.
 *
 * @author Klaus
 * @version 2
 * @since 15.07.2015.
 */
class RuleConfSourceElementsXMLHelper extends ElementXMLHelper {

    /**
     * A local list containing the categories.
     *
     * @see List
     */
    private List<Integer> categories;

    /**
     * A local list containing the quest text at get(0) and comments at
     * further positions. This list gets read and reset here, but filled in
     * another helper.
     *
     * @see List
     */
    private final List<String> comments;

    /**
     * A private variable containing the Helper for the parent XML element.
     *
     * @see android.sax.Element
     */
    private ElementXMLHelper parent;

    /**
     * A private helper to create quests.
     */
    private QuestCreator qc;

    /**
     * A collection needed to store quests.
     */
    private QuestCollection quests;

    /**
     * A local list containing requirements.
     *
     * @see List
     */
    private List<Integer> requirements;

    /**
     * A local list containing rule_ids.
     *
     * @see List
     */
    private List<String> rule_ids;

    /**
     * A local list containing the quest text. This list gets read and reset
     * here, but filled in another helper.
     *
     * @see List
     */
    private final List<String> text;

    /**
     * Create a new Helper for XML apply_rule, conf, public_conf and source
     * elements.
     *
     * @param parent Helper for parent XML element.
     * @param quests collection needed to store quests.
     * @param comments list containing the comments.
     * @param text list containing the quest text.
     * @see ElementXMLHelper
     * @see List
     * @see Quest
     * @see QuestCollection
     */
    RuleConfSourceElementsXMLHelper(ElementXMLHelper parent, QuestCollection quests, List<String> comments, List<String> text) {
        categories = new ArrayList<Integer>(2);
        requirements = new ArrayList<Integer>(1);
        rule_ids = new ArrayList<String>(3);
        this.parent = parent;
        this.quests = quests;
        this.comments = comments;
        this.text = text;
        qc = new QuestCreator();
    }

    /**
     * Clears all data to start collecting for a new quest.
     */
    private void clearData() {
        qc.abort();
        comments.clear();
        text.clear();
        categories.clear();
        requirements.clear();
        rule_ids.clear();
    }

    /**
     * This method is to call if an end-tag was read. It overrides the method
     * of ElementXMLHelper to retrieve the source text written before the end
     * tag and return the parent Helper if a quest end-tag was read.
     *
     * @param uri the Namespace URI, or the empty string if the element has no
     *            Namespace URI or if Namespace processing is not being
     *            performed.
     * @param localName the local name (without prefix), or the empty string if
     *                  Namespace processing is not being performed.
     * @param qName the qualified name (with prefix), or the empty string if
     *              qualified names are not available.
     * @return the ElementXMLHelper best suitable for the next action.
     * @see ElementXMLHelper
     */
    @Override
    public ElementXMLHelper endElement(String uri, String localName, String qName) {
        if (QuestDataXMLAccess.ELEMENT_QUEST.equals(localName)) {
            quests.addQuest(qc.craftQuest());
            clearData();
            return parent;
        }
        else if (QuestDataXMLAccess.ELEMENT_SOURCE.equals(localName)) qc.addSource(eleText);
        return super.endElement(uri, localName, qName);
    }

    /**
     * Read the XML file with a xmlPullParser. This method overrides the method
     * of ElementXMLHelper to parse through the configuration section, retrieve
     * given XML elements and their attributes and text. It calls several
     * internal methods to optimize the variety of possible following XML
     * elements.
     *
     * @param parser the parser.
     * @throws IOException
     * @throws XmlPullParserException - thrown to signal XML Pull Parser
     * related faults.
     * @see ElementXMLHelper
     * @see XmlPullParser - thrown to signal XML Pull Parser related faults.
     */
    @Override
    public void read(XmlPullParser parser) throws IOException, XmlPullParserException {
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() == XmlPullParser.START_TAG) {
                if (QuestDataXMLAccess.ELEMENT_CATEGORY.equals(parser.getName())) {
                    try {
                        readCategory(parser);
                    } catch (IllegalArgumentException e) {
                        /**
                         * It is ok to ignore this exception. This app will not collapse
                         * without this quest.
                         */
                    }
                    clearData();
                }
                return;
            }
        }
        super.read(parser);
    }

    /**
     * This method retrieves attributes out of XML apply_rule elements and
     * calls suitable methods for upcoming XML elements.
     *
     * @param parser the parser, position must be the start tag of XML
     *               apply_rule element.
     * @throws IOException
     * @throws XmlPullParserException - thrown to signal XML Pull Parser
     * related faults.
     * @throws IllegalArgumentException - if no valid category exists.
     * @see ElementXMLHelper
     * @see XmlPullParser - thrown to signal XML Pull Parser related faults.
     */
    private void readApplyRule(XmlPullParser parser) throws IOException, XmlPullParserException, IllegalArgumentException {
        rule_ids.add(parser.getAttributeValue(null, QuestDataXMLAccess.ATTRIBUTE_APPLY_RULE_ID));
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() == XmlPullParser.START_TAG) {
                if (QuestDataXMLAccess.ELEMENT_CONFIGURATION.equals(parser.getName())) {
                    readConf(parser);
                } else if (QuestDataXMLAccess.ELEMENT_APPLY_RULE.equals(parser.getName())) {
                    readApplyRule(parser);
                }
                return;
            }
        }
    }

    /**
     *
     * @param parser
     * @throws IOException
     * @throws XmlPullParserException
     * @throws IllegalArgumentException
     */
    private void readCategory(XmlPullParser parser) throws IOException, XmlPullParserException, IllegalArgumentException {
        categories.add(Quest.getCategoryField(parser.getAttributeValue(null, QuestDataXMLAccess.ATTRIBUTE_CATEGORY_NAME)));
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() == XmlPullParser.START_TAG) {
                if (QuestDataXMLAccess.ELEMENT_CATEGORY.equals(parser.getName())) {
                    readCategory(parser);
                } else if (QuestDataXMLAccess.ELEMENT_CONFIGURATION.equals(parser.getName())) {
                    readConf(parser);
                } else if (QuestDataXMLAccess.ELEMENT_APPLY_RULE.equals(parser.getName())) {
                    readApplyRule(parser);
                } else if (QuestDataXMLAccess.ELEMENT_REQUIREMENT.equals(parser.getName())) {
                    readRequirement(parser);
                }
                return;
            }
        }
    }

    /**
     * This method retrieves attributes out of XML conf elements and calls
     * suitable methods for upcoming XML elements. If no additional XML
     * elements exist it crafts a new Quest and adds it to the collection.
     *
     * @param parser the parser, position must be the start tag of XML conf
     *               element.
     * @throws IOException
     * @throws XmlPullParserException - thrown to signal XML Pull Parser
     * related faults.
     * @throws IllegalArgumentException - if no valid category exists.
     * @see ElementXMLHelper
     * @see XmlPullParser - thrown to signal XML Pull Parser related faults.
     */
    private void readConf(XmlPullParser parser) throws IOException, XmlPullParserException, IllegalArgumentException {
        setConf(Game.LOCATION_PRIVATE, parser);
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() == XmlPullParser.END_TAG) {
                if (QuestDataXMLAccess.ELEMENT_QUEST.equals(parser.getName())) {
                    quests.addQuest(qc.craftQuest());
                    return;
                }
            } else if (parser.getEventType() == XmlPullParser.START_TAG) {
                if (QuestDataXMLAccess.ELEMENT_CONFIGURATION.equals(parser.getName())) {
                    readConf(parser);
                } else if (QuestDataXMLAccess.ELEMENT_SOURCE.equals(parser.getName())) {
                    readSource(parser);
                } else if (QuestDataXMLAccess.ELEMENT_PUBLIC_CONFIGURATION.equals(parser.getName())){
                    readPublicConf(parser);
                }
                return;
            }
        }
    }

    /**
     * This method retrieves attributes out of XML public_conf elements and
     * calls suitable methods for upcoming XML elements  If no additional XML
     * elements exist it crafts a new Quest and adds it to the collection.
     *
     * @param parser the parser, position must be the start tag of XML
     *               public_conf element.
     * @throws IOException
     * @throws XmlPullParserException - thrown to signal XML Pull Parser
     * related faults.
     * @throws IllegalArgumentException - if no valid category exists.
     * @see ElementXMLHelper
     * @see XmlPullParser - thrown to signal XML Pull Parser related faults.
     */
    private void readPublicConf(XmlPullParser parser) throws IOException, XmlPullParserException, IllegalArgumentException {
        setConf(Game.LOCATION_PUBLIC, parser);
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() == XmlPullParser.END_TAG) {
                if (QuestDataXMLAccess.ELEMENT_QUEST.equals(parser.getName())) {
                    quests.addQuest(qc.craftQuest());
                    return;
                }
            } else if (parser.getEventType() == XmlPullParser.START_TAG) {
                if (QuestDataXMLAccess.ELEMENT_SOURCE.equals(parser.getName())) {
                    readSource(parser);
                } else if (QuestDataXMLAccess.ELEMENT_PUBLIC_CONFIGURATION.equals(parser.getName())){
                    readPublicConf(parser);
                }
                return;
            }
        }
    }

    /**
     *
     * @param parser
     * @throws IOException
     * @throws XmlPullParserException
     * @throws IllegalArgumentException
     */
    private void readRequirement(XmlPullParser parser) throws IOException, XmlPullParserException, IllegalArgumentException {
        requirements.add(Quest.getRequirementField(parser.getAttributeValue(null, QuestDataXMLAccess.ATTRIBUTE_REQUIRES)));
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() == XmlPullParser.START_TAG) {
                if (QuestDataXMLAccess.ELEMENT_CONFIGURATION.equals(parser.getName())) {
                    readConf(parser);
                } else if (QuestDataXMLAccess.ELEMENT_APPLY_RULE.equals(parser.getName())) {
                    readApplyRule(parser);
                } else if (QuestDataXMLAccess.ELEMENT_REQUIREMENT.equals(parser.getName())) {
                    readRequirement(parser);
                }
                return;
            }
        }
    }

    /**
     * This method retrieves text out of XML source elements, crafts a new
     * Quest and adds it to the collection.
     *
     * @param parser the parser, position must be the start tag of XML source
     *               element.
     * @throws IOException
     * @throws XmlPullParserException - thrown to signal XML Pull Parser
     * related faults.
     * @see ElementXMLHelper
     * @see XmlPullParser - thrown to signal XML Pull Parser related faults.
     */
    private void readSource(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.next();
        qc.addSource(parser.getText());
        quests.addQuest(qc.craftQuest());
    }

    /**
     * Adds a configuration to the quest in preparation.
     *
     * @param game_type takes a final Game.LOCATION_ flag.
     * @param attributes has to contain an attribute with the name equally to
     *                   the value of QuestDataXMLAccess.ATTRIBUTE_PLAYER
     * @throws IllegalArgumentException - if no valid category exists.
     * @see Attributes
     * @see Game
     * @see QuestDataXMLAccess
     */
    private void setConf(int game_type, Attributes attributes) throws IllegalArgumentException {
        setConf(game_type,
                attributes.getValue(QuestDataXMLAccess.ATTRIBUTE_QUEST_LEVEL),
                attributes.getValue(QuestDataXMLAccess.ATTRIBUTE_PLAYER),
                attributes.getValue(QuestDataXMLAccess.ATTRIBUTE_PARTNER),
                attributes.getValue(QuestDataXMLAccess.ATTRIBUTE_FRIENDSHIP_LEVEL),
                attributes.getValue(QuestDataXMLAccess.ATTRIBUTE_SKIP),
                attributes.getValue(QuestDataXMLAccess.ATTRIBUTE_ALLOW_DELETE));
    }

    /**
     * Adds a configuration to the quest in preparation.
     *
     * @param game_type takes a final Game.LOCATION_ flag.
     * @param parser has to contain an attribute with the name equally to the
     *               value of QuestDataXMLAccess.ATTRIBUTE_PLAYER on his
     *               current position.
     * @throws IllegalArgumentException - if no valid category exists.
     * @see Game
     * @see XmlPullParser
     */
    private void setConf(int game_type, XmlPullParser parser) throws IllegalArgumentException {
        setConf(game_type,
                parser.getAttributeValue(null, QuestDataXMLAccess.ATTRIBUTE_QUEST_LEVEL),
                parser.getAttributeValue(null, QuestDataXMLAccess.ATTRIBUTE_PLAYER),
                parser.getAttributeValue(null, QuestDataXMLAccess.ATTRIBUTE_PARTNER),
                parser.getAttributeValue(null, QuestDataXMLAccess.ATTRIBUTE_FRIENDSHIP_LEVEL),
                parser.getAttributeValue(null, QuestDataXMLAccess.ATTRIBUTE_SKIP),
                parser.getAttributeValue(null, QuestDataXMLAccess.ATTRIBUTE_ALLOW_DELETE));
    }

    /**
     * Adds a configuration to the quest in preparation.
     *
     * @param game_type takes a final Game.LOCATION_ flag.
     * @param level should be an Integer between 1 and 10.
     * @param player takes a String representation of the final Quest.PLAYER_
     *               flag, or can be null due to a default value.
     * @param partner takes a String representation of the final Quest.PARTNER_
     *                flag, or can be null due to a default value.
     * @param friends takes a String representation of the final Game.FRIENDS_
     *                flag, or can be null due to a default value.
     * @param skip takes a String representation of the final Quest.SKIP_ flag,
     *             or can be null due to a default value.
     * @param allow_delete takes a String representation of the final
     *                     Quest.DELETE_ flag, or can be null due to a default
     *                     value.
     * @throws IllegalArgumentException - if no valid category exists.
     * @see Game
     * @see Quest
     */
    private void setConf(int game_type,
                         String level,
                         String player,
                         String partner,
                         String friends,
                         String skip,
                         String allow_delete)
            throws IllegalArgumentException {
        if (qc.getInPreparation()) {
            qc.addConfiguration(game_type,
                    Integer.parseInt(level),
                    Quest.getPlayerField(player),
                    Quest.getPartnerField(partner),
                    Game.getFriendsField(friends),
                    Quest.getSkipField(skip),
                    Quest.getDeleteField(allow_delete));
        } else {
            if (categories.size() == 0) throw new IllegalArgumentException();
            qc.startNewQuest(categories.remove(0), text.remove(0),
                    Integer.parseInt(level),
                    Quest.getPlayerField(player),
                    Quest.getPartnerField(partner),
                    Game.getFriendsField(friends),
                    Quest.getSkipField(skip),
                    Quest.getDeleteField(allow_delete));
            if (categories.size() > 0) {
                for (int c : categories) qc.addCategory(c);
            }
            if (requirements.size() > 0) {
                for (int c : requirements) qc.addRequirement(c);
            }
            if (comments.size() > 0) {
                //noinspection Convert2streamapi
                for (String c : comments) qc.addComment(c);
            }
            if (text.size() > 0) {
                //noinspection Convert2streamapi
                for (String r : text) qc.addText(r);
            }
            if (rule_ids.size() > 0) {
                //noinspection Convert2streamapi
                for (String r : rule_ids) qc.addRuleId(r);
            }
        }
    }

    /**
     * This method is to call if a start-tag was read. It overrides the method
     * of ElementXMLHelper to retrieve given attributes.
     *
     * @param uri the Namespace URI, or the empty string if the element has no
     *            Namespace URI or if Namespace processing is not being
     *            performed.
     * @param localName the local name (without prefix), or the empty string if
     *                  Namespace processing is not being performed.
     * @param qName the qualified name (with prefix), or the empty string if
     *              qualified names are not available.
     * @param attributes the attributes attached to the element. If there are
     *                   no attributes, it shall be an empty Attributes object.
     *                   The value of this object after startElement returns is
     *                   undefined.
     * @return the ElementXMLHelper best suitable for the next action.
     * @see Attributes
     * @see ElementXMLHelper
     */
    @Override
    public ElementXMLHelper startElement(String uri, String localName, String qName, Attributes attributes) {
        /**
         * TODO
         * change to switch_statement ((is a little bit faster, but needs JAVA 7)
         *
         * always: more common tags should be asked first
         */
        try {
            if (QuestDataXMLAccess.ELEMENT_CATEGORY.equals(localName)) {
                categories.add(Quest.getCategoryField(attributes.getValue(QuestDataXMLAccess.ATTRIBUTE_CATEGORY_NAME)));
            } else if (QuestDataXMLAccess.ELEMENT_CONFIGURATION.equals(localName)) {
                setConf(Game.LOCATION_PRIVATE, attributes);
            } else if (QuestDataXMLAccess.ELEMENT_PUBLIC_CONFIGURATION.equals(localName)) {
                setConf(Game.LOCATION_PUBLIC, attributes);
            } else if (QuestDataXMLAccess.ELEMENT_APPLY_RULE.equals(localName)) {
                rule_ids.add(attributes.getValue(QuestDataXMLAccess.ATTRIBUTE_APPLY_RULE_ID));
            } else if (QuestDataXMLAccess.ELEMENT_REQUIREMENT.equals(localName)) {
                requirements.add(Quest.getRequirementField(attributes.getValue(QuestDataXMLAccess.ATTRIBUTE_REQUIRES)));
            }
        } catch (IllegalArgumentException e) {
            clearData();
            return parent;
        }
        return super.startElement(uri, localName, qName, attributes);
    }
}
