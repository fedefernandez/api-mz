package com.projectsexception.mz.htmlapi;

import java.util.List;

import org.xml.sax.SAXException;

import com.projectsexception.mz.htmlapi.handler.HandlerUtil;
import com.projectsexception.mz.htmlapi.handler.LeagueHandler;
import com.projectsexception.mz.htmlapi.handler.MatchesHandler;
import com.projectsexception.mz.htmlapi.handler.PlayersHandler;
import com.projectsexception.mz.htmlapi.handler.TeamHandler;
import com.projectsexception.mz.htmlapi.handler.TrainingHandler;
import com.projectsexception.mz.htmlapi.handler.UserDataHandler;
import com.projectsexception.mz.htmlapi.handler.UserHandler;
import com.projectsexception.mz.htmlapi.model.LeagueTeam;
import com.projectsexception.mz.htmlapi.model.Match;
import com.projectsexception.mz.htmlapi.model.Player;
import com.projectsexception.mz.htmlapi.model.Training;
import com.projectsexception.mz.htmlapi.model.UserData;

public class HTMLParser {

    private static final String HOST = "http://www.managerzone.com/";
    private static final String HOST_XML = HOST + "xml/";
    private static final String OP_USER_DATA = HOST_XML + "manager_data.php?sport_id=1&username=";
    private static final String OP_PLAYERS = HOST_XML + "team_playerlist.php?sport_id=1&team_id=";
    private static final String OP_LEAGUE = HOST_XML + "team_league.php?sport_id=1&league_id=";
    private static final String OP_MATCHES = HOST_XML + "team_matchlist.php?sport_id=1&team_id={}&match_status={}&limit={}";
    
    public static List<Player> readPlayers(String html) {
        List<Player> players;
        PlayersHandler playersHandler = new PlayersHandler();
        try {
            HandlerUtil.launchHandler(html, playersHandler);
            players = playersHandler.getPlayers();
        } catch (SAXException e) {
            players = null;
        }
        return players;
    }
    
    public static List<Training> readTraining(String html) {
        List<Training> trainings;
        TrainingHandler trainingHandler = new TrainingHandler();
        try {
            HandlerUtil.launchHandler(html, trainingHandler);
            trainings = trainingHandler.getTrainings();
        } catch (SAXException e) {
            trainings = null;
        }
        return trainings;
    }
    
    public static String readUser(String html) {
        String user;
        UserHandler handler = new UserHandler();
        try {
            HandlerUtil.launchHandler(html, handler);
            user = handler.getUsername();
        } catch (SAXException e) {
            user = null;
        }
        return user;
    }
    
    public static UserData readUserData(String username) {
        UserData userData = null;
        String url = OP_USER_DATA + username;
        final UserDataHandler handler = new UserDataHandler();
        final TeamHandler teamHandler = new TeamHandler();
        try {
            HandlerUtil.launchXMLHandler(url, handler);
            userData = handler.getUserData();
            if (userData != null && userData.getTeam() != null) {
                int teamId = userData.getTeam().getTeamId();
                url = OP_PLAYERS + teamId;
                HandlerUtil.launchXMLHandler(url, teamHandler);
                userData.getTeam().setCountryShortname(teamHandler.getCountry());                
                userData.getTeam().setCurrency(teamHandler.getCurrency());                
            }
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return userData;
    }
    
    public static List<LeagueTeam> league(int leagueId) {
        final String url = OP_LEAGUE + leagueId;
        final LeagueHandler handler = new LeagueHandler();
        try {
            HandlerUtil.launchXMLHandler(url, handler);
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return handler.getLeagueTeams();
    }
    
    public static List<Match> matches(int teamId, boolean played, int limit, int... types) {
        int matchStatus = 2;
        if (played) {
            matchStatus = 1;
        }
        final String url = replaceVars(OP_MATCHES, teamId, matchStatus, limit);
        final MatchesHandler handler = new MatchesHandler();
        handler.setMatchTypes(types);
        try {
            HandlerUtil.launchXMLHandler(url, handler);
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return handler.getMatches();
    }
    
    private static String replaceVars(String string, Object... values) {
        final StringBuilder output = new StringBuilder();
        replaceVars(output, string, 0, values);
        return output.toString();
    }
    
    private static void replaceVars(StringBuilder output, String string, int var, Object... values) {
        final int pos = string.indexOf("{}");
        if (pos != -1) {
            if (pos > 0) {
                output.append(string.substring(0, pos));
            }
            output.append(values[var]);
            replaceVars(output, string.substring(pos + 2), var + 1, values);
        } else {
            output.append(string);
        }
    }

}
