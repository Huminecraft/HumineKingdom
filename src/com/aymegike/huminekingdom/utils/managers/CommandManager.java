package com.aymegike.huminekingdom.utils.managers;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.listener.commands.HumineCommand;

public class CommandManager {
	
	public CommandManager(HumineKingdom pl) {
		
		pl.getCommand("kingdom").setExecutor(new HumineCommand());
		
	}

}
