package iwildsensestressanalyzer.weka;

import iwildsensestressanalyzer.IWildSenseStressAnalyzer;
import iwildsensestressanalyzer.esm.StressSurvey;
import iwildsensestressanalyzer.filereader.ClassesMappingReader;
import iwildsensestressanalyzer.filereader.WekaFilesCreatedReader;
import iwildsensestressanalyzer.filewriter.ARFFWekaWriter;
import iwildsensestressanalyzer.filewriter.CreatedFilesWriter;
import iwildsensestressanalyzer.filewriter.TemporaryWekaFileWriterForFeatures;
import iwildsensestressanalyzer.filewriter.WekaEvaluationOutputWriter;
import iwildsensestressanalyzer.participant.Participant;
import iwildsensestressanalyzer.preference.PreferenceManager;
import iwildsensestressanalyzer.utils.MathUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.lazy.IBk;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.supervised.instance.SMOTE;

/**
 * Works with the classification tasks, creating ARFF files and performing 
 * classification task
 * @author Matteo Ciman
 */
public class WekaAnalyzer {
    
    public static CreatedFilesWriter createdFilesWriter = null;
    
    public static void createWekaFiles(ArrayList<Participant> 
            participants, String subfolderName) {
        
        if (createdFilesWriter == null) {
            createdFilesWriter = new CreatedFilesWriter();
        }
        
        ArrayList<File> listCreatedFiles = new ArrayList<>();
        ArrayList<HashMap<String, String>> maps = 
                ClassesMappingReader.getAllClassMapping();
        
        /**
         * For Application used we need to create features only once all the 
         * application categories has been determined
         */
        WekaFeaturesForApplicationUsed.createFeaturesName();
        
        ArrayList<ArrayList<Double>> featuresForApplicationUsedForAllParticipants = 
                    new ArrayList<>(), 
                featuresForTouchesBufferedForAllParticipants = 
                    new ArrayList<>(), 
                featuresForUserActivityForAllParticipants = new ArrayList<>(),
                featuresForUserPresenceEventsForAllParticipants = 
                    new ArrayList<>(),
                featuresForUserPresenceLightForAllParticipants = 
                    new ArrayList<>();
        
        ArrayList<ArrayList<String>> featuresForDaysAndWeatherForAllParticipants = 
                new ArrayList<>();
        
        ArrayList<Integer> surveyAnswersForAllParticipants = 
            new ArrayList<>();
                
        int counter = 0; 
        for (Participant participant: participants) {
            
            System.out.println("*** Creating features for participant " + 
                    counter + "/" + participants.size());
            
            ArrayList<ArrayList<Double>> featuresForApplicationUsed = 
                        new ArrayList<>(), 
                    featuresForTouchesBuffered = new ArrayList<>(), 
                    featuresForUserActivity = new ArrayList<>(), 
                    featuresForUserPresenceEvents = new ArrayList<>(),
                    featuresForUserPresenceLight = new ArrayList<>();
            ArrayList<ArrayList<String>> featuresForDaysAndWeather = new ArrayList<>();
            
            ArrayList<Integer> surveyAnswers = new ArrayList<>();
            
            for (StressSurvey survey: participant.getStressSurveys()) {
                
                surveyAnswers.add(survey.getStress());
                
                featuresForApplicationUsed.add(WekaFeaturesForApplicationUsed.
                        getFeaturesForUserActivity(survey));
                featuresForTouchesBuffered.add(WekaFeaturesForTouchesBuffered.
                        getFeaturesForTouchesBufferedEvents(survey));
                featuresForUserActivity.add(WekaFeaturesForUserActivity.
                        getFeaturesForUserActivity(survey));
                featuresForUserPresenceEvents.add(WekaFeaturesForScreenEvents.
                        getFeaturesForScreenEvents(survey));
                featuresForUserPresenceLight.add(WekaFeaturesForUserPresenceLight.
                        getFeaturesForUserPresenceLightEvent(survey));
                featuresForDaysAndWeather.add(WekaFeaturesForDaysAndWeather.
                        getFeaturesForDaysAndWeather(survey));
            }
            
            /**
             * Adding features to the containers for the one with all the 
             * participants
             */
            featuresForApplicationUsedForAllParticipants.addAll(featuresForApplicationUsed);
            featuresForTouchesBufferedForAllParticipants.addAll(featuresForTouchesBuffered);
            featuresForUserActivityForAllParticipants.addAll(featuresForUserActivity);
            featuresForUserPresenceEventsForAllParticipants.addAll(featuresForUserPresenceEvents);
            featuresForUserPresenceLightForAllParticipants.addAll(featuresForUserPresenceLight);
            featuresForDaysAndWeatherForAllParticipants.addAll(featuresForDaysAndWeather);
            
            surveyAnswersForAllParticipants.addAll(surveyAnswers);
            
            /**
             * Normalizing features
             */
            ArrayList<ArrayList<Double>> normalizedFeaturesForApplicationUser = 
                        MathUtils.normalizeSetOfDoubleData(featuresForApplicationUsed, 
                                0.0, 1.0), 
                    normalizedFeaturesForTouchesBuffered = 
                        MathUtils.normalizeSetOfDoubleData(featuresForTouchesBuffered, 
                                0.0, 1.0),
                    normalizedFeaturesForUserActivity = 
                        MathUtils.normalizeSetOfDoubleData(featuresForUserActivity, 
                                0.0, 1.0),
                    normalizedFeaturesForUserPresence = 
                        MathUtils.normalizeSetOfDoubleData(featuresForUserPresenceEvents, 
                                0.0, 1.0),
                    normalizedFeaturesForUserPresenceLight = 
                        MathUtils.normalizeSetOfDoubleData(featuresForUserPresenceLight, 
                                0.0, 1.0);
            
            /**
             * Iterating over all the possible class mapping
             */
            for (HashMap<String, String> map: maps) {
                
                System.out.println("Creating file for map: " + 
                        map.get("map_name"));
            
                String classesString = "";
                for (String key: map.keySet()) {
                    if (!key.equals("map_name") && 
                            !classesString.contains(map.get(key))) {
                        classesString += "," + map.get(key);
                    }
                }
                classesString = classesString.replaceFirst(",", "");
                
                TemporaryWekaFileWriterForFeatures csvWriter = 
                        new TemporaryWekaFileWriterForFeatures(classesString);

                /**
                 * Writing in the temporary file only the instances that have
                 * an 
                 */
                for (int i = 0; i < surveyAnswers.size(); i++) {
                    
                    if (!map.get(String.valueOf(surveyAnswers.get(i))).equals("")) {

                        ArrayList<String> features = new ArrayList<>();
                        features.addAll(MathUtils
                                .convertFeaturesToASetOfString(
                                        normalizedFeaturesForApplicationUser.get(i)));
                        features.addAll(MathUtils
                                .convertFeaturesToASetOfString(
                                        normalizedFeaturesForTouchesBuffered.get(i)));
                        features.addAll(MathUtils
                                .convertFeaturesToASetOfString(
                                        normalizedFeaturesForUserActivity.get(i)));
                        features.addAll(MathUtils
                                .convertFeaturesToASetOfString(
                                        normalizedFeaturesForUserPresence.get(i)));
                        features.addAll(MathUtils
                                .convertFeaturesToASetOfString(
                                        normalizedFeaturesForUserPresenceLight.get(i)));

                        features.addAll(featuresForDaysAndWeather.get(i));

                        csvWriter.writeLine(features, 
                                map.get(String.valueOf(surveyAnswers.get(i))));
                    }
                }
                csvWriter.closeFile();

                try {
                    
                    File createdFile = 
                            createWekaFileFromCSVFile(csvWriter.getFile(), 
                                classesString, participant.getIMEI(), 
                                subfolderName, map.get("map_name"));

                    if (createdFile != null) {
                        listCreatedFiles.add(createdFile);
                    }
                    
                }
                catch(Exception exc) {
                    System.out.println("Exception in creating DataSource");
                    exc.printStackTrace();
                }
                
                csvWriter.deleteFile();
            }
            
            counter++;
        }
        
        /**
         * Normalizing features with all participants
         */
        ArrayList<ArrayList<Double>> 
            normalizedFeaturesForApplicationsForAllParticipants = 
                MathUtils.normalizeSetOfDoubleData(
                    featuresForApplicationUsedForAllParticipants, 0.0, 1.0),
            normalizedFeaturesForTouchesBufferedForAllParticipants = 
                MathUtils.normalizeSetOfDoubleData(
                    featuresForTouchesBufferedForAllParticipants, 0.0, 1.0), 
            normalizedFeaturesForUserActivityForAllParticipants = 
                MathUtils.normalizeSetOfDoubleData(
                    featuresForUserActivityForAllParticipants, 0.0, 1.0), 
            normalizedFeaturesForUserPresenceEventsForAllParticipants = 
                MathUtils.normalizeSetOfDoubleData(
                    featuresForUserPresenceEventsForAllParticipants, 0.0, 1.0),
            normalizedFeaturesforUserPresenceLightForAllParticipants = 
                MathUtils.normalizeSetOfDoubleData(
                    featuresForUserPresenceLightForAllParticipants, 0.0, 1.0);
        
        System.out.println("*** Creating files for ALL participants");
        for (HashMap<String, String> map: maps) {
            
            System.out.println("*** Creating file for mapping " + map.get("map_name"));
            
            String classesString = "";
            for(String key: map.keySet()) {
                if (!key.equals("map_name") && 
                        !classesString.contains(map.get(key))) {
                    classesString += "," + map.get(key);
                }
            }
            classesString = classesString.replaceFirst(",", "");
            
            TemporaryWekaFileWriterForFeatures csvWriter = 
                    new TemporaryWekaFileWriterForFeatures(classesString);
        
            for (int i = 0; i < surveyAnswersForAllParticipants.size(); i++) {
            
                if (!map.get(String.valueOf(surveyAnswersForAllParticipants.get(i))).equals("")) {
            
                    ArrayList<String> features = new ArrayList<>();
                    features.addAll(MathUtils.convertFeaturesToASetOfString(
                            normalizedFeaturesForApplicationsForAllParticipants.get(i)));
                    features.addAll(MathUtils.convertFeaturesToASetOfString(
                            normalizedFeaturesForTouchesBufferedForAllParticipants.get(i)));
                    features.addAll(MathUtils.convertFeaturesToASetOfString(
                            normalizedFeaturesForUserActivityForAllParticipants.get(i)));
                    features.addAll(MathUtils.convertFeaturesToASetOfString(
                            normalizedFeaturesForUserPresenceEventsForAllParticipants.get(i)));
                    features.addAll(MathUtils.convertFeaturesToASetOfString(
                            normalizedFeaturesforUserPresenceLightForAllParticipants.get(i)));
                    features.addAll(featuresForDaysAndWeatherForAllParticipants.get(i));
                    
                    csvWriter.writeLine(features, 
                            map.get(String.valueOf(surveyAnswersForAllParticipants.get(i))));
                }
            }
            csvWriter.closeFile();
                
            File createdFile = 
                createWekaFileFromCSVFile(csvWriter.getFile(), 
                    classesString, "_ALL", subfolderName, map.get("map_name"));
                
            if (createdFile != null) {
                listCreatedFiles.add(createdFile);
            }
            
            csvWriter.deleteFile();
        }
        
        createdFilesWriter.writeCreatedFiles(listCreatedFiles);
    }
    
