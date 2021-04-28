package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BreadthFirstSearch {


    public static ArrayList<Integer> breadthFirstSearch(GraphNodeAL startNode, GraphNodeAL destNode, int width, int[] graphArray) {
        ArrayList<Integer> agenda = new ArrayList<>();

        int destIndex = (int) ((destNode.y - 1) * width + destNode.x);
        int startIndex = (int) ((startNode.y - 1) * width + startNode.x);
        agenda.add(startIndex);
        graphArray[startIndex] = 1;
        int v, current;
        do {
            current = agenda.remove(0);
            if (current == destIndex) {
                int totalDistance = graphArray[current] - 1;
                ArrayList<Integer> newPath = new ArrayList<>();
                int cn = destIndex;
                v = graphArray[destIndex];
                if (cn == startIndex) {
                    return newPath;
                } else {
                    newPath.add(cn);
                    do {
                        if (cn - 1 >= 0 && cn - 1 % width != 0 && graphArray[cn - 1] == v - 1) {
                            cn = cn - 1;
                            v = v - 1;
                            newPath.add(cn);
                        } else if (cn + 1 < graphArray.length && cn % width != 0 && graphArray[cn + 1] == v - 1) {
                            cn = cn + 1;
                            v = v - 1;
                            newPath.add(cn);
                        } else if (cn - width >= 0 && graphArray[cn - width] == v - 1) {
                            cn = cn - width;
                            v = v - 1;
                            newPath.add(cn);
                        } else if (cn + width < graphArray.length && graphArray[cn + width] == v - 1) {
                            cn = cn + width;
                            v = v - 1;
                            newPath.add(cn);
                        }
                    } while (cn != startIndex);

                    System.out.println(totalDistance);
                    return newPath;
                }
            } else {
                v = graphArray[current];
                if (current + 1 < graphArray.length && current % width != 0 && graphArray[current + 1] == 0) {
                    graphArray[current + 1] = v + 1;
                    agenda.add(current + 1);
                }
                if (current - 1 >= 0 && current - 1 % width != 0 && graphArray[current - 1] == 0) {
                    graphArray[current - 1] = v + 1;
                    agenda.add(current - 1);
                }
                if (current + width < graphArray.length && graphArray[current + width] == 0) {
                    graphArray[current + width] = v + 1;
                    agenda.add(current + width);
                }
                if (current - width >= 0 && graphArray[current - width] == 0) {
                    graphArray[current - width] = v + 1;
                    agenda.add(current - width);
                }
            }
        }
        while (!agenda.isEmpty());
        return null;
    }


    public static <T> List<GraphNodeAL<?>> findPathBreadthFirst(GraphNodeAL<?> startNode, T lookingFor) {
        List<List<GraphNodeAL<?>>> agenda = new ArrayList<>(); // agenda comprised of a path lists here
        List<GraphNodeAL<?>> firstAgendaPath = new ArrayList<>(), resultPath;
        firstAgendaPath.add(startNode);
        agenda.add(firstAgendaPath);
        resultPath = findPathBreadthFirst(agenda, null, lookingFor);
        Collections.reverse(resultPath);
        return resultPath;
    }

    public static <T> List<GraphNodeAL<?>> findPathBreadthFirst(List<List<GraphNodeAL<?>>> agenda, List<GraphNodeAL<?>> encountered, T lookingfor) {
        if (agenda.isEmpty()) return null; //Search failed
        List<GraphNodeAL<?>> nextPath = agenda.remove(0); //Get first item (next path to consider) off agenda
        GraphNodeAL<?> currentNode = nextPath.get(0); //The first item in the next path is the current node
        if (currentNode.data.equals(lookingfor))
            return nextPath; //If that's the goal, we've found our path (so return it)
        if (encountered == null)
            encountered = new ArrayList<>(); //First node considered in search so create new (empty)encountered list
        encountered.add(currentNode); //Record current node as encountered so it isn't revisited again
        for (GraphNodeAL<?> adjNode : currentNode.adjNodeList) //For each adjacent node
            if (!encountered.contains(adjNode)) { //If it hasn't already been encountered
                List<GraphNodeAL<?>> newPath = new ArrayList<>(nextPath); //Create a new path list as a copy of
//the current/next path
                newPath.add(0, adjNode); //And add the adjacent node to the front of the new copy
                agenda.add(newPath); //Add the new path to the end of agenda (end->BFS!)
            }
        return findPathBreadthFirst(agenda, encountered, lookingfor); //Tail call
    }

}
