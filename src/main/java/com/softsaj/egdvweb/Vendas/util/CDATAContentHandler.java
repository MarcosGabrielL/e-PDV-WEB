package com.softsaj.gibgassecurity.gibgasVenda.util;
import com.sun.xml.txw2.output.XMLWriter;
import org.xml.sax.SAXException;

import java.io.Writer;
import java.util.regex.Pattern;

public class CDATAContentHandler extends XMLWriter {
    CDATAContentHandler(Writer writer, String encoding) {
        super(writer, encoding);
    }

    private static final Pattern XML_CHARS = Pattern.compile("[<>&]");

    public void characters(char[] ch, int start, int length) throws SAXException {
        boolean useCData = XML_CHARS.matcher(new String(ch, start, length)).find();
        if (useCData) {
            super.startCDATA();
        }
        super.characters(ch, start, length);
        if (useCData) {
            super.endCDATA();
        }
    }
}