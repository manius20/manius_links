package com.s16.socials;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public final class SocialPlugin extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadCommands();
        getLogger().info("S16Socials zostal pomyslnie wlaczony!");
    }

    @Override
    public void onDisable() {
        getLogger().info("S16Socials zostal wylaczony.");
    }

    public void loadCommands() {
        reloadConfig();
        ConfigurationSection cmdsSection = getConfig().getConfigurationSection("commands");
        if (cmdsSection == null) return;

        for (String key : cmdsSection.getKeys(false)) {
            PluginCommand command = getCommand(key);
            if (command != null) {
                command.setExecutor(this);
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmdName = command.getName().toLowerCase();
        String messagePath = "commands." + cmdName + ".message";

        if (getConfig().contains(messagePath)) {
            String rawMessage = getConfig().getString(messagePath, "&cBrak wiadomości.");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', rawMessage));
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Ta komenda nie jest skonfigurowana.");
        return true;
    }
}
