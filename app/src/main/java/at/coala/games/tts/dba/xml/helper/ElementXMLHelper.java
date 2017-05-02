package at.coala.games.tts.dba.xml.helper;

import org.xml.sax.Attributes;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * A Helper for a TwoWayHandler used if the schema of the XML-file is known.
 * Best practise is to implement a new subclass for every XML element. The
 * methods useful for parsing with a subclass of DefaultHandler provide
 * returning the ElementXMLHelper that can parse the next XML element of the
 * document. The method useful for parsing with an XmlPullParser can
 * recursively call subclasses to go deeper into the XML tree.
 *
 * @author Klaus
 * @see org.xml.sax.helpers.DefaultHandler
 * @see XmlPullParser
 */
public abstract class ElementXMLHelper {

    /**
     * A String containing the content read by characters().
     */
    String eleText = "";

    /**
     * This method is to call if xml content (text) was read. It writes the
     * read text into the field eleText. This method is final and can not be
     * overridden.
     *
     * @param ch the characters from the XML document.
     * @param start the start position in the array.
     * @param length the number of characters to read from the array.
     */
    public final void characters(char[] ch, int start, int length) {
        eleText = new String(ch, start, length);
    }

    /**
     * This method is to call if an end-tag was read. It does nothing and
     * returns this if not overridden.
     *
     * @param uri the Namespace URI, or the empty string if the element has no
     *            Namespace URI or if Namespace processing is not being
     *            performed.
     * @param localName the local name (without prefix), or the empty string if
     *                  Namespace processing is not being performed.
     * @param qName the qualified name (with prefix), or the empty string if
     *              qualified names are not available.
     * @return the ElementXMLHelper best suitable for the next action.
     */
    public ElementXMLHelper endElement(String uri, String localName, String qName) {
        return this;
    }

    /**
     * Read the XML file with a xmlPullParser.
     *
     * @param parser the parser.
     * @throws IOException
     * @throws XmlPullParserException - thrown to signal XML Pull Parser
     * related faults.
     * @see XmlPullParser - thrown to signal XML Pull Parser related faults.
     */
    public void read(XmlPullParser parser) throws IOException, XmlPullParserException {}

    /**
     * This method is to call if a start-tag was read. It does nothing and
     * returns this if not overridden.
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
     */
    public ElementXMLHelper startElement(String uri, String localName, String qName, Attributes attributes) {
        return this;
    }
}
