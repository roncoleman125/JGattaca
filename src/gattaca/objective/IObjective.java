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
public interface IObjective {
    public double fitness(Individual individual);
    public int getChromosomeLength();
    public double getMaxFitness();
}
