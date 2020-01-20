package com.aymegike.huminekingdom.listener.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.listener.events.PlayerChatEvent;
import com.aymegike.huminekingdom.utils.MenuList;

public class HumineCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] arg) {
		
		Player p = (Player) sender;
		if(lbl.equalsIgnoreCase("kingdom"))
		{
			PlayerChatEvent.getNameOfGrade.remove(p);
			PlayerChatEvent.getNameOfKingdom.remove(p);
			PlayerChatEvent.getNameOfPlayer.remove(p);
			PlayerChatEvent.getNameOfShematic.remove(p);
			PlayerChatEvent.getNameOfTraitor.remove(p);
			if(HumineKingdom.getKingdomManager().getPlayerKingdom(p) == null)
			{
				MenuList.noKingdomMenu(p).openMenu();
			}
			else
			{
				MenuList.mainKingdomMenu(p).openMenu();
			}
		}
		
		return false;
	}

}
