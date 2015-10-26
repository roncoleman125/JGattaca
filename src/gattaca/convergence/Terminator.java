/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gattaca.convergence;

import gattaca.Population;

/**
 *
 * @author Ron.Coleman
 */
abstract public class Terminator {
    public abstract Boolean isConverged(Population p);
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName().toUpperCase();
    }
}
