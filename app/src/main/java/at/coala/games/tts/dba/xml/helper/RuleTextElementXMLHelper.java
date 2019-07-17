package at.coala.games.tts.dba.xml.helper;


import org.xml.sax.Attributes;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import at.coala.games.tts.data.quest.RuleMap;
import at.coala.games.tts.dba.xml.QuestDataXMLAccess;

/**
 * A Helper to extract data out of XML rule_text elements and finally adds a
 * rule to the RuleMap.
 *
 * @author Klaus
 * @see RuleMap
 */
class RuleTextElementXMLHelper extends ElementXMLHelper {

    /**
     * A private variable containing a xsd:language code.
     */
    private String lang_code;

    /**
     * A private flag set true if the right language was found.
     */
    private boolean match;

    /**
     * A private variable containing the Helper for the parent XML element.
     *
     * @see android.sax.Element
     */
    private ElementXMLHelper parent;

    /**
     * a rules map to add the rules.
     */
    private RuleMap rules;

    /**
     * A private array with length == 1 containing the rule id. This array gets
     * read here, but set in another helper.
     */
    private final String[] rule_id;

    /**
     * Create a new Helper for XML rule_text elements.
     *
     * @param parent Helper for parent XML element.
     * @param rules map to store the rules.
     * @param lang_code a xsd:language code.
     * @param rule_id the rule id.
     * @see ElementXMLHelper
     * @see RuleMap
     */
    RuleTextElementXMLHelper(
            ElementXMLHelper parent, RuleMap rules, String lang_code, String[] rule_id) {
        this.parent = parent;
        this.rules = rules;
        this.lang_code = lang_code;
        this.rule_id = rule_id;
        match = false;
    }

    /**
     * This method is to call if an end-tag was read. It overrides the method
     * of ElementXMLHelper to retrieve the text written before the end tag and
     * add it to the source if it matches the needed language.
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
        if (match) {
            rules.addRule(rule_id[0], eleText);
            match = false;
            return parent;
        } else if (QuestDataXMLAccess.ELEMENT_RULE.equals(localName)) {
            return parent;
        }
        return super.endElement(uri, localName, qName);
    }

    /**
     * Read the XML file with a xmlPullParser. This method overrides the method
     * of ElementXMLHelper to parse through the rule_text section and add a new
     * rule.
     *
     * @param parser the parser.
     * @throws IOException
     * @throws XmlPullParserException - thrown to signal XML Pull Parser
     * related faults.
     * @see ElementXMLHelper
     * @see XmlPullParser
     */
    @Override
    public void read(XmlPullParser parser) throws IOException, XmlPullParserException {
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() == XmlPullParser.START_TAG
                    && QuestDataXMLAccess.ELEMENT_RULE_TEXT.equals(parser.getName())
                    && lang_code.equals(parser.getAttributeValue(
                            null, QuestDataXMLAccess.ATTRIBUTE_RULE_LANGUAGE))
                    && parser.next() == XmlPullParser.TEXT) {
                rules.addRule(rule_id[0], parser.getText());
                return;
            } else if (parser.getEventType() == XmlPullParser.END_TAG
                    && QuestDataXMLAccess.ELEMENT_RULE.equals(parser.getName())) {
                return;
            }
        }

        super.read(parser);
    }

    /**
     * This method is to call if a start-tag was read. It overrides the method
     * of ElementXMLHelper to check if the language matches the wanted
     * language.
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
    public ElementXMLHelper startElement(
            String uri, String localName, String qName, Attributes attributes) {
        if (QuestDataXMLAccess.ELEMENT_RULE_TEXT.equals(localName)) {
            /**
             * TODO
             * system for better language recognition (craft to upper case, object instead of string, ...)
             */
            match = lang_code.equals(
                    attributes.getValue(QuestDataXMLAccess.ATTRIBUTE_RULE_LANGUAGE));
        }
        return super.startElement(uri, localName, qName, attributes);
    }
}
