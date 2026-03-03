package com.darksoldier1404.dpew.functions;

import com.darksoldier1404.dpew.obj.Warps;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import static com.darksoldier1404.dpew.EasyWarp.plugin;

public class DPEWFunction {

    public static void initTask() {
        if (plugin.task != null) {
            plugin.task.cancel();
        }
        plugin.task = Bukkit.getScheduler().runTaskTimer(plugin, () -> Bukkit.getOnlinePlayers().forEach(p -> {
            Location loc = p.getLocation();
            plugin.warpsData.values().forEach(warp -> {
                if (warp.getLink() != null && warp.getLocation() != null && warp.getLocation().getWorld().getName().equals(p.getWorld().getName())) {
                    try {
                        if (loc.distance(warp.getLocation()) < 1) {
                            warp.teleportToLink(p);
                        }
                    } catch (Exception ignored) {
                    }
                }
            });
        }), 0L, 5L);
    }

    public static boolean isExistWarp(String warpName) {
        return plugin.warpsData.containsKey(warpName);
    }

    public static void createWarp(Player p, String warpName) {
        if (isExistWarp(warpName)) {
            p.sendMessage(plugin.prefix + "이미 존재하는 워프입니다.");
            return;
        }
        Warps warp = new Warps(warpName, null);
        plugin.warpsData.put(warpName, warp);
        plugin.warpsData.save(warpName);
        p.sendMessage(plugin.prefix + "워프가 생성되었습니다.");
    }

    public static void setWarpLocation(Player p, String warpName) {
        if (!isExistWarp(warpName)) {
            p.sendMessage(plugin.prefix + "존재하지 않는 워프입니다.");
            return;
        }
        Warps warp = plugin.warpsData.get(warpName);
        warp.setLocation(p.getLocation());
        plugin.warpsData.put(warpName, warp);
        plugin.warpsData.save(warpName);
        p.sendMessage(plugin.prefix + "워프 위치가 설정되었습니다.");
    }

    public static void setWarpLink(Player p, String warpName, String linkedWarpName) {
        if (!isExistWarp(warpName)) {
            p.sendMessage(plugin.prefix + "존재하지 않는 워프입니다.");
            return;
        }
        if (!isExistWarp(linkedWarpName)) {
            p.sendMessage(plugin.prefix + "연결할 워프가 존재하지 않습니다.");
            return;
        }
        Warps warp = plugin.warpsData.get(warpName);
        warp.setLink(linkedWarpName);
        plugin.warpsData.put(warpName, warp);
        plugin.warpsData.save(warpName);
        p.sendMessage(plugin.prefix + "워프 연결이 설정되었습니다.");
    }

    public static void deleteWarp(Player p, String warpName) {
        if (!isExistWarp(warpName)) {
            p.sendMessage(plugin.prefix + "존재하지 않는 워프입니다.");
            return;
        }
        plugin.warpsData.delete(warpName);
        plugin.warpsData.remove(warpName);
        p.sendMessage(plugin.prefix + "워프가 삭제되었습니다.");
    }

    public static void teleportToWarp(Player p, String warpName) {
        if (!isExistWarp(warpName)) {
            p.sendMessage(plugin.prefix + "존재하지 않는 워프입니다.");
            return;
        }
        Warps warp = plugin.warpsData.get(warpName);
        if (warp.getLocation() == null) {
            p.sendMessage(plugin.prefix + "워프 위치가 설정되지 않았습니다.");
            return;
        }
        warp.teleport(p);
        p.sendMessage(plugin.prefix + "워프로 이동하였습니다.");
    }

    public static void listWarps(Player p) {
        if (plugin.warpsData.isEmpty()) {
            p.sendMessage(plugin.prefix + "등록된 워프가 없습니다.");
            return;
        }
        StringBuilder warpList = new StringBuilder();
        plugin.warpsData.values().forEach((warp) -> {
            warpList.append("§e").append(warp.getName()).append("§f: ").append(warp.getLocation() != null ? "위치 설정됨" : "위치 미설정").append(", 연결된 워프: ").append(warp.getLink() != null ? "§e" + warp.getLink() : "§c없음").append("\n");
        });
        p.sendMessage(plugin.prefix + "등록된 워프 목록:\n" + warpList);
    }
}
