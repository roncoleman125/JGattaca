/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gattaca.blackjack.util;

/**
 *
 * @author Ron.Coleman
 */
public enum Action {
    NONE(-1),
    STAY (0),
    HIT (1),
    DOUBLE_DOWN (2);
    
    public int value;
    Action(int value) {
        this.value = value;
    } 
}
