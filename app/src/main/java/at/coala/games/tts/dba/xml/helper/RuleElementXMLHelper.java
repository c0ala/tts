package at.coala.games.tts.dba.xml.helper;


import org.xml.sax.Attributes;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import at.coala.games.tts.data.quest.RuleMap;
import at.coala.games.tts.dba.xml.QuestDataXMLAccess;

/**
 * A Helper to extract data out of XML rule elements.
 *
 * @author Klaus
 * @version 1.0
 * @since 16.07.2015.
 */
class RuleElementXMLHelper extends ElementXMLHelper {

    /**
     * A private variable containing the Helper for the nested XML element.
     *
     * @see ElementXMLHelper
     */
    private ElementXMLHelper child;

    /**
     * A private array with length == 1 containing the rule id. This array gets
     * set here, but read in another helper.
     */
    private final String[] rule_id;

    /**
     * Create a new Helper for XML rule elements.
     *
     * @param rules map to store the rules.
     * @param lang_code a xsd:language code.
     * @see RuleMap
     */
    RuleElementXMLHelper(RuleMap rules, String lang_code) {
        rule_id = new String[1];
        child = new RuleTextElementXMLHelper(this, rules, lang_code, rule_id);
    }

    /**
     * Read the XML file with a xmlPullParser. This method overrides the method
     * of ElementXMLHelper to parse through the rule section, retrieve given
     * attributes and call suitable Helpers for needed XML rule text elements.
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
            if (parser.getEventType() == XmlPullParser.START_TAG && QuestDataXMLAccess.ELEMENT_RULE.equals(parser.getName())) {
                rule_id[0] = parser.getAttributeValue(null, QuestDataXMLAccess.ATTRIBUTE_RULE_ID);
                child.read(parser);
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
        if (QuestDataXMLAccess.ELEMENT_RULE.equals(localName)) {
            rule_id[0] = attributes.getType(QuestDataXMLAccess.ATTRIBUTE_RULE_ID);
            return child;
        }
        return super.startElement(uri, localName, qName, attributes);
    }
}