    public static void startWithWekaClassificationTask(String toWorkWith, 
                String folder) {
        
        ArrayList<String> allFileNames = WekaFilesCreatedReader.getCreatedWekaFiles(folder),
                filesToUse = new ArrayList<>();
        
        for (String fileName: allFileNames) {
            if (toWorkWith.equals(IWildSenseStressAnalyzer.ONLY_MORE_THAN_ZERO) &&
                    fileName.contains(IWildSenseStressAnalyzer.FOLDER_MORE_ZERO_ANSWERS)) {
                
                filesToUse.add(fileName);
            }
            else if (toWorkWith.equals(IWildSenseStressAnalyzer.ONLY_MORE_THRESHOLD) &&
                    fileName.contains(IWildSenseStressAnalyzer.FOLDER_MORE_THRESHOLD)) {
                
                filesToUse.add(fileName);
            }
            else if (toWorkWith.equals(IWildSenseStressAnalyzer.ONLY_MORE_ONE_PER_DAY) &&
                    fileName.contains(IWildSenseStressAnalyzer.FOLDER_MORE_ONE_SURVEY_PER_DAY)) {
                
                filesToUse.add(fileName);
            }
            else if (toWorkWith.equals(IWildSenseStressAnalyzer.ONLY_MORE_INITIAL_AVERAGE) &&
                    fileName.contains(IWildSenseStressAnalyzer.FOLDER_MORE_THAN_INITIAL_AVERAGE)) {
                
                filesToUse.add(fileName);
            }
        }
        
        /**
         * Starting weka classification task
         */
        if (toWorkWith.equals(IWildSenseStressAnalyzer.ONLY_MORE_THAN_ZERO)) {
            
            System.out.println("*** Classification for participants with more than "
                + "zero answers ***");
            performWekaClassificationTask(filesToUse, 
                IWildSenseStressAnalyzer.TITLE_PARTICIPANTS_MORE_ZERO_ANSWERS, 
                folder, IWildSenseStressAnalyzer.FOLDER_MORE_ZERO_ANSWERS);
        }
        else if (toWorkWith.equals(IWildSenseStressAnalyzer.ONLY_MORE_THRESHOLD)) {
            
            System.out.println("*** Classification for participants with more "
                + "answers than the threshold ***");
            performWekaClassificationTask(filesToUse, 
                IWildSenseStressAnalyzer.TITLE_MORE_THRESHOLD, 
                folder, IWildSenseStressAnalyzer.FOLDER_MORE_THRESHOLD);
        }
        else if (toWorkWith.equals(IWildSenseStressAnalyzer.ONLY_MORE_ONE_PER_DAY)) {
        
            System.out.println("*** Classification for participants with more than "
                + "one answer per day ***");
            performWekaClassificationTask(filesToUse, 
                IWildSenseStressAnalyzer.TITLE_MORE_ONE_SURVEY_PER_DAY, 
                folder, IWildSenseStressAnalyzer.FOLDER_MORE_ONE_SURVEY_PER_DAY);
        }
        else if (toWorkWith.equals(IWildSenseStressAnalyzer.ONLY_MORE_INITIAL_AVERAGE)) {
        
            System.out.println("*** Classification for participants with number of "
                + "answers more than the initial average ***");
            performWekaClassificationTask(filesToUse, 
                IWildSenseStressAnalyzer.TITLE_MORE_THAN_INITIAL_AVERAGE, 
                folder, IWildSenseStressAnalyzer.FOLDER_MORE_THAN_INITIAL_AVERAGE);
        }
    }
    
