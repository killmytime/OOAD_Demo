package entity;

import java.util.Stack;

public class Transaction {
    private Stack<Flow> flows=new Stack<>();
    private float total;
    private String userId;

    public Stack<Flow> getFlows() {
        return flows;
    }

    public void setFlows(Stack<Flow> flows) {
        this.flows = flows;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
