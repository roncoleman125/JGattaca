/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gattaca.blackjack.player;

import gattaca.blackjack.card.Card;
import gattaca.blackjack.util.Action;
import java.util.ArrayList;

/**
 *
 * @author Ron.Coleman
 */
abstract public class Player {
    abstract public Action getAction();
    
    protected ArrayList<Card> hand = new ArrayList<>();
    
    protected Card upCard;
    
    protected double bankroll = 0.0;
    
    protected Dealer dealer;
    
    protected int aces = 0;
    
    public int handValue = 0;
    
    protected int betAmt = 1;
    
    public void hit(Card card) {
        hand.add(card);
        
        if(card.isAce())
            aces++;
        
        handValue = value();
    }
    
    public int handSize() {
        return hand.size();
    }

    public void dealt(Player player, Card card) {
        if(player instanceof Dealer)
            this.upCard = card;
    }
    
    public void makeDealer(Dealer dealer) {
        this.dealer = dealer;
    }
    
    public void doubleDown() {
        betAmt = 2;
    }
    
    public int getBet() {
        return betAmt;
    }
    
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
    
    public Boolean hasBlackjack() {
        if(hand.size() != 2 || value() != 21)
            return false;
        
        Boolean bj = hand.get(0).isAce() || hand.get(1).isAce();
        
        return bj;
    }
    
    public Boolean isSoft() {
        return aces != 0;
    }
    
    public void reset() {
        hand.clear();
        
        betAmt = 1;
        
        upCard = null;
        
        aces = 0;
        
        handValue = 0;
    }
    
    public void loses() {
        bankroll -= betAmt;
        
        dealer.wins(betAmt);
    }
    
    public void wins() {
        bankroll += betAmt;
        
        dealer.loses(betAmt);
    }
    
    public void winsBlackjack() {
        double earnings = 1.5 * betAmt;
        
        bankroll += earnings;
        
        dealer.loses(earnings);
    }
    
    public void pushes() {
        
    }
    
    public void reshuffling(Boolean yes) {
        
    }
    
    public double getBankroll() {
        return bankroll;
    }
    
    public void fund() {
        bankroll = 0;
    }
    
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
