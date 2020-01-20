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
			
			Kingdom kingdomOfDead = HumineKingdom.getKingdomManager().getPlayerKingdom(death);
			Kingdom kingdomOfKiller = HumineKingdom.getKingdomManager().getPlayerKingdom(killer);
			
			if (kingdomOfDead != null && kingdomOfKiller != null)
			{
				if (!kingdomOfDead.equals(kingdomOfKiller))
				{
					if (kingdomOfDead.getGlory() > 0)
					{
						kingdomOfDead.removeGlory(35);
						kingdomOfDead.sendMessageToMembers(Message.LOST_POINTS(35));
					}
					kingdomOfKiller.addGlory(35);
					kingdomOfKiller.sendMessageToMembers(Message.WIN_POINTS(35));
				}
			}
			else if (kingdomOfDead != null)
			{
				if (kingdomOfDead.getGlory() > 0)
				{
					kingdomOfDead.removeGlory(35);
					kingdomOfDead.sendMessageToMembers(Message.LOST_POINTS(25));
				}
			}
			
		} else if (death != null && killer == null){
			
			Kingdom dk = HumineKingdom.getKingdomManager().getPlayerKingdom(death);
			
			if (dk != null)
			{
				dk.removeGlory(25);
				dk.sendMessageToMembers(Message.LOST_POINTS(25));
			}
		}
		
	}

}
