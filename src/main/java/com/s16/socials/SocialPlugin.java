package com.s16.socials;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public final class SocialPlugin extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        
        ConfigurationSection cmds = getConfig().getConfigurationSection("commands");
        if (cmds != null) {
            for (String key : cmds.getKeys(false)) {
                if (getCommand(key) != null) {
                    getCommand(key).setExecutor(this);
                }
            }
        }
        getLogger().info("S16Socials włączony pomyślnie!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String name = command.getName().toLowerCase();
        String prefix = getConfig().getString("commands." + name + ".prefix", "&7Link: ");
        String url = getConfig().getString("commands." + name + ".url", "https://discord.gg/aAtDAXufUH");
        String hover = getConfig().getString("commands." + name + ".hover", "&eKliknij, aby otworzyć!");

        Component msg = LegacyComponentSerializer.legacy('&').deserialize(prefix)
                .append(Component.text(url)
                        .color(NamedTextColor.AQUA)
                        .clickEvent(ClickEvent.openUrl(url))
                        .hoverEvent(HoverEvent.showText(LegacyComponentSerializer.legacy('&').deserialize(hover))));

        sender.sendMessage(msg);
        return true;
    }
}
