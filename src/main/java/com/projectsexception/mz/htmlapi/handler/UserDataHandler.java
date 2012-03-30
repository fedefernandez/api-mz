package com.projectsexception.mz.htmlapi.handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.projectsexception.mz.htmlapi.model.Team;
import com.projectsexception.mz.htmlapi.model.UserData;

public class UserDataHandler extends DefaultHandler {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String ELEM_ERROR = "ManagerZone_Error";
    
    private static final String ELEM_USERDATA = "UserData";
    private static final String ELEM_TEAM = "Team";
    private static final String ATT_SPORT = "sport";
    private static final String ATT_SPORT_VALUE = "soccer";
    private static final String ATT_USER_ID = "userId";
    private static final String ATT_USERNAME = "username";
    private static final String ATT_COUNTRY = "countryShortname";
    private static final String ATT_USER_IMAGE = "userImage";
    private static final String ATT_TEAM_ID = "teamId";
    private static final String ATT_TEAM_NAME = "teamName";
    private static final String ATT_TEAM_NAME_SHORT = "nameShort";
    private static final String ATT_SERIES_ID = "seriesId";
    private static final String ATT_SERIES_NAME = "seriesName";
    private static final String ATT_START_DATE = "startDate";
    private static final String ATT_SPONSOR = "sponsor";
    private static final String ATT_RANK_POS = "rankPos";
    private static final String ATT_RANK_POINTS = "rankPoints";
    
    private UserData userData;

    @Override
    public void startDocument() throws SAXException {
        userData = new UserData();
    }

    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        
        if (ELEM_ERROR.equalsIgnoreCase(HandlerUtil.getName(localName, qName))) {
            throw new SAXException();
        }
        
        if (HandlerUtil.getName(localName, qName).equals(ELEM_USERDATA)) {
            userData.setUserId(parseInteger(HandlerUtil.getAttribute(attributes, ATT_USER_ID)));
            userData.setUsername(HandlerUtil.getAttribute(attributes, ATT_USERNAME));
            userData.setCountryShortname(HandlerUtil.getAttribute(attributes, ATT_COUNTRY));
            userData.setUserImage(HandlerUtil.getAttribute(attributes, ATT_USER_IMAGE));
        } else if (HandlerUtil.getName(localName, qName).equals(ELEM_TEAM)) {
            if (HandlerUtil.getAttribute(attributes, ATT_SPORT).equals(ATT_SPORT_VALUE) && 
                HandlerUtil.getAttribute(attributes, ATT_TEAM_NAME).length() > 0) {
                Team team = new Team();
                team.setTeamId(parseInteger(HandlerUtil.getAttribute(attributes, ATT_TEAM_ID)));
                team.setTeamName(HandlerUtil.getAttribute(attributes, ATT_TEAM_NAME));
                team.setNameShort(HandlerUtil.getAttribute(attributes, ATT_TEAM_NAME_SHORT));            
                team.setSeriesId(parseInteger(HandlerUtil.getAttribute(attributes, ATT_SERIES_ID)));
                team.setSeriesName(HandlerUtil.getAttribute(attributes, ATT_SERIES_NAME));            
                team.setStartDate(parseDate(HandlerUtil.getAttribute(attributes, ATT_START_DATE)));
                team.setSponsor(HandlerUtil.getAttribute(attributes, ATT_SPONSOR));
                team.setSport(HandlerUtil.getAttribute(attributes, ATT_SPORT));
                team.setRankPos(parseInteger(HandlerUtil.getAttribute(attributes, ATT_RANK_POS)));            
                team.setRankPoints(parseInteger(HandlerUtil.getAttribute(attributes, ATT_RANK_POINTS)));
                userData.setTeam(team);
            }
        }
    }
    
    public UserData getUserData() {
        return userData;
    }
    
    protected Date parseDate(String value) {
        final SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT.substring(0, value.length()));
        try {
            return formatter.parse(value);
        } catch (ParseException e) {
            return null;
        }
    }
    
    protected int parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

}
