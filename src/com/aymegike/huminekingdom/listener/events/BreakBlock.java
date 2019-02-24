package com.aymegike.huminekingdom.listener.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffectType;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.BlockList;
import com.aymegike.huminekingdom.utils.Message;
import com.aymegike.huminekingdom.utils.Permissions;
import com.aymegike.huminekingdom.utils.models.Kingdom;

public class BreakBlock implements Listener {
	
	@EventHandler
	public void onPlayerBreakBlock(BlockBreakEvent e) {		
		
		if (e.getBlock().getType() == Material.BEACON) {
			for (Kingdom kingdom : HumineKingdom.getKingdomManager().getKingdomList()) {
				for (int i = 0 ; i < kingdom.getShieldGenerators().size() ; i++) {					
					if (kingdom.getShieldGenerators().get(i).getLocation().getWorld() == e.getBlock().getLocation().getWorld() && kingdom.getShieldGenerators().get(i).getLocation().getBlockX() == e.getBlock().getLocation().getBlockX()
					&& kingdom.getShieldGenerators().get(i).getLocation().getBlockY() == e.getBlock().getLocation().getBlockY() && kingdom.getShieldGenerators().get(i).getLocation().getBlockZ() == e.getBlock().getLocation().getBlockZ()) {
						
						if ((!kingdom.isMember(e.getPlayer()) && e.getPlayer().hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) 
						|| (kingdom.isMember(e.getPlayer()) && e.getPlayer().hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE) && (kingdom.getPlayerGrade(e.getPlayer()) != null && !kingdom.getPlayerGrade(e.getPlayer()).containPermission(Permissions.BREAK.getPermission())) ) ) {
							e.setCancelled(true);
							return;
						}
						kingdom.removeShield(kingdom.getShieldGenerators().get(i));
						i--;
						for (OfflinePlayer op : kingdom.getMembers()) {
							
							if (op.isOnline()) {
								op.getPlayer().sendMessage(Message.SHIELD_DESTROY);
								op.getPlayer().playSound(op.getPlayer().getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 5, 1);
							}
							
						}
					}
				}
			}
		}
		
		e.setCancelled(containEgg(e.getBlock().getLocation()));
		
		
	}
	
	
	public static boolean containEgg(Location location) {
		if (BlockList.isInStructEgg(location.getBlock().getType())) {
			for (int x = location.getBlockX() -1 ; x <= location.getBlockX()+1 ; x++) {
				for (int y = location.getBlockY() - 1 ; y <= location.getBlockY()+1 ; y++) {
					for (int z = location.getBlockZ() - 1 ; z <= location.getBlockZ()+1 ; z++) {
						if (new Location(location.getWorld(), x, y, z).getBlock().getType() == Material.DRAGON_EGG) {
							return true;
						}
					}
				}
				
			}
		}
		
		return false;
	}

}
