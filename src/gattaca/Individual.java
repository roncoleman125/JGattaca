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

import gattaca.util.Config;
import java.util.Random;

/**
 * This class implements an individual.
 * @author Ron.Coleman
 */
public class Individual {   
    static Random ran = new Random(0);
    
    protected final StringBuffer chromosome = new StringBuffer();

    protected double fitness = Double.NEGATIVE_INFINITY;
    
    protected final int chromosomeLength;

    /**
     * Constructor
     * @param chromosomeLength Chromosome length
     */
    public Individual(int chromosomeLength) {
        this.chromosomeLength = chromosomeLength;
        
        for(int i=0; i < chromosomeLength; i++)
            chromosome.append("G");
    }
    
    /**
     * Creates a randomized individual.
     */
    public void generateIndividual() {
        for (int i = 0; i < size(); i++) {
            char base = getRandomAllele();
            
            chromosome.setCharAt(i, base);
        }
    }
    
    /**
     * Gets gene at the index on chromosome.
     * @param index
     * @return char representing the gene
     */
    public char getGene(int index) {
        assert(index >= 0 && index < chromosomeLength);
        
        return chromosome.charAt(index);
    }

    /**
     * Sets gene at the index on chromosome.
     * @param index Index
     * @param gene 
     */
    public void setGene(int index, char gene) {
        assert(index >= 0 && index < chromosomeLength);
        
        chromosome.setCharAt(index, gene);
        fitness = Double.NEGATIVE_INFINITY;
    }

    /* Public methods */
    public int size() {
        return chromosomeLength;
    }
    
    /**
     * Gets entire chromosome
     * @return 
     */
    public String getChromosome() {
        return chromosome.toString();
    }

    /**
     * Gets the individual fitness
     * @return Double
     */
    public double getFitness() {
        if (fitness == Double.NEGATIVE_INFINITY) {
            fitness = Fitness.calculate(this);
        }
        return fitness;
    }
    
    public static char getRandomAllele() {
        String alleles = Config.getInstance().alleles;
        
        char gene = alleles.charAt(ran.nextInt(alleles.length()));
        
        return gene;
    }

    /**
     * Converts this individual to a string.
     * @return 
     */
    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < size(); i++) {
            geneString += getGene(i);
        }
        return geneString;
    }
}