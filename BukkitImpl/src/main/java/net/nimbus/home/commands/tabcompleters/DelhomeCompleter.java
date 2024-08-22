package net.nimbus.home.commands.tabcompleters;

import net.nimbus.home.core.home.HomeLocation;
import net.nimbus.home.core.home.Homes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DelhomeCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> result = new ArrayList<>();
        if(args.length == 1) {
            if(!(sender instanceof Player p)) return result;
            List<String> options = Homes.get(p.getUniqueId()).stream().map(HomeLocation::getId).toList();
            for(String option : options) {
                if(option.toLowerCase().startsWith(args[0].toLowerCase())) {
                    result.add(option);
                }
            }
        }
        return result;
    }
}