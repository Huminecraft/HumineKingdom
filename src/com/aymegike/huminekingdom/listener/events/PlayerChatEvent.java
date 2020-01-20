package com.aymegike.huminekingdom.listener.events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.MenuList;
import com.aymegike.huminekingdom.utils.Message;
import com.aymegike.huminekingdom.utils.Permissions;
import com.aymegike.huminekingdom.utils.models.Grade;
import com.aymegike.huminekingdom.utils.models.Kingdom;
import com.aymegike.huminekingdom.utils.models.Shematic;

public class PlayerChatEvent implements Listener {
	
	public static ArrayList<OfflinePlayer> getNameOfKingdom = new ArrayList<OfflinePlayer>();
	public static ArrayList<OfflinePlayer> getNameOfPlayer = new ArrayList<OfflinePlayer>();
	public static ArrayList<OfflinePlayer> getNameOfTraitor = new ArrayList<OfflinePlayer>();
	public static ArrayList<OfflinePlayer> getNameOfGrade = new ArrayList<OfflinePlayer>();
	public static ArrayList<OfflinePlayer> getNameOfShematic = new ArrayList<OfflinePlayer>();
	
	@EventHandler
	public void onPlayerUseChat(org.bukkit.event.player.PlayerChatEvent e)
	{		
		Player player = e.getPlayer();
		if (getNameOfKingdom.contains(player))
		{
			e.setCancelled(true);
			String msg = e.getMessage();
			String[] getSpace = msg.split(" ");
			if(getSpace.length == 1)
			{
				if(HumineKingdom.getKingdom(msg) != null)
				{
					player.sendMessage(Message.KINGDOM_NAME_CANCEL(msg));
					player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
					getNameOfKingdom.remove(player);
					return;
				}
				player.sendMessage(Message.KINGDOM_VALIDE_CONSTRUCT);
				player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 5, 1);
				new Kingdom(msg, player, "Roi");
				MenuList.mainKingdomMenu(player).openMenu();
				
			}
			else
			{				
				player.sendMessage(Message.KINGDOM_INVALIDE_NAME);
				player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);			
			}
			
