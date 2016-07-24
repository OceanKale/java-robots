package robots.play;

import robots.world.Package;
import robots.world.*;
import robots.commands.*;

/**
   Class CluelessBrain has no real objective
*/
public class CluelessBrain extends Brain {

    /**
       Creates a clueless brain for use with the given game
    */
    public CluelessBrain(Game game) {
        super(game);
    }

    /**
       Stay where you are and do nothing, sending drop commands when we have no object to drop
    */
    public Command next() {
	return new DropCommand();
    }
}
