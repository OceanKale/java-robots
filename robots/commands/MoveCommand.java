package robots.commands;

import robots.world.*;

/**
   Jason Eckhart (jeckhart)<br>
   Nathan Nehrt  (nnehrt)<br>
   C212 Assignment 9<br><br>

   
   This class models a move command.
*/
public class MoveCommand extends Command {

    private Direction dir;

    /**
       Constructs a move command, in the given direction, with bid 1.
    */
    public MoveCommand(Direction dir) {
	super();
	this.dir = dir;
    }
    
    /**
       Constructs a move command with the given bid and direction.
    */
    public MoveCommand(int bid, Direction dir) {
	super(bid);
	this.dir = dir;
    };
    
    /**
       Returns a string representation of the move command in the form expected by the server. 
    */
    public String toString() {
	return this.getBid() + " Move " + this.dir.toString(); 
    }
}
