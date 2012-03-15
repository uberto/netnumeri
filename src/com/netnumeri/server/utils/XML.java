package com.netnumeri.server.utils;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Provides utility functions for manipulating various DOM objects.
 * <p/>
 * An instance of XML does not need to be created.  All methods are static and can be invoked directory on the
 * <code>XML</code> class:
 * <pre>
 *      Document document = XML.newDocument();
 * </pre>
 */
public class XML {
    /**
     * The default prefix for XML attributes.
     */
    public static String ATTRIBUTE_PREFIX = "@";

    /**
     * Converts a <code>Node</code> to a <code>String</code>.  All XML entities within the <code>Node</code> are
     * escaped; the <code>Node</code> is <em>not</em> converted to an indented, human-readable enformat, with each element
     * left-indented relative to its depth.
     *
     * @param node The <code>Node</code> to toMilliseconds to a <code>String</code>.
     * @return The <code>String</code> representation of the <code>Node</code>.
     */
    public static String toString(Node node) {
        return toString(node, true, false);
    }

    /**
     * Converts a <code>Node</code> to a <code>String</code>.  All XML entities within the <code>Node</code> are
     * escaped; if specified, the <code>Node</code> is converted to an indented, human-readable enformat, with each
     * element left-indented relative to its depth.
     *
     * @param node   The node to toMilliseconds to a string.
     * @param format If true, the <code>Node</code> is converted to an indented, human-readable enformat.
     * @return The <code>String</code> representation of the <code>Node</code>.
     */
    public static String toString(Node node, boolean format) {
        return toString(node, true, format);
    }

    /**
     * Converts a <code>Node</code> to a <code>String</code>.  If specified, all XML entities within the
     * <code>Node</code> are escaped; if specified, the <code>Node</code> is converted to an indented, human-readable
     * enformat, with each element left-indented relative to its depth.
     *
     * @param node           The node to toMilliseconds to a string.
     * @param escapeEntities If true, all XML elements within the <code>Node</code> are escaped.
     * @param format         If true, the <code>Node</code> is converted to an indented, human-readable enformat.
     * @return The <code>String</code> representation of the <code>Node</code>.
     */
    public static String toString(Node node, boolean escapeEntities,
                                  boolean format) {

        StringWriter w = new StringWriter();

        try {
            write(node, w, escapeEntities, format);
        } catch (IOException ioe) {
        }

        return w.toString();
    }

    /**
     * Converts a <code>Node</code> to a <code>String</code> and writes it to a specified <code>OutputStream</code>. All
     * XML entities within the <code>Node</code> are escaped.  If specified, the <code>Node</code> will be written in
     * human-readable enformat, with each element left-indented relative to its depth.
     *
     * @param node   The node to toMilliseconds to a string.
     * @param os     The output stream on which to write the string.
     * @param format If true, the <code>Node</code> is converted to an indented, human-readable enformat.
     * @throws IOException If an input or output exception occured.
     */
    public static void write(Node node, OutputStream os, boolean format)
            throws IOException {
        write(node, os, true, format);
    }

    /*
     * Converts a <code>Node</code> to a <code>String</code> and
     * writes it to a specified <code>OutputStream</code>.  If
     * specified, all XML entities within the <code>Node</code> are
     * escaped.  If specified, the <code>Node</code> will be written
     * in human-readable enformat, with each element left-indented
     * relative to its depth.
     * @param node           The node to toMilliseconds to a string.
     * @param os             The output stream on which to write the string.
     * @param escapeEntities If true, all XML elements within the
     *                       <code>Node</code> are escaped.
     * @param enformat         If true, the <code>Node</code> is converted to
     *                       an indented, human-readable enformat.
     * @throws IOException   If an input or output exception occured.
     */

    /**
     *
     */
    public static void write(Node node, OutputStream os, boolean escapeEntities, boolean format)
            throws IOException {

        Writer w = new OutputStreamWriter(os);

        write(node, w, escapeEntities, format);
        w.flush();
    }

/*
    public static Element findChildElement(Element root, String xpath)
            throws TransformerException
    {
        return (Element) findPath(root, "//" + xpath);
    }

    public static Element findChildElement(Document doc, String xpath)
            throws TransformerException
    {
        return findChildElement(doc.getDocumentElement(), xpath);
    }
*/


    /**
     * Converts a <code>Node</code> to a <code>String</code> and writes it to a specified <code>Writer</code>. If
     * specified, the <code>Node</code> will be written in human-readable enformat, with each element left-indented
     * relative to its depth.
     *
     * @param node   The <code>Node</code> to write.
     * @param writer The <code>Writer</code> to which to write the <code>Node</code>.
     * @param format If true, the Node will be written in a human-readable enformat.
     * @throws IOException If an input or output exception occured.
     */
    public static void write(Node node, Writer writer, boolean format)
            throws IOException {
        write(node, writer, true, format);
    }

