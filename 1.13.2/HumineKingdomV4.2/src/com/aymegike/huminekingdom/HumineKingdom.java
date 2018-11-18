package com.aymegike.huminekingdom;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import com.aymegike.huminekingdom.listener.CommandManager;
import com.aymegike.huminekingdom.utils.BlockList;
import com.aymegike.huminekingdom.utils.MenuList;
import com.aymegike.huminekingdom.utils.Permissions;
import com.aymegike.huminekingdom.utils.managers.EggManager;
import com.aymegike.huminekingdom.utils.managers.FileManager;
import com.aymegike.huminekingdom.utils.managers.GloryManager;
import com.aymegike.huminekingdom.utils.managers.KingdomManager;
import com.aymegike.huminekingdom.utils.models.Grade;
import com.aymegike.huminekingdom.utils.models.Kingdom;
import com.aypi.utils.inter.ZoneListener;

public class HumineKingdom extends JavaPlugin {
	
	/*
	 * Le plugin a besoin des imports:
	 *  
	 * Spigot 1.13.2 
	 * AYPI.V1.4.2
	 *  
	 */
	
	
	private static FileManager fileManager;
	private static KingdomManager kManager;
	private static GloryManager gloryManager;
	private static EggManager eggManager;
	
	public void onEnable() {
		System.out.println("-------------------------------------------");
		System.out.println("[HumineKingdom] EWOK PRODUCTION !");
		System.out.println("-------------------------------------------");
		new com.aymegike.huminekingdom.listener.EventManager(this);
		new FileManager();
		kManager = new KingdomManager();
		new CommandManager(this);
		gloryManager = new GloryManager();
		eggManager = new EggManager();
	}
	
