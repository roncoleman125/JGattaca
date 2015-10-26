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
public class Entropy extends Terminator {
    public final static Double TERMINAL_ENTROPY = 2.0;

    @Override
    public Boolean isConverged(Population p) {
        return p.getEntropy() < TERMINAL_ENTROPY;
    }   
}
