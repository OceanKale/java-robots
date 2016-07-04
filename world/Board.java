package robots.world;

import java.awt.*;
import javax.swing.*;
import java.util.Scanner;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator.*;

/**
   
   Class Board internally models the playing board transmitted by the server
   for the robots game. The board is a rectangular area of square tiles. Each
   tile is either open space, a wall, water, or a home base. 
*/
public class Board {
  
    private Tile[][] tiles;  // Tile[cols][rows]
    private int numCols;
    private int numRows;
       
  /**
     Constructs the default 7 x 5 board represented by the following map:<br>
     <tt>
     .......<br>
     ...~~~~<br>
     ##.~~~~<br>
     .......<br>
     ..@....<br>
     </tt>
  */
    public Board() {
	this.numCols = 7;
	this.numRows = 5;
	this.tiles = new Tile[7][5];
	// board row 0
	this.tiles[0][0] = new NormalTile(1,5);
	this.tiles[1][0] = new NormalTile(2,5);
	this.tiles[2][0] = new NormalTile(3,5);
	this.tiles[3][0] = new NormalTile(4,5);
	this.tiles[4][0] = new NormalTile(5,5);
	this.tiles[5][0] = new NormalTile(6,5);
	this.tiles[6][0] = new NormalTile(7,5); 
	// board row 1
	this.tiles[0][1] = new NormalTile(1,4);
	this.tiles[1][1] = new NormalTile(2,4);
	this.tiles[2][1] = new NormalTile(3,4);
	this.tiles[3][1] = new WaterTile(4,4);
	this.tiles[4][1] = new WaterTile(5,4);
	this.tiles[5][1] = new WaterTile(6,4);
	this.tiles[6][1] = new WaterTile(7,4);
	// board row 2
        this.tiles[0][2] = new WallTile(1,3);
        this.tiles[1][2] = new WallTile(2,3);
        this.tiles[2][2] = new NormalTile(3,3);
        this.tiles[3][2] = new WaterTile(4,3);
        this.tiles[4][2] = new WaterTile(5,3);
        this.tiles[5][2] = new WaterTile(6,3);
        this.tiles[6][2] = new WaterTile(7,3);
        // board row 3
        this.tiles[0][3] = new NormalTile(1,2);
        this.tiles[1][3] = new NormalTile(2,2);
        this.tiles[2][3] = new NormalTile(3,2);
        this.tiles[3][3] = new NormalTile(4,2);
        this.tiles[4][3] = new NormalTile(5,2);
        this.tiles[5][3] = new NormalTile(6,2);
        this.tiles[6][3] = new NormalTile(7,2);
        // board row 4
        this.tiles[0][4] = new NormalTile(1,1);
        this.tiles[1][4] = new NormalTile(2,1);
        this.tiles[2][4] = new HomeBaseTile(3,1);
        this.tiles[3][4] = new NormalTile(4,1);
        this.tiles[4][4] = new NormalTile(5,1);
        this.tiles[5][4] = new NormalTile(6,1);
        this.tiles[6][4] = new NormalTile(7,1);
  }

  /**
     Constructs the board from a give 2D array of tiles. The given array
     represents the board in column, row order.  Column 0 represents the 
     western-most column, and row 0 represents the northen most row.
  */
  public Board(Tile[][] tiles) {
    this.numCols = tiles.length;
    if (tiles.length == 0) { 
      this.numRows = 0;
    } else {
      this.numRows = tiles[0].length;
    }
    this.tiles = new Tile[this.numCols][this.numRows];
    this.tiles = tiles;
  }

  /**
     Returns the Tile adjacent to the current tile in the direciton of dir.
     Assumes that an adjacent tile exists.
  */
  public Tile adjacentTile(Tile current, Direction dir) {
    Location nextLocation = current.getLocation().next(dir);
    return this.get(nextLocation); 
  }

  /**
     Returns the number of columns in this board
  */
  public int getNumColumns() {
    return this.numCols;
  }

  /**
     Returns the number of rows in this board
  */
  public int getNumRows() {
    return this.numRows;
  }

  /**
     Returns the tile on the board at the specified location.
  */
  public Tile get(Location location) {
    return this.tiles[location.getColumn() - 1][Math.abs(location.getRow() - this.numRows)];
  }

  /**
     Returns the tile on the board at the specified column and row.
  */
  public Tile get(int col, int row) {
    return this.tiles[col - 1][Math.abs(row - this.numRows)];
  }

  /**
     Determines whether or not there is a tile adjacent to the current tile
     in the specified direction on this board.
  */
  public boolean inBounds(Tile current, Direction dir) {
    Location nextLocation = current.getLocation().next(dir);
    int nextCol = nextLocation.getColumn();
    int nextRow = nextLocation.getRow();
    if (nextCol >= 1  &&  nextCol <= this.numCols  &&  nextRow >= 1  &&  nextRow <= this.numRows) {
      return true;
    } else {
      return false;
    }
  }

  /**
     Uses the supplied drawing object, g2, to display a graphical representation
     of the board inside a rectangle of the given dimensions.
  */
  public void paint(Graphics2D g2, Dimension dim) {
    double wSize = dim.width / (double) this.numCols;
    double hSize = dim.height / (double) this.numRows;
    for (int r = 0; r < this.numRows; r++) {
      for (int c = 0; c < this.numCols; c++) {
        Rectangle2D.Double box = new Rectangle2D.Double((double) c * wSize, (double) r * hSize, wSize, hSize);
        this.tiles[c][r].paint(g2, box);
      }
    }
  }

  /**
     Generates a Board object by reading the description of the board from the 
     provided Scanner. 
  */
  public static Board read(Scanner scanner) {
    int numCols = scanner.nextInt();
    int numRows = scanner.nextInt();
    Tile[][] tiles = new Tile[numCols][numRows];
    for (int r = numRows - 1; r >= 0; r--) {
      String rowStr = scanner.next();
      for (int c = 0; c < numCols; c++) {
          tiles[c][r] = Tile.read(c + 1, Math.abs(r - numRows), rowStr.charAt(c));  
      }
    }
    return new Board(tiles);
  }
  
  /**
     Returns a human-readable representation of the board with rows listed 
     in north-south order (top to bottom).  The first row of the string
     contains the number of columns and rows.  The remaining rows give the
     character representations of the tiles.
  */
  public String toString() {
    String boardStr = this.numCols + " " + this.numRows + "\n";;
    for (int r = 0; r < this.numRows; r++) {
      for (int c = 0; c < this.numCols; c++) {
        boardStr += this.tiles[c][r].toString();
      }
      boardStr += "\n";
    }
    return boardStr;
  }

    public int getNumSafeTiles(){
	int safeTiles = 0;
	Tile t;
	for(int i = 1; i <= getNumColumns(); i++){
	    for(int j = 1; j <= getNumRows(); j++){
		t = get(i, j);
		if(t.isSafe() && t.isAccessible()){
		    safeTiles++;
		}
	    }
	}
	return safeTiles;
    }

	

  
}
