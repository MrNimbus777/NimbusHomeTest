package net.nimbus.home;

import net.nimbus.home.commands.executors.*;
import net.nimbus.home.commands.tabcompleters.*;
import net.nimbus.home.core.home.Homes;
import net.nimbus.home.events.player.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class NHome extends JavaPlugin {
    public static NHome a;

    public void loadConfig(boolean reload){
        File config = new File(getDataFolder(), "config.yml");
        if (!config.exists()) {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
            try {
                getConfig().load(config);
                getLogger().info("Created new config.yml file at " + config.getPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (reload) {
            try {
                getConfig().load(config);
                getLogger().info("Config reloaded.");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public void onEnable() {
        a = this;
        loadConfig(false);

        MySQLConnection.initiate(
                getConfig().getString("MySQL.address"),
                getConfig().getString("MySQL.name"),
                getConfig().getString("MySQL.user"),
                getConfig().getString("MySQL.password"));
        MySQLConnection.createIfNotExists("nh_homes", "uuid", "CHAR(20)", "homes", "MEDIUMTEXT");

        loadEvent(new PlayerJoinEvents());
        loadEvent(new PlayerQuitEvents());

        loadCommand("home", new HomeExe(), new HomeCompleter());
        loadCommand("sethome", new DelhomeExe(), new DelhomeCompleter());
        loadCommand("delhome", new SethomeExe(), new SethomeCompleter());
    }
    public void onDisable(){
        Homes.unload();
        MySQLConnection.close();
    }

    public void loadEvent(Listener listener){
        getServer().getPluginManager().registerEvents(listener, this);
    }

    public void loadCommand(String cmd, CommandExecutor executor) {
        try {
            getCommand(cmd).setExecutor(executor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadCommand(String cmd, CommandExecutor executor, TabCompleter tabCompleter) {
        try {
            loadCommand(cmd, executor);
            getCommand(cmd).setTabCompleter(tabCompleter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
