package com.aymegike.huminekingdom.listener.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class TeleportEvent implements Listener {
	
	 @EventHandler
	 public void dragonEggTpEvent(BlockFromToEvent event){
		 if(event.getBlock().getType().equals(Material.DRAGON_EGG)){
			 event.setCancelled(true);
	     }
	 }

}
