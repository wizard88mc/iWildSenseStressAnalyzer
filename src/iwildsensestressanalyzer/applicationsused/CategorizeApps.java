/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iwildsensestressanalyzer.applicationsused;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * This class categorize applications looking at their Java package name and
 * pinging the Google Play store to understand which category the app belongs
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class CategorizeApps {
    
    //TODO aggiungere hashmap per evitare di fare query di app già categorizzate
    //private static 
    
    private final static String HTML_PAGE = "https://play.google.com/store/apps/details?id=";
    private final static String INTERESTING_TAG = "<span itemprop=\"genre\">";
    
    public static void categorizeApp(ArrayList<ApplicationsUsedEvent> applicationUsed) {
        
        for (ApplicationsUsedEvent application: applicationUsed) {
            
            if (!false) {
                
                try {
                    URL url = new URL(HTML_PAGE + application.getApp() + "&hl=en");
                    InputStream inputStream = url.openStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    
                    String webPage = "", line;
                    while ((line = reader.readLine()) != null) {
                        webPage += line;
                    }
                    
                    /**
                     * Looking for the position on the page of the interesting
                     * tag with App categorization
                     */
                    int startIndex = webPage.indexOf(INTERESTING_TAG);
                    int endIndex = webPage.indexOf("</span>", startIndex + INTERESTING_TAG.length());
                    
                    String interestingSubstring = webPage.substring(startIndex + 
                            INTERESTING_TAG.length(), endIndex).trim();
                    
                    /**
                     * The interesting String  
                    */
                    System.out.println(interestingSubstring);
                    
                }
                catch(MalformedURLException exc) {
                    System.out.println("MalformedURLException at CategorizeApp");
                    exc.printStackTrace();
                }
                catch(IOException exc) {
                    System.out.println("IOException in CategorizeApp");
                    exc.printStackTrace();
                }
                
            }
            else {
                
            }
            
        }
        
    }
    
    
}
