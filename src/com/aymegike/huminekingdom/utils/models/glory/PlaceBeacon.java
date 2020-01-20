package com.aymegike.huminekingdom.utils.models.glory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.Message;
import com.aymegike.huminekingdom.utils.models.GloryEvent;
import com.aymegike.huminekingdom.utils.models.Kingdom;

public class PlaceBeacon extends GloryEvent {

	public PlaceBeacon(String event) {
		super(event);
	}

	@Override
	public void onPlayerBeGlorious(Player player, Event event) {
		
		Kingdom kingdom = HumineKingdom.getKingdomManager().getPlayerKingdom(player);
		
		if (kingdom == null)
			return;
		
		BlockPlaceEvent e = (BlockPlaceEvent) event;
		
		if (e.getBlock().getType() == Material.BEACON) {
			kingdom.sendMessageToMembers(Message.PLACE_BEACON(player));
			kingdom.addGlory(300);
		}
		
	}

}
