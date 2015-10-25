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
public class UpCard extends Card {
    
    public UpCard(Card card) {
        super(card.getRank(),card.getSuit());
    }
    
}
