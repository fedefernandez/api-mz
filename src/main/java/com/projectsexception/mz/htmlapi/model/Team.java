package com.projectsexception.mz.htmlapi.model;

import java.io.Serializable;
import java.util.Date;

public class Team implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 2332766798076349118L;
    
    private int teamId;
    private String sport;
    private String teamName;
    private String nameShort;
    private String countryShortname;
    private String currency;
    private int seriesId;
    private String seriesName;
    private Date startDate;
    private String sponsor;
    private int rankPos;    
    private int rankPoints;

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
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

    public String getCountryShortname() {
        return countryShortname;
    }

    public void setCountryShortname(String countryShortname) {
        this.countryShortname = countryShortname;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public int getRankPos() {
        return rankPos;
    }

    public void setRankPos(int rankPos) {
        this.rankPos = rankPos;
    }

    public int getRankPoints() {
        return rankPoints;
    }

    public void setRankPoints(int rankPoints) {
        this.rankPoints = rankPoints;
    }

}