    /**
     * Performs Classification
     */
    private static void performWekaClassificationTask(ArrayList<String> listFileNames, 
            String initialMessage, String folder, String subfolder) {
        
        WekaEvaluationOutputWriter outputWriter = 
                new WekaEvaluationOutputWriter(folder, subfolder);
        
        outputWriter.writeOnOutputFile(initialMessage);
        
        int counter = 1;
        
        for (String fileName: listFileNames) {
            
            System.out.println("**** Weka analysis file " + counter + "/" + 
                    listFileNames.size());
            
            File file = new File(fileName);

            try {
                DataSource source = new DataSource(file.getPath());
                Instances data = source.getDataSet();

                if (data.classIndex() == -1) {
                    data.setClassIndex(data.numAttributes() - 1);
                }
                
                int numClasses = data.numClasses();

                outputWriter.writeOnOutputFile(file.getPath());

                outputWriter
                        .writeClassesOccurrences(countOccurrences(data, numClasses));

                try {
                    /**
                     * ZeroR baseline classification test
                     */
                    ZeroR zeroR = new ZeroR();
                    zeroR.buildClassifier(data);
                    Evaluation zeroREval = new Evaluation(data);
                    zeroREval.crossValidateModel(zeroR, data, 10, new Random(10));

                    outputWriter.writeOnOutputFile("ZeroR Classification");
                    outputWriter.writeOnOutputFile(
                        evaluateClassificationPerformances(zeroREval, 
                            numClasses));

                    /**
                     * Tree classification test
                     */
                    J48 tree = new J48();
                    tree.buildClassifier(data);
                    Evaluation treeEval = new Evaluation(data);
                    treeEval.crossValidateModel(tree, data, 10, new Random(10));

                    outputWriter.writeOnOutputFile("TREE Classification");
                    outputWriter.writeOnOutputFile(
                        evaluateClassificationPerformances(treeEval, 
                            numClasses));

                    /**
                     * kNN classification test
                     */
                    for (int k = 2; k < 3; k++) {

                        IBk knn = new IBk(k);
                        knn.setOptions(weka.core.Utils.
                            splitOptions("-W 0 -X -A "
                                + "weka.core.neighboursearch.LinearNNSearch"));
                        knn.buildClassifier(data);
                        Evaluation knnEval = new Evaluation(data);
                        knnEval.crossValidateModel(knn, data, 10, new Random(10));

                        outputWriter.writeOnOutputFile(k + "-kNN Classification");
                        outputWriter.writeOnOutputFile(
                            evaluateClassificationPerformances(knnEval, 
                                numClasses));
                    }

                    /**
                     * SVM evaluation
                     */
                    try {
                        LibSVM svm = new LibSVM();
                        svm.setOptions(weka.core.Utils.splitOptions("-S 1 -K 2 "
                            + "-D 5 -G 0.0 -R 0.0 -N 0.5 -M 40 -C 2.0 "
                            + "-E 0.001 -P 0.1 -Z -seed 1"));
                        svm.setDoNotReplaceMissingValues(false);
                        svm.buildClassifier(data);
                        Evaluation svmEval = new Evaluation(data);
                        svmEval.crossValidateModel(svm, data, 10, new Random(10));

                        outputWriter.writeOnOutputFile("SVM Classification");
                        outputWriter.writeOnOutputFile(
                            evaluateClassificationPerformances(svmEval, 
                                numClasses));
                    }
                    catch(Exception exc) {
                        System.out.println("SVM exception: " + exc.toString());
                    }

                    /**
                     * Multilayer Perceptron evaluation
                     */
                    MultilayerPerceptron multiPerceptron = new MultilayerPerceptron();
                    multiPerceptron.setOptions(weka.core.Utils.
                        splitOptions("-L 0.3 -M 0.2 -N 500 -V 0 "
                            + "-S 0 -E 20 -H a"));
                    multiPerceptron.buildClassifier(data);
                    Evaluation multiPEval = new Evaluation(data);
                    multiPEval.crossValidateModel(multiPerceptron, data, 
                        10, new Random(10));

                    outputWriter.writeOnOutputFile("Multilayer Perceptron "
                        + "Classification");
                    outputWriter.writeOnOutputFile(
                        evaluateClassificationPerformances(multiPEval, numClasses));
                }
                catch(Exception exc) {
                    System.out.println("Exception in performing weka classification");
                    exc.printStackTrace();
                }
            }
            catch(Exception exc) {
                System.out.println("Exception in performWekaClassificationTask");
                exc.printStackTrace();
            }
            
            counter++;
        }
        
        outputWriter.closeFile();
    }
    
