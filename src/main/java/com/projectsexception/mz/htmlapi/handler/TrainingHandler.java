package com.projectsexception.mz.htmlapi.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.projectsexception.mz.htmlapi.model.SKILL_TYPE;
import com.projectsexception.mz.htmlapi.model.Training;

public class TrainingHandler extends DefaultHandler {
    
    private static final int SEARCHING_FORM = 1;
    private static final int SEARCHING_TABLE = 2;
    private static final int READING_TABLE = 3;
    private static final int READING_NUMBER = 4;
    private static final int READING_NAME = 5;
    private static final int READING_SKILL = 6;
    private static final int READING_TC = 7;
    private static final int READING_LEVEL = 8;

    private transient int state;
    private transient boolean data;
    private transient StringBuilder buffer;
    private transient boolean noTraining;
    
    private List<Training> trainings;
    private Training training;

    @Override
    public void startDocument() throws SAXException {
        state = SEARCHING_FORM;
        trainings = new ArrayList<Training>();
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (state) {
        case SEARCHING_FORM:
            if ("form".equals(HandlerUtil.getName(localName, qName)) 
                    && "sortbyform".equals(attributes.getValue("name"))) {
                state = SEARCHING_TABLE;                
            }
            break;
        case READING_TABLE:
            if ("td".equals(HandlerUtil.getName(localName, qName)) 
                    && !"5".equals(attributes.getValue("colspan"))) {
                state = READING_NUMBER;
                noTraining = false;
            }
            break;
        case READING_NUMBER:
            if ("td".equals(HandlerUtil.getName(localName, qName))) {
                if ("3".equals(attributes.getValue("colspan"))) {
                    noTraining = true;
                }
                state = READING_NAME;
            }
            break;
        case READING_NAME:
            if ("a".equals(HandlerUtil.getName(localName, qName))) {
                String id = readParameter(attributes.getValue("href"), "pid");
                if (id == null) {
                    state = READING_TABLE;
                } else {
                    training = new Training();
                    try {
                        training.setPlayerId(Integer.parseInt(id));
                        trainings.add(training);
                        if (noTraining) {
                            state = READING_TABLE;
                        }
                    } catch (NumberFormatException e) {
                        state = READING_TABLE;
                    }
                }
            }
            break;
        case READING_SKILL:
            if ("td".equals(HandlerUtil.getName(localName, qName))) {
                data = true;
                buffer = new StringBuilder();
            }
            break;
        case READING_TC:
            if ("img".equals(HandlerUtil.getName(localName, qName))) {
                training.setTc(true);
            }
            break;
        case READING_LEVEL:
            if ("img".equals(HandlerUtil.getName(localName, qName))) {
                calculateLevel(attributes.getValue("src"));
            }
            break;
        }
    }
    
    private void calculateLevel(String value) {
        Pattern pattern = Pattern.compile("bar_pos_(ball_)?(\\d)");
        Matcher matcher = pattern.matcher(value);
        if (matcher.find()) {
            if (matcher.group(1) != null) {
                training.setBall(true);
            }
            training.setLevel(Integer.parseInt(matcher.group(2)));
        } else {
            pattern = Pattern.compile("bar_neg_(ball_)?(\\d)");
            matcher = pattern.matcher(value);
            if (matcher.find()) {
                if (matcher.group(1) != null) {
                    training.setBall(true);
                }
                training.setLevel(-Integer.parseInt(matcher.group(2)));
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (data) {
            buffer.append(new String(ch, start, length));
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (state == SEARCHING_TABLE) {
            if ("form".equals(HandlerUtil.getName(localName, qName))) {
                state = READING_TABLE;                
            }
        } else if (state > READING_TABLE) {
            if ("table".equals(HandlerUtil.getName(localName, qName))) {
                throw new EndParsingSAXException();
            } else if ("td".equals(HandlerUtil.getName(localName, qName))) {
                if (state == READING_SKILL) {
                    SKILL_TYPE skillType = parseSkillType(buffer.toString().trim());
                    if (skillType == null) {
                        state = READING_TABLE;
                        trainings.remove(trainings.size() - 1);
                    } else {
                        training.setSkill(skillType);
                    }
                }
                if (state > READING_NUMBER) { 
                    state++;
                }
            } else if ("tr".equals(HandlerUtil.getName(localName, qName))) {
                state = READING_TABLE;
            }
        }
    }
    
    public List<Training> getTrainings() {
        return trainings;
    }

    private String readParameter(String url, String parameter) {
        int i = url.indexOf("?");
        if (i > -1) {
            String searchURL = url.substring(i + 1);
            StringTokenizer st = new StringTokenizer(searchURL, "&");
            String[] param;
            while (st.hasMoreTokens()) {
                param = st.nextToken().split("=");
                if (param.length == 2 && param[0].equals(parameter)) {
                    return param[1];
                }
            }
        }
        return null;
    }
    
    private SKILL_TYPE parseSkillType(String skill) {
        if (skill == null || skill.trim().length() == 0) {
            return null;
        }
        SKILL_TYPE[] values = SKILL_TYPE.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].getName().equals(skill)) {
                return values[i];
            }
        }
        return null;
    }

}
