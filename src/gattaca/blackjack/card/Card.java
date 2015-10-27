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
package gattaca.blackjack.card;

/**
 *
 * @author Ron.Coleman
 */
public class Card {
    public enum Rank {
        Two(2),
        Three(3),
        Four(4),
        Five(5),
        Six(6),
        Seven(7),
        Eight(8),
        Nine(9),
        Ten(10),
        Jack(10),
        Queen(10),
        King(10),
        Ace(1),
        None(-1);
        
        public int value;
        Rank(int value) {
            this.value = value;
        }
    }
    
    public enum Suit {
        Clubs,
        Diamonds,
        Hearts,
        Spades,
        None
    }
    
    public Rank rank;
    protected Suit suit;
    protected int value;
    
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
        this.value = rank.value;
    }
    
    public int value() {
        if(isAce())
            return value + 10;
        
        return value;
    }
    
    public Boolean isAce() {
        return rank == Rank.Ace;
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
                return Card.Rank.Two; 
            case 3:
                return Card.Rank.Three; 
            case 4:
                return Card.Rank.Four;
            case 5:
                return Card.Rank.Five;
            case 6:
                return Card.Rank.Six;
            case 7:
                return Card.Rank.Seven;
            case 8:
                return Card.Rank.Eight;
            case 9:
                return Card.Rank.Nine;
            case 10:
                return Card.Rank.Ten;
            case 11:
                return Card.Rank.Jack;
            case 12:
                return Card.Rank.Queen;
            case 13:
                return Card.Rank.King;
            case 14:
                return Card.Rank.Ace;
        }
        
        return Card.Rank.None;
    }
    
    final static public Card.Suit lookupSuit(int suit) {
        assert(suit >= 0 && suit <= 3);
        switch(suit) {
            case 0:
                return Card.Suit.Clubs;
            case 1:
                return Card.Suit.Diamonds;
            case 2:
                return Card.Suit.Hearts;
            case 3:
                return Card.Suit.Spades;
        }
        return Card.Suit.None;
    }
    
    @Override
    public String toString() {
        return rank.toString() + " of " + suit.toString();
    }
    
}