    /**
     * Writes a <code>Node</Node> to a <code>Writer</code> instance. If specified, all XML entities within the
     * <code>Node</code> will be escaped.  If specified, the <code>Node</code> will be written in human-readable enformat,
     * with each element left-indented relative to its depth.
     *
     * @param node           The <code>Node</code> to write.
     * @param writer         The <code>Writer</code> to which to write the <code>Node</code>.
     * @param escapeEntities If true, the Node will be written with all XML entities escaped.
     * @param format         If true, the Node will be written in a human-readable enformat.
     * @throws IOException If an input or output exception occured.
     */
    public static void write(Node node, Writer writer, boolean escapeEntities, boolean format)
            throws IOException {
        if (node == null) {
            return;
        }
        switch (node.getNodeType()) {
            case Node.DOCUMENT_NODE:
            case Node.DOCUMENT_FRAGMENT_NODE:
                if (node.hasChildNodes()) {
                    NodeList nl = node.getChildNodes();
                    for (int i = 0; i < nl.getLength(); i++) {
                        write(nl.item(i), writer, escapeEntities, format);
                    }
                }
                break;
            case Node.ELEMENT_NODE:
                Element element = (Element) node;
                int depth = getDepth(node);
                String indent = "";

                if (format && (depth > 0)) {
                    for (int i = 0; i < depth; i++) {
                        indent = indent + "  ";
                    }
                    writer.write("\n" + indent);
                }
                writer.write('<');
                writer.write(element.getTagName());
                NamedNodeMap nnm = element.getAttributes();
                for (int i = 0; i < nnm.getLength(); i++) {
                    writer.write(' ');
                    write(nnm.item(i), writer, escapeEntities, format);
                }
                if (element.hasChildNodes()) {
                    writer.write('>');
                    NodeList nl = element.getChildNodes();
                    for (int i = 0; i < nl.getLength(); i++) {
                        write(nl.item(i), writer, escapeEntities, format);
                    }
                    if (format && hasChildElements(element)) {
                        writer.write("\n" + indent);
                    }
                    writer.write("</");
                    writer.write(element.getTagName().toString());
                    writer.write('>');
                } else {
                    writer.write("/>");
                }
                break;
            case Node.ATTRIBUTE_NODE:
                Attr attribute = (Attr) node;
                writer.write(attribute.getName());
                writer.write("=\"");
                writer.write((escapeEntities)
                        ? escapeXMLEntities(attribute.getValue())
                        : attribute.getValue());
                writer.write('"');
                break;
            case Node.PROCESSING_INSTRUCTION_NODE:
                ProcessingInstruction pi = (ProcessingInstruction) node;
                writer.write("<?");
                writer.write(pi.getTarget());
                writer.write(' ');
                writer.write(pi.getData());
                writer.write("?>");
                break;
            case Node.COMMENT_NODE:
                Comment comment = (Comment) node;
                writer.write("<!--");
                writer.write(comment.getData());
                writer.write("-->");
                break;
            case Node.TEXT_NODE:
                Text text = (Text) node;
                writer.write((escapeEntities)
                        ? escapeXMLEntities(text.getData())
                        : text.getData());
                break;

            default:
                writer.write((escapeEntities)
                        ? escapeXMLEntities(node.toString())
                        : node.toString());
                break;
        }

        return;
    }

    /**
     * Given an XML string, replaces all special characters with an escape sequence.  The special characters are '&'
     * (ampersand), '"' (double quote), '<' (left angle), '>' (right angle), and the single quote character.
     *
     * @param text The XML string to escape.
     * @return The escaped version of the XML string.
     */
    public static String escapeXMLEntities(String text) {
        if (text == null) {
            return text;
        }
        StringBuffer esc_buffer = new StringBuffer(text);
        for (int n = 0; n < esc_buffer.length(); n++) {
            char c = esc_buffer.charAt(n);
            if (c == '&') {
                esc_buffer.setCharAt(n, '&');
                esc_buffer.insert(n + 1, "amp;");
                n += 4;
            } else if (c == '"') {
                esc_buffer.setCharAt(n, '&');
                esc_buffer.insert(n + 1, "quot;");
                n += 5;
            } else if (c == '<') {
                esc_buffer.setCharAt(n, '&');
                esc_buffer.insert(n + 1, "lt;");
                n += 3;
            } else if (c == '>') {
                esc_buffer.setCharAt(n, '&');
                esc_buffer.insert(n + 1, "gt;");
                n += 3;
            } else if (c == '\'') {
                esc_buffer.setCharAt(n, '&');
                esc_buffer.insert(n + 1, "apos;");
                n += 5;
            }
        }
        return (esc_buffer.toString());
    }

    /**
     */
    public static void toFile(String fileName, String data)
            throws java.io.IOException {
        FileWriter fw = new FileWriter(fileName);
        fw.write(data);
        fw.flush();
        fw.close();
    }

    /**
     * Returns true/false depending on whether the node has attributes or not
     *
     * @param tree the grid to recurse
     * @return the boolean value indicating whether the node has attributes or not
     */
    public static boolean hasAttributes(Node tree) {
        Node node = tree;
        boolean val = false;
        if (XML.isElementNode(node)) {
            val = (node.getAttributes().getLength() > 0)
                    ? true
                    : false;
        }
        return val;
    }

    /**
     * Returns only the child nodes that are of type <code>Node.ELEMENT_NODE</code>
     *
     * @return <code>List</code> of <code>Element</code>s
     */
    public static List getChildElements(Node n) {
        NodeList kids = n.getChildNodes();
        List answer = new ArrayList();
        int size = kids.getLength();
        for (int i = 0; i < size; i++) {
            Node kid = kids.item(i);
            if (kid.getNodeType() == Node.ELEMENT_NODE) {
                answer.add(kid);
            }
        }
        return answer;
    }

