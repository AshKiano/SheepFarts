package com.ashkiano.sheepfarts;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SheepRegrowWoolEvent;
import org.bukkit.inventory.ItemStack;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//TODO konfigurovatelné co má dropovat
//TODO vypínací a zapínací příkaz (také možnost příkazu s časovačem na jak dlouho se má zapnout, něco jako hapy hour)
public class SheepFarts extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        Metrics metrics = new Metrics(this, 19349);

        this.getLogger().info("Thank you for using the SheepFarts plugin! If you enjoy using this plugin, please consider making a donation to support the development. You can donate at: https://donate.ashkiano.com");

        checkForUpdates();
    }

    @EventHandler
    public void onSheepRegrowWool(SheepRegrowWoolEvent event) {
        // Create a diamond and place it at the sheep's location
        event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), new ItemStack(Material.DIAMOND));
    }

    private void checkForUpdates() {
        try {
            String pluginName = this.getDescription().getName();
            URL url = new URL("https://plugins.ashkiano.com/version_check.php?plugin=" + pluginName);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String jsonResponse = response.toString();
                JSONObject jsonObject = new JSONObject(jsonResponse);
                if (jsonObject.has("error")) {
                    this.getLogger().warning("Error when checking for updates: " + jsonObject.getString("error"));
                } else {
                    String latestVersion = jsonObject.getString("latest_version");

                    String currentVersion = this.getDescription().getVersion();
                    if (currentVersion.equals(latestVersion)) {
                        this.getLogger().info("This plugin is up to date!");
                    } else {
                        this.getLogger().warning("There is a newer version (" + latestVersion + ") available! Please update!");
                    }
                }
            } else {
                this.getLogger().warning("Failed to check for updates. Response code: " + responseCode);
            }
        } catch (Exception e) {
            this.getLogger().warning("Failed to check for updates. Error: " + e.getMessage());
        }
    }
}
