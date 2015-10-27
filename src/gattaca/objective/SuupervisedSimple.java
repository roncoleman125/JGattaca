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
 * This class is a simple demo of what the GA can do in a supervised manner.
 * @author Ron.Coleman
 */
public class SuupervisedSimple implements IObjective {
    protected String solution = "1111000000000000000000000000000000000000000000000000000000001111";
    
    /**
     * Gets the fitness of the individual.
     * @param individual Individual
     * @return Double
     */
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

    /**
     * Gets the chromosome length.
     * @return Integer
     */
    @Override
    public int getChromosomeLength() {
        return solution.length();
    }

    /**
     * Gets the maximum knowable fitness which is possible only with
     * supervised learning.
     * @return Double
     */
    @Override
    public double getMaxFitness() {
        return solution.length();
    }
}
