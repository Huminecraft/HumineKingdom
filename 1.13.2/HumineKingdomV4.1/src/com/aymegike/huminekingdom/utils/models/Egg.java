package com.aymegike.huminekingdom.utils.models;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.aymegike.huminekingdom.HumineKingdom;

public class Egg {
	
	private Location location;
	private Kingdom kingdom;
	
	public Egg(Kingdom kingdom, Location location, boolean place) {
		this.location = location;
		this.kingdom = kingdom;
		if (place)
			spawnEgg();
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public Kingdom getKingdom() {
		return this.kingdom;
	}
	
	private void spawnEgg() {
		registerBlockBefore();
		//TODO ANIMATION
	}
	
	public void destroyEgg() {
		replaceBlocks();
		//TODO ANIMATION
	}
	
	
	
	
	
	
	private void registerBlockBefore() {
		for (int x = location.getBlockX()-1 ; x<=location.getBlockX()+1 ; x++) {
			for (int z = location.getBlockZ()-1 ; z<=location.getBlockZ()+1 ; z++) {
				Block block = new Location(location.getWorld(), x, location.getY()-1, z).getBlock();
				HumineKingdom.getEggManager().getFileManager().printLine("block: "+block.getType()+" "+block.getWorld().getName()+" "+block.getLocation().getBlockX()+" "+block.getLocation().getBlockY()+" "+block.getLocation().getBlockZ());
			}
		}
	}
	
	private void replaceBlocks() {
		for (String line : HumineKingdom.getEggManager().getFileManager().getTextFile()) {
			String[] args = line.split(" ");
			if (args[0].equalsIgnoreCase("block:")) {
				Location loc = new Location(Bukkit.getWorld(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
				Material m = Material.valueOf(args[1]);
				loc.getBlock().setType(m);
			}
		}
	}
	
}
