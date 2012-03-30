package com.projectsexception.mz.htmlapi.model;

import java.io.Serializable;

public class LeagueTeam implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2530582544944120668L;
    
    private int teamId;
    private String teamName;
    private String nameShort;
    private int position;
    private int played;
    private int won;
    private int drawn;
    private int lost;
    private int goalsPlus;    
    private int goalsMinus;
    private int goalsDifference;
    private int points;

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getNameShort() {
        return nameShort;
    }

    public void setNameShort(String nameShort) {
        this.nameShort = nameShort;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getDrawn() {
        return drawn;
    }

    public void setDrawn(int drawn) {
        this.drawn = drawn;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getGoalsPlus() {
        return goalsPlus;
    }

    public void setGoalsPlus(int goalsPlus) {
        this.goalsPlus = goalsPlus;
    }

    public int getGoalsMinus() {
        return goalsMinus;
    }

    public void setGoalsMinus(int goalsMinus) {
        this.goalsMinus = goalsMinus;
    }
    
    public int getGoalsDifference() {
        return goalsDifference;
    }

    public void setGoalsDifference(int goalsDifference) {
        this.goalsDifference = goalsDifference;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}
