package com.projectsexception.mz.htmlapi.handler;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.projectsexception.mz.htmlapi.model.LeagueTeam;

public class LeagueHandler extends DefaultHandler {
    
    private static final String ELEM_ERROR = "ManagerZone_Error";
    
    private static final String ELEM_TEAM = "Team";
    private static final String ATT_POS = "pos";
    private static final String ATT_POSITION = "position";
    private static final String ATT_TEAM_ID = "teamId";
    private static final String ATT_TEAM_NAME = "teamName";
    private static final String ATT_TEAM_SHORT_NAME = "teamShortname";
    private static final String ATT_MATCHES_PLAYED = "played";
    private static final String ATT_MATCHES_WON = "won";
    private static final String ATT_MATCHES_LOST = "lost";
    private static final String ATT_MATCHES_DRAWN = "drawn";
    private static final String ATT_GOALS_PLUS = "goalsPlus";
    private static final String ATT_GOALS_MINUS = "goalsMinus";
    private static final String ATT_GOALS_DIFFERENCE = "goalsDifference";
    private static final String ATT_POINTS = "points";
    
    private List<LeagueTeam> league;
    
    @Override
    public void startDocument() throws SAXException {
        league = new ArrayList<LeagueTeam>();
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        
        if (ELEM_ERROR.equalsIgnoreCase(HandlerUtil.getName(localName, qName))) {
            throw new SAXException();
        }
        
        if (ELEM_TEAM.equalsIgnoreCase(HandlerUtil.getName(localName, qName))) {
            LeagueTeam team = new LeagueTeam();
            team.setTeamId(parseInteger(HandlerUtil.getAttribute(attributes, ATT_TEAM_ID)));
            team.setTeamName(HandlerUtil.getAttribute(attributes, ATT_TEAM_NAME));
            team.setNameShort(HandlerUtil.getAttribute(attributes, ATT_TEAM_SHORT_NAME));
            if (HandlerUtil.getAttribute(attributes, ATT_POS) != null) {
                team.setPosition(parseInteger(HandlerUtil.getAttribute(attributes, ATT_POS)));
            } else if (HandlerUtil.getAttribute(attributes, ATT_POSITION) != null) {
                team.setPosition(parseInteger(HandlerUtil.getAttribute(attributes, ATT_POSITION)));
            }
            team.setPlayed(parseInteger(HandlerUtil.getAttribute(attributes, ATT_MATCHES_PLAYED)));
            team.setWon(parseInteger(HandlerUtil.getAttribute(attributes, ATT_MATCHES_WON)));
            team.setDrawn(parseInteger(HandlerUtil.getAttribute(attributes, ATT_MATCHES_DRAWN)));
            team.setLost(parseInteger(HandlerUtil.getAttribute(attributes, ATT_MATCHES_LOST)));
            team.setGoalsPlus(parseInteger(HandlerUtil.getAttribute(attributes, ATT_GOALS_PLUS)));
            team.setGoalsMinus(parseInteger(HandlerUtil.getAttribute(attributes, ATT_GOALS_MINUS)));
            team.setGoalsDifference(parseInteger(HandlerUtil.getAttribute(attributes, ATT_GOALS_DIFFERENCE)));
            team.setPoints(parseInteger(HandlerUtil.getAttribute(attributes, ATT_POINTS)));
            league.add(team);
        }
    }

    public List<LeagueTeam> getLeagueTeams() {
        return league;
    }
    
    protected int parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception ex) {
            return 0;
        }
    }

}
