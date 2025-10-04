import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class assignment2 {

    private static void dfs(HashMap<String, ArrayList<String>> adjList) {
        // intialize a hash set to keep track of visited nodes
        // for each key in the adjList, if it isn't visited, then recursively do the dfs
        // initialize arraylist to return
        Object[] keys = adjList.keySet().toArray();
        HashSet<String> visited = new HashSet<>();

        for(int i = 0; i < adjList.size(); i++) {
            
        }
    }

    private static void dfsHelper(String starting, HashMap<String, ArrayList<String>> adjList, HashSet<String> visited) {
        // base case: if the starting node is not in the visited hashset, add it to the visited hashset and add to output
        // for every one of starting nodes neighbors, we're gonna check if it's in visited
        // if not, recursively call dfsHelper on neighbor
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int instances = sc.nextInt(); // take in input for # of trees to be created

        for (int i = 0; i < instances; i++) {
            int nodes = sc.nextInt(); // take in input for # of nodes in each instance of trees
            sc.nextLine();

            HashMap<String, ArrayList<String>> adjList = new HashMap<>(); // instantiating adjacency list for this
                                                                          // instance

            for (int j = 0; j < nodes; j++) {
                String vertices = sc.nextLine(); // take in input for vertices and their adjacent vertices
                String[] split = vertices.split(" ");

                ArrayList<String> adjacencies = new ArrayList<>(split.length); // list of vertex (index 0) and its
                                                                               // adjacent nodes (anything after)
                String key = split[0]; // key for hashmap

                for (int k = 1; k < split.length; k++) { // creating arraylist of adjacent nodes
                    adjacencies.add(split[k]);
                }

                adjList.put(key, adjacencies);
            }
            Object[] keys = adjList.keySet().toArray();
            for(int l = 0; l < keys.length; l++) {
                System.out.println(keys[l]);
            }
            dfs(adjList);
        }
        sc.close();
    }
}