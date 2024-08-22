package net.nimbus.home.events.player;

import net.nimbus.home.core.home.Homes;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinEvents implements Listener {
    @EventHandler
    public void onEvent(PlayerJoinEvent e){
        Homes.load(e.getPlayer().getUniqueId());
    }
}
