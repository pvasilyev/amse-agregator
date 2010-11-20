package ru.amse.agregator.searcher;

import java.util.Vector;

/*
 * Author: Bondarev Timofey
 * Date: Oct 24, 2010
 * Time: 2:32:27 PM
 */

public class UserQuery {
    private String query;
    private Vector<String> labels;

    public UserQuery(String queryExpression) {
        query = queryExpression;
        labels = new Vector<String>();
    }

    public UserQuery(String queryExpression, Vector<String> labels) {
        query = queryExpression;
        this.labels = new Vector<String>(labels);
    }

    public void setQueryExpression(String queryExpression) {
        query = queryExpression;
    }

    public void setLabels(Vector<String> labels) {
        this.labels = labels;
    }

    public void addLabel(String label) {
        labels.add(label);
    }

    public String getQueryExpression() {
        return query;
    }

    public Vector<String> getLabels() {
        return labels;
    }
}
