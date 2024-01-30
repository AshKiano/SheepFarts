package com.ashkiano.sheepfarts;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SheepRegrowWoolEvent;
import org.bukkit.inventory.ItemStack;

//TODO konfigurovatelné co má dropovat
//TODO vypínací a zapínací příkaz (také možnost příkazu s časovačem na jak dlouho se má zapnout, něco jako hapy hour)
public class SheepFarts extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        Metrics metrics = new Metrics(this, 19349);

        this.getLogger().info("Thank you for using the SheepFarts plugin! If you enjoy using this plugin, please consider making a donation to support the development. You can donate at: https://donate.ashkiano.com");
    }

    @EventHandler
    public void onSheepRegrowWool(SheepRegrowWoolEvent event) {
        // Create a diamond and place it at the sheep's location
        event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), new ItemStack(Material.DIAMOND));
    }
}
