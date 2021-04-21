package sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GraphNodeAL<T> implements Serializable{

    public T data;
    public int nodeValue;
    public double x,y;

    public ArrayList<GraphLinkAL> adjList;
    public ArrayList<GraphNodeAL> adjNodeList;

    public GraphNodeAL(double x,double y) {
        this.data = data;
        this.adjList = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.nodeValue = getNodeValue();
    }

    public GraphNodeAL(Landmark landmark) {
        this.data = (T) landmark;
        this.adjList = new ArrayList<>();
        this.setNodeValue(this.getNodeValue());
        this.x = landmark.x;
        this.y = landmark.y;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(int nodeValue) {
        this.nodeValue = nodeValue;
    }

    public List<GraphLinkAL> getAdjList() {
        return adjList;
    }

    public void setAdjList(ArrayList<GraphLinkAL> adjList) {
        this.adjList = adjList;
    }

    public T getData() {
        return data;
    }
}
