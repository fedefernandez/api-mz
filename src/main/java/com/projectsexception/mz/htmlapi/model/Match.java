package com.projectsexception.mz.htmlapi.model;

import java.io.Serializable;
import java.util.Date;

public class Match implements Serializable {
    
    private static final long serialVersionUID = -2783157379760107515L;
    
    private int id;
    private Date date;
    private boolean played;
    private MatchType type;
    private Team homeTeam;
    private int homeGoals;
    private Team awayTeam;    
    private int awayGoals;
    private int spectators;    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    public MatchType getType() {
        return type;
    }

    public void setType(MatchType type) {
        this.type = type;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public int getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }

    public void setSpectators(int spectators) {
        this.spectators = spectators;
    }

    public int getSpectators() {
        return spectators;
    }

}
