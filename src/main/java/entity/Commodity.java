package entity;

import java.util.UUID;

public class Commodity {
    private UUID commodityId;
    private String name;
    private int storage;
    private float credits;

    public Commodity(UUID commodityId, String name, int storage, float credits) {
        this.commodityId = commodityId;
        this.name = name;
        this.storage = storage;
        this.credits = credits;
    }

    public UUID getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(UUID commodityId) {
        this.commodityId = commodityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public boolean exchangeCommodity(){
        if (--storage<0) {
            storage++;
            return false;
        }
        return true;
    }
    public float getCredits() {
        return credits;
    }

    public void setCredits(float credits) {
        this.credits = credits;
    }
}
