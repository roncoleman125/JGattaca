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
package gattaca.blackjack.player;

import gattaca.blackjack.game.Action;
import java.util.HashMap;

/**
 *
 * @author Ron.Coleman
 */
public class GACasualBasicStrategyPlayer extends Player {
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
        else if(handValue <= 10) {
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
        
        String g = chromosome.substring(n,n+1);
        return g;
    }
}
