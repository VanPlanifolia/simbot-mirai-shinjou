package love.simbot.example.tool;

import java.util.Timer;

public class TimerPlus extends Timer {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    String name;
    String startTime;

}
