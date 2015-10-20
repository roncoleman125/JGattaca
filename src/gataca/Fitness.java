package gataca;

/**
 * This class implements the fitness calculator.
 * @author Ron.Coleman
 */
public class Fitness {

    static String solution = null;

    /**
     * Sets the desired solution
     * @param solution Supervised solution
     */
    public static void setSolution(String solution) {
        assert(solution.length() == Individual.CHROMOSOME_LENGTH);
        
        Fitness.solution = solution;
    }

    /**
     * Calculate individual fitness.
     * @param individual Individual
     * @return Integer
     */
    static double calculate(Individual individual) {
        int fitness = 0;
        // Loop through our individuals genes and compare them to our cadidates
        for (int i = 0; i < individual.size() && i < solution.length(); i++) {
            if (individual.getGene(i) == solution.charAt(i)) {
                fitness++;
            }
        }
        return fitness;
    }
    
    /**
     * Gets the highest fitness
     * @return Integer
     */
    static double getMaxFitness() {
        int maxFitness = solution.length();
        return maxFitness;
    }
}