/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gattaca.convergence;

import gattaca.Fitness;
import gattaca.Population;

/**
 *
 * @author Ron.Coleman
 */
public class Exact extends Terminator {
    @Override
    public Boolean isConverged(Population p) {
        return p.getFittest().getFitness() == Fitness.getMaxFitness();
    }
}
