package iwildsensestressanalyzer.filereader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * This class is responsible to read the file with the answers provided by
 * a participant to the surveys and the questionnaire
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class SurveyQuestionnaireReader extends BasicFileReader {
    
    private static final String SURVEY_IDENTIFIER = "stress_survey";
    private static final String QUESTIONNAIRE_IDENTIFIER = "stress_questionnaire";
    
    /**
     * 
     * Reads line by line the file of the Participant to retrieve the answers
     * to the surveys and the questionnaires
     * 
     * @param IMEI the IMEI of the participant
     * @param surveyLines used to return all the answers to the surveys
     * @param questionnaireLines used to return all the answer to the questionnaires
     */
    public static void readFile(String IMEI, ArrayList<String> surveyLines, 
            ArrayList<String> questionnaireLines) {
        
        BufferedReader reader;
        
        try {
            
            reader = new BufferedReader(new FileReader(FOLDER_DATA + 
                    FILE_SEPARATOR + FOLDER_SURVEY_DATA + FILE_SEPARATOR + 
                    IMEI + FILE_SURVEY));
            
            String line;
            
            while ((line = reader.readLine()) != null) {
                
                String[] lineEntries = line.split(",");
                
                /**
                 * [0]: timestamp
                 * [1]: timestamp
                 * [2]: timestamp
                 * [3]: stress_survey or _stress_questionnaire or something else
                 */
                
                if (lineEntries[3].equals(SURVEY_IDENTIFIER)) {
                    surveyLines.add(line);
                }
                else if (lineEntries[3].equals(QUESTIONNAIRE_IDENTIFIER)) {
                    questionnaireLines.add(line);
                }   
            }
            
        }
        catch(FileNotFoundException exc) {
            
            System.out.println("FileNotFoundException in SurveyQuestionnaireReader");
            exc.printStackTrace();
        }
        catch(IOException exc) {
            System.out.println("IOException in SurveyQuestionnaireReader");
            exc.printStackTrace();
        }
    }
    
}
