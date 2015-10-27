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
import gattaca.blackjack.game.Blackjack;
import gattaca.blackjack.player.Player;
import gattaca.util.Config;
import gattaca.blackjack.player.GACasualBasicStrategyPlayer;

/**
 *
 * @author Ron.Coleman
 */
public class GACasualBasicStrategy implements IObjective {

    @Override
    public double fitness(Individual individual) {
        Blackjack game = new Blackjack();
        
        for(Player player: Config.getInstance().players) {
            if(player instanceof GACasualBasicStrategyPlayer) {
                String chromosome = individual.getChromosome();
                
                ((GACasualBasicStrategyPlayer)player).setChromosome(chromosome);
            }
        }
        
        game.start();
        
        double earnings = game.getEarnings();
        
        return earnings;
    }

    /**
     * GA basic strategy player has five decisions to check driven by its chromosome.
     * @return Integer
     */
    @Override
    public int getChromosomeLength() {
        return 5;
    }

    @Override
    public double getMaxFitness() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
