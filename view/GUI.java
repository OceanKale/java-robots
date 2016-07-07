package robots.view;

import robots.world.Board;
import java.awt.*;
import javax.swing.*;

/**
   Displays a graphical interface to follow the client's
   connection to the server.
*/

public class GUI extends JFrame {

  private BoardView view;
  private int delay;

  /**
     Creates a GUI for the given board.
  */
  public GUI(Board board) {
    super("Robots");
    view = new BoardView(board);
    setContentPane(view);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    pack();
    setVisible(true);
  }

  /**
     Sets the delay between updates to the given number of
     milliseconds.
  */
  public void setDelay(int delay) {
    this.delay = delay;
  }

  /**
     Updates the display.
  */
  public void update() {
    try {
      if (delay > 0)
        Thread.sleep(delay);
      view.repaint();
    }
    catch (Throwable ex) { }
  }  
}
