package sample;
import java.io.Serializable;

public class GraphLinkAL implements Serializable{

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

    public GraphNodeAL<?> getStartNode() {
        return startNode;
    }

    public void setStartNode(GraphNodeAL<?> startNode) {
        this.startNode = startNode;
    }

    public GraphNodeAL<?> getDestNode() {
        return destNode;
    }

    public void setDestNode(GraphNodeAL<?> destNode) {
        this.destNode = destNode;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isEasiest() {
        return easiest;
    }

    public void setEasiest(boolean easiest) {
        this.easiest = easiest;
    }

    public boolean isHistorical() {
        return historical;
    }

    public void setHistorical(boolean historical) {
        this.historical = historical;
    }
}
