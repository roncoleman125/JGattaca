/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gattaca.blackjack.player;

import gattaca.blackjack.card.Card;
import gattaca.blackjack.util.Command;
import java.util.ArrayList;

/**
 *
 * @author Ron.Coleman
 */
abstract public class AbstractPlayer {
    abstract public Command getCommand();
    
    protected ArrayList<Card> hand = new ArrayList<>();
    
    protected Card upCard;
    
    private double bankroll = 0.0;
    
    protected int aces = 0;
    
    public int handValue = 0;
//    
//    public void up(Card card) {
//        this.upCard = card;
//    }
    
    public void hit(Card card) {
        hand.add(card);
        
        if(card.isAce())
            aces++;
        
        handValue = value();
    }
    
    public void dealt(AbstractPlayer player, Card card) {
        
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
        upCard = null;
    }
    
    public void loses(double amt) {
        bankroll -= amt;
    }
    
    public void wins(double amt) {
        bankroll += amt;
    }
    
    public void pushes() {
        
    }
    
    public void reshuffling(Boolean yes) {
        
    }
    
    public double getBankroll() {
        return bankroll;
    }
    
}
