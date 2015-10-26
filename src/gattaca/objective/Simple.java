/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gattaca.objective;

import gattaca.Individual;

/**
 *
 * @author Ron.Coleman
 */
public class Simple implements IObjective {
    protected String solution = "1111000000000000000000000000000000000000000000000000000000001111";
    @Override
    public double fitness(Individual individual) {
        int count = 0;
        
        // Loop through our individuals genes and compare them to our cadidates
        for (int i = 0; i < individual.size() && i < solution.length(); i++) {
            if (individual.getGene(i) == solution.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int getChromosomeLength() {
        return solution.length();
    }

    @Override
    public double getMaxFitness() {
        return solution.length();
    }
    
}
