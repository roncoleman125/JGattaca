package gattaca;

/**
 * This interface defines when the GA terminates (i.e., "converges").
 * @author Ron.Coleman
 */
interface Terminator {
    public Boolean terminate(Population p);
}

/**
 * This class implements the high-level GA.
 * @author Ron.Coleman
 */
public class GA {    
    // Terminates when entropy falls below this level in bits.
    private final static Double TERMINAL_ENTROPY = 2.0;
    private final static Double TERMINAL_LIKENESS = 0.70;
    
    // Possible onvergence conditions
    private final static Terminator USE_ENTROPY = (Population p) -> p.getEntropy() < TERMINAL_ENTROPY;
    private final static Terminator USE_FITNESS = (Population p) -> p.getFittest().getFitness() >= Fitness.getMaxFitness();
    private final static Terminator USE_LIKENESS =(Population p) -> p.getMaxLikenessRatio() >= 0.70;
    
    // Actual convergence criteria
    private final static Terminator TERMINAL = USE_LIKENESS;
    
    public static void main(String[] args) {      
        // Set a candidate solution
        String solution = "1111000000000000000000000000000000000000000000000000000000001111";
        
        Fitness.setSolution(solution);

        // Create an initial population
        Population population = new Population(50, true);
        
        // Evolve our population until we reach an optimum solution
        int generationCount = 0;
        double entropy = 0;
//        while (true) {
        while(!isConverged(population,TERMINAL)) {           
            generationCount++;
            
            entropy = population.getEntropy();
            if(entropy < 2.0)
                break;
            
            double mlr = population.getMaxLikenessRatio();
            
            System.out.printf("Generation: %2d fittest: %5.1f entrypy: %3.1f bits  mlr: %3.2f\n",generationCount,population.getFittest().getFitness(),entropy,mlr);
//            System.out.println("Generation: " + generationCount + " Fittest: " + population.getFittest().getFitness()+" Entropy: "+entropy);
            population = Algorithm.evolvePopulation(population);
        }
        System.out.println("Converged!");
        System.out.println("Generations: " + generationCount);
        System.out.printf("Entropy: %4.1f\n",entropy);
        System.out.printf("MLR: %5.2f\n",population.getMaxLikenessRatio());
        System.out.println("Genes:");
        System.out.println(population.getFittest());
    }
    
    private static Boolean isConverged(Population p,Terminator t) {
        return t.terminate(p);
    }    
}
