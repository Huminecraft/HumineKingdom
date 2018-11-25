package com.aymegike.huminekingdom.utils.managers;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.listener.commands.HumineComand;

public class CommandManager {
	
	public CommandManager(HumineKingdom pl) {
		
		pl.getCommand("kingdom").setExecutor(new HumineComand());
		
	}

}
