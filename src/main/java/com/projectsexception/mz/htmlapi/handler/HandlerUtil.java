package com.projectsexception.mz.htmlapi.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public final class HandlerUtil {
    
    private static XMLReader htmlParser;
    private static XMLReader xmlParser;
    
    private HandlerUtil() {}
    
    private static void initHTMLParser() {
        if (htmlParser == null) {
            htmlParser = new Parser();
        }
    }
    
    private static void initXMLParser() throws SAXException {
        if (xmlParser == null) {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            try {
                xmlParser = factory.newSAXParser().getXMLReader();
            } catch (ParserConfigurationException e) {
                throw new SAXException(e);
            }
        }
    }

    public static void launchHandler(String html, ContentHandler handler) throws SAXException {
        try {
            final InputSource source = new InputSource(new StringReader(html));
//            source.setEncoding("ISO-8859-1");
            initHTMLParser();
            htmlParser.setContentHandler(handler);
            htmlParser.parse(source);
        } catch (EndParsingSAXException e) {
            // Todo correcto
        } catch (IOException e) {
            throw new SAXException(e);
        } catch (Exception e) {
            throw new SAXException(e);
        } catch (Error e) {
            throw new SAXException(e.getMessage());
        }
    }
    
    public static void launchXMLHandler(String uri, DefaultHandler handler) throws SAXException {
        InputStream stream = null;
        try {
            stream = cargaInputStreamSocket(uri);
            final InputSource source = new InputSource(stream);
            source.setEncoding("UTF-8");
            initXMLParser();
            xmlParser.setContentHandler(handler);
            xmlParser.parse(source);
        } catch (EndParsingSAXException e) {
            // Todo correcto
        } catch (IOException e) {
            throw new SAXException(e);
        } catch (Exception e) {
            throw new SAXException(e);
        } catch (Error e) {
            throw new SAXException(e.getMessage());
        } finally {
            try {
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private static InputStream cargaInputStreamSocket(String url) throws IOException {
        InputStream in = null;
        int response = -1;
        URLConnection conn = null; 
        try {
            URL urlObj = new URL(url);
            conn = urlObj.openConnection();
            
            if (!(conn instanceof HttpURLConnection)) {
                throw new IOException("No HTTP connection");
            }
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        } catch (MalformedURLException ex) {
            throw new IOException("Error connecting");
        }
        return in;
    }
    
    public static String getName(String localName, String qName) {
        if (localName == null || localName.length() == 0) {
            return qName;
        }
        return localName;
    }
    
    public static String getAttribute(Attributes attributes, String name) {
        int index = attributes.getIndex(name);
        if (index < 0) {
            return attributes.getValue(name.toLowerCase());
        } else {
            return attributes.getValue(index);
        }
    }

}
