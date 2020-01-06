package entity;

import lombok.Data;

import java.util.ArrayList;

@Data
public class TaskPool {
    private String userId;
    private ArrayList<Task> tasks=new ArrayList<>();
}
