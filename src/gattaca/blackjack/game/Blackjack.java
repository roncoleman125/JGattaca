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
package gattaca.blackjack.game;

import gattaca.blackjack.player.Dealer;
import gattaca.util.Config;

/**
 *
 * @author Ron.Coleman
 */
public class Blackjack {
    public static int playerBlackjacks = 0;
    public static int playerBreaks = 0;
    public static int dealerBlackjacks = 0;
    public static int dealerBreaks = 0;
    public static int pushes = 0;
    public static int hands = 0;
    protected static int gameno = 0;
    public static Boolean interactive = Config.getInstance(Config.CONFIG_PATH).numGames <= 20;
    protected Dealer dealer;

    public Blackjack() {
        gameno++;
        this.dealer = Config.getInstance("gattaca.json").dealer;
    }
    
    public void start() {       
        dealer.start();
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
        
        dealer.start();
        
        double earnings = dealer.getBankroll(0);
        
        System.out.println("fitness = "+earnings);
    }
}
