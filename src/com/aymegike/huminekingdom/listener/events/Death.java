package com.aymegike.huminekingdom.listener.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.aymegike.huminekingdom.HumineKingdom;

public class Death implements Listener {
	
	@EventHandler
	public void onEntityDeath(PlayerDeathEvent e) {
		if (e.getEntity() instanceof Player) {
			
			Player player = (Player) e.getEntity();
			
			boolean contain = false;
			for (ItemStack it : e.getDrops()) {
				if (it.getType() == Material.DRAGON_EGG) {
					it.setType(Material.AIR);
					contain = true;
				}
			}
			
			if (contain) {
				if (HumineKingdom.getPlayerKingdom(player) != null)
					HumineKingdom.getEggManager().setUpEgg(HumineKingdom.getPlayerKingdom(player), new Location(player.getLocation().getWorld(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ()));
				else
					player.getLocation().getBlock().setType(Material.DRAGON_EGG);
			}
			
		}
	}

}
