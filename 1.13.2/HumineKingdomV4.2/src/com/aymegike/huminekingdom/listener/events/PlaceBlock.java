package com.aymegike.huminekingdom.listener.events;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.BlockList;
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
		
		for (Kingdom kigdom : HumineKingdom.getKingdomManager().getKingdomList()) {
			for (ShieldGenerator sg : kigdom.getShieldGenerators()) {

				if (sg.getLocation().getBlockX() == e.getBlock().getLocation().getBlockX()
				&& sg.getLocation().getBlockZ() == e.getBlock().getLocation().getBlockZ()
				&& sg.getLocation().getBlockY() <= e.getBlock().getLocation().getBlockY()) {
					if (BlockList.isInTopBeaconList(e.getBlock().getType())) {
						e.setCancelled(true);
						DustOptions dustOptions = new DustOptions(Color.BLACK, (float) 10.0);
						e.getBlock().getLocation().getWorld().spawnParticle(Particle.REDSTONE, e.getBlock().getLocation().add(0.5, 0.5, 0.5), 10, 0, 0.5, 0, 2, dustOptions);
						return;
					}
					
				}
			}
		}
		
		for (Zone zone : Aypi.getZoneManager().getZones()) {	
			if (zone.containLocation(new Location(player.getLocation().getWorld(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ()))) {
				return;
			}
		}
		
		if (e.getBlock().getType() == Material.BEACON) {
			
			if (HumineKingdom.getPlayerKingdom(player) != null) {
				
				Kingdom k = HumineKingdom.getPlayerKingdom(player);
				Square square = new Square(new Location(e.getBlock().getLocation().getWorld(), e.getBlock().getLocation().getBlockX()-60, 0, e.getBlock().getLocation().getBlockZ()-60), new Location(e.getBlock().getLocation().getWorld(), e.getBlock().getLocation().getBlockX()+60, 300, e.getBlock().getLocation().getBlockZ()+60));
				
				for (ShieldGenerator sg : k.getShieldGenerators()) {
					if (sg.getZone().containLocation(e.getBlock().getLocation())) {
						return;
					}
				}
				
				k.addShield(new ShieldGenerator(k, e.getBlock().getLocation(), new Zone(square , HumineKingdom.getZoneListener(k)), true));
				player.sendMessage(Message.SHIELD_PLACE);
				
			} else {
				
				//MESSAGE PREVENTION
				
			}
			
		}
		
		if (e.getBlock().getType() == Material.DRAGON_EGG) {
			
			if (HumineKingdom.getPlayerKingdom(player) != null) {
				
				e.setCancelled(true);
				for (int i = 0 ; i <= player.getInventory().getSize() ; i++) {
					if (player.getInventory().getItem(i) != null && player.getInventory().getItem(i).getType() == Material.DRAGON_EGG) {
						player.getInventory().setItem(i, null);
					}
				}
				HumineKingdom.getEggManager().setUpEgg(HumineKingdom.getPlayerKingdom(player), e.getBlock().getLocation());
			
			} else {
				
				// TODO
				
			}
			
		}
	}

}
