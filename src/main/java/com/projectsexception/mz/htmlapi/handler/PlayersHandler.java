package com.projectsexception.mz.htmlapi.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.projectsexception.mz.htmlapi.model.Player;
import com.projectsexception.mz.htmlapi.model.SKILL_TYPE;
import com.projectsexception.mz.htmlapi.model.Skill;

public class PlayersHandler extends DefaultHandler {
    
    private static final int SEARCHING_TABLE = 1;
    private static final int READING_PLAYERS = 2;
    
    private transient int state;
    private transient List<String> playerValues;
    private transient boolean data;
    private transient StringBuilder buffer;
    private transient String id;
    
    private List<Player> players;
    
    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public void startDocument() throws SAXException {
        state = SEARCHING_TABLE;
        playerValues = new ArrayList<String>();
        players = new ArrayList<Player>();
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (state) {
        case SEARCHING_TABLE:
            if ("table".equals(HandlerUtil.getName(localName, qName)) 
                    && "playerAltViewTable".equals(attributes.getValue("id"))) {
                state = READING_PLAYERS;                
            }
            break;
        case READING_PLAYERS:
            if ("td".equals(HandlerUtil.getName(localName, qName))) {
                data = true;
                buffer = new StringBuilder();
            } else if ("a".equals(HandlerUtil.getName(localName, qName))) {
                id = readParameter(attributes.getValue("href"), "pid");
            }
            break;
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
        if (state == READING_PLAYERS) {
            if ("table".equals(HandlerUtil.getName(localName, qName))) {
                throw new EndParsingSAXException();
            } else if ("tr".equals(HandlerUtil.getName(localName, qName))) {
                addNewPlayer();
            } else if ("td".equals(HandlerUtil.getName(localName, qName))) {
                playerValues.add(buffer.toString());
                data = false;
            }
        }
    }

    private void addNewPlayer() {
        SKILL_TYPE[] types = SKILL_TYPE.values();
        if (playerValues.size() == (8 + types.length)) {
            Player player = new Player();
            player.setId(Integer.parseInt(id));
            player.setNumber(Integer.parseInt(playerValues.get(0)));
            player.setName(playerValues.get(1));
            player.setValue(parseValue(playerValues.get(2)));
            player.setSalary(parseValue(playerValues.get(3)));
            player.setAge(Integer.parseInt(playerValues.get(4)));
            player.setBorn(Integer.parseInt(playerValues.get(5)));
            player.setHeight(Integer.parseInt(playerValues.get(6)));
            player.setWeight(Integer.parseInt(playerValues.get(7)));
            List<Skill> skills = new ArrayList<Skill>();
            for (int i = 0; i < types.length; i++) {
                skills.add(new Skill(types[i], Integer.parseInt(playerValues.get(8 + i)), false));
            }
            player.setSkills(skills);
            players.add(player);
        }
        playerValues.clear();
    }

    private int parseValue(String value) {
        int pos = value.lastIndexOf(' ');
        if (pos > 0) {
            String cleanValue = value.substring(0, pos).replaceAll(" ", "");
            return Integer.parseInt(cleanValue);
        }
        return 0;
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

}
