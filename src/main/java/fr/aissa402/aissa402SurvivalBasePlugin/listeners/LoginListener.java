package fr.aissa402.aissa402SurvivalBasePlugin.listeners;


import fr.aissa402.aissa402SurvivalBasePlugin.Main;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.time.Duration;

public class LoginListener implements Listener {
    public Main plugin;
    public LoginListener(Main plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        plugin.loggedPlayers.remove(player.getName());
        player.setGameMode(org.bukkit.GameMode.SPECTATOR);
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1000000, 255, false, false));
        Title.Times times = Title.Times.times(Duration.ofMillis(500), Duration.ofMillis(Integer.MAX_VALUE), Duration.ofMillis(1000));
        Title title = Title.title(
                net.kyori.adventure.text.Component.text("Welcome"),
                net.kyori.adventure.text.Component.text("Please login or register"),
                times
        );
        player.showTitle(title);
    }
    @EventHandler
    public void onPlayerQuit(PlayerJoinEvent event){
        Player player = event.getPlayer();

        plugin.loggedPlayers.remove(player.getName());

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (!plugin.loggedPlayers.contains(player.getName())){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerMove(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (!plugin.loggedPlayers.contains(player.getName())){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onInventoryClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (!plugin.loggedPlayers.contains(player.getName())){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerDropItem(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (!plugin.loggedPlayers.contains(player.getName())){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerPickupItem(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (!plugin.loggedPlayers.contains(player.getName())){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerChat(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (!plugin.loggedPlayers.contains(player.getName())){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage().toLowerCase();

        if (!plugin.loggedPlayers.contains(player.getName())) {
            if (!message.startsWith("/login") && !message.startsWith("/register")) {
                player.sendMessage("You must be logged in to use commands other than /login and /register.");
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerBreakBlock(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (!plugin.loggedPlayers.contains(player.getName())){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPlaceBlock(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (!plugin.loggedPlayers.contains(player.getName())){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDamage(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (!plugin.loggedPlayers.contains(player.getName())){
            event.setCancelled(true);
        }
    }



}
