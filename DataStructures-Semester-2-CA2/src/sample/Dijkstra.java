package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dijkstra {

    public static <T> CostedPath findCheapestPathDijkstra(GraphNodeAL<?> startNode, T lookingfor, String pathModifier){

        CostedPath cp = new CostedPath(); //Create result object for cheapest path
        List<GraphNodeAL<?>> encountered = new ArrayList<>(), unencountered = new ArrayList<>(); //Create encountered/unencountered lists
        startNode.nodeValue = 0; //Set the starting node value to zero
        unencountered.add(startNode); //Add the start node as the only value in the unencountered list to start
        GraphNodeAL<?> currentNode;
        do { //Loop until unencountered list is empty
            currentNode = unencountered.remove(0); //Get the first unencountered node (sorted list, so will have lowest value)

            encountered.add(currentNode); //Record current node in encountered list
            if (currentNode.data.equals(lookingfor)) { //Found goal - assemble path list back to start and return it
                cp.pathList.add(currentNode); //Add the current (goal) node to the result list (only element)
                cp.pathCost = currentNode.nodeValue; //The total cheapest path cost is the node value of the current/goal node
                while (currentNode != startNode) { //While we're not back to the start node...
                    boolean foundPrevPathNode = false; //Use a flag to identify when the previous path node is identified
                    for (GraphNodeAL<?> n : encountered) { //For each node in the encountered list...
                        for (GraphLinkAL e : n.adjList) {  //For each edge from that node...
                            if (e.destNode == currentNode && currentNode.nodeValue - e.cost == n.nodeValue) { //If that edge links to the
//current node and the difference in node values is the cost of the edge -> found path node!
                                cp.pathList.add(0, n); //Add the identified path node to the front of the result list
                                currentNode = n; //Move the currentNode reference back to the identified path node
                                foundPrevPathNode = true; //Set the flag to break the outer loop
                                break; //We've found the correct previous path node and moved the currentNode reference
//back to it so break the inner loop
                            }
                        }
                        if (foundPrevPathNode)
                            break; //We've identified the previous path node, so break the inner loop to continue
                    }
                }
//Reset the node values for all nodes to (effectively) infinity so we can search again (leave no footprint!)
                for (GraphNodeAL<?> n : encountered) {
                    n.nodeValue = Integer.MAX_VALUE;
                }
                for (GraphNodeAL<?> n : unencountered) {
                    n.nodeValue = Integer.MAX_VALUE;
                }
                for (GraphNodeAL n : cp.pathList) {
                    for (Object link : n.adjList
                    ) {
                        GraphLinkAL lnk = (GraphLinkAL) link;
                        if (pathModifier.equals("Historical") && lnk.historical) {
//                            System.out.println("e.cost inside Historical setting back to original mod: " + lnk.cost);
                            lnk.cost = (int) (lnk.cost * 2);
//
                        } else if (pathModifier.equals("Easiest") && lnk.easiest) {
                            lnk.cost = (int) (lnk.cost * 1.25);
//                            System.out.println("e.cost inside easy setting back to original: " + lnk.cost);
                        }
                    }
                }
                return cp; //The costed (cheapest) path has been assembled, so return it!
            }

//We're not at the goal node yet, so...
            for (GraphLinkAL e : currentNode.adjList) //For each edge/link from the current node...
                if (!encountered.contains(e.destNode)) { //If the node it leads to has not yet been encountered (i.e. processed)
//                    System.out.println("Before mod in 'not at goal yet': " + modifier + "\ne.Hist: " + e.historical + "\ne.easy: " + e.easiest);

                    if (pathModifier.equals("Historical") && e.historical) {
                        e.cost = (int) (e.cost * 0.5);

                    } else if (pathModifier.equals("Easiest") && e.easiest) {
                        e.cost = (int) (e.cost * 0.8);
                    }
                    e.destNode.nodeValue = Integer.min(e.destNode.nodeValue, currentNode.nodeValue + e.cost); //Update the node value at the end
                    //of the edge to the minimum of its current value or the total of the current node's value plus the cost of the edge
                    unencountered.add(e.destNode);
                }
            Collections.sort(unencountered, (n1, n2) -> n1.nodeValue - n2.nodeValue); //Sort in ascending node value order
        } while (!unencountered.isEmpty());
        return null; //No path found, so return null
    }


    public static <T> CostedPath findCheapestPathDijkstra2(GraphNodeAL<?> startNode, T lookingFor, ArrayList<GraphNodeAL> avoidList, String modifier) {
        CostedPath cp = new CostedPath();
        List<GraphNodeAL<?>> encountered = new ArrayList<>(), unencountered = new ArrayList<>();
        startNode.nodeValue = 0;
        unencountered.add(startNode);
        GraphNodeAL<?> currentNode;

        if (avoidList.isEmpty()) {
            return findCheapestPathDijkstra(startNode, lookingFor, modifier);
        } else {
            do {
                currentNode = unencountered.remove(0);

                for (Object avoid : avoidList) {
                    if (!currentNode.equals(avoid)) {

                        encountered.add(currentNode);
                        if (currentNode.data.equals(lookingFor)) {
                            cp.pathList.add(currentNode);
                            cp.pathCost = currentNode.nodeValue;
                            while (currentNode != startNode) {
                                boolean foundPrevPathNode = false;
                                for (GraphNodeAL<?> n : encountered) {
                                    for (GraphLinkAL e : n.adjList)
                                        if (e.destNode == currentNode && currentNode.nodeValue - e.cost == n.nodeValue) {
                                            cp.pathList.add(0, n);
                                            currentNode = n;
                                            foundPrevPathNode = true;
                                            break;
                                        }
                                    if (foundPrevPathNode)
                                        break;
                                }
                            }
                            for (GraphNodeAL<?> n : encountered) n.nodeValue = Integer.MAX_VALUE;
                            for (GraphNodeAL<?> n : unencountered) n.nodeValue = Integer.MAX_VALUE;
                            return cp;
                        }
                        for (GraphLinkAL e : currentNode.adjList)
                            if (!encountered.contains(e.destNode)) {
                                if (modifier.equals("Historical") && e.historical) {
                                    System.out.println("e.cost inside Historical mod: " + e.cost);
                                    e.cost = (int) (e.cost * 0.5);
                                } else if (modifier.equals("Easiest") && e.easiest) {
                                    e.cost = (int) (e.cost * 0.8);
                                    System.out.println("e.cost inside easy mod: " + e.cost);
                                }
                                e.destNode.nodeValue = Integer.min(e.destNode.nodeValue, currentNode.nodeValue + e.cost);
                                unencountered.add(e.destNode);
                            }
                    }
                }
                Collections.sort(unencountered, (n1, n2) -> n1.nodeValue - n2.nodeValue); //Sort in ascending node value order
            } while (!unencountered.isEmpty());

            return null;
        }
    }

}
