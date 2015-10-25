/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gattaca.blackjack.card;

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
        ACE(1),
        DONT_KNOW(-1);
        
        public int value;
        Rank(int value) {
            this.value = value;
        }
    }
    
    public enum Suit {
        CLUBS,
        DIAMONDS,
        HEARTS,
        SPADES,
        DONT_KNOW
    }
    
    public Rank rank;
    protected Suit suit;
    public int value;
    
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
        this.value = rank.value;
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
    
    final static public Card.Rank lookupRank(int rank) {
        assert(rank >= 2 && rank <= 14);
        
        switch(rank) {
            case 2:
                return Card.Rank.TWO; 
            case 3:
                return Card.Rank.THREE; 
            case 4:
                return Card.Rank.FOUR;
            case 5:
                return Card.Rank.FIVE;
            case 6:
                return Card.Rank.SIX;
            case 7:
                return Card.Rank.SEVEN;
            case 8:
                return Card.Rank.EIGHT;
            case 9:
                return Card.Rank.NINE;
            case 10:
                return Card.Rank.TEN;
            case 11:
                return Card.Rank.JACK;
            case 12:
                return Card.Rank.QUEEN;
            case 13:
                return Card.Rank.KING;
            case 14:
                return Card.Rank.ACE;
        }
        
        return Card.Rank.DONT_KNOW;
    }
    
    final static public Card.Suit lookupSuit(int suit) {
        assert(suit >= 0 && suit <= 3);
        switch(suit) {
            case 0:
                return Card.Suit.CLUBS;
            case 1:
                return Card.Suit.DIAMONDS;
            case 2:
                return Card.Suit.HEARTS;
            case 3:
                return Card.Suit.SPADES;
        }
        return Card.Suit.DONT_KNOW;
    }
    
    @Override
    public String toString() {
        return rank.toString() + " of " + suit.toString();
    }
    
}
