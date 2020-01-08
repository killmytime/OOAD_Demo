package configuration;

public enum CreditOrigin {
    //对应默认状态，完成任务，兑换商品，系统福利，购买行为部分
    //其中默认状态为保留状态，系统福利，购买行为这里不考虑
    DEFAULT,TASK,EXCHANGE,SYSTEM,PURCHASE;
}
