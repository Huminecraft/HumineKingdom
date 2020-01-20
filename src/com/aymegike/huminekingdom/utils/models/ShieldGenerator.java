package com.aymegike.huminekingdom.utils.models;

import org.bukkit.Location;

import com.aymegike.huminekingdom.utils.KingdomZone;

public class ShieldGenerator {
	
	private Kingdom kingdom;
	private Location location;
	private KingdomZone zone;
	private boolean isActive;
	
	public ShieldGenerator(Kingdom kingdom, Location location, KingdomZone zone, boolean isActive) {
		this.kingdom = kingdom;
		this.location = location;
		this.zone = zone;
		this.isActive = isActive;
	}
	
	public Kingdom getKingdom() {
		return kingdom;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public KingdomZone getZone() {
		return zone;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public void setActive(boolean active) {
		this.isActive = active;
	}

}