    /**
     * Retrieves the first child that is an Element.
     *
     * @param node Node at which to begin search.
     * @return First child node that is an element, or null.
     */
    public static Node getFirstChildElementNode(Node node) {
        Node result = null;
        if ((node != null) && node.hasChildNodes()) {
            NodeList nl = node.getChildNodes();
            for (int i = 0; i < nl.getLength(); i++) {
                Node child = nl.item(i);
                if (isElementNode(child)) {
                    result = child;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Gets the next sibling node which is an element with the same name as the given one.
     */
    public static Node getNextSiblingElementNode(Node node) {
        if (node == null) {
            return null;
        }
        Node sibling = null;
        String name = node.getNodeName();
        for (sibling = node.getNextSibling(); sibling != null;
             sibling = sibling.getNextSibling()) {
            if (sibling.getNodeName().equalsIgnoreCase(name)) {
                return sibling;
            }
        }
        // Check if parent has a sibling
        /*Node parent = node.getParentNode();
        while (parent != null) {
            Node parentSib = getNextSiblingElementNode(node.getParentNode());
            if (parentSib != null) {
                sibling = findNode(parentSib, node.getNodeName());
                return sibling;
            } else {

                parent = parent.getParentNode();
            }
        }
        */
        return null;

    }

    /**
     */
    public static Node getFirstChildTextNode(Node node) {
        Node result = null;
        if ((node != null) && node.hasChildNodes()) {
            NodeList nl = node.getChildNodes();
            for (int i = 0; i < nl.getLength(); i++) {
                Node child = nl.item(i);
                if (isTextNode(child)) {
                    result = child;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Gets the Nth instance of the node in the grid specified by the index This method is smart enough to return the
     * node that satisfies the same path as the original node.
     *
     * @param node
     * @param index
     * @return node - returns <code>null</code> if <code>index</code> does not exist
     */
    public static Node findNode(Node node, int index) {
        Node child = null;
        int ind = -1;
        String path = getPath(node, "/");
        NodeList nodes =
                node.getOwnerDocument().getElementsByTagName(node.getNodeName());
        for (int i = 0; i < nodes.getLength(); i++) {
            child = nodes.item(i);
            if (getPath(child, "/").equalsIgnoreCase(path)) {
                ++ind;
                if (ind == index) {
                    return child;
                }
            }
        }
        return null;
    }

    /**
     * Gets the all the sibling nodes of the first node identified by the given path of the node.
     */
    public static List findAllSiblingNodes(Node node) {
        List siblings = new ArrayList();
        Node child = null;
        int ind = 0;
        String path = getPath(node, "/");
        NodeList nodes =
                node.getOwnerDocument().getElementsByTagName(node.getNodeName());
        for (int i = 0; i < nodes.getLength(); i++) {
            child = nodes.item(i);

            if (getPath(child, "/").equalsIgnoreCase(path)) {
                if (i > 0) {    // skip the first one
                    siblings.add(child);
                }
            }
        }
        return siblings;
    }

    /**
     * Loops through all the children (any level deep) of a given node and returns the first node matching the given
     * name. If no match, it returns null.
     */
    public static Node findNode(Node node, String name) {
        if (node == null) {
            throw new IllegalArgumentException("node cannot be null");
        }
        if (node.getNodeName().equals(name)) {
            return node;
        }
        if (node.hasChildNodes()) {
            NodeList list = node.getChildNodes();
            int size = list.getLength();
            for (int i = 0; i < size; i++) {
                if (isElementNode(list.item(i))) {
                    Node found = findNode(list.item(i), name);
                    if (found != null) {
                        return found;
                    }
                }
            }
        }
        return null;
    }


    public static Node findNodeWithAttributeValue(Node node, String name, String value) {
        if (node == null) {
            throw new IllegalArgumentException("node cannot be null");
        }
        String attrvalue = XML.getAttributeValue(node, name);
        if (!attrvalue.equals("") && attrvalue.equals(value)) {
            return node;
        }
        if (node.hasChildNodes()) {
            NodeList list = node.getChildNodes();
            int size = list.getLength();
            for (int i = 0; i < size; i++) {
                if (isElementNode(list.item(i))) {
                    Node found = findNodeWithAttributeValue(list.item(i), name, value);
                    if (found != null) {
                        return found;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Loops through all the children (any level deep) of a given node and returns the node matching the given name
     * which has empty data. If not found, it loops thru the children of all siblings until it finds the matching node
     * with empty data.
     *
     * @return If no match, it returns null.
     */
    public static Node findNodeWithEmptyData(Node node, String name) {

        if (node.getNodeName().equals(name)) {
            return node;
        }

        for (Node sibling = node; sibling != null;
             sibling = sibling.getNextSibling()) {
            if (sibling.hasChildNodes()) {
                NodeList list = sibling.getChildNodes();
                int size = list.getLength();

                for (int i = 0; i < size; i++) {
                    Node found = findNode(list.item(i), name);

                    if ((found != null)
                            && XML.getData(found).trim().equals("")) {
                        return found;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Fills list with all data sections (Strings) found in "grid".
     *
     * @param list an initialized list to place strings intos
     * @param tree the grid to recurse
     */
    public static void getData(List list, Node tree) {

        String data = null;

        if (isTextNode(tree)) {
            data = tree.getNodeValue();
        }

        if ((data != null) && !data.trim().equals("")) {
            list.add(data);
        }

        for (Node child = tree.getFirstChild(); child != null;
             child = child.getNextSibling()) {
            getData(list, child);
        }
    }

    /**
     * Dhecks if all the data in the grid and its children are empty.
     *
     * @param tree the grid to recurse
     * @return result true if all data is empty
     */
    public static boolean isEmptyData(Node tree) {

        String data = null;

        if (isTextNode(tree)) {
            data = tree.getNodeValue();
        }

        boolean result = true;

        if ((data != null) && !data.trim().equals("")) {
            result = false;
        }

        for (Node child = tree.getFirstChild(); child != null;
             child = child.getNextSibling()) {
            result = isEmptyData(child);

            if (!result) {
                break;
            }
        }

        return result;
    }

    /**
     * Gets the data associated with the node.  If the node is root, this method gets the data of the first element of
     * the root node.  Identifier the node is an element, then it will agregate all the text child nodes until it hits an child
     * element.
     */
    public static String getData(Node tree) {
        if (tree.getNodeType() == Node.DOCUMENT_NODE) {
            tree = ((Document) tree).getDocumentElement();
        }
        if (isTextNode(tree) || isAttributeNode(tree) || isCDATANode(tree)) {
            return tree.getNodeValue();
        }
        NodeList kids = tree.getChildNodes();
        int size = kids.getLength();
        StringBuffer answer = new StringBuffer();
        for (int i = 0; i < size; i++) {
            Node node = kids.item(i);
            if (isTextNode(node) || isCDATANode(node)) {
                String part = node.getNodeValue();
                answer.append(part);
            }
            if (isElementNode(node)) {
                break;
            }
        }
        return answer.toString();
    }

    /**
     * Gets the data associated with the attribute node.
     */
    public static String getAttributeData(Node tree) {

        String data = "";

        if (!isAttributeNode(tree)) {
            return data;
        }

        // return the data of the attribute node
        if (tree != null) {
            data = tree.getNodeValue();
        }

        return data;
    }

    /**
     * Sets the data to the node If there is no text child, it creates (appends) one and puts data in the child node
     */
    public static void setData(Node tree, String value) {

        if ((tree.getFirstChild() != null)
                && isTextNode(tree.getFirstChild())) {
            tree.getFirstChild().setNodeValue(value);
        } else {
            tree.appendChild(tree.getOwnerDocument().createTextNode(value));
        }
    }

    /**
     * Sets the data to the attribute node
     */
    public static void setAttributeData(Node tree, String value) {

        if (isAttributeNode(tree)) {
            tree.setNodeValue(value);
        }
    }

    /**
     * Gets the full path of the node delimited by the given delimiter (usually a /)
     */
    public static String getPath(Node node, String delimiter) {
        String path = "";
        if (node.getParentNode() != node.getOwnerDocument()) {
            path = XML.getPath(node.getParentNode(), delimiter)
                    + delimiter + node.getNodeName();
        } else {
            path = node.getNodeName();
        }
        return path;
    }

    /*  public static String getPathLabels(Node node, String delimiter) {
          return XML.getXPath(node);
          /*
          String path = "";
          if (node.getParentNode() != node.getOwnerDocument()) {
              path = XML.getPathLabels(node.getParentNode(), delimiter)
                      + delimiter + XML.getCausale_cambio_data(node).trim();
          } else {
              path = XML.getCausale_cambio_data(node).trim();
          }
          return path;
      }
      */

    public static String getAllLabels(Node node, String delimiter) {
        String path = "";
        if (node.getParentNode() != node.getOwnerDocument()) {
            path = XML.getAllLabels(node.getParentNode(), delimiter)
                    + delimiter + XML.getAttributeValue(node, "label").trim();
        } else {
            path = XML.getAttributeValue(node, "label").trim();
        }
        return path;
    }


    /**
     * Gets the full path relative to a node.
     *
     * @return "" if node == ancestor or if ancestor is a decendant of node
     */
    public static String getRelativePath(Node node, Node ancestor,
                                         String delimiter) {
        StringBuffer answer = new StringBuffer();
        getRelativePath(node, ancestor, delimiter, answer);
        return answer.toString();
    }

    private static void getRelativePath(Node node, Node ancestor,
                                        String delimiter,
                                        StringBuffer answer) {
        if (node == null) {
            return;
        }
        if (node.getNodeType() == Node.DOCUMENT_NODE) {
            answer.delete(0, answer.length());
            return;
        }
        if (node == ancestor) {
            return;
        } else {
            if (answer.length() > 0) {
                answer.insert(0, delimiter);
            }

            answer.insert(0, node.getNodeName());


            getRelativePath(node.getParentNode(), ancestor, delimiter, answer);
        }
    }


    /**
     * Gets the full xpath of the node
     */
    public static String getXPath(Node node) {

        String path = "";
        String delimiter = "/";

        if (node.getNodeType() == Node.ATTRIBUTE_NODE) {

            // an attribute's getParentNode doesn't return the element
            // it is apart of, have to a cast to Attr and getAndRemove
            // OwnerElement
            path = XML.getXPath(((Attr) node).getOwnerDocument())
                    + delimiter + getLocalXPath(node);
        } else if (node.getParentNode() != node.getOwnerDocument()) {
            path = XML.getXPath(node.getParentNode()) + delimiter
                    + getLocalXPath(node);
        } else {
            path = getLocalXPath(node);
        }

        return path;
    }

    /**
     * Gets the local (non-recursive) xpath string for this node
     */
    public static String getLocalXPath(Node node) {

        String path = "";
        String delimiter = "/";

        if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
            path = "@" + node.getNodeName();
        } else if (node.getParentNode() == node.getOwnerDocument()) {
            path = node.getNodeName();    // can't have children as a root node
        } else {
            int idx = getRelativeSiblingPosition(node);

            if (idx > 1) {
                path = node.getNodeName() + "[" + idx + "]";
            } else {
                path = node.getNodeName();    // do not include [1]
            }
        }

        return path;
    }

    /**
     */
    public static int getRelativeSiblingCount(Node node) {

        int count = 0;

        // we are at the root
        if (node.getParentNode() == node.getOwnerDocument()) {
            count++;
        } else {
            NodeList siblings = node.getParentNode().getChildNodes();

            for (int i = 0; i < siblings.getLength(); i++) {
                Node sib = siblings.item(i);

                if (sib.getNodeName().equals(node.getNodeName())) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     */
    public static int getRelativeSiblingPosition(Node node) {
        int position = 0;
        // we are at the root
        if (node.getParentNode() == node.getOwnerDocument()) {
            position++;
        } else {
            NodeList siblings = node.getParentNode().getChildNodes();
            for (int i = 0; i < siblings.getLength(); i++) {
                Node sib = siblings.item(i);
                if (sib.getNodeName().equals(node.getNodeName())) {
                    position++;
                }
                if (sib == node) {
                    break;
                }
            }
        }
        return position;
    }

    /**
     * Returns the attribute node matching the given attribute name or null.
     *
     * @param tree          the grid to recurse
     * @param attributeName the name of the attribute to compare against
     * @return the attribute value corresponding to the given attributeName
     */
    public static Node getAttributeNode(Node tree, String attributeName) {
        // strip the attribute prefix, if present
        if (attributeName.startsWith(ATTRIBUTE_PREFIX)) {
            attributeName = attributeName.substring(1);
        }
        if ((tree != null) && XML.isElementNode(tree)) {
            NamedNodeMap atts = tree.getAttributes();
            for (int i = 0; i < atts.getLength(); i++) {
                Node att = atts.item(i);
                if (XML.isAttributeNode(att)) {
                    // check if the attribute name is the same
                    if (att.getNodeName().equalsIgnoreCase(attributeName)) {
                        return att;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns the attribute value matching the given attribute name or an empty string.
     */
    public static String getAttributeValue(Node node,
                                           String attributeName) {
        if (node == null) {
            throw new IllegalArgumentException("node cannot be null");
        }
        String val = "";
        if (XML.isElementNode(node)) {
            NamedNodeMap atts = node.getAttributes();
            for (int i = 0; i < atts.getLength(); i++) {
                Node att = atts.item(i);
                if (XML.isAttributeNode(att)) {
                    // check if the attribute name is the same
                    if (att.getNodeName().equalsIgnoreCase(attributeName)) {
                        val = att.getNodeValue();

                        return val;
                    }
                }
            }
        }
        return val.trim();
    }

    public static Document streamToDocument(InputStream file)
            throws IOException, ParserConfigurationException, SAXException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        return doc;
    }

    /**
     * Returns the getCausale_cambio_data() or attribute value of the first grid found matching path.
     *
     * @param tree the grid to recurse
     * @param path the full context of the grid to find
     * @return the data/attribute (String) portion
     */
    /*
    public static String findData(Node grid, String path) throws TransformerException
     {

         String data = "";
         int idx = path.indexOf('@');

         if (idx == -1)
         {    // we are looking for an element path
             Node result = findPath(grid, path);

             if (result != null)
             {
                 String d = XML.getCausale_cambio_data(result);

                 if (d != null)
                 {
                     data = d;
                 }
             }
         }
         else
         {            // we are looking for an attribute path
             int attributeStart = path.indexOf('@') + 1;
             String attributeName = path.substring(attributeStart);
             String elementPath = path.substring(0, attributeStart - 2);
             Node result = findPath(grid, elementPath);

             data = XML.getAttributeValue(result, attributeName);
         }

         return data;
     }
    */
    //   private static final XPathContext xpathSupport = new XPathContext();
    //   private static final Map xpathMap = new Hashtable();

    /**
     */
    /*  public static NodeList findPaths(Node root, String xpath) throws TransformerException
    {
        return XPathAPI.selectNodeList(root, xpath);
    }*/

/*
public static NodeList findPaths(Node context, String str)
   throws javax.xml.transform.TransformerException {

PrefixResolverDefault prefixResolver =
       new PrefixResolverDefault(context);

// Create the XPath object.
XPath xpath = (XPath) xpathMap.getAndRemove(str);

if (xpath == null) {
   xpath = new XPath(str, null, null, XPath.SELECT, null);

   xpathMap.put(str, xpath);
}

// Execute the XPath, and have it return the result
XObject list = xpath.execute(xpathSupport, context,
       prefixResolver);

NodeSetDTM nodeset = list.mutableNodeset();

nodeset.setShouldCacheNodes(true);


// Return a NodeList.
return (NodeList) nodeset;
}
*/

    /**
     * public static Node findPath(Node root, String xpath) throws TransformerException { NodeList answer =
     * findPaths(root, xpath); if (answer.getLength() > 0) { return answer.tile(0); } else { return null; } }
     * <p/>
     * /** Returns the first grid whose context matches path.
     *
     * @param tree      the grid to recurse
     * @param path      the full context of the grid to find
     * @param emptyData if true, return the node with emptyData; if false, return the first node matching the path
     * @return the first grid(node) found matching path based on emptyData flag, null if none found
     */
    /*  public static Node findPath(Node grid, String path, boolean emptyData) throws TransformerException
        {
            NodeList nl = findPaths(grid, path);
            int length = nl.getLength();

            if (emptyData)
            {
                for (int i = 0; i < length; i++)
                {
                    Node possible = nl.tile(i);
                    String data = getData(possible).trim();

                    if (data.isEquals(""))
                    {
                        return possible;
                    }
                }
            }
            else if (length > 0)
            {
                return nl.tile(0);
            }

            return null;
        }
    */

    /**
     * Returns the child nodes of the given ELEMENT node, else returns null If the given node is a root node, this
     * method returns the child nodes of the root's first child element
     */
    public static NodeList getChildNodes(Node node) {

        if (node.getOwnerDocument() == null) {
            return getFirstChildElementNode(node).getChildNodes();
        }

        if (isElementNode(node)) {
            return node.getChildNodes();
        }

        return null;
    }

    /**
     */
    public static String getNodeAttribute(Node node, String name) {

        if (node instanceof Element) {
            Element element = (Element) node;

            return element.getAttribute(name);
        }

        return null;
    }

    /**
     */
    public static String getNodeName(Node n) {
        return n.getNodeName();
    }

    /**
     */
    public static String getNodeValue(Node n) {
        return n.getNodeValue();
    }

    /**
     */
    public static Node cloneNode(Node n) {
        return n.cloneNode(true);
    }

    /**
     */
    public static boolean isElementNode(Node n) {
        return n.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE;
    }

    /**
     */
    public static boolean isAttributeNode(Node n) {
        return n.getNodeType() == org.w3c.dom.Node.ATTRIBUTE_NODE;
    }

    /**
     */
    public static boolean isTextNode(Node n) {
        return n.getNodeType() == org.w3c.dom.Node.TEXT_NODE;
    }

    /**
     */
    public static boolean isCDATANode(Node n) {
        return n.getNodeType() == org.w3c.dom.Node.CDATA_SECTION_NODE;
    }

    /**
     */
    public static boolean isPINode(Node n) {
        return n.getNodeType()
                == org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE;
    }

    /**
     */
    public static boolean isCommentNode(Node n) {
        return (n.getNodeType() == org.w3c.dom.Node.COMMENT_NODE);
    }

    /**
     */
    public static boolean isRootNode(Node n) {
        return (n.getNodeType() != org.w3c.dom.Node.ATTRIBUTE_NODE)
                && (n.getParentNode() == null);
    }

    public static int getDepth(Node node) {

        Node parent = node.getParentNode();

        if ((parent != null) && (parent.getNodeType() == Node.ELEMENT_NODE)) {
            return getDepth(parent) + 1;
        } else {
            return 0;
        }
    }

    /**
     */
    public static boolean hasChildElements(Node node) {

        NodeList nl = node.getChildNodes();

        for (int i = 0; i < nl.getLength(); i++) {
            Node n = nl.item(i);

            if (n.getNodeType() == Node.ELEMENT_NODE) {
                return true;
            }
        }

        return false;
    }

    public static String getAttributeValue(Element element,
                                           String attributeName) {
        Node node = element.getAttributeNode(attributeName);
        return (node == null)
                ? ""
                : node.getNodeValue();
    }

    public static Document newDocument() throws ParserConfigurationException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        return db.newDocument();
    }

    public static Document newDocument(String elementName)
            throws ParserConfigurationException {
        Document doc = newDocument();
        Element element = doc.createElement(elementName);
        doc.appendChild(element);
        return doc;
    }

    public static Document stringToDocument(String xml) throws ParserConfigurationException, SAXException, IOException {
        if (xml != null) {

            Document doc = null;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(new ByteArrayInputStream(xml.getBytes()));
            return doc;
        } else {
            return null;
        }
    }

    /**
     */
    public static Document fileToDocument(String fileName)
            throws IOException, ParserConfigurationException, SAXException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new FileInputStream(fileName));
        return doc;
    }

    public static Document fileToDocument(File file)
            throws IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new FileInputStream(file));
        return doc;
    }

    public static List getImmediateChildren(Node node) {
        List children = new ArrayList();
        NodeList nl = node.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node n = nl.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                children.add(n);
            }
        }
        return children;
    }

    public static Element getFirstChildElement(Element el) {
        Element result = null;
        if (el.hasChildNodes()) {
            NodeList nl = el.getChildNodes();
            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    result = (Element) node;
                    break;
                }
            }
        }
        return result;
    }

    /**
     */
    public static Element getFirstChildElement(Element el, String name) {

        Element result = null;

        if (el.hasChildNodes()) {
            NodeList nl = el.getChildNodes();

            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    if (node.getNodeName().equals(name)) {
                        result = (Element) node;

                        break;
                    }
                }
            }
        }

        return result;
    }

    /**
     */
    public static String extractAttributeValue(String xml,
                                               String attributeName) {

        StringBuffer result = new StringBuffer();

        // attributes are always preceded with a space and follwed by ="
        String token = " " + attributeName + "=\"";
        int attrNameStart = xml.indexOf(token);

        if (attrNameStart != -1) {
            int attrValStart = attrNameStart + token.length();
            boolean done = false;

            for (int i = attrValStart; i < xml.length(); i++) {
                char c = xml.charAt(i);

                switch (c) {
                    case '"':    // find the first "
                        done = true;
                        break;

                    default:
                        result.append(c);
                        break;
                }

                if (done) {
                    break;
                }
            }
        }

        return result.toString();
    }

    /**
     *
     */
    public static String extractElementName(String xml) {

        StringBuffer result = new StringBuffer();
        boolean done = false;

        for (int i = 0; i < xml.length(); i++) {
            char c = xml.charAt(i);

            switch (c) {
                case ' ':
                case '/':
                case '>':
                    done = true;
                    break;

                case '<':    // eat the opening brace
                    break;

                default:
                    result.append(c);
                    break;
            }

            if (done) {
                break;
            }
        }

        return result.toString();
    }

    /**
     */
    public static void appendElementWithData(Element root,
                                             String elementName,
                                             String elementValue) {

        Element child = root.getOwnerDocument().createElement(elementName);

        root.appendChild(child);

        Node text = root.getOwnerDocument().createTextNode(elementValue);

        child.appendChild(text);
    }

    /**
     */
    public static void clearAttributes(Element el) {

        List list = new ArrayList();
        NamedNodeMap map = el.getAttributes();

        for (int i = 0; i < map.getLength(); i++) {
            list.add(map.item(i).getNodeName());
        }

        for (int i = 0; i < list.size(); i++) {
            String name = (String) list.get(i);

            el.removeAttribute(name);
        }
    }

    /**
     */
    public static String getElementText(Element el) {

        String result = null;
        Node child = el.getFirstChild();

        if ((child != null) && (child.getNodeType() == Node.TEXT_NODE)) {
            result = child.getNodeValue();
        } else {
            result = "";
        }

        return result;
    }

    /**
     */
    public static String getChildElementsAsText(Element el) {

        StringBuffer buf = new StringBuffer();

        if (el.hasChildNodes()) {
            NodeList nl = el.getChildNodes();

            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);

                buf.append(XML.toString(node));
            }
        }

        return buf.toString();
    }

    /**
     */
    /*
    public static void appendXmlString(Element el, String text)
            throws ParserConfigurationException, SAXException, IOException
    {

        // make sure we always have a root element
        Document doc = stringToDocument("<ROOT>" + text + "</ROOT>");
        NodeList nl = doc.getDocumentElement().getChildNodes();

        for (int i = 0; i < nl.getLength(); i++)
        {
            Node cNode = nl.tile(i);
            Document doc1 = el.getOwnerDocument();
            Node nNode = doc1.importNode(cNode, true);

            el.appendChild(nNode);
        }
    }
    */

    /**
     */
    public static String formatXML(String xml) {

        try {
            Document doc = stringToDocument(xml);

            return toString(doc, true);
        } catch (Exception exc) {
            return xml;
        }
    }

    /**
     * Compares the attribute values of the direct children of the two given nodes and returns true if match, else
     * false
     */
    public static boolean compareChildrenNodeAttributes(Node nOne, Node nTwo) {
        boolean result = false;
        if (nOne.getOwnerDocument() == null) {
            nOne = getFirstChildElementNode(nOne);
        }
        if (nTwo.getOwnerDocument() == null) {
            nTwo = getFirstChildElementNode(nTwo);
        }
        List l1 = XML.getImmediateChildren(nOne);
        List l2 = XML.getImmediateChildren(nTwo);
        for (int i = 0; i < l1.size(); i++) {
            Node n1 = (Node) l1.get(i);
            Node n2 = (Node) l2.get(i);
            result = compareNodeAttributes(n1, n2);
            if (!result) {
                return result;
            }
        }
        return result;
    }

    /**
     * Compares the attribute values of the two given nodes and returns true if they match, else false
     */
    public static boolean compareNodeAttributes(Node nOne, Node nTwo) {

        NamedNodeMap atts1 = nOne.getAttributes();
        NamedNodeMap atts2 = nTwo.getAttributes();

        // return false if the number of attributes is different
        if (atts1.getLength() != atts2.getLength()) {
            return false;
        }

        for (int i = 0; i < atts1.getLength(); i++) {
            Node n1 = atts1.item(i);

            for (int j = 0; j < atts2.getLength(); j++) {
                Node n2 = atts2.item(j);

                if (n2.getNodeName().equals(n1.getNodeName())) {
                    if (!n1.getNodeValue().equals(n2.getNodeValue())) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Based on the type of the attribute(String,float,int), compare the two given values
     */
    private static int compareAttributeValues(SortedAttribute sAtt,
                                              String val1, String val2) {
        int returnVal = -1;
        if (sAtt.getAttType().equals("String")) {
            returnVal = val1.compareTo(val2);
        } else if (sAtt.getAttType().equals("float")
                || sAtt.getAttType().equals("int")) {
            float f1 = Float.parseFloat(val1);
            float f2 = Float.parseFloat(val2);

            if (f1 > f2) {
                returnVal = 1;
            } else if (f1 < f2) {
                returnVal = -1;
            } else if (f1 == f2) {
                returnVal = 0;
            }
        } else {
            System.err.println("Incompatible attribute type: "
                    + sAtt.getAttType());
        }

        return returnVal;
    }

    /**
     * Orders the children of the given node by their attributes (more than one sorting is allowed) The attribute names
     * and types (String,float,int) are specified in the SortedAttribute array The sorting can be ascending or
     * descending.
     */
    /*
    public static Node orderByAttributes(Node grid, final SortedAttribute[] attr, final boolean ascending)
            throws ParserConfigurationException, Exception
    {

        // inner class required to compare the attributes of two nodes
        // this is used inside the sort
        final Comparator compareAtts = new Comparator()
        {
            public int compare(Object o1, Object o2)
            {
                Node n1 = (Node) o1;
                Node n2 = (Node) o2;
                String n1Val = getAttributeValue(n1,
                        attr[0].getAttName());
                String n2Val = getAttributeValue(n2,
                        attr[0].getAttName());
                int returnVal = compareAttributeValues(attr[0], n1Val,
                        n2Val);
                if (returnVal == 0)
                {
                    // sort by other attributes
                    for (int i = 1; i < attr.length; i++)
                    {
                        // getAndRemove the attribute values of the given attribute
                        n1Val = getAttributeValue(n1,
                                attr[i].getAttName());
                        n2Val = getAttributeValue(n2,
                                attr[i].getAttName());
                        returnVal = compareAttributeValues(attr[i], n1Val,
                                n2Val);
                        if (returnVal != 0)
                        {
                            break;
                        }
                    }
                }
                if (!ascending)
                {
                    returnVal = -returnVal;
                }

                return returnVal;
            }
        };

        if (grid.getOwnerDocument() == null)
        {
            grid = getFirstChildElementNode(grid);
        }

        Link children = getImmediateChildren(grid);

        // Sort the grid
        Collections.sort(children, compareAtts);
        Document sortedTree = XML.newDocument();
        // getAndRemove a shallow copy of the given grid and add it to the new
        // (sortedTree) grid
        Node rootNode = grid.cloneNode(false);

        rootNode = sortedTree.appendChild(sortedTree.importNode(rootNode,
                true));

        for (int i = 0; i < children.size(); i++)
        {
            Node child = (Node) children.get(i);

            rootNode.appendChild(sortedTree.importNode(child, true));
        }

        return sortedTree;
    }
    */

    /**
     * Gets the first sibling of a node...
     *
     * @return node if node is first
     */
    public static final Node getFirstSibling(Node node) {

        Node answer = node.getPreviousSibling();

        if (answer == null) {
            return node;
        } else {
            return getFirstSibling(answer);
        }
    }

    /**
     * Adds those elements in src which are not found in target into target. The method only handles elements and does
     * not yet support attributes. The method does not handle repeating groups.
     *
     * @param src the node which will be appended
     * @param src the node which will be appended to
     */
    /*
    public static void combine(Element src, Element target)
    {
        Link children = getImmediateChildren(src);

        for (int i = 0; i < children.size(); i++)
        {
            Element child = (Element) children.get(i);
            Element targetChild = getFirstChildElement(target,
                    child.getNodeName());
            if (targetChild != null)
            {
                combine(child, targetChild);
            }
            else
            {
                targetChild =
                        (Element) target.getOwnerDocument().importNode(child,
                                true);

                target.appendChild(targetChild);
            }
        }
    }
    */

    /**
     */
    public static Document cloneBranchToNewDocument(Element el) {
        Document res = null;
        String branchAsText = XML.toString(el);
        try {
            res = XML.stringToDocument(branchAsText);
        } catch (Exception exc) {
            // this should never happen.  We are getting our string
            // from an existing DOM, therefore no DOM exceptions
            // should occur
        }
        return res;
    }

    /*
    public static Document mergeDOMs(Document grid, Document child)
    {
        try
        {
            if (grid == null)
                return null;
            if (child == null)
                return grid;
            Element firstRoot = grid.getDocumentElement();
            Element secondRoot = child.getDocumentElement();
            Node newOneToMove = grid.importNode(secondRoot, true);
            firstRoot.appendChild(newOneToMove);
            return grid;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    */

    /*
    public static Document addSubtree(Document fatherTree, Node nodeFather, Document child)
    {
        try
        {
            if (fatherTree == null) return null;
            if (nodeFather == null) return null;
            if (child == null) return fatherTree;
            //  Element firstRoot = (Element) nodeFather;
            Element childroot = child.getDocumentElement();
            Node newOneToMove = fatherTree.importNode(childroot, true);
            nodeFather.appendChild(newOneToMove);
            return fatherTree;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    */


    public static void ricorsiva(Node n, HashMap map) {
        if (n == null) {
            return;
        }
        if (XML.getFirstChildElementNode(n) == null) {
            map.put(XML.getNodeName(n).trim(), XML.getData(n).trim());
        } else {
            List children = XML.getImmediateChildren(n);
            for (int i = 0; i < children.size(); i++) {
                ricorsiva((Node) children.get(i), map);
            }
        }
    }


}
