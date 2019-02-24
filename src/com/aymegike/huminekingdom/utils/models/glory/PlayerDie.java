package com.aymegike.huminekingdom.utils.models.glory;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.Message;
import com.aymegike.huminekingdom.utils.models.GloryEvent;
import com.aymegike.huminekingdom.utils.models.Kingdom;

public class PlayerDie extends GloryEvent {

	public PlayerDie(String event) {
		super(event);
	}

	@Override
	public void onPlayerBeGlorious(Player player, Event event) {
		
		Player death = player;
		Player killer = player.getKiller();
		
		if (death != null && killer != null) {
			
			Kingdom dk = HumineKingdom.getPlayerKingdom(death);
			Kingdom kk = HumineKingdom.getPlayerKingdom(killer);
			
			if (dk != null && kk != null) {
				if (!dk.equals(kk)) {
					dk.setGlory(dk.getGlory() - 35);
					dk.sendMessageToMembers(Message.LOST_POINTS(35));
					kk.setGlory(kk.getGlory() + 35);
					kk.sendMessageToMembers(Message.WIN_POINTS(35));
				} else {
					dk.setGlory(dk.getGlory() - 25);
					dk.sendMessageToMembers(Message.LOST_POINTS(25));
				}
			}
			
		} else if (death != null && killer == null){
			
			Kingdom dk = HumineKingdom.getPlayerKingdom(death);
			
			dk.setGlory(dk.getGlory() - 25);
			dk.sendMessageToMembers(Message.LOST_POINTS(25));
		}
		
	}

}
