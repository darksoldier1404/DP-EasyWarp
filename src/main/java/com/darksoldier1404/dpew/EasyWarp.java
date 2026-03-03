package com.darksoldier1404.dpew;

import com.darksoldier1404.dpew.commands.DPEWCommand;
import com.darksoldier1404.dpew.functions.DPEWFunction;
import com.darksoldier1404.dpew.obj.Warps;
import com.darksoldier1404.dppc.data.DPlugin;
import com.darksoldier1404.dppc.data.DataContainer;
import com.darksoldier1404.dppc.data.DataType;
import com.darksoldier1404.dppc.utils.PluginUtil;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class EasyWarp extends DPlugin {
    public static EasyWarp plugin;
    public static DataContainer<String, Warps> warpsData;
    public static BukkitTask task;
    public static Set<UUID> delayQueue = new HashSet<>();

    public EasyWarp() {
        super(false);
        plugin = this;
        init();
    }

    @Override
    public void onLoad() {
        PluginUtil.addPlugin(plugin, 29891);
        warpsData = loadDataContainer(new DataContainer<>(this, DataType.CUSTOM, "warps"), Warps.class);
    }

    @Override
    public void onEnable() {
        DPEWFunction.initTask();
        DPEWCommand.init();
    }

    @Override
    public void onDisable() {
        saveAllData();
    }
}
