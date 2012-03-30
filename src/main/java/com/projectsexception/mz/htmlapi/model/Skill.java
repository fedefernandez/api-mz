package com.projectsexception.mz.htmlapi.model;

public class Skill {
    
    private long id;
    private SKILL_TYPE type;
    private int value;
    private boolean maxed;

    public Skill() {
        super();
    }

    public Skill(SKILL_TYPE type, int value, boolean maxed) {
        super();
        this.type = type;
        this.value = value;
        this.maxed = maxed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SKILL_TYPE getType() {
        return type;
    }

    public void setType(SKILL_TYPE type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isMaxed() {
        return maxed;
    }

    public void setMaxed(boolean maxed) {
        this.maxed = maxed;
    }

}