    /**
     * Evaluates the performances of a classifier analyzing it using the 
     * PerformanceEvaluator
     * @param eval the result of the classification task 
     * @param easyTask true if it is the easy task with three labels, 
     * false otherwise
     * @return a formatted String with the results of the classification task
     */
    private static String evaluateClassificationPerformances(Evaluation eval, 
            int numberOfClasses) {
        
        PerformanceEvaluator evaluator = new PerformanceEvaluator(eval, 
                numberOfClasses);
        
        return evaluator.evaluatePerformances();
    }
    
    /**
     * Creates an .arff file from the temporary .csv file
     * @param tempCSVFile the original csv file
     * @param listClasses a comma separated string with all the possible classes
     * @param IMEI the IMEI of the participant
     * @param subfolder the subfolder where to save the file
     * @param mapName the name of the mapping
     * @return 
     */
    private static File createWekaFileFromCSVFile(File tempCSVFile, 
            String listClasses, String IMEI, String subfolder, String mapName) {
        
        try {
            if (IWildSenseStressAnalyzer.DEBUG) {
                System.out.println("Importing data from CSV file");
            }
            DataSource source = new DataSource(tempCSVFile.getPath());
            Instances dataSet = source.getDataSet();
            
            if (dataSet.classIndex() == -1) {
                dataSet.setClassIndex(dataSet.numAttributes() - 1);
            }
            
            checkNumberOfIstances(dataSet, listClasses.split(",").length);
            if (IWildSenseStressAnalyzer.DEBUG) {
                System.out.println("***** Starting applying SMOTE filter *****");
            }
            dataSet = applySMOTEFilterToTrainingData(dataSet, 
                    listClasses.split(",").length);
            if (IWildSenseStressAnalyzer.DEBUG) {
                System.out.println("***** SMOTE filter completed *****");
            }
            
            ARFFWekaWriter writer = new ARFFWekaWriter(IMEI, subfolder, 
                    mapName);

            PreferenceManager preference = new PreferenceManager(IMEI, 
                    subfolder, mapName);

            writer.writePreambleWekaFile(listClasses);
            preference.writePreamble();

            for (int i = 0; i < dataSet.numInstances(); i++) {
                /**
                 * Prints an instance on the output file
                 */
                writer.writeInstance(dataSet.instance(i).toString());
                preference.writeInstance(dataSet.instance(i).toString());
            }

            writer.closeFile();
            preference.closeFile();

            return writer.getOutputFiles();
        }
        catch(Exception exc) {
            System.out.println("Exception in loading data source");
            exc.printStackTrace();
            
            return null;
        }
    }
    
