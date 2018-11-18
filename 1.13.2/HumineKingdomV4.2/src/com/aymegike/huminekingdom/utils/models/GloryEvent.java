package com.aymegike.huminekingdom.utils.models;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public abstract class GloryEvent {
	
	private String event;
	
	public GloryEvent(String event) {
		this.event = event;
	}
	
	public abstract void onPlayerBeGlorious(Player player, Event event);
	
	public String getEvent() {
		return event;
	}
	

}
