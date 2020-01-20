package com.aymegike.huminekingdom.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.listener.events.PlayerChatEvent;
import com.aymegike.huminekingdom.listener.events.PlayerClick;
import com.aymegike.huminekingdom.utils.models.Grade;
import com.aymegike.huminekingdom.utils.models.Kingdom;
import com.aymegike.huminekingdom.utils.models.Shematic;
import com.aypi.manager.FileManager;
import com.aypi.utils.Button;
import com.aypi.utils.Menu;
import com.aypi.utils.inter.MenuItemListener;

import net.md_5.bungee.api.ChatColor;

public class MenuList {
	
	public static ArrayList<Menu> user = new ArrayList<Menu>();
	
	
	//MENU CREATION D'UN ROYAUME
	public static Menu noKingdomMenu(Player player) {
		closePlayerMenu(player);

		Menu menu = new Menu(player, ChatColor.DARK_PURPLE+"Tu n'as pas encore de royaume !", 3*9, true);
		
		ArrayList<String> lore = new ArrayList<String>();
		
		ItemStack emerald = new ItemStack(Material.EMERALD);
		ItemMeta emeraldM = emerald.getItemMeta();
		emeraldM.setDisplayName(ChatColor.GREEN+"INFORMATION !");
		lore.add(ChatColor.DARK_PURPLE+"Afin de devenir plus puissant, tu");
		lore.add(ChatColor.DARK_PURPLE+"peux créer ton royaume et recruter");
		lore.add(ChatColor.DARK_PURPLE+"des amis pour dominer le monde de "+ChatColor.WHITE+"HumineCraft"+ChatColor.DARK_PURPLE+" !");
		emeraldM.setLore(lore);
		emerald.setItemMeta(emeraldM);
		
		lore.clear();
		
		lore.add(ChatColor.DARK_PURPLE+"Cliquez pour valider");
		
		ItemStack accept = new ItemStack(Material.EMERALD_BLOCK);
		ItemMeta acceptm = accept.getItemMeta();
		acceptm.setDisplayName(ChatColor.YELLOW+"Créer un nouveau Royaume !"+ChatColor.GREEN);
		acceptm.setLore(lore);
		accept.setItemMeta(acceptm);
		
		ItemStack decline = new ItemStack(Material.REDSTONE_BLOCK);
		ItemMeta declineM = decline.getItemMeta();
		declineM.setDisplayName(ChatColor.YELLOW+"Non une autre fois peut-être..."+ChatColor.RED);
		declineM.setLore(lore);
		decline.setItemMeta(declineM);
		
		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 1);
		menu.fillLine(glass, 3);
		
