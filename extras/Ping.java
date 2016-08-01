package robots.test;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.ConnectException;

/**
   Test the server connection.
*/
public class Ping {
  
  /**
     Usage: java robots.test.Ping HOSTNAME PORT
  */
  public static void main (String[] args) {
    if (args.length != 2) {
      System.out.printf("Usage: java robots.test.Ping HOSTNAME PORT%n");
      System.exit(-1);
    }
    String hostname = args[0];
    int port = Integer.parseInt(args[1]);

    try {
      Socket socket = new Socket(hostname, port);
      System.out.printf("%nRobots server is up and running on %s:%d.%n%n",
                        hostname, port);

/***********************************************************/
/***********************************************************/
      
      socket.close();
    }
    catch (UnknownHostException e) {
      System.err.printf("%nHostname %s is unknown.%n%n", hostname);
    }
    catch (ConnectException e) {
      System.err.printf("%nRobots server is not responding. " +
                        "Try again in a few minutes.%n");
    }
    catch (IOException e) {
      System.out.printf("Unexpected IO exception...%n");
      e.printStackTrace();
    }
  }
}

