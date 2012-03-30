package com.projectsexception.mz.htmlapi.handler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class UserHandler extends DefaultHandler {
    
    private static final int SEARCHING_FORM = 1;
    private static final int SEARCHING_SPAN = 2;
    private static final int READING_USER = 3;

    private transient int state;
    private transient boolean data;
    private transient StringBuilder buffer;
    
    private String username;

    @Override
    public void startDocument() throws SAXException {
        state = SEARCHING_FORM;
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (state) {
        case SEARCHING_FORM:
            if ("div".equals(HandlerUtil.getName(localName, qName)) 
                    && "logout_etc".equals(attributes.getValue("id"))) {
                state = SEARCHING_SPAN;
            }
            break;
        case SEARCHING_SPAN:
            if ("span".equals(HandlerUtil.getName(localName, qName))) {
                state = READING_USER;
                data = true;
                buffer = new StringBuilder();
            }
            break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (data) {
            buffer.append(new String(ch, start, length));
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (state == READING_USER && "span".equals(HandlerUtil.getName(localName, qName))) {
            username = buffer.toString().trim();
            throw new EndParsingSAXException();
        }
    }

    public String getUsername() {
        return username;
    }

}
