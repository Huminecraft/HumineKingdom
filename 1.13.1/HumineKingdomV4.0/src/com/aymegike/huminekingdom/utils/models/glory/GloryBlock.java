package com.aymegike.huminekingdom.utils.models.glory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.aymegike.huminekingdom.utils.models.GloryEvent;

public class GloryBlock extends GloryEvent{

	public GloryBlock(String event) {
		super(event);
		
	}

	@Override
	public void onPlayerBeGlorious(Player player, Event e) {
		//Block block = ((BlockPlaceEvent) e).getBlock();
		
		Bukkit.broadcastMessage(player.getName()+": ");
		
	}

}
