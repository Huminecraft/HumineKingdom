package com.aymegike.huminekingdom.utils.managers;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.aymegike.huminekingdom.utils.models.GloryEvent;
import com.aymegike.huminekingdom.utils.models.glory.GloryBlock;

public class GloryManager implements Listener {
	
	private ArrayList<GloryEvent> ges;
	
	public GloryManager() {
		ges = new ArrayList<GloryEvent>();
		ges.add(new GloryBlock("BlockPlaceEvent"));
	}
	
	private void update(Player player, Event event) {
		for (GloryEvent ge : ges) {
			if (ge.getEvent().equalsIgnoreCase(event.getEventName())) {
				ge.onPlayerBeGlorious(player, event);
			}
		}
	}
	
	public void addEvent(GloryEvent gloryEvent) {
		ges.add(gloryEvent);
	}
	
	public void removeEvent(GloryEvent gloryEvent) {
		ges.remove(gloryEvent);
	}
	
	public ArrayList<GloryEvent> getGloryEvents() {
		return ges;
	}
	
	//implementer une liste d'events susebtible d'être utilisé
	@EventHandler
	public void onPlayerPlaceBlock(BlockPlaceEvent e) {
		update(e.getPlayer(), e);
	}
	
	
	
	

}
