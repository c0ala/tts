package at.coala.games.tts.dba.xml.helper;

import org.xml.sax.Attributes;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import at.coala.games.tts.data.quest.QuestCollection;
import at.coala.games.tts.dba.xml.QuestDataXMLAccess;

/**
 * A Helper to extract data out of XML language elements.
 *
 * @author Klaus
 */
class LanguageElementXMLHelper extends ElementXMLHelper {

    /**
     * A private variable containing the Helper for nested XML elements.
     *
     * @see ElementXMLHelper
     */
    private ElementXMLHelper child;

    /**
     * A private variable containing a xsd:language code.
     */
    private String lang_code;

    /**
     * A private variable containing the Helper for the parent XML element.
     *
     * @see android.sax.Element
     */
    private ElementXMLHelper parent;

    /**
     * Create a new Helper for XML language elements.
     *
     * @param parent Helper for parent XML element.
     * @param quests collection needed to store quests.
     * @param lang_code a xsd:language code.
     * @see ElementXMLHelper
     * @see Quest
     * @see QuestCollection
     */
    LanguageElementXMLHelper(ElementXMLHelper parent, QuestCollection quests, String lang_code) {
        this.parent = parent;
        this.child = new QuestTextElementXMLHelper(parent, quests);
        this.lang_code = lang_code;
    }

    /**
     * This method is to call if an end-tag was read. It overrides the method
     * of ElementXMLHelper to return the parent Helper if a quest end-tag was
     * read.
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
            return parent;
        }
        return super.endElement(uri, localName, qName);
    }

    /**
     * Read the XML file with a xmlPullParser. This method overrides the method
     * of ElementXMLHelper to parse through the language section and call
     * suitable Helpers for needed upcoming XML elements.
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
            if (parser.getEventType() == XmlPullParser.START_TAG
                    && QuestDataXMLAccess.ELEMENT_LANGUAGE.equals(parser.getName())
                    && lang_code.equals(parser.getAttributeValue(
                            null, QuestDataXMLAccess.ATTRIBUTE_QUEST_LANGUAGE))) {
                    child.read(parser);
                    return;
            } else if(parser.getEventType() == XmlPullParser.END_TAG
                    && QuestDataXMLAccess.ELEMENT_QUEST.equals(parser.getName())) {
                return;
            }
        }
        super.read(parser);
    }

    /**
     * This method is to call if a start-tag was read. It overrides the method
     * of ElementXMLHelper to return a suitable Helper for needed upcoming XML
     * elements. If the language matches the wanted language code the child
     * element will be returned.
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
        if (QuestDataXMLAccess.ELEMENT_LANGUAGE.equals(localName)) {
            /**
             * TODO
             * system for better language recognition (craft to upper case,
             * object instead of string, ...)
             */
            if (lang_code.equals(
                    attributes.getValue(QuestDataXMLAccess.ATTRIBUTE_QUEST_LANGUAGE))) {
                return child;
            }
        }
        return super.startElement(uri, localName, qName, attributes);
    }
}
