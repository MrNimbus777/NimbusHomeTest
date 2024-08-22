package net.nimbus.home.core.home;

import net.nimbus.home.MySQLConnection;
import net.nimbus.home.core.events.HomeSetEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class Homes {
    private static final Map<UUID, Map<String, HomeLocation>> map = new HashMap<>();

    public static HomeLocation get(UUID uuid, String id) {
        return map.getOrDefault(uuid, new HashMap<>()).getOrDefault(id, null);
    }
    public static List<HomeLocation> get(UUID uuid) {
        return new ArrayList<>(map.getOrDefault(uuid, new HashMap<>()).values());
    }

    public static boolean register(HomeLocation homeLocation) {
        HomeSetEvent event = new HomeSetEvent(homeLocation, homeLocation.getHolder());
        if(event.isCancelled()) return false;
        Map<String, HomeLocation> homes = map.getOrDefault(homeLocation.getHolder().getUniqueId(), new HashMap<>());
        homes.put(homeLocation.getId(), homeLocation);
        map.put(homeLocation.getHolder().getUniqueId(), homes);
        return true;
    }
    public static void unregister(HomeLocation homeLocation){
        Map<String, HomeLocation> homes = map.get(homeLocation.getHolder().getUniqueId());
        if(homes == null) return;
        if(!homes.containsKey(homeLocation.getId())) return;
        homes.remove(homeLocation.getId());
        map.put(homeLocation.getHolder().getUniqueId(), homes);
    }

    public static void load(UUID uuid){
        Player holder = Bukkit.getPlayer(uuid);
        String save = MySQLConnection.getString("nh_homes", "uuid", uuid.toString(), "homes");
        List<HomeLocation> list = Arrays.stream(save.split(";"))
                .map(s -> HomeLocation.fromString(holder, s))
                .toList();
        if(list.isEmpty()) return;
        Map<String, HomeLocation> homes = new HashMap<>();
        list.forEach(h -> homes.put(h.getId(), h));
        map.put(uuid, homes);
    }

    public static void save(UUID uuid) {
        String save = map.getOrDefault(uuid, new HashMap<>()).values().stream().map(Object::toString).collect(Collectors.joining(";"));
        MySQLConnection.set("nh_homes", "uuid", uuid.toString(), "homes", save);
    }

    public static void unload(UUID uuid){
        save(uuid);
        map.remove(uuid);
    }

    public static void unload(){
        map.keySet().forEach(Homes::save);
        map.clear();
    }

}
