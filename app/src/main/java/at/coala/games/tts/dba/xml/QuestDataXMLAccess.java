package at.coala.games.tts.dba.xml;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import at.coala.games.tts.data.quest.QuestCollection;
import at.coala.games.tts.data.quest.RuleMap;

/**
 * Provides methods to parse XML files and extract quests and rules into
 * data-sets.
 *
 * TODO Validate XML file against schema.
 *
 * @author Klaus
 * @version 1.1
 * @since 16.07.2015.
 */
public class QuestDataXMLAccess {

    /**
     * A constant containing the name of the XML quest's delete flag attribute.
     */
    public static final String ATTRIBUTE_ALLOW_DELETE = "allow_delete";

    /**
     * A constant containing the name of the XML apply_rule id attribute.
     */
    public static final String ATTRIBUTE_APPLY_RULE_ID = "rule";

    /**
     * A constant containing the name of the XML category attribute.
     */
    @Deprecated
    public static final String ATTRIBUTE_CATEGORY = "category";

    /**
     * A constant containing the name of the XML category_name attribute.
     */
    public static final String ATTRIBUTE_CATEGORY_NAME = "category_name";

    /**
     * A constant containing the name of the XML friendship level attribute.
     */
    public static final String ATTRIBUTE_FRIENDSHIP_LEVEL = "friends";

    /**
     * A constant containing the name of the XML partner attribute.
     */
    public static final String ATTRIBUTE_PARTNER = "partner";

    /**
     * A constant containing the name of the XML player attribute.
     */
    public static final String ATTRIBUTE_PLAYER = "player";

    /**
     * A constant containing the name of the XML quest's language attribute.
     */
    public static final String ATTRIBUTE_QUEST_LANGUAGE = "lang_code";

    /**
     * A constant containing the name of the XML quest's level id attribute.
     */
    public static final String ATTRIBUTE_QUEST_LEVEL = "level";

    /**
     * A constant containing the name of the XML requirement id attribute.
     */
    @Deprecated
    public static final String ATTRIBUTE_REQUIREMENT = "requirement";

    /**
     * A constant containing the name of the XML requirement id attribute.
     */
    public static final String ATTRIBUTE_REQUIRES = "requires";

    /**
     * A constant containing the name of the XML rule id attribute.
     */
    public static final String ATTRIBUTE_RULE_ID = "id";

    /**
     * A constant containing the name of the XML rule's language attribute.
     */
    public static final String ATTRIBUTE_RULE_LANGUAGE = ATTRIBUTE_QUEST_LANGUAGE;

    /**
     * A constant containing the ski of the XML quest's skip attribute.
     */
    public static final String ATTRIBUTE_SKIP = "skip";

    /**
     * A constant containing the name of the XML apply_rule element.
     */
    public static final String ELEMENT_APPLY_RULE = "apply_rule";

    /**
     * A constant containing the name of the XML category element.
     */
    public static final String ELEMENT_CATEGORY = "category";

    /**
     * A constant containing the name of the XML comment element.
     */
    public static final String ELEMENT_COMMENT = "comment";

    /**
     * A constant containing the name of the XML configuration element.
     */
    public static final String ELEMENT_CONFIGURATION = "conf";

    /**
     * A constant containing the name of the XML language element.
     */
    public static final String ELEMENT_LANGUAGE = "language";

    /**
     * A constant containing the name of the XML public configuration element.
     */
    public static final String ELEMENT_PUBLIC_CONFIGURATION = "public_conf";

    /**
     * A constant containing the name of the XML quest element.
     */
    public static final String ELEMENT_QUEST = "quest";

    /**
     * A constant containing the name of the XML quests element.
     */
    @SuppressWarnings("unused")
    public static final String ELEMENT_QUESTS = "quests";

    /**
     * A constant containing the name of the XML quest_text element.
     */
    public static final String ELEMENT_QUEST_TEXT = "quest_text";

    /**
     * A constant containing the name of the XML requirement element.
     */
    public static final String ELEMENT_REQUIREMENT = "requirement";

    /**
     * A constant containing the name of the XML rule element.
     */
    public static final String ELEMENT_RULE = "rule";

    /**
     * A constant containing the name of the XML rules element.
     */
    public static final String ELEMENT_RULES = "rules";

    /**
     * A constant containing the name of the XML rule_text element.
     */
    public static final String ELEMENT_RULE_TEXT = "rule_text";

    /**
     * A constant containing the name of the XML source element.
     */
    public static final String ELEMENT_SOURCE = "source";

    /**
     * Reads a XML file and stores the quests and rules into the data-sets.
     *
     * @param quests collection needed to store quests.
     * @param rules map to store the rules.
     * @param lang_code a xsd:language code.
     * @param fileStream a file stream with the XML to parse.
     * @throws IOException
     * @see InputStream
     * @see QuestCollection
     * @see RuleMap
     */
    public void getQuests(QuestCollection quests, RuleMap rules, String lang_code, InputStream fileStream) throws IOException {
        try {
            XMLReader xr = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            xr.setContentHandler(new QuestDataXMLHandler(quests, rules, lang_code));
            xr.parse(new InputSource(fileStream));
        } catch (SAXException e) {
            /**
             * It is ok to ignore this exception. This app will not collapse
             * without parsing this file.
             */
        } catch (ParserConfigurationException e) {
            /**
             * It is ok to ignore this exception. This app will not collapse
             * without parsing this file.
             */
        }
    }

    /**
     * Reads a XML file and stores the quests and rules into the data-sets.
     *
     * @param quests collection needed to store quests.
     * @param rules map to store the rules.
     * @param lang_code a xsd:language code.
     * @param parser a pull-parser with the XML to parse.
     * @throws IOException
     * @see QuestCollection
     * @see RuleMap
     * @see XmlPullParser
     */
    public void getQuests(QuestCollection quests, RuleMap rules, String lang_code, XmlPullParser parser) throws IOException {
        TwoWayHandler qdxHandler = new QuestDataXMLHandler(quests, rules, lang_code);
        try {
            qdxHandler.read(parser);
        } catch (XmlPullParserException e) {
            /**
             * It is ok to ignore this exception. This app will not collapse
             * without parsing this file.
             */
        }
    }
}
