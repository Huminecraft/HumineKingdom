package com.aymegike.huminekingdom.utils.managers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.listener.events.BreakBlock;
import com.aymegike.huminekingdom.listener.events.Death;
import com.aymegike.huminekingdom.listener.events.ExplosionBlock;
import com.aymegike.huminekingdom.listener.events.InventoryListener;
import com.aymegike.huminekingdom.listener.events.ItemDrop;
import com.aymegike.huminekingdom.listener.events.PlaceBlock;
import com.aymegike.huminekingdom.listener.events.PlayerChatEvent;
import com.aymegike.huminekingdom.listener.events.PlayerClick;
import com.aymegike.huminekingdom.listener.events.PlayerLeft;
import com.aymegike.huminekingdom.listener.events.TeleportEvent;

public class EventManager {
	
	public EventManager(HumineKingdom pl) {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerChatEvent(), pl);
		pm.registerEvents(new PlaceBlock(), pl);
		pm.registerEvents(new BreakBlock(), pl);
		pm.registerEvents(new ExplosionBlock(), pl);
		pm.registerEvents(new PlayerClick(), pl);
		pm.registerEvents(new GloryManager(), pl);
		pm.registerEvents(new TeleportEvent(), pl);
		pm.registerEvents(new PlayerLeft(), pl);
		pm.registerEvents(new ItemDrop(), pl);
		pm.registerEvents(new Death(), pl);
		pm.registerEvents(new InventoryListener(), pl);
	}

}
