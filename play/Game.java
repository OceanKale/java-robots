package robots.play;

import robots.world.Package;
import robots.world.*;
import robots.commands.*;
import robots.view.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.regex.Pattern;

/**
   Class Game plays one game. If the game is online, communication with the server occurs 
   through a Scanner and PrintWriter. The state of an online game is captured in the following data structures:

*/
public class Game {

    private Board board;
    private PlayerRobot player;
    private Robot rob;
    private Integer id;
    private int startMoney;
    private PackageCollection packages;
    private boolean online;
    private boolean gooey = false;
    private GUI gui;
    private Scanner fromServer;
    private PrintWriter toServer;
    private HashMap<Integer,Robot> allRobots = new HashMap<Integer,Robot>();
    private TargetChooser chooser;

    /**
       Creates an offline game with the given board and player.
    */
    public Game(Board board, PlayerRobot player) {
	this.board = board;
	this.player = player;
	online = false;
        allRobots.put(player.getId(), player);
	chooser = new TargetChooser(this);

    }

    /**
       Creates an online game that communicates with the server via the given Scanner and PrintWriter.
       Initiates the connection by sending the word Player and waiting for the server to respond with 
       the board configuration, followed by the robot controlled by this game, followed by all other 
       robots currently known to the server. Creates the Board, the PlayerRobot, and the allRobots 
       collection from the server information. 
    */
    public Game(Scanner fromServer, PrintWriter toServer) {
	this.fromServer = fromServer;
	this.toServer = toServer;
	online = true;
	toServer.println("Player");
	this.board = Board.read(fromServer);
        //System.out.println("Board read successfully"); // for testing
	this.player = PlayerRobot.read(fromServer);
        allRobots.put( new Integer( player.getId() ), player );
        //System.out.println("Player Robot read successfully"); // for testing
        this.startMoney = player.getMoney();
        fromServer.nextLine();
        update(fromServer.nextLine());
	
    }

    /**
       Returns true if this game is online, and false otherwise.
    */
    public boolean isOnline() {
	return online;
    }

    /**
       Returns a collection of all live robots in this game. 
    */
    public HashMap<Integer, Robot> getAllRobots() {
	return allRobots;
    }

    /**
       Returns the board for this game.
    */
    public Board getBoard() {
	return board;
    }

    /**
       Returns the player robot for this game. 
    */    
    public PlayerRobot getPlayer() {
	return player;
    }

    /**
       Returns true if this game has an associated GUI, and false otherwise.
    */
    public boolean hasGUI() {
	return gooey;
    }

    /**
       Creates a GUI visualization for this game. 

       Parameters:
       delay - number of milliseconds between updates of the GUI
    */
    public void createGUI(int delay) {
	gooey = true;
	gui = new GUI(board);
	gui.setDelay(delay);
    }

    /**
       Enters into a loop that processes rounds of the game until the player robot dies. 
       Does nothing if the game is not online. 
    */
    public void play() {
        System.out.printf("Player starting money: %d\n", player.getMoney());
	if(online == true){
	    while(!player.isDead()) {
		round();
                gui.update();
	    }
	}
        System.out.printf("Player ending money: %d\n", player.getMoney());
	System.out.printf("Player ending score: %d\n", player.getScore());
        System.out.println("Game over, sucka.");
    }

    /**
       Process one round of the game, which consists of three steps.

       1. Server sends a PackageCollection that describes all packages at the player's current location. 
       This collection is stored in the board.
       2. Client uses the installed robot brain to decide on a next command for the player robot and then 
       sends the selected command to the server.
       3. Server sends an update response. The information in the response is extracted and incorporated 
       into the client's data structures.

       Does nothing if the game is not online. 
    */
    public void round() {
	if(online == true) {
            //System.out.printf("Packages from server: %s\n",fromServer.nextLine());
            packages = PackageCollection.read(new Scanner(fromServer.nextLine()));
            //System.out.printf("Packages on current tile: %s\n", packages.toString());
            player.getTile().getPackageCollection().clear();
            player.getTile().getPackageCollection().add(packages);
	    Command nextCommand = player.getBrain().next();
            System.out.printf("Player robot command: %s\n",nextCommand.toString());
            Scanner cmdStr = new Scanner(nextCommand.toString());
            int prevBid = cmdStr.nextInt();
            player.pay(prevBid);
            toServer.println(nextCommand.toString());
	    update(fromServer.nextLine());
	}
    }

