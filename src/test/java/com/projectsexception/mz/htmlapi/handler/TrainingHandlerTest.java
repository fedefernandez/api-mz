package com.projectsexception.mz.htmlapi.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.projectsexception.mz.htmlapi.model.SKILL_TYPE;
import com.projectsexception.mz.htmlapi.model.Training;

public class TrainingHandlerTest {

    @Test
    public void testEmpty() throws IOException, SAXException {
        TrainingHandler handler = new TrainingHandler();
        String html = convertStreamToString(TrainingHandlerTest.class.getResourceAsStream("/training_empty.html"));
        HandlerUtil.launchHandler(html, handler);
        assertTrue(handler.getTrainings().isEmpty());
    }

    @Test
    public void test() throws IOException, SAXException {
        TrainingHandler handler = new TrainingHandler();
        String html = convertStreamToString(TrainingHandlerTest.class.getResourceAsStream("/training_01.html"));
        HandlerUtil.launchHandler(html, handler);
        assertEquals(24, handler.getTrainings().size());
        for (int i = 0 ; i < handler.getTrainings().size() ; i++) {
            Training t = handler.getTrainings().get(i);
            assertFalse(t.isBall());
            assertFalse(t.isTc());
            if (i < 19) {
                assertNotNull(t.getSkill());
            } else {
                assertNull(t.getSkill());
            }
        }
    }

    @Test
    public void testTc() throws IOException, SAXException {
        TrainingHandler handler = new TrainingHandler();
        String html = convertStreamToString(TrainingHandlerTest.class.getResourceAsStream("/training_src_wed.html"));
        HandlerUtil.launchHandler(html, handler);
        assertEquals(3, handler.getTrainings().size());
        SKILL_TYPE[] skillTypes = new SKILL_TYPE[] {
                SKILL_TYPE.SHOOTING, SKILL_TYPE.TACKLING, SKILL_TYPE.SHOOTING
        };
        int pos = 0;
        for (Training t : handler.getTrainings()) {
            assertTrue(t.isTc());
            assertFalse(t.isBall());
            assertEquals(skillTypes[pos++], t.getSkill());
        }
    }

    @Test
    public void testNeg() throws IOException, SAXException {
        TrainingHandler handler = new TrainingHandler();
        String html = convertStreamToString(TrainingHandlerTest.class.getResourceAsStream("/negativo.htm"));
        HandlerUtil.launchHandler(html, handler);
        assertEquals(22, handler.getTrainings().size());
        SKILL_TYPE[] skillTypes = new SKILL_TYPE[] {
                SKILL_TYPE.BALL_CONTROL, SKILL_TYPE.SPEED, SKILL_TYPE.SPEED,
                SKILL_TYPE.TACKLING, SKILL_TYPE.TACKLING, SKILL_TYPE.TACKLING,
                SKILL_TYPE.STAMINA, SKILL_TYPE.SPEED, SKILL_TYPE.AERIAL_PASSING, 
                SKILL_TYPE.SPEED, SKILL_TYPE.BALL_CONTROL, SKILL_TYPE.SPEED,
                SKILL_TYPE.PLAY_INTELLIGENCE, SKILL_TYPE.BALL_CONTROL, SKILL_TYPE.AERIAL_PASSING,
                SKILL_TYPE.STAMINA, SKILL_TYPE.BALL_CONTROL, SKILL_TYPE.SPEED,
                SKILL_TYPE.SHOOTING, SKILL_TYPE.AERIAL_PASSING, SKILL_TYPE.STAMINA, 
                null
        };
        int[] levels = new int[] {
                5, 5, 5, 5, 4, 4, 3, 3, 3, 5, 3, 3, 2, 2, 2, 2, 2, 2, 2, 1, -5, 0
        };
        for (int i = 0 ; i < handler.getTrainings().size() ; i++) {
            Training t = handler.getTrainings().get(i);
            if (i == 9) {
                assertTrue(t.isBall());
            } else {
                assertFalse(t.isBall());
            }
            if (i > 0) {
                assertFalse(t.isTc());
            } else {
                assertTrue(t.isTc());
            }
            assertEquals(skillTypes[i], t.getSkill());
            assertEquals(levels[i], t.getLevel());
        }
    }

    @Test
    public void testLostBall() throws IOException, SAXException {
        TrainingHandler handler = new TrainingHandler();
        String html = convertStreamToString(TrainingHandlerTest.class.getResourceAsStream("/balonperdido.htm"));
        HandlerUtil.launchHandler(html, handler);
        assertEquals(22, handler.getTrainings().size());
        SKILL_TYPE[] skillTypes = new SKILL_TYPE[] {
                SKILL_TYPE.PLAY_INTELLIGENCE, SKILL_TYPE.TACKLING, SKILL_TYPE.SPEED,
                SKILL_TYPE.TACKLING, SKILL_TYPE.SPEED, SKILL_TYPE.TACKLING,
                SKILL_TYPE.SPEED, SKILL_TYPE.SPEED, SKILL_TYPE.BALL_CONTROL, 
                SKILL_TYPE.SPEED, SKILL_TYPE.AERIAL_PASSING, SKILL_TYPE.BALL_CONTROL,
                SKILL_TYPE.STAMINA, SKILL_TYPE.BALL_CONTROL, SKILL_TYPE.SHOOTING,
                SKILL_TYPE.AERIAL_PASSING, SKILL_TYPE.STAMINA, SKILL_TYPE.SPEED,
                SKILL_TYPE.PLAY_INTELLIGENCE, SKILL_TYPE.AERIAL_PASSING, SKILL_TYPE.PLAY_INTELLIGENCE, 
                null
        };
        int[] levels = new int[] {
                5, 5, 5, 5, 5, 4, 4, 4, 4, 4, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2, -5, 0
        };
        for (int i = 0 ; i < handler.getTrainings().size() ; i++) {
            Training t = handler.getTrainings().get(i);
            if (i != 20) {
                assertFalse(t.isBall());
            } else {
                assertTrue(t.isBall());
            }
            if (i > 0) {
                assertFalse(t.isTc());
            } else {
                assertTrue(t.isTc());
            }
            
            assertEquals(skillTypes[i], t.getSkill());
            assertEquals(levels[i], t.getLevel());
        }
    }
    
    public String convertStreamToString(InputStream is)
            throws IOException {
        /*
         * To convert the InputStream to String we use the
         * Reader.read(char[] buffer) method. We iterate until the
         * Reader return -1 which means there's no more data to
         * read. We use the StringWriter class to produce the string.
         */
        if (is != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(
                        new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {        
            return "";
        }
    }

}
