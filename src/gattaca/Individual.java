package gattaca;

import java.util.Random;

/**
 * This class implements an individual.
 * @author Ron.Coleman
 */
public class Individual {
    final static String ALLELES = "01";
    
//    final static int CHROMOSOME_LENGTH = 64;
    
    static Random ran = new Random(0);
    
    protected final StringBuffer chromosome = new StringBuffer();

    protected double fitness = 0;
    
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
        fitness = 0;
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
        if (fitness == 0) {
            fitness = Fitness.calculate(this);
        }
        return fitness;
    }
    
    public static char getRandomAllele() {
        char gene = ALLELES.charAt(ran.nextInt(ALLELES.length()));
        
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