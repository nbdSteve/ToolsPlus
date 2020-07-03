package gg.steve.mc.tp.attribute.utils;

public class CooldownUtil {
    private String tool;
    private int duration;
    private Long end;

    public CooldownUtil(String tool, int duration) {
        this.tool = tool;
        this.duration = duration;
        this.end = System.currentTimeMillis() + (this.duration * 1000);
    }

    public boolean isActive() {
        return ((end - System.currentTimeMillis()) / 1000) > 0;
    }

    public String getTool() {
        return tool;
    }

    public int getDuration() {
        return duration;
    }

    public Long getRemaining() {
        return (end - System.currentTimeMillis()) / 1000;
    }
}
