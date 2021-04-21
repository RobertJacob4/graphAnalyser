package sample;

import java.util.ArrayList;
import java.util.List;

public class CostedPath {

    public int pathCost = 0;
    public List<GraphNodeAL<?>> pathList = new ArrayList<>();

    public int getPathCost() {
        return pathCost;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    public List<GraphNodeAL<?>> getPathList() {
        return pathList;
    }

    public void setPathList(List<GraphNodeAL<?>> pathList) {
        this.pathList = pathList;
    }
}
