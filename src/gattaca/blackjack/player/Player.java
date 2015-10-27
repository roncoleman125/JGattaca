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
import gattaca.blackjack.game.Action;
import java.util.ArrayList;

/**
 * This class in the main player interface to Blackjack.
 * @author Ron.Coleman
 */
abstract public class Player {
    /**
     * The only abstract method which gets the player's action.
     * @return Action
     */
    abstract public Action getAction();
    
    /** Player's hand where cards are stored */
    protected ArrayList<Card> hand = new ArrayList<>();
    
    /** Dealer's up card */
    protected Card upCard;
    
    /** Initial bankroll */
    protected double bankroll = 0.0;
    
    /** Reference to dealer so that player can report earnings and loses. */
    protected Dealer dealer;
    
    /** Number of aces, a convenience member to calculate the hand value */
    protected int aces = 0;
    
    /** Hand value -- calculated during hit */
    public int handValue = 0;
    
    /** Default bet amount changes on double-down */
    protected int betAmt = 1;
    
    /**
     * Hits this player.
     * @param card Card
     */
    public void hit(Card card) {
        hand.add(card);
        
        if(card.isAce())
            aces++;
        
        handValue = value();
    }
    
    /**
     * Gets the player's hand size
     * @return 
     */
    public int handSize() {
        return hand.size();
    }

    /**
     * Reports player being dealt a card which useful for card counting.
     * @param player Player which got the card
     * @param card Card
     */
    public void dealt(Player player, Card card) {
        if(player instanceof Dealer)
            this.upCard = card;
    }
    
    /**
     * Points this player to the dealer.
     * @param dealer Dealer
     */
    public void makeDealer(Dealer dealer) {
        this.dealer = dealer;
    }
    
    /**
     * Double-down
     */
    public void doubleDown() {
        betAmt = 2;
    }
    
    /**
     * Gets the bet amount
     * @return Integer
     */
    public int getBet() {
        return betAmt;
    }
    
    /**
     * Gets the hand value.
     * @return Integer
     */
    protected int value() {
        int sum = 0;
       
        for(Card card: hand) {
            sum += card.rank.value;
        }
        
        for(int i=0; i < aces; i++) {
            if(sum + 10 > 21)
                break;
            
            sum += 10;
        }
        
        return sum;
    }
    
    /**
     * Returns true if the player has blackjack
     * @return 
     */
    public Boolean hasBlackjack() {       
        return value() == 21 && hand.size() == 2;
    }

    /**
     * Resets the player
     */
    public void reset() {
        hand.clear();
        
        betAmt = 1;
        
        upCard = null;
        
        aces = 0;
        
        handValue = 0;
    }
    
    /**
     * Captures a loss.
     */
    public void loses() {
        bankroll -= betAmt;
        
        dealer.wins(betAmt);
    }
    
    /**
     * Captures a win.
     */
    public void wins() {
        bankroll += betAmt;
        
        dealer.loses(betAmt);
    }
    
    /**
     * Captures a win on blackjack
     */
    public void winsBlackjack() {
        double earnings = 1.5 * betAmt;
        
        bankroll += earnings;
        
        dealer.loses(earnings);
    }
    
    /**
     * Captures a push.
     */
    public void pushes() {
        
    }
    
    /**
     * Indicates the deck has been reshuffled which useful to know for card counting.
     * @param yes 
     */
    public void reshuffling(Boolean yes) {
        
    }
    
    /**
     * Gets the bankroll
     * @return 
     */
    public double getBankroll() {
        return bankroll;
    }
    
    /**
     * Funds the player.
     */
    public void fund() {
        bankroll = 0;
    }
    
    /**
     * Gets a string representation of the player.
     * @return 
     */
    @Override
    public String toString() {
        String cards = "";
        
        int sz = hand.size();
        
        for(int k=0; k < sz; k++) {
            cards += hand.get(k).toString();
            
            if(k != sz-1)
                cards += " + ";
        }
                
        String name = this.getClass().getSimpleName();
        
       return name + " (" + bankroll + "): " + cards + " = " + handValue;
    }  
}
