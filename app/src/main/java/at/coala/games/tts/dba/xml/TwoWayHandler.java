package at.coala.games.tts.dba.xml;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * This interface combines two ways of parsing XML. First it implements the
 * ContentHandler and so guarantees that implementing classes are able to
 * handle different basic parsing events if set als ContentHandler for a
 * standard XMLReader. And in the second place it guarantees the implementation
 * of a method that takes a XmlPullParser. For most Android use cases this
 * parser provides a more efficient way to parse XML documents, however it
 * needs some kind of precompiled XML-source. Best practise is to implement
 * both ways to parse XML and combine further functionality like creating data
 * and objects out of the XML.
 *
 * @author Klaus
 * @see ContentHandler
 * @see XmlPullParser
 * @see org.xml.sax.XMLReader
 */
interface TwoWayHandler extends ContentHandler {

    /**
     * This method is called after xml content (text) was read. For more
     * information read the method description in ContentHandler.
     *
     * @param ch the characters from the XML document.
     * @param start the start position in the array.
     * @param length the number of characters to read from the array.
     * @see ContentHandler
     */
    @Override
    void characters(char[] ch, int start, int length);

    /**
     * This method is called after an end-tag was read. For more information
     * read the method description in ContentHandler.
     *
     * @param uri the Namespace URI, or the empty string if the element has no
     *            Namespace URI or if Namespace processing is not being
     *            performed.
     * @param localName the local name (without prefix), or the empty string if
     *                  Namespace processing is not being performed.
     * @param qName the qualified name (with prefix), or the empty string if
     *              qualified names are not available.
     * @see ContentHandler
     */
    @Override
    void endElement(String uri, String localName, String qName);

    /**
     * Read the XML file with a xmlPullParser.
     *
     * @param xmlPullParser the parser.
     * @throws IOException
     * @throws XmlPullParserException - thrown to signal XML Pull Parser
     * related faults.
     * @see XmlPullParser
     */
    void read(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException;

    /**
     * This method is called after a start-tag was read. For more information
     * read the method description in ContentHandler.
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
     * @see ContentHandler
     */
    @Override
    void startElement(String uri, String localName, String qName, Attributes attributes);
}
