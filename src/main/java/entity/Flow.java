package entity;

import configuration.CreditOrigin;

public class Flow {
    private String flowId;
    private CreditOrigin creditOrigin;
    private float amount;
    private String description;

    public Flow(String flowId, CreditOrigin creditOrigin, float amount, String description) {
        this.flowId = flowId;
        this.creditOrigin = creditOrigin;
        this.amount = amount;
        this.description = description;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public CreditOrigin getCreditOrigin() {
        return creditOrigin;
    }

    public void setCreditOrigin(CreditOrigin creditOrigin) {
        this.creditOrigin = creditOrigin;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
