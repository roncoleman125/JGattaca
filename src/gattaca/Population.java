package gattaca;

import java.util.HashMap;

/**
 * This class contains the gene pool of individuals.
 * @author Ron.Coleman
 */
public class Population {
    // ln(2) -- for converting to log2(2)
    private final double LN_2 = 0.693147;
    
    private final Individual[] genePool;
    
    private final int popSize;

    /**
     * Constructor
     * @param popSize Population (gene pool) size
     * @param initialize Initializes gene pool with random individuals.
     */
    public Population(int popSize, boolean initialize) {
        this.popSize = popSize;
        
        // Create empty population (eg, no individuals, yet)
        this.genePool = new Individual[popSize];
        
        // Initialise population, otherwise, saveIndividual will need to be invoked.
        if (initialize) {
            // Loop and create individuals
            for (int i = 0; i < genePool.length; i++) {
                Individual newIndividual = new Individual(Fitness.getChromosomeLength());
                newIndividual.generateIndividual();
                genePool[i] = newIndividual;
            }
        }
    }

    /**
     * Gets an individual
     * @param index Index
     * @return Individual
     */
    public Individual getIndividual(int index) {
        assert(index < popSize);
        
        return genePool[index];
    }

    /**
     * Gets fittest individual in gene pool.
     * @return Individual
     */
    public Individual getFittest() {
        Individual fittest = genePool[0];
        
        // Loop through individuals to find fittest
        for (int i = 0; i < this.size(); i++) {
            if (fittest.getFitness() <= getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }


    /**
     * Gets population size.
     * @return Integer
     */
    public int size() {
        return popSize;
    }

    /**
     * Saves an individual
     * @param index Index in gene pool
     * @param indiv Individual to save
     */
    public void saveIndividual(int index, Individual indiv) {
        genePool[index] = indiv;
    }
    
    /**
     * Gets the entropy of the population in bits.
     * @return Double
     */
    public double getEntropy() {
        HashMap<String,Integer> counts = new HashMap<>();
        
        for(Individual indiv: genePool) {
            String key = indiv.getChromosome();
            int count = counts.getOrDefault(key, 0) + 1;
            counts.put(key, count);
        }
        
        double entropy = 0.0;
        
        for(String key: counts.keySet()) {
            int count = counts.get(key);
            
            double prob = (double) count / (double) genePool.length;
            
            // Measure entropy in bits
            entropy += -prob * Math.log(prob) / LN_2;
        }
        
        return entropy;
    }
    
    /**
     * Gets the maximum likeness as a fraction.
     * @return double
     */
    public double getMaxLikenessRatio() {
       HashMap<String,Integer> counts = new HashMap<>();
        
        for(Individual indiv: genePool) {
            String key = indiv.getChromosome();
            int count = counts.getOrDefault(key, 0) + 1;
            counts.put(key, count);
        }
        
        int maxCount = -1;
        
        for(String key: counts.keySet()) {
            int count = counts.get(key);
            
            if(count > maxCount)
                maxCount = count;
        }
        
        double ratio = maxCount / (double)genePool.length;
        return ratio;
    }
}