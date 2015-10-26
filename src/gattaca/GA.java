package gattaca;

import gattaca.convergence.Terminator;
import gattaca.util.Config;

/**
 * This class implements the high-level GA.
 * @author Ron.Coleman
 */
public class GA {       
    public static void main(String[] args) {      
        // Create an initial population
        Population population = new Population(50, true);
        
        // Evolve our population until we reach an optimum solution
        int generationCount = 0;

        while(!isConverged(population)) {           
            generationCount++;
            
            double entropy = population.getEntropy();
            
            double mlr = population.getMaxLikenessRatio();
            
            System.out.printf("Generation: %2d fittest: %5.1f entrypy: %3.1f bits  mlr: %3.2f\n",generationCount,population.getFittest().getFitness(),entropy,mlr);
            population = Algorithm.evolvePopulation(population);
        }
        
        double entropy = population.getEntropy();
        double mlr = population.getMaxLikenessRatio(); 
        
        System.out.println("Converged!");
        System.out.println("Generations: " + generationCount);
        System.out.printf("Entropy: %4.1f\n",entropy);
        System.out.printf("MLR: %5.2f\n",mlr);
        
        Individual fittest = population.getFittest();
        System.out.println("Genes (len = "+fittest.getChromosome().length()+"):");
        System.out.println(fittest);
    }
    
    private static Boolean isConverged(Population p) {      
        return Config.getInstance().terminator.isConverged(p);
    }    
}
