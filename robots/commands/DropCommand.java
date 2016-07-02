package robots.commands;

import robots.world.*;
import robots.world.Package; // ?
import java.util.Iterator;

/**
 * A class that models a drop command.
*/

public class DropCommand extends Command {

    private PackageCollection packages;
    private Package pkg;

    /**
       Constructs a drop command with an empty collection of packages and a bid of 1. 
    */
    public DropCommand() {
	super();
	packages = new PackageCollection();
    }

    /**
       Constructs a drop command with the given bid and an empty collection of packages. 
    */
    public DropCommand(int bid) {
	super(bid);
	packages = new PackageCollection();
    }

    /**
       Construct a drop command with the given collection of packages and a bid of 1. 
    */
    public DropCommand(PackageCollection packages) {
	super();
	this.packages = packages;
    }

    /**
       Construct a drop command with the given bid and the given collection of packages.
    */
    public DropCommand(int bid, PackageCollection packages) {
	super(bid);
	this.packages = packages;
    }

    /**
       Construct a drop command with the given package and a bid of 1. 
    */
    public DropCommand(Package pkg) {
	super();
	this.pkg = pkg;
    }
    
    /**
       Construct a drop command with the given bid and the given package. 
    */
    public DropCommand(int bid, Package pkg) {
	super(bid);
	this.pkg = pkg;
    }

    /**
       Returns a string representation of the drop command in the form expected by the server. 
    */
    public String toString() {
	String s = "";
	s = s + this.getBid() + " Drop";
	if (this.packages != null) {
	    Iterator<Package> it = packages.iterator();
	    while(it.hasNext()) {
		s = s + " " + it.next().getId();
	    }
	}
	else if (this.pkg != null) {
	    s = s + " " + pkg.getId();
	}
       
	return s;
    }
    
}
