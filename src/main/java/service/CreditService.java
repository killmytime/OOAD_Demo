package service;

import entity.Commodity;
import entity.Task;
import entity.User;

public interface CreditService {
    public void taskCalculation(Task task, User user);
    public boolean exchangeCalculation(Commodity commodity,User user);
//    public void purchaseCalculation(Float purchaseMoney,User user);
}
