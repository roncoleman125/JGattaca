/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gattaca.blackjack.player;

import gattaca.blackjack.card.Card;
import gattaca.blackjack.game.Dealer;
import gattaca.blackjack.util.Command;

/**
 *
 * @author Ron.Coleman
 */
public class FourRulePlayer extends Player {     
    @Override
    public Command getCommand() {
        if(this.handValue >= 17)
            return Command.STAY;
        
        else if(this.handValue <= 10)
            return Command.HIT;
        
        else if(this.upCard.value() >= 10)
            return Command.HIT;
                    
        return Command.STAY;
    }
    
    @Override
    public void dealt(Player player, Card card) {
        if(player instanceof Dealer)
            this.upCard = card;
    }
}
