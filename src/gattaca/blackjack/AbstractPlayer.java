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
abstract public class AbstractPlayer {
    protected ArrayList<Card> hand = new ArrayList<>();
    protected Card upCard;
    private double bankroll;
    
    abstract public Command getCommand();
    
    int aces = 0;
    
    public void up(Card card) {
        this.upCard = card;
    }
    
    public void hit(Card card) {
        hand.add(card);
        
        if(card.isAce())
            aces++;
    }
    
    public void dealt(AbstractPlayer player, Card card) {
        
    }
    
    public int value() {
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
    
    public Boolean isSoft() {
        return aces != 0;
    }
    
    public void reset() {
        hand.clear();
    }
    
    public void lose(int amt) {
        bankroll -= amt;
    }
    
    public void win(int amt) {
        bankroll += amt;
    }
    
    public double getBankroll() {
        return bankroll;
    }
    
}
