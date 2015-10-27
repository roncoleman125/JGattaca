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
package gattaca.objective;

import gattaca.Individual;

/**
 * This class is used where we know the casual Blackjack basic strategy.
 * @author Ron.Coleman
 */
public class SupervisedCasualBasicStrategy implements IObjective {
    // Indices in the solution have the following meaning:
    // 0 - My hand >= 17
    // 1 - Dealer up card >= 7
    // 2 - My hand <= 10
    // 3 - My hand == 11 with two cards
    // 4 - STAY, if none of the above hold
    protected String solution = "SHHDS";
    
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
    
    /**
     * Gets the alleles
     * @return String
     */
    @Override
    public String getAlleles() {
        return "SHD";
    }
}
