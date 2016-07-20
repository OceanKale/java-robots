package robots.world;

import java.util.Iterator;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

/**
    This class uses an internal ArrayList to manage a collection of Packages. 
*/
public class PackageCollection implements Iterable<Package> {

    private ArrayList<Package> packages;
    private Iterator<Package> it;
    
    /**
       Constructs a new empty collection of packages. 
    */
    public PackageCollection() {
      this.packages = new ArrayList<Package>();
    }

    /**
       Constructs a new collection of packages containing the given package. 
    */
    public PackageCollection(Package pkg) {
      this.packages = new ArrayList<Package>();
      packages.add(pkg);
    }

    /**
       Removes all packages from this collection, and then returns this collection.
    */
    public PackageCollection clear() {
	packages.clear();
	return this;
    }

    /**
       Adds the given package to this collection, and then returns this collection. 
    */
    public PackageCollection add(Package pkg) {
	packages.add(pkg);
	return this;
    }

    /**
       Adds the given collection of packages to this collection, and then returns this collection. 
    */
    public PackageCollection add(PackageCollection packages) {
      Iterator<Package> inPackagesIt = packages.iterator();
      while( inPackagesIt.hasNext() ) {
        Package nextP = inPackagesIt.next();
        this.packages.add(nextP);
      }
      return this;
    }

    /**
       Returns the package in this collection with the given id. If no such package exits, then null is returned. 
    */
    public Package get(int id) {
	it = this.iterator();
	while (it.hasNext()) {
	    Package nextP = it.next();
	    if (nextP.getId() == id) {
		return nextP;
	    }
	}
	return null;
    }

    /**
       Returns an iterator over this package collection. 
    */
    public Iterator<Package> iterator() {
	it = packages.iterator();
	return it;
    }

    /**
       Removes the package with the given id from this collection and returns the removed package, if it exists. 
       If there is no such package in this collection, then null is returned. 
    */
    public Package remove(int id) {
        Package packToRemove = this.get(id);
        if (packToRemove == null) {
            return null;
        } else {
            packages.remove(packToRemove);
            return packToRemove;
        }
    }

    /**
       Returns true if this collection is empty, and false otherwise. 
    */
    public boolean isEmpty() {
        return packages.isEmpty();
    }

    /**
       Returns the number of packages in this collection. 
    */
    public int size() {
        return packages.size();
    }

    /**
       Returns the total weight of all packages in this collection. 
    */
    public int getTotalWeight() {
        it = packages.iterator();
        int weight = 0;
        while (it.hasNext()) {
            weight += it.next().getWeight();
        }
        return weight;
    }

    /**
       Reads a line of package information from the given scanner and constructs a collection of packages.
    */
    public static PackageCollection read(Scanner scanner) {
        PackageCollection inPackages = new PackageCollection();
        while (scanner.hasNext()) {
            int id       = scanner.nextInt();
            int col      = scanner.nextInt();
            int row      = scanner.nextInt();
            Location loc = new Location(col, row);
            int weight   = scanner.nextInt();
            Package pack = new Package(id, loc, weight);
            inPackages.add(pack);
        }
        return inPackages;
    }

    /**
       Produces a textual representation of this collection of packages, which is a String of package ids only, in construction order, 
       each of which is separated by a single blank. 
    */
    public String toString() {
        String outStr = "";
        it = packages.iterator();
        while(it.hasNext()) {
            outStr = outStr + it.next().getId();
            if (it.hasNext()){
                outStr += " ";
            }
        }
        return outStr;
    }

    /**
       Sorts the packages by weight (heaviest to lightest)
    */
    public void sort() {
        Collections.sort(packages); 
    }

    /**
       Sorts the packages according to the given Comparator
    */
    public void sort(Comparator<Package> comparator) {
        Collections.sort(packages, comparator); 
    }

    public PackageCollection getByColumn(int col){
	PackageCollection colPack = new PackageCollection();
	Iterator<Package> it = this.iterator();
	while(it.hasNext()){
	    Package p = it.next();
	    if(p.getDestination().getColumn() == col){
		colPack.add(p);
	    }
	}
	return colPack;
    }



}
