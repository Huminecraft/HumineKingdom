package com.aymegike.huminekingdom.utils.models.glory;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.Message;
import com.aymegike.huminekingdom.utils.models.GloryEvent;
import com.aymegike.huminekingdom.utils.models.Kingdom;
import com.aymegike.huminekingdom.utils.models.ShieldGenerator;

public class ActiveTnt extends GloryEvent {

	public ActiveTnt(String event) {
		super(event);
	}

	@Override
	public void onPlayerBeGlorious(Player player, Event event) {
		
		Kingdom kingdom = HumineKingdom.getKingdomManager().getPlayerKingdom(player);
		
		if (kingdom == null)
			return;
		
		PlayerInteractEvent e = (PlayerInteractEvent) event;
		
		if (player.getInventory().getItemInMainHand().getType() == Material.FLINT_AND_STEEL && e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null && (e.getClickedBlock().getType() == Material.TNT || e.getClickedBlock().getType() == Material.TNT_MINECART))
		{			
			Location blockLoc = e.getClickedBlock().getLocation();
			boolean isInAPersonalZone =  false;
			for (ShieldGenerator sg : kingdom.getShieldGenerators())
			{
				if (sg.getZone().containLocation(blockLoc))
				{
					isInAPersonalZone = true;
					break;
				}
			}
			
			if (!isInAPersonalZone)
			{
				if (kingdom.getGlory() > 0)
				{
					kingdom.removeGlory(9);
					kingdom.sendMessageToMembers(Message.LOST_POINTS(9));
				}
			}
			
		}
		
	}

}
