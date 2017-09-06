
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Uses the A* algorithm to find the shortest path through the capitals from one
 * capital to another.
 *
 * @author Zachary Cohan
 */
public class Navigator {

    private static Scanner in;
    private static HashMap<String, City> cities;
    private static LinkedList<City>[] routes;

    private static LinkedList<Road>[] roads;
    private static final String EXIT = "";
    private static int stateId = 0;

    /**
     * @param args the command line arguments
     *
     *
     */
    public static boolean findPath(City start, City dest) {

        double travelled = 0;
        City active = null;
        City temp = null;
        HashMap<City, City> closedList = new HashMap<>();
        //LinkedList<City> closedList = new LinkedList<>();
        PriorityQueue<City> openList = new PriorityQueue();
        openList.add(start);

        while (!openList.isEmpty()) {
            temp = active;
            active = openList.poll();

//            System.out.println(active.name + " made ACTIVE node");

            if (closedList.containsKey(active)) {
                continue;
            }
            if (active.equals(dest)) {
                City c = dest;
                System.out.print(c.name);
                c = c.getReturnCity();
                Loop:
                while (c != null && c != start) {
                    //System.out.println("WE HAVE REACHED OUR DESTINATION");
//                    System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX        "+c.name);
//                    System.out.println();
//                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@        "+closedList.get(c).name);
                    System.out.print(" <-- " + c.name);
                    c = c.getReturnCity();

                }
                System.out.println(" <-- " + start.name);
                return true;
            }
//            System.out.println("The total distance to get to " + active.name + " is " + active.runningTotal);
            for (City c : active.getAdjList()) {
                if (!closedList.containsKey(c)) {
                    c.calcDist(dest);
                    c.score = active.runningTotal + c.dist + active.adjList.get(c);
                    c.runningTotal = active.runningTotal + active.adjList.get(c);
                    openList.add(c);
                    c.setReturnCity(active);
//                    System.out.println(c.name + " added to the OpenList with a running Total of " + c.runningTotal + " ,a distance of " + c.dist + " from " + dest.name + ", and a");
//                    System.out.println("score of " + c.score + ", and we got there from " + active.name);
                }

            }
//            System.out.println(active);
//            System.out.print("xxxxxxxxxxxxxxxxxxxxxxxx ");
//            System.out.println(temp);
            closedList.put(active, temp);

            //System.out.println(active.name + " added to the ClosedList");
        }

        System.out.println("There is no way to get from " + start.name + " to " + dest.name);
        return false;
    }

    public static void main(String[] args) {
        if (args.length == 1) {
            try {
                in = new Scanner(new File(args[0]));
            } catch (FileNotFoundException e) {
                System.out.println("File \"" + args[0] + "\" could not be located.");
            }
        } else {
            File file = new File("US-Capitals.geo");
            try {
                in = new Scanner(file);
            } catch (FileNotFoundException e) {
                System.out.println("Default File Not Located (US-Capitals.geo)");
            }
        }
        cities = new HashMap();
        while (in.hasNextLine()) {
            //get the next line, break it up, add a new city to the hash table based on the stats from the file
            String s = in.nextLine();
            if (s.equals("")) {
                break;
            }
            String[] stats = s.split("\t");
            double lt = Double.parseDouble(stats[1]);
            lt = Math.toRadians(lt);
            double lg = Double.parseDouble(stats[2]);
            lg = Math.toRadians(lg);
            City city = new City(stats[0], lt, lg, stateId++);
            cities.put(stats[0], city);
            //        System.out.println(city.toString());
        }
        //  roads = new LinkedList[cities.size()];
        routes = new LinkedList[cities.size()];
        for (int i = 0; i < cities.size(); i++) {
            //roads[i] = new LinkedList();
            routes[i] = new LinkedList();
        }
        //create an array of linkedLists as big as the city hash table

        while (in.hasNextLine()) {

            String s = in.nextLine();
            String[] stats = s.split("\t");
            City c1 = cities.get(stats[0]);
            City c2 = cities.get(stats[1]);
            double dist = Double.parseDouble(stats[2]);
            //if either city doesnt have a linked list, create one
            //and put in the the index of the state Id
//            routes[c1.id()].add(c2);
//            routes[c2.id()].add(c1);
//            roads[c1.id()].add(new Road(c1,c2,dist));
//            roads[c2.id()].add(new Road(c2,c1,dist));
            c1.addToAdjList(c2, dist);
            c2.addToAdjList(c1, dist);
        }

        in = new Scanner(System.in);
        String input;
        do {
            input = in.nextLine();
            String[] params = input.split("-");

//            System.out.println(cities.get("Albany"));
//            System.out.println(cities.get("Sacramento"));
//            if(cities.get("Albany").compareTo(cities.get("Sacramento")) < 1)
//                System.out.println(cities.get("Albany").toString());
//            else
//                System.out.println(cities.get("Sacramento").toString());  
            if (input.equals(EXIT)) {
                break;
            }
            System.out.println(findPath(cities.get(params[0]), cities.get(params[1])));

        } while (!input.equals(EXIT));

    }

}
