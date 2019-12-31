package entity;

import lombok.Data;

import java.util.Date;

@Data
public class DailyTask extends Task {
    Date finishDate;
    Date today;
}
