package iwildsensestressanalyzer.weka;

import iwildsensestressanalyzer.utils.MathUtils;
import weka.classifiers.Evaluation;

/**
 *
 * @author Matteo Ciman
 */
public class PerformanceEvaluator {
    
    private final int numberOfClasses;
    private final Evaluation evaluation;
    
    public PerformanceEvaluator(Evaluation evaluation, int numberOfClasses) {
        this.evaluation = evaluation;
        this.numberOfClasses = numberOfClasses;
    }
    
    /**
     * Calculates the average per-class effectiveness of the classifier
     * @return the average accuracy
     */
    private double calculateAverageAccuracy() {
        
        double averageAccuracy = 0.0;
        
        for (int i = 0; i < numberOfClasses; i++) {
            
            double numerator = evaluation.numTruePositives(i) + evaluation.numTrueNegatives(i);
            double denominator = evaluation.numTruePositives(i) + 
                    evaluation.numFalseNegatives(i) + 
                    evaluation.numFalsePositives(i) + 
                    evaluation.numTrueNegatives(i);
            
            averageAccuracy += (numerator / denominator);
            
        }
        
        averageAccuracy /= ((double) numberOfClasses);
        
        return averageAccuracy;
    }
    
    /**
     * Calculates the average per-class classification error
     * @return the classification error
     */
    private double calculateErrorRate() {
        
        double errorRate = 0.0;
        
        for (int i = 0; i < numberOfClasses; i++) {
            
            double numerator = evaluation.numFalsePositives(i) + evaluation.numFalseNegatives(i);
            double denominator = evaluation.numTruePositives(i) + 
                    evaluation.numFalseNegatives(i) + 
                    evaluation.numFalsePositives(i) + 
                    evaluation.numTrueNegatives(i);
            
            errorRate += (numerator / denominator);
        }
        
        errorRate /= (double) numberOfClasses;
        
        return errorRate;
    }
    
    /**
     * Calculates the agreement of the data class labels with those of a 
     * classifier if calculated from sums of pre-text decisions
     * @return the precision with micro-averaging
     */
    private double calculateMicroPrecision() {
        
        double numerator = 0.0, denominator = 0.0;
        
        for (int i = 0; i < numberOfClasses; i++) {
            
            numerator += evaluation.numTruePositives(i);
            denominator += (evaluation.numTruePositives(i) + 
                    evaluation.numFalsePositives(i));
        }
        
        return numerator / denominator;
    }
    
    /**
     * Calculates the effectiveness of a classifier to identify class labels if 
     * calculated from sums of per-text decisions
     * @return the recall with micro-averaging
     */
    private double calculateMicroRecall() {
        
        double numerator = 0.0, denominator = 0.0;
        
        for (int i = 0; i < numberOfClasses; i++) {
            
            numerator += evaluation.numTruePositives(i);
            denominator += evaluation.numTruePositives(i) + 
                    evaluation.numFalseNegatives(i);
        }
        
        return numerator / denominator;
    }
    
    /**
     * An average per-class agreement of the data class labels with those of 
     * a classifier
     * @return the precision with macro averaging 
     */
    private double calculateMacroPrecision() {
        
        double macroAveraging = 0.0;
        
        for (int i = 0; i < numberOfClasses; i++) {
            
            double denominator = evaluation.numTruePositives(i) + 
                    evaluation.numFalsePositives(i);
            
            if (denominator != 0.0) {
            
                macroAveraging += evaluation.numTruePositives(i) / 
                    (denominator);
            }
        }
        
        macroAveraging /= numberOfClasses;
        
        return macroAveraging;
    }
    
    /**
     * Calculates the average per-class effectiveness of a classifier to identify
     * class labels
     * @return the recall with macro averaging
     */
    private double calculateMacroRecall() {
        
        double macroRecall = 0.0;
        for (int i = 0; i < numberOfClasses; i++) {
            
            macroRecall += evaluation.numTruePositives(i) / 
                    (evaluation.numTruePositives(i) + evaluation.numFalseNegatives(i));
        }
        
        macroRecall /= numberOfClasses;
        
        return macroRecall;
    }
    
    /**
     * Calculates the Fscore
     * @param beta the beta parameter of the f-score
     * @param precision the value of the precision
     * @param recall the value of the recall
     * @return the F-beta score
     */
    private double calculateFscore(double beta, double precision, 
            double recall) {
        
        return ((Math.pow(beta, 2) + 1) * precision * recall) / 
                (Math.pow(beta, 2) * precision + recall);
        
    }
    
    /**
     * Evaluates the performances of the classifier calculating all the 
     * metrics
     * @return a printable string with all the calculated evaluations 
     */
    public String evaluatePerformances() {
        
        String result = "Average Accuracy: " + 
                MathUtils.DECIMAL_FORMAT.format(calculateAverageAccuracy()) 
                + System.getProperty("line.separator") + 
                "Error rate: " 
                + MathUtils.DECIMAL_FORMAT.format(calculateErrorRate()) 
                + System.getProperty("line.separator");
        
        for (int i = 0; i < numberOfClasses; i++) {
            result += "Class " + (i+1) + ": {" + 
                    "TP: " + evaluation.numTruePositives(i) + "; " + 
                    "FP: " + evaluation.numFalsePositives(i) + "; " + 
                    "TN: " + evaluation.numTrueNegatives(i) + "; " + 
                    "FN: " + evaluation.numFalseNegatives(i) + "; " + 
                    "Precision: " + 
                    MathUtils.DECIMAL_FORMAT.format(evaluation.precision(i)) 
                    + "; Recall: " + 
                    MathUtils.DECIMAL_FORMAT.format(evaluation.recall(i)) +  
                    "; F-Measure: " + 
                    MathUtils.DECIMAL_FORMAT.format(evaluation.fMeasure(i))
                    + "}";
            
            result += System.getProperty("line.separator");
        }   
        
        double microPrecision = calculateMicroPrecision(), 
                microRecall = calculateMicroRecall(), 
                macroPrecision = calculateMacroPrecision(), 
                macroRecall = calculateMacroRecall();
        
        if (Double.isInfinite(microPrecision) || Double.isNaN(microPrecision)) {
            microPrecision = 0.0;
        }
        if (Double.isInfinite(microRecall) || Double.isNaN(microRecall)) {
            microRecall = 0.0;
        }
        if (Double.isInfinite(macroPrecision) || Double.isNaN(macroPrecision)) {
            macroPrecision = 0.0;
        }
        if (Double.isInfinite(macroRecall) || Double.isNaN(macroRecall)) {
            macroRecall = 0.0;
        }
        
        double microFscore = calculateFscore(1, microPrecision, microRecall), 
                macroFscore = calculateFscore(1, macroPrecision, macroRecall);
        
        result += "Micro: {Precision: " + MathUtils.DECIMAL_FORMAT.format(microPrecision) + 
                "; Recall: " + MathUtils.DECIMAL_FORMAT.format(microRecall) + 
                "; Fscore: " + MathUtils.DECIMAL_FORMAT.format(microFscore) + 
                " }" + System.getProperty("line.separator");
        
        result += "Macro {Precision: " + MathUtils.DECIMAL_FORMAT.format(macroPrecision) + 
                "; Recall: " + MathUtils.DECIMAL_FORMAT.format(macroRecall) + 
                "; Fscore: " + MathUtils.DECIMAL_FORMAT.format((macroFscore)) + "}";
        
        result += System.getProperty("line.separator");
        
        try {
        result += evaluation.toMatrixString();
        }
        catch(Exception exc) {
            System.out.println("Exception in writing confusion matrix");
        }
        
        return result;
    }
}