			getNameOfKingdom.remove(player);	
		}
		else if(getNameOfPlayer.contains(player))
		{
			e.setCancelled(true);
			String msg = e.getMessage();
			Kingdom playerKingdom = HumineKingdom.getKingdomManager().getPlayerKingdom(player);
			if(playerKingdom != null)
			{
				Player invitedPlayer = Bukkit.getPlayer(msg);
				if(invitedPlayer != null)
				{
					if(HumineKingdom.getKingdomManager().getPlayerKingdom(invitedPlayer) == null)
					{
						player.sendMessage(Message.KINGDOM_INVATION_NAME(msg));
						player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 1);
						MenuList.invitationMenu(player, invitedPlayer).openMenu();
						invitedPlayer.playSound(invitedPlayer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 1);
					}
					else
					{
						player.sendMessage(Message.KINGDOM_INVATION_ARLY_INVITED(msg));
						player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
					}
				}
				else
				{					
					player.sendMessage(Message.KINGDOM_INVITATION_NAME_DONT_CONNECT(msg));
					player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);				
				}
			}
			
			MenuList.membersMenu(player).openMenu();
			getNameOfPlayer.remove(player);
		}		
		else if(getNameOfTraitor.contains(player))
		{
			e.setCancelled(true);
			String msg = e.getMessage();
			Kingdom playerKingdom = HumineKingdom.getKingdomManager().getPlayerKingdom(player);
			if(playerKingdom != null)
			{
				Player traitor = Bukkit.getPlayer(msg);
				if(traitor != null)
				{
					if (traitor.getUniqueId() == player.getUniqueId())
					{
						player.sendMessage("Mais.. Ne te déclare pas comme un traître voyons. Sois un peu plus discret.");
						player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);		
					}
					else if (playerKingdom.isTraitor(traitor))
					{
						player.sendMessage("Ce traître a déjà été ajouté.");
						player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);							
					}
					else if (playerKingdom.isOldMember(traitor))
					{
						playerKingdom.addTraitor(Bukkit.getOfflinePlayer(traitor.getUniqueId()));
						player.sendMessage("Ce vilain traître a bien été ajouté.");
						player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 1);
						
					}
					else if (playerKingdom.isMember(traitor))
					{
						playerKingdom.removeMember(traitor);
						playerKingdom.addTraitor(Bukkit.getOfflinePlayer(traitor.getUniqueId()));
						player.sendMessage("Ce vilain traître a bien été expulsé du royaume !");
						player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 1);
						
					}
					else
					{
						player.sendMessage("Ce joueur n'a jamais été dans ce royaume, il ne peut pas vous avoir trahi.");
						player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);						
					}
										
				}
				else
				{					
					player.sendMessage(Message.KINGDOM_INVITATION_NAME_DONT_CONNECT(msg));
					player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);				
				}
			}
			
			MenuList.traitorsMenu(player).openMenu();
			getNameOfTraitor.remove(player);
		}		
		else if (getNameOfGrade.contains(player))
		{
			e.setCancelled(true);
			String msg = e.getMessage();
			String[] getSpace = msg.split(" ");
			if(getSpace.length == 1){
				if(HumineKingdom.getKingdomManager().getPlayerKingdom(player).getGrade(msg) != null){
					player.sendMessage(Message.GRADE_NAME_CANCEL(msg));
					player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
					getNameOfGrade.remove(player);
					return;
				}
				player.sendMessage(Message.GRADE_VALIDE_CONSTRUCT);
				player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 5, 1);
				HumineKingdom.getKingdomManager().getPlayerKingdom(player).addGrade(new Grade(HumineKingdom.getKingdomManager().getPlayerKingdom(player), msg));
				MenuList.gradeListMenu(player).openMenu();
				
			}else{				
				player.sendMessage(Message.GRADE_INVALIDE_NAME);
				player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
			
			}
			
			getNameOfGrade.remove(player);	
			
		} else if(getNameOfShematic.contains(player)) {
			e.setCancelled(true);
			
			String msg = e.getMessage();
			String[] getSpace = msg.split(" ");
			
			if(getSpace.length == 1){
				if(HumineKingdom.getKingdomManager().getPlayerKingdom(player).getShematic(msg) != null){
					player.sendMessage(Message.SHEMATIC_NAME_CANCEL(msg));
					player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
					getNameOfGrade.remove(player);
					return;
				}
				player.sendMessage(Message.SHEMATIC_VALIDE_CONSTRUCT);
				player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 5, 1);
				HumineKingdom.getKingdomManager().getPlayerKingdom(player).addShematic(new Shematic(HumineKingdom.getKingdomManager().getPlayerKingdom(player), msg, PlayerClick.getShieldGenerator.get(player)));
				MenuList.shematicMenu(player).openMenu();
				PlayerClick.getShieldGenerator.remove(player);
				
			} else{				
				player.sendMessage(Message.SHEMATIC_INVALIDE_NAME);
				player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
			
			}
			
			getNameOfShematic.remove(player);
			
		} else {
			
			if(HumineKingdom.getKingdomManager().getPlayerGrade(player) != null){
				if(HumineKingdom.getKingdomManager().getPlayerGrade(player).containPermission(Permissions.CHAT.getPermission())){
					String msg = e.getMessage();
					String[] args = msg.split("");
					if(args[0].equalsIgnoreCase("*")){
						for(OfflinePlayer op : HumineKingdom.getKingdomManager().getPlayerKingdom(player).getMembers()){
							if(op.isOnline()){
								if(HumineKingdom.getKingdomManager().getPlayerGrade(player).containPermission(Permissions.CHAT.getPermission())){
									e.setCancelled(true);
									op.getPlayer().playSound(op.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
									op.getPlayer().sendMessage(ChatColor.BLACK+"["+ChatColor.DARK_PURPLE+HumineKingdom.getKingdomManager().getPlayerKingdom(player).getName()+ChatColor.BLACK+"-"+ChatColor.DARK_PURPLE+HumineKingdom.getKingdomManager().getPlayerGrade(player).getName()+ChatColor.BLACK+"] "+ChatColor.WHITE+player.getName()+ChatColor.WHITE+": "+ChatColor.GRAY+msg.replace("*", ""));								
								}
							}
						}
						System.out.println("["+HumineKingdom.getKingdomManager().getPlayerKingdom(player).getName()+"-"+HumineKingdom.getKingdomManager().getPlayerGrade(player).getName()+"] "+player.getName()+": "+msg.replace("*", ""));
						
					}
				}
			}
		}		
	}

}
