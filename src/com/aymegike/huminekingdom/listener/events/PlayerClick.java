package com.aymegike.huminekingdom.listener.events;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.Particle.DustOptions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.Message;
import com.aymegike.huminekingdom.utils.models.Kingdom;
import com.aymegike.huminekingdom.utils.models.ShieldGenerator;

public class PlayerClick implements Listener { 
	
	public static ArrayList<OfflinePlayer> getBeacon = new ArrayList<OfflinePlayer>();
	
	public static HashMap<OfflinePlayer, ShieldGenerator> getShieldGenerator = new HashMap<OfflinePlayer, ShieldGenerator>();
	
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent e)
	{
		Player player = e.getPlayer();
		Kingdom playerKingdom = HumineKingdom.getKingdomManager().getPlayerKingdom(player);
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && getBeacon.contains(player))
		{
			e.setCancelled(true);
			if (e.getClickedBlock().getType() == Material.BEACON)
			{
				if (playerKingdom.getShieldGenerator(e.getClickedBlock().getLocation()) != null)
				{
					player.sendMessage(Message.SHIELD_GIVE_NAME);
					player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
					getShieldGenerator.put(player, playerKingdom.getShieldGenerator(e.getClickedBlock().getLocation()));
					PlayerChatEvent.getNameOfShematic.add(player);
				}
				else
				{
					player.sendMessage(Message.SHIELD_NOT_REGISTER);
					player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
				}
			} 
			else
			{
				player.sendMessage(Message.SHIELD_IS_NOT_A_BEACON);
				player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
			}			
			getBeacon.remove(player);
		}
		
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.BEDROCK && playerKingdom != null)
		{			
			if (playerKingdom.getShieldGenerator(e.getClickedBlock().getLocation()) != null && !playerKingdom.getShieldGenerator(e.getClickedBlock().getLocation()).isActive() )
			{				
				if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.NETHER_STAR)
				{
					e.setCancelled(true);
					playerKingdom.regeneShield(playerKingdom.getShieldGenerator(e.getClickedBlock().getLocation()));
					for (int i = 0 ; i < player.getInventory().getSize() ; i++)
					{
						if (player.getInventory().getItem(i) != null && e.getPlayer().getInventory().getItem(i).getType() == Material.NETHER_STAR)
						{
							int nb = player.getInventory().getItem(i).getAmount();
							nb--;
							if (nb == 0)
							{
								e.getPlayer().getInventory().setItem(i, new ItemStack(Material.AIR));
							}
							else
							{
								e.getPlayer().getInventory().setItem(i, new ItemStack(Material.NETHER_STAR, nb));
							}
							return;
						}
					}
				}
				else
				{
					e.getPlayer().sendMessage(Message.SHIELD_VOID_EXPLAIN);
					e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 5, 1);
					DustOptions dustOptions = new DustOptions(Color.BLACK, (float) 10.0);
					e.getClickedBlock().getWorld().spawnParticle(Particle.REDSTONE, e.getClickedBlock().getLocation().add(0.5, 0.5, 0.5), 10, 0, 0.5, 0, 2, dustOptions);
				}				
			}	
		}
		
		
		// EGG	
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || (e.getAction() == Action.LEFT_CLICK_BLOCK && player.getGameMode() != GameMode.CREATIVE))
		{	
			if (e.getClickedBlock().getType() == Material.DRAGON_EGG)
			{
				//if (HumineKingdom.getEggManager().getEgg() != null)
				//{
				if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR)
				{
					if (playerKingdom != null)
					{
						if (HumineKingdom.getEggManager().getEgg() != null)
						{
							HumineKingdom.getEggManager().getEgg().destroyEgg(e.getPlayer());
						}
						e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.DRAGON_EGG));
					}
					else
					{
						DustOptions dustOptions = new DustOptions(Color.PURPLE, (float) 10.0);
						e.getClickedBlock().getWorld().spawnParticle(Particle.REDSTONE, e.getClickedBlock().getLocation().add(0.5, 0.5, 0.5), 10, 0, 0.5, 0, 2, dustOptions);
						e.getPlayer().sendMessage(Message.EGG_NO_KINGDOM);
						e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 5, 1);
					}
				}
				else
				{
					DustOptions dustOptions = new DustOptions(Color.PURPLE, (float) 10.0);
					e.getClickedBlock().getWorld().spawnParticle(Particle.REDSTONE, e.getClickedBlock().getLocation().add(0.5, 0.5, 0.5), 10, 0, 0.5, 0, 2, dustOptions);
					e.getPlayer().sendMessage(Message.EGG_HAND_IS_FULL);
					e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 5, 1);
				}
				e.setCancelled(true);
			}
		}
		
		if (e.getAction() == Action.LEFT_CLICK_BLOCK && player.getGameMode() == GameMode.CREATIVE)
		{	
			if (e.getClickedBlock().getType() == Material.DRAGON_EGG)
			{
				if (playerKingdom != null)
				{
					if (HumineKingdom.getEggManager().getEgg() != null)
					{
						HumineKingdom.getEggManager().getEgg().destroyEgg(e.getPlayer());
					}
				}			
			}
		}
	}
}
