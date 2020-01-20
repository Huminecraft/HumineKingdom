package com.aymegike.huminekingdom.utils.models.glory;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.Message;
import com.aymegike.huminekingdom.utils.models.GloryEvent;
import com.aymegike.huminekingdom.utils.models.Kingdom;
import com.aymegike.huminekingdom.utils.models.ShieldGenerator;

public class PlaceTnt extends GloryEvent {

	public PlaceTnt(String event) {
		super(event);
	}

	@Override
	public void onPlayerBeGlorious(Player player, Event event)
	{
		Kingdom kingdom = HumineKingdom.getKingdomManager().getPlayerKingdom(player);
		
		if (kingdom == null)
			return;
		
		Location location = null;
		if (event instanceof BlockPlaceEvent)
		{
			BlockPlaceEvent e = (BlockPlaceEvent) event;
			
			if (e.getBlock().getType() == Material.TNT)
			{		
				location = e.getBlock().getLocation();
			}
		}	
		else if (event instanceof PlayerInteractEvent)
		{
			PlayerInteractEvent e = (PlayerInteractEvent) event;

			if (e.getItem() != null && e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getItem().getType() == Material.TNT_MINECART)
			{		
				if ( e.getClickedBlock() != null && 
						(e.getClickedBlock().getType() == Material.ACTIVATOR_RAIL
						|| e.getClickedBlock().getType() == Material.DETECTOR_RAIL
						|| e.getClickedBlock().getType() == Material.POWERED_RAIL
						| e.getClickedBlock().getType() == Material.RAIL))
				{
					location = e.getClickedBlock().getLocation();
				}
			}
		}	

		if (location == null)
		{
			return;
		}
		
		boolean isInAPersonalZone = false;
		for (ShieldGenerator sg : kingdom.getShieldGenerators())
		{
			if (sg.getZone().containLocation(location))
			{
				isInAPersonalZone = true;
				break;
			}
		}
			
		if (!isInAPersonalZone)
		{
			if (kingdom.getGlory() > 0)
			{
				kingdom.removeGlory(1);
				kingdom.sendMessageToMembers(Message.LOST_POINTS(1));
			}
			else
			{
				if (event instanceof BlockPlaceEvent)
				{
					BlockPlaceEvent e = (BlockPlaceEvent) event;
					e.setCancelled(true);
				}	
				else if (event instanceof PlayerInteractEvent)
				{
					PlayerInteractEvent e = (PlayerInteractEvent) event;
					e.setCancelled(true);
				}
				player.sendMessage("Ton royaume n'a plus assez de gloire pour te permettre cette petite folie !");	
				player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);

			}
		}		
	}

}
