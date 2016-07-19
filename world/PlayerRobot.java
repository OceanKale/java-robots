package robots.world;

import robots.play.*;
import robots.world.Package;

import java.awt.*;
import javax.swing.*;
import java.util.Scanner;
import java.util.Iterator;
import java.util.ArrayList;


/**
  

   This class represents the player's robot. Two differences between instances of this class 
   and instances of the parent class are the Color returned by getColor() and a slight variation 
   in the String returned by toString(). 
*/
public class PlayerRobot extends Robot {

    private Brain brain;
    private Iterator<Package> it;
    private ArrayList<Tile> visitedBases = new ArrayList<Tile>();;
    
    /**
       Constructs a live robot with the given id, carrying capacity, and money
    */
    public PlayerRobot(int id, int carryingCapacity, int money) {
        super(id, carryingCapacity, money);
    }

    /**
       Returns the Color used by paint to display the robot
    */
    public Color getColor() {
	return Color.RED;
    }

    /**
       Reads the robot information (id, carrying capacity, and amount of money) from the given scanner
       and returns a newly constructed robot
    */
    public static PlayerRobot read(Scanner scanner){
	int robId = scanner.nextInt();	       
	int rCarryingCapacity = scanner.nextInt();
	int rMoney = scanner.nextInt();
	//scanner.next();  // Protects server scanner stream for next method that calls scanner.
	PlayerRobot p = new PlayerRobot(robId, rCarryingCapacity, rMoney);
	return p;
    }

    /**
       Returns a string describing the robot
    */
    public String toString() {
      return "Player" + super.toString();
    }

    /**
       Returns a PackageCollection consisting of all packages currently being carried 
       whose destination is this robot's current location. 
    */
    public PackageCollection deliverables() {
	PackageCollection deliverable = new PackageCollection();
	it = this.getPackageCollection().iterator();
	Location l = this.getTile().getLocation();
	while(it.hasNext()) {
	    Package p = it.next();
	    if(p.getDestination().equals(l)) {
		deliverable.add(p);
	    }
	}
	if(deliverable.isEmpty()) {
	return null;
	}
	else {
	    return deliverable;
	}
    }

    /**
       Returns the robot's brain.
    */
    public Brain getBrain() {
        return brain;
    }

    /**
       Sets the robot's brain to the given brain.
    */
    public void setBrain( Brain brain ) {
        this.brain = brain;
    }

    /**
       Moves the robot to the given tile, marks the tile as visited, and adds to the
       visitedBases list if the tile is a HomeBase tile.
    */
    public boolean move(Tile tile){
	tile.markVisited();
	if(tile instanceof HomeBaseTile){
	    visitedBases.add(tile);
	}
	return super.move(tile);
    }
    
    /**
       Returns the list of visited HomeBase tiles
    */
    public ArrayList<Tile> getVisitedBases(){
	return visitedBases;
    }

    /**
       Clears the list of visited HomeBase tiles
    */
    public void clearVisitedBases(){
	visitedBases.clear();
    }
    


}
