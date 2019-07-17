package at.coala.games.tts.dba.xml.helper;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import at.coala.games.tts.data.quest.QuestCollection;
import at.coala.games.tts.dba.xml.QuestDataXMLAccess;


/**
 * A Helper to extract data out of XML quest_text and comment elements.
 *
 * @author Klaus
 */
class QuestTextElementXMLHelper extends ElementXMLHelper {

    /**
     * A local list containing the comments This list gets filled here, but
     * read and reset in another helper.
     *
     * @see List
     */
    private final List<String> comments;

    /**
     * A local list containing the quest text. This list gets filled here, but
     * read and reset in another helper.
     *
     * @see List
     */
    private final List<String> text;

    /**
     * A private variable containing the Helper for the parent's following XML
     * element.
     *
     * @see ElementXMLHelper
     */
    private ElementXMLHelper uncle;

    /**
     * Create a new Helper for XML quest_text elements.
     *
     * @param grand_parent Helper for the parent' parent XML element.
     * @param quests collection needed to store quests.
     * @see ElementXMLHelper
     * @see at.coala.games.tts.data.quest.Quest
     * @see QuestCollection
     */
    QuestTextElementXMLHelper(ElementXMLHelper grand_parent, QuestCollection quests) {
        comments = new ArrayList<String>(3);
        text = new ArrayList<String>(2);
        uncle = new RuleConfSourceElementsXMLHelper(grand_parent, quests, comments, text);
    }

    /**
     * This method is to call if an end-tag was read. It overrides the method
     * of ElementXMLHelper to retrieve the text written before the end tag and
     * add it to the quest text and comments.
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
        if (QuestDataXMLAccess.ELEMENT_QUEST_TEXT.equals(localName)) text.add(eleText);
        else if (QuestDataXMLAccess.ELEMENT_COMMENT.equals(localName)) comments.add(eleText);
        else if (QuestDataXMLAccess.ELEMENT_LANGUAGE.equals(localName)) return uncle;
       return super.endElement(uri, localName, qName);
    }

    /**
     * Read the XML file with a xmlPullParser. This method overrides the method
     * of ElementXMLHelper to parse through the quest_text and comment section,
     * retrieve them and call a suitable Helper for needed upcoming XML elements.
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
                if (QuestDataXMLAccess.ELEMENT_QUEST_TEXT.equals(parser.getName())) {
                    if (parser.next() == XmlPullParser.TEXT) text.add(parser.getText());
                } else if (QuestDataXMLAccess.ELEMENT_COMMENT.equals(parser.getName())) {
                    if (parser.next() == XmlPullParser.TEXT) comments.add(parser.getText());
                }
            }
            else if (parser.getEventType() == XmlPullParser.END_TAG
                    && QuestDataXMLAccess.ELEMENT_LANGUAGE.equals(parser.getName())) {
                uncle.read(parser);
                return;
            }
        }
        super.read(parser);
    }
}
