package com.projectsexception.mz.htmlapi.model;

public enum SKILL_TYPE {
    
    SPEED("Speed"),
    STAMINA("Stamina"),
    PLAY_INTELLIGENCE("Play Intelligence"),
    PASSING("Passing"),
    SHOOTING("Shooting"),
    HEADING("Heading"),
    KEEPING("Keeping"),
    BALL_CONTROL("Ball Control"),
    TACKLING("Tackling"),
    AERIAL_PASSING("Aerial Passing"),
    SET_PLAYS("Set plays"),
    EXPERIENCE("Experience"),
    FORM("Form");
    
    private String name;
    
    private SKILL_TYPE(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

}
