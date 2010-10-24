package ru.amse.agregator.searcher;

/*
 * Author: Bondarev Timofey
 * Date: Oct 24, 2010
 * Time: 2:32:27 PM
 */

public class Query {
    private String query;
    private String[] labels;

    public void setQueryExpression(String queryExpression) {
        query = queryExpression;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    public void addLabel(String label) {
        labels[labels.length + 1] = label;
    }

    public String getQueryExpression() {
        return query;
    }

    public String[] getLabels() {
        return labels;
    }
}
