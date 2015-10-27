/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gattaca.blackjack.game;

import gattaca.blackjack.player.Dealer;
import gattaca.util.Config;

/**
 *
 * @author Ron.Coleman
 */
public class Game {
    protected Dealer dealer;

    public Game() {
        this.dealer = Config.getInstance("gattaca.json").dealer;
    }
    
    public void start() {       
        dealer.go();
    }
    
    public double getEarnings() {
        int playerNum = 0;
        
        return dealer.getBankroll(playerNum);
    }
    
    public static void main(String args[]) {
        Dealer dealer = new Dealer();
        
        dealer.go();
    }   
}
