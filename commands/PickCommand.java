package robots.commands;

import robots.world.*;
import robots.world.Package;
import java.util.Iterator;

/**
     A class that models a pick command.
*/
public class PickCommand extends Command {

    private PackageCollection packages;
    private Package pkg;

    /**
       Constructs a pick command with an empty collection of packages and a bid of 1.
    */
    public PickCommand() {
	super();
	packages = new PackageCollection();
    }

    /**
       Constructs a pick command with the given bid and an empty collection of packages. 
    */
    public PickCommand(int bid) {
	super(bid);
	packages = new PackageCollection();
    }

    /**
       Constructs a pick command with the given collection of packages and a bid of 1. 
    */
    public PickCommand(PackageCollection packages) {
	super();
	this.packages = packages;
    }

    /**
       Constructs a pick command with the given bid and the given collection of packages. 
    */
    public PickCommand(int bid, PackageCollection packages) {
	super(bid);
	this.packages = packages;
    }

    /**
       Constructs a pick command with the given package and a bid of 1. 
    */
    public PickCommand(Package pkg) {
	super();
	this.pkg = pkg;
    }
    
    /**
       Constructs a pick command with the given bid and the given package. 
    */
    public PickCommand(int bid, Package pkg) {
	super(bid);
	this.pkg = pkg;
    }

    /**
       Returns a string representation of the pick command in the form expected by the server. 
    */
    public String toString() {
      String s = "";
      s = s + this.getBid() + " Pick";
      if (this.pkg == null) {
        Iterator<Package> it = packages.iterator();
        while(it.hasNext()) {
          s = s + " " + it.next().getId();
        }
      }
      else {
        s = s + " " + pkg.getId();
      }
      return s;
    }
	    
}
