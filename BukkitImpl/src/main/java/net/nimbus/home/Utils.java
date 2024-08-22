package net.nimbus.home;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Utils {
    public static Location adapt(net.nimbus.homeapi.Location location) {
        return new Location(Bukkit.getWorld(location.getWorld()), location.getX(), location.getY(), location.getZ());
    }
    public static net.nimbus.homeapi.Location adapt(Location location) {
        return new net.nimbus.homeapi.Location(location.getWorld().getName(), location.getX(), location.getY(), location.getZ());
    }
}
