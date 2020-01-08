package configuration;

public enum CommodityInfo {
    Book("book",20),Water("cola",5),Bag("bag",13),Tissue("tissue",10),bottle("botle",18);
    private String name;
    private float credits;

    CommodityInfo(String name, float credits) {
        this.name = name;
        this.credits = credits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCredits() {
        return credits;
    }

    public void setCredits(float credits) {
        this.credits = credits;
    }
}
