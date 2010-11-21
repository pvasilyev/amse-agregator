package ru.amse.agregator.quality.clusterization.simgraph;

import ru.amse.agregator.quality.clusterization.metric.Metric;
import ru.amse.agregator.storage.DBWrapper;

import java.io.Serializable;
import java.util.ArrayList;

import org.bson.types.ObjectId;
import ru.amse.agregator.storage.DataBase;


/**
 *
 * @author pavel
 */
abstract public class Graph extends Object implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Metric metric;
    private double threshold;

    public Graph(Metric met, double thresh) {
        metric = met;
        threshold = thresh;
    }

    public Graph(Metric met, double thresh, ArrayList<ObjectId> objs) {
        this(met, thresh);
        build(objs);
    }

    // inner class representing an edge of a graph
    static public class Edge extends Object implements Serializable {

		private static final long serialVersionUID = 1L;
		
		final public ObjectId obj1, obj2;
        final public double weight;

        public Edge(ObjectId objId1, ObjectId objId2, double edgeWeight) {
            obj1 = objId1;
            obj2 = objId2;
            weight = edgeWeight;
        }
    }

    final public void build(ArrayList<ObjectId> objects) {
        //compare all pairs of objects and put in the graph those under the threshold
        int i = 0;
        for (ObjectId id1 : objects) {
            DBWrapper object1 = DataBase.getAttractionById(id1);
            int j = 0;
            for (ObjectId id2 : objects) {
                if (j >= i) {
                    break;
                }
                DBWrapper object2 = DataBase.getAttractionById(id2);
                double distance = metric.compute(object1, object2);
                if (distance < threshold) {
                    addEdge(new Edge(id1, id2, distance));
                }
                ++j;
            }
            ++i;
        }
    }

    abstract protected void addEdge(Edge edge);

    abstract protected void addEdges(ArrayList<Edge> edges);

    abstract public void startIterating();

    abstract public boolean hasNext();

    abstract public Edge getNextEdge();

    abstract public int getEdgeCount();
    
}
