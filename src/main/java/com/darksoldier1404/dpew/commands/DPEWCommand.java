package com.darksoldier1404.dpew.commands;

import com.darksoldier1404.dpew.functions.DPEWFunction;
import com.darksoldier1404.dppc.builder.command.ArgumentIndex;
import com.darksoldier1404.dppc.builder.command.ArgumentType;
import com.darksoldier1404.dppc.builder.command.CommandBuilder;

import static com.darksoldier1404.dpew.EasyWarp.plugin;

public class DPEWCommand {
    public static void init() {
        CommandBuilder builder = new CommandBuilder(plugin);

        builder.beginSubCommand("create", "/dpew create <warpName>")
                .withPermission("dpew.admin")
                .withArgument(ArgumentIndex.ARG_0, ArgumentType.STRING)
                .executesPlayer((p, args) -> {
                    String warpName = args.getString(ArgumentIndex.ARG_0);
                    DPEWFunction.createWarp(p, warpName);
                    return true;
                });

        builder.beginSubCommand("setlocation", "/dpew setlocation <warpName>")
                .withPermission("dpew.admin")
                .withArgument(ArgumentIndex.ARG_0, ArgumentType.STRING, plugin.warpsData.keySet())
                .executesPlayer((p, args) -> {
                    String warpName = args.getString(ArgumentIndex.ARG_0);
                    DPEWFunction.setWarpLocation(p, warpName);
                    return true;
                });

        builder.beginSubCommand("setlink", "/dpew setlink <warpName> <linkedWarpName>")
                .withPermission("dpew.admin")
                .withArgument(ArgumentIndex.ARG_0, ArgumentType.STRING, plugin.warpsData.keySet())
                .withArgument(ArgumentIndex.ARG_1, ArgumentType.STRING, plugin.warpsData.keySet())
                .executesPlayer((p, args) -> {
                    String warpName = args.getString(ArgumentIndex.ARG_0);
                    String linkedWarpName = args.getString(ArgumentIndex.ARG_1);
                    DPEWFunction.setWarpLink(p, warpName, linkedWarpName);
                    return true;
                });

        builder.beginSubCommand("teleport", "/dpew teleport <warpName>")
                .withPermission("dpew.teleport")
                .withArgument(ArgumentIndex.ARG_0, ArgumentType.STRING, plugin.warpsData.keySet())
                .executesPlayer((p, args) -> {
                    String warpName = args.getString(ArgumentIndex.ARG_0);
                    DPEWFunction.teleportToWarp(p, warpName);
                    return true;
                });

        builder.beginSubCommand("delete", "/dpew delete <warpName>")
                .withPermission("dpew.admin")
                .withArgument(ArgumentIndex.ARG_0, ArgumentType.STRING, plugin.warpsData.keySet())
                .executesPlayer((p, args) -> {
                    String warpName = args.getString(ArgumentIndex.ARG_0);
                    DPEWFunction.deleteWarp(p, warpName);
                    return true;
                });

        builder.beginSubCommand("list", "/dpew list")
                .withPermission("dpew.teleport")
                .executesPlayer((p, args) -> {
                    DPEWFunction.listWarps(p);
                    return true;
                });

        builder.beginSubCommand("reload", "/dpew reload")
                .withPermission("dpew.admin")
                .executesPlayer((p, args) -> {
                    plugin.reloadConfig();
                    DPEWFunction.initTask();
                    p.sendMessage(plugin.getPrefix() + "EasyWarp configuration reloaded.");
                    return true;
                });

        builder.build("dpew");
    }
}
