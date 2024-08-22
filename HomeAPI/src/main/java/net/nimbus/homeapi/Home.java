package net.nimbus.homeapi;

public interface Home {
    boolean teleport();
    boolean save();
    boolean remove();
    Location getLocation();
    String getId();
}