	public void onDisable() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			MenuList.closePlayerMenu(player);
			player.closeInventory();
		}
	}
	
	public static FileManager getFileManager() {
		return fileManager;
	}
	
	public static Kingdom getPlayerKingdom(OfflinePlayer player) {
		for (Kingdom kingdom : kManager.getKingdomList()) {
			for (OfflinePlayer op : kingdom.getMembers()) {
				if (op.getUniqueId().toString().equalsIgnoreCase(player.getUniqueId().toString())) {
					return kingdom;
				}
			}
		}
		
		return null;
	}
	
	public static Grade getPlayerGrade(OfflinePlayer player) {
		
		if (HumineKingdom.getPlayerKingdom(player) != null) {
			for (Grade grade : HumineKingdom.getPlayerKingdom(player).getGrades()) {
				if (grade.getMembers().contains(player)) {
					return grade;
				}
			}
		}
		
		return null;
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
	
	public static boolean playerAsKingdomPermission(OfflinePlayer player, String permission) {
		return HumineKingdom.getPlayerGrade(player).getPermissions().contains(permission);
	}
	
	public static GloryManager getGloryManager() {
		return gloryManager;
	}
	
	public static EggManager getEggManager() {
		return eggManager;
	}
	
	//ZONE PLAYER GESTION
	
	public static ZoneListener getZoneListener(Kingdom kingdom) {

		return new ZoneListener() {

			private void cancel(Player player, Event e) {
				
				player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 5, 1);
			}
			
			private void accept(Player player, Event e) {}

			@Override
			public void onPlayerEnter(Player player, PlayerMoveEvent e) {}

			@Override
			public void onPlayerTryToPlaceBlock(Player player, Block block, BlockPlaceEvent e) {
				
				if (player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
					
					Location ploc = new Location(e.getBlock().getLocation().getWorld(), e.getBlock().getLocation().getBlockX() + 0.5, e.getBlock().getLocation().getBlockY(), e.getBlock().getLocation().getBlockZ()+0.5);
					
					if (HumineKingdom.getPlayerKingdom(player) != null && HumineKingdom.getPlayerKingdom(player) == kingdom) {
						if (HumineKingdom.getPlayerGrade(player) != null && HumineKingdom.getPlayerGrade(player).containPermission(Permissions.BUILD.getPermission())) {
							accept(player, e);
						} else {
							if (!BlockList.isInZoneList(e.getBlock().getType()) && e.getPlayer().getGameMode() != GameMode.CREATIVE) {
								e.getBlock().breakNaturally();
								DustOptions dustOptions = new DustOptions(Color.BLACK, (float) 10.0);
								e.getBlock().getWorld().spawnParticle(Particle.REDSTONE, ploc, 10, 0, 0.5, 0, 2, dustOptions);
								cancel(player, e);
							}
						}
					} else {
						if (!BlockList.isInZoneList(e.getBlock().getType()) && e.getPlayer().getGameMode() != GameMode.CREATIVE) {
							e.getBlock().breakNaturally();
							DustOptions dustOptions = new DustOptions(Color.BLACK, (float) 10.0);
							e.getBlock().getWorld().spawnParticle(Particle.REDSTONE, ploc, 10, 0, 0.5, 0, 2, dustOptions);
							cancel(player, e);
						}
					}
					
				}
				
			}

			@Override
			public void onPlayerTryToRemoveBlock(Player player, Block block, BlockBreakEvent e) {
				if (player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
					
					Location ploc = new Location(e.getBlock().getLocation().getWorld(), e.getBlock().getLocation().getBlockX() + 0.5, e.getBlock().getLocation().getBlockY(), e.getBlock().getLocation().getBlockZ()+0.5);
					
					if (HumineKingdom.getPlayerKingdom(player) != null && HumineKingdom.getPlayerKingdom(player) == kingdom) {
						if (HumineKingdom.getPlayerGrade(player) != null && HumineKingdom.getPlayerGrade(player).containPermission(Permissions.BREAK.getPermission())) {
							accept(player, e);
						} else {
							if (!BlockList.isInZoneList(e.getBlock().getType()) && e.getPlayer().getGameMode() != GameMode.CREATIVE) {
								e.setCancelled(true);
								DustOptions dustOptions = new DustOptions(Color.BLACK, (float) 10.0);
								e.getBlock().getWorld().spawnParticle(Particle.REDSTONE, ploc, 10, 0, 0.5, 0, 2, dustOptions);
								cancel(player, e);
							}
						}
					} else {
						if (!BlockList.isInZoneList(e.getBlock().getType()) && e.getPlayer().getGameMode() != GameMode.CREATIVE) {
							e.setCancelled(true);
							DustOptions dustOptions = new DustOptions(Color.BLACK, (float) 10.0);
							e.getBlock().getWorld().spawnParticle(Particle.REDSTONE, ploc, 10, 0, 0.5, 0, 2, dustOptions);
							cancel(player, e);
						}
					}
					
				}
			}

			@Override
			public void onPlayerTryToInteractEvent(Player player, PlayerInteractEvent e) {
				if (player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
					
					if (HumineKingdom.getPlayerKingdom(player) != null && HumineKingdom.getPlayerKingdom(player) == kingdom) {
						if (HumineKingdom.getPlayerGrade(player) != null && HumineKingdom.getPlayerGrade(player).containPermission(Permissions.BREAK.getPermission())) {
							accept(player, e);
						} else {
							if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
								Location ploc = new Location(e.getClickedBlock().getLocation().getWorld(), e.getClickedBlock().getLocation().getBlockX() + 0.5, e.getClickedBlock().getLocation().getBlockY(), e.getClickedBlock().getLocation().getBlockZ()+0.5);
								if (e.getClickedBlock().getType() == Material.BEACON) {
									e.setCancelled(true);
									DustOptions dustOptions = new DustOptions(Color.BLACK, (float) 10.0);
									e.getClickedBlock().getWorld().spawnParticle(Particle.REDSTONE, ploc, 10, 0, 0.5, 0, 2, dustOptions);
								}
							}
						}
					} else {
						
						if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
							Location ploc = new Location(e.getClickedBlock().getLocation().getWorld(), e.getClickedBlock().getLocation().getBlockX() + 0.5, e.getClickedBlock().getLocation().getBlockY(), e.getClickedBlock().getLocation().getBlockZ()+0.5);
							if (e.getClickedBlock().getType() == Material.BEACON) {
								e.setCancelled(true);
								DustOptions dustOptions = new DustOptions(Color.BLACK, (float) 10.0);
								e.getClickedBlock().getWorld().spawnParticle(Particle.REDSTONE, ploc, 10, 0, 0.5, 0, 2, dustOptions);
							}
						}
			
					}
					
				}

			}

		};
		
	}

}
