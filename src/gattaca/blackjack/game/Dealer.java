/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gattaca.blackjack.game;

import gattaca.blackjack.card.Card;
import gattaca.blackjack.card.Shoe;
import gattaca.blackjack.util.Command;
import gattaca.blackjack.player.Player;
import gattaca.blackjack.util.Config;
import java.util.ArrayList;

/**
 *
 * @author Ron.Coleman
 */
public class Dealer extends Player {
    protected ArrayList<Player> players; 
    protected Shoe shoe;
    protected Player dealer;
    
    public Dealer() {
        Config config = Config.getInstance("gattaca.json");
        
        this.players = config.getPlayers(); 
        
        int numDecks = config.getNumDecks();
        
        this.shoe = new Shoe(numDecks);
        this.bankroll = 10000;
    }
    
    public void go() {  
        // Add dealer as last player to go
        players.add(dealer = this);
        
        int numGames = Config.getInstance().getNumGames();
        
        for(int game=0; game < 2; game++) {
                                
            if (Config.getInstance().isDebugging())
                System.out.println(">>>> GAME "+game+" STARTING");

            play();
            
            if (Config.getInstance().isDebugging())
                System.out.println(">>>> GAME "+game+" OVER");
        }
    }
    
    protected void play() {
        openGame();
        
        // Get number of players
        int numPlayers = players.size();
        
        // Play up to the dealer in which we'll don something different
        for(int k=0; k < players.size()-1; k++) {
            Player player = players.get(k);
            
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
                                    
                if (Config.getInstance().isDebugging())
                    System.out.println(player + " LOSES!");
                
                numPlayers--;
            }
            
            // If only
            if(numPlayers == 1)
                break;
        }
        
        closeGame(numPlayers);
        
        if (Config.getInstance().isDebugging())
            System.out.println(dealer + "");
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
        if(dealer.handValue > 21) {
            for(int k=0; k < players.size()-1; k++) {
                Player player = players.get(k);
                
                if(player.handValue <= 21) {
                    player.wins(1);
                    dealer.loses(1);
                    
                    if(Config.getInstance().isDebugging())
                        System.out.println(player+" WINS!");
                }
            }
            
            return;
        }
            
        // Else dealer did not break, so find who won, who lost
        for(int k=0; k < players.size()-1; k++) {
            Player player = players.get(k);

            int playerValue = player.handValue;
            
            // Skip players already broke
            if(playerValue > 21)
                continue;

            // Player wins with Blackjack which pays 2:1
            if(player.hasBlackjack()) {
                player.wins(1.5);
                dealer.loses(1.5);
 
                if (Config.getInstance().isDebugging())
                    System.out.println(player + " BLACKJACK WINS!");
            }
            
            // Test player against dealer
            else if (playerValue <= 21) {
                // Player loses if handValue is less than dealer or dealer has blackjack
                if(playerValue < dealer.handValue || dealer.hasBlackjack()) {
                    player.loses(1);
                    dealer.wins(1);
                                        
                    if(Config.getInstance().isDebugging())
                        System.out.println(player+" LOSES!");
                }
                
                else if(playerValue > dealer.handValue) {
                    player.wins(1);
                    dealer.loses(1);
                                        
                    if(Config.getInstance().isDebugging())
                        System.out.println(player+" WINS!");
                }

                else {
                    player.pushes();
                                        
                    if(Config.getInstance().isDebugging())
                        System.out.println(player+" PUSHES.");
                }
            }

            // Player if hand over 21
            else {
                player.loses(1);

                if (Config.getInstance().isDebugging()) {
                    System.out.println(player + " WINS!");
                }
            }
        }
    }
    
    protected void dealInitial() {        
        // First round
        for(Player player: players) {
            Card card = shoe.deal();
            
            player.hit(card);
            
            distribute(player,card,true);
        }
        
        // Second round
        for (Player player : players) {
            Card card = shoe.deal();
            
            player.hit(card);
            
            distribute(player,card,false);
        }
    }
    
    protected void distribute(Player player, Card card, Boolean mine) {
        for(Player p: players) {
            if(!mine && player instanceof Dealer)
                continue;
            
            p.dealt(player, card);
        }
    }

    public void openGame() {
        for(Player player: players) {
            player.reset();
        }
        
        dealInitial();
    }
    
    @Override
    public void reset() {
        Boolean yes = shoe.reshuffle();
        
        for(Player player: players)
            player.reshuffling(yes);
        
        hand.clear();
    }
    
    @Override
    public Command getCommand() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
