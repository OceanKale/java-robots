package robots.world;

import java.awt.*;
import javax.swing.*;
import java.util.Scanner;
import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.util.ArrayList;

/**
   This class represents a single tile on the playing board. It contains game-state information such 
   as whether or not a robot is present and information about the location and type of the tile. 
*/
public abstract class Tile extends Object {

    private Location location;
    // private Robot r;
    private ArrayList<Robot> robs;
    private PackageCollection packages = new PackageCollection();
    private Boolean visited = false;

    /**
       Constructs a tile with the given column and row as its location    
    */
    public Tile(int col, int row) {
	this.location = new Location(col, row);
        // this.r = null;
	this.robs = new ArrayList<Robot>();
    }

    /**
       Constructs a tile with the given location
    */
    public Tile(Location location) {
	this.location = location;
        //this.r = null;
	this.robs = new ArrayList<Robot>();
    }

    /**
       Returns whether or not a robot can be safely placed on this tile
    */
    public abstract boolean isSafe();

    /**
       Returns whether or not a robot can move to this tile
    */
    public abstract boolean isAccessible();

    /**
       Generates a single-character string of this Tile's character
       representation (as specified below in read)
    */
    public abstract String toString();

    /**
       Returns the location of this Tile on the board
    */
    public Location getLocation() {
	return this.location;
    }

    /**
       Generates a Tile given a server coordinate and character representation
    */
    public static Tile read(int col, int row, char c) {
	Tile t;

	switch(c) {
          case '.': t = new NormalTile(col, row); break;
          case '@': t = new HomeBaseTile(col, row); break;
          case '#': t = new WallTile(col, row); break;
	  case '~': t = new WaterTile(col, row); break;
          default: throw new UnknownTileException("Character " 
                                                  + String.valueOf(c) + " is not a valid tile representation.");
	}
	return t;
    }

    /**
       Returns whether or not this tile contains a robot
    */
    public boolean hasRobot() {
	//return(this.r != null);
	return !robs.isEmpty();
    }

    /**
       Returns amount of surrounding robots ("surrounding" defined as a
       maximum distance as 2 tiles away from tile measured).
    */
    /*
    public int getSurroundingRobots(){
	Tile cur;
	int numRobut = 0;
	int col = getLocation().getColumn();
	int row = getLocation().getRow();
	for(int i = col - 2; i <= col + 4; i++){
	    for(int j = row - 2; j <= row + 4; j++){
		cur = new Tile(col, row);
		if(cur.hasRobot()){
		    numRobut += robs.size();
		}
	    }
	}
	return numRobut;
    }
    */

    /**
       Returns the robot at this location
    */
    public Robot getRobot() throws NoRobotHereException {
	if (robs.isEmpty()) {
        throw new NoRobotHereException();
      } 
	else {
	    //return this.r;
	    return robs.get(robs.size() - 1);
	    //Cycle through robots at location via index in length of "robs"?
	    //Or just utilize "r" variable that will be initialized in set method
      }
    }

    /**
       Gets the given robot from the tile (but does not remove the robot from the tile)
    */
    public Robot getRobot(Robot rob) throws NoRobotHereException {
        int robIndex = robs.indexOf(rob);

	if (robs.isEmpty()) {
            throw new NoRobotHereException();
        } 
	else if ( robIndex == -1) {
            throw new NoRobotHereException();
        }
        else {
	    return robs.get(robIndex);
        }
    }

    /**
       If the tile is inaccessible,then nothing happens; if the tile is unsafe
       then the robot dies; otherwise, the robot is placed on the tile
    */
    public void setRobot(Robot robot) {
	if(this.isAccessible() && this.isSafe()) {
	    //this.r = robot;
	    robs.add(robot);
	}
	else if (this.isAccessible() && !this.isSafe()){
	    //this.r = robot;
	    robs.add(robot);
	    robs.remove(robot);
            //this.removeRobot();
	    robot.die();
        }
    }
    
    /**
       Removes all robots (if any) on the tile
    */
    public void removeRobot() {
	// save "r" variable commented above to indicate location or utilize
	// index of robot desired removed
	robs.clear();
	//this.r = null;
    }

   /**
       Removes the given robot if on the tile, does nothing if the robot not on the tile
    */
    public void removeRobot(Robot rob) {
	robs.remove(rob);
    }

    /**
       Uses the supplied drawing object, g2, to display a graphical representation of the tile inside 
       the given bounding box. If there is a robot on this tile, its paint  method is called so that 
       the robot is displayed on top of this tile
    */
    public void paint(Graphics2D g2, Rectangle2D.Double box) {
	Color color = this.getColor();
	g2.setColor(color);
	g2.fill(box);
        g2.setColor(Color.BLACK);
        g2.draw(box);
	if(this.hasRobot()) {
	    this.getRobot().paint(g2, box);
	}
    }

    /**
       Returns the color used by paint to display the tile
    */
    public abstract Color getColor();

    /**
       Returns true if the given object is a tile and it has the same location
       as this tile
    */
    public boolean equals(Object obj) {
        if (obj instanceof Tile) {
            Tile other = (Tile) obj;
            int thisRow = this.getLocation().getRow();
            int thisCol = this.getLocation().getColumn();
            int otherRow = other.getLocation().getRow();
            int otherCol = other.getLocation().getColumn();
            if ( thisCol == otherCol  &&  thisRow == otherRow ) {
                return true;
            }
        }
        return false;
        
        /**
	String s = ""+obj.getClass().getName();
	// comp stands for "compare"
	String comp = "Tile";
	if(comp.equals(s)) {
	    Tile o = (Tile)obj;
	    return(o.getLocation().equals(this.getLocation()));
	}
	else {
	    return false;
	}
        */
    }

    /**
       Returns the collection of packages at this tile.
    */
    public PackageCollection getPackageCollection() {
        return this.packages;
    }

    /**
       Returns true iff the tile has been visited by the player robot
    */
    public boolean hasBeenVisited() {
	return visited;
    }

    /**
       Marks the tile as visited
    */
    public void markVisited() {
        visited = true;
    }

}
