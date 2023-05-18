/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.factory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Nix
 */
public class UrlConnectionFactory {
    
    private static final int TIMEOUT = 10000;
    private static final String REQUEST_METHOD = "GET";
    private static final String USER_AGENT = "User-Agent";
    private static final String MOZILLA = "Mozilla/5.0";

    private UrlConnectionFactory() {
    }
    
    public static HttpURLConnection getHttpUrlConnection(String path) throws MalformedURLException, IOException{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        
        conn.setReadTimeout(TIMEOUT);
        conn.setConnectTimeout(TIMEOUT);
        conn.setRequestMethod(REQUEST_METHOD);
        conn.addRequestProperty(USER_AGENT, MOZILLA);
        
        return conn;
    }
}
