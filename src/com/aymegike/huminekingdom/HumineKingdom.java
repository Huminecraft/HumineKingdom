package com.aymegike.huminekingdom;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.aymegike.huminekingdom.utils.MenuList;
import com.aymegike.huminekingdom.utils.managers.CommandManager;
import com.aymegike.huminekingdom.utils.managers.EggManager;
import com.aymegike.huminekingdom.utils.managers.FileManager;
import com.aymegike.huminekingdom.utils.managers.GloryManager;
import com.aymegike.huminekingdom.utils.managers.KingdomManager;
import com.aymegike.huminekingdom.utils.models.Kingdom;

public class HumineKingdom extends JavaPlugin
{
	private static FileManager fileManager;
	private static KingdomManager kManager;
	private static GloryManager gloryManager;
	private static EggManager eggManager;
	
	public void onEnable()
	{
		System.out.println("-------------------------------------------");
		System.out.println("[HumineKingdom] HUMINE PRODUCTION !");
		System.out.println("-------------------------------------------");
		new com.aymegike.huminekingdom.utils.managers.EventManager(this);
		fileManager = new FileManager();
		kManager = new KingdomManager();
		gloryManager = new GloryManager();
		eggManager = new EggManager();
		new CommandManager(this);
	}
	
	public void onDisable()
	{
		for (Player player : Bukkit.getOnlinePlayers())
		{
			MenuList.closePlayerMenu(player);
		}
	}
	
	public static FileManager getFileManager() {
		return fileManager;
	}
	
	public static Kingdom getKingdom(String name) {
		
		for (Kingdom kingdom : kManager.getKingdomList()) {
			if (kingdom.getName().equalsIgnoreCase(name)) {
				return kingdom;
			}
		}
		
		return null;
	}
	
	public static KingdomManager getKingdomManager() {
		return kManager;
	}
		
	public static GloryManager getGloryManager() {
		return gloryManager;
	}
	
	public static EggManager getEggManager() {
		return eggManager;
	}
}
