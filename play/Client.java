package robots.play;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.ConnectException;

/**
   
   Class Client tests the server connection.
*/
public class Client {
  
  /**
     Usage: java robots.test.Client HOSTNAME PORT
  */
    public Client(){
    }

    /**
       Establishes the server connection and plays the game.
    */
  public static void main (String[] args) {
    if (args.length != 2) {
      System.out.printf("Usage: java robots.test.Client HOSTNAME PORT%n");
      System.exit(-1);
    }
    String hostname = args[0];
    int port = Integer.parseInt(args[1]);

    try {
      Socket socket = new Socket(hostname, port);
      System.out.printf("%nRobots server is up and running on %s:%d.%n%n",
                        hostname, port);

      /***********************************************************/
      /* Create a Game and then play it. */
      /***********************************************************/
      Scanner fromServer = new Scanner(socket.getInputStream());
      PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
      Game game = new Game(fromServer, toServer);
      //Brain brain = new CycleBrain(game);
      //Brain brain = new WanderBrain(5,5,5,game);
      Brain brain = new PlottingBrain(game);
      game.getPlayer().setBrain(brain);
      game.createGUI(250);
      game.play();
      socket.close();
    }
    catch (UnknownHostException e) {
      System.err.printf("%nHostname %s is unknown.%n%n", hostname);
    }
    catch (ConnectException e) {
      System.err.printf("%nRobots server is not responding. " +
                        "Try again in a few minutes.%n" +
                        "Post a note on the message board if the " +
                        "situation persists.%n%n");
    }
    catch (IOException e) {
      System.out.printf("Unexpected IO exception...%n");
      e.printStackTrace();
    }
  }
}

