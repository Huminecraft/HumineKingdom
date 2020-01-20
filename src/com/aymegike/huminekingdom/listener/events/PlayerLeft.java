package com.aymegike.huminekingdom.listener.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.models.Kingdom;

public class PlayerLeft implements Listener {
	
	@EventHandler
	public static void onPlayerLeft(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		boolean contain = false;
		for (int i = 0 ; i < player.getInventory().getSize() ; i++)
		{
			if (player.getInventory().getItem(i) != null && player.getInventory().getItem(i).getType() == Material.DRAGON_EGG)
			{
				player.getInventory().setItem(i, new ItemStack(Material.AIR));
				contain = true;
			}
		}
		
		if (contain)
		{
			Kingdom playerKingdom = HumineKingdom.getKingdomManager().getPlayerKingdom(player);
			if ( playerKingdom != null)
			{
				HumineKingdom.getEggManager().setUpEgg(playerKingdom, player.getLocation());
			}
			else
			{
				player.getLocation().getBlock().setType(Material.DRAGON_EGG);
			}
		}
	}

}
