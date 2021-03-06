package com.aymegike.huminekingdom.utils.managers;
import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.Message;
import com.aymegike.huminekingdom.utils.models.Egg;
import com.aymegike.huminekingdom.utils.models.Kingdom;
import com.aypi.manager.FileManager;

public class EggManager {
	
	private FileManager fm;
	private Egg egg = null;
	
	private Location loc = null;
	private Kingdom kingdom = null;
	
	
	public EggManager()
	{		
		fm = new com.aypi.manager.FileManager(new File(com.aymegike.huminekingdom.utils.managers.FileManager.KINGDOM_FILE+"/../egg.craft"));
		
		for (String line : fm.getTextFile())
		{
			String[] args = line.split(" ");
			if (args[0].equalsIgnoreCase("location:"))
			{
				loc = new Location(Bukkit.getWorld(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
			}
			else if (args[0].equalsIgnoreCase("kingdom:")) {
				kingdom = HumineKingdom.getKingdom(args[1]);
			}
		}
		
		if (loc != null && kingdom != null) {
			this.egg = new Egg(kingdom, loc, false);
		}
		
	}
	
	public void setUpEgg(Kingdom kingdom, Location loc) {
		this.kingdom = kingdom;
		this.loc = new Location(loc.getWorld(), loc.getBlockX(), loc.getY(), loc.getBlockZ());
		fm.clearFile();
		fm.printLine("kingdom: "+kingdom.getName());
		fm.printLine("location: "+loc.getWorld().getName()+" "+loc.getBlockX()+" "+loc.getBlockY()+" "+loc.getBlockZ());
		this.egg = new Egg(kingdom, loc, true);
	}
	
	public FileManager getFileManager() {
		return fm;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public Kingdom getKingdom() {
		return kingdom;
	}
	
	public Egg getEgg() {
		return egg;
	}
	
	public void removeEgg(Player player)
	{
		Kingdom playerKingdom = HumineKingdom.getKingdomManager().getPlayerKingdom(player);
		playerKingdom.addGlory(400);
		egg.getKingdom().removeGlory(400);
		
		if (!playerKingdom.equals(egg.getKingdom()))
		{
			playerKingdom.sendMessageToMembers(Message.WIN_POINTS(400));
			egg.getKingdom().sendMessageToMembers(Message.LOST_POINTS(400));
		}
			
		this.egg = null;
		fm.clearFile();
	}

}
