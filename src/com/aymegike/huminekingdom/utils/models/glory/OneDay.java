package com.aymegike.huminekingdom.utils.models.glory;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.Message;
import com.aymegike.huminekingdom.utils.models.GloryEvent;
import com.aymegike.huminekingdom.utils.models.Kingdom;
import com.aypi.manager.FileManager;

public class OneDay extends GloryEvent {

	public OneDay(String event) {
		super(event);
	}

	@Override
	public void onPlayerBeGlorious(Player player, Event event) {
		FileManager playerConnection = new FileManager(new File("plugins/HumineKingdom/playerlog.craft"));
		boolean isRegister = false;
		String playerLine = "";
		for (String line : playerConnection.getTextFile()) {
			if (line.contains(player.getUniqueId().toString())) {
				isRegister = true;
				playerLine = line;
				break;
			}
		}
		
		if (isRegister) {
			
			int mounth = Integer.parseInt(playerLine.split(" ")[1]);
			int date = Integer.parseInt(playerLine.split(" ")[2]);
						
			if (mounth != new Date().getMonth() || date != new Date().getDate()) {
				playerConnection.removeAllLine(playerLine);
				playerConnection.printLine(player.getUniqueId().toString()+" "+new Date().getMonth()+" "+new Date().getDate());
				Kingdom kingdom = HumineKingdom.getKingdomManager().getPlayerKingdom(player);
				if (kingdom != null) {
					kingdom.addGlory(10);
					kingdom.sendMessageToMembers(Message.WIN_POINTS(10));
				}
			}
		} else {
			
			playerConnection.removeAllLine(playerLine);
			playerConnection.printLine(player.getUniqueId().toString()+" "+new Date().getMonth()+" "+new Date().getDate());
			
		}
	}

}
