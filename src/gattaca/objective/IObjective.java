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
 * GA objective function
 * @author Ron.Coleman
 */
public interface IObjective {
    /**
     * Gets the fitness of the individual.
     * @param individual Individual
     * @return Double
     */
    public double fitness(Individual individual);
    
    /**
     * Gets the chromosome length.
     * @return Integer
     */
    public int getChromosomeLength();
    
    /**
     * Gets the maximum knowable fitness which is possible only with
     * supervised learning.
     * @return Double
     */
    public double getMaxFitness();
    
    /**
     * Gets the alleles
     * @return String
     */
    public String getAlleles();
}
