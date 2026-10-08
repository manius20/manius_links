package com.s16.socials;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
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
        
        ConfigurationSection cmdsSection = getConfig().getConfigurationSection("commands");
        if (cmdsSection != null) {
            for (String key : cmdsSection.getKeys(false)) {
                if (getCommand(key) != null) {
                    getCommand(key).setExecutor(this);
                }
            }
        }
        
        getLogger().info("S16Socials zostal pomyslnie wlaczony!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmdName = command.getName().toLowerCase();
        
        String prefixPath = "commands." + cmdName + ".prefix";
        String urlPath = "commands." + cmdName + ".url";
        String hoverPath = "commands." + cmdName + ".hover";

        if (getConfig().contains(urlPath)) {
            String prefixText = getConfig().getString(prefixPath, "&7Link: ");
            String url = getConfig().getString(urlPath, "https://discord.gg/aAtDAXufUH");
            String hoverText = getConfig().getString(hoverPath, "&eKliknij, aby otworzyć w przeglądarce!");

            // Konwersja kolorów z tradycyjnego kodu & na format Adventure
            Component prefix = LegacyComponentSerializer.legacy('&').deserialize(prefixText);
            
            // Stworzenie klikalnego linku
            Component linkComponent = Component.text(url)
                    .color(NamedTextColor.AQUA)
                    .decorate(TextDecoration.UNDERLINED)
                    .clickEvent(ClickEvent.openUrl(url))
                    .hoverEvent(HoverEvent.showText(LegacyComponentSerializer.legacy('&').deserialize(hoverText)));

            // Połączenie całości w jedną wiadomość
            Component finalMessage = prefix.append(linkComponent);
            
            sender.sendMessage(finalMessage);
            return true;
        }

        sender.sendMessage(Component.text("Ta komenda nie jest skonfigurowana.", NamedTextColor.RED));
        return true;
    }
}
