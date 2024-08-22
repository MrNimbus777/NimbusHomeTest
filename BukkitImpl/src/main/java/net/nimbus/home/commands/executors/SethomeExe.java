package net.nimbus.home.commands.executors;

import net.nimbus.home.core.home.HomeLocation;
import net.nimbus.home.core.home.Homes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SethomeExe implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0) {
            return true;
        }
        if(!(sender instanceof Player p)) {
            return true;
        }

        String id = args[0].toLowerCase();
        HomeLocation homeLocation = Homes.get(p.getUniqueId(), id);
        if(homeLocation != null){
            return true;
        }

        homeLocation = new HomeLocation(p, p.getLocation().clone(), id);
        if(Homes.register(homeLocation)){
            // success
        } else {
            // failure
        }

        return true;
    }
}