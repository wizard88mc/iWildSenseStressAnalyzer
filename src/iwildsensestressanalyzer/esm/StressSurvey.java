package iwildsensestressanalyzer.esm;

/**
 *
 * This class holds the answers provided by the participant to the ESM 
 * survey about Valence, Energy and Stress among the day
 * 
 * @author Matteo Ciman
 * @version 0.1: First draft of the class with the main constructor
 */
public class StressSurvey {
    
    private final long timestamp;
    private final int valence;
    private final int energy;
    private final int stress;
    
    public StressSurvey(String lineWithAnswer) {
        
        String[] answerElements = lineWithAnswer.split(",");
        /**
         * [0]: not necessary timestamp
         * [1]: timestamp of the answer 
         * [2]: not necessary timestamp
         * [3]: stress_survey
         * [4]: answer provided: energy-valence-stress
         */
        timestamp = Long.valueOf(answerElements[1]);
        
        String[] elementsSurvey = answerElements[4].split("-");
        /**
         * [0]: energy
         * [1]: valence
         * [2]: stress
         */
        valence = Integer.valueOf(elementsSurvey[0]);
        energy = Integer.valueOf(elementsSurvey[1]);
        stress = Integer.valueOf(elementsSurvey[2]);
    }
    
    /**
     * Returns the timestamp when the answer is provided
     * @return the timestamp
     */
    public long getTimestamp() {
        return this.timestamp;
    }
    
    /**
     * Returns the energy answer
     * @return the energy answer
     */
    public int getEnergy() {
        return this.energy;
    }
    
    /**
     * Returns the valence answer
     * @return the valence answer
     */
    public int getValence() {
        return this.valence;
    }
    
    /**
     * Returns the stress answer
     * @return the stress answer
     */
    public int getStress() {
        return this.stress;
    }
}
