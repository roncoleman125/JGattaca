/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gattaca.blackjack.player;

import gattaca.blackjack.util.Action;
import java.util.HashMap;

/**
 *
 * @author Ron.Coleman
 */
public class GASimpleBasicStrategyPlayer extends Player {
    protected String chromosome;
    
    protected static final HashMap<String,Action> geneToAction = new HashMap<>();
    
    static {
        geneToAction.put("H", Action.NONE);
        geneToAction.put("S",Action.STAY);
        geneToAction.put("D", Action.DOUBLE_DOWN);       
    }
    
    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }
    
    @Override
    public Action getAction() {
        if(handValue >= 17) {
            Action action = geneToAction.get(gene(0));
            return action;
        }
        else if(handValue <= 10 || upCard.value() >= 7) {
            Action action = geneToAction.get(gene(1));
            return action;
        }
        
        else if(upCard.value() >= 7) {
            Action action = geneToAction.get(gene(2));
            return action;           
        }
        
        else if(handValue == 11 && hand.size() == 2) {
            Action action = geneToAction.get(gene(3));
            return action; 
        }
        
        Action action = geneToAction.get(gene(4));
        return action; 
    } 
    
    protected String gene(int n) {
        assert(n >= 0 && n < chromosome.length());
        
        return chromosome.substring(n,n);
    }
}
