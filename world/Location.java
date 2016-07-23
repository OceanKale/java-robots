package robots.world;

/**

   Class Location represents a location as row and column values
   on the board.

*/
public class Location {

  private int column;
  private int row;  

  /**
     Contstructs a Location with the given row and column. The
     column and row values must be within acceptable integer
     values in Java. 
  */
  public Location(int column, int row) {
    this.column = column;
    this.row    = row;
  }

  /**
     Method equals returns true if and only if the given object
     is a Location and it has the same coordinates as this 
     Location.
  */
  public boolean equals(Object obj) {
    if (obj instanceof Location) {
      Location other = (Location) obj;
      if (this.getColumn() == other.getColumn()  &&  this.getRow() == other.getRow()) {
        return true;
      }
    }
      return false;
  }

  /**
     Method getColumn takes no arguments and returns this
     location's column.
  */
  public int getColumn() {
    return this.column;
  }

  /**
     Method getRow takes no arguments and returns this
     location's row.
  */
  public int getRow() {
    return this.row;
  }

  /**
     Method next returns the next Location in the specified 
     direction from the current Location.
  */
  public Location next(Direction dir) {
    int curCol = this.getColumn();
    int curRow = this.getRow();

    if (dir.equals(Direction.NORTH)) {
      return new Location((curCol),(curRow + 1));
    } else if (dir.equals(Direction.SOUTH)) {
      return new Location((curCol),(curRow - 1));
    } else if (dir.equals(Direction.EAST)) {
      return new Location((curCol + 1),(curRow));
    } else {
      return new Location((curCol - 1),(curRow));
    }
  }

  /**
     Method toString takes no arguments and returns a string
     representing this location's column and row in the format
     "(column#,row#)".
  */
  public String toString() {
    return "(" + this.column + "," + this.row + ")";
  }
}