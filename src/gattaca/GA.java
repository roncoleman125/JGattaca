/*
 Copyright (c) 2015 Ron Coleman
 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files (the
 "Software"), to deal in the Software without restriction, including
 without limitation the rights to use, copy, modify, merge, publish,
 distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to
 the following conditions:
 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package gattaca;

import gattaca.util.Config;

/**
 * This class implements the high-level GA.
 * @author Ron.Coleman
 */
public class GA {       
    public static void main(String[] args) {      
        // Create an initial population
        int popSize = Config.getInstance("gattaca.json").popSize;
        
        Population population = new Population(popSize, true);
        
        // Evolve our population until we reach an optimum solution
        int generationCount = 0;

        while(!isConverged(population)) {           
            generationCount++;
            
            double entropy = population.getEntropy();
            
            double mlr = population.getMaxLikenessRatio();
            
            Individual fittest = population.getFittest();
            
            double fitness = fittest.getFitness();
            
            System.out.printf("Generation: %2d fittest: %5.1f entrypy: %3.1f bits  mlr: %3.2f %s\n",generationCount,fitness,entropy,mlr,fittest);
                        
            population = Algorithm.evolvePopulation(population);
        }
        
        double entropy = population.getEntropy();
        double mlr = population.getMaxLikenessRatio(); 
        
        System.out.println("Converged!");
        System.out.println("Generations: " + generationCount);
        System.out.printf("Entropy: %4.1f\n",entropy);
        System.out.printf("MLR: %5.2f\n",mlr);
        
        Individual fittest = population.getFittest();
        System.out.print("Genes (len = "+fittest.getChromosome().length()+"): ");
        System.out.println(fittest);
    }
    
    private static Boolean isConverged(Population p) {      
        return Config.getInstance().terminator.isConverged(p);
    }    
}
