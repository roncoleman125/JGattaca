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
import gattaca.util.Config;
import java.util.ArrayList;

/**
 *
 * @author Ron.Coleman
 */
public class Dealer extends Player {
    protected ArrayList<Player> players; 
    protected Shoe shoe;
    protected Boolean interactive = true;
    
    public Dealer() {
        Config config = Config.getInstance("gattaca.json");
        
        this.shoe = new Shoe(config.numDecks);
        
        this.bankroll = 10000;
        
        if(config.numGames >= 1000)
            interactive = false;
    }
    
    public void go() {  
        // Let players know who dealer is to report earnings & loses
        players.stream().forEach((player) -> {
            player.makeDealer(this);
        });

        // Add dealer as last player's turn
        players.add(dealer = this);
        
        int numGames = Config.getInstance().numGames;
        
        for(int game=0; numGames < 1000; game++) {
                                
            log(">>>> GAME "+game+" STARTING");

            play();
            
            log(">>>> GAME "+game+" OVER");
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
                else if(cmd == Command.DOUBLE_DOWN && player.handSize() == 2) {
                    Card card = shoe.deal();
                    
                    player.hit(card);
                    
                    player.doubleDown();
                }
                    
            } while(cmd == Command.HIT && player.handValue <= 21);
            
            // If player broke, take them out of game
            if(player.handValue > 21) {
                player.loses();
                                    
                log(player + " LOSES!");
                
                numPlayers++;
            }
            
            // If only only one player left (the dealer!), close game
            if(numPlayers == 1)
                break;
        }
        
        closeGame(numPlayers);
        
        // Report dealer outcome
        log(dealer + "");
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
                    player.wins();
                    log(player + " WINS!");
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
                player.winsBlackjack();
                log(player + " BLACKJACK WINS!");
            }
            
            // Test player against dealer
            else if (playerValue <= 21) {
                // Player loses if handValue is less than dealer or dealer has blackjack
                if(playerValue < dealer.handValue || dealer.hasBlackjack()) {
                    player.loses();
                    log(player + " LOSES!");
                }
                
                else if(playerValue > dealer.handValue) {
                    player.wins();
                    log(player + " WINS!");
                }

                else {
                    player.pushes();
                    log(player + " PUSHES.") ;       
                }
            }

            // Player if hand over 21
            else {
                player.loses();
                log(player + " WINS!");
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
    
    public void wins(double amt) {
        bankroll += amt;
    }
    
    public void loses(double amt) {
        bankroll -= amt;
    }
    
    public void log(String msg) {
        if(interactive)
            System.out.println(msg);
    }
}
