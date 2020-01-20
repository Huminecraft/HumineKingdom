package com.aymegike.huminekingdom.listener.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.BlockList;
import com.aymegike.huminekingdom.utils.KingdomZone;
import com.aymegike.huminekingdom.utils.Message;
import com.aymegike.huminekingdom.utils.models.Kingdom;
import com.aymegike.huminekingdom.utils.models.ShieldGenerator;
import com.aypi.Aypi;
import com.aypi.utils.Square;
import com.aypi.utils.Zone;

public class PlaceBlock implements Listener {
	
	@EventHandler
	public void onPlayerPlaceBlock(BlockPlaceEvent e) {
		Player player = e.getPlayer();
		
		if (e.getBlock().getType() == Material.DRAGON_EGG)
		{
			Kingdom playerKingdom = HumineKingdom.getKingdomManager().getPlayerKingdom(player);
			if (playerKingdom != null)
			{				
				e.getBlock().setType(Material.AIR);
				e.setCancelled(true);
				for (int i = 0 ; i <= player.getInventory().getSize() ; i++)
				{
					if (player.getInventory().getItem(i) != null && player.getInventory().getItem(i).getType() == Material.DRAGON_EGG)
					{
						player.getInventory().setItem(i, null);
					}
				}
				HumineKingdom.getEggManager().setUpEgg(playerKingdom, e.getBlock().getLocation());			
			}			
		}
		
		for (Kingdom kingdom : HumineKingdom.getKingdomManager().getKingdomList())
		{
			for (ShieldGenerator sg : kingdom.getShieldGenerators())
			{
				if (sg.getLocation().getWorld().equals(e.getBlock().getLocation().getWorld())
				&& sg.getLocation().getBlockX() == e.getBlock().getLocation().getBlockX()
				&& sg.getLocation().getBlockZ() == e.getBlock().getLocation().getBlockZ()
				&& sg.getLocation().getBlockY() <= e.getBlock().getLocation().getBlockY())
				{
					if (BlockList.isInTopBeaconList(e.getBlock().getType()))
					{
						e.setCancelled(true);
						return;
					}
					
				}
			}
		}

		if (BreakBlock.containEgg(e.getBlock().getLocation()))
		{
			e.setCancelled(true);
			return;
		}
		
		if (e.getBlock().getType() == Material.BEACON)
		{
			Kingdom playerKingdom = HumineKingdom.getKingdomManager().getPlayerKingdom(player);			
			if (playerKingdom != null)
			{				
				Square square = new Square(new Location(e.getBlock().getLocation().getWorld(), e.getBlock().getLocation().getBlockX()-60, 0, e.getBlock().getLocation().getBlockZ()-60), new Location(e.getBlock().getLocation().getWorld(), e.getBlock().getLocation().getBlockX()+60, 300, e.getBlock().getLocation().getBlockZ()+60));
				
				/*for (ShieldGenerator sg : playerKingdom.getShieldGenerators()) {
					if (sg.getZone().containLocation(e.getBlock().getLocation())) {
						return;
					}
				}*/
				
				System.out.println("ON PLAAACe UN BEACOON");
				playerKingdom.addShield(new ShieldGenerator(playerKingdom, e.getBlock().getLocation(), new KingdomZone(square, e.getBlock().getLocation().getWorld(), playerKingdom), true));
				player.sendMessage(Message.SHIELD_PLACE);
				
			} else {

				//MESSAGE PREVENTION
				
			}			
		}
		

		
		/*for (Zone zone : Aypi.getZoneManager().getZones())
		{	
			if (zone.containLocation(new Location(player.getLocation().getWorld(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ())))
			{
				return;
			}
		}*/
		
	}

}
