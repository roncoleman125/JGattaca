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

import java.util.Random;

/**
 * This class implements the low level GA.
 * @author Ron.Coleman
 */
public class Algorithm {
    /* GA parameters */
    private static final double UNIFORM_RATE = 0.5;
    private static final double MUTATION_RATE = 0.015;
    private static final int TOURNAMENT_SIZE = 5;
    private static final boolean ELITIST = true;

    static final Random ran = new Random(0);
       
    /**
     * Evolves a population.
     * @param pop Population
     * @return Evolved population
     */
    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.size(), false);

        // Keep our best individual
        if (ELITIST) {
            newPopulation.saveIndividual(0, pop.getFittest());
        }

        // Crossover population
        int elitismOffset;
        if (ELITIST) {
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }
        
        // Loop over the population size and create new individuals with crossover
        for (int i = elitismOffset; i < pop.size(); i++) {
            Individual mother = tournamentSelection(pop);
            
            Individual father = tournamentSelection(pop);
            
            Individual child = crossover(mother, father);
            
            newPopulation.saveIndividual(i, child);
        }

        // Mutate population
        for (int i = elitismOffset; i < newPopulation.size(); i++) {
            mutate(newPopulation.getIndividual(i));
        }

        return newPopulation;
    }

    // Crossover individuals
    private static Individual crossover(Individual indiv1, Individual indiv2) {
        Individual newSol = new Individual(Fitness.getChromosomeLength());
        // Loop through genes
        for (int i = 0; i < indiv1.size(); i++) {
            // Crossover
            if (ran.nextDouble() <= UNIFORM_RATE) {
                newSol.setGene(i, indiv1.getGene(i));
            } else {
                newSol.setGene(i, indiv2.getGene(i));
            }
        }
        return newSol;
    }

    /**
     * Mutates an individual
     * @param individual 
     */
    private static void mutate(Individual individual) {
        // Loop through genes
        for (int i = 0; i < individual.size(); i++) {
            if (ran.nextDouble() <= MUTATION_RATE) {
                // Create random gene
                char base = Individual.getRandomAllele();
                individual.setGene(i, base);
            }
        }
    }

    /**
     * Selects an individual to cross over
     * @param pop Population
     * @return Individual
     */
    private static Individual tournamentSelection(Population pop) {
        // Create a tournament population
        Population tournament = new Population(TOURNAMENT_SIZE, false);
        
        // For each place in the tournament get a random individual
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            int randomId = ran.nextInt(pop.size());
            
            tournament.saveIndividual(i, pop.getIndividual(randomId));
        }

        // Get the fittest
        Individual fittest = tournament.getFittest();
        return fittest;
    }
}