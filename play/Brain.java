package robots.play;

import robots.world.Package;
import robots.world.*;
import robots.commands.*;

/**
    Class Brain represents an abstract brain for controlling a player robot. Every brain must 
   be able to produce a next command, based on the state of a certain game. 
*/
public abstract class Brain {

    private Game game;

    /**
       Creates a brain that is knowlegeable about the given game, thereby providing the brain 
       access to all known information about the current game state. 
    */
    public Brain(Game game) {
	this.game = game;
    }

    /**
       Returns the current game being controlled by this brain. 
    */
    public Game getGame() {
	return game;
    }

    /**
       This brain uses the current game state to produce the next command for the 
       player robot. Assumes that the game has been previously set. 
    */
    public abstract Command next();

    
}
