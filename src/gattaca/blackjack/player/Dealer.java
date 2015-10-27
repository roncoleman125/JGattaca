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

import gattaca.blackjack.card.Card;
import gattaca.blackjack.card.Shoe;
import gattaca.blackjack.game.Blackjack;
import gattaca.blackjack.game.Action;
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
    
    /**
     * Constructor
     */
    public Dealer() {
        Config config = Config.getInstance("gattaca.json");
        
        this.shoe = new Shoe(config.numDecks);
        
        this.bankroll = INTIAL_CREDIT;
    }
    
    /**
     * Gets player k bankroll
     * @param k Player number
     * @return Double
     */
    public double getBankroll(int k) {
        assert(k >= 0 && k < players.size());
        
        return players.get(k).getBankroll();
    }
    
    /**
     * Starts the game.
     */
    public void start() {  
        Blackjack.log("!!!!! ENTERING CASINO");
        
        // Let players know who dealer is to report earnings & loses
        players.stream().forEach((player) -> {
            player.makeDealer(this);
            player.fund();
        });
        
        dealer = this;
        
        int numGames = Config.getInstance().numGames;

        for(int game=0; game < numGames; game++) {
                                
            Blackjack.log(">>>> GAME "+game+" STARTING");

            play();
            
            Blackjack.log(">>>> GAME "+game+" OVER");
        }
        
        Blackjack.log("!!!!! LEAVING CASINO");
    }
    
    /**
     * Plays one game.
     */
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
                                    
                Blackjack.log(player + " LOSES!");
                
                numPlayers--;
            }
            
            // If only only one player left (the dealer!), close game
            if(numPlayers == 1)
                break;
        }
        
        closeGame(numPlayers);
        
        // Report dealer outcome
        Blackjack.log(dealer + "");
    }

    /**
     * Opens a game, namely, initializes it.
     */
    protected void openGame() {
        for(Player player: players) {
            player.reset();            
        }
        
        dealInitial();
    }
    
    /**
     * Closes a game
     * @param numPlayers Number of players in the game, not necessarily players.size().
     */
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
                    Blackjack.log(player + " WINS!");
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
                Blackjack.log(player + " BLACKJACK WINS!");
            }
            
            // Test player against dealer
            else if (playerValue <= 21) {
                // Player loses if handValue is less than dealer or dealer has blackjack
                if(playerValue < dealer.handValue || dealer.hasBlackjack()) {
                    player.loses();
                    Blackjack.log(player + " LOSES!");
                }
                
                else if(playerValue > dealer.handValue) {
                    player.wins();
                    Blackjack.log(player + " WINS!");
                }

                else {
                    player.pushes();
                    Blackjack.log(player + " PUSHES.") ;       
                }
            }

            // Player if hand over 21
            else {
                player.loses();
                Blackjack.log(player + " WINS!");
            }
        }
    }
    
    /**
     * Deals first second round of cards.
     */
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
    
    /**
     * Conditionally distribute dealt card to all players.
     * @param player Player which received the card
     * @param card Card
     * @param mine If false don't distribute this dealer's card
     */
    protected void distribute(Player player, Card card, Boolean mine) {
        for(Player p: players) {
            if(!mine && player instanceof Dealer)
                continue;
            
            p.dealt(player, card);
        }
    }
    
    /**
     * Resets the dealer state.
     */
    @Override
    public void reset() {
        Boolean yes = shoe.reshuffle();
        
        for(Player player: players)
            player.reshuffling(yes);
        
        hand.clear();
        
        aces = 0;
        
        handValue = 0;
    }
    
    /**
     * Gets the dealer's action which, in this case, is not meaningful
     * @return 
     */
    @Override
    public Action getAction() {
        return Action.NONE;
    }
    
    /**
     * Dealer won this amount
     * @param amt Amount
     */
    public void wins(double amt) {
        bankroll += amt;
    }
    
    /**
     * Dealer loses this amount.
     * @param amt Amount
     */
    public void loses(double amt) {
        bankroll -= amt;
        
        assert(bankroll > 0);
    }
    
    /**
     * Fund the dealer's account
     */
    @Override
    public void fund() {
        bankroll = INTIAL_CREDIT;
    }
}
