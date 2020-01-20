package com.aymegike.huminekingdom.utils.managers;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.aymegike.huminekingdom.utils.models.GloryEvent;
import com.aymegike.huminekingdom.utils.models.glory.ActiveTnt;
import com.aymegike.huminekingdom.utils.models.glory.DestroyBeacon;
import com.aymegike.huminekingdom.utils.models.glory.GloryBlock;
import com.aymegike.huminekingdom.utils.models.glory.KingUseEgg;
import com.aymegike.huminekingdom.utils.models.glory.OneDay;
import com.aymegike.huminekingdom.utils.models.glory.PlaceBeacon;
import com.aymegike.huminekingdom.utils.models.glory.PlaceTnt;
import com.aymegike.huminekingdom.utils.models.glory.PlayerDie;

public class GloryManager implements Listener {
	
	private ArrayList<GloryEvent> ges;
	
	public GloryManager() {
		ges = new ArrayList<GloryEvent>();
		ges.add(new GloryBlock("BlockPlaceEvent"));
		ges.add(new KingUseEgg("PlayerInteractEvent"));
		ges.add(new PlaceBeacon("BlockPlaceEvent"));
		ges.add(new DestroyBeacon("BlockBreakEvent"));
		ges.add(new DestroyBeacon("EntityExplodeEvent"));
		ges.add(new OneDay("PlayerJoinEvent"));
		ges.add(new PlaceTnt("BlockPlaceEvent"));
		ges.add(new PlaceTnt("PlayerInteractEvent"));
		ges.add(new ActiveTnt("PlayerInteractEvent"));
		ges.add(new PlayerDie("PlayerDeathEvent"));
	}
	
	private void update(Player player, Event event)
	{
		for (GloryEvent ge : ges)
		{
			if (ge.getEvent().equalsIgnoreCase(event.getEventName()))
			{
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
	
	@EventHandler
	public void onPlayerBreakBlock(BlockBreakEvent e) {
		update(e.getPlayer(), e);
	}
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent e) {

		System.out.println("ON PASSE LEVENT");
		update(null, e);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		update(e.getPlayer(), e);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		update(e.getPlayer(), e);
	}
	
	@EventHandler
	public void onPlayerDie(PlayerDeathEvent e) {
		update(e.getEntity(), e);
	}
	
	
	
	

}
