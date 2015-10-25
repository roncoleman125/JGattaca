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
package gattaca.blackjack.util;

import gattaca.blackjack.player.AbstractPlayer;
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
    public final static String CONFIG_PATH = "pokpoker.json";
    
    /** JSON parser to read and process the config file */
    protected static JSONParser parser = new JSONParser();
    
    /** Number of players specified by the config file */
    protected ArrayList<AbstractPlayer> players = new ArrayList<>();
    
    /** Number of games to play */
    protected int numGames;
    
    /** Number of decks */
    protected int numDecks;
    
    /** Debugging state in the config file */
    protected Boolean debug = false;
    
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
            // Load the parameters from the config file
            JSONObject json = (JSONObject) parser.parse(new FileReader(path));
            
            config.numGames = ((Long) json.get("numGames")).intValue();
            
            config.numDecks = ((Long) json.get("numDecks")).intValue();
            
            config.debug = (Boolean) json.get("debug");
                        
            JSONArray playersArray = (JSONArray)json.get("players");
            
            // Get the players
            Iterator<JSONObject> iter = playersArray.iterator();
            
            while(iter.hasNext()) {
                String className = (String) iter.next().get("player");
                
                AbstractPlayer player = (AbstractPlayer) Class.forName(className).newInstance();
                
                config.players.add(player);
            }
            
            return config;
            
        } catch (IOException | ParseException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    /**
     * Gets players.
     * @return List of players
     */
    public ArrayList<AbstractPlayer> getPlayers() {
        return players;
    }
    
    /**
     * Gets number of players.
     * @return Integer
     */
    public int getNumPlayers() {
        return players.size();
    }
    
    /**
     * Gets number of games.
     * @return Integer
     */
    public int getNumGames() {
        return numGames;
    }
    
    /**
     * Gets number of decks.
     * @return Integer
     */
    public int getNumDecks() {
        return numDecks;
    }
    
    /**
     * Answers whether game is in debug mode.
     * @return Boolean
     */
    public Boolean isDebugging() {
        return debug;
    }
}
