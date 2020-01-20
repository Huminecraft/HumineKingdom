package com.aymegike.huminekingdom.listener.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.models.Kingdom;
import com.aypi.utils.Timer;
import com.aypi.utils.inter.TimerFinishListener;

public class ItemDrop implements Listener {
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e)
	{		
		Player p = e.getPlayer();
		
		Kingdom playerKingdom = HumineKingdom.getKingdomManager().getPlayerKingdom(p);
		
		if(e.getItemDrop().getItemStack().getType() == Material.DRAGON_EGG)
		{		
			if(playerKingdom != null)
			{		
				if(HumineKingdom.getEggManager().getEgg() != null && HumineKingdom.getEggManager().getEgg().getKingdom().getName().equalsIgnoreCase(playerKingdom.getName()))
				{
					for(Player pls : Bukkit.getOnlinePlayers())
					{
						pls.playSound(pls.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 5, 5);
					}
				}
				else
				{
					for(OfflinePlayer op : playerKingdom.getMembers())
					{
						if(op.isOnline())
						{
							op.getPlayer().sendMessage(ChatColor.WHITE+p.getName()+ChatColor.DARK_PURPLE+" vient de replacer l'oeuf");
							op.getPlayer().playSound(op.getPlayer().getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 5, 1);
						}
					}
				}
				
				timer(p, e, true);		
			}
			else
			{		
				timer(p, e, false);
			}
		}
	}
	
	private void timer(Player p, PlayerDropItemEvent e, boolean kingdom)
	{		
		e.getItemDrop().setPickupDelay(100);
		e.getItemDrop().setGlowing(true);
		e.getItemDrop().setInvulnerable(true);
		e.getItemDrop().setPortalCooldown(100);
		new Timer(Bukkit.getPluginManager().getPlugin("HumineKingdom"), 1, new TimerFinishListener() {

			@Override
			public void execute() {
				if (kingdom)
					HumineKingdom.getEggManager().setUpEgg(HumineKingdom.getKingdomManager().getPlayerKingdom(p), new Location(e.getItemDrop().getLocation().getWorld(), e.getItemDrop().getLocation().getBlockX(), e.getItemDrop().getLocation().getBlockY(), e.getItemDrop().getLocation().getBlockZ()));
				else
					e.getItemDrop().getLocation().getBlock().setType(Material.DRAGON_EGG);
				e.getItemDrop().setInvulnerable(false);
            	e.getItemDrop().remove();				
			}
			
		}).start();
	}

}
