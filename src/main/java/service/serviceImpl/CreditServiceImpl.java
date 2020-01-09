package service.serviceImpl;

import configuration.CreditOrigin;
import configuration.Global;
import entity.*;
import service.CreditService;

import java.util.Date;
import java.util.Stack;

public class CreditServiceImpl implements CreditService {
    //加积分项，必要的校验在完成部分已经进行
    public void taskCalculation(Task task, User user) {
        user.setCredits(user.getCredits()+task.getRewards());
        Flow newFlow=new Flow(user.getUserId()+String.valueOf(new Date().getTime()) +(++Global.index), CreditOrigin.TASK,task.getRewards(),"完成任务"+task.getTaskName());
        Transaction transaction=user.getTransaction();
        Stack<Flow> flows=transaction.getFlows();
        float total = transaction.getTotal()+task.getRewards();
        flows.push(newFlow);
        transaction.setFlows(flows);
        transaction.setTotal(total);
    }


    public boolean exchangeCalculation(Commodity commodity, User user) {
        if (!commodity.exchangeCommodity()) return false;
        user.setCredits(user.getCredits()-commodity.getCredits());
        Flow newFlow=new Flow(user.getUserId()+String.valueOf(new Date().getTime()) +(++Global.index), CreditOrigin.EXCHANGE,-commodity.getCredits(),"兑换商品"+commodity.getName());
        Transaction transaction=user.getTransaction();
        Stack<Flow> flows=transaction.getFlows();
        float total = transaction.getTotal()-commodity.getCredits();
        flows.push(newFlow);
        transaction.setFlows(flows);
        transaction.setTotal(total);
        return true;
    }
}
