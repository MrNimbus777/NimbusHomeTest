package net.nimbus.home.core.events;

import net.nimbus.home.core.home.HomeLocation;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class HomeTeleportEvent extends Event implements Cancellable {
    final HomeLocation home;
    final Player player;
    final Location from;
    HandlerList handlerList;
    boolean isCancelled;

    public HomeTeleportEvent(HomeLocation home, Player player, Location from){
        this.home = home;
        this.player = player;
        this.from = from;

        handlerList = new HandlerList();
    }
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        isCancelled = b;
    }

    public Player getPlayer() {
        return player;
    }
    public HomeLocation getHome() {
        return home;
    }

    public Location getFrom() {
        return from;
    }
}