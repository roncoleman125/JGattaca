/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gattaca.blackjack.player;

import gattaca.blackjack.card.Card;
import gattaca.blackjack.card.Shoe;
import gattaca.blackjack.util.Action;
import gattaca.util.Config;
import java.util.ArrayList;

/**
 *
 * @author Ron.Coleman
 */
public class Dealer extends Player {
    protected static final double INTIAL_CREDIT = 10000;
    
    protected ArrayList<Player> players = Config.getInstance().players; 
    protected Shoe shoe;
    protected Boolean interactive = true;
    
    public Dealer() {
        Config config = Config.getInstance("gattaca.json");
        
        this.shoe = new Shoe(config.numDecks);
        
        this.bankroll = INTIAL_CREDIT;
        
        if(config.numGames >= 1000)
            interactive = false;
    }
    
    public double getBankroll(int k) {
        assert(k >= 0 && k < players.size());
        
        return players.get(k).getBankroll();
    }
    
    public void go() {  
        log(">>>> ENTERING CASINO");
        
        // Let players know who dealer is to report earnings & loses
        players.stream().forEach((player) -> {
            player.makeDealer(this);
            player.fund();
        });
        
        dealer = this;
        
        int numGames = Config.getInstance().numGames;

        for(int game=0; game < numGames; game++) {
                                
            log(">>>> GAME "+game+" STARTING");

            play();
            
            log(">>>> GAME "+game+" OVER");
        }
        
        log(">>>> LEAVING CASINO");
    }
    
    protected void play() {
        openGame();
        
        // Get number of players
        int numPlayers = players.size();
        
        // Play up to the dealer in which we'll don something different
        for(int k=0; k < players.size()-1; k++) {
            Player player = players.get(k);
            
            Action action;
            
            do {
                action = player.getAction();
                
                if(action == Action.HIT) {
                    Card card = shoe.deal();
                    
                    player.hit(card);
                }
                else if(action == Action.DOUBLE_DOWN && player.handSize() == 2) {
                    Card card = shoe.deal();
                    
                    player.hit(card);
                    
                    player.doubleDown();
                }
                    
            } while(action == Action.HIT && player.handValue <= 21);
            
            // If player broke, take them out of game
            if(player.handValue > 21) {
                player.loses();
                                    
                log(player + " LOSES!");
                
                numPlayers--;
            }
            
            // If only only one player left (the dealer!), close game
            if(numPlayers == 1)
                break;
        }
        
        closeGame(numPlayers);
        
        // Report dealer outcome
        log(dealer + "");
    }

    public void openGame() {
        for(Player player: players) {
            player.reset();            
        }
        
        dealInitial();
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
    
    @Override
    public void reset() {
        Boolean yes = shoe.reshuffle();
        
        for(Player player: players)
            player.reshuffling(yes);
        
        hand.clear();
        
        aces = 0;
        
        handValue = 0;
    }
    
    @Override
    public Action getAction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void wins(double amt) {
        bankroll += amt;
    }
    
    public void loses(double amt) {
        bankroll -= amt;
        
        assert(bankroll > 0);
    }
    
    @Override
    public void fund() {
        bankroll = INTIAL_CREDIT;
    }
    
    public void log(String msg) {
        if(interactive)
            System.out.println(msg);
    }
}
