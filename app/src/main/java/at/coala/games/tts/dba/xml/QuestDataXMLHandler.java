package at.coala.games.tts.dba.xml;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import at.coala.games.tts.data.quest.QuestCollection;
import at.coala.games.tts.data.quest.RuleMap;
import at.coala.games.tts.dba.xml.helper.ElementXMLHelper;
import at.coala.games.tts.dba.xml.helper.QuestElementXMLHelper;

/**
 * This class implements the TwoWayHandler interface and extends the
 * DefaultHandler. This means that there are two ways to parse a XML document.
 * First it implements the ContentHandler and so guarantees that implementing
 * classes are able to handle different basic parsing events if set als
 * ContentHandler for a standard XMLReader. And in the second place it
 * guarantees the implementation of a method that takes a XmlPullParser. For
 * most Android use cases this parser provides a more efficient way to parse
 * XML documents, however it needs some kind of precompiled XML-source. Best
 * practise is to implement both ways to parse XML and combine further
 * functionality like creating data and objects out of the XML.
 *
 * @author Klaus
 * @see org.xml.sax.ContentHandler
 * @see DefaultHandler
 * @see TwoWayHandler
 * @see XmlPullParser
 * @see org.xml.sax.XMLReader
 */
class QuestDataXMLHandler extends DefaultHandler implements TwoWayHandler {

    /**
     * A Helper that is called for all relevant actions.
     */
    private ElementXMLHelper xmlHelper;

    /**
     * Create a new QuestDataXMLHandler.
     *
     * @param quests collection needed to store quests.
     * @param rules map to store the rules.
     * @param lang_code a xsd:language code.
     * @see RuleMap
     * @see QuestCollection
     */
    QuestDataXMLHandler(QuestCollection quests, RuleMap rules, String lang_code) {
        xmlHelper = new QuestElementXMLHelper(quests, rules, lang_code);
    }

    /**
     * This method is called after xml content (text) was read. For more
     * information read the method description in DefaultHandler or
     * TwoWayHandler.
     *
     * @param ch the characters from the XML document.
     * @param start the start position in the array.
     * @param length the number of characters to read from the array.
     * @see DefaultHandler
     * @see TwoWayHandler
     */
    @Override
    public void characters(char[] ch, int start, int length) {
        xmlHelper.characters(ch, start, length);
    }

    /**
     * This method is called after an end-tag was read. For more information
     * read the method description in DefaultHandler or TwoWayHandler.
     *
     * @param uri the Namespace URI, or the empty string if the element has no
     *            Namespace URI or if Namespace processing is not being
     *            performed.
     * @param localName the local name (without prefix), or the empty string if
     *                  Namespace processing is not being performed.
     * @param qName the qualified name (with prefix), or the empty string if
     *              qualified names are not available.
     * @see DefaultHandler
     * @see TwoWayHandler
     */
    @Override
    public void endElement(String uri, String localName, String qName) {
        xmlHelper = xmlHelper.endElement(uri, localName, qName);
    }

    /**
     * Read the XML file with a xmlPullParser. For more information read the
     * method description in TwoWayHandler.
     *
     * @param parser the parser.
     * @throws IOException
     * @throws XmlPullParserException - thrown to signal XML Pull Parser
     * related faults.
     * @see TwoWayHandler
     * @see XmlPullParser
     */
    @Override
    public void read(XmlPullParser parser) throws IOException, XmlPullParserException {
        xmlHelper.read(parser);
    }

    /**
     * This method is called after a start-tag was read. For more information
     * read the method description in DefaultHandler or TwoWayHandler.
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
     * @see Attributes
     * @see DefaultHandler
     * @see TwoWayHandler
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        xmlHelper = xmlHelper.startElement(uri, localName, qName, attributes);
    }
}
