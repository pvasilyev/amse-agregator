package ru.amse.agregator.searcher;

/*
 * Author: Bondarev Timofey
 * Date: Oct 24, 2010
 * Time: 2:32:27 PM
 */

public class UserQuery {
    private String query;
    private String[] labels;

    public UserQuery(String queryExpression) {
        query = queryExpression;
        labels = new String[0];
    }

    public UserQuery(String queryExpression, String[] labels) {
        query = queryExpression;
        System.arraycopy(this.labels, 0, labels, 0, labels.length);
        System.out.println(this.labels[3]);
    }

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