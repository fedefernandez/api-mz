package com.projectsexception.mz.htmlapi.handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.projectsexception.mz.htmlapi.model.Match;
import com.projectsexception.mz.htmlapi.model.MatchType;
import com.projectsexception.mz.htmlapi.model.Team;

public class MatchesHandler extends DefaultHandler {
    
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    private static final String ELEM_MATCH = "Match";
    private static final String ELEM_TEAM = "Team";
    private static final String ATT_MATCH_ID = "id";
    private static final String ATT_MATCH_DATE = "date";
    private static final String ATT_MATCH_STATUS = "status";
    private static final String MATCH_STATUS_PLAYED = "played";
    private static final String ATT_MATCH_TYPE = "type";
    private static final String ATT_MATCH_TYPE_NAME = "typeName";
    private static final String ATT_MATCH_TYPE_ID = "typeId";
    private static final String ATT_TEAM_ID = "teamId";
    private static final String ATT_TEAM_NAME = "teamName";
    private static final String ATT_TEAM_COUNTRY = "countryShortname";
    private static final String ATT_TEAM_FIELD = "field";
    private static final String ATT_MATCH_GOALS = "goals";
    private static final String TEAM_FIELD_HOME = "home";
    
    private Map<String, Team> teams;
    private List<Match> matches;
    private Match match;
    
    private int[] types;
    private boolean validMatch;
    
    public void setMatchTypes(int... types) {
        this.types = types;
    }
    
    @Override
    public void startDocument() throws SAXException {
        teams = new HashMap<String, Team>();
        matches = new ArrayList<Match>();
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (ELEM_MATCH.equalsIgnoreCase(HandlerUtil.getName(localName, qName))) {
            int matchType = MatchType.parseMatchType(HandlerUtil.getAttribute(attributes, ATT_MATCH_TYPE));
            if (types == null || Arrays.binarySearch(types, matchType) >= 0) {
                validMatch = true;
                match = new Match();
                match.setId(parseInteger(HandlerUtil.getAttribute(attributes, ATT_MATCH_ID)));
                match.setDate(parseDate(HandlerUtil.getAttribute(attributes, ATT_MATCH_DATE)));
                match.setPlayed(MATCH_STATUS_PLAYED.equals(HandlerUtil.getAttribute(attributes, ATT_MATCH_STATUS)));
                match.setType(new MatchType(matchType, 
                        HandlerUtil.getAttribute(attributes, ATT_MATCH_TYPE_NAME), 
                        parseInteger(HandlerUtil.getAttribute(attributes, ATT_MATCH_TYPE_ID))));
            } else {
                validMatch = false;
            }
        } else if (validMatch && ELEM_TEAM.equalsIgnoreCase(HandlerUtil.getName(localName, qName))) {
            String teamId = HandlerUtil.getAttribute(attributes, ATT_TEAM_ID);
            Team team;
            if (teams.containsKey(teamId)) {
                team = teams.get(teamId);
            } else {
                team = new Team();
                team.setTeamId(parseInteger(teamId));
                team.setTeamName(HandlerUtil.getAttribute(attributes, ATT_TEAM_NAME));
                team.setCountryShortname(HandlerUtil.getAttribute(attributes, ATT_TEAM_COUNTRY));
                teams.put(teamId, team);
            }
            if (TEAM_FIELD_HOME.equals(HandlerUtil.getAttribute(attributes, ATT_TEAM_FIELD))) {
                match.setHomeTeam(team);
                match.setHomeGoals(Integer.parseInt(HandlerUtil.getAttribute(attributes, ATT_MATCH_GOALS)));
            } else {
                match.setAwayTeam(team);
                match.setAwayGoals(Integer.parseInt(HandlerUtil.getAttribute(attributes, ATT_MATCH_GOALS)));
            }
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (validMatch && ELEM_MATCH.equalsIgnoreCase(HandlerUtil.getName(localName, qName))) {
            matches.add(match);
        }
    }
    
    public List<Match> getMatches() {
        return matches;
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
