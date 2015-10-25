/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gattaca.blackjack.player;

import gattaca.blackjack.util.Command;

/**
 *
 * @author Ron.Coleman
 */
public class FourRuleBot extends AbstractPlayer {   
    @Override
    public Command getCommand() {
        if(value() >= 17)
            return Command.STAY;
        
        else if(value() <= 10)
            return Command.HIT;
        
        else if(this.upCard.value >= 17)
            return Command.HIT;
                    
        return Command.STAY;
    }
}
