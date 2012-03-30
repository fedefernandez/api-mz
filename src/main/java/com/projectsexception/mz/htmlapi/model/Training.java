package com.projectsexception.mz.htmlapi.model;

public class Training {
    
    private int playerId;
    private String week;
    private int day;
    private SKILL_TYPE skill;
    private int level;
    private boolean tc;
    private boolean ball;

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public SKILL_TYPE getSkill() {
        return skill;
    }

    public void setSkill(SKILL_TYPE skill) {
        this.skill = skill;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isTc() {
        return tc;
    }

    public void setTc(boolean tc) {
        this.tc = tc;
    }

    public boolean isBall() {
        return ball;
    }

    public void setBall(boolean ball) {
        this.ball = ball;
    }

    @Override
    public String toString() {
        return "Training [playerId=" + playerId
                + ", skill=" + skill + ", level=" + level + ", tc=" + tc
                + ", ball=" + ball + "]";
    }

}
