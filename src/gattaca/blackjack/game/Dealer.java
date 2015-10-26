/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gattaca.blackjack.game;

import gattaca.blackjack.card.Card;
import gattaca.blackjack.card.Shoe;
import gattaca.blackjack.util.Command;
import gattaca.blackjack.player.AbstractPlayer;
import gattaca.blackjack.util.Config;
import java.util.ArrayList;

/**
 *
 * @author Ron.Coleman
 */
public class Dealer extends AbstractPlayer {
    protected ArrayList<AbstractPlayer> players; 
    protected Shoe shoe;
    protected AbstractPlayer dealer;
    
    public Dealer() {
        Config config = Config.getInstance("gattaca.json");
        
        this.players = config.getPlayers(); 
        
        int numDecks = config.getNumDecks();
        
        this.shoe = new Shoe(numDecks);
    }
    
    public void go() {  
        // Add dealer as last player to go
        players.add(dealer = this);
        
        int numGames = Config.getInstance().getNumGames();
        
        for(int i=0; i < numGames; i++)
            play();
    }
    
    protected void play() {
        openGame();
        
        // Get number of players
        int numPlayers = players.size();
        
        for(AbstractPlayer player: players) {
            // Play up to the dealer in which we'll don something different
            if(player instanceof Dealer)
                break;
            
            Command cmd;
            
            do {
                cmd = player.getCommand();
                
                if(cmd == Command.HIT) {
                    Card card = shoe.deal();
                    
                    player.hit(card);
                }
                    
            } while(cmd == Command.HIT && player.handValue <= 21);
            
            // If player broke, take them out of game
            if(player.handValue > 21) {
                player.loses(1);
                
                numPlayers--;
            }
            
            // If only
            if(numPlayers == 1)
                break;
        }
        
        closeGame(numPlayers);
    }
    
    protected void closeGame(int numPlayers) {
        // If dealer only one left, then done.
        if(numPlayers == 1)
            return;
        
        // Reveal dealer hole card
        distribute(dealer, hand.get(1), true);

        // Dealer must hit until 17 or greater
        while (dealer.handValue < 16) {
            Card card = shoe.deal();

            hit(card);

            distribute(dealer, card, true);
        }

        // If dealer broke, pay remaining players
        int dealerValue = dealer.handValue;
        
        if(dealerValue > 21) {
            for(AbstractPlayer player: players)
                if(!(player instanceof Dealer) && player.handValue <= 21)
                    player.wins(1);
            
            return;
        }
            
        // Else dealer did not break, so find who won, who lost
        for (AbstractPlayer player : players) {
            int playerValue = player.handValue;
            
            // Skip players already broke
            if(playerValue > 21)
                continue;

            // Player wins with Blackjack which pays 2:1
            if(player.hasBlackjack())
                player.wins(1.5);
            
            // Test player against dealer
            else if (playerValue <= 21) {
                // Player loses if handValue is less than dealer or dealer has blackjack
                if(playerValue < dealerValue || dealer.hasBlackjack())
                    player.loses(1);
                
                else if(playerValue > dealerValue)
                    player.wins(1);

                else // Push
                    player.pushes();
            }

            // Player if hand over 21
            else
                player.loses(1);
        }
    }
    protected void dealInitial() {        
        // First round
        for(AbstractPlayer player: players) {
            Card card = shoe.deal();
            
            player.hit(card);
            
            distribute(player,card,true);
        }
        
        // Second round
        for (AbstractPlayer player : players) {
            Card card = shoe.deal();
            
            player.hit(card);
            
            distribute(player,card,false);
        }
    }
    
    protected void distribute(AbstractPlayer player, Card card, Boolean mine) {
        for(AbstractPlayer p: players) {
            if(!mine && player instanceof Dealer)
                continue;
            
            p.dealt(player, card);
        }
    }

    public void openGame() {
        for(AbstractPlayer player: players) {
            player.reset();
        }
        
        dealInitial();
    }
    
    @Override
    public void reset() {
        Boolean yes = shoe.reshuffle();
        
        for(AbstractPlayer player: players)
            player.reshuffling(yes);
        
        hand.clear();
    }
    
    @Override
    public Command getCommand() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
