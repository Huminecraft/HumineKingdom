package com.aymegike.huminekingdom.utils.models.glory;

import java.util.Date;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.Message;
import com.aymegike.huminekingdom.utils.models.GloryEvent;
import com.aymegike.huminekingdom.utils.models.Kingdom;

public class KingUseEgg extends GloryEvent {

	public KingUseEgg(String event) {
		super(event);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onPlayerBeGlorious(Player player, Event event) {
		
		Kingdom kingdom = HumineKingdom.getPlayerKingdom(player);
		
		PlayerInteractEvent e = (PlayerInteractEvent) event;
		
		if (kingdom == null || !kingdom.isMaster() || !kingdom.getKing().getName().equalsIgnoreCase(player.getName()) || e.getClickedBlock() == null || e.getClickedBlock().getType() != Material.DRAGON_EGG)
			return;
		
		e.setCancelled(true);
		
		if (kingdom.getLastUpdate().getMonth() != new Date().getMonth() || kingdom.getLastUpdate().getDate() != new Date().getDate()) {
			kingdom.updateDate();
			kingdom.setGlory(kingdom.getGlory() + 30);
			player.sendMessage(Message.EGG_UPDATE_DATE);
		}
		
	}

}
