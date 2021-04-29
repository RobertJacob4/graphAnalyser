package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.util.*;

public class Utilities {

    public static ObservableList<Landmark> landmarks;
    public static ArrayList<GraphNodeAL> graphlist;
    public static ArrayList<GraphNodeAL> waypoints;
    public static ArrayList<GraphNodeAL> avoids;

    public static void createLandmarkList() {
        landmarks = FXCollections.observableArrayList();
    }


    public static void createGraphList() {
        graphlist = new ArrayList<>();
        waypoints = new ArrayList<>();
        avoids = new ArrayList<>();
    }

    public static void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("graphlist.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(graphlist);
            out.close();
            System.out.println("The Object  was succesfully written to a file");
//            fileOut.close();
        } catch( Exception e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        try {
            FileInputStream fileIn = new FileInputStream("graphlist.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            graphlist.addAll((ArrayList)in.readObject());
            fileIn.close();
            in.close();
        } catch (Exception e) {
            System.out.println("Load Failure");
        }
    }

}
