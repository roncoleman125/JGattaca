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
        if(handValue >= 17)
            return Command.STAY;
        
        else if(handValue <= 10 || upCard.value() >= 7)
            return Command.HIT;
        
        else if(handValue == 11 && hand.size() == 2)
            return Command.DOUBLE_DOWN;
        
        return Command.STAY;
    }
    
    @Override
    public void dealt(Player player, Card card) {
        if(player instanceof Dealer)
            this.upCard = card;
    }
}
