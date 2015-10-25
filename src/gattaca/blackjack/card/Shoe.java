/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gattaca.blackjack.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Ron.Coleman
 */
public class Shoe {
    protected ArrayList<Card> cards = new ArrayList<>();
    
    protected int nextIndex = 0;
    protected int burnIndex = 0;
    
    protected Random ran = new Random(0);
    
    public Shoe(int decks) {
        for(int n=0; n < decks; n++) {
            for(int suit=0; suit <= 4; suit++) {
                for(int rank=2; rank <= 13; rank++) {
                    Card card = new Card(Card.lookupRank(rank),Card.lookupSuit(suit));
                    cards.add(card);
                }
            }
        }
        
        Collections.shuffle(cards, ran);
        
        int depth = cards.size() / 4;
        
        burnIndex = cards.size() - ran.nextInt(depth);
    }
    
    public Card deal() {
        Card card = cards.get(nextIndex++);
        
        return card;
    }
    
    public Boolean reshuffle() {
        if(nextIndex < burnIndex)
            return false;
        
        nextIndex = 0;
        
        Collections.shuffle(cards);
        
        return true;
    }
}
