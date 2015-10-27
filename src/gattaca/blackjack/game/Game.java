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
    public static int playerBlackjacks = 0;
    public static int playerBreaks = 0;
    public static int dealerBlackjacks = 0;
    public static int dealerBreaks = 0;
    public static int pushes = 0;
    public static int hands = 0;
    protected static int gameno = 0;
    public static Boolean interactive = Config.getInstance(Config.CONFIG_PATH).numGames <= 20;
    protected Dealer dealer;

    public Game() {
        gameno++;
        this.dealer = Config.getInstance("gattaca.json").dealer;
    }
    
    public void start() {       
        dealer.go();
    }
    
    public double getEarnings() {
        int playerNum = 0;
        
        return dealer.getBankroll(playerNum);
    }
 
    public static void log(String msg) {
        if(interactive)
            System.out.println(msg);
    }
    
    public static void main(String args[]) {
        Dealer dealer = Config.getInstance("gattaca.json").dealer;;
        
        dealer.go();
        
        double earnings = dealer.getBankroll(0);
        
        System.out.println("fitness = "+earnings);
    }
}
