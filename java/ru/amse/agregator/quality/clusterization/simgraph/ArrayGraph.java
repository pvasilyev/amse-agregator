package ru.amse.agregator.quality.clusterization.simgraph;

import java.util.ArrayList;
import java.util.Iterator;
import org.bson.types.ObjectId;
import ru.amse.agregator.quality.clusterization.metric.Metric;

/**
 *
 * @author pavel
 */
final public class ArrayGraph extends Graph {

    private ArrayList<Edge> edges = new ArrayList<Edge>();
    private Iterator iterator;

    public ArrayGraph(Metric metric, double threshold) {
        super(metric, threshold);
    }

    public ArrayGraph(Metric metric, double threshold, ArrayList<ObjectId> objects) {
        super(metric, threshold, objects);
    }

    protected void addEdge(Edge edge) {
        edges.add(edge);
    }

    protected void addEdges(ArrayList<Edge> edges) {
        this.edges.addAll(edges);
    }

    public void startIterating() {
        iterator = edges.iterator();
    }

    public Edge getNextEdge() {
        return (Edge)iterator.next();
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public int getEdgeCount() {
        return edges.size();
    }
}