    /**
       This method is called at the end of each round to parse the server response and update 
       the game's data structures accordingly. The server response includes information about 
       all the robots and their actions. 
    */
    public void update(String response) {
        String dirs = "NSEW";
	Scanner serverLine = new Scanner(response);
        ArrayList<Integer> idsSeen = new ArrayList<Integer>(); // store ids of robots listed in the update

        //System.out.printf("Update called with: %s\n", response);

        while(serverLine.hasNext()) {
            String nextToken = serverLine.next();

            if (nextToken.charAt(0) == '#') { // id for a robot
                String idString = nextToken;
                //System.out.printf("idString: %s\n", idString);
                idString = idString.substring(1);
                id = Integer.valueOf(idString);
                idsSeen.add(id);
                if ( allRobots.containsKey(id) ) {
                    rob = allRobots.get(id);
                } else {
                    rob = null;
                }
            } 
            else if (nextToken.compareTo("X") == 0) { // move the robot (create new robot if necessary) 
                String robCol = serverLine.next();
                Integer col = Integer.valueOf(robCol);
                serverLine.next();
                String robRow = serverLine.next();
                Integer row = Integer.valueOf(robRow);
                Location robLoc = new Location (col, row);
                if ( ! allRobots.containsKey(id) ) { // new robot
                    int carrying = player.getMaximumCarryingCapacity();
                    int money = this.startMoney;
                    rob = new Robot (id.intValue(), carrying, money);
                    allRobots.put(id, rob);
                } 
                rob.move(board.get(robLoc));
                //System.out.printf("Robot: %d moved to location: %s\n", id, robLoc.toString());
            } 
            else if (dirs.indexOf(nextToken) >= 0) { // move N, S, E or W
                char dir = nextToken.charAt(0);
                switch(dir) {
                    case 'N': rob.move(board.adjacentTile(rob.getTile(), Direction.NORTH)); break;
                    case 'S': rob.move(board.adjacentTile(rob.getTile(), Direction.SOUTH)); break;
                    case 'E': rob.move(board.adjacentTile(rob.getTile(), Direction.EAST));  break;
                    case 'W': rob.move(board.adjacentTile(rob.getTile(), Direction.WEST));  break;
                    default: throw new RuntimeException("Impossible server direction during game.");
                }
		//                System.out.printf("Robot %d moved to location %s\n", rob.getId(), rob.getTile().getLocation().toString());
            }
            else if (nextToken.compareTo("P") == 0) { // pick
                String packIdStr = serverLine.next();
                int packId = Integer.valueOf(packIdStr).intValue();
                PackageCollection tilePackages = rob.getTile().getPackageCollection();
		Package pack = tilePackages.get(packId);
                rob.pick(pack);
            }
            else if (nextToken.compareTo("D") == 0) { // drop
                String packIdStr = serverLine.next();
                int packId = Integer.valueOf(packIdStr).intValue();
                rob.drop(packId);
            }
            else {
                System.out.printf("%s\n", response );
                break;
            }
	}
        
        // kill robots not listed in the update - these robots are dead

        Iterator<Integer> it = allRobots.keySet().iterator();
        ArrayList<Integer> robsToRemove = new ArrayList<Integer>();
        while( it.hasNext() ) {
            Integer i = it.next();
            //System.out.printf("%d ", i);
            if (idsSeen.indexOf(i) == -1) { 
                Robot robToKill = allRobots.get(i);                
                robToKill.die();
                //System.out.printf("Robot %d is dead\n", robToKill.getId());
                robsToRemove.add(i);
                //allRobots.remove(i);
            }
            //System.out.println();
        }
        Iterator<Integer> it2 = robsToRemove.iterator();
        while(it2.hasNext()) {
            Integer i = it2.next();
            allRobots.remove(i);
        }
    }
}
