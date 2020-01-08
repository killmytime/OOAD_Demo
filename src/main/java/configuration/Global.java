package configuration;

import entity.Commodity;
import entity.TaskPool;
import entity.User;

import java.util.ArrayList;

public class Global {
    public static final TaskPool AdminPool=new TaskPool();
    public static final ArrayList<User> userList=new ArrayList<>();
    public static final ArrayList<Commodity> commodities=new ArrayList<>();
    public static int index=1;
}
