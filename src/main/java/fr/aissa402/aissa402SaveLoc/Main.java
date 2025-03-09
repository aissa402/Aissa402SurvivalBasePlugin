package fr.aissa402.aissa402SaveLoc;

import fr.aissa402.aissa402SaveLoc.Commands.SaveCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {



    public String webhookUrl;


    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        // Plugin startup logic
        if (!getConfig().contains("webhookUrl")){
            this.getLogger().warning("Vous devez configurer le webhookUrl dans le fichier config.yml !");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        webhookUrl = Objects.requireNonNull(getConfig().get("webhookUrl")).toString();

        this.getLogger().info("Plugin Aissa402SaveLoc démarré !");
        Objects.requireNonNull(this.getCommand("saveloc")).setExecutor(new SaveCommand(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.getLogger().info("Plugin Aissa402SaveLoc arrêté !");
    }
}


//https://discord.com/api/webhooks/1346533061566664829/XG1sQ9Gw5WCHD8EcMdADv1wP3R-saAXZfthTDg0k2gaD2HOMRhiQRwnObUyXUykOizq_