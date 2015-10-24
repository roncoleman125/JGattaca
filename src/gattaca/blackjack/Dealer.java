/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gattaca.blackjack;

import java.util.ArrayList;

/**
 *
 * @author Ron.Coleman
 */
public class Dealer extends AbstractPlayer {
    protected ArrayList<AbstractPlayer> players = new ArrayList<>(); 
    protected Shoe shoe = new Shoe(6);
    
    public Dealer() {
        
    }
    
    public void add(AbstractPlayer player) {
        players.add(player);
    }
    
    public void go() {
        openGame();
        
        dealInitial();
        
        play();
    }
    
    protected void play() {
        openGame();
        
        // Get number of players not including dealer
        int numPlayers = players.size() - 1;
        
        for(AbstractPlayer player: players) {
            if(player instanceof Dealer)
                break;
            
            Command cmd;
            
            do {
                cmd = player.getCommand();
                
                if(cmd == Command.HIT) {
                    Card card = shoe.deal();
                    
                    player.hit(card);
                }
                    
            } while(cmd == Command.HIT && player.value() <= 21);
            
            if(player.value() > 21) {
                player.lose(1);
                
                numPlayers--;
            }
            
            if(numPlayers == 0)
                break;
        }
        
        closeGame(numPlayers);
    }
    
    protected void closeGame(int numPlayers) {
        if(numPlayers == 1)
            return;
        
        // Reveal the hole card
        distribute(this, hand.get(1), true);

        // Dealer must hit until 17 or greater
        while (this.value() < 16) {
            Card card = shoe.deal();

            hit(card);

            distribute(this, card, true);
        }

        // Close out the bets on the table
        int dealerValue = value();

        for (AbstractPlayer player : players) {
            int playerValue = player.value();

            // Player wins
            if (playerValue <= 21 && playerValue > dealerValue) {
                player.win(1);
            } // Player loses
            else if (playerValue <= 21) {
                player.lose(1);
            } // Push
            else if (playerValue == dealerValue)
                    ;
        }
    }
    protected void dealInitial() {
        // Dealer last to go
        add(this);
        
        // First round
        for(AbstractPlayer player: players) {
            Card card = shoe.deal();
            player.hit(card);
            
            for(AbstractPlayer p: players) {
                p.dealt(player, card);
            }
        }
        
        // Second round
        for (AbstractPlayer player : players) {
            Card card = shoe.deal();
            player.hit(card);
            
            distribute(player,card,false);
        }
    }
    
    protected void distribute(AbstractPlayer player, Card card, Boolean meToo) {
        for(AbstractPlayer p: players) {
            if(!meToo)
                continue;
            p.dealt(player, card);
        }
    }

    public void openGame() {
        for(AbstractPlayer player: players) {
            player.reset();
        }
    }
    
    @Override
    public void reset() {
        shoe.reset();
        hand.clear();
    }
    
    @Override
    public Command getCommand() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
