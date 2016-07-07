package robots.view;

import robots.world.Board;
import java.awt.*;
import javax.swing.*;

/**
   A panel for displaying the board.
*/

public class BoardView extends JPanel {
  private Board board;

  /**
     Creates a view from a board.
  */
  public BoardView(Board board) {
    this.board = board;
    setBackground(Color.WHITE);
  }

  /**
     Paints the panel.
  */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    
    try {
      java.lang.reflect.Method paint =
        Board.class.getMethod("paint",
                              new Class[] { Graphics2D.class, Dimension.class });
      paint.invoke(board, g2, getSize());
    } catch (Throwable ex) { }
  }

  /**
     Returns the preferred size of this panel.
  */
  public Dimension getPreferredSize() {
    int tileSize = 30;
    int numCols = board.getNumColumns();
    int numRows = board.getNumRows();
    int boardWidth = Math.min(tileSize * numCols, 1200 / numCols * numCols);
    int boardHeight = Math.min(tileSize * numRows, 700 / numRows * numRows);
    return new Dimension(boardWidth + 1, boardHeight + 1);
  }

}

