package com.projectsexception.mz.htmlapi.handler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TeamHandler extends DefaultHandler {

    private static final String ELEM_ERROR = "ManagerZone_Error";
    private static final String ELEM_TEAM_PLAYERS = "TeamPlayers";
    private static final String ATT_COUNTRY = "countryShortname";
    private static final String ATT_CURRENCY = "teamCurrency";
    
    private String country;
    private String currency;

    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        
        if (ELEM_ERROR.equalsIgnoreCase(HandlerUtil.getName(localName, qName))) {
            throw new SAXException();
        }
        
        if (HandlerUtil.getName(localName, qName).equals(ELEM_TEAM_PLAYERS)) {
            country = HandlerUtil.getAttribute(attributes, ATT_COUNTRY);
            currency = HandlerUtil.getAttribute(attributes, ATT_CURRENCY);
            throw new EndParsingSAXException();
        }
    }

    public String getCountry() {
        return country;
    }

    public String getCurrency() {
        return currency;
    }

}
