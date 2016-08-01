package robots.play;

import robots.world.Package;
import robots.world.*;
import robots.commands.*;

import java.util.Iterator;

/**
     Class WanderBrain causes the robot to walk in a random pattern around the 
   board, picking up packages it happens across and dropping them if and when 
   a destination is encountered. 
*/
public class WanderBrain extends Brain {

    Board board = getGame().getBoard();
    PlayerRobot player = getGame().getPlayer();
    double rando;
    Direction curDir;
    int moveBid;
    int dropBid;
    int pickBid;
    boolean moveNext = false;

    /**
       Creates a random brain with the given bid amounts for each type of command. 
    */
    public WanderBrain(int dropBid, int pickBid, int moveBid, Game game) {
	super(game);
	this.dropBid = dropBid;
	this.pickBid = pickBid;
	this.moveBid = moveBid;
    }

    /**
       Sets the direction of the next robot move by randomly finding the direction of
       a safe tile next to the current tile.  
    */
    public void curDirec(Tile curTile) {
	rando = Math.random();
	
	if (rando >= 0.75) {
	    if(board.inBounds(curTile, Direction.NORTH) && board.adjacentTile(curTile, Direction.NORTH).isSafe()) {
		curDir = Direction.NORTH;
	    }
	    else {
		curDirec(curTile);
	    }
	}
	else if (rando >= 0.50) {
	    if(board.inBounds(curTile, Direction.SOUTH) && board.adjacentTile(curTile, Direction.SOUTH).isSafe()) {
		curDir = Direction.SOUTH;
	    }
	    else {
		curDirec(curTile);
	    }
	}
	else if (rando >= 0.25) {
	    if(board.inBounds(curTile, Direction.WEST) && board.adjacentTile(curTile, Direction.WEST).isSafe()) {
		curDir = Direction.WEST;
	    }
	    else {
		curDirec(curTile);
	    }
	}
	else {
	    if(board.inBounds(curTile, Direction.EAST) && board.adjacentTile(curTile, Direction.EAST).isSafe()) {
		curDir = Direction.EAST;
	    }
	    else {
		curDirec(curTile);
	    }
	}
     }

    /**
       The brain executes the following algorithm:<br><br>

       * If the robot lands on a tile that is the destination for one or more of 
       the packages it is carrying, then those packages are dropped.<br>
       * If the robot lands on a tile that contains packages, it attempts to pick them 
       all up, and then plans to execute a move at the next opportunity. (This ensures 
       that the robot will not continually try to pick up packages that it can't carry.)<br>
       * Otherwise, the robot chooses a direction, at random, that leads to a safe adjacent 
       tile and moves there.<br><br> 

       The robot uses the bid amounts specified in the constructor for each command type. 
    */
    public Command next() {
	Tile t = player.getTile();
	PackageCollection tilePack    = t.getPackageCollection();
        PackageCollection packsToDrop = player.deliverables();
        
        if ( ! moveNext ) {
            curDirec(t);
            if( packsToDrop != null ) {
                return new DropCommand(dropBid, packsToDrop);
            }
            else if( ! tilePack.isEmpty() ) {
                //System.out.printf("Packages: %s\n",tilePack.toString());
                moveNext = true;
                return new PickCommand(pickBid, tilePack);
            }
            else {
                return new MoveCommand(moveBid, curDir);
            }
        } 
        else {
            moveNext = false;
            return new MoveCommand(moveBid, curDir);
        }
    }
}
