/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gattaca.blackjack.game;

/**
 *
 * @author Ron.Coleman
 */
public class Game {
    public static void main(String args[]) {
        Dealer dealer = new Dealer();
        
        dealer.go();
    }
}
