package net.nimbus.home.commands.executors;

import net.nimbus.home.core.home.HomeLocation;
import net.nimbus.home.core.home.Homes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelhomeExe implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0) {
            return true;
        }
        if(!(sender instanceof Player p)) {
            return true;
        }

        String id = args[0];
        HomeLocation homeLocation = Homes.get(p.getUniqueId(), id);
        if(homeLocation == null) {
            return true;
        }

        if(homeLocation.remove()){
            // success
        } else {
            // failure
        }
        return true;
    }
}
