/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gattaca.blackjack;

/**
 *
 * @author Ron.Coleman
 */
public class Card {
    public enum Rank {
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10),
        JACK(10),
        QUEEN(10),
        KING(10),
        ACE(1);
        
        public int value;
        Rank(int value) {
            this.value = value;
        }
    }
    
    public enum Suit {
        CLUBS,
        DIAMONDS,
        HEARTS,
        SPADES
    }
    
    public Rank rank;
    protected Suit suit;
    
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }
    
    public Boolean isAce() {
        return rank == Rank.ACE;
    }
    
    public Rank getRank() {
        return rank;
    }
    
    public Suit getSuit() {
        return suit;
    }
}
