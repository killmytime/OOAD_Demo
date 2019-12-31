package configuration;

public enum TaskStatus {
    //这里描述如果觉得词不达意，全局修改一下就好了
    OFFLINE(0), POST(1), PROCESSING(2), FINISH(3), ABANDON(-1),DELETE(-2,);

    private int value;

    TaskStatus(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }

}
