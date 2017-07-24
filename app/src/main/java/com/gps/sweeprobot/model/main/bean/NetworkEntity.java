package com.gps.sweeprobot.model.main.bean;

/**
 * Created by admin on 2017/4/17.
 */

public class NetworkEntity extends BaseEntity{

    private String name;
    private int level;

    public NetworkEntity() {
    }

    public NetworkEntity(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "NetworkEntity{" +
                "name='" + name + '\'' +
                ", level=" + level +
                '}';
    }
}
