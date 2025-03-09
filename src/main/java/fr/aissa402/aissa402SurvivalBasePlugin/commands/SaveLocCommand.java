package fr.aissa402.aissa402SurvivalBasePlugin.commands;

import fr.aissa402.aissa402SurvivalBasePlugin.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;

public class SaveLocCommand implements CommandExecutor {
    private final Main main;

    public SaveLocCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!Main.saveLoc){
            sender.sendMessage("This feature is disabled, Please contact an admin / dev");
            return false;
        }
        if (!(sender instanceof Player player)){
            sender.sendMessage("Vous devez être un joueur pour exécuter cette commande !");
            return false;
        }

        if (!(command.getName().equalsIgnoreCase("saveloc"))){
            return false;
        }

        if (!(args.length >= 1)){
            player.sendMessage("Usage: /saveloc <title> [description]");
            return false;
        }

        String title = args[0];
        String description = "";
        StringBuilder descriptionB = new StringBuilder();
        String author = player.getName();
        String world = player.getWorld().getName();

        String x = String.valueOf(Math.round(player.getLocation().getX()));
        String y = String.valueOf(Math.round(player.getLocation().getY()));
        String z = String.valueOf(Math.round(player.getLocation().getZ()));

        for (int i = 1; i < args.length; i++){
            descriptionB.append(args[i]).append(" ");
        }


        description = descriptionB.toString().trim();



        HashMap<String,Object> param = new HashMap<>();
        HashMap<String,Object> field1 = new HashMap<>();
        HashMap<String,Object> field2 = new HashMap<>();
        HashMap<String,Object> field3 = new HashMap<>();
        HashMap<String,Object> field4 = new HashMap<>();


        HashMap<String,Object> embed = new HashMap<>();

        field1.put("name", title);
        field1.put("value", description);

        field2.put("name", "Joueur");
        field2.put("value", author);

        field3.put("name", "Position");
        field3.put("value", "X: " + x + " Y: " + y + " Z: " + z);

        field4.put("name", "Monde");
        field4.put("value", world);

        embed.put("fields", List.of(field1,field2,field3,field4));


        List<HashMap<String,Object>> embeds = List.of(embed);

        param.put("username", "SaveLoc Plugin");
        param.put("embeds",embeds);

        JSONObject json = new JSONObject(param);
        String jsonS = json.toJSONString();

        HttpClient client;
        HttpRequest request;

        try {
            client = HttpClient.newHttpClient();
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
            requestBuilder.uri(URI.create(main.webhookUrl));
            requestBuilder.header("Content-Type", "application/json");
            requestBuilder.POST(HttpRequest.BodyPublishers.ofString(jsonS));
            request = requestBuilder.build();
        } catch (Exception e) {
            e.printStackTrace();
            main.getLogger().warning("Error while creating request, Please verify your webhookUrl");
            player.sendMessage("Error while sending location to Discord, Please contact an admin / dev");
            return false;
        }


        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
            player.sendMessage("Error while sending location to Discord, Please contact an admin / dev");
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            player.sendMessage("Error while sending location to Discord, Please contact an admin / dev");
            return false;
        }


        player.sendMessage("Location X:" + x + " Y : " + y + " Z: " + z + " World: " + world + " saved as " + title);
        return false;

    }
}
