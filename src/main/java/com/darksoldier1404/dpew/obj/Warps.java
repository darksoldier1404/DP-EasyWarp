package com.darksoldier1404.dpew.obj;

import com.darksoldier1404.dppc.data.DataCargo;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import static com.darksoldier1404.dpew.EasyWarp.delayQueue;
import static com.darksoldier1404.dpew.EasyWarp.plugin;

public class Warps implements DataCargo {
    private String name;
    private Location location;
    private String link;

    public Warps() {
    }

    public Warps(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public Warps(String name, Location location, String link) {
        this.name = name;
        this.location = location;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void teleport(Player p) {
        if (location == null) {
            return;
        }
        if (delayQueue.contains(p.getUniqueId())) {
            return;
        }
        p.teleport(location);
        plugin.delayQueue.add(p.getUniqueId());
        Bukkit.getScheduler().runTaskLater(plugin, () -> plugin.delayQueue.remove(p.getUniqueId()), 60L);
    }

    public void teleportToLink(Player p) {
        if (link != null && !link.isEmpty()) {
            if (plugin.warpsData.containsKey(link)) {
                Warps linkedWarp = plugin.warpsData.get(link);
                linkedWarp.teleport(p);
            }
        }
    }

    @Override
    public YamlConfiguration serialize() {
        YamlConfiguration data = new YamlConfiguration();
        data.set("name", name);
        data.set("loc", location);
        data.set("link", link);
        return data;
    }

    @Override
    public Warps deserialize(YamlConfiguration data) {
        this.name = data.getString("name");
        this.location = data.getLocation("loc");
        this.link = data.getString("link");
        return this;
    }
}
