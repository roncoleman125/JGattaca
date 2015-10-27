/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gattaca.objective;

import gattaca.Individual;
import gattaca.blackjack.game.Game;
import gattaca.blackjack.player.Player;
import gattaca.util.Config;
import gattaca.blackjack.player.GASimpleBasicStrategyPlayer;

/**
 *
 * @author Ron.Coleman
 */
public class GASimpleBasicStrategy implements IObjective {

    @Override
    public double fitness(Individual individual) {
        Game game = new Game();
        
        for(Player player: Config.getInstance().players) {
            if(player instanceof GASimpleBasicStrategyPlayer) {
                String chromosome = individual.getChromosome();
                
                ((GASimpleBasicStrategyPlayer)player).setChromosome(chromosome);
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
    
}