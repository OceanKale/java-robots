package robots.world;

import java.util.Scanner;

/**


   Class Package represents a package used for scoring points.
*/
public class Package implements Comparable<Package> {
  
  private int id;
  private Location destination;
  private int weight;

  /**
     Contructs a new Package with the given ID, destination, and weight.
  */
  public Package(int id, Location destination, int weight) {
    this.id          = id;
    this.destination = destination;
    this.weight      = weight;
  }

  /**
     Returns a negative number if this package is heavier than the given package, 
     0 if they have the same weight, and a positive number if this package is lighter 
     than the given package.<br>
 
     Note: this class has a natural ordering that is inconsistent with equals. 
  */
  public int compareTo(Package that) {
    if( this.weight < that.getWeight() ) {
      return 1;
    } else if ( this.weight == that.getWeight() ) {
      return 0;
    } else {
      return -1;
    }
  }
  
  /**
     Method getId takes no arguments and returns the unique identifier
     assigned to this package.
  */
  public int getId() {
    return this.id;
  }

  /**
     Method getDestination takes no arguments and returns the destination
     (a string representation of a Location) that this package must be 
     brought to.
  */
  public Location getDestination() {
    return this.destination;
  }

  /**
     Method getWeight takes no arguments and returns weight of this package.
  */
  public int getWeight() {
    return this.weight;
  }
  
  /**
     Method "read" reads the textual representation of a single package 
     as transmitted by the server and creates an instance of the class.
  */
  public static Package read(Scanner scanner) {
    int id      = scanner.nextInt();
    int destCol = scanner.nextInt();
    int destRow = scanner.nextInt();
    int weight  = scanner.nextInt();
    return new Package(id, new Location(destCol, destRow), weight);
  }

  /**
     Method toString takes no arguments and returns a string representation
     of this package containing its ID, weight, and destination in the format
     "Package #(id): weight (weight), destination (col,row)".
  */
    public String toString() {
	return "Package #" + this.id + ": weight " + this.weight + ", destination " + this.destination.toString();
    }
    
    public void piggyBack(Package p){
	if(p.getDestination().equals(this.getDestination())){
	    weight += p.getWeight();
	}
    }
}
