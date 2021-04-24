package sample;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.*;
import java.util.ArrayList;

public class Utilities {

    public static ArrayList<GraphNodeAL> graphList;
    public static ArrayList<GraphNodeAL> wayPoints;
    public static ArrayList<GraphNodeAL> avoidPoint;


    public void createNodes(){
        graphList = new ArrayList<>();
        wayPoints = new ArrayList<>();
        avoidPoint = new ArrayList<>();
    }

    public static void load() throws Exception
    {
        XStream xstream = new XStream(new DomDriver());
        ObjectInputStream is = xstream.createObjectInputStream(new FileReader("graphlist.xml"));
        graphList = (ArrayList<GraphNodeAL>) is.readObject();
        is.close();
    }

    public static void save() throws Exception
    {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("graphlist.xml"));
        out.writeObject(graphList);
        out.close();
    }

}
