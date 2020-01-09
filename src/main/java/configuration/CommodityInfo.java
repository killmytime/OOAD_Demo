package configuration;

public enum CommodityInfo {
    Book("book", 100, 20), Water("cola", 20, 5), Bag("bag", 0, 13), Tissue("tissue", 33, 10), bottle("botle", 24, 18);
    private String name;
    private int storage;
    private float credits;

    CommodityInfo(String name,int storage, float credits) {
        this.name = name;
        this.storage=storage;
        this.credits = credits;
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

    public float getCredits() {
        return credits;
    }

    public void setCredits(float credits) {
        this.credits = credits;
    }


}
