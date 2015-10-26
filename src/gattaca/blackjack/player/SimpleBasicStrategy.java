/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gattaca.blackjack.player;

import gattaca.blackjack.util.Action;

/**
 *
 * @author Ron.Coleman
 */
public class SimpleBasicStrategy extends Player {     
    @Override
    public Action getAction() {
        if(handValue >= 17)
            return Action.STAY;
        
        else if(upCard.value() >= 7)
            return Action.HIT;
        
        else if(handValue <= 10)
            return Action.HIT;

        else if(handValue == 11 && hand.size() == 2)
            return Action.DOUBLE_DOWN;
        
        return Action.STAY;
    }
}
