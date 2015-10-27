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
package gattaca.util;

import gattaca.blackjack.player.Dealer;
import gattaca.objective.IObjective;
import gattaca.blackjack.player.Player;
import gattaca.convergence.Terminator;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * This class implements the configurator singleton which gets loaded at run time.
 * @author Ron.Coleman
 */
public class Config {
    /** Path of the config file */
    public final static String CONFIG_PATH = "gattaca.json";
    
    /** JSON parser to read and process the config file */
    protected static JSONParser parser = new JSONParser();
    
    /** Blackjack players */
    public ArrayList<Player> players = new ArrayList<>();
    
    /** Number of games to play */
    public int numGames;
    
    /** Number of decks */
    public int numDecks;
    
    /** GA population size */
    public int popSize;
    
    /** Alleles for GA */
    public String alleles = null;
    
    /** Debugging state in the config file */
    public Boolean isDebugging = false;
    
    /** GA objective function */
    public IObjective objective = null;
    
    /** GA convergence method */
    public Terminator terminator = null;
    
    /** Dealer for Blackjack */
    public Dealer dealer = null;
    
    /** This one and only configuration */
    protected static Config config;
    
    /**
     * Constructor can only be constructed through singleton.
     */
    private Config() {
        
    }
    
    /**
     * Gets an instance of a configuration singleton from default file.
     * @return Configuration
     */
    public static Config getInstance() {
        return getInstance(CONFIG_PATH);
    }
    
    /**
     * Gets a configuration single t
     * @param path
     * @return Configuration
     */
    public static Config getInstance(String path) {
        if(config != null)
            return config;
        
        config = new Config();
        
        try {
            // Load required parameters from config file
            JSONObject json = (JSONObject) parser.parse(new FileReader(path));
            
            config.popSize = ((Long) json.get("popSize")).intValue();
            
            config.alleles = (String) json.get("alleles");
            
            config.isDebugging = (Boolean) json.get("debug");

            String className = (String) json.get("objective");
            config.objective = (IObjective) Class.forName(className).newInstance();
            
            className = (String) json.get("terminator");
            config.terminator = (Terminator) Class.forName(className).newInstance();
            
            // Load optional Blackjack stuff
            Object obj = json.get("numGames");
            if(obj != null)
                config.numGames = ((Long) obj).intValue();
            
            obj = json.get("numDecks");
            if((obj != null))
                config.numDecks = ((Long) obj).intValue();
            
            JSONArray playersArray = (JSONArray)json.get("players");
            
            if (playersArray != null) {
                Iterator<JSONObject> iter = playersArray.iterator();

                while (iter.hasNext()) {
                    className = (String) iter.next().get("player");

                    Player player = (Player) Class.forName(className).newInstance();

                    config.players.add(player);
                }
            }
            
            className = (String) json.get("dealer");
            if (className != null) {
                config.dealer = (Dealer) Class.forName(className).newInstance();

                config.players.add(config.dealer);
            }
            
            return config;
            
        } catch (IOException | ParseException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