		glass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
		glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 2);
		
		menu.setButton(10, new Button(accept, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				 player.sendMessage(Message.KINGDOM_CREATE);
				 player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
				 if(!PlayerChatEvent.getNameOfKingdom.contains(player))
					 PlayerChatEvent.getNameOfKingdom.add(player);		
				 menu.closePlayerMenu();
				 MenuList.user.remove(menu);
			}
			
		}));
		
		menu.setButton(16, new Button(decline, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
				player.sendMessage(Message.KINGDOM_CANCEL);
				MenuList.user.remove(menu);
			}
			
		}));
		
		menu.setItem(13, emerald);
		
		MenuList.user.add(menu);
		return menu;
	}
	
	//MENU PRINCIPALE
	public static Menu mainKingdomMenu(Player player) {
		closePlayerMenu(player);

		Menu menu = new Menu(player, ChatColor.DARK_PURPLE+"-Votre royaume-", 6*9, false);
		
		ItemStack close = new ItemStack(Material.BARRIER);
		ItemMeta closem = close.getItemMeta();
		closem.setDisplayName(ChatColor.RED+"Fermer");
		close.setItemMeta(closem);

		ItemStack quit = new ItemStack(Material.REDSTONE_BLOCK);
		ItemMeta quitm = quit.getItemMeta();
		quitm.setDisplayName(ChatColor.RED+"Quitter le royaume");
		quit.setItemMeta(quitm);

		ItemStack gestion = new ItemStack(Material.BEACON);
		ItemMeta gestionm = gestion.getItemMeta();
		gestionm.setDisplayName(ChatColor.AQUA+"Gestion des Générateurs de bouclier");
		gestion.setItemMeta(gestionm);
		
		ItemStack membre = new ItemStack(Material.PLAYER_HEAD);//, 1, (byte) SkullType.PLAYER.ordinal());
		SkullMeta membrem = (SkullMeta) membre.getItemMeta();
		membrem.setDisplayName(ChatColor.GREEN+"Liste des membres");
		membrem.setOwningPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
		membre.setItemMeta(membrem);
		
		ItemStack traitor = new ItemStack(Material.PLAYER_HEAD);//, 1, (byte) SkullType.PLAYER.ordinal());
		SkullMeta traitorm = (SkullMeta) membre.getItemMeta();
		traitorm.setDisplayName(ChatColor.GREEN+"Liste des traîtres");
		traitorm.setOwningPlayer(Bukkit.getOfflinePlayer("MrMonsterLP"));
		traitor.setItemMeta(traitorm);
		
		ItemStack grade = new ItemStack(Material.NAME_TAG);
		ItemMeta gradem = grade.getItemMeta();
		gradem.setDisplayName(ChatColor.BLUE+"Liste des grades");
		grade.setItemMeta(gradem);
		
		ItemStack classement = new ItemStack(Material.DRAGON_EGG);
		ItemMeta classementm = classement.getItemMeta();
		classementm.setDisplayName(ChatColor.DARK_PURPLE+"Classement");
		classement.setItemMeta(classementm);
		
		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 1);
		menu.fillLine(glass, 3);
		menu.fillLine(glass, 4);
		menu.fillLine(glass, 6);
		
		glass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
		glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 2);
		menu.fillLine(glass, 5);
		
		Grade playerGrade = HumineKingdom.getKingdomManager().getPlayerGrade(player);

		if (playerGrade != null && playerGrade.containPermission(Permissions.EXCLUDE.getPermission()))
		{
				menu.setButton(10, new Button(gestion, new MenuItemListener()
				{			
					@Override
					public void onItemClick()
					{
						menu.closePlayerMenu();
						player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
						MenuList.GestionMenu(player).openMenu();
						MenuList.user.remove(menu);
					}
					
				}));
		}
		
		menu.setButton(13, new Button(membre, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				MenuList.membersMenu(player).openMenu();
				MenuList.user.remove(menu);
			}
			
		}));
		
		menu.setButton(22, new Button(traitor, new MenuItemListener()
		{			
			@Override
			public void onItemClick()
			{
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				MenuList.traitorsMenu(player).openMenu();
				MenuList.user.remove(menu);
			}
			
		}));
		
		menu.setButton(16, new Button(grade, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				MenuList.gradeListMenu(player).openMenu();
				MenuList.user.remove(menu);
			}
			
		}));
		
		menu.setButton(40, new Button(classement, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				MenuList.rankedMenu(player).openMenu();
				MenuList.user.remove(menu);
			}
			
		}));

		Kingdom playerKingdom = HumineKingdom.getKingdomManager().getPlayerKingdom(player);
		if (playerKingdom != null)
		{
			menu.setButton(43, new Button(quit, new MenuItemListener() {
				
				@Override
				public void onItemClick()
				{		
					MenuList.validSupressionKingdomMenu(player, playerKingdom).openMenu();					
				}			
			}));
		}
		
		menu.setButton(45, new Button(close, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				MenuList.user.remove(menu);
			}
			
		}));
		
		menu.setButton(53, new Button(close, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				MenuList.user.remove(menu);
			}
			
		}));
		
		MenuList.user.add(menu);
		return menu;
	}
	
	//-----------------------------------------------------------
	// MENU DES MEMBRES
	//-----------------------------------------------------------
	
	public static Menu membersMenu(Player player) {
		closePlayerMenu(player);
		int size = HumineKingdom.getKingdomManager().getPlayerKingdom(player).getMembers().size();
		int c = 0;
		int multi = 1;
		for(int i = 1 ; i<size-2 ; i++){
			c++;
			if(c>=9){				
				c = 0;
				multi++;
			}
		}
		
		Menu menu = new Menu(player, ChatColor.DARK_GREEN+"- Membres -", (multi+1)*9, false);

		Kingdom playerKingdom = HumineKingdom.getKingdomManager().getPlayerKingdom(player);
		Grade playerGrade = HumineKingdom.getKingdomManager().getPlayerGrade(player);
		
		boolean playerHasGrade = playerGrade != null;
		boolean playerCanExclude = playerHasGrade && playerGrade.containPermission(Permissions.EXCLUDE.getPermission());
		boolean playerCanManageGrade = playerHasGrade && playerGrade.containPermission(Permissions.MANAGE_GRADE.getPermission());
		boolean shouldOpenProfilMenu = playerCanExclude || playerCanManageGrade;
				
		ArrayList<String> lore = new ArrayList<String>();
		
		ItemStack quit = new ItemStack(Material.BARRIER);
		ItemMeta quitm = quit.getItemMeta();
		quitm.setDisplayName(ChatColor.RED+"Quitter");
		quit.setItemMeta(quitm);
		
		lore.clear();
		
		ItemStack ice = new ItemStack(Material.ICE);
		ItemMeta icem = ice.getItemMeta();
		icem.setDisplayName(ChatColor.AQUA+"Raffraîchir la page");
		ice.setItemMeta(icem);
		
		ItemStack glass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, menu.getSize()/9);
		
		int slot = 0;
		for (OfflinePlayer op : playerKingdom.getMembers())
		{
			
			ItemStack membre = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta membrem = (SkullMeta) membre.getItemMeta();
			membrem.setDisplayName(ChatColor.GREEN+op.getName());
			membrem.setOwningPlayer(op);
			if (HumineKingdom.getKingdomManager().getPlayerGrade(op) != null)
			{
				lore.add(ChatColor.DARK_PURPLE+"Grade: "+ChatColor.WHITE+ HumineKingdom.getKingdomManager().getPlayerGrade(op).getName());
			}
			else
			{
				lore.add(ChatColor.DARK_PURPLE+"Grade: "+ChatColor.WHITE+"Aucun");
			}
			if (shouldOpenProfilMenu)
			{
				lore.add(ChatColor.WHITE + "" +  ChatColor.ITALIC + "Cliquez pour gérer le profil de ce membre.");
			}
			membrem.setLore(lore);
			lore.clear();
			membre.setItemMeta(membrem);
			if (shouldOpenProfilMenu)
			{
				menu.setButton(slot, new Button(membre, new MenuItemListener()
				{				
					@Override
					public void onItemClick()
					{
						menu.closePlayerMenu();
						player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
						if (HumineKingdom.getKingdomManager().getPlayerKingdom(op) == playerKingdom)
							MenuList.playerProfilMenu(player, op).openMenu();
						else
							MenuList.membersMenu(player).openMenu();
					}				
				}));
			}
			else
			{
				menu.setItem(slot, membre);
			}
			
			slot++;
		}
		
		menu.setButton(menu.getSize()-1, new Button(quit, new MenuItemListener()
		{			
			@Override public void onItemClick()
			{
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				MenuList.mainKingdomMenu(player).openMenu();
				MenuList.user.remove(menu);
			}
			
		}));
		
		
		if (playerGrade != null && playerGrade.containPermission(Permissions.INVITE.getPermission()))
		{
			ItemStack add = new ItemStack(Material.WRITABLE_BOOK);
			ItemMeta addm = add.getItemMeta();
			addm.setDisplayName(ChatColor.GREEN+"Ajouter un joueur");
			add.setItemMeta(addm);
			
			
			menu.setButton(menu.getSize()-9, new Button(add, new MenuItemListener()
			{			
				@Override
				public void onItemClick()
				{
					menu.closePlayerMenu();	
					
					if (playerGrade != null && playerGrade.containPermission(Permissions.INVITE.getPermission()))
					{
						player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
						PlayerChatEvent.getNameOfPlayer.add(player);
						player.sendMessage(Message.KINGDOM_INVITATION);
					}
					else
					{
						  player.sendMessage(Message.PERMISSION);
						  player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
					}
						
					MenuList.user.remove(menu);				
				}
				
			}));
		}
		
		menu.setButton(menu.getSize()-5, new Button(ice, new MenuItemListener()
		{			
			@Override
			public void onItemClick()
			{
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.BLOCK_SNOW_FALL, 5, 1);
				MenuList.membersMenu(player).openMenu();
			}			
		}));
		
		MenuList.user.add(menu);
		return menu;
	}
	
	//MENU D'INVITATION 
	public static Menu invitationMenu(Player sender, Player receiver) {
		closePlayerMenu(receiver);
		Menu menu = new Menu(receiver, ChatColor.BOLD+sender.getName()+ChatColor.DARK_PURPLE+ "t'invite dans "+ChatColor.WHITE+HumineKingdom.getKingdomManager().getPlayerKingdom(sender).getName()+ChatColor.DARK_GREEN+" !", 3*9, true);
		
		ArrayList<String> lore = new ArrayList<String>();
		
		ItemStack emerald = new ItemStack(Material.EMERALD);
		ItemMeta emeraldM = emerald.getItemMeta();
		emeraldM.setDisplayName(ChatColor.WHITE+sender.getName()+ChatColor.GREEN+" t'invite pour rejoindre "+ChatColor.WHITE+HumineKingdom.getKingdomManager().getPlayerKingdom(sender).getName());
		lore.add("");
		lore.add(ChatColor.GOLD+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		lore.add(ChatColor.DARK_PURPLE+"Afin de devenir plus puissant, tu");
		lore.add(ChatColor.DARK_PURPLE+"peux rejoindre "+ChatColor.WHITE+HumineKingdom.getKingdomManager().getPlayerKingdom(sender).getName()+ChatColor.DARK_PURPLE+" afin de ");
		lore.add(ChatColor.DARK_PURPLE+"dominer le monde de "+ChatColor.WHITE+"HumineCraft"+ChatColor.DARK_PURPLE+" !");
		lore.add(ChatColor.GOLD+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		emeraldM.setLore(lore);
		emerald.setItemMeta(emeraldM);
		
		lore.clear();
		
		lore.add(ChatColor.DARK_PURPLE+"Clique pour valider");
		
		ItemStack accept = new ItemStack(Material.EMERALD_BLOCK);
		ItemMeta acceptm = accept.getItemMeta();
		acceptm.setDisplayName(ChatColor.YELLOW+"Accepter l'invitation"+ChatColor.GREEN);
		acceptm.setLore(lore);
		accept.setItemMeta(acceptm);
		
		ItemStack decline = new ItemStack(Material.REDSTONE_BLOCK);
		ItemMeta declineM = decline.getItemMeta();
		declineM.setDisplayName(ChatColor.YELLOW+"Non une autre fois peut-être..."+ChatColor.RED);
		declineM.setLore(lore);
		decline.setItemMeta(declineM);
		
		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 1);
		menu.fillLine(glass, 3);
		
		glass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
		glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 2);
		
		menu.setButton(13, new Button(emerald, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				receiver.playSound(sender.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 50, 1);				
			}
			
		}));
		
		menu.setButton(10, new Button(accept, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				receiver.playSound(sender.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 50, 1);
				menu.closePlayerMenu();
				MenuList.user.remove(menu);
				HumineKingdom.getKingdomManager().getPlayerKingdom(sender).addMember(receiver);
				for(OfflinePlayer op : HumineKingdom.getKingdomManager().getPlayerKingdom(sender).getMembers()) {
					if (op.isOnline()) {
						op.getPlayer().sendMessage(Message.KINGDOM_WELCOM(sender, receiver));
						
					}
				}
			}
			
		}));
		
		menu.setButton(16, new Button(decline, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				receiver.playSound(sender.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 50, 1);
				receiver.sendMessage(Message.KINGDOM_INVITATION_REFUSE_RECEIVER);
				sender.sendMessage(Message.KINGDOM_INVITATION_REFUSE_SENDER(receiver));
				sender.playSound(sender.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
				menu.closePlayerMenu();
				MenuList.user.remove(menu);
			}
			
		}));
		
		MenuList.user.add(menu);
		return menu;
				
	}
	
	//PLAYER MENU
	public static Menu playerProfilMenu(Player player, OfflinePlayer target)
	{
		closePlayerMenu(player);
		Menu menu = new Menu(player, ChatColor.DARK_GREEN+"- "+ChatColor.WHITE+target.getName()+ChatColor.DARK_GREEN+" -", 3*9, false);
		
		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 1);
		menu.fillLine(glass, 3);
		
		glass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
		glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 2);
		
		ItemStack back = new ItemStack(Material.BARRIER);
		ItemMeta backm = back.getItemMeta();
		backm.setDisplayName(ChatColor.RED+"Retour");
		back.setItemMeta(backm);
		
		ArrayList<String> lore = new ArrayList<String>();
		
		if (HumineKingdom.getKingdomManager().getPlayerGrade(target) != null)
		{
			lore.add(ChatColor.DARK_PURPLE+"Grade: "+ChatColor.WHITE+HumineKingdom.getKingdomManager().getPlayerGrade(target).getName());
		}
		else
		{
			lore.add(ChatColor.DARK_PURPLE+"Grade: "+ChatColor.WHITE+"Aucun");
		}
		
		ItemStack membre = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta membrem = (SkullMeta) membre.getItemMeta();
		membrem.setDisplayName(ChatColor.GREEN+target.getName());
		membrem.setOwningPlayer(Bukkit.getOfflinePlayer(target.getUniqueId()));
		membrem.setLore(lore);
		membre.setItemMeta(membrem);
		
		lore.clear();
		
		ItemStack grade = new ItemStack(Material.NAME_TAG);
		ItemMeta gradem = grade.getItemMeta();
		gradem.setDisplayName(ChatColor.GREEN+"Changer le grade de "+ChatColor.WHITE+target.getName());
		grade.setItemMeta(gradem);
		
		ItemStack quitter = new ItemStack(Material.REDSTONE_BLOCK);
		ItemMeta quitterm = quitter.getItemMeta();
		
		if (player.getName().equalsIgnoreCase(target.getName())) {
			quitterm.setDisplayName(ChatColor.RED+"Quitter le royaume");
		} else {
			quitterm.setDisplayName(ChatColor.RED+"Exclure du royaume");
		}
		
		if (HumineKingdom.getKingdomManager().getPlayerGrade(target) != null && HumineKingdom.getKingdomManager().getPlayerGrade(target).getName().equalsIgnoreCase(HumineKingdom.getKingdomManager().getPlayerKingdom(target).getKingGradeName()) && player.getName().equalsIgnoreCase(target.getName())) {
			lore.add("");
			lore.add(ChatColor.RED+""+ChatColor.ITALIC+"Ton royaume sera supprimé et toute");
			lore.add(ChatColor.RED+"la gloire durement acquise avec sera perdue.");
			quitterm.setLore(lore);
		}
		
		quitter.setItemMeta(quitterm);
		
		menu.setButton(10, new Button(grade, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				if (HumineKingdom.getKingdomManager().getPlayerKingdom(player) == HumineKingdom.getKingdomManager().getPlayerKingdom(target))
				{					
					if (HumineKingdom.getKingdomManager().getPlayerGrade(target) != null && HumineKingdom.getKingdomManager().getPlayerGrade(target).getName().equalsIgnoreCase(HumineKingdom.getKingdomManager().getPlayerKingdom(target).getKingGradeName()))
					{
						for(OfflinePlayer op : HumineKingdom.getKingdomManager().getPlayerKingdom(player).getMembers()) {
							if (op.isOnline()) {
								op.getPlayer().sendMessage(Message.KINGDOM_MUNITY(player));
								op.getPlayer().playSound(op.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 5, 1);
							}
						}
						player.sendMessage(ChatColor.ITALIC+"(je crois que tu t'es grillé)");
						return; 
					}
					
					if (HumineKingdom.getKingdomManager().getPlayerGrade(player) != null && HumineKingdom.getKingdomManager().getPlayerGrade(player).containPermission(Permissions.MANAGE_GRADE.getPermission()) && !player.getName().equalsIgnoreCase(target.getName()))
					{
						menu.closePlayerMenu();
						player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
						MenuList.addPlayerGrade(player, target).openMenu();
					}
					else if (player.getName().equalsIgnoreCase(target.getName()))
					{
						player.sendMessage(Message.GRADE_CAN_CHANGE_BY_YOUR_SELF);
						player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
					}
					else
					{
						player.sendMessage(Message.PERMISSION);
						player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
					}
				} else {
					MenuList.membersMenu(player).openMenu();
				}
			}
			
		}));
		
		menu.setItem(13, membre);
		
		menu.setButton(16, new Button(quitter, new MenuItemListener() {
			
			@Override
			public void onItemClick()
			{
				Kingdom playerKingdom = HumineKingdom.getKingdomManager().getPlayerKingdom(player);
				if (playerKingdom == HumineKingdom.getKingdomManager().getPlayerKingdom(target))
				{		
					Grade playerGrade = HumineKingdom.getKingdomManager().getPlayerGrade(player);
					Grade targetGrade = HumineKingdom.getKingdomManager().getPlayerGrade(target);
					//auto-quit
					if (player.getUniqueId().equals(target.getUniqueId()))
					{
						MenuList.validSupressionKingdomMenu(player, playerKingdom).openMenu();					
					}
					else if (playerGrade != null)
					{
						if (targetGrade != null && targetGrade.getName().equalsIgnoreCase(playerKingdom.getKingGradeName()))
						{ 
							for(OfflinePlayer op : playerKingdom.getMembers())
							{
								if (op.isOnline())
								{
									op.getPlayer().sendMessage(Message.KINGDOM_MUNITY(player));
									op.getPlayer().playSound(op.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 5, 1);
								}
							}
							player.sendMessage(ChatColor.ITALIC+"(je crois que tu t'es grillé)");
						}
						else if (playerGrade.containPermission(Permissions.EXCLUDE.getPermission()))
						{
							for (OfflinePlayer op : playerKingdom.getMembers())
							{
								if (op.isOnline())
								{
									op.getPlayer().sendMessage(Message.KINGDOM_LEFT(target, playerKingdom));
									op.getPlayer().playSound(op.getPlayer().getLocation(), Sound.ENTITY_FIREWORK_ROCKET_SHOOT, 5, 1);
								}
							}
							
							menu.closePlayerMenu();
							playerKingdom.removeMember(target);
							if (!player.getName().equalsIgnoreCase(target.getName()))
							{
								MenuList.membersMenu(player).openMenu();
							}							
						}
						else
						{
							player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
							player.sendMessage(Message.PERMISSION);
						}
					}
					else
					{
						player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
						player.sendMessage(Message.PERMISSION);
					}					
				}
			}
			
		}));
		
		menu.setButton(26, new Button(back, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				menu.closePlayerMenu();
				MenuList.membersMenu(player).openMenu();
			}
			
		}));
		
		MenuList.user.add(menu);
		return menu;		
	}
	
	
	
	
	//-----------------------------------------------------------
	// MENU DES TRAITRES
	//-----------------------------------------------------------
		
	public static Menu traitorsMenu(Player player)
	{
		closePlayerMenu(player);
		Kingdom playerKingdom = HumineKingdom.getKingdomManager().getPlayerKingdom(player);
		int size = playerKingdom.getTraitors().size();
		int c = 0;
		int multi = 1;
		for(int i = 1 ; i < size-2 ; i++)
		{
			c++;
			if(c >= 9)
			{				
				c = 0;
				multi++;
			}
		}
			
		Menu menu = new Menu(player, ChatColor.DARK_GREEN+"- Traîtres -", (multi+1)*9, false);
		
		boolean playerCanManageTraitor = HumineKingdom.getKingdomManager().getPlayerGrade(player) != null && HumineKingdom.getKingdomManager().getPlayerGrade(player).containPermission(Permissions.TRAITOR_GESTION.getPermission());
		
		ArrayList<String> lore = new ArrayList<String>();
			
		ItemStack quit = new ItemStack(Material.BARRIER);
		ItemMeta quitm = quit.getItemMeta();
		quitm.setDisplayName(ChatColor.RED+"Quitter");
		quit.setItemMeta(quitm);
			
		lore.clear();
			
		ItemStack ice = new ItemStack(Material.ICE);
		ItemMeta icem = ice.getItemMeta();
		icem.setDisplayName(ChatColor.AQUA+"Raffraîchir la page");
		ice.setItemMeta(icem);
					
		ItemStack glass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
			
		menu.fillLine(glass, menu.getSize()/9);
			
		int slot = 0;
		for (OfflinePlayer traitor : playerKingdom.getTraitors())
		{				
			ItemStack membre = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta membrem = (SkullMeta) membre.getItemMeta();
			membrem.setDisplayName(ChatColor.GREEN+traitor.getName());

			if (playerCanManageTraitor)
			{				
				lore.add(ChatColor.RED+""+ChatColor.ITALIC+"Cliquez pour gérer ce traître.");
				membrem.setLore(lore);
				lore.clear();
			}
			
			membrem.setOwningPlayer(traitor);
			membre.setItemMeta(membrem);
			if (playerCanManageTraitor)
			{
				menu.setButton(slot, new Button(membre, new MenuItemListener()
				{					
					@Override
					public void onItemClick()
					{
						menu.closePlayerMenu();
						player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
						MenuList.traitorProfilMenu(player, traitor).openMenu();
					}
						
				}));	
			}
			else
			{
				menu.setItem(slot, membre);
			}
			slot++;
		}
			
		menu.setButton(menu.getSize()-1, new Button(quit, new MenuItemListener()
		{				
			@Override
			public void onItemClick()
			{
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				MenuList.mainKingdomMenu(player).openMenu();
				MenuList.user.remove(menu);
			}				
		}));

		if (playerCanManageTraitor)
		{
			ItemStack add = new ItemStack(Material.WRITABLE_BOOK);
			ItemMeta addm = add.getItemMeta();
			addm.setDisplayName(ChatColor.GREEN+ "Ajouter un traître");
			add.setItemMeta(addm);
			
			menu.setButton(menu.getSize()-9, new Button(add, new MenuItemListener()
			{
				@Override
				public void onItemClick()
				{
					menu.closePlayerMenu();	
					player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
					PlayerChatEvent.getNameOfTraitor.add(player);
					player.sendMessage(Message.ADD_TRAITOR);
	
					MenuList.user.remove(menu);					
				}				
			}));
		}
			
		menu.setButton(menu.getSize()-5, new Button(ice, new MenuItemListener() {
				
				@Override
				public void onItemClick() 
				{
					menu.closePlayerMenu();
					player.playSound(player.getLocation(), Sound.BLOCK_SNOW_FALL, 5, 1);
					MenuList.traitorsMenu(player).openMenu();
				}				
			}));
			
		MenuList.user.add(menu);
		return menu;
	}
	
	//PLAYER MENU
	public static Menu traitorProfilMenu(Player player, OfflinePlayer traitor)
	{
		closePlayerMenu(player);
		Menu menu = new Menu(player, ChatColor.DARK_GREEN+"- "+ChatColor.WHITE+traitor.getName()+ChatColor.DARK_GREEN+" -", 3*9, false);
			
		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
			
		menu.fillLine(glass, 1);
		menu.fillLine(glass, 3);
			
		glass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
		glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
			
		menu.fillLine(glass, 2);
			
		ItemStack back = new ItemStack(Material.BARRIER);
		ItemMeta backm = back.getItemMeta();
		backm.setDisplayName(ChatColor.RED+"Retour");
		back.setItemMeta(backm);			
		
		ItemStack membre = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta membrem = (SkullMeta) membre.getItemMeta();
		membrem.setDisplayName(ChatColor.GREEN + traitor.getName());
		membrem.setOwningPlayer(traitor);
		membre.setItemMeta(membrem);
			
		ItemStack retirer = new ItemStack(Material.REDSTONE_BLOCK);
		ItemMeta quitterm = retirer.getItemMeta();
		
		quitterm.setDisplayName(ChatColor.RED+"Retirer des traîtres.");
						
		retirer.setItemMeta(quitterm);
			
		menu.setButton(10, new Button(membre, new MenuItemListener()
		{				
			@Override
			public void onItemClick() {	}				
		}));
			
		//menu.setItem(13, membre);
			
		menu.setButton(16, new Button(retirer, new MenuItemListener()
		{				
			@Override
			public void onItemClick()
			{
				Kingdom playerKingdom = HumineKingdom.getKingdomManager().getPlayerKingdom(player);
				playerKingdom.removeTraitor(traitor);	
				MenuList.traitorsMenu(player).openMenu();
			}
				
		}));
			
		menu.setButton(26, new Button(back, new MenuItemListener()
		{				
			@Override
			public void onItemClick()
			{
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				menu.closePlayerMenu();
				MenuList.traitorsMenu(player).openMenu();
			}				
		}));
			
		MenuList.user.add(menu);
		return menu;		
	}
	
	
	
	
	public static Menu validSupressionKingdomMenu(Player player, Kingdom kingdom)
	{
		closePlayerMenu(player);
		Menu menu = new Menu(player, ChatColor.DARK_GREEN+"- "+ ChatColor.WHITE + player.getName()+ChatColor.DARK_GREEN+" -", 3*9, false);

		Grade grade = HumineKingdom.getKingdomManager().getPlayerGrade(player);		

		boolean isKing = grade != null && grade.getName().equalsIgnoreCase(kingdom.getKingGradeName());
		
		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 1);
		menu.fillLine(glass, 3);
		
		glass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
		glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 2);
		
		ItemStack quitter = new ItemStack(Material.REDSTONE_BLOCK);
		ItemMeta quitterm = quitter.getItemMeta();
		
		quitterm.setDisplayName(ChatColor.RED+"Confirmer la décision");

		ArrayList<String> lore = new ArrayList<String>();
		
		lore.add("");
		if (isKing)
		{
			lore.add(ChatColor.RED+""+ChatColor.ITALIC+"Ton royaume sera supprimé et toute");
			lore.add(ChatColor.RED+"la gloire durement aquise avec sera perdue.");
		}
		else
		{
			lore.add(ChatColor.RED+""+ChatColor.ITALIC+"Tu vas quitter ce royaume définitivement.");
			lore.add(ChatColor.RED+"Confirmes-tu ta décision ?");			
		}
		quitterm.setLore(lore);
		
		quitter.setItemMeta(quitterm);
		
		menu.setButton(13, new Button(quitter, new MenuItemListener()
		{			
			@Override
			public void onItemClick()
			{
				if (isKing)
				{
					for (OfflinePlayer op : kingdom.getMembers())
					{
						if (op.isOnline())
						{
							op.getPlayer().sendMessage(Message.KINGDOM_DELET(kingdom, grade, player));
						}
					}	
					kingdom.delete();					
				} 
				else
				{
					for (OfflinePlayer op : kingdom.getMembers())
					{
						if (op.isOnline())
						{
							op.getPlayer().sendMessage(Message.KINGDOM_LEFT(player, kingdom));
							op.getPlayer().playSound(op.getPlayer().getLocation(), Sound.ENTITY_FIREWORK_ROCKET_SHOOT, 5, 1);
						}
					}
						
					kingdom.removeMember(player);							
				}	

				menu.closePlayerMenu();				
			}			
		}));
		
		ItemStack back = new ItemStack(Material.BARRIER);
		ItemMeta backm = back.getItemMeta();
		backm.setDisplayName(ChatColor.RED+"Retour");
		back.setItemMeta(backm);
		menu.setButton(26, new Button(back, new MenuItemListener()
		{			
			@Override
			public void onItemClick()
			{
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				menu.closePlayerMenu();
				MenuList.mainKingdomMenu(player).openMenu();
			}			
		}));
		
		MenuList.user.add(menu);
		return menu;	
	}
	
	//ADD GRADE FOR A PLAYER
	public static Menu addPlayerGrade(Player player, OfflinePlayer target) {
		closePlayerMenu(player);
		
		int size = HumineKingdom.getKingdomManager().getPlayerKingdom(player).getGrades().size();
		int c = 0;
		int multi = 1;
		for(int i = 1 ; i<size-2 ; i++){
			c++;
			if(c>=9)
			{				
				c = 0;
				multi++;
			}
		}
		
		Menu menu = new Menu(player, ChatColor.BLUE+"- "+ChatColor.WHITE+"Grades à ajouter"+ChatColor.BLUE+" -", (multi+1)*9, false);
		
		ItemStack membre = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta membrem = (SkullMeta) membre.getItemMeta();
		membrem.setDisplayName(ChatColor.GREEN+target.getName());
		membrem.setOwningPlayer(Bukkit.getOfflinePlayer(target.getUniqueId()));
		membre.setItemMeta(membrem);
		
		ItemStack retour = new ItemStack(Material.BARRIER);
		ItemMeta retourm = retour.getItemMeta();
		retourm.setDisplayName(ChatColor.RED+"Retour");
		retour.setItemMeta(retourm);
		
		
		ItemStack glass = new ItemStack(Material.MAGENTA_STAINED_GLASS);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, menu.getSize()/9);
		
		
		ArrayList<Grade> list = HumineKingdom.getKingdomManager().getPlayerKingdom(target).getGrades();
		ArrayList<String> lore;
		
		int slot = 0;
		for(int i = 0 ; i<list.size(); i++){
			if(!list.get(i).getName().equalsIgnoreCase(HumineKingdom.getKingdomManager().getPlayerKingdom(target).getKingGradeName()))
			{				
				lore = list.get(i).getPermissions();
				
				ItemStack nametag = new ItemStack(Material.NAME_TAG);
				ItemMeta nametagm = nametag.getItemMeta();
				nametagm.setDisplayName(ChatColor.BLUE+list.get(i).getName());
				nametagm.setLore(lore);
				nametag.setItemMeta(nametagm);
				
				final Grade g = list.get(i);
				
				menu.setButton(slot, new Button(nametag, new MenuItemListener() {
					
					@Override
					public void onItemClick()
					{
						if (HumineKingdom.getKingdomManager().getPlayerKingdom(player).getGrade(g.getName()) != null && HumineKingdom.getKingdomManager().getPlayerKingdom(target) == HumineKingdom.getKingdomManager().getPlayerKingdom(player))
						{
							Grade oldGrade = HumineKingdom.getKingdomManager().getPlayerGrade(target);
							if (oldGrade != null)
							{
								oldGrade.removeMember(target);
							}
							g.addMember(target);
							menu.closePlayerMenu();
							MenuList.playerProfilMenu(player, target).openMenu();
							player.sendMessage(Message.GRADE_UP(target, g));
							player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
							if (target.isOnline())
							{
								target.getPlayer().sendMessage(Message.GRADE_UP_TARGET(g));
								target.getPlayer().playSound(target.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 1);
							}
						}
						else
						{
							MenuList.membersMenu(player).openMenu();
							player.sendMessage(Message.PROBLEM);
						}
					}
					
				}));
				slot++;
			}
		}
		
		menu.setButton(menu.getSize()-1, new Button(retour, new MenuItemListener()
		{			
			@Override
			public void onItemClick()
			{
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				MenuList.playerProfilMenu(player, target).openMenu();
			}			
		}));
		
		menu.setItem(menu.getSize()-9, membre);
		
		MenuList.user.add(menu);
		return menu;
	}
	
	//GRADE LIST MENU
	public static Menu gradeListMenu(Player player)
	{
		closePlayerMenu(player);
		
		Kingdom playerKingdom = HumineKingdom.getKingdomManager().getPlayerKingdom(player);
		
		int size = playerKingdom.getGrades().size();
		int c = 0;
		int multi = 1;
		for(int i = 1 ; i<size-2 ; i++)
		{
			c++;
			if(c>=9)
			{				
				c = 0;
				multi++;
			}
		}
		
		Menu menu = new Menu(player, ChatColor.BLUE+ "- "+ChatColor.WHITE+"grades "+ChatColor.BLUE+"-", (multi+1)*9, false);

		Grade playerGrade = HumineKingdom.getKingdomManager().getPlayerGrade(player);
		
		boolean playerCanManageGrade = playerGrade != null && playerGrade.containPermission(Permissions.MANAGE_GRADE.getPermission());
				
		int slot = 0;
		for (Grade grade : HumineKingdom.getKingdomManager().getPlayerKingdom(player).getGrades())
		{
			if (!grade.getName().equalsIgnoreCase(HumineKingdom.getKingdomManager().getPlayerKingdom(player).getKingGradeName()))
			{				
				ItemStack nametag = new ItemStack(Material.NAME_TAG);
				ItemMeta nametagm = nametag.getItemMeta();
				nametagm.setDisplayName(ChatColor.BLUE+grade.getName());
				if (playerCanManageGrade)
				{
					ArrayList<String> lore = new ArrayList<String>();
					lore.add(ChatColor.WHITE + "" + ChatColor.ITALIC + "Cliquez pour voir le grade.");
					nametagm.setLore(lore);
				}
				nametag.setItemMeta(nametagm);
				
				if (playerCanManageGrade)
				{
					menu.setButton(slot, new Button(nametag, new MenuItemListener() {
						
						@Override
						public void onItemClick() {
							menu.closePlayerMenu();
							
							player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
							if (HumineKingdom.getKingdomManager().getPlayerKingdom(player).getGrade(grade.getName()) != null)
							{
								MenuList.gradeMenu(player, grade).openMenu();							
							}
							else
							{
								player.sendMessage(Message.PROBLEM);
								MenuList.gradeListMenu(player).openMenu();
							}
						}
						
					}));
				}
				else
				{
					menu.setItem(slot, nametag);
				}
				
				slot++;
				
			}
		}
		
		ItemStack glass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		ItemStack ice = new ItemStack(Material.ICE);
		ItemMeta icem = ice.getItemMeta();
		icem.setDisplayName(ChatColor.AQUA+"Raffraîchir la page");
		ice.setItemMeta(icem);
		
		menu.fillLine(glass, menu.getSize()/9);
		
		ItemStack quit = new ItemStack(Material.BARRIER);
		ItemMeta quitm = quit.getItemMeta();
		quitm.setDisplayName(ChatColor.RED+"Quitter");
		quit.setItemMeta(quitm);
		
		if (playerCanManageGrade)
		{
			ItemStack add = new ItemStack(Material.WRITABLE_BOOK);
			ItemMeta addm = add.getItemMeta();
			addm.setDisplayName(ChatColor.GREEN+"Ajouter un grade");
			add.setItemMeta(addm);
			
			menu.setButton(menu.getSize()-9, new Button(add, new MenuItemListener() {
				
				@Override
				public void onItemClick() {
					if (playerKingdom.getGrades().size() >= 9)
					{
						player.sendMessage("Tu as créé le nombre maximum de grade !");
						player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);						
					}
					else
					{
						if (HumineKingdom.getKingdomManager().getPlayerGrade(player) != null && HumineKingdom.getKingdomManager().getPlayerGrade(player).containPermission(Permissions.MANAGE_GRADE.getPermission())) {
							player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
							menu.closePlayerMenu();
							player.sendMessage(Message.GRADE_GIVE_NAME);
							PlayerChatEvent.getNameOfGrade.add(player);
						} else {
							player.sendMessage(Message.PERMISSION);
							player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
						}
					}
				}
				
			}));
		}
		
		menu.setButton(menu.getSize()-1, new Button(quit, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				menu.closePlayerMenu();
				MenuList.mainKingdomMenu(player).openMenu();
			}
			
		}));
		
		menu.setButton(menu.getSize()-5, new Button(ice, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.BLOCK_SNOW_FALL, 5, 1);
				MenuList.gradeListMenu(player).openMenu();
			}
			
		}));
		
		MenuList.user.add(menu);
		return menu;
				
	}
	
	//GRADE MENU
	public static Menu gradeMenu(Player player, Grade grade) {
		closePlayerMenu(player);
		Menu menu = new Menu(player, ChatColor.BLUE+" - "+ChatColor.WHITE+grade.getName()+ChatColor.BLUE+" -", 3*9, false);
		
		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 1);
		menu.fillLine(glass, 3);
		
		glass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
		glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 2);
		
		ItemStack back = new ItemStack(Material.BARRIER);
		ItemMeta backm = back.getItemMeta();
		backm.setDisplayName(ChatColor.RED+"Retour");
		back.setItemMeta(backm);
		
		ArrayList<String> lore = new ArrayList<String>();
		
		lore.add(ChatColor.GOLD+"~~~~~~~~~~~~~~~~~~~");
		
		for (String str : grade.getPermissions()) {
			lore.add("- "+str);
		}
		
		lore.add(ChatColor.GOLD+"~~~~~~~~~~~~~~~~~~~");
		
		ItemStack membre = new ItemStack(Material.NAME_TAG);
		ItemMeta membrem = membre.getItemMeta();
		membrem.setDisplayName(ChatColor.BLUE+grade.getName());
		membrem.setLore(lore);
		membre.setItemMeta(membrem);
		
		lore.clear();
		
		ItemStack paper = new ItemStack(Material.PAPER);
		ItemMeta paperm = paper.getItemMeta();
		paperm.setDisplayName(ChatColor.GREEN+"Modifier les permissions "+ChatColor.WHITE+grade.getName());
		paper.setItemMeta(paperm);
		
		ItemStack quitter = new ItemStack(Material.REDSTONE_BLOCK);
		ItemMeta quitterm = quitter.getItemMeta();
		quitterm.setDisplayName(ChatColor.RED+"Supprimer le grade: "+ChatColor.WHITE+grade.getName());	
		quitter.setItemMeta(quitterm);
		
		menu.setButton(10, new Button(paper, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				if (HumineKingdom.getKingdomManager().getPlayerKingdom(player).getGrade(grade.getName()) != null) {
					if (HumineKingdom.getKingdomManager().getPlayerGrade(player) != null && HumineKingdom.getKingdomManager().getPlayerGrade(player).containPermission(Permissions.MANAGE_GRADE.getPermission()) && HumineKingdom.getKingdomManager().getPlayerGrade(player) != grade) {
						
						player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
						menu.closePlayerMenu();
						MenuList.permissionsMenu(player, grade).openMenu();
						
					}  else if (HumineKingdom.getKingdomManager().getPlayerGrade(player) == grade) {
						
						player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
						player.sendMessage(Message.GRADE_AUTO_DELET);
						
					} else {
						player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
						player.sendMessage(Message.PERMISSION);
					}
				} else {
					MenuList.gradeListMenu(player).openMenu();
				}
			}
			
		}));
		
		menu.setItem(13, membre);
		
		menu.setButton(16, new Button(quitter, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				if (HumineKingdom.getKingdomManager().getPlayerKingdom(player).getGrade(grade.getName()) != null) {
					if (HumineKingdom.getKingdomManager().getPlayerGrade(player) != null && HumineKingdom.getKingdomManager().getPlayerGrade(player).containPermission(Permissions.MANAGE_GRADE.getPermission()) && HumineKingdom.getKingdomManager().getPlayerGrade(player) != grade) {
						player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
						menu.closePlayerMenu();
						player.sendMessage(Message.GRADE_DELET(grade));
						grade.getKingdom().removeGrade(grade);
						grade.delet();
						MenuList.gradeListMenu(player).openMenu();
						
					}  else if (HumineKingdom.getKingdomManager().getPlayerGrade(player) == grade) {
						
						player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
						player.sendMessage(Message.GRADE_AUTO_DELET);
						
					} else {
						player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
						player.sendMessage(Message.PERMISSION);
					}
				} else {
					MenuList.gradeListMenu(player).openMenu();
				}
			}
			
		}));
		
		menu.setButton(26, new Button(back, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				menu.closePlayerMenu();
				MenuList.gradeListMenu(player).openMenu();
			}
			
		}));
		
		MenuList.user.add(menu);
		
		return menu;
	}
	
	//PERMISSION MENU
	public static Menu permissionsMenu(Player player, Grade grade) {
		closePlayerMenu(player);
		
		int size = HumineKingdom.getKingdomManager().getPlayerKingdom(player).getGrades().size();
		int c = 0;
		int multi = 1;
		for(int i = 1 ; i<size-2 ; i++){
			c++;
			if(c>=9){				
				c = 0;
				multi++;
			}
		}
		
		Menu menu = new Menu(player, ChatColor.GREEN+"- "+ChatColor.WHITE+"Liste des permissions"+ChatColor.GREEN+" -", (multi+1)*9, false);		
		
		ItemStack igrade = new ItemStack(Material.NAME_TAG);
		ItemMeta igradem = igrade.getItemMeta();
		igradem.setDisplayName(ChatColor.BLUE+grade.getName());
		igrade.setItemMeta(igradem);
		
		ItemStack retour = new ItemStack(Material.BARRIER);
		ItemMeta retourm = retour.getItemMeta();
		retourm.setDisplayName(ChatColor.RED+"Retour");
		retour.setItemMeta(retourm);
		
		
		ItemStack glass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, menu.getSize()/9);
		
		for (int i = 0 ; i<Permissions.getAllPermissions().size() ; i++) {
			
			ArrayList<String> lore = new ArrayList<String>();
			
			for (String str : Permissions.getAllPermissions().get(i).getLore())
				lore.add(str);
			
			lore.add("   ");
			
			if (grade.getPermissions().contains(Permissions.getAllPermissions().get(i).getPermission())) {
				lore.add(ChatColor.RED+"Retirer la permission");		
			} else {
				lore.add(ChatColor.GREEN+"Ajouter la permission");
			}
			
			ItemStack paper = new ItemStack(Material.PAPER);
			ItemMeta paperm = paper.getItemMeta();
			paperm.setDisplayName(ChatColor.BLUE+"- "+Permissions.getAllPermissions().get(i).getName()+" -");
			paperm.setLore(lore);
			paper.setItemMeta(paperm);
			
			final int rank = i;
			
			menu.setButton(rank, new Button(paper, new MenuItemListener() {
				
				@Override
				public void onItemClick() {
					if (HumineKingdom.getKingdomManager().getPlayerKingdom(player).getGrade(grade.getName()) != null) {
					
						if (HumineKingdom.getKingdomManager().getPlayerGrade(player).containPermission(Permissions.MANAGE_GRADE.getPermission())) {
							
							boolean contain = false;
							
							for (String str : grade.getPermissions()) {
								if (str.equalsIgnoreCase(Permissions.getAllPermissions().get(rank).getPermission())) {
									contain = true;
								}
							}
							
							if (!contain) {
								grade.addPermission(Permissions.getAllPermissions().get(rank).getPermission());
							} else {
								grade.removePermission(Permissions.getAllPermissions().get(rank).getPermission());
							}
							
							player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
							menu.closePlayerMenu();
							MenuList.gradeMenu(player, grade).openMenu();
						}else {
							player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
							player.sendMessage(Message.PERMISSION);
						}
					
					} else {
						MenuList.gradeListMenu(player).openMenu();
					}
					
				}
				
			}));
			
		}
		
		menu.setItem(menu.getSize()-9, igrade);
		menu.setButton(menu.getSize()-1, new Button(retour, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				menu.closePlayerMenu();
				MenuList.gradeMenu(player, grade).openMenu();
			}
			
		}));
		
		MenuList.user.add(menu);
		return menu;
	}
	
	//SHIELDLIST MENU
	public static Menu GestionMenu(Player player) {
		
		closePlayerMenu(player);
		
		Menu menu = new Menu(player, ChatColor.BLUE+ "- "+ChatColor.WHITE+"mes generateurs "+ChatColor.BLUE+"-", 3*9, false);
		
		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 1);
		menu.fillLine(glass, 3);
		
		glass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
		glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 2);
		
		ItemStack back = new ItemStack(Material.BARRIER);
		ItemMeta backm = back.getItemMeta();
		backm.setDisplayName(ChatColor.RED+"Retour");
		back.setItemMeta(backm);
		
		ArrayList<String> lore = new ArrayList<String>();
		
		ItemStack beacon = new ItemStack(Material.BEACON);
		ItemMeta beaconm = beacon.getItemMeta();
		beaconm.setDisplayName(ChatColor.AQUA+"Info des générateurs");
		lore.add(ChatColor.GOLD+"---------------------------");
		lore.add(ChatColor.LIGHT_PURPLE+"nombre de generateur(s): "+ChatColor.WHITE+HumineKingdom.getKingdomManager().getPlayerKingdom(player).getShieldGenerators().size());
		lore.add(ChatColor.GOLD+"---------------------------");
		beaconm.setLore(lore);
		beacon.setItemMeta(beaconm);
		
		lore.clear();
		
		ItemStack saveCons = new ItemStack(Material.EMERALD);
		ItemMeta saveConsm = saveCons.getItemMeta();
		saveConsm.setDisplayName(ChatColor.GREEN+"Liste des plans de construction");
		lore.add("");
		saveConsm.setLore(lore);
		saveCons.setItemMeta(saveConsm);
		
		lore.clear();
		
		menu.setItem(13, beacon);
		menu.setButton(10, new Button(saveCons, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				if (HumineKingdom.getKingdomManager().getPlayerGrade(player) != null && HumineKingdom.getKingdomManager().getPlayerGrade(player).containPermission(Permissions.BEACON_GESTION.getPermission())) {
					player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
					menu.closePlayerMenu();
					MenuList.shematicMenu(player).openMenu();
				} else {
					player.sendMessage(Message.PERMISSION);
					player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
				}
			}
			
		}));
		
		
		menu.setButton(26, new Button(back, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				menu.closePlayerMenu();
				MenuList.mainKingdomMenu(player).openMenu();
			}
			
		}));
		
		MenuList.user.add(menu);
		return menu;
	}
	
	//SHEMATIC MENU
	public static Menu shematicMenu(Player player) {
		closePlayerMenu(player);
		int size = HumineKingdom.getKingdomManager().getPlayerKingdom(player).getShematics().size();
		int c = 0;
		int multi = 1;
		for(int i = 1 ; i<size-2 ; i++){
			c++;
			if(c>=9){				
				c = 0;
				multi++;
			}
		}
		
		Menu menu = new Menu(player, ChatColor.DARK_GREEN+"- Mes plans -", (multi+1)*9, false);
		
		ArrayList<String> lore = new ArrayList<String>();
		
		ItemStack quit = new ItemStack(Material.BARRIER);
		ItemMeta quitm = quit.getItemMeta();
		quitm.setDisplayName(ChatColor.RED+"Quitter");
		quit.setItemMeta(quitm);
		
		lore.clear();
		
		ItemStack ice = new ItemStack(Material.ICE);
		ItemMeta icem = ice.getItemMeta();
		icem.setDisplayName(ChatColor.AQUA+"Raffraîchir la page");
		ice.setItemMeta(icem);
				
		ItemStack glass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, menu.getSize()/9);
		
		int slot = 0;
		for (Shematic s : HumineKingdom.getKingdomManager().getPlayerKingdom(player).getShematics()) {
			
			ItemStack item = new ItemStack(Material.PAPER, 1);
			ItemMeta itemm = item.getItemMeta();
			itemm.setDisplayName(ChatColor.GREEN+s.getName());
			itemm.setLore(lore);
			lore.clear();
			item.setItemMeta(itemm);
			menu.setButton(slot, new Button(item, new MenuItemListener() {
				
				@Override
				public void onItemClick() {
						menu.closePlayerMenu();
					 	player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
					 	MenuList.shematicsMenu(player, s).openMenu();
					 	MenuList.user.remove(menu);
				}
				
			}));
			
			slot++;
		}
		
		menu.setButton(menu.getSize()-1, new Button(quit, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				MenuList.mainKingdomMenu(player).openMenu();
				MenuList.user.remove(menu);
			}
			
		}));

		menu.setButton(menu.getSize()-9, new Button(quit, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				MenuList.mainKingdomMenu(player).openMenu();
				MenuList.user.remove(menu);
			}
			
		}));

		ItemStack add = new ItemStack(Material.WRITABLE_BOOK);
		ItemMeta addm = add.getItemMeta();
		addm.setDisplayName(ChatColor.GREEN+"Ajouter un plan");
		add.setItemMeta(addm);
		
		menu.setButton(menu.getSize()-9, new Button(add, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();			
				
				if (HumineKingdom.getKingdomManager().getPlayerGrade(player) != null && HumineKingdom.getKingdomManager().getPlayerGrade(player).containPermission(Permissions.BEACON_GESTION.getPermission())) {
	
					player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
					PlayerClick.getBeacon.add(player);
					player.sendMessage(Message.SHEMATIC_CREATE);
				}else{
					  player.sendMessage(Message.PERMISSION);
					  player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
				}
					
				MenuList.user.remove(menu);
				
			}
			
		}));
		
		menu.setButton(menu.getSize()-5, new Button(ice, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.BLOCK_SNOW_FALL, 5, 1);
				MenuList.shematicMenu(player).openMenu();
			}
			
		}));
		
		MenuList.user.add(menu);
		return menu;
	}
	
	//SHEMATICS MENU
	public static Menu shematicsMenu(Player player, Shematic shematic) {
		closePlayerMenu(player);
		Menu menu = new Menu(player, "- "+shematic.getName()+" -", 3*9, false);
		
		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 1);
		menu.fillLine(glass, 3);
		
		glass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
		glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 2);
		
		ItemStack back = new ItemStack(Material.BARRIER);
		ItemMeta backm = back.getItemMeta();
		backm.setDisplayName(ChatColor.RED+"Retour");
		back.setItemMeta(backm);
		
		ArrayList<String> lore = new ArrayList<String>();
		
		lore.add(ChatColor.GOLD+"~~~~~~~~~~~~~~~~~~~");
		lore.add(ChatColor.DARK_PURPLE+"Blocs: "+ChatColor.WHITE+ChatColor.ITALIC+new FileManager(shematic.getFile()).getTextFile().size());
		lore.add(ChatColor.GOLD+"~~~~~~~~~~~~~~~~~~~");
		lore.add("");
		lore.add(ChatColor.GREEN+"Clique pour refaire un nouveau scan de la zone.");
		
		ItemStack membre = new ItemStack(Material.PAPER);
		ItemMeta membrem = membre.getItemMeta();
		membrem.setDisplayName(ChatColor.BLUE+shematic.getName());
		membrem.setLore(lore);
		membre.setItemMeta(membrem);
		
		lore.clear();
		
		ItemStack paper = new ItemStack(Material.ANVIL);
		ItemMeta paperm = paper.getItemMeta();
		paperm.setDisplayName(ChatColor.GREEN+"Reconstruire "+ChatColor.WHITE+shematic.getName());
		paper.setItemMeta(paperm);
		
		ItemStack list = new ItemStack(Material.OAK_SIGN);
		ItemMeta listm = list.getItemMeta();
		listm.setDisplayName(ChatColor.GREEN+"Voir les blocs manquants pour reconstruire " + ChatColor.WHITE+shematic.getName());
		list.setItemMeta(listm);
		
		ItemStack quitter = new ItemStack(Material.REDSTONE_BLOCK);
		ItemMeta quitterm = quitter.getItemMeta();
		quitterm.setDisplayName(ChatColor.RED+"Supprimer le plan : "+ChatColor.WHITE+shematic.getName());	
		quitter.setItemMeta(quitterm);
		
		menu.setButton(3*9-1, new Button(back, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				MenuList.shematicMenu(player).openMenu();
			}
			
		}));
		
		menu.setButton(1*9+1, new Button(paper, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();
			 	shematic.rebuild(player);
			 	player.sendMessage(ChatColor.DARK_PURPLE+"Reconstruction de "+shematic.getName()+".");
			 	player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 5, 1);
			}
			
		}));
		
		menu.setButton(2*9-2, new Button(quitter, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				player.sendMessage(Message.SHEMATIC_DELET(shematic));
				shematic.getkingdom().removeShematic(shematic);
				menu.closePlayerMenu();
				MenuList.shematicMenu(player).openMenu();
			}
			
		}));
		
		menu.setButton(9*1-5, new Button(membre, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();
				player.sendMessage(Message.SHEMATIC_SCAN);
				player.playSound(player.getLocation(), Sound.BLOCK_BEACON_AMBIENT, 5, 1);
				shematic.refresh();
				player.sendMessage(Message.SHEMATIC_SCAN_FINISH);
				player.playSound(player.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 5, 1);
				MenuList.shematicsMenu(player, shematic).openMenu();
			}
			
		}));

		if (!shematic.getRemainingBlocs().isEmpty())
		{
			menu.setButton(9*2-5, new Button(list, new MenuItemListener() {
				
				@Override
				public void onItemClick()
				{
					menu.closePlayerMenu();
					MenuList.remainingBlocs(player, shematic).openMenu();
				}
				
			}));
		}
		
		MenuList.user.add(menu);
		return menu;
	}
	
	public static Menu remainingBlocs(Player player, Shematic shematic)
	{
		closePlayerMenu(player);
		Menu menu = new Menu(player, "- "+shematic.getName()+" -", 4*9, false);
		
		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 1);
		menu.fillLine(glass, 3);
		
		glass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
		glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 2);
		
		ItemStack back = new ItemStack(Material.BARRIER);
		ItemMeta backm = back.getItemMeta();
		backm.setDisplayName(ChatColor.RED+"Retour");
		back.setItemMeta(backm);
		
		menu.setButton(4*9-1, new Button(back, new MenuItemListener()
		{			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				MenuList.shematicMenu(player).openMenu();
			}
			
		}));		
		
		int indexItem = 0;
		for (HashMap.Entry<Material, Integer> entry : shematic.getRemainingBlocs().entrySet())
		{
			ItemStack material = new ItemStack(entry.getKey());
			ItemMeta materialm = back.getItemMeta();
			materialm.setDisplayName(ChatColor.RED+ entry.getKey().name() + " : " + entry.getValue() + " manquants");
			material.setItemMeta(materialm);	

			menu.setButton(indexItem, new Button(material, new MenuItemListener()
			{
				@Override
				public void onItemClick()
				{					
					
				}
			}));	
			indexItem++;			
		}		
	
		MenuList.user.add(menu);
		return menu;
	}
	
	public static Menu rankedMenu(Player player) {		
		closePlayerMenu(player);
		
		Menu menu = new Menu(player, ChatColor.DARK_PURPLE+"- "+ChatColor.WHITE+"Classement"+ChatColor.DARK_PURPLE+" -", 3*9, false);

		Kingdom playerKingdom = HumineKingdom.getKingdomManager().getPlayerKingdom(player);
		
		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 1);
		menu.fillLine(glass, 3);
		
		ItemStack diamond = new ItemStack(Material.DIAMOND_BLOCK, 1);
		ItemMeta diamondm = diamond.getItemMeta();
		diamondm.setDisplayName(ChatColor.AQUA+"- "+ChatColor.DARK_AQUA+"1"+ChatColor.AQUA+" -");
		diamond.setItemMeta(diamondm);
		
		ItemStack gold = new ItemStack(Material.GOLD_BLOCK, 1);
		ItemMeta goldm = gold.getItemMeta();
		goldm.setDisplayName(ChatColor.YELLOW+"- "+ChatColor.GOLD+"2"+ChatColor.YELLOW+" -");
		gold.setItemMeta(goldm);
		
		ItemStack iron = new ItemStack(Material.IRON_BLOCK, 1);
		ItemMeta ironm = iron.getItemMeta();
		ironm.setDisplayName(ChatColor.GREEN+"- "+ChatColor.DARK_GREEN+"3"+ChatColor.GREEN+" -");
		iron.setItemMeta(ironm);
		
		ItemStack quit = new ItemStack(Material.BARRIER);
		ItemMeta quitm = quit.getItemMeta();
		quitm.setDisplayName(ChatColor.RED+"Quitter");
		quit.setItemMeta(quitm);
		
		menu.fillLine(glass, menu.getSize()/9);
		
		ItemStack first = null;
		ItemStack second = null;
		ItemStack three = null;
		
		int maxPoint = 	-1;
		OfflinePlayer firstKing = null;
		
		for (Kingdom kingdom : HumineKingdom.getKingdomManager().getKingdomList())
		{
			if (kingdom != null && kingdom.getGlory() > maxPoint)
			{
				firstKing = kingdom.getKing();
				maxPoint = kingdom.getGlory();
			}
		}
		
		ArrayList<String> lore = new ArrayList<String>();
		
		if (firstKing != null)
		{
			first = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta firstm = (SkullMeta) first.getItemMeta();
			firstm.setDisplayName(ChatColor.GREEN+HumineKingdom.getKingdomManager().getPlayerKingdom(firstKing).getName());
			firstm.setOwningPlayer(firstKing);
			lore.add(ChatColor.DARK_PURPLE+"Roi: "+ChatColor.WHITE+firstKing.getName());
			lore.add(ChatColor.DARK_PURPLE+"Gloire: "+ChatColor.WHITE+HumineKingdom.getKingdomManager().getPlayerKingdom(firstKing).getGlory());
			firstm.setLore(lore);
			first.setItemMeta(firstm);
		}
		
		lore.clear();
		
		int secondMaxPoint = -2147483648;
		OfflinePlayer secondKing = null;
		
		for (Kingdom kingdom : HumineKingdom.getKingdomManager().getKingdomList())
		{
			if (kingdom != null)
			{
				if (kingdom.getGlory() > secondMaxPoint && kingdom.getGlory() <= maxPoint) 
				{
					if (kingdom.getKing() != null && firstKing != null)
					{
						if (!kingdom.getKing().getName().equalsIgnoreCase(firstKing.getName()))
						{
							secondKing = kingdom.getKing();
							secondMaxPoint = kingdom.getGlory();
						}
					}
				}
			}
		}
		
		if (secondKing != null) {
			second = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta secondm = (SkullMeta) second.getItemMeta();
			secondm.setDisplayName(ChatColor.GREEN+HumineKingdom.getKingdomManager().getPlayerKingdom(secondKing).getName());
			secondm.setOwningPlayer(secondKing);
			lore.add(ChatColor.DARK_PURPLE+"Roi: "+ChatColor.WHITE+secondKing.getName());
			lore.add(ChatColor.DARK_PURPLE+"Gloire: "+ChatColor.WHITE+HumineKingdom.getKingdomManager().getPlayerKingdom(secondKing).getGlory());
			secondm.setLore(lore);
			second.setItemMeta(secondm);
		}

		lore.clear();
		
		int threeMaxPoint = -2147483648;
		OfflinePlayer threeKing = null;
		
		for (Kingdom kingdom : HumineKingdom.getKingdomManager().getKingdomList()) {
			if (kingdom.getGlory() > threeMaxPoint && kingdom.getGlory() <= secondMaxPoint && !kingdom.getKing().getName().equalsIgnoreCase(secondKing.getName()) && !kingdom.getKing().getName().equalsIgnoreCase(firstKing.getName())) {
				threeKing = kingdom.getKing();
				threeMaxPoint = kingdom.getGlory();
			}
		}
		
		if (threeKing != null) {
			three = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta threem = (SkullMeta) three.getItemMeta();
			threem.setDisplayName(ChatColor.GREEN+HumineKingdom.getKingdomManager().getPlayerKingdom(threeKing).getName());
			threem.setOwningPlayer(threeKing);
			lore.add(ChatColor.DARK_PURPLE+"Roi: "+ChatColor.WHITE+threeKing.getName());
			lore.add(ChatColor.DARK_PURPLE+"Gloire: "+ChatColor.WHITE+HumineKingdom.getKingdomManager().getPlayerKingdom(threeKing).getGlory());
			threem.setLore(lore);
			three.setItemMeta(threem);
		}
		lore.clear();
		
		if (playerKingdom != null)
		{
			ItemStack obsi = new ItemStack(Material.OBSIDIAN, 1);
			ItemMeta obsim = obsi.getItemMeta();
			obsim.setDisplayName(ChatColor.GREEN+"- "+ChatColor.DARK_GREEN + playerKingdom.getName() +ChatColor.GREEN+" -");
			obsi.setItemMeta(obsim);
			
			menu.setItem(21, obsi);
			menu.setItem(23, obsi);
			ItemStack ownGlory = new ItemStack(Material.PLAYER_HEAD, 1);
			SkullMeta ownGlorym = (SkullMeta) ownGlory.getItemMeta();
			ownGlorym.setDisplayName(ChatColor.GREEN+playerKingdom.getName());
			ownGlorym.setOwningPlayer(playerKingdom.getKing());
			lore.add(ChatColor.DARK_PURPLE+"Gloire: "+ChatColor.WHITE+playerKingdom.getGlory());
			ownGlorym.setLore(lore);
			ownGlory.setItemMeta(ownGlorym);
			menu.setItem(22, ownGlory);
		}
		
		lore.clear();
		
		ItemStack noBody = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta noBodym = (SkullMeta) noBody.getItemMeta();
		noBodym.setDisplayName(ChatColor.GREEN+"Personne...");
		noBodym.setOwningPlayer(Bukkit.getOfflinePlayer("MHF_Question"));
		noBody.setItemMeta(noBodym);
		lore.clear();
		
		menu.setItem(1, (first != null) ? first : noBody);
		menu.setItem(4, (second != null) ? second : noBody);
		menu.setItem(7, (three != null) ? three : noBody);
		
			
		menu.setItem(0, diamond);
		menu.setItem(2, diamond);
		menu.setItem(3, gold);
		menu.setItem(5, gold);
		menu.setItem(6, iron);
		menu.setItem(8, iron);
		
		
		menu.setButton(menu.getSize()-1, new Button(quit, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				MenuList.mainKingdomMenu(player).openMenu();
				MenuList.user.remove(menu);
			}
			
		}));

		menu.setButton(menu.getSize()-9, new Button(quit, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				MenuList.mainKingdomMenu(player).openMenu();
				MenuList.user.remove(menu);
			}
			
		}));
		
		MenuList.user.add(menu);
		return menu;		
	}
	
	public static void closePlayerMenu(Player player) {
		for(int i = 0 ; i<user.size() ; i++) {
			if (user.get(i).getPlayer() != null && user.get(i).getPlayer().getName().equalsIgnoreCase(player.getName())) {
				user.get(i).closePlayerMenu();
				MenuList.user.remove(user.get(i));
				i--;
			}
		}
	}

	
}