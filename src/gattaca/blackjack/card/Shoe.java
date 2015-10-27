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
        this(decks,System.currentTimeMillis());
    }
    
    public Shoe(int decks, long seed) {
        ran = new Random(seed);
        
        for(int n=0; n < decks; n++) {
            for(int suit=0; suit < 4; suit++) {
                for(int rank=2; rank <= 14; rank++) {
                    Card card = new Card(Card.lookupRank(rank),Card.lookupSuit(suit));
                    cards.add(card);
                }
            }
        }
        
        Collections.shuffle(cards, ran);
        
        // Should really be based on the number of players? At some casinos
        // the player places the burn card. 
        int depth = cards.size() / 3;
        
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
