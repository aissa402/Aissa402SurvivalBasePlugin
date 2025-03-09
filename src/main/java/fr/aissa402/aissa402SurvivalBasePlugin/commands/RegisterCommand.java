package fr.aissa402.aissa402SurvivalBasePlugin.commands;

import com.google.common.hash.Hashing;
import fr.aissa402.aissa402SurvivalBasePlugin.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

public class RegisterCommand implements CommandExecutor {
    public Main plugin;
    RegisterCommand(Main plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!(sender instanceof Player player)){
            sender.sendMessage("Only players can execute this command!");
            return false;
        }
        if (!(args.length == 2)){
            player.sendMessage("Usage: /register <password> <confirm-password>");
            return false;
        }

        if (Main.config.contains("passwords." + player.getName())){
            player.sendMessage("You are already registered! Login using /login <password>");
            player.sendMessage("To change your password, use /changepassword <old-password> <new-password> <confirm-new-password>");
            return false;
        }

        if (!args[0].equals(args[1])){
            player.sendMessage("Passwords do not match!");
            return false;
        }

        if (args[0].length() <5){
            player.sendMessage("Password must be at least 5 characters long!");
            return false;
        }

        String passwordHash = Hashing.sha256()
                .hashString(args[0] + Main.salt, StandardCharsets.UTF_8)
                .toString();
        Main.config.set("passwords." + player.getName(), passwordHash);
        plugin.saveConfig();
        player.sendMessage("You are now registered! Please login using /login <password>");
        return false;
    }
}
