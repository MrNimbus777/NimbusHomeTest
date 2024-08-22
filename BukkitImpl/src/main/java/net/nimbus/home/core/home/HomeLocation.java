package net.nimbus.home.core.home;

import net.nimbus.home.Utils;
import net.nimbus.home.core.events.HomeRemoveEvent;
import net.nimbus.home.core.events.HomeTeleportEvent;
import net.nimbus.homeapi.Home;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class HomeLocation implements Home {
    final Player holder;
    Location location;
    final String id;
    public HomeLocation(Player holder, Location location, String id){
        this.holder = holder;
        this.location = location;
        this.id = id;
    }

    public net.nimbus.homeapi.Location getLocation() {
        return Utils.adapt(location);
    }

    public String getId() {
        return id;
    }

    public Player getHolder() {
        return holder;
    }

    public boolean teleport(Player player){
        HomeTeleportEvent event = new HomeTeleportEvent(this, player, player.getLocation().clone());
        Bukkit.getServer().getPluginManager().callEvent(event);
        if(event.isCancelled()) return false;
        player.teleport(location);
        return true;
    }

    @Override
    public boolean teleport(){
        return teleport(holder);
    }

    @Override
    public boolean save() {
        return Homes.save(holder.getUniqueId());
    }

    public boolean remove(Player initiator){
        HomeRemoveEvent event = new HomeRemoveEvent(this, initiator);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if(event.isCancelled()) return false;
        Homes.unregister(this);
        return true;
    }

    @Override
    public boolean remove(){
        return remove(holder);
    }

    @Override
    public String toString() {
        return id+","+
                location.getWorld().getName()+","+
                location.getX()+","+
                location.getY()+","+
                location.getZ()+","+
                location.getYaw()+","+
                location.getPitch();
    }
    public static HomeLocation fromString(Player player, String s) {
        String[] split = s.split(",");
        if(split.length != 7) return null;

        try {
            String id = split[0];
            World world = Bukkit.getWorld(split[1]);
            double x = Double.parseDouble(split[2]);
            double y = Double.parseDouble(split[3]);
            double z = Double.parseDouble(split[4]);
            float yaw = Float.parseFloat(split[5]);
            float pitch = Float.parseFloat(split[6]);

            return new HomeLocation(player, new Location(world, x, y, z, yaw, pitch), id);
        } catch (Exception e) {
            return null;
        }
    }
}
