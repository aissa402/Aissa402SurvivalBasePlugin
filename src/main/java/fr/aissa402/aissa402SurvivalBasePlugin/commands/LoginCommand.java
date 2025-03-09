package fr.aissa402.aissa402SurvivalBasePlugin.commands;

import com.google.common.hash.Hashing;
import fr.aissa402.aissa402SurvivalBasePlugin.Main;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginCommand implements CommandExecutor {
    public Main plugin;
    public LoginCommand(Main plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!(sender instanceof Player player)){
            sender.sendMessage("Only players can execute this command!");
            return false;
        }
        if (!(args.length == 1)){
            player.sendMessage("Usage: /login <password>");
            return false;
        }

        if (!(Main.config.contains("passwords." + player.getName()))){;
            player.sendMessage("You are not registered yet! Please register using /register <password> <confirm-password>");
            return false;
        }
        String inputPasswordHash = Hashing.sha256()
                .hashString(args[0] + Main.salt, StandardCharsets.UTF_8)
                .toString();
        String storedPasswordHash = Main.config.getString("passwords." + player.getName());
        if (inputPasswordHash.equals(storedPasswordHash)){
            player.sendMessage("You are now logged in!");
            plugin.loggedPlayers.add(player.getName());
            player.setGameMode(GameMode.SURVIVAL);
            player.removePotionEffect(PotionEffectType.BLINDNESS);

        } else {
            player.sendMessage("Invalid password!");

        }


        return false;
    }
}
