package at.coala.games.tts.dba.xml.helper;

import org.xml.sax.Attributes;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import at.coala.games.tts.data.quest.Quest;
import at.coala.games.tts.data.quest.QuestCollection;
import at.coala.games.tts.data.quest.RuleMap;
import at.coala.games.tts.dba.xml.QuestDataXMLAccess;

/**
 * A Helper to extract data out of XML quest elements.
 *
 * @author Klaus
 * @version 1.1
 * @since 15.07.2015.
 */
public class QuestElementXMLHelper extends ElementXMLHelper {

    /**
     * A private variable containing the Helper for the first nested XML
     * element.
     *
     * @see ElementXMLHelper
     */
    private ElementXMLHelper firstChild;

    /**
     * A private variable containing the Helper for everything after the
     * questions section.
     *
     * @see ElementXMLHelper
     */
    private ElementXMLHelper ruleHelper;

    /**
     * Create a new Helper for XML quest elements.
     *
     * @param quests collection needed to store quests.
     * @param rules map to store the rules.
     * @param lang_code a xsd:language code.
     * @see QuestCollection
     * @see RuleMap
     */
    public QuestElementXMLHelper(QuestCollection quests, RuleMap rules, String lang_code) {
        this.firstChild = new LanguageElementXMLHelper(this, quests, lang_code);
        this.ruleHelper = new RuleElementXMLHelper(rules, lang_code);
    }

    /**
     * Read the XML file with a xmlPullParser. This method overrides the method
     * of ElementXMLHelper to parse through the quest section, retrieve given
     * attributes and call suitable Helpers for needed upcoming XML elements.
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
        while(parser.next() != XmlPullParser.END_DOCUMENT) {
            if(parser.getEventType() == XmlPullParser.START_TAG) {
                if (QuestDataXMLAccess.ELEMENT_QUEST.equals(parser.getName())) {
                    firstChild.read(parser);
                } else if (QuestDataXMLAccess.ELEMENT_RULES.equals(parser.getName())) {
                    ruleHelper.read(parser);
                    return;
                }
            }
        }
        super.read(parser);
    }

    /**
     * This method is to call if a start-tag was read. It overrides the method
     * of ElementXMLHelper to retrieve given attributes and return a suitable
     * Helper for needed upcoming XML elements.
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
        if (QuestDataXMLAccess.ELEMENT_QUEST.equals(localName)) {
            return firstChild;
        } else if (QuestDataXMLAccess.ELEMENT_RULES.equals(localName)) return ruleHelper;
        return super.startElement(uri, localName, qName, attributes);
    }
}
