/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iwildsensestressanalyzer.applicationsused;

import iwildsensestressanalyzer.IWildSenseStressAnalyzer;
import iwildsensestressanalyzer.dataanalyzer.ApplicationUsedAnalyzer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * This class categorize applications looking at their Java package name and
 * pinging the Google Play store to understand which category the app belongs
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class CategorizeApps {
    
    private static final HashMap<String, String> appAlreadyCategorized = 
            new HashMap<String, String>();
    
    private static final ArrayList<String> appNotRelevant = new ArrayList<String>();
    
    static {
        appAlreadyCategorized.put("com.android.mms", "Communication");
        appAlreadyCategorized.put("com.android.contacts", "Contacts");
        appAlreadyCategorized.put("com.android.phone", "Calls");
        appAlreadyCategorized.put("com.android.launcher", "Launcher");
        appAlreadyCategorized.put("com.google.android.browser", "Browser");
        appAlreadyCategorized.put("com.android.settings", "Settings");
        appAlreadyCategorized.put("com.google.android.gallery3d", "Media & Video");
        appAlreadyCategorized.put("com.google.android.dialer", "Communication");
        appAlreadyCategorized.put("pct.droid", "Entertainment");
        appAlreadyCategorized.put("com.google.android.contacts", "Contacts");
        appAlreadyCategorized.put("com.jayapps.mtube", "Media & Video");
        appAlreadyCategorized.put("com.android.calculator2", "Tools");
        appAlreadyCategorized.put("com.swisscom.tvguide", "Media & Video");
        appAlreadyCategorized.put("ch.orange.android", "Communication");
        appAlreadyCategorized.put("com.cyanogenmod.trebuchet", "Launcher");
        appAlreadyCategorized.put("com.android.dialer", "Communication");
        appAlreadyCategorized.put("com.tfsapps.playtube2", "Media & Video");
        appAlreadyCategorized.put("com.android.documentsui", "Productivity");
        appAlreadyCategorized.put("fr.amazon.mShop.android", "Shopping");
        appAlreadyCategorized.put("ch.neoos.doodle", "Productivity");
        appAlreadyCategorized.put("com.sjbstudio.the15minutemealsrecipes", "Lifestyle");
        appAlreadyCategorized.put("com.android.packageinstaller", "Tools");
        appAlreadyCategorized.put("com.handcent.nextsms", "Communication");
        appAlreadyCategorized.put("com.tripadvisor.android.apps.cityguide.london", "Travel & Local");
        appAlreadyCategorized.put("com.android.htmlviewer", "Tools");
        
        appNotRelevant.add("com.android.systemui");
        appNotRelevant.add("ch.unige.ping");
        appNotRelevant.add("com.android.vending");
        appNotRelevant.add("is.unige.ch.myapplication");
        appNotRelevant.add("com.android.shell");
        appNotRelevant.add("com.cyanogenmod.lockclock");
        appNotRelevant.add("com.example.roulezjeuneve");
        appNotRelevant.add("com.android.captiveportallogin");
    }
    
    private final static String HTML_PAGE = "https://play.google.com/store/apps/details?id=";
    private final static String INTERESTING_TAG = "<span itemprop=\"genre\">";
    
    public static String categorizeApp(String applicationName) {
            
        if (!appAlreadyCategorized.containsKey(applicationName)) {

            try {
                URL url = new URL(HTML_PAGE + applicationName+ "&hl=en");
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

                String category = webPage.substring(startIndex + 
                        INTERESTING_TAG.length(), endIndex).trim().replace("&amp;", "&");

                /**
                 * The interesting String  
                */
                appAlreadyCategorized.put(applicationName, category);
                ApplicationUsedAnalyzer.addCategory(category);
                return category;

            }
            catch(MalformedURLException exc) {
                System.out.println("MalformedURLException at CategorizeApp");
                //exc.printStackTrace();
                return null;
            }
            catch(IOException exc) {
                if (!appNotRelevant.contains(applicationName)) {
                    System.out.println("IOException in CategorizeApp: " + applicationName);
                    //exc.printStackTrace();
                }
                return null;
            }

        }
        else {
            return (String) appAlreadyCategorized.get(applicationName);
        }
        
    }
}
