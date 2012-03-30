package com.projectsexception.mz.htmlapi.model;

import java.io.Serializable;

public class MatchType implements Serializable {
    
    private static final long serialVersionUID = -2553790402355193424L;
    
    public static final int UNDEFINED = 1;
    public static final int QUALIFICATION = 2;
    public static final int PRIVATE_CUP = 3;
    public static final int CUP = 4;
    public static final int SPECIAL = 5;
    public static final int FRIENDLY = 6;
    public static final int LEAGUE = 7;
    public static final int FRIENDLY_SERIES = 8;
    
    private int type;
    private String typeName;    
    private int typeId;

    public MatchType() {
    }

    public MatchType(int type, String typeName, int typeId) {
        this.type = type;
        this.typeName = typeName;
        this.typeId = typeId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
    
    public static int parseMatchType(String type) {
        if (type == null || type.length() == 0) {
            return UNDEFINED;
        }
        if (type.equals("cup_group") || type.equals("cup_playoff")) {
            return CUP;
        }
        if (type.equals("private_cup_group") || type.equals("private_cup_playoff")) {
            return PRIVATE_CUP;
        }
        if (type.equals("special")) {
            return SPECIAL;
        }
        if (type.equals("friendly")) {
            return FRIENDLY;
        }
        if (type.equals("league")) {
            return LEAGUE;
        }
        if (type.equals("friendly_series")) {
            return FRIENDLY_SERIES;
        }
        if (type.equals("qualification")) {
            return QUALIFICATION;
        }
        return UNDEFINED;
    }

}
