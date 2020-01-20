package com.aymegike.huminekingdom.listener.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryListener implements Listener {

	@EventHandler
	public void onItemMove(InventoryMoveItemEvent e)
	{
		if (e.getItem() != null && e.getItem().getType() == Material.DRAGON_EGG)
		{
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onHopperSuck(InventoryPickupItemEvent e)
	{
		
		if (e.getInventory().getType() == InventoryType.HOPPER)
		{
			if (e.getItem() != null && e.getItem().getItemStack() != null && e.getItem().getItemStack().getType() == Material.DRAGON_EGG)
			{
				e.setCancelled(true);
			}			
		}		
	}
	
	@EventHandler
	public void onItemDrag(InventoryClickEvent e)
	{
		if (e.getInventory().getType() != InventoryType.CRAFTING)
		{				
			if (e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.DRAGON_EGG)
			{
				e.setCancelled(true);
			}
		}
	}
}