    /**
     * Counts the number of instances for each class and duplicates an instance 
     * if it is the only one for a class
     * @param instances the set of instances
     * @param numberOfClasses the number of target classes
     */
    private static void checkNumberOfIstances(Instances instances, 
            int numberOfClasses) {
        
        Integer[] classOccurences = countOccurrences(instances, numberOfClasses);
        
        for (int i = 0; i < classOccurences.length; i++) {
            
            if (classOccurences[i] == 1) {
                /**
                 * Duplicating an instance if it is the only one for that 
                 * particular class
                 */
                duplicateInstance(instances, i);
            }
        }
    }
    
    /**
     * Duplicates a single instance for a class label that has only a single 
     * instance in its training set
     * @param instances all the instances
     * @param index the index of the class that we have to duplicate
     */
    private static void duplicateInstance(Instances instances, int index) {
        
        boolean done = false;
        for (int i = 0; i < instances.numInstances() && !done; i++) {
            
            int classAttribute = (int) instances.instance(i)
                    .value(instances.instance(i).classAttribute());
            
            if (classAttribute == index) {
                /**
                 * Copying the single instance
                 */
                Instance newInstance = (Instance) instances.instance(i).copy();
                instances.add(newInstance);
                
                done = true;
            }
        }
    }
    
    /**
     * Applies SMOTE filter to balance unbalance training set
     * @param instances the set of instances
     */
    private static Instances applySMOTEFilterToTrainingData(Instances instances, 
            int numberOfClasses) {
        /**
         * First of all we count the number of instances for each class
         */
        Integer[] occurrences = countOccurrences(instances, numberOfClasses);
        
        /**
         * Getting the number of max occurrences and the index
         */
        int numberOfMaxInstances = Integer.MIN_VALUE, indexMaxOccurrences = -1;
        
        for (int i = 0; i < occurrences.length; i++) {
            if (occurrences[i] > numberOfMaxInstances) {
                numberOfMaxInstances = occurrences[i];
                indexMaxOccurrences = i;
            }
        }
        
        /**
         * Time to apply the SMOTHE filter to all the classes != from the 
         * one with the highest number of occurrences
         */
        for (int i = 0; i < occurrences.length; i++) {
            
            if (i != indexMaxOccurrences) {
             
                try {
                    /**
                     * Maximum distance between two classes counter is 2
                     */
                    while (Math.abs(occurrences[i] - numberOfMaxInstances) > 5) {
                        
                        SMOTE filter = new SMOTE();
                        filter.setInputFormat(instances);

                        double percentage = 
                                (numberOfMaxInstances - occurrences[i]) * 100 
                                / occurrences[i];
                        if (percentage > 100) {
                            percentage = 100;
                        }
                        if (percentage == 0.0) {
                            percentage = 0.5;
                        }

                        filter.setPercentage(percentage);
                        if (occurrences[i] > 10) {
                            filter.setNearestNeighbors(5);
                        }
                        else if (occurrences[i] > 5) {
                            filter.setNearestNeighbors(2);
                        }
                        else if (occurrences[i] <= 5) {
                            filter.setNearestNeighbors(1);
                        }

                        filter.setClassValue(String.valueOf(i + 1));
                    
                        instances = Filter.useFilter(instances, filter);
                        
                        occurrences = countOccurrences(instances, 
                                numberOfClasses);
                    }
                }
                catch(Exception exc) {
                    System.out.println("Exception in applying SMOTHE filter: " 
                            + exc.toString());
                }
            }
        }
        
        return instances;
    }
    
    /**
     * Counts the number of occurrences for each class
     * @param instances the set of instances
     * @param numberOfClasses the total number of classes of the dataset
     * @return an array with the number of occurrences for each class
     */
    private static Integer[] countOccurrences(Instances instances, 
            int numberOfClasses) {
        
        Integer[] occurrences = new Integer[numberOfClasses];
        for (int i = 0; i < occurrences.length; i++) {
            occurrences[i] = 0;
        }
        
        for (int i = 0; i < instances.numInstances(); i++) {
            
            int classAttributeValue = (int) instances.instance(i)
                    .value(instances.instance(i).classAttribute());
            
            occurrences[classAttributeValue]++;
        }
        
        return occurrences;
    }
}
