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
public class Likeness extends Terminator {
    public final Double TERMINAL_LIKENESS = 0.70;
    
    @Override
    public Boolean isConverged(Population p) {
        return p.getMaxLikenessRatio() >= TERMINAL_LIKENESS;
    }
}
