package gattaca;

import gattaca.objective.IObjective;
import gattaca.util.Config;

/**
 * This class implements the fitness calculator.
 * @author Ron.Coleman
 */
public class Fitness {

    private final static IObjective objective = Config.getInstance().objective;
    
    public static int getChromosomeLength() {
        return objective.getChromosomeLength();
    }
    
    /**
     * Calculate individual fitness.
     * @param individual Individual
     * @return Integer
     */
    public static double calculate(Individual individual) {
        return objective.fitness(individual);
    }
    
    /**
     * Gets the highest fitness
     * @return Integer
     */
    public static double getMaxFitness() {
        return objective.getMaxFitness();
    }
}