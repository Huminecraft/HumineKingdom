package com.aymegike.huminekingdom.utils.models.glory;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.Message;
import com.aymegike.huminekingdom.utils.models.GloryEvent;
import com.aymegike.huminekingdom.utils.models.Kingdom;

public class DestroyBeacon extends GloryEvent {

	public DestroyBeacon(String event) {
		super(event);
	}

	@Override
	public void onPlayerBeGlorious(Player player, Event event) {
		
		Kingdom kingdom = HumineKingdom.getPlayerKingdom(player);
		
		if (kingdom == null)
			return;
		
		BlockBreakEvent e = (BlockBreakEvent) event;
		
		if (e.getBlock().getType() == Material.BEACON) {
			Location blockLoc = e.getBlock().getLocation();
			for (Kingdom owner : HumineKingdom.getKingdomManager().getKingdomList()) {
				if (owner.getShieldGenerator(blockLoc) != null) {
					
					if (!owner.equals(kingdom)) {
						owner.setGlory(owner.getGlory() - 150);
						owner.sendMessageToMembers(Message.LOST_POINTS(150));
						kingdom.setGlory(kingdom.getGlory() + 150);
						kingdom.sendMessageToMembers(Message.WIN_POINTS(150));
					} else {
						owner.setGlory(owner.getGlory() - 100);
						owner.sendMessageToMembers(Message.LOST_POINTS(100));
					}
					return;
				}
			}
		}
		
	}

}
