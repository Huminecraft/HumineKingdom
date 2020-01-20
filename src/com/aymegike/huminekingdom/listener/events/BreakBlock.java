package com.aymegike.huminekingdom.listener.events;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffectType;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.BlockList;
import com.aymegike.huminekingdom.utils.Message;
import com.aymegike.huminekingdom.utils.Permissions;
import com.aymegike.huminekingdom.utils.models.Kingdom;
import com.aymegike.huminekingdom.utils.models.ShieldGenerator;

public class BreakBlock implements Listener {
	
	@EventHandler
	public void onPlayerBreakBlock(BlockBreakEvent e)
	{
		Player player = e.getPlayer();
		System.out.println("BB 1");
		if (e.getBlock().getType() == Material.BEACON)
		{
			System.out.println("BB 2");
			for (Kingdom kingdom : HumineKingdom.getKingdomManager().getKingdomList())
			{
				ArrayList<ShieldGenerator> shieldGenerators = kingdom.getShieldGenerators();
				for (int i = 0 ; i < shieldGenerators.size() ; i++)
				{					
					Location shieldLocation = shieldGenerators.get(i).getLocation();
					Location breakBlockLocation = e.getBlock().getLocation();

					System.out.println("ON A SHIELD LOCATiON = " + shieldLocation);
					System.out.println("ET BREAK LOCATION = " + breakBlockLocation);
					/*if (shieldLocation.getWorld() == breakBlockLocation.getWorld() 
							&& shieldLocation.getBlockX() == breakBlockLocation.getBlockX()
							&& shieldLocation.getBlockY() == breakBlockLocation.getBlockY() 
							&& shieldLocation.getBlockZ() == breakBlockLocation.getBlockZ())*/

					if (shieldLocation.equals(breakBlockLocation))
					{	
						System.out.println("BB 2.5");
						if (!kingdom.isMember(e.getPlayer()) && e.getPlayer().hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE) )
						{
							System.out.println("BB 3");
							e.setCancelled(true);
							return;
						}
						
						if (kingdom.isMember(e.getPlayer()) && e.getPlayer().hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE))
						{
							if (kingdom.getPlayerGrade(e.getPlayer()) == null)
							{
								System.out.println("BB 3.5");
								e.setCancelled(true);
								return;							
							}
							if (kingdom.getPlayerGrade(e.getPlayer()) != null && !kingdom.getPlayerGrade(e.getPlayer()).containPermission(Permissions.BREAK.getPermission()))
							{
								System.out.println("BB 3.5 : Grade is " + kingdom.getPlayerGrade(e.getPlayer()).getName());
								System.out.println("And has permissions : " + kingdom.getPlayerGrade(e.getPlayer()).getPermissions());
								e.setCancelled(true);
								return;								
							}
						}
						
						System.out.println("BB 4");		
						boolean isMember = kingdom.isMember(player);
						if ((!isMember && player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) 
								|| (isMember && player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE) 
										&& (kingdom.getPlayerGrade(player) != null && 
										!kingdom.getPlayerGrade(player).containPermission(Permissions.BREAK.getPermission())) ) ) 
						{
							System.out.println("BB 5");
							e.setCancelled(true);
							return;
						}
						kingdom.removeShield(kingdom.getShieldGenerators().get(i));
						System.out.println("BB 6");

						System.out.println("On enlève de la gloire");
						kingdom.removeGlory(300);
						kingdom.sendMessageToMembers(Message.LOST_POINTS(300));
						
						for (OfflinePlayer op : kingdom.getMembers())
						{							
							if (op.isOnline())
							{
								op.getPlayer().sendMessage(Message.SHIELD_DESTROY);
								op.getPlayer().playSound(op.getPlayer().getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 5, 1);
							}
							
						}
					}
				}
			}
		}
		
		if (containEgg(e.getBlock().getLocation()) && player.getGameMode() != GameMode.CREATIVE)
		{
			System.out.println("BB 7");
			e.setCancelled(true);
		}
	}
	
	
	public static boolean containEgg(Location location)
	{
		//TO CHANGE : stock the block we don't want to break and just check if the block is in the list
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
