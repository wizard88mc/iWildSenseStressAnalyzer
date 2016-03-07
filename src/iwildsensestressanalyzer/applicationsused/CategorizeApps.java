package iwildsensestressanalyzer.applicationsused;

import iwildsensestressanalyzer.dataanalyzer.ApplicationUsedAnalyzer;
import iwildsensestressanalyzer.filereader.BasicFileReader;
import iwildsensestressanalyzer.participant.Participant;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
public class CategorizeApps extends BasicFileReader {
    
    private static final String FILE_APPS_CATEGORY = "apps_category.csv";
    private static final String FILE_APPS_NOT_RELEVANT = "apps_not_relevant.csv";
    private static final String FILE_CATEGORY_APP_OR_GAME = "category_app_or_game.csv";
    
    private static final HashMap<String, String> APPS_ALREADY_CATEGORIZED = 
        new HashMap<>();
    private static final ArrayList<String> APPS_NOT_RELEVANT = new ArrayList<>();
    private static final HashMap<String, String> CATEGORY_APP_OR_GAME = 
        new HashMap<>();
    
    private static BufferedWriter fileAppsCategoryWriter = null;
    
    static {
        
        /**
         * File for Application Categories
         */
        File fileAppsCategory = new File(FOLDER_DATA + FILE_SEPARATOR + 
                FOLDER_SETTINGS + FILE_SEPARATOR + FILE_APPS_CATEGORY);
            
        if (!fileAppsCategory.exists()) {
            fileAppsCategory.getParentFile().mkdirs();
        }
        
        File fileAppsNotRelevant = new File(FOLDER_DATA + FILE_SEPARATOR + 
                FOLDER_SETTINGS + FILE_SEPARATOR + FILE_APPS_NOT_RELEVANT);
        
        if (!fileAppsNotRelevant.exists()) {
            fileAppsNotRelevant.getParentFile().mkdirs();
        }
        
        /**
         * Creating file readers and writers
         */
        
        BufferedReader appsCategoryReader = null, appsNotRelevantReader = null;
        
        try {
            appsCategoryReader = new BufferedReader(new FileReader(fileAppsCategory));
        }
        catch(FileNotFoundException exc) {
            System.out.println("File apps Category not found.");
            
        }
        
        try {
            appsNotRelevantReader = 
                    new BufferedReader(new FileReader(fileAppsNotRelevant));
        }
        catch(FileNotFoundException exc) {
            System.out.println("File apps not relevant not found.");
        }
        
        if (appsCategoryReader != null) {
            
            String line;
            try {
                while ((line = appsCategoryReader.readLine()) != null) {
                    String[] elements = line.split(",");
                    APPS_ALREADY_CATEGORIZED.put(elements[0], elements[1]);
                }
            }
            catch(IOException exc) {
                System.out.println("IOException in reading Apps Category file");
                exc.printStackTrace();
            }
        }
        
        if (appsNotRelevantReader != null) {
            
            String line;
            try {
                while ((line = appsNotRelevantReader.readLine()) != null) {
                    APPS_NOT_RELEVANT.add(line);
                }
            }
            catch(IOException exc) {
                System.out.println("IOException in reading Apps Not Relevant file");
                exc.printStackTrace();
            }
        }
        
        try {
            fileAppsCategoryWriter = new BufferedWriter(
                new FileWriter(fileAppsCategory, true));
        }
        catch(IOException exc) {
            System.out.println("IOException in creating writer for Apps "
                    + "Category file");
            exc.printStackTrace();
        }
    }
    
    static {
        
        File fileCategoryAppOrGame = new File(FOLDER_DATA + FILE_SEPARATOR + 
                FOLDER_SETTINGS + FILE_SEPARATOR + FILE_CATEGORY_APP_OR_GAME);
        
        try {
            BufferedReader reader = 
                    new BufferedReader(new FileReader(fileCategoryAppOrGame));
            
            String line;
            
            try {
                while ((line = reader.readLine()) != null) {
                    String[] elements = line.split(",");
                    CATEGORY_APP_OR_GAME.put(elements[0], elements[1]);
                    
                    ApplicationUsedAnalyzer.addAppType(elements[1]);
                }
            }
            catch(IOException exc) {
                System.out.println("IOException in reading line for app or"
                        + " game file");
                exc.printStackTrace();
            }
            
            reader.close();
        }
        catch(FileNotFoundException exc) {
            System.out.println("File not found app or game file");
            exc.printStackTrace();
        }
        catch(IOException exc) {
            System.out.println("IOException in closing app or game file");
            exc.printStackTrace();
        }
    }
    
    private final static String HTML_PAGE = "https://play.google.com/store/apps/details?id=";
    private final static String INTERESTING_TAG = "<span itemprop=\"genre\">";
    
    public static String[] categorizeApp(String applicationName) {
            
        if (!APPS_NOT_RELEVANT.contains(applicationName)) {
            if (!APPS_ALREADY_CATEGORIZED.containsKey(applicationName)) {

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
                    APPS_ALREADY_CATEGORIZED.put(applicationName, category);
                    ApplicationUsedAnalyzer.addCategory(category);
                    
                    fileAppsCategoryWriter.write(applicationName + "," + category);
                    fileAppsCategoryWriter.newLine();
                    fileAppsCategoryWriter.flush();
                    
                    return new String[]{category, getAppOrGame(category)};

                }
                catch(MalformedURLException exc) {
                    System.out.println("MalformedURLException at CategorizeApp");
                    //exc.printStackTrace();
                    return null;
                }
                catch(IOException exc) {
                    if (!APPS_NOT_RELEVANT.contains(applicationName)) {
                        System.out.println("IOException in CategorizeApp: " + applicationName);
                        //exc.printStackTrace();
                    }
                    return null;
                }

            }
            else {
                String category = (String) APPS_ALREADY_CATEGORIZED.get(applicationName);
                return new String[]{category, getAppOrGame(category)};
            }
        }
        else {
            return null;
        }
    }
    
    /**
     * Returns if the category is an App or a Game
     * @param categoryName the category of the application
     * @return "App" if the category belongs to App set, "Game" otherwise
     */
    private static String getAppOrGame(String categoryName) {
        
        return (String) CATEGORY_APP_OR_GAME.get(categoryName);
    }
    
    public static void closeFile() {
        
        try {
            fileAppsCategoryWriter.flush();
            fileAppsCategoryWriter.close();
        }
        catch(IOException exc) {
            System.out.println("IOException in closing AppsCategory file.");
            exc.printStackTrace();
        }
    }
}
