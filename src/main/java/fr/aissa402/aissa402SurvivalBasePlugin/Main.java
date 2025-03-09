package fr.aissa402.aissa402SurvivalBasePlugin;

import net.kyori.adventure.title.Title;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public final class Main extends JavaPlugin {

    public static String webhookUrl;
    public static FileConfiguration config;
    public static String salt = "dfg54g46d115515fgjuhy5jk884d";

    public ArrayList<String> loggedPlayers = new ArrayList<>();
    public static Boolean saveLoc = true;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();


        config = this.getConfig();

        if (config.contains("webhook-url") ) {
            webhookUrl = config.getString("webhook-url");
            if (webhookUrl == null || webhookUrl.isEmpty()) {
                getLogger().warning("Please set the webhook-url in the config.yml file !");
                this.getServer().getPluginManager().disablePlugin(this);
            }
        } else {
            getLogger().warning("Please set the webhook-url in the config.yml file !");
            saveLoc = false;
        }

        if (!config.contains("passwords")) {
            config.set("passwords", new HashMap<String,String>());
            //                               Username : HashedPassword (SHA-256)
        }



        getServer().getPluginManager().registerEvents(new fr.aissa402.aissa402SurvivalBasePlugin.listeners.LoginListener(this), this);
        Objects.requireNonNull(getCommand("saveloc")).setExecutor(new fr.aissa402.aissa402SurvivalBasePlugin.commands.SaveLocCommand(this));

        getServer().getOnlinePlayers().forEach(player -> {
            player.setGameMode(org.bukkit.GameMode.SPECTATOR);
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1000000, 255, false, false));
            Title.Times times = Title.Times.times(Duration.ofMillis(500), Duration.ofMillis(Integer.MAX_VALUE), Duration.ofMillis(1000));
            Title title = Title.title(
                    net.kyori.adventure.text.Component.text("Welcome"),
                    net.kyori.adventure.text.Component.text("Please login or register"),
                    times
            );
            player.showTitle(title);
        });


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Plugin disabled!");
    }
}
