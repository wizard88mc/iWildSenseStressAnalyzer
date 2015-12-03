package iwildsensestressanalyzer.weka;

import iwildsensestressanalyzer.esm.StressSurvey;
import iwildsensestressanalyzer.filewriter.ARFFWekaWriter;
import iwildsensestressanalyzer.filewriter.WekaEvaluationOutputWriter;
import iwildsensestressanalyzer.participant.Participant;
import iwildsensestressanalyzer.utils.MathUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.lazy.IBk;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
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
    
    public static ArrayList<File> listCreatedFiles = new ArrayList<File>();
    
    public static void workWithClassificationProblem(ArrayList<Participant> 
            participants, String initialMessage, String subfolderName) {
        
        /**
         * For Application used we need to create features only once all the 
         * application categories has been determined
         */
        WekaFeaturesForApplicationUsed.createFeaturesName();
        
        ArrayList<ArrayList<Double>> featuresForApplicationUsedForAllParticipants = 
                    new ArrayList<ArrayList<Double>>(), 
                featuresForTouchesBufferedForAllParticipants = 
                    new ArrayList<ArrayList<Double>>(), 
                featuresForUserActivityForAllParticipants = 
                    new ArrayList<ArrayList<Double>>(), 
                featuresForUserPresenceLightForAllParticipants = 
                    new ArrayList<ArrayList<Double>>();
        
        ArrayList<Integer> surveyAnswersForAllParticipants = 
            new ArrayList<Integer>();
                
        for (Participant participant: participants) {
            
            ARFFWekaWriter output = new ARFFWekaWriter(participant.getIMEI(), 
                    subfolderName);
            
            ArrayList<ArrayList<Double>> featuresForApplicationUsed = 
                        new ArrayList<ArrayList<Double>>(), 
                    featuresForTouchesBuffered = 
                        new ArrayList<ArrayList<Double>>(), 
                    featuresForUserActivity = 
                        new ArrayList<ArrayList<Double>>(), 
                    featuresForUserPresenceLight = 
                        new ArrayList<ArrayList<Double>>();
            
            ArrayList<Integer> surveyAnswers = new ArrayList<Integer>();
            
            for (StressSurvey survey: participant.getStressSurveys()) {
                
                surveyAnswers.add(survey.getStress());
                
                featuresForApplicationUsed.add(WekaFeaturesForApplicationUsed.getFeaturesForUserActivity(survey));
                featuresForTouchesBuffered.add(WekaFeaturesForTouchesBuffered.getFeaturesForTouchesBufferedEvents(survey));
                featuresForUserActivity.add(WekaFeaturesForUserActivity.getFeaturesForUserActivity(survey));
                featuresForUserPresenceLight.add(WekaFeaturesForUserPresenceLight.getFeaturesForUserPresenceLightEvent(survey));
            }
            
            /**
             * Adding features to the containers for the one with all the 
             * participants
             */
            featuresForApplicationUsedForAllParticipants.addAll(featuresForApplicationUsed);
            featuresForTouchesBufferedForAllParticipants.addAll(featuresForTouchesBuffered);
            featuresForUserActivityForAllParticipants.addAll(featuresForUserActivity);
            featuresForUserPresenceLightForAllParticipants.addAll(featuresForUserPresenceLight);
            
            surveyAnswersForAllParticipants.addAll(surveyAnswers);
            
            /**
             * Normalizing features
             */
            ArrayList<ArrayList<Double>> normalizedFeaturesForApplicationUser = 
                        MathUtils.normalizeSetOfDoubleData(featuresForApplicationUsed, 0.0, 1.0), 
                    normalizedFeaturesForTouchesBuffered = 
                        MathUtils.normalizeSetOfDoubleData(featuresForTouchesBuffered, 0.0, 1.0),
                    normalizedFeaturesForUserActivity = 
                        MathUtils.normalizeSetOfDoubleData(featuresForUserActivity, 0.0, 1.0),
                    normalizedFeaturesForUserPresenceLight = 
                        MathUtils.normalizeSetOfDoubleData(featuresForUserPresenceLight, 0.0, 1.0);
            
            /**
             * Writing output file for the participant
             */
            for (int i = 0; i < surveyAnswers.size(); i++) {
                
                ArrayList<Double> features = new ArrayList<Double>();
                features.addAll(normalizedFeaturesForApplicationUser.get(i));
                features.addAll(normalizedFeaturesForTouchesBuffered.get(i));
                features.addAll(normalizedFeaturesForUserActivity.get(i));
                features.addAll(normalizedFeaturesForUserPresenceLight.get(i));
                
                output.writeCalculatedFeaturesOnOutputFiles(
                        MathUtils.convertFeaturesToASetOfString(features), 
                        surveyAnswers.get(i));
            }
            
            output.closeFiles();
            
            listCreatedFiles.addAll(output.getOutputFiles());
        }
        
        /**
         * Normalizing features with all participants
         */
        ArrayList<ArrayList<Double>> normalizedFeaturesForApplicationsForAllParticipants = 
                    MathUtils.normalizeSetOfDoubleData(featuresForApplicationUsedForAllParticipants, 0.0, 1.0), 
                normalizedFeaturesForTouchesBufferedForAllParticipants = 
                    MathUtils.normalizeSetOfDoubleData(featuresForTouchesBufferedForAllParticipants, 0.0, 1.0), 
                normalizedFeaturesForUserActivityForAllParticipants = 
                    MathUtils.normalizeSetOfDoubleData(featuresForUserActivityForAllParticipants, 0.0, 1.0), 
                normalizedFeaturesforUserPresenceLightForAllParticipants = 
                    MathUtils.normalizeSetOfDoubleData(featuresForUserPresenceLightForAllParticipants, 0.0, 1.0);
        
        /**
         * Writing output file for all participants
         */
        ARFFWekaWriter writerForAllParticipants = new ARFFWekaWriter("_ALL", 
                subfolderName);
        
        for (int i = 0; i < surveyAnswersForAllParticipants.size(); i++) {
            
            ArrayList<Double> features = new ArrayList<Double>();
            features.addAll(normalizedFeaturesForApplicationsForAllParticipants.get(i));
            features.addAll(normalizedFeaturesForTouchesBufferedForAllParticipants.get(i));
            features.addAll(normalizedFeaturesForUserActivityForAllParticipants.get(i));
            features.addAll(normalizedFeaturesforUserPresenceLightForAllParticipants.get(i));
            
            writerForAllParticipants.writeCalculatedFeaturesOnOutputFiles(
                    MathUtils.convertFeaturesToASetOfString(features), 
                    surveyAnswersForAllParticipants.get(i));
        }
        
        writerForAllParticipants.closeFiles();
        
        listCreatedFiles.addAll(writerForAllParticipants.getOutputFiles());
        
        performWekaClassificationTask(initialMessage, subfolderName, false);
        performWekaClassificationTask(initialMessage, subfolderName, true);
    }
    
    /**
     * Performs Classification
     */
    private static void performWekaClassificationTask(String initialMessage, 
            String subfolder, boolean oversampleData) {
        
        WekaEvaluationOutputWriter outputWriter = 
                new WekaEvaluationOutputWriter(subfolder);
        
        outputWriter.writeOnOutputFile(initialMessage);
        
        for (File file: listCreatedFiles) {
            
            try {
                DataSource source = new DataSource(file.getPath());
                Instances data = source.getDataSet();
            
                if (data.classIndex() == -1) {
                    data.setClassIndex(data.numAttributes() - 1);
                }
            
                if (data.numInstances() > 10) {
                    
                    outputWriter.writeOnOutputFile(file.getPath());
                    
                    boolean easyTask = file.getName().contains("EASY");
                    
                    if (oversampleData) {
                    /**
                     * Initial filtering 
                     */
                        checkNumberOfIstances(data, easyTask);
                        data = applySMOTEFilterToTrainingData(data, easyTask);
                    }
                    /**
                     * ZeroR baseline classification test
                     */
                    ZeroR zeroR = new ZeroR();
                    zeroR.buildClassifier(data);
                    Evaluation zeroREval = new Evaluation(data);
                    zeroREval.crossValidateModel(zeroR, data, 10, new Random(10));
                    
                    outputWriter.writeOnOutputFile("ZeroR Classification");
                    outputWriter.writeOnOutputFile(evaluateClassificationPerformances(zeroREval, easyTask));
                    
                    /**
                     * Tree classification test
                     */
                    J48 tree = new J48();
                    tree.buildClassifier(data);
                    Evaluation treeEval = new Evaluation(data);
                    treeEval.crossValidateModel(tree, data, 10, new Random(10));
                    
                    outputWriter.writeOnOutputFile("TREE Classification");
                    outputWriter.writeOnOutputFile(evaluateClassificationPerformances(treeEval, easyTask));
                    
                    /**
                     * kNN classification test
                     */
                    for (int k = 0; k < 3; k++) {
                        
                        IBk knn = new IBk(k);
                        knn.setOptions(weka.core.Utils.splitOptions("-W 0 -X -A weka.core.neighboursearch.LinearNNSearch"));
                        knn.buildClassifier(data);
                        Evaluation knnEval = new Evaluation(data);
                        knnEval.crossValidateModel(knn, data, 10, new Random(10));
                        
                        outputWriter.writeOnOutputFile(k + "-kNN Classification");
                        outputWriter.writeOnOutputFile(evaluateClassificationPerformances(knnEval, easyTask));
                    }
                    
                    /**
                     * SVM evaluation
                     */
                    try {
                        LibSVM svm = new LibSVM();
                        svm.setOptions(weka.core.Utils.splitOptions("-S 1 -K 2 "
                                + "-D 5 -G 0.0 -R 0.0 -N 0.5 -M 40 -C 2.0 "
                                + "-E 0.001 -P 0.1 -Z -seed 1"));
                        svm.setDoNotReplaceMissingValues(true);
                        svm.buildClassifier(data);
                        Evaluation svmEval = new Evaluation(data);
                        svmEval.crossValidateModel(svm, data, 10, new Random(10));
						
                        outputWriter.writeOnOutputFile("SVM Classification");
                        outputWriter.writeOnOutputFile(evaluateClassificationPerformances(svmEval, easyTask));
                    }
                    catch(Exception exc) {}
                    
                    /**
                     * Multilayer Perceptron evaluation
                     */
                    MultilayerPerceptron multiPerceptron = new MultilayerPerceptron();
                    multiPerceptron.setOptions(weka.core.Utils.splitOptions("-L 0.3 -M 0.2 -N 1000 -V 0 -S 1 -E 20 -H a"));
                    multiPerceptron.buildClassifier(data);
                    Evaluation multiPEval = new Evaluation(data);
                    multiPEval.crossValidateModel(multiPerceptron, data, 10, new Random(10));
					
                    outputWriter.writeOnOutputFile("Multilayer Perceptron Classification");
                    outputWriter.writeOnOutputFile(evaluateClassificationPerformances(multiPEval, easyTask));
					
                    /**
                     * Voted Perceptron evaluation
                     */
                    /*VotedPerceptron votedPerceptron = new VotedPerceptron();
                    votedPerceptron.setOptions(weka.core.Utils.splitOptions("-I 10 -E 1.0 -S 2 -M 10000"));
                    votedPerceptron.buildClassifier(data);
                    Evaluation votedPEval = new Evaluation(data);
                    votedPEval.crossValidateModel(votedPerceptron, data, 10, new Random(10));
					
                    outputWriter.writeOnOutputFile("Voted Perceptron Classification");
                    outputWriter.writeOnOutputFile(evaluateClassificationPerformances(votedPEval, easyTask));*/
                    
                    /**
                     * Bayesan Network evaluation
                     */
                    BayesNet bayes = new BayesNet();
                    bayes.setOptions(weka.core.Utils.splitOptions("-D -Q weka.classifiers.bayes.net.search.local.SimulatedAnnealing "
                            + "-- -A 10.0 -U 10000 -D 0.999 -R 1 -S BAYES "
                            + "-E weka.classifiers.bayes.net.estimate.SimpleEstimator -- -A 0.5"));
                    bayes.buildClassifier(data);
                    Evaluation bayesEval = new Evaluation(data);
                    bayesEval.crossValidateModel(bayes, data, 10, new Random(10));
					
                    outputWriter.writeOnOutputFile("Bayes network");
                    outputWriter.writeOnOutputFile(evaluateClassificationPerformances(bayesEval, easyTask));
                }
            
            }
            catch(Exception exc) {
                System.out.println("Exception in performWekaClassificationTask");
                exc.printStackTrace();
            }
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
    private static String evaluateClassificationPerformances(Evaluation eval, boolean easyTask) {
        
        int numberOfClasses = 5;
        if (easyTask) {
            numberOfClasses = 3;
        }
        
        PerformanceEvaluator evaluator = new PerformanceEvaluator(eval, 
                numberOfClasses);
        
        return evaluator.evaluatePerformances();
        
    }
    
    private static void checkNumberOfIstances(Instances instances, boolean easy) {
        
        Integer[] classOccurences; 
        if (!easy) {
            classOccurences = new Integer[]{0, 0, 0, 0, 0};
        }
        else {
            classOccurences = new Integer[]{0, 0, 0};
        }
        
        for (int i = 0; i < instances.numInstances(); i++) {
            
            Attribute classInstance = instances.instance(i)
                    .attribute(instances.numAttributes() - 1);
            
            int classIndex = Integer.valueOf(classInstance.toString()) - 1;
            
            classOccurences[classIndex]++;
        }
        
        for (int i = 0; i < classOccurences.length; i++) {
            
            if (classOccurences[i] == 1) {
                //duplicate instance
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
            
            Attribute instanceClass = instances.instance(i)
                    .attribute(instances.numAttributes() - 1);
            
            if (Integer.valueOf(instanceClass.toString()) - 1 == index) {
                //copy this instance
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
            boolean easy) {
        /**
         * First of all we count the number of istances for each class
         */
        Integer[] occurrences;
        if (!easy) {
            occurrences = new Integer[]{0, 0, 0, 0, 0};
        }
        else {
            occurrences = new Integer[]{0, 0, 0};
        }
        
        for (int i = 0; i < instances.numInstances(); i++) {
            
            Attribute classInstance = instances.instance(i)
                    .attribute(instances.numAttributes() - 1);
            
            int classIndex = Integer.valueOf(classInstance.toString()) - 1;
            
            occurrences[classIndex]++;
        }
        
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
                    while (Math.abs(occurrences[i] - numberOfMaxInstances) > 10) {
                        SMOTE filter = new SMOTE();
                        filter.setInputFormat(instances);

                        double percentage = numberOfMaxInstances / occurrences[i];
                        if (percentage > 100) {
                            percentage = 100;
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

                        filter.setClassValue(String.valueOf(i));
                    
                        instances = Filter.useFilter(instances, filter);
                        
                        occurrences[i] += (occurrences[i] * (int) percentage / 100);
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
}