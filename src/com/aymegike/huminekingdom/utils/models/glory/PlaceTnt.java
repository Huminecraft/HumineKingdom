package com.aymegike.huminekingdom.utils.models.glory;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;

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
	public void onPlayerBeGlorious(Player player, Event event) {
		Kingdom kingdom = HumineKingdom.getPlayerKingdom(player);
		
		if (kingdom == null)
			return;
		
		BlockPlaceEvent e = (BlockPlaceEvent) event;
		
		if (e.getBlock().getType() == Material.TNT) {
			
			Location blockLoc = e.getBlock().getLocation();
			boolean isInAPersonalZone =  false;
			for (ShieldGenerator sg : kingdom.getShieldGenerators()) {
				if (sg.getZone().containLocation(blockLoc)) {
					isInAPersonalZone = true;
					break;
				}
			}
			
			if (!isInAPersonalZone) {
				kingdom.setGlory(kingdom.getGlory() - 1);
				kingdom.sendMessageToMembers(Message.LOST_POINTS(1));
			}
			
		}
		
	}

}
