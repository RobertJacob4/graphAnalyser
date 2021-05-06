package sample;

import java.io.Serializable;

public class GraphLinkAL implements Serializable {
    public GraphNodeAL<?> startNode, destNode;
    public int cost;
    public boolean easiest;
    public boolean historical;

    public <T> GraphLinkAL(GraphNodeAL<?> startNode, GraphNodeAL<?> destNode, int cost)  {
        this.startNode = startNode;
        this.destNode = destNode;
        this.cost = cost;
    }


    public <T> GraphLinkAL(GraphNodeAL<?> startNode, GraphNodeAL<?> destNode, int cost, boolean hist, boolean easy) {
        this.startNode = startNode;
        this.destNode = destNode;
        this.cost = cost;
        this.historical = hist;
        this.easiest = easy;
    }

    @Override
    public String toString() {
        return "GraphLinkAL{" +
                "startNode=" + startNode +
                ", destNode=" + destNode +
                ", cost=" + cost +
                ", easiest=" + easiest +
                ", historical=" + historical +
                '}';
    }
}