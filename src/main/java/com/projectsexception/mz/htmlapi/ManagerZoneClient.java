package com.projectsexception.mz.htmlapi;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class ManagerZoneClient {

    private static final String USER_MAIL = "username";
    private static final String USER_PASSWORD = "password";
    
    private static final String SERVER = "http://www.managerzone.com/?lang=en&";
    private static final String LOGIN = SERVER + "p=login";
    private static final String CLUBHOUSE = SERVER + "p=clubhouse";
    private static final String PLAYERS = SERVER + "p=players&sub=alt";
    private static final String TRAINING = SERVER + "p=training_report&sub=daily&d=";
    private static final String LOGOUT = SERVER + "p=logout&manual=1";
    
    private static final int SUCCESS = 1;
    
    enum ConnectionType {
        STATUS,
        DATA;
    }
    
    private DefaultHttpClient httpclient;
    
    public ManagerZoneClient() {
        httpclient = new DefaultHttpClient();
    }
        
    private int postCredentials(String path, String username, String password) {
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair(USER_MAIL, username));
        nvps.add(new BasicNameValuePair(USER_PASSWORD, password));
        return post(path, nvps, true);
    }
    
    private int post(String ruta, List <NameValuePair> nvps, boolean status) {
        if (ruta == null) {
            ruta = LOGIN;
        }
        HttpPost httpost = new HttpPost(ruta);
        
        int result = 0;
        try {   
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
    
            HttpResponse response = httpclient.execute(httpost);
            HttpEntity entity = response.getEntity();           
            if (status) {
                result = response.getStatusLine().getStatusCode();
            } else {
                String resultS = printInputStream(entity.getContent());
                result = parserCode(resultS, SUCCESS);
            }
            entity.consumeContent();
            
        } catch (UnsupportedEncodingException e) {
            //
        } catch (ClientProtocolException e) {
            //
        } catch (IOException e) {
            //
        }       
        return result;
            
    }
    
    private int get(String path) {      
        return Integer.parseInt(get(path, ConnectionType.STATUS));
    }
    
    private String get(String path, ConnectionType connectionType) {
        String resultado = null;
        try {
            HttpGet httpget = new HttpGet(path);
    
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (connectionType == ConnectionType.STATUS) {
                resultado = Integer.toString(response.getStatusLine().getStatusCode());
            } else {
                resultado = printInputStream(entity.getContent());
            }
            entity.consumeContent();
            
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultado;
    }
    
    public boolean login(String username, String password) {     
        boolean success = false;        
        int codigo = postCredentials(LOGIN, username, password);        
        if (codigo < 400) {            
            success = true;
        }
        return success;        
    }
    
    public String getTeam() {
        return get(CLUBHOUSE, ConnectionType.DATA);
    }
    
    public String getPlayers() {
        return get(PLAYERS, ConnectionType.DATA);
    }
    
    public String getTraining(int day) {
        return get(TRAINING + day, ConnectionType.DATA);
    }
    
    public void logout() {
        get(LOGOUT);
    }
    
    public void closeConnection() {
        try {
            httpclient.getConnectionManager().shutdown();
        } catch (Exception e) {
            //
        }
    }
    
    private static int parserCode(String result, int defaultCode) {
        int code;
        try {
            code = Integer.parseInt(result);
        } catch (Exception e) {
            code = defaultCode;
        }
        return code;
    }
    
    private static String printInputStream(InputStream ists) throws IOException {          
        StringBuilder sb = new StringBuilder();
        if (ists != null) {
            String line;
            try {
                BufferedReader r1 = new BufferedReader(new InputStreamReader(ists, "UTF-8"));
                while ((line = r1.readLine()) != null) {                    
                    sb.append(line);
                }
            } finally {
                ists.close();
            }
        }
        return sb.toString();
    }

}