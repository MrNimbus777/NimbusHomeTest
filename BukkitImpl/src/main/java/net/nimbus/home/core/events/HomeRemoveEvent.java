package net.nimbus.home.core.events;

import net.nimbus.home.core.home.HomeLocation;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class HomeRemoveEvent extends Event implements Cancellable {
    final HomeLocation home;
    final Player player;
    HandlerList handlerList;
    boolean isCancelled;

    public HomeRemoveEvent(HomeLocation home, Player player){
        this.home = home;
        this.player = player;

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
}
