package net.nimbus.home.events.player;

import net.nimbus.home.core.home.Homes;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitEvents implements Listener {
    @EventHandler
    public void onEvent(PlayerQuitEvent e) {
        Homes.unload(e.getPlayer().getUniqueId());
    }
}
