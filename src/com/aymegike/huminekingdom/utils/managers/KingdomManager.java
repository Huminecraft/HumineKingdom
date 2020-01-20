package com.aymegike.huminekingdom.utils.managers;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.OfflinePlayer;

import com.aymegike.huminekingdom.utils.models.Grade;
import com.aymegike.huminekingdom.utils.models.Kingdom;

public class KingdomManager {
	
	private ArrayList<Kingdom> kingdoms = new ArrayList<Kingdom>();
	private File kingdomList;
	
	public KingdomManager() {
		
		kingdomList = new File(FileManager.KINGDOM_FILE);
		File[] list = kingdomList.listFiles();
		
		for (File file : list) {
			kingdoms.add(new Kingdom(file.getName()));
		}
		
	}
	
	public void addKingdom(Kingdom kingdom) {
		kingdoms.add(kingdom);
	}
	
	public void removeKingdom(Kingdom kingdom) {
		kingdoms.remove(kingdom); 
	}
	
	public ArrayList<Kingdom> getKingdomList(){
		return kingdoms;
	}
	

	public Kingdom getPlayerKingdom(OfflinePlayer player)
	{
		if (player != null)
		{
			for (Kingdom kingdom : getKingdomList())
			{
				for (OfflinePlayer op : kingdom.getMembers())
				{
					if (op.getUniqueId().equals(player.getUniqueId()))
					{
						return kingdom;
					}
				}
			}
		}		
		return null;
	}


	public Grade getPlayerGrade(OfflinePlayer player)
	{		
		Kingdom playerKingdom = getPlayerKingdom(player);
		if (playerKingdom != null)
		{
			for (Grade grade : getPlayerKingdom(player).getGrades())
			{
				if (grade.getMembers().contains(player))
				{
					return grade;
				}
			}
		}
		
		return null;
	}

	public boolean playerHasKingdomPermission(OfflinePlayer player, String permission)
	{
		Grade playerGrade = getPlayerGrade(player);
		return playerGrade != null && playerGrade.getPermissions().contains(permission);
	}
}
